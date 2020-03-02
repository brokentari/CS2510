import java.util.Comparator;

import tester.Tester;

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  /*- TEMPLATE
   * 
   * Fields:
   * title : String
   * author: String
   * price : int
   * 
   */
}

class BooksByTitle implements Comparator<Book> {
  /*- TEMPLATE
   * 
   * Methods:
   * this.compare(Book current, Book next) : int
   */
  public int compare(Book current, Book next) {
    return current.title.compareTo(next.title);
  }
}

class BooksByAuthor implements Comparator<Book> {
  /*- TEMPLATE
   * 
   * Methods:
   * this.compare(Book current, Book next) : int
   */
  public int compare(Book current, Book next) {
    return current.author.compareTo(next.author);
  }
}

class BooksByPrice implements Comparator<Book> {
  /*- TEMPLATE
   * 
   * Methods:
   * this.compare(Book current, Book next) : int
   */
  public int compare(Book current, Book next) {
    return current.price - next.price;
  }
}

interface IList<T> {
  IList<T> append(IList<T> app);

  boolean sameList(Comparator<T> comp, IList<T> t);

  int length();

  T getFirst();

  IList<T> getRest();
}

class MtList<T> implements IList<T> {

  /*- TEMPLATE
   * 
   * Methods:
   * this.append(IList<T> app) : IList<T>
   * this.sameList(Comparator<T> comp, IList<T> t) : boolean
   * this.length() : int
   * this.getFirst() : T
   * this.getRest() : IList<T>
   *
   */

  public IList<T> append(IList<T> app) {
    return app;
  }

  public boolean sameList(Comparator<T> comp, IList<T> t) {
    return true;
  }

  public int length() {
    return 0;
  }

  public T getFirst() {
    throw new RuntimeException("There is no first of an empty list");
  }

  public IList<T> getRest() {
    throw new RuntimeException("There is no rest of an empty list");
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T data, IList<T> rest) {
    this.first = data;
    this.rest = rest;
  }

  /*- TEMPLATE
   * 
   * Fields:
   * first : T
   * rest : IList<T> 
   * 
   * Methods:
   * this.append(IList<T> app) : IList<T>
   * this.sameList(Comparator<T> comp, IList<T> t) : boolean
   * this.length() : int
   * this.getFirst() : T
   * this.getRest() : IList<T>
   * 
   * Methods for fields:
   * this.rest.append(IList<T> app) : IList<T>
   * this.rest.sameList(Comparator<T> comp, IList<T> t) : boolean
   * this.rest.length() : int 
   *
   */

  public IList<T> append(IList<T> list2) {
    return new ConsList<T>(this.first, this.rest.append(list2));
  }

  public boolean sameList(Comparator<T> comp, IList<T> t) {
    if (this.length() != t.length()) {
      return false;
    }

    return comp.compare(this.first, t.getFirst()) == 0 && this.rest.sameList(comp, t.getRest());
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public T getFirst() {
    return this.first;
  }

  public IList<T> getRest() {
    return this.rest;
  }
}

interface IBST<T> {
  ABST<T> insert(T t);

  boolean present(T t);

  T getLeftmost();

  T getLeftMostHelper(T t);

  ABST<T> getRight();

  boolean sameTree(ABST<T> input);

  boolean sameTreeAcc(ABST<T> input);

  boolean sameData(ABST<T> input);

  IList<T> buildList();

  ABST<T> getRightHelperL(ABST<T> acc, T data);

  ABST<T> getRightHelperR(ABST<T> acc);

  T getData();

  ABST<T> getLeftSide();

  ABST<T> getRightSide();
}

abstract class ABST<T> implements IBST<T> {
  Comparator<T> order;

  /*- TEMPLATE:
   * 
   * Fields:
   * order : Comparator<T>
   * 
   */
}

class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    this.order = order;
  }

  /*- TEMPLATE:
   * 
   * Fields:
   * order : Comparator<T>
   * 
   * Methods:
   * this.insert(T t) : ABST<T>
   * this.present(T t) : boolean
   * this.getLeftMost() : T
   * this.getLeftMostHelper(T t) : T
   * this.getRight() : ABST<T>
   * this.getRightHelperL(ABST<T> acc, T data) : ABST<T>
   * this.getRightHelperR(ABST<T> acc) : ABST<T> 
   * this.sameTree(ABST<T> input) : boolean 
   * this.sameTreeAcc(ABST<T> input) : boolean
   * this.buildList() : IList<T>
   * this.sameData(ABST<T> input) : boolean
   * this.getData() : T
   * this.getLeftSIde() : ABST<T>
   * this.getRightSide() : ABST<T>
   * 
   */

  public ABST<T> insert(T t) {
    return new Node<T>(this.order, t, this, this);
  }

  public boolean present(T t) {
    return false;
  }

  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  public T getLeftMostHelper(T t) {
    return t;
  }

  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  public ABST<T> getRightHelperL(ABST<T> acc, T data) {
    return acc;
  }

  public ABST<T> getRightHelperR(ABST<T> acc) {
    return acc;
  }

  public boolean sameTree(ABST<T> input) {
    return true;
  }

  public boolean sameTreeAcc(ABST<T> input) {
    return true;
  }

  public IList<T> buildList() {
    return new MtList<T>();
  }

  public boolean sameData(ABST<T> input) {
    return true;
  }

  public T getData() {
    throw new RuntimeException("No data in an empty tree");
  }

  public ABST<T> getLeftSide() {
    throw new RuntimeException("No left side in an empty tree");
  }

  public ABST<T> getRightSide() {
    throw new RuntimeException("No right side in an empty tree");
  }

}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    this.order = order;
    this.data = data;
    this.left = left;
    this.right = right;
  }

  /*- TEMPLATE:
   * 
   * Fields:
   * data : T
   * left : ABST<T>
   * right : ABST<T>
   * order : Comparator<T>
   * 
   * Methods:
   * this.insert(T t) : ABST<T>
   * this.present(T t) : boolean
   * this.getLeftMost() : T
   * this.getLeftMostHelper(T t) : T
   * this.getRight() : ABST<T>
   * this.getRightHelperL(ABST<T> acc, T data) : ABST<T>
   * this.getRightHelperR(ABST<T> acc) : ABST<T> 
   * this.sameTree(ABST<T> input) : boolean 
   * this.sameTreeAcc(ABST<T> input) : boolean
   * this.buildList() : IList<T>
   * this.sameData(ABST<T> input) : boolean
   * this.getData() : T
   * this.getLeftSIde() : ABST<T>
   * this.getRightSide() : ABST<T>
   * 
   * Methods for FIelds:
   * this.left.insert(T t) : ABST<T>
   * this.right.insert(T t) : ABST<T>
   * this.right.present(T t) : boolean
   * this.left.present(T t) : boolean
   * order.compare(T current, T next) : int
   * this.left.getLeftMostHelper(T t) : T 
   * this.right.getRightHelperR(ABST<T> acc, T data) : ABST<T>
   * this.left.getRightHelperR(ABST<T> acc, T data) : ABST<T>
   * this.sameTreeAcc(ABST<T> input) : boolean
   * this.buildList() : IList<T>
   * this.left.sameTreeAcc(ABST<T> input) : boolean
   * this.right.sameTreeAcc(ABST<T> input) : boolean
   */

  public ABST<T> insert(T t) {
    if (order.compare(this.data, t) > 0) {
      return new Node<T>(order, this.data, this.left.insert(t), right);
    }
    else {
      return new Node<T>(order, this.data, this.left, this.right.insert(t));
    }
  }

  public boolean present(T t) {
    return (order.compare(this.data, t) == 0 || this.right.present(t) || this.left.present(t));
  }

  public T getLeftmost() {
    return this.left.getLeftMostHelper(this.data);
  }

  public T getLeftMostHelper(T t) {
    return this.left.getLeftMostHelper(this.data);
  }

  public ABST<T> getRight() {
    return this.right
        .getRightHelperR(this.left.getRightHelperL(new Leaf<T>(this.order), this.data));
  }

  public ABST<T> getRightHelperL(ABST<T> acc, T data) {
    acc = acc.insert(data);
    return this.left.getRightHelperL(this.right.getRightHelperR(acc), this.data);
  }

  public ABST<T> getRightHelperR(ABST<T> acc) {
    acc = acc.insert(this.data);
    return this.left.getRightHelperR(this.right.getRightHelperR(acc));
  }

  public boolean sameTree(ABST<T> input) {
    if (this.equals(input)) {
      return true;
    }
    else if (this.sameData(input)) {
      return this.sameTreeAcc(input);
    }

    return false;
  }

  public boolean sameTreeAcc(ABST<T> input) {
    if (this.order.compare(this.data, input.getData()) == 0) {
      return this.left.sameTreeAcc(input.getLeftSide())
          && this.right.sameTreeAcc(input.getRightSide());
    }
    else {
      return false;
    }
  }

  public IList<T> buildList() {
    return this.left.buildList().append(new ConsList<T>(this.data, new MtList<T>()))
        .append(this.right.buildList());
  }

  public boolean sameData(ABST<T> input) {
    IList<T> originalList = this.buildList();
    IList<T> inputList = input.buildList();

    return originalList.sameList(this.order, inputList);
  }

  public T getData() {
    return this.data;
  }

  public ABST<T> getLeftSide() {
    return this.left;
  }

  public ABST<T> getRightSide() {
    return this.right;
  }
}

class ExamplesBST {
  Comparator<Book> byTitle = new BooksByTitle();
  Comparator<Book> byAuthor = new BooksByAuthor();
  Comparator<Book> byPrice = new BooksByPrice();

  Book gatsby = new Book("The Great Gatsby", "Scott Fitzgerald", 10);
  Book quixote = new Book("Don Quixote", "Miguel de Cervantes", 15);
  Book moby = new Book("Moby Dick", "Herman Melvill", 21);
  Book wp = new Book("War and Peace", "Leo Tolstoy", 20);
  Book quarantine = new Book("Quarantine", "Homer", 8);
  Book potter = new Book("Harry Potter", "", 11);
  Book lotr = new Book("Lord of the Rings", "J.R. Tolkien", 19);

  IList<Book> list1 = new ConsList<Book>(quixote,
      new ConsList<Book>(moby, new ConsList<Book>(quarantine,
          new ConsList<Book>(gatsby, new ConsList<Book>(wp, new MtList<Book>())))));
  IList<Book> list2 = new ConsList<Book>(moby, new MtList<Book>());
  IList<Book> list3 = new ConsList<Book>(quixote,
      new ConsList<Book>(potter, new ConsList<Book>(moby, new ConsList<Book>(quarantine,
          new ConsList<Book>(gatsby, new ConsList<Book>(wp, new MtList<Book>()))))));

  ABST<Book> leaf = new Leaf<Book>(byTitle);
  ABST<Book> leafPrice = new Leaf<Book>(byPrice);

  // a tree built ordered by titles
  ABST<Book> tree1Author = new Node<Book>(byTitle, moby,
      new Node<Book>(byTitle, quixote, leaf, leaf),
      new Node<Book>(byTitle, gatsby, new Node<Book>(byTitle, quarantine, leaf, leaf),
          new Node<Book>(byTitle, wp, leaf, leaf)));

  // tree built ordered by price
  ABST<Book> tree1Price = new Node<Book>(byPrice, quixote,
      new Node<Book>(byPrice, gatsby, leafPrice, leafPrice),
      new Node<Book>(byPrice, wp, new Node<Book>(byPrice, lotr, leafPrice, leafPrice),
          new Node<Book>(byPrice, moby, leafPrice, leafPrice)));

  ABST<Book> tree1_2 = new Node<Book>(byTitle, moby, new Node<Book>(byTitle, quixote, leaf, leaf),
      new Node<Book>(byTitle, gatsby, new Node<Book>(byTitle, quarantine, leaf, leaf),
          new Node<Book>(byTitle, wp, leaf, leaf)));

  ABST<Book> tree2 = new Node<Book>(byTitle, moby, leaf, leaf);

  ABST<Book> tree3 = new Node<Book>(byTitle, moby,
      new Node<Book>(byTitle, quixote, leaf, new Node<Book>(byTitle, potter, leaf, leaf)),
      new Node<Book>(byTitle, gatsby, new Node<Book>(byTitle, quarantine, leaf, leaf),
          new Node<Book>(byTitle, wp, leaf, leaf)));

  ABST<Book> tree4 = new Node<Book>(byTitle, new Book("Ender's Game", "Orson Scott Card", 6),
      new Node<Book>(byTitle, new Book("City of Bones", "Cassandra Clare", 12),
          new Node<Book>(byTitle, new Book("Alexander Hamilton", "Ron Chernow", 13),
              new Leaf<Book>(byTitle), new Leaf<Book>(byTitle)),
          new Leaf<Book>(byTitle)),
      new Node<Book>(byTitle, new Book("Handmaid's Tale", "Margaret Atwood", 9),
          new Leaf<Book>(byTitle), new Leaf<Book>(byTitle)));

  ABST<Book> tree4_2 = new Node<Book>(byTitle, new Book("Ender's Game", "Orson Scott Card", 6),
      new Node<Book>(byTitle, new Book("City of Bones", "Cassandra Clare", 12),
          new Node<Book>(byTitle, new Book("Alexander Hamilton", "Ron Chernow", 13),
              new Leaf<Book>(byTitle), new Leaf<Book>(byTitle)),
          new Leaf<Book>(byTitle)),
      new Node<Book>(byTitle, new Book("Handmaid's Tale", "Margaret Atwood", 9),
          new Leaf<Book>(byTitle), new Leaf<Book>(byTitle)));

  boolean testGetLeftmost(Tester t) {
    return t.checkExpect(tree1Author.getLeftmost(), quixote)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"), leaf,
            "getLeftmost")
        && t.checkExpect(tree2.getLeftmost(), moby) && t.checkExpect(tree3.getLeftmost(), quixote)
        && t.checkExpect(tree4.getLeftmost(), new Book("Alexander Hamilton", "Ron Chernow", 13))
        && t.checkExpect(tree1Price.getLeftmost(), gatsby);
  }

  boolean testGetRight(Tester t) {
    return t.checkExpect(tree1Author.getRight(),
        new Node<Book>(byTitle, moby, leaf,
            new Node<Book>(byTitle, gatsby, new Node<Book>(byTitle, quarantine, leaf, leaf),
                new Node<Book>(byTitle, wp, leaf, leaf))))
        && t.checkException(new RuntimeException("No right of an empty tree"), leaf, "getRight")
        && t.checkExpect(tree1Price.getRight(),
            new Node<Book>(byPrice, quixote, leafPrice,
                new Node<Book>(byPrice, wp, new Node<Book>(byPrice, lotr, leafPrice, leafPrice),
                    new Node<Book>(byPrice, moby, leafPrice, leafPrice))));
  }

  boolean testPresent(Tester t) {
    return t.checkExpect(leaf.present(lotr), false)
        && t.checkExpect(tree1Author.present(quarantine), true)
        && t.checkExpect(tree1Author.present(lotr), false)
        && t.checkExpect(tree1Author.insert(lotr).present(lotr), true);
  }

  boolean testInsert(Tester t) {
    return t.checkExpect(tree1Author.insert(potter), tree3)
        && t.checkExpect(leaf.insert(moby), tree2)
        && t.checkExpect(tree2.insert(quixote),
            new Node<Book>(byTitle, moby, new Node<Book>(byTitle, quixote, leaf, leaf), leaf))
        && t.checkExpect(tree1Price.insert(potter),
            new Node<Book>(byPrice, quixote,
                new Node<Book>(byPrice, gatsby, leafPrice,
                    new Node<Book>(byPrice, potter, leafPrice, leafPrice)),
                new Node<Book>(byPrice, wp, new Node<Book>(byPrice, lotr, leafPrice, leafPrice),
                    new Node<Book>(byPrice, moby, leafPrice, leafPrice))));
  }

  boolean testsameData(Tester t) {
    return t.checkExpect(tree1Author.sameData(tree1_2), true)
        && t.checkExpect(tree1Author.sameData(tree4), false)
        && t.checkExpect(tree2.sameData(leaf), false) && t.checkExpect(leaf.sameData(leaf), true)
        && t.checkExpect(tree1Author.sameData(tree1Author), true)
        && t.checkExpect(tree1Author.sameData(tree1Price), false);
  }

  boolean testsameTree(Tester t) {
    return t.checkExpect(tree1Author.sameTree(tree1Author), true)
        && t.checkExpect(tree4.sameTree(tree1Author), false)
        && t.checkExpect(tree4.sameTree(tree4_2), true)
        && t.checkExpect(tree1Author.sameTree(tree1_2), true)
        && t.checkExpect(tree1Author.sameTree(tree1Price), false);
  }

  boolean testBuildList(Tester t) {
    return t.checkExpect(tree1Author.buildList(), list1) && t.checkExpect(tree2.buildList(), list2)
        && t.checkExpect(tree3.buildList(), list3)
        && t.checkExpect(leaf.buildList(), new MtList<Book>());
  }
}
