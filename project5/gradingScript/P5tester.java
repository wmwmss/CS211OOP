/** Example of using unit tests for this assignment.  To run them on the command line, make
 * sure that the junit-cs211.jar is in the same directory.  
 * 
 * On Mac/Linux:
 *  javac -cp .:junit-cs211.jar *.java         # compile everything
 *  java -cp .:junit-cs211.jar P5tester        # run tests
 * 
 * On windows replace colons with semicolons: (: with ;)
 *  demo$ javac -cp .;junit-cs211.jar *.java   # compile everything
 *  demo$ java -cp .;junit-cs211.jar P5tester  # run tests
 */
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

  
public class P5tester {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("P5tester");
  }

  // ******** Coordinate ********
  
  private void check_coord(int x, int y, String value) {
    Coordinate c = new Coordinate(x,y);
    assertEquals("Coordinate "+value+" incorrect X value", x, c.getX());
    assertEquals("Coordinate "+value+" incorrect Y value", y, c.getY());
    assertEquals("Coordinate "+value+" incorrect toString", value, c.toString());
  }
  
  @Test(timeout=1000) public void coordinate_check_1() { check_coord(0,0,"(0, 0)"); }
  @Test(timeout=1000) public void coordinate_check_2() { check_coord(1,5,"(1, 5)"); }
  @Test(timeout=1000) public void coordinate_check_3() { check_coord(3,-6,"(3, -6)"); }
  @Test(timeout=1000) public void coordinate_check_4() { check_coord(-99,33,"(-99, 33)"); }
 
  private void check_coord_plus(int x, int y, int dx, int dy, int nx, int ny) {
    Coordinate c = new Coordinate(x,y);
    Coordinate d = new Coordinate(dx,dy);
    String errBase = "Coordinate ("+x+", "+y+")+("+dx+", "+dy+") incorrect";
    assertEquals(errBase+" X value (x,y version)", nx, c.plus(dx,dy).getX());
    assertEquals(errBase+" Y value (x,y version)", ny, c.plus(dx,dy).getY());
    assertEquals(errBase+" X value (coord version)", nx, c.plus(d).getX());
    assertEquals(errBase+" Y value (coord version)", ny, c.plus(d).getY());
  }

  @Test(timeout=1000) public void coordinate_plus_1() { check_coord_plus(0,0,1,1,1,1); }
  @Test(timeout=1000) public void coordinate_plus_2() { check_coord_plus(0,0,-1,3,-1,3); }
  @Test(timeout=1000) public void coordinate_plus_3() { check_coord_plus(2,4,1,1,3,5); }
  @Test(timeout=1000) public void coordinate_plus_4() { check_coord_plus(2,4,4,-3,6,1); }
  @Test(timeout=1000) public void coordinate_plus_5() { check_coord_plus(-2,1,1,1,-1,2); }
  @Test(timeout=1000) public void coordinate_plus_6() { check_coord_plus(-2,1,3,-8,1,-7); }

  // ******** Direction ********

  private Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.NONE};
  private int[][] direction_coords = {{0,1}, {0,-1}, {-1,0}, {1,0}, {0,0}};
  
  @Test(timeout=1000) public void direction_delta() { 
    for (int i = 0;  i < directions.length;  i++) {
      Direction d = directions[i];
      int[] c = direction_coords[i];
      assertEquals("Direction "+d+" has incorrect delta X", c[0], d.delta().getX());
      assertEquals("Direction "+d+" has incorrect delta Y", c[1], d.delta().getY());
    }
  }
  
  private void check_coord_dir(int x, int y, int dir, int nx, int ny) {
    Direction d = directions[dir];
    int dx = direction_coords[dir][0];
    int dy = direction_coords[dir][1];
    Coordinate c = new Coordinate(x,y);
    String errBase = "Coordinate ("+x+", "+y+")+"+d+" incorrect";
    assertEquals(errBase+" X value (direction version)", nx, c.plus(d).getX());
    assertEquals(errBase+" Y value (direction version)", ny, c.plus(d).getY());
  }

  @Test(timeout=1000) public void coordinate_plusdir_0() { check_coord_dir(5,5,0,5,6); }
  @Test(timeout=1000) public void coordinate_plusdir_1() { check_coord_dir(5,5,1,5,4); }
  @Test(timeout=1000) public void coordinate_plusdir_2() { check_coord_dir(5,5,2,4,5); }
  @Test(timeout=1000) public void coordinate_plusdir_3() { check_coord_dir(5,5,3,6,5); }
  @Test(timeout=1000) public void coordinate_plusdir_4() { check_coord_dir(5,5,4,5,5); }
  
  // ******** GridCoordsOutOfBoundsException ********
  
  @Test(timeout=1000) public void gridexcep_inherits() {
    Object o = new GridCoordsOutOfBoundsException();
    // writing this next line was an adventure:
    assertTrue("GridCoordsOutOfBoundsException should be an IndexOutOfBoundsException", o instanceof IndexOutOfBoundsException);
  }
  
  @Test(timeout=1000) public void gridexcep_constructor_1() {
    Exception e = new GridCoordsOutOfBoundsException();
  }
  
  @Test(timeout=1000) public void gridexcep_constructor_2() {
    Exception e = new GridCoordsOutOfBoundsException("test");
    assertEquals("Incorrect exception message", "test", e.getMessage());
  }

  // ******** Grid ********
  
  @Test(timeout=1000) public void grid_dimensions() {
    Grid g = new Grid(5,8);
    assertEquals("Incorrect Grid X dimension", 5, g.sizeX());
    assertEquals("Incorrect Grid Y dimension", 8, g.sizeY());
  }
  
  @Test(timeout=1000) public void grid_initial() {
    Grid g = new Grid(5,8);
    for (int x = 0;  x < 5;  x++) {
      for (int y = 0;  y < 8;  y++) {
        String errMsg = "There should initially be no token at position ("+x+", "+y+")"; 
        assertFalse(errMsg, g.hasToken(x,y));
      }
    }
  }
  
  @Test(timeout=1000) public void grid_tokens_1() {
    // fair warning: the grid data below is transposed, 
    // so the first row represents the first colunm of the grid
    boolean[][] gridData = { {true, true, false, false}, 
                             {true, false, true, false},
                             {false, false, true, true},
                           };
    Grid g = new Grid(3,4);
    for (int x = 0;  x < 3;  x++) {
      for (int y = 0;  y < 4;  y++) {
        if (gridData[x][y]) { g.setToken(x,y); }
        else { g.resetToken(x,y); }
      }
    }
    // now verify
    for (int x = 0;  x < 3;  x++) {
      for (int y = 0;  y < 4;  y++) {
        String errMsg = "Incorrect token value at coordinates ("+x+", "+y+")";
        assertEquals(errMsg, gridData[x][y], g.hasToken(x,y));
      }
    }
  }

  @Test(timeout=1000) public void grid_tokens_2() {
    // fair warning: the grid data below is transposed, 
    // so the first row represents the first colunm of the grid
    boolean[][] gridData = { {true, true, false, false}, 
                             {true, false, true, false},
                             {false, false, true, true},
                           };
    Grid g = new Grid(3,4);
    for (int x = 0;  x < 3;  x++) {
      for (int y = 0;  y < 4;  y++) {
        if (gridData[x][y]) { g.setToken(new Coordinate(x,y)); }
        else { g.resetToken(new Coordinate(x,y)); }
      }
    }
    // now verify
    for (int x = 0;  x < 3;  x++) {
      for (int y = 0;  y < 4;  y++) {
        String errMsg = "Incorrect token value at coordinates ("+x+", "+y+")";
        assertEquals(errMsg, gridData[x][y], g.hasToken(new Coordinate(x,y)));
      }
    }
  }

  @Test(timeout=10000) public void grid_bounds() {
    Grid g = new Grid(1,1);
    try{ 
      g.setToken(-1,0); 
      fail("Out-of-bounds setToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.setToken(1,0); 
      fail("Out-of-bounds setToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.setToken(0,-1); 
      fail("Out-of-bounds setToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.setToken(0,1); 
      fail("Out-of-bounds setToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.resetToken(-1,0); 
      fail("Out-of-bounds resetToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.resetToken(1,0); 
      fail("Out-of-bounds resetToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.resetToken(0,-1); 
      fail("Out-of-bounds resetToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.resetToken(0,1); 
      fail("Out-of-bounds resetToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.hasToken(-1,0); 
      fail("Out-of-bounds hasToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.hasToken(1,0); 
      fail("Out-of-bounds hasToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.hasToken(0,-1); 
      fail("Out-of-bounds hasToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
    try{ 
      g.hasToken(0,1); 
      fail("Out-of-bounds hasToken should be signalled with an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
  }

  // ******** Signalable ********
  
  private abstract class SignalableTest<T> implements Signalable<T> {}
  
  // ******** Drone ********
  
  @Test(timeout=10000) public void drone_constructor_1() {
    Grid g = new Grid(10,10);
    Drone d = new Drone(4,5,g);
    assertEquals("Incorrect initial drone X position", 4, d.getPosition().getX());
    assertEquals("Incorrect initial drone Y position", 5, d.getPosition().getY());
  }
  
  @Test(timeout=10000) public void drone_constructor_2() {
    Grid g = new Grid(10,10);
    Drone d = new Drone(new Coordinate(4,5),g);
    assertEquals("Incorrect initial drone X position", 4, d.getPosition().getX());
    assertEquals("Incorrect initial drone Y position", 5, d.getPosition().getY());
  }
  
  private int[][] move_positions = { {3,3}, {3,4}, {4,4}, {4,3}, {3,3}, };
  private Direction[] move_actions = { Direction.NONE, Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };  

  @Test(timeout=10000) public void drone_move() {
    Grid g = new Grid(10,10);
    int[] initPos = move_positions[0];
    Drone d = new Drone(initPos[0],initPos[1],g);
    String sequence = "("+initPos[0]+", "+initPos[1]+")";
    for (int i = 0;  i < move_actions.length;  i++) {
      Direction dir = move_actions[i];
      int[] pos = move_positions[i];
      sequence += "+" + dir;
      d.move(dir);
      assertEquals("Incorrect X position after " + sequence, pos[0], d.getPosition().getX()); 
      assertEquals("Incorrect Y position after " + sequence, pos[1], d.getPosition().getY()); 
    }
  }
    
  @Test(timeout=10000) public void drone_bounds() {
    Grid g = new Grid(1,1);
    for (int i = 0;  i < 4;  i++) {
      Drone d = new Drone(0,0,g);
      Direction dir = directions[i];
      try{
        d.move(dir);
        fail("Drone should throw exception if "+dir+" goes out-of-bounds");
      } catch (GridCoordsOutOfBoundsException e) {}
      assertEquals("Drone should not move if "+dir+" goes out-of-bounds", 0, d.getPosition().getX());
      assertEquals("Drone should not move if "+dir+" goes out-of-bounds", 0, d.getPosition().getY());
    }
  }

  private class TestListener implements Signalable<Drone> {
    private Drone d = null;
    public Drone get() { return d; }
    @Override public void signal(Drone d) { this.d = d; } 
  }
  
  private Drone setup_drone() {
    Grid g = new Grid(1,1);
    Drone d = new Drone(0,0,g);
    g.setToken(0,0);
    return d;
  }
  
  @Test(timeout=10000) public void drone_listener_1() {
    TestListener tl = new TestListener();
    Drone d = setup_drone();
    d.addTokenListener(tl);
    d.move(Direction.NONE);  // try to trigger the token signal
    assertSame("Token listener was not signalled", d, tl.get()); 
  }
  
  @Test(timeout=10000) public void drone_listener_2() {
    TestListener[] listeners = new TestListener[5];
    Drone d = setup_drone();
    for (int i = 0;  i < listeners.length;  i++) {
      listeners[i] = new TestListener();
      d.addTokenListener(listeners[i]);
    }
    d.move(Direction.NONE);  // try to trigger the token signal
    for (int i = 0;  i < listeners.length;  i++) {
      assertSame("Token listener "+i+" was not signalled", d, listeners[i].get());
    }
  }

  @Test(timeout=10000) public void drone_listener_3() {
    TestListener[] listeners = new TestListener[5];
    boolean[] enabled = {true, false, true, false, true};
    Drone d = setup_drone();
    for (int i = 0;  i < listeners.length;  i++) {
      listeners[i] = new TestListener();
      d.addTokenListener(listeners[i]);
    }
    for (int i = 0;  i < listeners.length;  i++) {
      if (!enabled[i]) d.removeTokenListener(listeners[i]);
    }
    d.move(Direction.NONE);  // try to trigger the token signal
    for (int i = 0;  i < listeners.length;  i++) {
      if (enabled[i]) 
        assertSame("Token listener "+i+" was not signalled", d, listeners[i].get());
      else
        assertNull("Token listener "+i+" was removed and should have been null", listeners[i].get());
    }
  }
  
  
  
  // ******** Controller ********
  
  @Test(timeout=1000) public void controller_inherits() {
    Object o = new Controller();
    assertTrue("A controller should be a Signalable", o instanceof Signalable);
  }

  @Test(timeout=1000) public void controller_signal() {
    Signalable<Drone> s = new Controller();
    Grid g = new Grid(1,1);
    Drone d = new Drone(0,0,g);
    s.signal(d);
  }

  @Test(timeout=1000) public void controller_initial() {
    Controller ctrl = new Controller();
    assertEquals("Controller should have an initial count of zero", 0, ctrl.count());
    assertEquals("Controller should initially have no saved positions", 0, ctrl.getCoords().size());
  }

  
  @Test(timeout=1000) public void controller_positions() {
    Coordinate[] pList = {new Coordinate(0,0), new Coordinate(2,5), new Coordinate(5,7), new Coordinate(2,6)};
    Controller ctrl = new Controller();
    Grid g = new Grid(10,10);
    for (Coordinate c : pList) { 
      Drone d = new Drone(c, g);
      ctrl.signal(d);
    }
    assertEquals("Controller has wrong count after several signals", pList.length, ctrl.count());    
    for (Coordinate c : pList) {
      boolean found = false;
      for (Coordinate cc : ctrl.getCoords()) {
        if (cc.getX() == c.getX() && cc.getY() == c.getY()) { found = true; }
      }
      assertTrue("Controller coordinate " + c + " missing after signal", found);
    }
  }
  
  @Test(timeout=10000) public void controller_sim() {
    Coordinate[] pList = {new Coordinate(0,0), new Coordinate(2,5), new Coordinate(5,7), new Coordinate(2,6)};
    Controller ctrl = new Controller();
    Grid g = new Grid(10,12);    
    for (Coordinate c : pList) { g.setToken(c); }
    Drone d = new Drone(4,6,g);
    d.addTokenListener(ctrl);
    d.search();
    assertEquals("Controller has wrong count after search", pList.length, ctrl.count());    
    for (Coordinate c : pList) {
      boolean found = false;
      for (Coordinate cc : ctrl.getCoords()) {
        if (cc.getX() == c.getX() && cc.getY() == c.getY()) { found = true; }
      }
      assertTrue("Controller coordinate " + c + " missing after search", found);
    }
  }
  
}