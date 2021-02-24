/** 
 * Person class represents a person who has a name and an age.
 * This is the parent class you should inherit from.
 */
public class Person{
  private String name;
  private int age;
  
  Person(String name, int age){
    this.name = name;
    this.age = age;
  }
  
  public String getName(){
    return this.name;
  }
  
  public void setName(String name){
    System.out.println("(Person) " + this.name + " has changed name to " + name);
    this.name = name;
  }
  
  public int getAge(){
    return this.age;
  }
  
  public void setAge(int age){
    System.out.println("(Person) " + this.name + " has changed age from " + this.age + " to " + age);
    //System.out.println(this);
    this.age = age;
  }
  
  // toString() outputs the person's name and age
  @Override
  public String toString(){
    return "(Person) " + this.getName() + ", " + this.age + " years old";
  }
  
  public static void main(String[] args){
    // test the inheritance implementation
    
    // Test Case 1
    // create a Person object, and print out the info by calling toString()
    System.out.println("Test Case 1");
    Person p1 = new Person("John", 20);
    System.out.println(p1.toString());
    
    // change the person's name, print out the info, and notice the setName of Person class is called
    p1.setName("Mike");
    System.out.println(p1);
    System.out.println();
    
    // Test Case 2
    // create a Student object, and print out the info by calling toString()
    System.out.println("Test Case 2");
    Student s1 = new Student("Tom", 18, "Physics");
    System.out.println(s1);
    
    // change the Student's age, and explain why setAge() method can be called 
    s1.setAge(21);
    System.out.println(s1);
    System.out.println();
    
    // Test Case 3
    // create an undergradStudent object, and print out the info by calling toString()
    System.out.println("Test Case 3");
    UndergradStudent u1 = new UndergradStudent("Lisa", 19, "CS", "dancing");
    System.out.println(u1);
    
    // change the major, and explain how setMajor in UndergradStudent could access major data field
    u1.setMajor("Math");
    System.out.println(u1);
    System.out.println();
    
    // Test Case 4
    // create a Person object that is actually an UndergradStudent, and print out the info by calling toString()
    // explain why we can do this, of which class setAge method is called and how the code executes
    System.out.println("Test Case 4");
    Person u2 = new UndergradStudent("Jessica", 19, "CS", "jogging");
    System.out.println(u2);
    u2.setAge(22);
    System.out.println(u2);
  }
}