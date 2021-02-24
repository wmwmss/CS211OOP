import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5grid {

  private static Object[][][] data = {
	  {{10, "Grid class grader"},
		  {"grid_exists", 1.0, "existence"},
		  {"grid_size_.*", 1.0, "dimensions and size getters"},
		  {"grid_initial", 1.0, "initial token locations"},
		  {"grid_tokens_xy.*", 3.0, "token position (X,Y version of set/reset/has)"},
		  {"grid_tokens_coord.*", 3.0, "token position (Coordinate version of set/reset/has)"},
		  {"grid_bounds_.*", 1.0, "boundary exceptions"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5grid().getClass(), data, args);
  }

  @Test(timeout=10000) public void grid_exists() throws Exception { new Grid(1,1); }

  private void check_size(int x, int y) throws Exception {
    Grid g = new Grid(x,y); 
    assertEquals("Incorrect Grid X dimension", x, g.sizeX());
    assertEquals("Incorrect Grid Y dimension", y, g.sizeY());
  }

  @Test(timeout=10000) public void grid_size_0() throws Exception { check_size(1,1); }
  @Test(timeout=10000) public void grid_size_1() throws Exception { check_size(10,10); }
  @Test(timeout=10000) public void grid_size_2() throws Exception { check_size(2,8); }
  @Test(timeout=10000) public void grid_size_3() throws Exception { check_size(9,4); }

  @Test(timeout=10000) public void grid_initial() throws Exception { 
      int xd = 6, yd = 11;
      Grid g = new Grid(xd, yd);
      for (int x = 0;  x < xd ; x++) {
        for (int y = 0;  y < yd;  y++) {
	  String errMsg = String.format("There should initially be no token at position (%d, %d)", x, y);
	  assertFalse(errMsg, g.hasToken(x,y));
	}
      }
  }

  private boolean[][] gridData0 = { 
    { true, false, false, },
    {false,  true, false, },
    {false, false,  true, },
  };

  private boolean[][] gridData1 = { 
    {true, false, true, false, true, false, },
    {false, true, false, true, false, true, },
    {true, false, true, false, true, false, },
  };

  private boolean[][] gridData2 = { 
    {false, false, false, },
    {false, false,  true, },
    {false,  true, false, },
    {false,  true,  true, },
    { true, false, false, },
    { true, false,  true, },
    { true,  true, false, },
    { true,  true,  true, },
  };

  private void setGridDataXY(boolean[][] gridData, boolean withReset) throws Exception {
    int sx = gridData.length, sy = gridData[0].length;
    Grid g = new Grid(sx, sy);
    for (int x = 0;  x < sx;  x++) {
      for (int y = 0;  y < sy;  y++) {
        if (gridData[x][y]) { g.setToken(x, y); }
	else if (withReset) { g.setToken(x,y);  g.resetToken(x, y); }
      }
    }
    for (int x = 0;  x < sx;  x++) {
      for (int y = 0;  y < sy;  y++) {
        String errMsg = String.format("Incorrect token value at (%d, %d)", x, y);
	assertEquals(errMsg, gridData[x][y], g.hasToken(x, y));
      }
    }
  }

  @Test(timeout=10000) public void grid_tokens_xy_0() throws Exception { setGridDataXY(gridData0, false); }
  @Test(timeout=10000) public void grid_tokens_xy_1() throws Exception { setGridDataXY(gridData1, false); }
  @Test(timeout=10000) public void grid_tokens_xy_2() throws Exception { setGridDataXY(gridData2, false); }
  @Test(timeout=10000) public void grid_tokens_xy_0r() throws Exception { setGridDataXY(gridData0, true); }
  @Test(timeout=10000) public void grid_tokens_xy_1r() throws Exception { setGridDataXY(gridData1, true); }
  @Test(timeout=10000) public void grid_tokens_xy_2r() throws Exception { setGridDataXY(gridData2, true); }

  private void setGridDataCoord(boolean[][] gridData, boolean withReset) throws Exception {
    int sx = gridData.length, sy = gridData[0].length;
    Grid g = new Grid(sx, sy);
    for (int x = 0;  x < sx;  x++) {
      for (int y = 0;  y < sy;  y++) {
        if (gridData[x][y]) { g.setToken(new Coordinate(x, y)); }
	else if (withReset) { g.setToken(new Coordinate(x, y));  g.resetToken(new Coordinate(x, y)); }
      }
    }
    for (int x = 0;  x < sx;  x++) {
      for (int y = 0;  y < sy;  y++) {
        String errMsg = String.format("Incorrect token value at (%d, %d)", x, y);
	assertEquals(errMsg, gridData[x][y], g.hasToken(new Coordinate(x, y)));
      }
    }
  }

  @Test(timeout=10000) public void grid_tokens_coord_0() throws Exception { setGridDataCoord(gridData0, false); }
  @Test(timeout=10000) public void grid_tokens_coord_1() throws Exception { setGridDataCoord(gridData1, false); }
  @Test(timeout=10000) public void grid_tokens_coord_2() throws Exception { setGridDataCoord(gridData2, false); }
  @Test(timeout=10000) public void grid_tokens_coord_0r() throws Exception { setGridDataCoord(gridData0, true); }
  @Test(timeout=10000) public void grid_tokens_coord_1r() throws Exception { setGridDataCoord(gridData1, true); }
  @Test(timeout=10000) public void grid_tokens_coord_2r() throws Exception { setGridDataCoord(gridData2, true); }

  private interface GridFunc { void call(Grid g, int x, int y); };
  private int[][] delta = { {-1,0}, {1,0}, {0,-1}, {0,1} };

  private void check_bounds(int i, GridFunc f, String funcName) throws Exception {
    Grid g = new Grid(5,5);
    int x = delta[i][0], y = delta[i][1];
    if (x > 0) x += 4;
    if (y > 0) y += 4;
    try {
      f.call(g, x, y);
      fail("Out-of-bounds " + funcName + " should produce an exception");
    } catch (GridCoordsOutOfBoundsException e) {}
  }

  @Test(timeout=10000) public void grid_bounds_set_0() throws Exception 
  { check_bounds(0, (g,x,y)->{g.setToken(x,y);}, "setToken"); }
  @Test(timeout=10000) public void grid_bounds_set_1() throws Exception 
  { check_bounds(1, (g,x,y)->{g.setToken(x,y);}, "setToken"); }
  @Test(timeout=10000) public void grid_bounds_set_2() throws Exception 
  { check_bounds(2, (g,x,y)->{g.setToken(x,y);}, "setToken"); }
  @Test(timeout=10000) public void grid_bounds_set_3() throws Exception 
  { check_bounds(3, (g,x,y)->{g.setToken(x,y);}, "setToken"); }

  @Test(timeout=10000) public void grid_bounds_reset_0() throws Exception 
  { check_bounds(0, (g,x,y)->{g.resetToken(x,y);}, "resetToken"); }
  @Test(timeout=10000) public void grid_bounds_reset_1() throws Exception 
  { check_bounds(1, (g,x,y)->{g.resetToken(x,y);}, "resetToken"); }
  @Test(timeout=10000) public void grid_bounds_reset_2() throws Exception 
  { check_bounds(2, (g,x,y)->{g.resetToken(x,y);}, "resetToken"); }
  @Test(timeout=10000) public void grid_bounds_reset_3() throws Exception 
  { check_bounds(3, (g,x,y)->{g.resetToken(x,y);}, "resetToken"); }

  @Test(timeout=10000) public void grid_bounds_has_0() throws Exception 
  { check_bounds(0, (g,x,y)->{g.hasToken(x,y);}, "hasToken"); }
  @Test(timeout=10000) public void grid_bounds_has_1() throws Exception 
  { check_bounds(1, (g,x,y)->{g.hasToken(x,y);}, "hasToken"); }
  @Test(timeout=10000) public void grid_bounds_has_2() throws Exception 
  { check_bounds(2, (g,x,y)->{g.hasToken(x,y);}, "hasToken"); }
  @Test(timeout=10000) public void grid_bounds_has_3() throws Exception 
  { check_bounds(3, (g,x,y)->{g.hasToken(x,y);}, "hasToken"); }

}
