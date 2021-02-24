/** 
 * UndergradStudent class represents a undergraduate student who has a hobby.
 * A UndergradStudent is a Student. 
 */
public class UndergradStudent extends Student{
  private String hobby;
  
  UndergradStudent(String name, int age, String major, String hobby){
    super(name, age, major);
    this.hobby = hobby;
  }
  
  public void setMajor(String major){
    System.out.println("(UndergradStudent) " + this.getName() + ", has changed major from " + this.major + " to " + major);
    this.major = major;
  }
  
  @Override
  public void setAge(int age){
    System.out.println("(UndergradStudent) " + this.getName() + ", has changed age from " + this.getAge() + " to " + age);
    super.setAge(age);
  }
  
  @Override
  public String toString(){
    return "(UndergradStudent) " + super.toString() + ", likes " + this.hobby;
  }
  
}