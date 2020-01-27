interface ILoString {
  
}

interface ILoLoString {
  
}

class MtLoString implements ILoString {
  MtLoString() { };
}

class RNA implements ILoString {
  String base;
  ILoString rest;
  
  RNA(String base, ILoString rest) {
    this.base = base;
    this.rest = rest;
  }
}

class Protein implements ILoString {
  String codon;
  ILoString rest;
  
  Protein(String codon, ILoString rest) {
    this.codon = codon;
    this.rest = rest;
  }
}

class ILoProtein implements ILoLoString {
  ILoString first;
  ILoString rest;
  
  ILoProtein(ILoString first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
}