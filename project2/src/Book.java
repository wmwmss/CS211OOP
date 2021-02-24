
public class Book {

  private String bookID;
  private String title;
  private String author;
  private int publicationYear;
  
  public Book(String bookID, String title, String author, int publicationYear) {
    this.bookID = bookID;
    this.title = title;
    this.author = author;
    this.publicationYear = publicationYear;
  }
  
  public Book() { this("", "", "", 0); }
  
  public String getBookId() { return bookID; }
  public String getTitle() { return title; }
  public String getAuthor() { return author; }
  public int getPublicationYear() { return publicationYear; }
  
  public void setBookId(String bookID) { this.bookID = bookID; }
  public void setTitle(String title)  { this.title = title; }
  public void setAuthor(String author) { this.author = author; }
  public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

  private boolean wellDefined() {
    return (bookID != null && title != null && author != null);
  }
  
  public boolean equals(Object o) {
    if (!wellDefined()) return false;
    if (!(o instanceof Book)) return false;
    Book b = (Book)o;
    if (!b.wellDefined()) return false;
    if (!getBookId().equals(b.getBookId())) return false;
    if (!getTitle().equals(b.getTitle())) return false;
    if (!getAuthor().equals(b.getAuthor())) return false;
    if (getPublicationYear() != b.getPublicationYear()) return false;
    return true;
  }
  
  public int hashCode() { return getBookId().hashCode(); }
  
  public String toString() { 
    return String.format("%s %s %s %d", bookID, title, author, publicationYear);
  }
}
