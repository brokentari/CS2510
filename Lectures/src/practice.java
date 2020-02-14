import tester.Tester;

interface IBT {
  int generation(String s);
  int generationAcc(String s, int acc);
}

class Leaf implements IBT {
  String data;
  Leaf (String data) {
    this.data = data;
  }
  @Override
  public int generation(String s) {
    // TODO Auto-generated method stub
    if (this.data.equals(s)) {
      return 1;
    }
    return -1;
  }
  @Override
  public int generationAcc(String s, int acc) {
    // TODO Auto-generated method stub
    if (this.data.equals(s)) {
      return acc;
    }
    return -1 ;
  }
}

class Node implements IBT {
  String data;
  IBT leftChild;
  IBT rightChild;
  
  Node (String data, IBT left, IBT right) {
    this.data = data;
    this.leftChild = left;
    this.rightChild = right;
  }

  @Override
  public int generation(String s) {
    return generationAcc(s, 1);
  }

  @Override
  public int generationAcc(String s, int acc) {
    // TODO Auto-generated method stub
    if (this.data.equals(s)) {
      return acc;
    }
    else {
      return Math.max(this.leftChild.generationAcc(s, acc + 1 ),
                      this.rightChild.generationAcc(s, acc + 1));
    }
  }
}

class ExampleNodes {
  IBT tree1 = new Node("a", new Node("b", new Leaf("d"), new Leaf("e")), 
      new Node("c", new Leaf("f"), new Leaf("g")));
  
  boolean testGen(Tester t) {
    return t.checkExpect(this.tree1.generation("a"), 1)
        && t.checkExpect(this.tree1.generation("e"), 3)
        && t.checkExpect(this.tree1.generation("b"), 2)
        && t.checkExpect(this.tree1.generation("z"), -1);
  }
}