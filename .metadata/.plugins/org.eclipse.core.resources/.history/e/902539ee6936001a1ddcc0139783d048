import tester.Tester;

interface IGamePiece {
  // moves this game piece on the board
  IGamePiece move();
  
}

class Spaceship {
  Location loc;
  String color;
  int time; // in seconds
  
  Spaceship (Location loc, String color, int time) {
    this.loc = loc;
    this.color = color;
    this.time = time;
  }
  
  /* fields: 
   *  this.loc ... Location
   *  this.color ... String
   *  this.time ... Integer
   * methods:
   *  this.reduceTime(double) ... Double
   *  this.move(Integer, Integer) ... Spaceship
   * methods for fields:
   *  this.loc.moveLocation(Integer, Integer) ... Location
   */
  
  // reduce the time in this Spaceship by a given percentage
  int reduceTime(int percent) {
    return this.time - this.time * percent / 100;
  }
  
  // move this Spaceship by a given x and y
  Spaceship move(int x, int y) {
    return new Spaceship(this.loc.moveLocation(x, y), this.color, this.time);
  }
}

class Location {
  int x;
  int y;
  
  Location(int x, int y) {
    this.x = x;
    this.y = y;
  }   
  
  /*
   * fields:
   *  this.x ... Integer
   *  this.y ... Integer
   * methods;
   *  this.moveLocation(Integer, Integer) ... Location
   * 
   */
  
  Location moveLocation(int x, int y) {
    return new Location(this.x + x, this.y + y);
  }
  
  double distance() {
    return Math.sqrt((Math.pow(this.x, 2) + Math.pow(this.y, 2)));
  }
}

class ExamplesGame {
  Location loc1= new Location(200, 300);
  Spaceship ship2 = new Spaceship(this.loc1, "red", 10);
  
  //test for reduce time
  
  boolean testReduce(Tester t) {
    return t.checkExpect(this.ship2.reduceTime(10), 9) &&
           t.checkExpect(this.ship2.reduceTime(50), 5);
  }
  
  //test for moving ship
  
  boolean testMove(Tester t) {
    return t.checkExpect(this.ship2.move(0, 0), this.ship2) &&
           t.checkExpect(this.ship2.move(100, 100), 
                         new Spaceship(new Location(300, 400),
                                       this.ship2.color,
                                       this.ship2.time));
  }
  
  //test for moving location
  boolean testMoveLoc(Tester t) {
    return t.checkExpect(this.loc1.moveLocation(50, 50), new Location(250, 350)) &&
           t.checkExpect(this.loc1.moveLocation(100, 100), new Location(300, 400));
  }
  
  boolean testDist(Tester t) {
    return t.checkExpect(this.loc1.distance(), 360.555);
  }
}