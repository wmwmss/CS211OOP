import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  
public class P2Librarian {

  private static Object[][][] data = {
	  {{10, "Librarian class grader"},
		  {"test_constructor_.*", 1.0, "constructor"},
		  {"test_getset_inherit", 1.0, "person-related get/set methods"},
		  {"test_getset_employeeid_.*", 1.0, "get/set employeeID"},
		  {"test_tostring_.*", 1.0, "toString"},
		  {"test_book_reg_.*", 1.0, "register books"},
		  {"test_book_del_.*", 1.0, "delete books"},
		  {"test_book_list_.*", 1.0, "list all books"},
		  {"test_memb_reg_.*", 1.0, "register members"},
		  {"test_memb_del_.*", 1.0, "delete members"},
		  {"test_memb_list_.*", 1.0, "list all members"},
	  },
  };
  private final double ERR = GradeHelper.ERR;

  public static void main(String args[]){
	  GradeHelper.tester(new P2Librarian().getClass(), data, args);
  }


  @Test(timeout=1000) public void test_constructor_def() { Librarian l = new Librarian(); }
  @Test(timeout=1000) public void test_constructor_full() { 
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345);
    Librarian l = new Librarian("Ada", "Lovelace", a, "M1248");
    assertEquals("badly initialized employeeID", "M1248", l.getEmployeeId());
  }

  @Test(timeout=1000) public void test_getset_inherit() { 
    Librarian l = new Librarian();
    Address a = new Address("123 Main Street", "Anytown", "VA", 12345);
    l.setFirstName("Ada");
    l.setLastName("Lovelace");
    l.setAddress(a);
    assertEquals("incorrect firstName after set", "Ada", l.getFirstName());
    assertEquals("incorrect lastName after set", "Lovelace", l.getLastName());
    assertSame("incorrect address after set", a, l.getAddress());
  }

  @Test(timeout=1000) public void test_getset_employeeid_1() { 
    Librarian l = new Librarian();
    String value = "test";
    l.setEmployeeId(value);
    assertEquals("incorrect employeeID after set", value, l.getEmployeeId());
  }
  @Test(timeout=1000) public void test_getset_memberid_2() { 
    Librarian l = new Librarian();
    String value = "AlphaBetaGamma";
    l.setEmployeeId(value);
    assertEquals("incorrect employeeID after set", value, l.getEmployeeId());
  }
  @Test(timeout=1000) public void test_tostring_1() { 
    Librarian l = new Librarian("test", "test", new Address("test", "test", "test", 12345), "test"); 
    assertEquals("incorrect toString", "test test test test 12345 test test", l.toString());
  }
  @Test(timeout=1000) public void test_tostring_2() { 
    Librarian l = new Librarian("Prince", "Charming", new Address("777 Fairytale Ln", "Orlando", "FL", 99999), "M123"); 
    assertEquals("incorrect toString", "M123 Prince Charming 777 Fairytale Ln 99999 Orlando FL", l.toString());
  }

  private void bookSetup(Book... bookList) {
    Library.BookArray = new ArrayList<Book>();
    Library.BookArray.addAll(Arrays.asList(bookList));
  }

  private void memberSetup(Member... memberList) {
    Library.MemberArray = new ArrayList<Member>();
    Library.MemberArray.addAll(Arrays.asList(memberList));
  }

  private Book getBook(int i) { return new Book(""+i, "title"+i, "author"+i, i); }
  private Member getMember(int i) { 
    return new Member("first"+i, "last"+i, new Address("street"+i,"city"+i,"state"+i,i), ""+i, null);
  }

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
  private boolean sameMember(Member m1, Member m2) {
    if (m1 == null || m2 == null) return false;
    if (m1.getMemberId() == null || m2.getMemberId() == null) return false;
    if (m1.getFirstName() == null || m2.getFirstName() == null) return false;
    if (m1.getLastName() == null || m2.getLastName() == null) return false;
    return (m1.getMemberId().equals(m2.getMemberId()) &&
            m1.getFirstName().equals(m2.getFirstName()) &&
	    m1.getLastName().equals(m2.getLastName()));
  }


  private boolean contains(ArrayList<Book> books, Book book) {
    for (Book b : books) { if (sameBook(b, book)) return true; }
    return false;
  }

  private boolean contains(ArrayList<Member> members, Member member) {
    for (Member m : members) { if (sameMember(m, member)) return true; }
    return false;
  }

  @Test(timeout=1000) public void test_book_reg_1() { 
    Book b = getBook(0);
    bookSetup();
    Librarian l = new Librarian();
    l.registerBooks(b);
    assertTrue("book not found after registering", contains(Library.BookArray, b));
    assertEquals("wrong number of books after registering", 1, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_reg_2() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    bookSetup();
    Librarian l = new Librarian();
    l.registerBooks(b0);
    l.registerBooks(b1);
    assertTrue("book 0 not found after registering", contains(Library.BookArray, b0));
    assertTrue("book 1 not found after registering", contains(Library.BookArray, b1));
    assertEquals("wrong number of books after registering", 2, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_reg_3() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    bookSetup();
    Librarian l = new Librarian();
    for (int i = 0;  i < books.length; i++) { l.registerBooks(books[i]); }
    for (int i = 0;  i < books.length; i++) { 
      assertTrue("book " + i + " not found after registering", contains(Library.BookArray, books[i]));
    }
    assertEquals("wrong number of books after registering", books.length, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_del_1() { 
    Book b = getBook(0);
    bookSetup(b);
    Librarian l = new Librarian();
    l.deleteBooks(b);
    assertFalse("book still found after deleting", contains(Library.BookArray, b));
    assertEquals("wrong number of books after deleting", 0, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_del_2() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    bookSetup(b0, b1);
    Librarian l = new Librarian();
    l.deleteBooks(b0);
    assertFalse("book 0 still found after deleting", contains(Library.BookArray, b0));
    assertTrue("book 1 not found after deleting book 0", contains(Library.BookArray, b1));
    assertEquals("wrong number of books after one deletion", 1, Library.BookArray.size());
    l.deleteBooks(b1);
    assertFalse("book 1 still found after deleting", contains(Library.BookArray, b1));
    assertEquals("wrong number of books after two deletions", 0, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_del_3() { 
    Book b0 = getBook(0);
    Book b1 = getBook(1);
    bookSetup(b1, b0);
    Librarian l = new Librarian();
    l.deleteBooks(b0);
    assertFalse("book 0 still found after deleting", contains(Library.BookArray, b0));
    assertTrue("book 1 not found after deleting book 0", contains(Library.BookArray, b1));
    assertEquals("wrong number of books after one deletion", 1, Library.BookArray.size());
    l.deleteBooks(b1);
    assertFalse("book 1 still found after deleting", contains(Library.BookArray, b1));
    assertEquals("wrong number of books after two deletions", 0, Library.BookArray.size());
  }
  @Test(timeout=1000) public void test_book_del_4() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    bookSetup(books);
    Librarian l = new Librarian();
    for (int i = 0;  i < books.length; i++) { 
      l.deleteBooks(books[i]);
      assertFalse("book " + i + " still found after deleting", contains(Library.BookArray, books[i]));
      for (int j = i+1;  j < books.length; j++) {
        assertTrue("book " + j + " not found after deleting book " + i, contains(Library.BookArray, books[j]));
      }
      assertEquals("wrong number of books after deleting book " + i, books.length - i-1, Library.BookArray.size());
    }
  }

  private String bookOutput(int i) {
    return String.format("%-10s %-20s %-20s %-10d", i, "title"+i, "author"+i, i);
  }
  private String memberOutput(int i) {
    return String.format("%-10s %-10s %-10s %-10s", i, "first"+i, "last"+i, "city"+i).trim();
  }


  @Test(timeout=1000) public void test_book_list_1() { 
    Book b = getBook(0);
    bookSetup(b);
    Librarian l = new Librarian();
    GradeHelper.setCapture();
    l.listBooks();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    assertTrue("string output missing Book: " + bookOutput(0), lines.contains(bookOutput(0)));
  }
  @Test(timeout=1000) public void test_book_list_2() { 
    Book[] books = new Book[5];
    for (int i = 0;  i < books.length; i++) { books[i] = getBook(i); }
    bookSetup(books);
    Librarian l = new Librarian();
    GradeHelper.setCapture();
    l.listBooks();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    for (int i = 0;  i < books.length; i++) {
      assertTrue("string output missing Book: " + bookOutput(i), lines.contains(bookOutput(i)));
    }
  }
  @Test(timeout=1000) public void test_memb_reg_1() { 
    Member m = getMember(0);
    memberSetup();
    Librarian l = new Librarian();
    l.registerMember(m);
    assertTrue("member not found after registering", contains(Library.MemberArray, m));
    assertEquals("wrong number of members after registering", 1, Library.MemberArray.size());
  }
  @Test(timeout=1000) public void test_memb_reg_2() { 
    Member m0 = getMember(0);
    Member m1 = getMember(1);
    memberSetup();
    Librarian l = new Librarian();
    l.registerMember(m0);
    l.registerMember(m1);
    assertTrue("member 0 not found after registering", contains(Library.MemberArray, m0));
    assertTrue("member 1 not found after registering", contains(Library.MemberArray, m1));
    assertEquals("wrong number of members after registering", 2, Library.MemberArray.size());
  }
  @Test(timeout=1000) public void test_memb_reg_3() { 
    Member[] membs = new Member[5];
    for (int i = 0;  i < membs.length; i++) { membs[i] = getMember(i); }
    memberSetup();
    Librarian l = new Librarian();
    for (int i = 0;  i < membs.length; i++) { l.registerMember(membs[i]); }
    for (int i = 0;  i < membs.length; i++) { 
      assertTrue("member " + i + " not found after registering", contains(Library.MemberArray, membs[i]));
    }
    assertEquals("wrong number of members after registering", membs.length, Library.MemberArray.size());
  }

  @Test(timeout=1000) public void test_memb_del_1() { 
    Member m = getMember(0);
    memberSetup(m);
    Librarian l = new Librarian();
    l.deleteMember(m);
    assertFalse("member still found after deleting", contains(Library.MemberArray, m));
    assertEquals("wrong number of members after deleting", 0, Library.MemberArray.size());
  }
  @Test(timeout=1000) public void test_memb_del_2() { 
    Member m0 = getMember(0);
    Member m1 = getMember(1);
    memberSetup(m0, m1);
    Librarian l = new Librarian();
    l.deleteMember(m0);
    assertFalse("member 0 still found after deleting", contains(Library.MemberArray, m0));
    assertTrue("member 1 not found after deleting member 0", contains(Library.MemberArray, m1));
    assertEquals("wrong number of members after one deletion", 1, Library.MemberArray.size());
    l.deleteMember(m1);
    assertFalse("member 1 still found after deleting", contains(Library.MemberArray, m1));
    assertEquals("wrong number of members after two deletions", 0, Library.MemberArray.size());
  }
  @Test(timeout=1000) public void test_memb_del_3() { 
    Member m0 = getMember(0);
    Member m1 = getMember(1);
    memberSetup(m1, m0);
    Librarian l = new Librarian();
    l.deleteMember(m0);
    assertFalse("member 0 still found after deleting", contains(Library.MemberArray, m0));
    assertTrue("member 1 not found after deleting member 0", contains(Library.MemberArray, m1));
    assertEquals("wrong number of members after one deletion", 1, Library.MemberArray.size());
    l.deleteMember(m1);
    assertFalse("member 1 still found after deleting", contains(Library.MemberArray, m1));
    assertEquals("wrong number of members after two deletions", 0, Library.MemberArray.size());
  }
  @Test(timeout=1000) public void test_memb_del_4() { 
    Member[] membs = new Member[5];
    for (int i = 0;  i < membs.length; i++) { membs[i] = getMember(i); }
    memberSetup(membs);
    Librarian l = new Librarian();
    for (int i = 0;  i < membs.length; i++) { 
      l.deleteMember(membs[i]);
      assertFalse("member " + i + " still found after deleting", contains(Library.MemberArray, membs[i]));
      for (int j = i+1;  j < membs.length; j++) {
        assertTrue("member " + j + " not found after deleting member " + i, contains(Library.MemberArray, membs[j]));
      }
      assertEquals("wrong number of members after deleting member " + i, membs.length - i-1, Library.MemberArray.size());
    }
  }

  @Test(timeout=1000) public void test_memb_list_1() { 
    Member m = getMember(0);
    memberSetup(m);
    Librarian l = new Librarian();
    GradeHelper.setCapture();
    l.listMembers();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    for (int i = 0;  i < lines.size();  i++) { lines.set(i, lines.get(i).trim()); }
    assertTrue("string output missing Member: " + memberOutput(0), lines.contains(memberOutput(0)));
  }
  @Test(timeout=1000) public void test_memb_list_2() { 
    Member[] membs = new Member[5];
    for (int i = 0;  i < membs.length; i++) { membs[i] = getMember(i); }
    memberSetup(membs);
    Librarian l = new Librarian();
    GradeHelper.setCapture();
    l.listMembers();
    String output = GradeHelper.getCapture();
    GradeHelper.unsetCapture();
    ArrayList<String> lines = new ArrayList<String>(Arrays.asList(output.split("\n")));
    for (int i = 0;  i < lines.size();  i++) { lines.set(i, lines.get(i).trim()); }
    for (int i = 0;  i < membs.length; i++) {
      assertTrue("string output missing Member: " + memberOutput(i), lines.contains(memberOutput(i)));
    }
  }

}

