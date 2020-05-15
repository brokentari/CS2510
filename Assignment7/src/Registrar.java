import java.util.function.BiPredicate;
import tester.Tester;


class SameStudent implements BiPredicate<Student, Student> {
  public boolean test(Student t, Student u) {
    return t.equalTo(u);
  }
}

class IsStudentInClass implements BiPredicate<Course, Student> {
  public boolean test(Course t, Student u) {
    return t.isStudentInClass(u);
  }
}

interface IList<T> {
  public IList<T> addElement(T t);

  boolean present( BiPredicate<T, T> comp, T element2);

  <U> int inElement(BiPredicate<T, U> pred, U element2);
}

class MtList<T> implements IList<T> {

  public IList<T> addElement(T t) {
    return new ConsList<T>(t, this);
  }

  public boolean present(BiPredicate<T, T> comp, T element2) {
    return false;
  }

  public <U> int inElement(BiPredicate<T, U> pred, U element2) {
    return 0;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public IList<T> addElement(T t) {
    return new ConsList<T>(t, this);
  }

  public boolean present(BiPredicate<T, T> comp, T element2) {
    return comp.test(this.first, element2) || this.rest.present(comp, element2);
  }

  public <U> int inElement(BiPredicate<T, U> pred, U element2) {
    int numOfAppears = 0;
    if (pred.test(this.first, element2)) {
      numOfAppears = 1 + this.rest.inElement(pred, element2);
    }
    else {
      numOfAppears = this.rest.inElement(pred, element2);
    }
    
    return numOfAppears;
  }
}

class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  Instructor(String name, IList<Course> courses) {
    this(name);
    this.courses = courses;
  }

  boolean equalTo(Instructor i) {
    return this.name.contentEquals(i.name);
  }

  boolean dejavu(Student s) {
    return this.courses.inElement(new IsStudentInClass(), s) >= 2;
  }

  void assignClass(Course c) {
    this.courses = this.courses.addElement(c);
  }
}

class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    prof.assignClass(this);
    this.students = new MtList<Student>();
  }

  Course(String name, Instructor prof, IList<Student> students) {
    this(name, prof);
    this.students = students;
  }

  void register(Student s) {
    this.students = students.addElement(s);
  }

  boolean isStudentInClass(Student c) {
    return this.students.present(new SameStudent(), c);
  }
}

class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    courses = new MtList<Course>();
  }

  Student(String name, int id, IList<Course> registeredCourses) {
    this(name, id);
    this.courses = registeredCourses;
  }

  void enroll(Course c) {
    this.courses = this.courses.addElement(c);
    c.register(this);
  }

  boolean classmates(Student s) {
    return this.courses.inElement(new IsStudentInClass(), this) > 0
        && this.courses.inElement(new IsStudentInClass(), s) > 0;

  }

  boolean equalTo(Student s) {
    return this.name.contentEquals(s.name) && this.id == s.id;
  }
}

class ExamplesRegistrar {
  IList<Course> emptyCourse = new MtList<Course>();

  Student john = new Student("John", 1);
  Student isaac = new Student("Isaac", 2);
  Student charlie = new Student("Charlie", 3);
  Student duke = new Student("Duke", 4);
  Student kaleb = new Student("Kaleb", 5);
  Student mike = new Student("mike", 6);
  Student mia = new Student("mia", 7);
  Student alyssa = new Student("alyssa", 8);

  Instructor amit = new Instructor("Amit Sesh");
  Instructor leena = new Instructor("Leena Razzaq");
  Instructor clark = new Instructor("Clark Freifeld");
  Instructor weintraub = new Instructor("Mike Weintraub");

  Course fundiesA = new Course("CS2510", amit);
  Course fundiesA_2 = new Course("CS2510", amit);
  Course fundiesL = new Course("CS2510", leena);
  Course oodC = new Course("CS3500", clark);
  Course oodW = new Course("CS3500", weintraub);
  Course testCourse = new Course("TS1000", amit);

  boolean testEnroll(Tester t) {
    boolean initialConditionsJohn = t.checkExpect(john.courses, emptyCourse);
    john.enroll(fundiesA);
    boolean finalConditionsJohn = t.checkExpect(john.courses,
        new ConsList<Course>(fundiesA, emptyCourse));
    boolean initialConditionsIsaac = t.checkExpect(isaac.courses, emptyCourse);
    isaac.enroll(oodC);
    boolean finalConditionsIsaac = t.checkExpect(isaac.courses,
        new ConsList<Course>(oodC, emptyCourse));
    kaleb.enroll(fundiesA);
    john.enroll(testCourse);

    return initialConditionsJohn && finalConditionsJohn && initialConditionsIsaac
        && finalConditionsIsaac
        && t.checkExpect(fundiesA.isStudentInClass(john), true)
        && t.checkExpect(oodC.isStudentInClass(john), false)
        && t.checkExpect(john.classmates(isaac), false)
        && t.checkExpect(kaleb.classmates(john), true)
        && t.checkExpect(leena.dejavu(isaac), false) 
        && t.checkExpect(amit.dejavu(john), true);
  }

}
//john.enroll(fundiesA);
//isaac.enroll(fundiesA);
//charlie.enroll(fundiesL);
//duke.enroll(fundiesL);
//kaleb.enroll(oodC);
//mike.enroll(oodW);
//mia.enroll(fundiesA);
//mia.enroll(oodC);
//alyssa.enroll(oodW);

//&& t.checkExpect(john.classmates(isaac), true)
//&& t.checkExpect(duke.classmates(charlie), true)
//&& t.checkExpect(mike.classmates(isaac), false)
//&& t.checkExpect(kaleb.classmates(mike), false)
//&& t.checkExpect(fundiesA.equals(fundiesA_2), true);
