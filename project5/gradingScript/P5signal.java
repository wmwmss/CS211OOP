import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P5signal {

  private static Object[][][] data = {
	  {{5, "Signalable<T> class grader"},
		  {"signalable_exists", 5.0, "existence"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P5signal().getClass(), data, args);
  }

  private class SignalableTest<T> implements Signalable<T> {
    public void signal(T message) {}
  }

  @Test(timeout=10000) public void signalable_exists() throws Exception {
    Signalable<Integer> s = new SignalableTest<Integer>();
    s.signal(null);
  }

}
