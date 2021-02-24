import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P2Person {

  private static Object[][][] data = {
	  {{10, "Person class grader"},
		  {"test_constructor_def", 1.0, "default constructor"},
		  {"test_constructor_full", 1.0, "full constructor"},
		  {"test_getset_first_.*", 2.0, "get/set firstName"},
		  {"test_getset_last_.*", 2.0, "get/set lastName"},
		  {"test_getset_addr_.*", 2.0, "get/set address"},
		  {"test_tostring_.*", 2.0, "toString"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P2Person().getClass(), data, args);
  }



  @Test(timeout=1000) public void test_constructor_def() { Person p = new Person(); }
  @Test(timeout=1000) public void test_constructor_full() { 
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345);
    Person p = new Person("Ada", "Lovelace", a);
    assertEquals("badly initialized firstName", "Ada", p.getFirstName());
    assertEquals("badly initialized lastName", "Lovelace", p.getLastName());
    assertSame("badly initialized address", a, p.getAddress());
  }

  @Test(timeout=1000) public void test_getset_first_1() { 
    Person p = new Person(); 
    String value = "test";
    p.setFirstName(value);
    assertEquals("incorrect firstName after set", value, p.getFirstName());
  }
  @Test(timeout=1000) public void test_getset_first_2() { 
    Person p = new Person(); 
    String value = "Isaac";
    p.setFirstName(value);
    assertEquals("incorrect firstName after set", value, p.getFirstName());
  }
  @Test(timeout=1000) public void test_getset_last_1() { 
    Person p = new Person(); 
    String value = "test";
    p.setLastName(value);
    assertEquals("incorrect lastName after set", value, p.getLastName());
  }
  @Test(timeout=1000) public void test_getset_last_2() { 
    Person p = new Person(); 
    String value = "Newton";
    p.setLastName(value);
    assertEquals("incorrect lastName after set", value, p.getLastName());
  }
  @Test(timeout=1000) public void test_getset_addr_1() { 
    Person p = new Person(); 
    Address a = new Address();
    p.setAddress(a);
    assertSame("incorrect address after set", a, p.getAddress());
  }
  @Test(timeout=1000) public void test_getset_addr_2() { 
    Person p = new Person(); 
    Address a = new Address("4400 University Dr", "Fairfax", "VA", 22030);
    p.setAddress(a);
    assertSame("incorrect address after set", a, p.getAddress());
  }
  @Test(timeout=1000) public void test_tostring_1() { 
    Person p = new Person("test", "test", new Address("test", "test", "test", 12345)); 
    assertEquals("incorrect toString", "test test test 12345 test test", p.toString());
  }
  @Test(timeout=1000) public void test_tostring_2() { 
    Person p = new Person("Prince", "Charming", new Address("777 Fairytale Ln", "Orlando", "FL", 99999)); 
    assertEquals("incorrect toString", "Prince Charming 777 Fairytale Ln 99999 Orlando FL", p.toString());
  }

}

