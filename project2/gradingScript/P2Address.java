import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P2Address {

  private static Object[][][] data = {
	  {{10, "Address class grader"},
		  {"test_constructor_def", 1.0, "default constructor"},
		  {"test_constructor_full", 1.0, "full constructor"},
		  {"test_getset_street_.*", 2.0, "get/set streetNumber"},
		  {"test_getset_city_.*", 2.0, "get/set city"},
		  {"test_getset_state_.*", 2.0, "get/set state"},
		  {"test_getset_zip_.*", 2.0, "get/set zipCode"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P2Address().getClass(), data, args);
  }



  @Test(timeout=1000) public void test_constructor_def() { Address a = new Address(); }
  @Test(timeout=1000) public void test_constructor_full() { 
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345); 
    assertEquals("badly initialized streetNumber", "123 Main Street", a.getStreetNo());
    assertEquals("badly initialized city", "Anytown", a.getCity());
    assertEquals("badly initialized state", "VA", a.getState());
    assertEquals("badly initialized zipCode", 12345, a.getZipCode());
  }

  @Test(timeout=1000) public void test_getset_street_1() { 
    Address a = new Address(); 
    String value = "test";
    a.setStreetNo(value);
    assertEquals("incorrect streetNumber after set", value, a.getStreetNo());
  }
  @Test(timeout=1000) public void test_getset_street_2() { 
    Address a = new Address(); 
    String value = "7 Candy Cane Lane";
    a.setStreetNo(value);
    assertEquals("incorrect streetNumber after set", value, a.getStreetNo());
  }
  @Test(timeout=1000) public void test_getset_city_1() { 
    Address a = new Address(); 
    String value = "test";
    a.setCity(value);
    assertEquals("incorrect city after set", value, a.getCity());
  }
  @Test(timeout=1000) public void test_getset_city_2() { 
    Address a = new Address(); 
    String value = "Zootopia";
    a.setCity(value);
    assertEquals("incorrect city after set", value, a.getCity());
  }
  @Test(timeout=1000) public void test_getset_state_1() { 
    Address a = new Address(); 
    String value = "test";
    a.setState(value);
    assertEquals("incorrect state after set", value, a.getState());
  }
  @Test(timeout=1000) public void test_getset_state_2() { 
    Address a = new Address(); 
    String value = "Puerto Rico";
    a.setState(value);
    assertEquals("incorrect state after set", value, a.getState());
  }
  @Test(timeout=1000) public void test_getset_zip_1() { 
    Address a = new Address(); 
    int value = 11111;
    a.setZipCode(value);
    assertEquals("incorrect zipCode after set", value, a.getZipCode());
  }
  @Test(timeout=1000) public void test_getset_zip_2() { 
    Address a = new Address(); 
    int value = 19191;
    a.setZipCode(value);
    assertEquals("incorrect zipCode after set", value, a.getZipCode());
  }

}

