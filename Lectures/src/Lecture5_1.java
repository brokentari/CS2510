// represents a list of Paintings
interface ILofPainting {
  //counts the painting in this ILofPainting
  int count();
  
  // get all of the paintings by the the artist with the given name
  ILofPainting getPaintingsByName(String name);
}

//a class to represent a non-empty list of paintings
class MtLofPainting implements ILofPainting {
  MtLofPainting() { }

  @Override
  public int count() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ILofPainting getPaintingsByName(String name) {
    // TODO Auto-generated method stub
    return new MtLofPainting();
  }
}

class ConsLofPainting implements ILofPainting {
  Painting first;
  ILofPainting rest;
  
  ConsLofPainting (Painting first, ILofPainting rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* fields:
   *  this.first ... Painting
   *  this.rest ...  ILofPainting
   * methods: 
   *  this.count() ... Integer
   * methods for fields:
   *  this.rest.count()  ... Integer
   */

  @Override
  public int count() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ILofPainting getPaintingsByName(String name) {
    // TODO Auto-generated method stub
    if (this.first.paintedBy(name)) {
      return new ConsLofPainting(this.first, this.rest.getPaintingsByName(name));
    }
    else { 
      return this.rest.getPaintingsByName(name);
    }
  }
}

class Painting {
  Artist artist;
  String title;
  int year;
  int price; // in millions of dollars
  
  Painting(Artist artist, String title, int year, int price) {
    this.artist = artist;
    this.title = title;
    this.year = year;
    this.price = price;
  }
  
  /* fields:
   *  this.artist ... Artist
   *  this.title ... String
   *  this.year ... int
   *  this.price ... int
   * methods: 
   *  
   * methods for fields: 
   */
  
  boolean paintedBy(String name) {
    return this.artist.hasName(name);
  }
}


//a class to represent Artists
class Artist {
  String name;
  int yob;
  
  Artist(String name, int yob) {
    this.name = name;
    this.yob = yob;
  }
  
  /* fields:
   *  this.name ... String
   *  this.yob ... int
   * methods: 
   *  
   */
  
  boolean hasName(String name) {
    return this.name.equals(name);
  }
}


class ExamplesPainting {
  Artist banksy= new Artist("Banksy", 1973);
  Painting girl = new Painting(this.banksy, "Girl with Balloon", 2002, 23);
  Painting flower = new Painting(this.banksy, "Flower Thrower", 2005, 35);
  Artist monet = new Artist("Claude Monet", 1840);
  Painting waterlilies = new Painting(this.monet, "Water Lily Pond", 1900, 50);
  Painting poppies = new Painting(this.monet, "Poppies", 1873, 55);
}

