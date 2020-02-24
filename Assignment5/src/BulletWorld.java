import java.util.Random;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;

class BulletWorld extends World {
  IList<AGamePiece> ships;
  IList<AGamePiece> bullets;
  int ticks;
  int startingBullets;
  int destroyedShips;

  BulletWorld() {
    ticks = 0;

    this.bullets = new MtList<AGamePiece>();
    this.ships = new ConsList<AGamePiece>(new Ship(), new MtList<AGamePiece>());
  }

  BulletWorld(IList<AGamePiece> s, IList<AGamePiece> b, int ticks, int startBullets,
      int destroyedShips) {
    this.ships = s;
    this.bullets = b;
    this.ticks = ticks;
    this.startingBullets = startBullets;
    this.destroyedShips = destroyedShips;
  }

  BulletWorld(int startBullets) {
    this();
    this.startingBullets = startBullets;
  }

  /*- TEMPLATE:
   *
   * Fields:
   * this.ships : IList<AGamePiece>
   * this.bullets : IList<AGamePiece>
   * this.ticks : int
   * this.startingBullets : int
   * this.destroyedShips : int
   *
   * Methods:
   * this.makeScene() : WorldScene
   * this.onTick() : World
   * this.onKeyReleased(String key) : World
   * this.worldEnds() : WorldEnd
   *
   * Methods for Fields:
   * this.ships/bullets.filter(IPred<T> pred) : IList<T>
   * this.ships/bullets.map(IFunc<T, U> f) : IList<U>
   * this.ships/bullets.foldr(IFunc<T, U, U> func, U base) : U
   * this.ships/bullets.length() : int
   * this.ships/bullets.addElement(T t) : IList<T>
   * this.ships/bullets.filter2(ICompare<T> func, T t) : IList<T>
   * this.ships/bullets.append(IList<T> list2) : IList<T>
   * this.ships/bullets.generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base) : U
   */

  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    TextImage bulletsText = new TextImage("bullets left: " + Integer.toString(this.startingBullets)
        + "; " + "ships destroyed: " + this.destroyedShips, Global.FONT_SIZE, Global.FONT_COLOR);

    return bullets.foldr(new DrawPiece(), ships.foldr(new DrawPiece(), scene))
        .placeImageXY(bulletsText, 130, 280);
  }

  public World onTick() {
    if ((this.ticks % 28) == 0) {
      Random rand = new Random();
      int numOfShips = rand.nextInt(Global.NUM_OF_SHIPS_MAX);
      int xPosition = 0;
      int speed;
      for (int i = 0; i < numOfShips; i++) {
        int sideOfScreen = rand.nextInt(2);
        if (sideOfScreen == 0) {
          xPosition = rand.nextInt(13) - 20;
          speed = Global.SHIP_SPEED;
        }
        else {
          xPosition = rand.nextInt(10) + 500;
          speed = -Global.SHIP_SPEED;
        }
        this.ships = this.ships.addElement(new Ship(xPosition, rand.nextInt(214) + 34, speed));
      }
    }

    IList<AGamePiece> bulletsInScreen = bullets.filter(new IsInsideScreen()).map(new MovePiece());
    IList<AGamePiece> shipsInScreen = ships.filter(new IsInsideScreen()).map(new MovePiece());
    IList<AGamePiece> newShipsInScreen = bulletsInScreen.foldr(new DestroyPiece(), shipsInScreen);
    IList<AGamePiece> newBulletsInScreen = shipsInScreen.foldr(new DestroyPiece(), bulletsInScreen);

    this.destroyedShips = this.destroyedShips
        + (shipsInScreen.length() - newShipsInScreen.length());

    return new BulletWorld(newShipsInScreen, newBulletsInScreen, this.ticks + 1,
        this.startingBullets, this.destroyedShips);
  }

  public World onKeyReleased(String key) {
    if ((startingBullets != 0) && (key.equals(" "))) {
      return new BulletWorld(this.ships, this.bullets.addElement(new Bullet()), this.ticks,
          this.startingBullets - 1, destroyedShips);
    }
    return this;
  }

  public WorldEnd worldEnds() {
    if (this.startingBullets == 0 && this.bullets.length() == 0) {
      return new WorldEnd(true, this.makeScene());
    }
    return new WorldEnd(false, this.makeScene());
  }
}