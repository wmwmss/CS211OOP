import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5coord {

  private static Object[][][] data = {
	  {{5, "Coordinate class grader"},
		  {"coordinate_constructor", 1.0, "constructor exists"},
		  {"coordinate_getxy_.*", 1.0, "X,Y getters"},
		  {"coordinate_plus_xy_.*", 1.0, "plus(int,int)"},
		  {"coordinate_plus_delta_.*", 1.0, "plus(Coordinate)"},
		  {"coordinate_tostring_.*", 1.0, "toString"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5coord().getClass(), data, args);
  }

  @Test(timeout=1000) public void coordinate_constructor() throws Exception { 
      new Coordinate(0,0);
  }

  private int[][] coordinates = { {0,0}, {0,1}, {1,0}, {0,-1}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1},
	  {48,49}, {2, 300}, {-32,43}, {-2,243}, {234,-234}, {34,-24}, {-23,-38} };

  private void check_coord(int i) throws Exception {
      int[] pair = coordinates[i];
      int x = pair[0];
      int y = pair[1];
      Coordinate c = new Coordinate(x,y);
      String errMsg = String.format("Coordinate (%d, %d) incorrect ", x, y);
      assertEquals(errMsg + "X", x, c.getX());
      assertEquals(errMsg + "Y", y, c.getY());
  }

  @Test(timeout=1000) public void coordinate_getxy_0() throws Exception { check_coord(0); }
  @Test(timeout=1000) public void coordinate_getxy_1() throws Exception { check_coord(1); }
  @Test(timeout=1000) public void coordinate_getxy_2() throws Exception { check_coord(2); }
  @Test(timeout=1000) public void coordinate_getxy_3() throws Exception { check_coord(3); }
  @Test(timeout=1000) public void coordinate_getxy_4() throws Exception { check_coord(4); }
  @Test(timeout=1000) public void coordinate_getxy_5() throws Exception { check_coord(5); }
  @Test(timeout=1000) public void coordinate_getxy_6() throws Exception { check_coord(6); }
  @Test(timeout=1000) public void coordinate_getxy_7() throws Exception { check_coord(7); }
  @Test(timeout=1000) public void coordinate_getxy_8() throws Exception { check_coord(8); }
  @Test(timeout=1000) public void coordinate_getxy_9() throws Exception { check_coord(9); }
  @Test(timeout=1000) public void coordinate_getxy_a() throws Exception { check_coord(10); }
  @Test(timeout=1000) public void coordinate_getxy_b() throws Exception { check_coord(11); }
  @Test(timeout=1000) public void coordinate_getxy_c() throws Exception { check_coord(12); }
  @Test(timeout=1000) public void coordinate_getxy_d() throws Exception { check_coord(13); }
  @Test(timeout=1000) public void coordinate_getxy_e() throws Exception { check_coord(14); }
  @Test(timeout=1000) public void coordinate_getxy_f() throws Exception { check_coord(15); }

  private void check_plus_xy(int a, int b) throws Exception {
    int[] pair1 = coordinates[a], pair2 = coordinates[b], pair3 = {pair1[0]+pair2[0], pair1[1]+pair2[1]};
    Coordinate c = new Coordinate(pair1[0], pair1[1]).plus(pair2[0], pair2[1]);

    String errMsg = String.format("Coordinate (%d, %d).plus(%d, %d) incorrect ", pair1[0], pair1[1], pair2[0], pair2[1]);
    assertEquals(errMsg + "X", pair3[0], c.getX());
    assertEquals(errMsg + "Y", pair3[1], c.getY());
  }

  @Test(timeout=1000) public void coordinate_plus_xy_00() throws Exception { check_plus_xy(0,0); }
  @Test(timeout=1000) public void coordinate_plus_xy_12() throws Exception { check_plus_xy(1,2); }
  @Test(timeout=1000) public void coordinate_plus_xy_24() throws Exception { check_plus_xy(2,4); }
  @Test(timeout=1000) public void coordinate_plus_xy_36() throws Exception { check_plus_xy(3,6); }
  @Test(timeout=1000) public void coordinate_plus_xy_48() throws Exception { check_plus_xy(4,8); }
  @Test(timeout=1000) public void coordinate_plus_xy_5a() throws Exception { check_plus_xy(5,10); }
  @Test(timeout=1000) public void coordinate_plus_xy_6c() throws Exception { check_plus_xy(6,12); }
  @Test(timeout=1000) public void coordinate_plus_xy_7e() throws Exception { check_plus_xy(7,14); }
  @Test(timeout=1000) public void coordinate_plus_xy_81() throws Exception { check_plus_xy(8,1); }
  @Test(timeout=1000) public void coordinate_plus_xy_93() throws Exception { check_plus_xy(9,3); }
  @Test(timeout=1000) public void coordinate_plus_xy_a5() throws Exception { check_plus_xy(10,5); }
  @Test(timeout=1000) public void coordinate_plus_xy_b7() throws Exception { check_plus_xy(11,7); }
  @Test(timeout=1000) public void coordinate_plus_xy_c9() throws Exception { check_plus_xy(12,9); }
  @Test(timeout=1000) public void coordinate_plus_xy_db() throws Exception { check_plus_xy(13,11); }
  @Test(timeout=1000) public void coordinate_plus_xy_ec() throws Exception { check_plus_xy(14,13); }
  @Test(timeout=1000) public void coordinate_plus_xy_ff() throws Exception { check_plus_xy(15,15); }

  private void check_plus_delta(int a, int b) throws Exception {
    int[] pair1 = coordinates[a], pair2 = coordinates[b], pair3 = {pair1[0]+pair2[0], pair1[1]+pair2[1]};
    Coordinate c = new Coordinate(pair1[0], pair1[1]).plus(new Coordinate(pair2[0], pair2[1]));

    String errMsg = String.format("Coordinate (%d, %d).plus( (%d, %d) ) incorrect ", pair1[0], pair1[1], pair2[0], pair2[1]);
    assertEquals(errMsg + "X", pair3[0], c.getX());
    assertEquals(errMsg + "Y", pair3[1], c.getY());
  }

  @Test(timeout=1000) public void coordinate_plus_delta_00() throws Exception { check_plus_delta(0,0); }
  @Test(timeout=1000) public void coordinate_plus_delta_12() throws Exception { check_plus_delta(1,2); }
  @Test(timeout=1000) public void coordinate_plus_delta_24() throws Exception { check_plus_delta(2,4); }
  @Test(timeout=1000) public void coordinate_plus_delta_36() throws Exception { check_plus_delta(3,6); }
  @Test(timeout=1000) public void coordinate_plus_delta_48() throws Exception { check_plus_delta(4,8); }
  @Test(timeout=1000) public void coordinate_plus_delta_5a() throws Exception { check_plus_delta(5,10); }
  @Test(timeout=1000) public void coordinate_plus_delta_6c() throws Exception { check_plus_delta(6,12); }
  @Test(timeout=1000) public void coordinate_plus_delta_7e() throws Exception { check_plus_delta(7,14); }
  @Test(timeout=1000) public void coordinate_plus_delta_81() throws Exception { check_plus_delta(8,1); }
  @Test(timeout=1000) public void coordinate_plus_delta_93() throws Exception { check_plus_delta(9,3); }
  @Test(timeout=1000) public void coordinate_plus_delta_a5() throws Exception { check_plus_delta(10,5); }
  @Test(timeout=1000) public void coordinate_plus_delta_b7() throws Exception { check_plus_delta(11,7); }
  @Test(timeout=1000) public void coordinate_plus_delta_c9() throws Exception { check_plus_delta(12,9); }
  @Test(timeout=1000) public void coordinate_plus_delta_db() throws Exception { check_plus_delta(13,11); }
  @Test(timeout=1000) public void coordinate_plus_delta_ec() throws Exception { check_plus_delta(14,13); }
  @Test(timeout=1000) public void coordinate_plus_delta_ff() throws Exception { check_plus_delta(15,15); }

  private void check_tostring(int i) throws Exception {
      int[] pair = coordinates[i];
      int x = pair[0];
      int y = pair[1];
      Coordinate c = new Coordinate(x,y);
      String expected = String.format("(%d, %d)", x, y);
      assertEquals("incorrect toString()", expected, c.toString());
  }

  @Test(timeout=1000) public void coordinate_tostring_0() throws Exception { check_tostring(0); }
  @Test(timeout=1000) public void coordinate_tostring_1() throws Exception { check_tostring(1); }
  @Test(timeout=1000) public void coordinate_tostring_2() throws Exception { check_tostring(2); }
  @Test(timeout=1000) public void coordinate_tostring_3() throws Exception { check_tostring(3); }
  @Test(timeout=1000) public void coordinate_tostring_4() throws Exception { check_tostring(4); }
  @Test(timeout=1000) public void coordinate_tostring_5() throws Exception { check_tostring(5); }
  @Test(timeout=1000) public void coordinate_tostring_6() throws Exception { check_tostring(6); }
  @Test(timeout=1000) public void coordinate_tostring_7() throws Exception { check_tostring(7); }
  @Test(timeout=1000) public void coordinate_tostring_8() throws Exception { check_tostring(8); }
  @Test(timeout=1000) public void coordinate_tostring_9() throws Exception { check_tostring(9); }
  @Test(timeout=1000) public void coordinate_tostring_a() throws Exception { check_tostring(10); }
  @Test(timeout=1000) public void coordinate_tostring_b() throws Exception { check_tostring(11); }
  @Test(timeout=1000) public void coordinate_tostring_c() throws Exception { check_tostring(12); }
  @Test(timeout=1000) public void coordinate_tostring_d() throws Exception { check_tostring(13); }
  @Test(timeout=1000) public void coordinate_tostring_e() throws Exception { check_tostring(14); }
  @Test(timeout=1000) public void coordinate_tostring_f() throws Exception { check_tostring(15); }
}
