import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5control {

  private static Object[][][] data = {
	  {{5, "Controller class grader"},
		  {"controller_inherits", 0.5, "inheritance"},
		  {"controller_signal", 0.5, "copable of receiving signals"},
		  {"controller_initial", 1.0, "initial conditions"},
		  {"controller_count_.*", 1.0, "coordinate count"},
		  {"controller_positions_.*", 1.0, "coordinate positions"},
		  {"controller_dups_.*", 1.0, "duplicate coordinates"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5control().getClass(), data, args);
  }

  @Test(timeout=1000) public void controller_inherits() throws Exception { 
      Object o = new Controller();
      assertTrue("Controller should be a Signalable", o instanceof Signalable);
  }

  @Test(timeout=1000) public void controller_signal() throws Exception { 
      Signalable<Drone> s = new Controller();
      Grid g = new Grid(1,1);
      Drone d = new Drone(0,0,g);
      s.signal(d);
  }

  @Test(timeout=1000) public void controller_initial() throws Exception { 
      Controller ctrl = new Controller();
      assertEquals("Controller should have an initial count of zero", 0, ctrl.count());
      assertEquals("Controller should initially have no saved positions", 0, ctrl.getCoords().size());
  }

  private void signal(Controller ctrl, int[] coord) {
    Grid g = new Grid(10, 10);
    Drone d = new Drone(coord[0], coord[1], g);
    ctrl.signal(d);
  }

  private int[][] coordList0 = { {5,5}, };
  private int[][] coordList1 = { {0,0}, {1,2}, {3,4}, {5,6}, };
  private int[][] coordList2 = { {1,8}, {1,2}, {3,4}, {5,6}, {3,5}, {2,5} };
  private int[][] coordList3 = { {1,8}, {2,1}, {3,4}, {5,6}, {5,9}, {2,5} };

  private void check_count(int[][] coordList) {
    Controller ctrl = new Controller();
    for (int[] pair : coordList) { signal(ctrl, pair); }
    assertEquals("Controller has wrong count after several signals", coordList.length, ctrl.count());
  }

  @Test(timeout=1000) public void controller_count_0() throws Exception { check_count(coordList0); }
  @Test(timeout=1000) public void controller_count_1() throws Exception { check_count(coordList1); }
  @Test(timeout=1000) public void controller_count_2() throws Exception { check_count(coordList2); }
  @Test(timeout=1000) public void controller_count_3() throws Exception { check_count(coordList3); }

  private void check_pos(int[][] coordList) {
    Controller ctrl = new Controller();
    for (int[] pair : coordList) { signal(ctrl, pair); }
    assertEquals("Controller has superfluous coordinates in list", coordList.length, ctrl.getCoords().size());
    for (int[] pair : coordList) {
      boolean found = false;
      for (Coordinate cc : ctrl.getCoords()) {
        if (cc.getX() == pair[0] && cc.getY() == pair[1]) { found = true; }
      }
      String errMsg = String.format("Controller coordinate (%d, %d) missing after signal", pair[0], pair[1]);
      assertTrue(errMsg, found);
    }
  }

  @Test(timeout=1000) public void controller_positions_0() throws Exception { check_pos(coordList0); }
  @Test(timeout=1000) public void controller_positions_1() throws Exception { check_pos(coordList1); }
  @Test(timeout=1000) public void controller_positions_2() throws Exception { check_pos(coordList2); }
  @Test(timeout=1000) public void controller_positions_3() throws Exception { check_pos(coordList3); }

  private void check_dup(int[][] coordList) {
    Controller ctrl = new Controller();
    for (int[] pair : coordList) { signal(ctrl, pair); }
    for (int[] pair : coordList) { signal(ctrl, pair); }
    assertEquals("Controller has wrong count after several duplicate signals", coordList.length, ctrl.count());
    assertEquals("Controller has superfluous coordinates in list", coordList.length, ctrl.getCoords().size());
    for (int[] pair : coordList) {
      boolean found = false;
      for (Coordinate cc : ctrl.getCoords()) {
        if (cc.getX() == pair[0] && cc.getY() == pair[1]) { found = true; }
      }
      String errMsg = String.format("Controller coordinate (%d, %d) missing after signal", pair[0], pair[1]);
      assertTrue(errMsg, found);
    }
  }

  @Test(timeout=1000) public void controller_dups_0() throws Exception { check_dup(coordList0); }
  @Test(timeout=1000) public void controller_dups_1() throws Exception { check_dup(coordList1); }
  @Test(timeout=1000) public void controller_dups_2() throws Exception { check_dup(coordList2); }
  @Test(timeout=1000) public void controller_dups_3() throws Exception { check_dup(coordList3); }
}
