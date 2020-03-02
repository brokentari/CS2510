import java.util.Comparator;

class Book1 {
  String title;
  String author;
  int price;

  Book1(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}


public class Testing {
  public static void main(String[] args) {
    Comparator<Book> byTitle = new BooksByTitle();
    Comparator<Book> byAuthor = new BooksByAuthor();
    Comparator<Book> byPrice = new BooksByPrice();

    Book gatsby = new Book("The Great Gatsby", "Scott Fitzgerald", 10);
    Book quixote = new Book("Don Quixote", "Miguel de Cervantes", 15);
    Book moby = new Book("Moby Dick", "Herman Melvill", 5);
    Book wp = new Book("War and Peace", "Leo Tolstoy", 20);
    Book odyssey = new Book("Quarantine", "Homer", 8);
    Book potter = new Book("Harry Potter", "", 11);
    Book lotr = new Book("Lord of the Rings", "J.R. Tolkien", 19);
    
    System.out.print(byTitle.compare(gatsby, null));
  }
}


///*
// * class BooksByTitle implements Comparator<String> { public int compare(String
// * o1, String o2) { return o1.compareTo(o2); } }
// * 
// * class BooksByAuthor implements Comparator<String> { public int compare(String
// * o1, String o2) { return o1.compareTo(o2); } }
// * 
// * class BooksByPrice implements Comparator<Integer> { public int
// * compare(Integer o1, Integer o2) { return Math.max(o1, o2); } }
// */
//
//
//
//
//
//Leaf(Comparator<T> order, T data) {
//  this.order = order;
//  this.data = data;
//}
//
//public ABST<T> insert(T t) {
//  if (order.compare(this.data, t) > 0) {
//    return new Node<T>(order, this.data, new Leaf<T>(order, t), this.right);
//  }
//  else {
//    return new Node<T>(order, this.data, this.left, new Leaf<T>(order, t));
//  }
//}
//
//public boolean present(T t) {
//  return (this.order.compare(this.data, t)) == 0;
//}
//
//public T getLeftMost() {
//  throw new RuntimeException("No leftmost item of an empty tree");
//}
//
//public T getLeftMostHelper() {
//  return this.data;
//}
//
//public ABST<T> getRight() {
//  throw new RuntimeException("No right of an empty tree");
//}
//
//public ABST<T> getRightHelperR() {
//  return this;
//}
//
//public ABST<T> getRightHelperL() {
//  return null;
//}
//
//public boolean sameTree(ABST<T> input) {
//  if (input.data == null || this.data == null) {
//    return false;
//  }
//  return (this.order.compare(this.data, input.data)) == 0;
//}
//
//public boolean sameTreeAcc(ABST<T> input, T acc) {
//  return false;
//}
//
//public IList<T> buildList() {
//  return new ConsList<T>(this.data, new MtList<T>());
//}