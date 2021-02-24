/** 
 * Student class represents a student who has a major.
 * A Student is a Person. 
 */
public class Student extends Person{
  protected String major;
  
  Student(String name, int age, String major){
    super(name, age);
    this.major = major;
  }
  
  @Override
  public String getName(){
    return super.getName() + ", Mason student";
  }
  
  @Override
  public String toString(){
    return "(Student) " + super.toString() + ", majored in " + this.major;
  }
  
}