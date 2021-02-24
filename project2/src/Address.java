
public class Address {
 
  private String streetNumber;
  private int zipCode;
  private String city;
  private String state;
  
  public Address(String streetNumber, String city, String state, int zipCode) {
    this.streetNumber = streetNumber;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }
  
  public Address() { this("","","",0); }
  
  public String getStreetNo() { return streetNumber; }
  public String getCity() { return city; }
  public String getState() { return state; }
  public int getZipCode() { return zipCode; }

  public void setStreetNo(String streetNumber) { this.streetNumber = streetNumber; }
  public void setCity(String city) { this.city = city; }
  public void setState(String state) { this.state = state; }
  public void setZipCode(int zipCode) { this.zipCode = zipCode; }
  
  
  private boolean wellDefined() {
    return (streetNumber != null && city != null && state != null);
  }
  
  public boolean equals(Object o) {
    if (!wellDefined()) return false;
    if (!(o instanceof Address)) return false;
    Address a = (Address)o;
    if (!a.wellDefined()) return false;
    if (!getStreetNo().equals(a.getStreetNo())) return false;
    if (!getCity().equals(a.getCity())) return false;
    if (!getState().equals(a.getState())) return false;
    if (getZipCode() != a.getZipCode()) return false;
    return true;
  }
  
  public int hashCode() { return getStreetNo().hashCode(); }

  
  public String toString() {
    return String.format("%s %d %s %s", streetNumber, zipCode, city, state);
  }

}
