
//This class defines the super class Person


public class Person {
  
  private String firstName;
  private String lastName;
  private Address address;

  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public Address getAddress() { return address; }
  
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setAddress(Address address) { this.address = address; }

  public Person(String firstName, String lastName, Address address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
  }

  public Person() { this("","", new Address()); }
  
  public String toString() { 
    return String.format("%s %s %s", getFirstName(), getLastName(), getAddress().toString());
  }
  
  private boolean wellDefined() {
    return (firstName != null && lastName != null && address != null &&
            address.getStreetNo() != null && address.getCity() != null && address.getState() != null);
  }   
  
  public boolean equals(Object o) {
    if (!wellDefined()) return false;
    if (!(o instanceof Person)) return false;
    Person p = (Person)o;
    if (!p.wellDefined()) return false;
    if (!getFirstName().equals(p.getFirstName())) return false;
    if (!getLastName().equals(p.getLastName())) return false;
    if (!getAddress().equals(p.getAddress())) return false;
    return true;
  }
  
  public int hashCode() { return getLastName().hashCode(); }


}
