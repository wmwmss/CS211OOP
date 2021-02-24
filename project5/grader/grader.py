#!/usr/bin/python3

import sys
import shutil
import subprocess
from pathlib import Path
from os import chdir
import platform
import re
import os

# definitions

gradehelp = 'GradeHelper.java'
junitfile = 'junit-cs211.jar'

COMPILE_CODE = 0
COMPILE_TEST = 1
RUN_TEST = 2

# utility functions

def rm_all(path):
    for f in path.iterdir():
        if f.is_dir():
            rm_all(f)
            f.rmdir()
        else:
            f.unlink()

def copy(p1, p2):
    shutil.copy(str(p1), str(p2))

def copy_java(p1, p2):
    for f in list(p1.glob('**/*.java')):
        copy(f, p2)

def parse_score(report):
    lines = report.splitlines()
    score = 0
    for line in lines:
        m = re.search(r"\[ *([0-9]+)/.*\]", line)
        if m:
            score += int(m.group(1))
    return score

def admin_score(rows):
    for r in rows:
        if 'admin' in r and len(r) > 1:
            return int(r[1])
    return 0

# check args

if '-h' in sys.argv or '--help' in sys.argv:
    print('grader.py [-h] [-d] [project_dir]')
    print('    -h help')
    print('    -d debug output')
    print('    by default, project_dir=files')
    quit()
debug = False
if '-d' in sys.argv:
    debug = True

files = 'files'
for arg in sys.argv[1:]:
    if arg[0:1] != '-':
        files = arg

# system-specific setup

classpath = '.:{}'.format(junitfile)
if platform.system() == 'Windows':
    classpath = '.;{}'.format(junitfile)

def sendfile(src, dest):
    if platform.system() == 'Windows':
        shutil.copy(str(src), str(dest))
    else:
        dest.symlink_to(src)

# setup paths

cdir = Path.cwd()
wdir = cdir / 'work'
fdir = cdir / files

if wdir.exists() and not wdir.is_dir():
    wdir.unlink()
if not wdir.exists():
    wdir.mkdir(0o755)
if not fdir.exists() or not fdir.is_dir():
    print(str(fdir) + ' does not exist', file=sys.stderr)
    exit(1)

# read in configuration / command information
conffile = fdir / 'config'
cmd = []

if not conffile.exists():
    print('config file ' + str(conffile) + ' does not exist', file=sys.stderr)
    exit(1)

with conffile.open() as f_conf:
    cmdcontent = f_conf.readlines()
    cmd = [x.strip().split(':') for x in cmdcontent] 

# iterate over all student dirs

studentdirs = [x for x in fdir.iterdir() if x.is_dir()]

for stdir in sorted(studentdirs, key=lambda x: x.name[5:]):
    # set up the working dir
    rm_all(wdir)
    copy_java(stdir, wdir)
    chdir(wdir)

    # parse the dir name

    dname = stdir.name.split('_')
    section = dname[0]
    userid = dname[1]
    project = dname[2].lower()

    # find and process the ID file

    for idfilename in ['id.txt', 'ID.txt', 'Id.txt', 'id.txt.txt', 'ID.TXT', 'id', 'ID', 'Id', 'file not found']:
        idfile = stdir / idfilename
        if idfile.exists():
            break
    admin_total = admin_score(cmd)
    admin = admin_total
    notes = ''
    idc = {}
    if not idfile.exists():
        admin = 0
        notes = '\n      +---> no ID file found;'
    else:
        with idfile.open() as f_id:
            idcontent = f_id.readlines()
            idcontent = [x.split(':') for x in idcontent]
            idc = {x[0].strip().lower() : x[1].strip() for x in idcontent if len(x) > 1}
    for s in [['full name', ''], ['userid', userid], ['g#', '********'], ['lecture section', ''], ['lab section', section]]:
        if not s[0] in idc:
            admin = 0
            notes += '\n      +---> missing {};'.format(s[0])
            idc[s[0]] = s[1]
    if 'g' in idc['g#'] or 'G' in idc['g#']:
        idc['g#'] = idc['g#'][1:]
    idstring = '{} ({}, G{}) CS211-{}({})'.format(idc['full name'],idc['userid'],idc['g#'],idc['lecture section'],idc['lab section'])

    # print initial info

    print(idstring)
    print('  [{:3}/{:3}] admin subtotal {}'.format(admin, admin_total, notes))

    # install tester into working dir
            
    for ffile in fdir.iterdir():
        if not ffile.is_dir():
            destfile = wdir / ffile.name
            if destfile.exists() and not destfile.is_dir():
                destfile.unlink()
            sendfile(ffile, destfile) 
    for ffile in [cdir / junitfile, cdir / gradehelp]:
        destfile = wdir / ffile.name
        if destfile.exists() and not destfile.is_dir():
            destfile.unlink()
        sendfile(ffile, destfile) 

    # initial compile

    results = [[0,0,0] for x in cmd]
    for c in range(len(cmd)):
        curcmd = cmd[c]
        if len(curcmd) < 2 or 'admin' in curcmd:
            continue
        spr = subprocess.run(['javac','-encoding','ISO-8859-1']+curcmd[0].split(','), stderr=subprocess.PIPE)
        if spr.returncode == 0:
            results[c][COMPILE_CODE] = 1
        else:
            logfile = stdir / 'compile.{}.txt'.format(curcmd[0])
            if debug:
                print(spr.stderr.decode('utf-8'))
            with logfile.open('w') as f:
                f.write(spr.stderr.decode('utf-8'))

    # build the tester(s)
     
    for c in range(len(cmd)):
        curcmd = cmd[c]
        if len(curcmd) < 2 or 'admin' in curcmd or results[c][COMPILE_CODE] == 0:
            continue
        spr = subprocess.run(['javac', '-encoding','ISO-8859-1','-cp', classpath, curcmd[1]], stderr=subprocess.PIPE)
        if spr.returncode == 0:
            results[c][COMPILE_TEST] = 1
        else:
            logfile = stdir / 'compile.{}.txt'.format(curcmd[1])
            if debug:
                print(spr.stderr.decode('utf-8'))
            with logfile.open('w') as f:
                f.write(spr.stderr.decode('utf-8'))

    # run the tester(s)

    for c in range(len(cmd)):
        curcmd = cmd[c]
        if results[c][COMPILE_TEST] == 0 or 'admin' in curcmd:
            continue
        tester = curcmd[1][:-5]
        if debug:
            spr = subprocess.run(['java', '-cp', classpath, tester, 'debug'], stdout=subprocess.PIPE,stderr=subprocess.PIPE)
        else:
            spr = subprocess.run(['java', '-cp', classpath, tester], stdout=subprocess.PIPE,stderr=subprocess.PIPE)
        scorereport = spr.stdout.decode('utf-8')
        print(scorereport.rstrip())
        results[c][RUN_TEST] = parse_score(scorereport)
        if spr.returncode != 0:
            logfile = stdir / 'test.{}.txt'.format(tester)
            with logfile.open('w') as f:
                f.write(spr.stderr.decode('utf-8'))
                #f.write(spr.stdout.decode('utf-8'))

     # report the results

    total = admin
    perfect = admin_total
    for c in range(len(cmd)):
        if 'admin' in cmd[c]:
            continue
        subtotal = 0
        weights = [int(x) for x in cmd[c][2:]]
        if len(weights) < 3:
            weights = [1,1,1]
        submax = weights[2]
        for i in range(len(weights)-1):
            if results[c][i] > 0:
                subtotal = weights[i]
        if results[c][2] > subtotal:
            subtotal = results[c][2]
        total += subtotal
        notes = '  [{:3}/{:3}] tester {} subtotal'.format(subtotal, submax, cmd[c][1][:-5])
        if results[c][0] == 0:
            notes += '\n      +---> ' + cmd[c][0] + ' failed to compile;'
        elif results[c][1] == 0:
            notes += '\n      +---> ' + cmd[c][1] + ' tester failed to compile - source file incompatible with tester;'
        print(notes)    
        perfect += weights[2]
    print('  [{:3}/{:3}] TOTAL'.format(total, perfect))
    print()

chdir(cdir)
