import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5drone {

  private static Object[][][] data = {
	  {{15, "Drone class grader"},
		  {"drone_constructor_.*", 1.0, "constructor"},
		  {"drone_move_.*", 2.0, "move()"},
		  {"drone_bounds_.*", 2.0, "boundary"},
		  {"drone_listener_one_.*", 2.0, "single listener"},
		  {"drone_listener_multi_.*", 2.0, "multi listener"},
		  {"drone_listener_rem_.*", 1.0, "removing listeners"},
		  {"drone_sim_.*", 3.0, "simulation"},
		  {"drone_teleport_.*", 1.0, "no teleportation"},
		  {"drone_allsearch_.*", 1.0, "all spots searched"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5drone().getClass(), data, args);
  }

  @Test(timeout=1000) public void drone_constructor_xy() throws Exception { 
    Grid g = new Grid(2,2);
    Drone d = new Drone(5,8,g);
    assertEquals("Incorrect initial drone X position", 5, d.getPosition().getX());
    assertEquals("Incorrect initial drone Y position", 8, d.getPosition().getY());
  }

  @Test(timeout=1000) public void drone_constructor_coords() throws Exception { 
    Grid g = new Grid(2,2);
    Drone d = new Drone(new Coordinate(5,8),g);
    assertEquals("Incorrect initial drone X position", 5, d.getPosition().getX());
    assertEquals("Incorrect initial drone Y position", 8, d.getPosition().getY());
  }


  public P5drone() {
    delta.put(Direction.UP, new int[]{0,1});
    delta.put(Direction.DOWN, new int[]{0,-1});
    delta.put(Direction.LEFT, new int[]{-1,0});
    delta.put(Direction.RIGHT, new int[]{1,0});
    delta.put(Direction.NONE, new int[]{0,0});
  }

  private Map<Direction, int[]> delta = new HashMap<Direction, int[]>();

  private Direction[][] move_seq = {
    {Direction.UP},
    {Direction.DOWN},
    {Direction.LEFT},
    {Direction.RIGHT},
    {Direction.NONE},
    {Direction.UP, Direction.RIGHT},
    {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.NONE},
    {Direction.UP, Direction.RIGHT, Direction.UP, Direction.LEFT},
    {Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.RIGHT, Direction.UP},
    {Direction.NONE, Direction.NONE, Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN},
  };

  private int[] getCoord(int[] start, Direction[] seq, int k) throws Exception {
    int[] pos = {start[0], start[1]};
    if (k < 0 || k > seq.length) k = seq.length;
    for (int i = 0;  i < k;  i++) {
      Direction d = seq[i];
      int[] dpos = delta.get(d);
      pos[0] += dpos[0];
      pos[1] += dpos[1];
    }
    return pos;
  }

  private void test_move(int x, int y, int seq_num) throws Exception {
    GradeHelper.setCapture();
    Grid g = new Grid(20,20);
    Drone drone = new Drone(x, y, g);
    Direction[] seq = move_seq[seq_num];
    int[] start = {x, y};

    String seqStr = String.format("Sequence (%d, %d)", x, y);

    for (int i = 0;  i < seq.length;  i++) {
      Direction d = seq[i];
      drone.move(d);
      seqStr += "+" + d.name();
      int[] current = getCoord(start, seq, i+1);
      String errMsg = seqStr + " incorrect ";
      assertEquals(errMsg + "X", current[0], drone.getPosition().getX());
      assertEquals(errMsg + "Y", current[1], drone.getPosition().getY());
    }
    GradeHelper.unsetCapture();
  }

  @Test(timeout=1000) public void drone_move_one_00() throws Exception { test_move(10,10,0); }
  @Test(timeout=1000) public void drone_move_one_01() throws Exception { test_move(10,10,1); }
  @Test(timeout=1000) public void drone_move_one_02() throws Exception { test_move(10,10,2); }
  @Test(timeout=1000) public void drone_move_one_03() throws Exception { test_move(10,10,3); }
  @Test(timeout=1000) public void drone_move_one_04() throws Exception { test_move(10,10,4); }
  @Test(timeout=1000) public void drone_move_one_10() throws Exception { test_move(4,15,0); }
  @Test(timeout=1000) public void drone_move_one_11() throws Exception { test_move(4,15,1); }
  @Test(timeout=1000) public void drone_move_one_12() throws Exception { test_move(4,15,2); }
  @Test(timeout=1000) public void drone_move_one_13() throws Exception { test_move(4,15,3); }
  @Test(timeout=1000) public void drone_move_one_14() throws Exception { test_move(4,15,4); }
  @Test(timeout=1000) public void drone_move_one_20() throws Exception { test_move(14,5,0); }
  @Test(timeout=1000) public void drone_move_one_21() throws Exception { test_move(14,5,1); }
  @Test(timeout=1000) public void drone_move_one_22() throws Exception { test_move(14,5,2); }
  @Test(timeout=1000) public void drone_move_one_23() throws Exception { test_move(14,5,3); }
  @Test(timeout=1000) public void drone_move_one_24() throws Exception { test_move(14,5,4); }
  @Test(timeout=1000) public void drone_move_one_30() throws Exception { test_move(7,8,0); }
  @Test(timeout=1000) public void drone_move_one_31() throws Exception { test_move(7,8,1); }
  @Test(timeout=1000) public void drone_move_one_32() throws Exception { test_move(7,8,2); }
  @Test(timeout=1000) public void drone_move_one_33() throws Exception { test_move(7,8,3); }
  @Test(timeout=1000) public void drone_move_one_34() throws Exception { test_move(7,8,4); }

  @Test(timeout=1000) public void drone_move_multi_00() throws Exception { test_move(10,10,5); }
  @Test(timeout=1000) public void drone_move_multi_01() throws Exception { test_move(10,10,6); }
  @Test(timeout=1000) public void drone_move_multi_02() throws Exception { test_move(10,10,7); }
  @Test(timeout=1000) public void drone_move_multi_03() throws Exception { test_move(10,10,8); }
  @Test(timeout=1000) public void drone_move_multi_04() throws Exception { test_move(10,10,9); }
  @Test(timeout=1000) public void drone_move_multi_10() throws Exception { test_move(4,15,5); }
  @Test(timeout=1000) public void drone_move_multi_11() throws Exception { test_move(4,15,6); }
  @Test(timeout=1000) public void drone_move_multi_12() throws Exception { test_move(4,15,7); }
  @Test(timeout=1000) public void drone_move_multi_13() throws Exception { test_move(4,15,8); }
  @Test(timeout=1000) public void drone_move_multi_14() throws Exception { test_move(4,15,9); }
  @Test(timeout=1000) public void drone_move_multi_20() throws Exception { test_move(14,5,5); }
  @Test(timeout=1000) public void drone_move_multi_21() throws Exception { test_move(14,5,6); }
  @Test(timeout=1000) public void drone_move_multi_22() throws Exception { test_move(14,5,7); }
  @Test(timeout=1000) public void drone_move_multi_23() throws Exception { test_move(14,5,8); }
  @Test(timeout=1000) public void drone_move_multi_24() throws Exception { test_move(14,5,9); }
  @Test(timeout=1000) public void drone_move_multi_30() throws Exception { test_move(7,8,5); }
  @Test(timeout=1000) public void drone_move_multi_31() throws Exception { test_move(7,8,6); }
  @Test(timeout=1000) public void drone_move_multi_32() throws Exception { test_move(7,8,7); }
  @Test(timeout=1000) public void drone_move_multi_33() throws Exception { test_move(7,8,8); }
  @Test(timeout=1000) public void drone_move_multi_34() throws Exception { test_move(7,8,9); }

  private class TestListener implements Signalable<Drone> {
    private Drone d = null;
    public Drone get() { return d; }
    @Override public void signal(Drone d) { this.d = d; }
  }

  private void check_listeners(int num, int x, int y, boolean token) throws Exception {
    GradeHelper.setCapture();
    TestListener[] listener = new TestListener[num];
    for (int i = 0;  i < num;  i++) { listener[i] = new TestListener(); }

    Grid g = new Grid(10, 10);
    Drone d = new Drone(x, y, g);
    if (token) g.setToken(x,y);
    for (TestListener lis : listener) { d.addTokenListener(lis); }

    d.move(Direction.NONE);
    if (token) {
      for (int i = 0;  i < num;  i++) { assertSame("Token listener "+i+" was not signalled", d, listener[i].get()); }
    } else {
      for (int i = 0;  i < num;  i++) { assertNull("Token listener "+i+" should not be signalled", listener[i].get()); }
    }
    GradeHelper.unsetCapture();
  }

  @Test(timeout=1000) public void drone_listener_one_hastoken() throws Exception { check_listeners(1, 5, 5, true); }
  @Test(timeout=1000) public void drone_listener_one_notoken() throws Exception { check_listeners(1, 5, 5, false); }
  @Test(timeout=1000) public void drone_listener_multi_hastoken() throws Exception { check_listeners(8, 5, 5, true); }
  @Test(timeout=1000) public void drone_listener_multi_notoken() throws Exception { check_listeners(8, 5, 5, false); }

  private void check_rem_listeners(int num, int x, int y) throws Exception {
    GradeHelper.setCapture();
    TestListener[] listener = new TestListener[num];
    for (int i = 0;  i < num;  i++) { listener[i] = new TestListener(); }

    Grid g = new Grid(10, 10);
    Drone d = new Drone(x, y, g);
    g.setToken(x,y);
    for (TestListener lis : listener) { d.addTokenListener(lis); }
    for (int i = 0;  i < listener.length;  i++) 
    { if (i % 2 == 0) { d.removeTokenListener(listener[i]); } }

    d.move(Direction.NONE);
    for (int i = 0;  i < num;  i++) { 
      if (i % 2 == 0)
        assertNull("Removed token listener "+i+" should not receive signal", listener[i].get());
      else
	assertSame("Token listener "+i+" was not signalled", d, listener[i].get()); 
    }
    GradeHelper.unsetCapture();
  }

  @Test(timeout=1000) public void drone_listener_rem_one() throws Exception { check_rem_listeners(1, 5, 5); }
  @Test(timeout=1000) public void drone_listener_rem_multi() throws Exception { check_rem_listeners(5, 5, 5); }

  private int[][][] coordList = {
    {{5,5}},
    {{1,2}, {4,6}},
    {{0,0}, {9,3}, {2,4}},
    {{0,0}, {0,9}, {9,0}, {9,9}},
    {{8,2}, {3,8}, {5,2}, {7,7}, {1,9}},
  };

  private class TestController implements Signalable<Drone> {
    private int[][] coords;
    private boolean[] found;
    public TestController(int[][] coords) { 
      this.coords = coords;
      this.found = new boolean[coords.length];
    }
    public void signal(Drone d) {
      Coordinate pos = d.getPosition();
      boolean good = false;
      for (int i = 0;  i < coords.length;  i++) {
        if (pos.getX() == coords[i][0] && pos.getY() == coords[i][1]) { 
	  found[i] = true; 
	  good = true;
	  break;
	}
      }
      if (!good) { fail(String.format("bad token reported at (%d, %d)", pos.getX(), pos.getY())); }
    }
    public int[] foundAll() {
      for (int i=0;  i < found.length;  i++) { if (!found[i]) return coords[i]; }
      return null;
    }
  }

  private void run_sim(int x, int y, int i) throws Exception {
    GradeHelper.setCapture();

    Grid g = new Grid(10, 10);
    int[][] coords = coordList[i];
    TestController ctrl = new TestController(coords);
    for (int[] c : coords) { g.setToken(c[0], c[1]); }
    Drone d = new Drone(x, y, g);
    d.addTokenListener(ctrl);
    
    d.search();
    int[] missing = ctrl.foundAll();
    if (missing != null) {
      fail(String.format("Controller coordinate (%d, %d) missing after search", missing[0], missing[1]));
    }

    GradeHelper.unsetCapture();
  }
	 
  @Test(timeout=1000) public void drone_sim_00() throws Exception { run_sim(0, 0, 0); }
  @Test(timeout=1000) public void drone_sim_01() throws Exception { run_sim(0, 0, 1); }
  @Test(timeout=1000) public void drone_sim_02() throws Exception { run_sim(0, 0, 2); }
  @Test(timeout=1000) public void drone_sim_03() throws Exception { run_sim(0, 0, 3); }
  @Test(timeout=1000) public void drone_sim_04() throws Exception { run_sim(0, 0, 4); }
  @Test(timeout=1000) public void drone_sim_10() throws Exception { run_sim(5, 5, 0); }
  @Test(timeout=1000) public void drone_sim_11() throws Exception { run_sim(5, 5, 1); }
  @Test(timeout=1000) public void drone_sim_12() throws Exception { run_sim(5, 5, 2); }
  @Test(timeout=1000) public void drone_sim_13() throws Exception { run_sim(5, 5, 3); }
  @Test(timeout=1000) public void drone_sim_14() throws Exception { run_sim(5, 5, 4); }
  @Test(timeout=1000) public void drone_sim_20() throws Exception { run_sim(2, 7, 0); }
  @Test(timeout=1000) public void drone_sim_21() throws Exception { run_sim(2, 7, 1); }
  @Test(timeout=1000) public void drone_sim_22() throws Exception { run_sim(2, 7, 2); }
  @Test(timeout=1000) public void drone_sim_23() throws Exception { run_sim(2, 7, 3); }
  @Test(timeout=1000) public void drone_sim_24() throws Exception { run_sim(2, 7, 4); }

  private class SpotDrone extends Drone {
    private boolean[][] checked;
    public SpotDrone(int x, int y, Grid g) { 
      super(x, y, g);
      this.checked = new boolean[g.sizeX()][g.sizeY()];
    }
    @Override public void move(Direction d) throws GridCoordsOutOfBoundsException {
      super.move(d);
      Coordinate pos = getPosition();
      checked[pos.getX()][pos.getY()] = true;
    }
    public boolean allChecked() {
      for (boolean[] line : checked) for (boolean b : line) if (!b) return false;
      return true;
    }
  }

  private void check_allspots(int x, int y) throws Exception {
    GradeHelper.setCapture();
    Grid g = new Grid(10, 10);
    SpotDrone d = new SpotDrone(x, y, g);
    d.search();
    assertTrue("not all spots checked by search()", d.allChecked());

    GradeHelper.unsetCapture();
  }

  @Test(timeout=1000) public void drone_allsearch_00() throws Exception { check_allspots(0,0); }
  @Test(timeout=1000) public void drone_allsearch_55() throws Exception { check_allspots(5,5); }

  private class RelDrone extends Drone {
    private int lastX, lastY;
    public RelDrone(int x, int y, Grid g) { 
      super(x, y, g);
      this.lastX = x;  this.lastY = y;
    }
    @Override public void move(Direction d) throws GridCoordsOutOfBoundsException {
      super.move(d);
      Coordinate pos = getPosition();
      int netChange = Math.abs(pos.getX() - lastX) + Math.abs(pos.getY() - lastY);
      String errMsg = String.format("Too large of a jump after move(), (%d, %d) to (%d, %d)", lastX, lastY, pos.getX(), pos.getY());
      assertTrue(errMsg, netChange <= 1);
      lastX = pos.getX();
      lastY = pos.getY();
    }
  }

  private void check_teleport(int x, int y) throws Exception {
    GradeHelper.setCapture();
    Grid g = new Grid(10, 10);
    RelDrone d = new RelDrone(x, y, g);
    d.search();
    GradeHelper.unsetCapture();
  }

  @Test(timeout=1000) public void drone_teleport_00() throws Exception { check_teleport(0,0); }
  @Test(timeout=1000) public void drone_teleport_55() throws Exception { check_teleport(5,5); }

  private void check_bounds(Direction dir) throws Exception {
    Grid g = new Grid(1,1);
    Drone d = new Drone(0,0,g);
    try{
      d.move(dir);
      fail("Drone should throw exception if "+dir.name()+" goes out-of-bounds");
    } catch (GridCoordsOutOfBoundsException e) {}
  }

  @Test(timeout=1000) public void drone_bounds_up_except() throws Exception { check_bounds(Direction.UP); }
  @Test(timeout=1000) public void drone_bounds_down_except() throws Exception { check_bounds(Direction.DOWN); }
  @Test(timeout=1000) public void drone_bounds_left_except() throws Exception { check_bounds(Direction.LEFT); }
  @Test(timeout=1000) public void drone_bounds_right_except() throws Exception { check_bounds(Direction.RIGHT); }

  private void check_remain(Direction dir) throws Exception {
    Grid g = new Grid(1,1);
    Drone d = new Drone(0,0,g);
    try{
      d.move(dir);
    } catch (GridCoordsOutOfBoundsException e) {}
    boolean remain = d.getPosition().getX() == 0 && d.getPosition().getY() == 0;
    assertTrue("Drone should not move if "+dir.name()+" goes out-of-bounds", remain);
  }

  @Test(timeout=1000) public void drone_bounds_up_remain() throws Exception { check_remain(Direction.UP); }
  @Test(timeout=1000) public void drone_bounds_down_remain() throws Exception { check_remain(Direction.DOWN); }
  @Test(timeout=1000) public void drone_bounds_left_remain() throws Exception { check_remain(Direction.LEFT); }
  @Test(timeout=1000) public void drone_bounds_right_remain() throws Exception { check_remain(Direction.RIGHT); }

}
