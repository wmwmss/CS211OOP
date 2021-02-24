import java.util.ArrayList;

public class VIPMember extends Member {
  private ArrayList<Book> allBooks;
  
  public VIPMember(String firstName, String lastName, Address address, String memberID) {
    super(firstName, lastName, address, memberID);
    this.allBooks = new ArrayList<Book>();
  }

  public VIPMember() { this("", "", new Address(), ""); }
  
  public void borrowBooks(Book book) { 
    super.borrowBooks(book);
    if (getBorrowedBooks().contains(book) && !allBooks.contains(book))
      allBooks.add(book);
  }

  
  public void setAllBorrowedBooks(ArrayList<Book> allBooks) { this.allBooks = allBooks; }
  
  public ArrayList<Book> getAllBorrowedBooks() { return allBooks; }
}