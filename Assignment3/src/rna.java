import tester.Tester;

// represents a list of strings
interface ILoString {

  //outputs whether or not the base is a stop codon
   boolean stopCodon(String base);
   
   // checks if protein is empty 
   String checkProtein();
   
  // converts an RNA sequence into a list of strings, aka codons
  ILoString convertRNAHelper(String base, ILoString codons);

  // outputs a list of codons
  ILoString convertRNA();

  // accumulates a list of proteins and outputs a list of list of proteins
  ILoLoString translateHelper(ILoString proteins, ILoLoString proteinsSoFar);
  
  // outputs a list of list of proteins
  ILoLoString translate();
  
}

// represents an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {}
  
  /* CLASS TEMPLATE:
   * Methods:
   * ... this.stopCodon(String) ... - boolean
   * ... this.checkProtein() ... - String
   * ... this.returnRNA(String) ... - ILoString
   * ... this.convertRNAHelper(String, ILoString) ... - ILoString
   * ... this.convertRNA() ... - ILoString
   * ... this.translateHelper(ILoString, ILoLoString)... - ILoLoString
   * ... this.translate()... - ILoLoString
   */

  // outputs whether or not the current base is a stop codon
  //same as class template
   public boolean stopCodon(String base) {
     return false;
   }
   
   // returns empty string if protein is empty
   // same as class template
   public String checkProtein() {
     return "";
   }
   
  // return empty list of codons if RNA sequence is empty
  // same as class template
  public ILoString returnRNA(String base) {
    return new MtLoString();
  }

  // return a new protein with the current base and codons 
  // if the codon is complete, otherwise return the rest of the
  // codons
  // same as class template
  public ILoString convertRNAHelper(String base, ILoString codons) {
    /* Fields of Parameters if ILoString is not empty:
     * ... codons.first ... - String
     * ... codons.rest ... - ILoString 
     * 
     * Parameters:
     * ... base ... - String
     * ... codons ... - ILoString
     * 
     * Methods of Parameters:
     * ... this.convertRNAHelper(String, ILoString) ... - ILoString
     * 
     */
    if (base.length() == 3) {
      return new ConsLoString(base, codons);
    } return codons;
  }
  
  // returns an empty codon
  // same as class template
  public ILoString convertRNA() {
    return new MtLoString();
  }

  // returns the current list of list of proteins if the current protein
  // is empty, otherwise creates a new list of list of proteins
  // same as class template
  public ILoLoString translateHelper(ILoString proteins, ILoLoString proteinsSoFar) {
    /* Fields of Parameters if proteins or proteinsSoFar is not empty:
     * ... proteins.first ... - String
     * ... proteins.rest ... - ILoString
     * ... proteinsSoFar.first ... - ILoString
     * ... proteinsSoFar.rest ... - ILoLoString
     * 
     * Parameters:
     * ... proteins ... - ILoString
     * ... proteinsSoFar... - ILoLoString
     * 
     * Methods of Parameters:
     * ... this.checkProteins() ... - boolean
     * ... this.translateHelper(ILoString, ILoLoString) ... - ILoLoString
     * 
     */
    if (proteins.checkProtein().equals("")) {
      return proteinsSoFar;
    } return new ConsLoLoString(proteins, proteinsSoFar);
  }

  // returns list of list of proteins
  // same as class template
  public ILoLoString translate() {
    return new MtLoLoString();
  }
}

// represents a list of string containing strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /* CLASS TEMPLATE:
   * Fields:
   * ... this.first ... - String
   * ... this.rest ... - ILoString
   * 
   * Methods of Fields:
   * ... this.first.convertRNA(ILoString, ILoLoString) ... - ILoLoString
   * ... this.rest.convertRNA(ILoString, ILoLoString) ... - ILoLoString
   * 
   * Methods:
   * ... this.stopCodon(String) ... - boolean
   * ... this.checkProtein() ... - String
   * ... this.returnRNA(String) ... - ILoString
   * ... this.convertRNAHelper(String, ILoString) ... - ILoString
   * ... this.convertRNA() ... - ILoString
   * ... this.translateHelper(ILoString, ILoLoString)... - ILoLoString
   * ... this.translate()... - ILoLoString

   */
  
  // returns true if base is a stop codon
  // same as class template
  public boolean stopCodon(String base) {
    if (base.equals("UAG") ||
        base.equals("UAA") ||
        base.equals("UGA")) {
      return true;
    } return false;
  }
  
  // returns list of codons and accumulates the base codon
  // if it has not reached 3
  // same as class template
  public ILoString convertRNAHelper(String base, ILoString codons) {
    /*
     * Fields of Parameters if proteins or proteinsSoFar is not empty:
     * ... codons.first ... - String
     * ... codons.rest ... - ILoString
     * 
     * Parameters:
     * ... base ... - String
     * ... codons ... - ILoString
     * 
     * Methods of Parameters:
     * ... this.checkProteins() ... - boolean
     * ... this.translateHelper(ILoString, ILoLoString) ... - ILoLoString
     * 
     */
    if (base.length() < 3) {
      return this.rest.convertRNAHelper(base + this.first, codons);
    }
    return this.rest.convertRNAHelper(this.first, new ConsLoString(base, codons));
  }

  // returns a list of codons from RNA
  // same as class template
  public ILoString convertRNA() {
    return this.convertRNAHelper("", new MtLoString());
  }


  // returns a list of list of proteins, 
  // accumulating list of list of proteins
  // and creating a new protein if stop codon is
  // found
  // same as class template
  public ILoLoString translateHelper(ILoString proteins, ILoLoString proteinsSoFar) { 
    /* Fields of Parameters:
     * ... proteins.first ... - String
     * ... proteins.rest ... - ILoString
     * ... proteinsSoFar.first ... - ILoString
     * ... proteinsSoFar.rest ... - ILoLoString
     * 
     * Parameters:
     * ... proteins ... - ILoString
     * ... proteinsSoFar... - ILoLoString
     * 
     * Methods of Parameters:
     * ... this.checkProteins() ... - boolean
     * ... this.translateHelper(ILoString, ILoLoString) ... - ILoLoString
     * 
     */
    if (stopCodon(this.first)) {                                                                                                                                                                                                                                                                                                                                     
      return this.rest.translateHelper(new MtLoString(), new ConsLoLoString(proteins, proteinsSoFar));
    }
    return this.rest.translateHelper(new ConsLoString(this.first, proteins), proteinsSoFar);
  }

  // returns list of list of proteins
  // same as class template
  public ILoLoString translate() {
    return this.convertRNA().translateHelper(new MtLoString(), new MtLoLoString());
  }
  
  // returns protein's codons 
  // same as class template
  public String checkProtein() {
    return this.first + this.rest.checkProtein();
  }
}

//represents a list of list of strings
interface ILoLoString {}

// represents an empty list of list of strings
class MtLoLoString implements ILoLoString {
  MtLoLoString() {}

}

// represents a list of list of strings containing lists of strings
class ConsLoLoString implements ILoLoString {
  ILoString first;
  ILoLoString rest;

  ConsLoLoString(ILoString first, ILoLoString rest) {
    this.first = first;
    this.rest = rest;
  }

}

// examples of RNA
class ExamplesRNA {

  // examples of RNA sequence
  ILoString empty = new MtLoString();
  ILoString RNA1 = new ConsLoString("U", (new ConsLoString("C", new ConsLoString("A", new ConsLoString("G", 
      new ConsLoString("A",  new ConsLoString("U", new ConsLoString("A", new ConsLoString("G", this.empty)))))))));
  ILoString RNA2 = new ConsLoString("A", new ConsLoString("U", this.empty));
  ILoString RNA3 = new ConsLoString("A", this.empty);
  ILoString RNA4 = new ConsLoString("U", new ConsLoString("C", new ConsLoString("A", new ConsLoString("U",
      new ConsLoString("A", new ConsLoString("G", this.empty))))));
  ILoString RNA5 = new ConsLoString("U", new ConsLoString("C", new ConsLoString("A", new ConsLoString("U",
      new ConsLoString("A", new ConsLoString("G", new ConsLoString("U", new ConsLoString("C",
          new ConsLoString("A", new ConsLoString("U", new ConsLoString("C", this.empty)))))))))));
  ILoString RNA6 = new ConsLoString("A", new ConsLoString("U", new ConsLoString("G", 
      new ConsLoString("A", new ConsLoString("A", new ConsLoString("A", this.empty))))));
  
  ILoString RNA5Codons = new ConsLoString("UCA", 
      new ConsLoString("UAG", new ConsLoString("UCA", this.empty)));

  // examples of protein
  ILoString protein1 = new ConsLoString("ACA", new ConsLoString("AAG", this.empty));
  ILoString protein2 = new ConsLoString("UUG", this.empty);
  ILoString protein3 = new ConsLoString
      ("UCA", new ConsLoString("GAU", this.empty));
  ILoString protein4 = new ConsLoString("ACA", new ConsLoString("AAG", new ConsLoString("UUG", this.empty)));

  // examples of lists of proteins
  ILoLoString emptyListOfProtein = new MtLoLoString();
  ILoLoString listOfProtein1 = new ConsLoLoString(this.protein1, new ConsLoLoString(this.protein2, this.emptyListOfProtein));
  ILoLoString listOfProtein2 = new ConsLoLoString(this.protein3, this.emptyListOfProtein);

  // tests translateHelper method
  boolean testTranslateHelper(Tester t) {
    return t.checkExpect(this.RNA5Codons.translateHelper(new MtLoString(), new MtLoLoString()), new ConsLoLoString(new ConsLoString("UCA", this.empty), 
        new ConsLoLoString(new ConsLoString("UCA", this.empty), this.emptyListOfProtein)));
    }

  // tests translate method
  boolean testTranslate(Tester t) {
    return
      t.checkExpect(this.empty.translate(), this.emptyListOfProtein) 
      && t.checkExpect(this.RNA1.translate(), new ConsLoLoString(this.protein3,
          this.emptyListOfProtein))
      && t.checkExpect(this.RNA2.translate(), this.emptyListOfProtein)
      && t.checkExpect(this.RNA3.translate(), this.emptyListOfProtein)
      && t.checkExpect(this.RNA4.translate(), new ConsLoLoString(new ConsLoString("UCA", this.empty), this.emptyListOfProtein))
      && t.checkExpect(this.RNA5.translate(), new ConsLoLoString(new ConsLoString("UCA", this.empty), 
      new ConsLoLoString(new ConsLoString("UCA", this.empty), this.emptyListOfProtein)))
      && t.checkExpect(this.RNA6.translate(), new ConsLoLoString(new ConsLoString("AUG", new ConsLoString("AAA", this.empty)), 
          this.emptyListOfProtein));
    }

  // tests the convertRNAHelper method
  boolean testConvertRNAHelper(Tester t) {
    return t.checkExpect(this.empty.convertRNAHelper("", this.empty), this.empty)
        && t.checkExpect(this.RNA1.convertRNAHelper("", this.empty), 
            new ConsLoString("GAU", new ConsLoString("UCA", this.empty)))
        && t.checkExpect(this.RNA2.convertRNAHelper("", this.empty), this.empty)
        && t.checkExpect(this.RNA4.convertRNAHelper("", this.empty), new ConsLoString("UAG", new ConsLoString("UCA", this.empty)))
        && t.checkExpect(this.RNA5.convertRNAHelper("", this.empty), new ConsLoString("UCA", 
           new ConsLoString("UAG", new ConsLoString("UCA", this.empty))))
        && t.checkExpect(this.RNA6.convertRNAHelper("", this.empty), new ConsLoString("AAA", new ConsLoString("AUG", this.empty)));
  }

  // tests the convertRNA method
  boolean testConvertRNA(Tester t) {
    return t.checkExpect(this.empty.convertRNA(), this.empty)
        && t.checkExpect(this.RNA1.convertRNA(), 
            new ConsLoString("GAU", new ConsLoString("UCA", this.empty)))
        && t.checkExpect(this.RNA2.convertRNA(), this.empty)
        && t.checkExpect(this.RNA4.convertRNA(), new ConsLoString("UAG", new ConsLoString("UCA", this.empty)));
  }
}
