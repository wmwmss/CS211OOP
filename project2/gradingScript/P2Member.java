import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P2Member {

  private static Object[][][] data = {
	  {{10, "Member class grader"},
		  {"test_constructor_.*", 0.5, "constructor"},
		  {"test_getset_inherit", 0.5, "person-related get/set methods"},
		  {"test_getset_memberid_.*", 1.0, "get/set memberID"},
		  {"test_getset_borrowed_.*", 1.0, "get/set borrowedBooks"},
		  {"test_tostring_.*", 1.0, "toString"},
		  {"test_borrow_.*", 2.0, "borrow books"},
		  {"test_return_.*", 2.0, "return books"},
		  {"test_list_.*", 2.0, "list books"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P2Member().getClass(), data, args);
  }


  @Test(timeout=1000) public void test_constructor_def() { Member m = new Member(); }
  @Test(timeout=1000) public void test_constructor_full() { 
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345);
    Member m = new Member("Ada", "Lovelace", a, "M1248", null);
    assertEquals("badly initialized memberID", "M1248", m.getMemberId());
  }

  @Test(timeout=1000) public void test_getset_inherit() { 
    Member m = new Member();
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345);
    m.setFirstName("Ada");
    m.setLastName("Lovelace");
    m.setAddress(a);
    assertEquals("incorrect firstName after set", "Ada", m.getFirstName());
    assertEquals("incorrect lastName after set", "Lovelace", m.getLastName());
    assertSame("incorrect address after set", a, m.getAddress());
  }

  @Test(timeout=1000) public void test_getset_memberid_1() { 
    Member m = new Member();
    String value = "test";
    m.setMemberId(value);
    assertEquals("incorrect memberID after set", value, m.getMemberId());
  }
  @Test(timeout=1000) public void test_getset_memberid_2() { 
    Member m = new Member();
    String value = "AlphaBetaGamma";
    m.setMemberId(value);
    assertEquals("incorrect memberID after set", value, m.getMemberId());
  }
  @Test(timeout=1000) public void test_getset_borrowed_1() { 
    Member m = new Member(); 
    ArrayList<Book> a = new ArrayList<Book>();
    m.setBorrowedBooks(a);
    assertSame("incorrect borrowedBooks after set", a, m.getBorrowedBooks());
  }
  @Test(timeout=1000) public void test_tostring_1() { 
    Member m = new Member("test", "test", new Address("test", "test", "test", 12345), "test", null); 
    assertEquals("incorrect toString", "test test test test 12345 test test", m.toString());
  }
  @Test(timeout=1000) public void test_tostring_2() { 
    Member m = new Member("Prince", "Charming", new Address("777 Fairytale Ln", "Orlando", "FL", 99999), "M123", null); 
    assertEquals("incorrect toString", "M123 Prince Charming 777 Fairytale Ln 99999 Orlando FL", m.toString());
  }

  private Member bookSetup(Book... bookList) {
    Library.BookArray = new ArrayList<Book>();
    Library.BookArray.addAll(Arrays.asList(bookList));

    ArrayList<Book> books = new ArrayList<Book>();
    Member m = new Member();
    m.setBorrowedBooks(books);

    return m;
  }

  private Book getBook(int i) { return new Book(""+i, "title"+i, "author"+i, i); }

  private boolean sameBook(Book b1, Book b2) {
    if (b1 == null || b2 == null) return false;
    if (b1.getBookId() == null || b2.getBookId() == null) return false;
    if (b1.getTitle() == null || b2.getTitle() == null) return false;
    if (b1.getAuthor() == null || b2.getAuthor() == null) return false;
    return (b1.getBookId().equals(b2.getBookId()) &&
            b1.getTitle().equals(b2.getTitle()) &&
	    b1.getAuthor().equals(b2.getAuthor()) &&
	    b1.getPublicationYear() == b2.getPublicationYear());
  }

  private boolean contains(ArrayList<Book> books, Book book) {
    for (Book b : books) { if (sameBook(b, book)) return true; }
    return false;
  }

  @Test(timeout=1000) public void test_borrow_1() { 
    Book b = getBook(0);
    Member m = bookSetup(b);
    m.borrowBooks(b);
    assertTrue("book not found after borrowing", contains(m.getBorrowedBooks(), b));
    assertEquals("wrong number of books after borrowing", 1, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_borrow_2() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    Member m = bookSetup(b0, b1);
    m.borrowBooks(b0);
    m.borrowBooks(b1);
    assertTrue("book 0 not found after borrowing", contains(m.getBorrowedBooks(), b0));
    assertTrue("book 1 not found after borrowing", contains(m.getBorrowedBooks(), b1));
    assertEquals("wrong number of books after borrowing", 2, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_borrow_3() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    Member m = bookSetup(books);
    for (int i = 0;  i < books.length; i++) { m.borrowBooks(books[i]); }
    for (int i = 0;  i < books.length; i++) { 
      assertTrue("book " + i + " not found after borrowing", contains(m.getBorrowedBooks(), books[i]));
    }
    assertEquals("wrong number of books after borrowing", books.length, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_return_1() { 
    Book b = getBook(0);
    Member m = bookSetup(b);
    m.getBorrowedBooks().add(b);
    m.returnBooks(b);
    assertFalse("book still found after returning", contains(m.getBorrowedBooks(), b));
    assertEquals("wrong number of books after returning", 0, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_return_2() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    Member m = bookSetup(b0, b1);
    m.getBorrowedBooks().addAll(Arrays.asList(b0, b1));
    m.returnBooks(b0);
    assertFalse("book 0 still found after returning", contains(m.getBorrowedBooks(), b0));
    assertTrue("book 1 not found after returning book 0", contains(m.getBorrowedBooks(), b1));
    assertEquals("wrong number of books after one return", 1, m.getBorrowedBooks().size());
    m.returnBooks(b1);
    assertFalse("book 1 still found after returning", contains(m.getBorrowedBooks(), b1));
    assertEquals("wrong number of books after two returns", 0, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_return_3() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    Member m = bookSetup(b0, b1);
    m.getBorrowedBooks().addAll(Arrays.asList(b1, b0));
    m.returnBooks(b0);
    assertFalse("book 0 still found after returning", contains(m.getBorrowedBooks(), b0));
    assertTrue("book 1 not found after returning book 0", contains(m.getBorrowedBooks(), b1));
    assertEquals("wrong number of books after one return", 1, m.getBorrowedBooks().size());
    m.returnBooks(b1);
    assertFalse("book 1 still found after returning", contains(m.getBorrowedBooks(), b1));
    assertEquals("wrong number of books after two returns", 0, m.getBorrowedBooks().size());
  }
  @Test(timeout=1000) public void test_return_4() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    Member m = bookSetup(books);
    m.getBorrowedBooks().addAll(Arrays.asList(books));
    for (int i = 0;  i < books.length; i++) { 
      m.returnBooks(books[i]);
      assertFalse("book " + i + " still found after returning", contains(m.getBorrowedBooks(), books[i]));
      for (int j = i+1;  j < books.length; j++) {
        assertTrue("book " + j + " not found after returning book " + i, contains(m.getBorrowedBooks(), books[j]));
      }
      assertEquals("wrong number of books after returning book " + i, books.length - i-1, m.getBorrowedBooks().size());
    }
  }

  private String bookOutput(int i) {
    return String.format("%-10s %-20s %-20s %-10d", i, "title"+i, "author"+i, i);
  }


  @Test(timeout=1000) public void test_list_1() { 
    Book b = getBook(0);
    Member m = bookSetup(b);
    m.getBorrowedBooks().add(b);
    GradeHelper.setCapture();
    m.listBorrowedBooks();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    assertTrue("string output missing Book: " + bookOutput(0), lines.contains(bookOutput(0)));
  }
  @Test(timeout=1000) public void test_list_2() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    Member m = bookSetup(books);
    m.getBorrowedBooks().addAll(Arrays.asList(books));
    GradeHelper.setCapture();
    m.listBorrowedBooks();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    for (int i = 0;  i < books.length; i++) {
      assertTrue("string output missing Book: " + bookOutput(i), lines.contains(bookOutput(i)));
    }
  }

}

