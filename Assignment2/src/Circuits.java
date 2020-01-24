import tester.Tester;

interface ICircuit {
  int countComponents();
  
  double totalVoltage();
  
  double totalCurrent();
  
  double totalResistance();
  
  ICircuit reversePolarity();
}

class Battery implements ICircuit {
  String name;
  double voltage;
  double nominalResistance;
  
  Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }

  @Override
  public int countComponents() {
    // TODO Auto-generated method stub
    return 1;
  }

  @Override
  public double totalVoltage() {
    // TODO Auto-generated method stub
    return this.voltage;
  }

  @Override
  public double totalCurrent() {
    // TODO Auto-generated method stub
    return this.voltage / this.nominalResistance;
  }

  @Override
  public double totalResistance() {
    // TODO Auto-generated method stub
    return this.nominalResistance;
  }

  @Override
  public ICircuit reversePolarity() {
    // TODO Auto-generated method stub
    return new Battery(this.name, (-this.voltage), this.nominalResistance);
  }
}

class Resistor implements ICircuit {
  String name;
  double resistance;
  
  Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }

  @Override
  public int countComponents() {
    // TODO Auto-generated method stub
    return 1;
  }

  @Override
  public double totalVoltage() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double totalCurrent() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double totalResistance() {
    // TODO Auto-generated method stub
    return this.resistance;
  }

  @Override
  public ICircuit reversePolarity() {
    // TODO Auto-generated method stub
    return this;
  }
}

class Series implements ICircuit {
  ICircuit first;
  ICircuit second;
  
  Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public int countComponents() {
    // TODO Auto-generated method stub
    return this.first.countComponents() + this.second.countComponents();
  }

  @Override
  public double totalVoltage() {
    // TODO Auto-generated method stub
    return this.first.totalVoltage() + this.second.totalVoltage();
  }

  @Override
  public double totalCurrent() {
    // TODO Auto-generated method stub
    return this.totalVoltage() / this.totalResistance();
  }

  @Override
  public double totalResistance() {
    // TODO Auto-generated method stub
    return this.first.totalResistance() + this.second.totalResistance();
  }

  @Override
  public ICircuit reversePolarity() {
    // TODO Auto-generated method stub
    return new Series(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

class Parallel implements ICircuit {
  ICircuit first;
  ICircuit second;
  
  Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public int countComponents() {
    // TODO Auto-generated method stub
    return this.first.countComponents() + this.second.countComponents();
  }

  @Override
  public double totalVoltage() {
    // TODO Auto-generated method stub
    return this.first.totalVoltage();
  }

  @Override
  public double totalCurrent() {
    // TODO Auto-generated method stub
    return this.totalVoltage() / this.totalResistance();
  }

  @Override
  public double totalResistance() {
    // TODO Auto-generated method stub
    return 1 / ((1 / this.first.totalResistance()) + (1 / this.second.totalResistance()));
  }

  @Override
  public ICircuit reversePolarity() {
    // TODO Auto-generated method stub
    return new Parallel(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

class ExamplesCircuits {
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
  
  // Circuits
  ICircuit simpleSeries = new Series(batteryOne, resistorOne);
  ICircuit componentOne = new Series(resistorFour, resistorFive);
  ICircuit componentTwo = new Parallel(componentOne, 
      new Parallel(resistorOne, new Parallel(resistorTwo, resistorThree)));
  ICircuit complexCircuit = new Series(batteryOneComplex,
      new Series(batteryTwoComplex, componentTwo));
  
  boolean testCount(Tester t) {
    return t.checkExpect(batteryOne.countComponents(), 1) &&
           t.checkExpect(componentOne.countComponents(), 2) &&
           t.checkExpect(complexCircuit.countComponents(), 7) &&
           t.checkExpect(componentTwo.countComponents(), 5);
  }
  
  boolean testVoltage(Tester t) {
    return t.checkExpect(batteryOne.totalVoltage(), 10.0) &&
           t.checkExpect(resistorOne.totalVoltage(), 0.0) &&
           t.checkExpect(componentOne.totalVoltage(), 0.0) &&
           t.checkExpect(complexCircuit.totalVoltage(), 30.0);
  }
  
  boolean testResistance(Tester t) {
    return t.checkExpect(batteryOneComplex.totalResistance(), 0.0) &&
           t.checkExpect(batteryTwoComplex.totalResistance(), 0.0) &&
           t.checkExpect(simpleSeries.totalResistance(), 125.0) &&
           t.checkExpect(complexCircuit.totalResistance(), 50.0) &&
           t.checkExpect(componentOne.totalResistance(), 250.0) &&
           t.checkExpect(componentTwo.totalResistance(), 50.0);
  }
  
  boolean testCurrent(Tester t) {
    return t.checkInexact(batteryOne.totalCurrent(), 0.4, 0.01) &&
          t.checkInexact(resistorOne.totalCurrent(), 0.0, 0.01) &&
          t.checkInexact(simpleSeries.totalCurrent(), 0.08, 0.01) &&
          t.checkInexact(complexCircuit.totalCurrent(), 0.6, 0.01);
  }
  
  boolean testPolarity(Tester t) {
    return t.checkExpect(batteryOne.reversePolarity(), new Battery("B 1", -10, 25)) &&
           t.checkExpect(resistorOne.reversePolarity(), resistorOne) &&
           t.checkExpect(simpleSeries.reversePolarity(), new Series(
               new Battery("B 1", -10, 25), resistorOne));
  }
}