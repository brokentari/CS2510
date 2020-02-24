import java.util.Comparator;

public class Testing {
  public static void main(String[] args) {
    System.out.print("b".compareTo("a"));
  }
}


/*
 * class BooksByTitle implements Comparator<String> { public int compare(String
 * o1, String o2) { return o1.compareTo(o2); } }
 * 
 * class BooksByAuthor implements Comparator<String> { public int compare(String
 * o1, String o2) { return o1.compareTo(o2); } }
 * 
 * class BooksByPrice implements Comparator<Integer> { public int
 * compare(Integer o1, Integer o2) { return Math.max(o1, o2); } }
 */