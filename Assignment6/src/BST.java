import java.util.Comparator;

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

interface IBST<T> {
  ABST<T> insert(T t);
  
  boolean present(T t);
  
  T getLeftMost();
  
  T getLeftMostHelper();
  
  ABST<T> getRight();
  
  ABST<T> getRightHelperR(); 
  
  ABST<T> getRightHelperL();
}

abstract class ABST<T> implements IBST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;
 
  Comparator<T> order;
}

class Leaf<T> extends ABST<T> {

  Leaf(Comparator<T> order, T data) {
    this.order = order;
    this.data = data;
    this.left = null;
    this.right = null;
  }

  public ABST<T> insert(T t) {
    if (order.compare(this.data, t) > 0) {
      return new Node<T>(order, this.data, new Leaf<T>(order, t), this.right);
    }
    else {
      return new Node<T>(order, this.data, this.left, new Leaf<T>(order, t));
    }
  }

  public boolean present(T t) {
    return (this.order.compare(this.data, t)) == 0;
  }

  public T getLeftMost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  public T getLeftMostHelper() {
    return this.data;
  }

  @Override
  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  public ABST<T> getRightHelperR() {
    return this;
  }

  public ABST<T> getRightHelperL() {
    return null;
  }
}

class Node<T> extends ABST<T> {
  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    this.order = order;
    this.data = data;
    this.left = left;
    this.right = right;
  }
  // t - data
  public ABST<T> insert(T t) {
    if (order.compare(data, t) > 0) {
      return new Node<T>(order, this.data, this.left.insert(t), right);
    }
    else {
      return new Node<T>(order, this.data, this.left, this.right.insert(t));
    }
  }
  
  public boolean present(T t) {
    return (order.compare(this.data, t) == 0 ||
        this.right.present(t) || this.left.present(t));
  }
  
  public T getLeftMost() {
    return this.left.getLeftMostHelper();
  }
  
  public T getLeftMostHelper() {
    return this.left.getLeftMostHelper();
  }
  
  public ABST<T> getRight() {
    return new Node<T>(this.order, this.data, this.left.getRightHelperL(), this.right.getRightHelperR());
  }
  
  public ABST<T> getRightHelperR() {
    return new Node<T>(this.order, this.data, this.left.getRightHelperR(), this.right.getRightHelperR());
  }
  
  public ABST<T> getRightHelperL() {
    return new Node<T>(this.order, this.data, this.left.getRightHelperL(), this.right.getRightHelperR());
  }
}

class BooksByTitle implements Comparator<Book> {
  public int compare(Book current, Book next) {
    return current.title.compareTo(next.title);
  }
}

class BooksByAuthor implements Comparator<Book> {
  public int compare(Book current, Book next) {
    return current.author.compareTo(next.author);
  }
}

class BooksByPrice implements Comparator<Book> {
  public int compare(Book current, Book next) {
    return next.price - current.price;
  }
}

class ExamplesBST {
  Book gatsby = new Book("The Great Gatsby", "Scott Fitzgerald", 10);
  Book quixote = new Book("Don Quixote", "Miguel de Cervantes", 15);
  Book moby = new Book("Moby Dick", "Herman Melvill", 5);
  Book wp = new Book("War and Peace", "Leo Tolstoy", 20);
  Book odyssey = new Book("The Odyssey", "Homer", 8);
}