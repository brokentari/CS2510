import tester.Tester;

interface ITransportation {
  boolean isMoreFuelEfficientThan(int mpg);
}

class Bicycle implements ITransportation {
  String brand;
  
  Bicycle (String brand) {
    this.brand = brand;
  }

  @Override
  public boolean isMoreFuelEfficientThan(int mpg) {
    return true;
  }
}

class Car implements ITransportation {
  String make;
  int mpg;
  
  Car (String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }

  @Override
  public boolean isMoreFuelEfficientThan(int mpg) {
    return this.mpg >= mpg;
  }
}

class Person {
  String name;
  ITransportation mot;
  
  Person (String name, ITransportation mot) {
    this.name = name;
    this.mot = mot;
  }
  
  boolean motMeetsFuelEfficiency (int mpg) {
    return this.mot.isMoreFuelEfficientThan(mpg);
  }
}

class ExamplesPerson {
  ITransportation diamondback = new Bicycle("Diamondback");
  ITransportation toyota = new Car("Toyota", 30);
  ITransportation lambo = new Car("Lamborghini", 17);
  
  Person bob = new Person("bob", this.diamondback);
  Person ben = new Person("ben", this.toyota);
  Person becca = new Person("becca", this.lambo);
  
  boolean testFuelEfficiency(Tester t) {
    return t.checkExpect(this.bob.motMeetsFuelEfficiency(50), true) &&
           t.checkExpect(this.ben.motMeetsFuelEfficiency(40), false) &&
           t.checkExpect(this.becca.motMeetsFuelEfficiency(10), true);
  }
}


interface IPet{ 
  boolean hasSameName(String name);
}

class Cat implements IPet {
  String name;
  String kind;
  boolean longhaired;
  
  Cat (String name, String kind, boolean longhaired) {
    this.name = name;
    this.kind = kind;
    this.longhaired = longhaired;
  }

  @Override
  public boolean hasSameName(String name) {
    // TODO Auto-generated method stub
    return this.name.equals(name);
  }
}

class Dog implements IPet {
  String name;
  String kind;
  boolean male;
  
  Dog(String name, String kind, boolean male) {
    this.name = name;
    this.kind = kind;
    this.male = male;
  }

  @Override
  public boolean hasSameName(String name) {
    // TODO Auto-generated method stub
    return this.name.equals(name);
  }
}

class NoPet implements IPet {
  NoPet() { }

  @Override
  public boolean hasSameName(String name) {
    // TODO Auto-generated method stub
    return false;
  }
}

class Person2 {
  String name;
  IPet pet;
  int age;
  
  Person2 (String name, IPet pet, int age) {
    this.name = name;
    this.pet = pet;
    this.age = age;
  }
  
  // is this Person older than the given Person?
  boolean isOlder(Person2 other) {
    return this.age >= other.age;
  }
  
  // does the name of this person's pet match the given name
  boolean sameNamePet(String name) {
    return this.pet.hasSameName(name);
  }
  
  Person2 perish() {
    return new Person2(this.name, new NoPet(), this.age);
  }
}

class ExamplesPerson2 {
  IPet rex = new Dog("Rex", "Husky", true);
  IPet abby = new Dog("Abby", "Pug", false);
  IPet link = new Cat("Link", "Siamese", false);
  IPet roach = new Cat("Roach", "Black", true);
  IPet no_pet = new NoPet();
  
  Person2 jim = new Person2 ("Jim", rex, 15);
  Person2 bob = new Person2 ("Bob", abby, 20);
  Person2 karen = new Person2 ("Karen", link, 50);
  Person2 eva = new Person2 ("Eva", roach, 37);
  
  boolean testAge(Tester t) {
    return t.checkExpect(this.jim.isOlder(bob), false) &&
           t.checkExpect(this.karen.isOlder(eva), true);
  }
  
  boolean testName(Tester t) {
    return t.checkExpect(this.karen.sameNamePet("Link"), true) &&
           t.checkExpect(this.jim.sameNamePet("Link"), false);
  }
  
  boolean testPerish(Tester t) {
    return t.checkExpect(this.jim.perish(), new Person2("Jim", no_pet, 15));
  }
}

