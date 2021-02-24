import java.util.ArrayList;

public class Member extends Person {

  private String memberID;
  private ArrayList<Book> borrowedBooks;
  
  public String getMemberId() { return memberID; }
  public ArrayList<Book> getBorrowedBooks() { return borrowedBooks; }
  
  
  public void setMemberId(String memberID) { this.memberID = memberID; }
  public void setBorrowedBooks(ArrayList<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }
  
  public Member(String firstName, String lastName, Address address, String memberID, ArrayList<Book> borrowedBooks) {
    super(firstName, lastName, address);
    this.memberID = memberID;
    this.borrowedBooks = borrowedBooks;
  }

  public Member(String firstName, String lastName, Address address, String memberID) {
    this(firstName, lastName, address, memberID, new ArrayList<Book>());
  }
  
  public Member() { this("","",new Address(), ""); }

  private void moveBook(Book book, ArrayList<Book> from, ArrayList<Book> to) {
    if (from == null) {
      if (book.equals(book) && (to != null)) { to.add(book); }
      return;
    }
    for (Book b : from) {
      if (book.equals(b)) {
        from.remove(b);
        if (to != null) to.add(b);
        return;
      }
    }
  }
  
  public void borrowBooks(Book book) { 
    moveBook(book, null, borrowedBooks); 
  }

//Return borrowed books -- -- A member can return a book that s/he borrowed from the library. The member should give a complete book information (book ID,title, author, ...) to return the book and the book should be in borrowedBook list. The borrowedBooks list should be updated after the operation. Use the following method signature.

  public void returnBooks(Book book) {
    //moveBook(book, borrowedBooks, Library.BookArray); 
    moveBook(book, borrowedBooks, null);
  }

//list borrowed books -- A member can list the list of books s/he borrowed but not returned. Use the following method signature.

  public void listBorrowedBooks( ) {
    System.out.printf("%-10s %-20s %-20s %-10s\n", "Book ID", "Title", "Author", "Year of Publication");
    for (Book b : borrowedBooks) {
      System.out.printf("%-10s %-20s %-20s %-10d\n", b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublicationYear());
    }
  }

//The class aslo has an overriden toString()method that returns a String which is a concatination of the MemberID, firstName, lastName, street no, zipcode, city, and state. For Example, 001 John Smith 4400 University Drive 22030 Fairfax VA
  
  public String toString() {
    return getMemberId() + " " + super.toString();
  }
}
