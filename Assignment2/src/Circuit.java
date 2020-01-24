import tester.Tester;

// represents a circuit component
interface ICircuit {
  // count number of components
  int countComponents();

  // calculates total voltage
  double totalVoltage();

  // calculates total current
  double totalCurrent();

  // calculates total resistance
  double totalResistance();

  // reverses voltage of every battery
  ICircuit reversePolarity();
}

// represent a battery
class Battery implements ICircuit {
  String name;
  double voltage;
  double nominalResistance;

  Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }

  /*-
   * fields: 
   *  this.name ... String 
   *  this.voltage ... double 
   *  this.nominalResistance ... double
   * methods: 
   *  this.countComponents() ... integer 
   *  this.totalVoltage() ... double
   *  this.totalCurrent() ... double 
   *  this.totalResistance() ... double
   *  this.reversePolarity ... ICircuit
   */

  // count number of components
  public int countComponents() {
    return 1;
  }

  // calculates total voltage
  public double totalVoltage() {
    return this.voltage;
  }

  // calculates total current
  public double totalCurrent() {
    return this.voltage / this.nominalResistance;
  }

  // calculates total resistance
  public double totalResistance() {
    return this.nominalResistance;
  }

  // reverses voltage of every battery
  public ICircuit reversePolarity() {
    return new Battery(this.name, (-this.voltage), this.nominalResistance);
  }
}

// represents a resistor
class Resistor implements ICircuit {
  String name;
  double resistance;

  Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }

  /*-
   * fields: 
   *  this.name ... String 
   *  this.resistance ... double
   * methods
   *  this.countComponents() ... integer 
   *  this.totalVoltage() ... double
   *  this.totalCurrent() ... double 
   *  this.totalResistance() ... double
   *  this.reversePolarity ... ICircuit
   */

  // count number of components
  public int countComponents() {
    return 1;
  }

  // calculates total voltage
  public double totalVoltage() {

    return 0;
  }

  // calculates total current
  public double totalCurrent() {

    return 0;
  }

  // calculates total resistance
  public double totalResistance() {
    return this.resistance;
  }

  // reverses voltage of every battery
  public ICircuit reversePolarity() {
    return this;
  }
}

// represents a series circuit
class Series implements ICircuit {
  ICircuit first;
  ICircuit second;

  Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  /*-
   * fields:
   *  this.first ... ICircuit 
   *  this.second ... ICircuit 
   * methods
   *  this.countComponents() ... integer 
   *  this.totalVoltage() ... double
   *  this.totalCurrent() ... double 
   *  this.totalResistance() ... double
   *  this.reversePolarity ... ICircuit 
   * methods for fields:
   *  this.first.countComponents() ... integer
   *  this.rest.countComponents() ... integer
   */

  // count number of components
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // calculates total voltage
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  }

  // calculates total current
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // calculates total resistance
  public double totalResistance() {
    return this.first.totalResistance() + this.second.totalResistance();
  }

  // reverses voltage of every battery
  public ICircuit reversePolarity() {
    return new Series(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

// represents a parallel circuit
class Parallel implements ICircuit {
  ICircuit first;
  ICircuit second;

  Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  /*-
   * fields:
   *  this.first ... ICircuit 
   *  this.second ... ICircuit 
   * methods
   *  this.countComponents() ... integer 
   *  this.totalVoltage() ... double
   *  this.totalCurrent() ... double 
   *  this.totalResistance() ... double
   *  this.reversePolarity ... ICircuit 
   * methods for fields:
   *  this.first.countComponents() ... integer
   *  this.rest.countComponents() ... integer
   */

  // count number of components
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // calculates total voltage
  public double totalVoltage() {
    return this.first.totalVoltage();
  }

  // calculates total current
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // calculates total resistance
  public double totalResistance() {
    return 1 / ((1 / this.first.totalResistance()) + (1 / this.second.totalResistance()));
  }

  // reverses voltage of every battery
  public ICircuit reversePolarity() {
    return new Parallel(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

class ExamplesCircuits {
  // Own Example
  // A Battery is connected in series with a Parallel circuit, which cotains two
  // parallel Resistors, which is then connected to a Battery in series.
  ICircuit exampleCircuit = new Series(new Battery("EB 1", 5.0, 10.0),
      new Series(new Parallel(new Resistor("ER 1", 150.0), new Resistor("ER 2", 300.0)),
          new Battery("EB 2", 10.0, 5.0)));

  // Batteries
  ICircuit batteryOne = new Battery("B 1", 10.0, 25.0);
  ICircuit batteryOneComplex = new Battery("BT 1", 10.0, 0.0);
  ICircuit batteryTwoComplex = new Battery("BT 2", 20.0, 0.0);

  // Resistors
  ICircuit resistorOne = new Resistor("R 1", 100.0);
  ICircuit resistorTwo = new Resistor("R 2", 250.0);
  ICircuit resistorThree = new Resistor("R 3", 500.0);
  ICircuit resistorFour = new Resistor("R 4", 200.0);
  ICircuit resistorFive = new Resistor("R 5", 50.0);

  // Circuits and Components
  ICircuit simpleSeries = new Series(this.batteryOne, this.resistorOne);
  ICircuit componentOne = new Series(resistorFour, resistorFive);
  ICircuit componentTwo = new Parallel(componentOne, resistorOne);
  ICircuit componentThree = new Parallel(componentTwo, resistorTwo);
  ICircuit componentFour = new Parallel(componentThree, resistorThree);
  ICircuit componentFive = new Series(batteryOneComplex, batteryTwoComplex);
  ICircuit complexCircuit = new Series(componentFive, componentFour);

  // test for counting components
  boolean testCount(Tester t) {
    return t.checkExpect(this.batteryOne.countComponents(), 1)
        && t.checkExpect(this.componentOne.countComponents(), 2)
        && t.checkExpect(this.complexCircuit.countComponents(), 7)
        && t.checkExpect(this.exampleCircuit.countComponents(), 4);
  }

  // test for calculating voltage
  boolean testVoltage(Tester t) {
    return t.checkExpect(this.batteryOne.totalVoltage(), 10.0)
        && t.checkExpect(this.resistorOne.totalVoltage(), 0.0)
        && t.checkExpect(this.componentOne.totalVoltage(), 0.0)
        && t.checkExpect(this.complexCircuit.totalVoltage(), 30.0)
        && t.checkExpect(this.exampleCircuit.totalVoltage(), 15.0);
  }

  // test for calculating resistance
  boolean testResistance(Tester t) {
    return t.checkExpect(batteryOneComplex.totalResistance(), 0.0)
        && t.checkExpect(batteryTwoComplex.totalResistance(), 0.0)
        && t.checkExpect(simpleSeries.totalResistance(), 125.0)
        && t.checkExpect(complexCircuit.totalResistance(), 50.0)
        && t.checkExpect(componentOne.totalResistance(), 250.0)
        && t.checkExpect(exampleCircuit.totalResistance(), 115.0);
  }

  // test for calculating current
  boolean testCurrent(Tester t) {
    return t.checkInexact(batteryOne.totalCurrent(), 0.4, 0.01)
        && t.checkInexact(resistorOne.totalCurrent(), 0.0, 0.01)
        && t.checkInexact(simpleSeries.totalCurrent(), 0.08, 0.01)
        && t.checkInexact(complexCircuit.totalCurrent(), 0.6, 0.01)
        && t.checkInexact(exampleCircuit.totalCurrent(), 0.13, 0.01);
  }

  // test for calculating polarity
  boolean testPolarity(Tester t) {
    return t.checkExpect(batteryOne.reversePolarity(), new Battery("B 1", -10, 25))
        && t.checkExpect(resistorOne.reversePolarity(), resistorOne) && t.checkExpect(
            simpleSeries.reversePolarity(), new Series(new Battery("B 1", -10, 25), resistorOne));
  }
}