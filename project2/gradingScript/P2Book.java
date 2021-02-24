import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P2Book {

  private static Object[][][] data = {
	  {{10, "Book class grader"},
		  {"test_constructor_def", 1.0, "default constructor"},
		  {"test_constructor_full", 1.0, "full constructor"},
		  {"test_getset_bookid_.*", 2.0, "get/set bookID"},
		  {"test_getset_title_.*", 2.0, "get/set title"},
		  {"test_getset_author_.*", 2.0, "get/set author"},
		  {"test_getset_pubyear_.*", 2.0, "get/set publicationYear"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P2Book().getClass(), data, args);
  }



  @Test(timeout=1000) public void test_constructor_def() { Book b = new Book(); }
  @Test(timeout=1000) public void test_constructor_full() { 
    Book b = new Book("B99", "Java for fun and profit", "Snow White", 1997); 
    assertEquals("badly initialized bookID", "B99", b.getBookId());
    assertEquals("badly initialized title", "Java for fun and profit", b.getTitle());
    assertEquals("badly initialized author", "Snow White", b.getAuthor());
    assertEquals("badly initialized publicationYear", 1997, b.getPublicationYear());
  }

  @Test(timeout=1000) public void test_getset_bookid_1() { 
    Book b = new Book(); 
    String value = "test";
    b.setBookId(value);
    assertEquals("incorrect bookID after set", value, b.getBookId());
  }
  @Test(timeout=1000) public void test_getset_bookid_2() { 
    Book b = new Book(); 
    String value = "B3.1415926589";
    b.setBookId(value);
    assertEquals("incorrect bookID after set", value, b.getBookId());
  }
  @Test(timeout=1000) public void test_getset_title_1() { 
    Book b = new Book(); 
    String value = "test";
    b.setTitle(value);
    assertEquals("incorrect title after set", value, b.getTitle());
  }
  @Test(timeout=1000) public void test_getset_title_2() { 
    Book b = new Book(); 
    String value = "Frodo Baggins and the Order of the Phoenix";
    b.setTitle(value);
    assertEquals("incorrect title after set", value, b.getTitle());
  }
  @Test(timeout=1000) public void test_getset_author_1() { 
    Book b = new Book(); 
    String value = "test";
    b.setAuthor(value);
    assertEquals("incorrect author after set", value, b.getAuthor());
  }
  @Test(timeout=1000) public void test_getset_author_2() { 
    Book b = new Book(); 
    String value = "Wonder Woman";
    b.setAuthor(value);
    assertEquals("incorrect author after set", value, b.getAuthor());
  }
  @Test(timeout=1000) public void test_getset_pubyear_1() { 
    Book b = new Book(); 
    int value = 1999;
    b.setPublicationYear(value);
    assertEquals("incorrect publicationYear after set", value, b.getPublicationYear());
  }
  @Test(timeout=1000) public void test_getset_pubyear_2() { 
    Book b = new Book(); 
    int value = 2019;
    b.setPublicationYear(value);
    assertEquals("incorrect publicationYear after set", value, b.getPublicationYear());
  }

}

