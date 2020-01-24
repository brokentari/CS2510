import tester.Tester;

interface RNA {
  
}

class Protein {
  Protein() {};
}

class ProteinExamples{
  ProteinExamples() {};
  
  boolean testExamples(Tester t) {
    return t.checkExpect(5+5, 10);
  }
}