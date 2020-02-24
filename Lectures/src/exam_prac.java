import tester.Tester;

interface ILoString{
  ILoString  genPair();
  ILoString getRest();
  PairOfLists unzip();
}

class MtLoString implements ILoString {
  public ILoString genPair() {
    return this;
  }
  
  public ILoString getRest() {
    return this;
  }
  
  public PairOfLists unzip() {
    return new PairOfLists(this, this);
  }
}

class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoString genPair() {
    return new ConsLoString(this.first, this.rest.getRest().genPair());
  }
  
  public ILoString getRest() {
    return this.rest;
  }
  
  public PairOfLists unzip() {
    return new PairOfLists(this.genPair(), this.rest.genPair());
  }
}

class PairOfLists {
  ILoString first, second;
  
  PairOfLists(ILoString first, ILoString second) {
    this.first = first;
    this.second = second;
  }
}


class ExamplePair {
  ILoString l1 = new ConsLoString("T", new ConsLoString("U", new ConsLoString("V", 
      new ConsLoString("W", new ConsLoString("X", new ConsLoString("Y", new ConsLoString("Z", new MtLoString())))))));
  
  boolean testPair(Tester t) {
    return t.checkExpect(l1.unzip(), new PairOfLists(new ConsLoString("T", (new ConsLoString("V", (new ConsLoString("X", (new ConsLoString("Z", new MtLoString()))))))), 
        new ConsLoString("U", new ConsLoString( "W", new ConsLoString("Y", new MtLoString())))));
  }
}
