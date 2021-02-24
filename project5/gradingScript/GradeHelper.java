import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.*;
import java.util.regex.*;

public class GradeHelper {

	public final static double ERR = 0.00001;
	private static ArrayList<String[]> testResults = new ArrayList<>();
	private static boolean debug = false;

  public static void setDebug(boolean debug) { GradeHelper.debug = debug; }

  public static void tester(Class<?> testBody, Object[][][] matrix, String[] args) {
	  for (String s : args) {
		  if (s.equals("debug")) { setDebug(true); }
	  }
	  tester(testBody, matrix);
  }

  public static void tester(Class<?> testBody, Object[][][] matrix) {
      GradeHelper.runTests(testBody);
      System.out.print(getScores(matrix));
      System.err.println(getFailures());
      for (String[] s : testResults) {
	      if (s[1] != null) { System.exit(1); }
      }
  }
  
  /*Main method runs tests in this file*/ 
  public static Result runTests(Class<?> testBody) {
	  setUp();
	  JUnitCore tester = new JUnitCore();
	  tester.addListener( new RunListener() {
            public void testFailure(Failure failed) throws Exception {
		    String[] result = new String[]{failed.getTestHeader(), failed.getTrace(), failed.getMessage()};
		    testResults.add(result);
	    }
	    public void testFinished(Description desc) throws Exception { 
		    int num = testResults.size();
		    if (num > 0 && testResults.get(num-1)[0].equals(desc.toString())) return; 
		    String[] result = {desc.toString(), null, null};
		    testResults.add(result);
	    }
	  } );
	  Result result = tester.run(testBody);
	  cleanUp();
	  return result;
  } 

  public static String getFailures() {
	  int counter = 1;
	  String msg = "";
	  for (String[] s : testResults) {
		  if (s[1] != null) {
			  msg += String.format("(%d) %s: %s\n.", counter++, s[0], s[1]);
		  }
	  }
	  return msg;
  }

  public static String getScores(Object[][][] matrix) {
	  String result = "";
	  int totearn = 0, totpts = 0;
	  for (Object[][] category : matrix) {
		  scaleScores(category);
		  int points = 0;
		  String catdesc = "";
		  double total = 0.0;
		  String testmsg = "";
		  for (Object[] test : category) {
			  if (test.length > 0 && test[0] instanceof Integer) {
				  points = (Integer)test[0]; 
				  if (test.length > 1) catdesc = test[1].toString();
			  }
			  else {
				  String desc = (String)test[0];
				  Pattern p = Pattern.compile(desc);
				  double scale = (Double)test[1];
				  if (test.length > 2) { desc = (String)test[2]; }

				  int count = 0; 
				  int countpassed = 0;
				  for (String[] testRes : testResults) {
					  if (p.matcher(testRes[0]).find()) {
						  count++;
						  if (testRes[1]==null) countpassed++;
						  else if (debug) {
							  testmsg += String.format("      (*E*) %s: %s\n", testRes[0], testRes[2]);
						  }
					  }
				  }
				  double score = 1.0;
				  if (count > 0) { score = ((double)countpassed)/count; }
				  if (countpassed < count) {
					  testmsg += String.format("      +---> %d/%d %s tests failed;\n", (count-countpassed), count, desc);
				  }
				  if (debug) {
					  testmsg += String.format("      : test %s; %d cases found, %d passed, %s weight\n", (String)test[0], count, countpassed, scale);
				  }

				  total += score*scale;

			  }
		  }
		  total *= points;
		  long intTotal = Math.round(total);
		  if (intTotal == points && points - total > 0.01) { intTotal--; }
		  result += String.format("  [%3d/%3d] %s\n%s", intTotal, points, catdesc, testmsg);
		  totpts += points;
		  totearn += intTotal;
	  }
	  //result += String.format("  [%3d/%3d] total\n", totearn, totpts);
	  return result;
  }

  private static void scaleScores(Object[][] category) {
	  double div = 0;
	  for (Object[] test : category) {
		  if (test.length > 1 && test[1] instanceof Double) 
		  { 
	              div += (Double)test[1]; 
		  }
	  }
	  for (Object[] test : category) {
		  if (test.length > 1 && test[1] instanceof Double)
		  { test[1] = (Double)test[1] / div; }
	  }
  }

  private static ByteArrayOutputStream localOut, localErr;
  private static ByteArrayInputStream localIn;
  private static PrintStream sysOut, sysErr;
  private static InputStream sysIn;
  public static final String [] empty = {};

  //@BeforeClass
  public static void setUp() {
    sysOut = System.out;
    sysErr = System.err;
    sysIn  = System.in;
  }

  //@AfterClass
  public static void cleanUp() {
	  unsetCapture();
	  unsetInput();
  }

  public static void resetIO() {
	  setCapture();
	  setInput("");
  }

  // Before every test is run, reset the streams to capture
  // stdout/stderr
  //@Before
  public static void setCapture() {
    localOut = new ByteArrayOutputStream();
    localErr = new ByteArrayOutputStream();
    System.setOut(null);
    System.setErr(null);
    System.setOut(new PrintStream(localOut));
    System.setErr(new PrintStream(localErr));
  }

  public static String getCapture() {
    return localOut.toString().replaceAll("\\r?\\n", "\n");
  }

  // After every test, restore stdout/stderr
  //@After
  public static void unsetCapture() {
    System.setOut(null);
    System.setOut(sysOut);
    System.setErr(null);
    System.setErr(sysErr);
  }

  public static void setInput(String input) {
    localIn = new ByteArrayInputStream(input.getBytes());
    System.setIn(localIn);
  }

  public static void unsetInput() {
    try {
      if (localIn != null) localIn.close();
    }
    catch (Exception e) {}
    System.setIn(null);
    System.setIn(sysIn);
  }

}
