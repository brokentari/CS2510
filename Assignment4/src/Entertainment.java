import tester.*;

interface IEntertainment {
  // compute the total price of this Entertainment
  double totalPrice();

  // computes the minutes of entertainment of this IEntertainment
  int duration();

  // produce a String that shows the name and price of this IEntertainment
  String format();

  // is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);

  // is this Magazine the same as that one?
  boolean sameMagazine(Magazine other);

  // is this TV series the same as that one?
  boolean sameTVSeries(TVSeries other);

  // is this Podcast the same as that one?
  boolean samePodcast(Podcast other);
}

abstract class AEntertainment implements IEntertainment {
  String name;
  double price;
  int installments;

  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  // computes the price of entertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  // computes the minutes of entertainment
  public int duration() {
    return this.installments * 50;
  }

  // produces a String that shows the name and price of this entertainment
  public String format() {
    return this.name + ", " + this.price + ".";
  }

  // produces false if not a magazine
  public boolean sameMagazine(Magazine that) {
    return false;
  }

  // produces false if not a tvseries
  public boolean sameTVSeries(TVSeries that) {
    return false;
  }

  // produces false if not a podcast
  public boolean samePodcast(Podcast that) {
    return false;
  }

}

class Magazine extends AEntertainment {
  String genre;
  int pages;

  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.pages = pages;
    this.genre = genre;
  }

  // computes the minutes of entertainment of this Magazine, (includes all
  // installments)
  public int duration() {
    return this.pages * 5;
  }

  public boolean sameMagazine(Magazine that) {
    return this.name.equals(that.name) && (Math.abs(this.price - that.price) < .001)
        && this.genre.equals(that.genre) && this.pages == that.pages
        && this.installments == that.installments;
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameMagazine(this);
  }
}

class TVSeries extends AEntertainment {
  String corporation;

  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  public boolean sameTVSeries(TVSeries that) {
    return this.name.equals(that.name) && (Math.abs(this.price - that.price) < .01)
        && this.installments == that.installments && this.corporation.equals(that.corporation);
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameTVSeries(this);
  }
}

class Podcast extends AEntertainment {

  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  public boolean samePodcast(Podcast that) {
    return this.name.equals(that.name) && (Math.abs(this.price - that.price) < .01)
        && this.installments == that.installments;
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.samePodcast(this);
  }
}

class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);

  // testing total price method
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001);
  }

}