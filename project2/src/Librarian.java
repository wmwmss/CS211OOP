public class Librarian extends Person {
  
  private String employeeID;
  
  public String getEmployeeId() { return employeeID; }
  public void setEmployeeId(String employeeID) { this.employeeID = employeeID; }

  public Librarian(String first, String last, Address address, String employeeID) {
    super(first, last, address);
    this.employeeID = employeeID;
  }
  public Librarian() { this("", "", new Address(), ""); }

  public void registerBooks(Book book) {
    if (book.getBookId() != null && book.getTitle() != null && book.getAuthor() != null) 
      Library.BookArray.add(book);
  }

  public boolean deleteBooks(Book book) {
    for (Book b : Library.BookArray) {
      if (book.equals(b)) {
        Library.BookArray.remove(b);
        return true;
      }
    }
    return false; 
  }

  public void registerMember(Member member) {
    if (member.getFirstName() != null && member.getLastName() != null &&  member.getAddress() != null) 
      Library.MemberArray.add(member);
  }

  public boolean deleteMember(Member member) { 
    return Library.MemberArray.remove(member);
  }

//List Members-- A librarian can list all the members of the library on the console. Use the following method signature.

  public void listMembers() {
    System.out.printf("%-10s %-10s %-10s %-10s\n", "Member ID", "First Name", "Last Name", "City");
    for (Member m : Library.MemberArray) {
      System.out.printf("%-10s %-10s %-10s %-10s\n", m.getMemberId(), m.getFirstName(), m.getLastName(), m.getAddress().getCity());
    }
  }


/*The output of this opeartion is displayed on the console as follows (Use the printf formating string : "%-10s %-10s %-10s %-10s \n")
         
Member ID First Name Last Name City
001 Jhon Smith Fairfax
002 Mohammed Abdul DC
003 Lee Chris Springfield
004 Wole Soyinka Alexandria
-- -- -- -- --
-- -- -- -- --
*/

//List Books-- A librarian can list all the books in the library on the console. Use the following method signature.

  public void listBooks( ) {
    System.out.printf("%-10s %-20s %-20s %-10s\n", "Book ID", "Title", "Author", "Year of Publication");
    for (Book b : Library.BookArray) {
      System.out.printf("%-10s %-20s %-20s %-10d\n", b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublicationYear());
    }
  }

/*The output of this opeartion is displayed on the console as follows (Use the printf formating string: "%-10s %-20s %-20s %-10d\n")
         
Book ID Title Author Year Of Publication
001 Programming with Java Jhon Brown 2007
002 Python 3 Doug Hellmann 2017
007 The Da Vinci Code Dan Brown 2000
045 John Adams David McCullough 2002
015 Introduction to Biology Smith Ben 2011
-- -- -- -- --
-- -- -- -- --
*/
//The class aslo has an overriden toString() method that returns a String which is a concatination of the EmployeeID, firstName, lastName, street no, zipcode, city, and state. For Example, 001 John Smith 4400 University Drive 22030 Fairfax VA
  
  public String toString() {
    return getEmployeeId() + " " + super.toString();
  }
}