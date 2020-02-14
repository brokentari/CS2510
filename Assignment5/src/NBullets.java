import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import tester.Tester;

class Global {
  static final int WIDTH = 500;
  static final int HEIGHT = 300;
  // seconds / frame
  static final double TICK_RATE = 1 / 28;
  // time in between spawn
  static final int SHIP_FREQ = 1;
  static final int NUM_OF_SHIPS_MIN = 1;
  static final int NUM_OF_SHIPS_MAX = 3;
  static final int BULLET_RADIUS = 4;
  static final int BULLET_RADIUS_EXPL = 2;
  static final int BULLET_RADIUS_MAX = 12;
  static final Color BULLET_COLOR = Color.PINK;
  // pixels per tick
  static final int BULLET_SPEED = 8;
  static final int SHIP_RADIUS = HEIGHT / 30;
  static final Color SHIP_COLOR = Color.CYAN;
  static final int SHIP_SPEED = BULLET_SPEED / 2;
  static final int UPPER_BOUND = HEIGHT / 7;
  static final int LOWER_BOUND = HEIGHT - UPPER_BOUND;
  static final Color FONT_COLOR = Color.BLACK;
  static final int FONT_SIZE = 13;
}

interface IPred<T> {
  boolean apply(T t);
}

interface IFunc<X, Y> {
  Y apply(X x);
}

interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

interface IList<T> {
  IList<T> filter(IPred<T> pred);

  int length();

  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U, U> func, U base);

  IList<T> addElement(T t);
}

class DrawShips implements IFunc2<Ship, WorldScene, WorldScene> {
  public WorldScene apply(Ship arg1, WorldScene arg2) {
    // TODO Auto-generated method stub
    return arg1.draw(arg2);
  }
}

class MoveShips implements IFunc<Ship, Ship> {
  public Ship apply(Ship s) {
    return s.move();
  }
}

class MoveBullets implements IFunc<Bullet, Bullet> {
  public Bullet apply(Bullet b) {
    return b.move();
  }
}

//y =(Math.random() * (Global.LOWER_BOUND - Global.UPPER_BOUND)) + Global.UPPER_BOUND
class Bullet {
  int radius;
  int speed;
  Color color;
  int x;
  int y;

  Bullet() {
    this.radius = Global.BULLET_RADIUS;
    this.speed = Global.BULLET_SPEED;
    this.color = Global.BULLET_COLOR;
    this.x = 100;
    this.y = 100;
  }

  Bullet(int x, int y, int speed, int radius, Color color) {
    this.radius = radius;
    this.speed = speed;
    this.color = color;
    this.y = y;
    this.x = x;
  }

  Bullet move() {
    return new Bullet(this.x, this.y - this.speed, this.speed, this.radius, this.color);
  }

  WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), this.x,
        this.y);
  }
}

class Ship {
  int radius;
  int speed;
  Color color;
  int x;
  int y;

  Ship() {
    Random randNum = new Random();
    int choice = randNum.nextInt(1);
    int x;
    if (choice == 1) {
      x = 500;
    }
    else {
      x = 0;
    }
    this.x = x;
    this.y = randNum.nextInt(214) + 34;
    this.color = Global.SHIP_COLOR;
    this.radius = Global.SHIP_RADIUS;
    this.speed = Global.SHIP_SPEED;
  }

  Ship(int x, int y, int speed, int radius, Color color) {
    this.radius = radius;
    this.speed = speed;
    this.color = color;
    this.y = y;
    this.x = x;
  }

  Ship move() {
    return new Ship(this.x + this.speed, y, this.speed, this.radius, this.color);
  }

  WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), this.x,
        this.y);
  }
}

class MtList<T> implements IList<T> {
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  public int length() {
    return 0;
  }

  @Override
  public <U> IList<U> map(IFunc<T, U> f) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }

  @Override
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    // TODO Auto-generated method stub
    return base;
  }

  @Override
  public IList<T> addElement(T t) {
    // TODO Auto-generated method stub
    return this;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public IList<T> filter(IPred<T> pred) {
    // TODO Auto-generated method stub
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  @Override
  public int length() {
    // TODO Auto-generated method stub
    return 1 + this.rest.length();
  }

  @Override
  public <U> IList<U> map(IFunc<T, U> f) {
    // TODO Auto-generated method stub
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  @Override
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    // TODO Auto-generated method stub
    return func.apply(this.first, this.rest.foldr(func, base));
  }

  @Override
  public IList<T> addElement(T t) {
    // TODO Auto-generated method stub
    return new ConsList<T>(t, this);
  }
}

class BulletWorld extends World {
  IList<Ship> ships;
  IList<Bullet> bullets;
  int ticks;

  BulletWorld() {
    ticks = 0;

    this.bullets = new MtList<Bullet>();
    // new Bullet(150, 320, Global.BULLET_SPEED, Global.BULLET_RADIUS,
    // Global.BULLET_COLOR);
    this.ships = new ConsList<Ship>(new Ship(), new MtList<Ship>());
  }

  BulletWorld(IList<Ship> s, IList<Bullet> b, int ticks) {
    this.ships = s;
    this.bullets = b;
    this.ticks = ticks;
  }

  @Override
  public WorldScene makeScene() {
    // TODO Auto-generated method stub
    WorldScene scene = this.getEmptyScene();

    return ships.foldr(new DrawShips(), scene);
  }

  public World onTick() {
    if ((this.ticks % 28) == 0) {
      Random randNum = new Random();
      int num_of_ship = randNum.nextInt(Global.NUM_OF_SHIPS_MAX);
      for (int i = 0; i < num_of_ship; i++) {
        this.ships = this.ships.addElement(new Ship());
      }
    }
    return new BulletWorld(ships.map(new MoveShips()), bullets.map(new MoveBullets()),
        this.ticks + 1);
  }

  public World onKeyReleased(String key) {
    switch (key) {
    case " ":
      return new BulletWorld(this.ships, this.bullets.addElement(new Bullet()), this.ticks);
    }
    return this;
  }
}

//the "test", that is the runner of our game.
class RunBulletWorld {
  boolean testGo(Tester t) {
    // create my world. This will initialize everything
    BulletWorld myWorld = new BulletWorld();

    // Start the game with a big bang. The third argument is the number of seconds
    // between ticks. So currently the game
    // progresses at 200 ticks/second. This is probably higher than what is
    // supported by the frame refresh rate by
    // the image library, which is why the animation looks slower than it should.
    return myWorld.bigBang(Global.WIDTH, Global.HEIGHT, 0.035);
  }
}