import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5direct {

  private static Object[][][] data = {
	  {{5, "Direction class grader"},
		  {"direction_exists", 1.0, "direction objects exist"},
		  {"direction_delta_.*", 2.0, "direction delta()"},
		  {"direction_coord_plus_.*", 2.0, "Coordinate.plus(Direction)"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5direct().getClass(), data, args);
  }

  private Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.NONE};
  private int[][] dcoords = { {0,1}, {0,-1}, {-1,0}, {1,0}, {0,0}};

  @Test(timeout=1000) public void direction_exists() throws Exception {}

  private void check_delta(int i) throws Exception {
    Direction d = directions[i];
    int[] dc = dcoords[i];
    Coordinate c = d.delta();
    assertEquals("Direction " + d.name() + " has incorrect delta X", dc[0], c.getX());
    assertEquals("Direction " + d.name() + " has incorrect delta Y", dc[1], c.getY());
  }

  @Test(timeout=1000) public void direction_delta_up() throws Exception { check_delta(0); }
  @Test(timeout=1000) public void direction_delta_down() throws Exception { check_delta(1); }
  @Test(timeout=1000) public void direction_delta_left() throws Exception { check_delta(2); }
  @Test(timeout=1000) public void direction_delta_right() throws Exception { check_delta(3); }
  @Test(timeout=1000) public void direction_delta_none() throws Exception { check_delta(4); }

  private void check_plus(int i, int x, int y) throws Exception {
    Direction d = directions[i];
    int[] dc = dcoords[i];
    Coordinate c = new Coordinate(x, y).plus(d);
    int nx = x+dc[0], ny = y+dc[1];
    String errMsg = String.format("incorrect result of (%d, %d).plus(%s), component ", x, y, d.name());
    assertEquals(errMsg + "X", nx, c.getX());
    assertEquals(errMsg + "Y", ny, c.getY());
  }

  @Test(timeout=1000) public void direction_coord_plus_up0() throws Exception { check_plus(0,0,0); }
  @Test(timeout=1000) public void direction_coord_plus_up1() throws Exception { check_plus(0,23,54); }
  @Test(timeout=1000) public void direction_coord_plus_up2() throws Exception { check_plus(0,-23,54); }
  @Test(timeout=1000) public void direction_coord_plus_up3() throws Exception { check_plus(0,23,-54); }
  @Test(timeout=1000) public void direction_coord_plus_down0() throws Exception { check_plus(1,0,0); }
  @Test(timeout=1000) public void direction_coord_plus_down1() throws Exception { check_plus(1,23,54); }
  @Test(timeout=1000) public void direction_coord_plus_down2() throws Exception { check_plus(1,-23,54); }
  @Test(timeout=1000) public void direction_coord_plus_down3() throws Exception { check_plus(1,23,-54); }
  @Test(timeout=1000) public void direction_coord_plus_left0() throws Exception { check_plus(2,0,0); }
  @Test(timeout=1000) public void direction_coord_plus_left1() throws Exception { check_plus(2,23,54); }
  @Test(timeout=1000) public void direction_coord_plus_left2() throws Exception { check_plus(2,-23,54); }
  @Test(timeout=1000) public void direction_coord_plus_left3() throws Exception { check_plus(2,23,-54); }
  @Test(timeout=1000) public void direction_coord_plus_right0() throws Exception { check_plus(3,0,0); }
  @Test(timeout=1000) public void direction_coord_plus_right1() throws Exception { check_plus(3,23,54); }
  @Test(timeout=1000) public void direction_coord_plus_right2() throws Exception { check_plus(3,-23,54); }
  @Test(timeout=1000) public void direction_coord_plus_right3() throws Exception { check_plus(3,23,-54); }
  @Test(timeout=1000) public void direction_coord_plus_none0() throws Exception { check_plus(4,0,0); }
  @Test(timeout=1000) public void direction_coord_plus_none1() throws Exception { check_plus(4,23,54); }
  @Test(timeout=1000) public void direction_coord_plus_none2() throws Exception { check_plus(4,-23,54); }
  @Test(timeout=1000) public void direction_coord_plus_none3() throws Exception { check_plus(4,23,-54); }


}
