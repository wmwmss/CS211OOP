import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5except {

  private static Object[][][] data = {
	  {{5, "GridCoordsOutOfBoundsException class grader"},
		  {"exception_inherits", 2.0, "constructor inheritance"},
		  {"exception_constructor_1", 1.0, "default constructor"},
		  {"exception_constructor_2", 2.0, "message constructor"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5except().getClass(), data, args);
  }

  @Test(timeout=1000) public void exception_inherits() throws Exception { 
      Object o = new GridCoordsOutOfBoundsException();
      assertTrue("GridCoordsOutOfBoundsException should be an IndexOutOfBoundsException", o instanceof IndexOutOfBoundsException);
  }

  @Test(timeout=1000) public void exception_constructor_1() throws Exception { 
      Exception e = new GridCoordsOutOfBoundsException();
  }

  @Test(timeout=1000) public void exception_constructor_2() throws Exception { 
      Exception e = new GridCoordsOutOfBoundsException("error string");
      assertEquals("Incorrect exception message", "error string", e.getMessage());
  }

}
