import tester.Tester;

interface ILoString {
  String getCodon();
  
  String getBase();
  
  ILoString getRest();

  int countBase();
  
  ILoString convertCodons();
  
  ILoLoString translate();
  
}

interface ILoLoString {
  
}

class MtLoString implements ILoString {
  MtLoString() { };
 
  
  public String getCodon() { 
    return ""; 
  }

  public int countBase() {
    // TODO Auto-generated method stub
    return 0;
  }

  public ILoString getRest() {
    // TODO Auto-generated method stub
    return this;
  }


  @Override
  public ILoString convertCodons() {
    // TODO Auto-generated method stub
    return this;
  }


  @Override
  public ILoLoString translate() {
    // TODO Auto-generated method stubro
    return null;
  }
}

// represents the RNA and Proteins
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public String getBase() {
    return this.first;
  }
  
  public ILoString getRest() {
    return this.rest;
  }
 
  
  // returns a codon
  public String getCodon() {
    if (this.countBase() >= 3) {
      return this.first + this.rest.getBase() + this.rest.getRest().getBase();
    }
    else if (this.countBase() == 2) {
      return this.first + this.rest.getBase();
    } 
    else if (this.countBase() == 1) {
      return this.first;
    } 
    return "";
  }
  
  // returns a list of codons
  public ILoString convertCodons() {
    if (this.countBase() >= 3) {
      return new ConsLoString(this.getCodon(), this.getRest().getRest().getRest().convertCodons());
    }
    else if (this.countBase() == 2) {
      return new ConsLoString(this.getCodon(), this.getRest().getRest().convertCodons());
    } 
    else if (this.countBase() == 1) {
      return new ConsLoString(this.getCodon(), this.getRest().convertCodons());
    } 
    return new MtLoString();
  }

  @Override
  public int countBase() {
    // TODO Auto-generated method stub
    return 1 + this.rest.countBase();
  }
  
  public ILoLoString translate() {
    ILoString listOfCodon = this.convertCodons();
    if (listOfCodon.getBase().equals("UAG") 
      || (listOfCodon.getBase().equals("UAA") 
       || (listOfCodon.getBase().equals("UGA")))) {
       return new ILoLoString(this);
    }
    else {
      return null;
    }
  }
}
  
  /*
  boolean checkEnd() {
    if ((this.codon.length() < 3) || 
        (this.codon.contentEquals("UAG")) ||
        (this.codon.contentEquals("UAA")) ||
        (this.codon.contentEquals("UGA"))) {
      return false;
    } else {
      return true;
    }
  }
  */

class MtLoProtein implements ILoLoString {
  
}

class ConsLoLoString implements ILoLoString {
  ILoString first;
  ILoString rest;
  
  ConsLoLoString(ILoString first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
}

class ExamplesRNA {
  ILoString empty = new MtLoString();
  ILoString RNA1 = new ConsLoString("U", (new ConsLoString("C", new ConsLoString("A", new ConsLoString("G", 
      new ConsLoString("A",  new ConsLoString("U", new ConsLoString("A", new ConsLoString("G", empty)))))))));
  ILoString RNA2 = new ConsLoString("A", new ConsLoString("U", empty));
  ILoString RNA3 = new ConsLoString("A", empty);
  
  boolean testCodon(Tester t) {
    return t.checkExpect(RNA1.getCodon(), "UCA")
        && t.checkExpect(RNA2.getCodon(), "AU")
        && t.checkExpect(RNA3.getCodon(), "A");
  }
  
  boolean testGetListofCodon(Tester t) {
    return t.checkExpect(RNA1.convertCodons(), 
        new ConsLoString("UCA", new ConsLoString("GAU", new ConsLoString ("AG", empty))))
        && t.checkExpect(RNA2.convertCodons(), new ConsLoString("AU", empty))
        && t.checkExpect(RNA3.convertCodons(), new ConsLoString("A", empty));
  }
}