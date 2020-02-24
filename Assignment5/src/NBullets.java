import javalib.funworld.World;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import tester.Tester;

class RunBulletWorld {
  IList<AGamePiece> emptyList = new MtList<AGamePiece>();
  AGamePiece bullet1 = new Bullet();
  AGamePiece bullet2 = new Bullet();
  AGamePiece bullet3 = new Bullet(100, 100, 10, 50, Global.BULLET_COLOR, 90, 2);

  AGamePiece ship1 = new Ship(bullet1.x, bullet1.y, Global.SHIP_SPEED, Global.SHIP_RADIUS,
      Global.SHIP_COLOR);
  AGamePiece ship2 = new Ship();
  AGamePiece ship3 = new Ship(600, 700, 1);

  IList<AGamePiece> bullets_1 = new ConsList<AGamePiece>(bullet1, emptyList);
  IList<AGamePiece> bullets_2 = new ConsList<AGamePiece>(bullet2, emptyList);

  IList<AGamePiece> ships_1 = new ConsList<AGamePiece>(ship1, emptyList);
  IList<AGamePiece> ships_2 = new ConsList<AGamePiece>(ship2, emptyList);

  // testing for gamepiece methods
  boolean testAGamePieceMethods(Tester t) {
    World testingWorld = new BulletWorld();
    // tests for moving AGamePiece
    return t.checkExpect(bullet1.move(), new Bullet(250, 297, 8, 4, bullet1.color, 90, 1))
        && t.checkExpect(ship1.move(), new Ship(254, 305, ship1.speed))

        // tests for cheking if in contact with another piece
        && t.checkExpect(bullet1.inContactWith(ship3), false)
        && t.checkExpect(ship1.inContactWith(bullet1), true)

        // tests for generatating debris when exploding
        && t.checkExpect(ship1.explodes(), emptyList)
        && t.checkExpect(bullet1.explodes(),
            new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 180, 2),
                new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 0, 2),
                    emptyList)))

        // tests for drawing AGamePiece
        && t.checkExpect(ship2.draw(testingWorld.getEmptyScene()), testingWorld.getEmptyScene())
        && t.checkExpect(bullet3.draw(testingWorld.getEmptyScene()), testingWorld.getEmptyScene()
            .placeImageXY(new CircleImage(50, OutlineMode.SOLID, Global.BULLET_COLOR), 100, 100));
  }

  // testsing for function objects
  boolean testFunctionObjects(Tester t) {
    // tests for Overlap()
    return t.checkExpect(ships_1.filter2(new Overlap(), bullet1), emptyList)
        && t.checkExpect(ships_2.filter2(new Overlap(), bullet1), ships_2)

        // tests for GenerateBullets()
        && t.checkExpect(ships_1.foldr(new GenerateBullets(), emptyList), new MtList<AGamePiece>())
        && t.checkExpect(bullets_1.foldr(new GenerateBullets(), emptyList),
            new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 180, 2),
                new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 0, 2),
                    emptyList)))

        // tests for DestroyPiece()
        && t.checkExpect(ships_1.foldr(new DestroyPiece(), bullets_1),
            new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 180, 2),
                new ConsList<AGamePiece>(new Bullet(250, 305, 8, 6, Global.BULLET_COLOR, 0, 2),
                    emptyList)))
        && t.checkExpect(bullets_1.foldr(new DestroyPiece(), ships_1), emptyList)
        && t.checkExpect(ships_2.foldr(new DestroyPiece(), bullets_1), bullets_1)
        && t.checkExpect(bullets_1.foldr(new DestroyPiece(), ships_2), ships_2)

        // tests for OutOfScreenPiece()
        && t.checkExpect(new IsInsideScreen().apply(bullet1), true)
        && t.checkExpect(new IsInsideScreen().apply(ship2), true)
        && t.checkExpect(new IsInsideScreen().apply(ship3), false)

        // tests for MovePiece() {
        && t.checkExpect(new MovePiece().apply(bullet1),
            new Bullet(250, 297, 8, 4, bullet1.color, 90, 1))
        && t.checkExpect(new MovePiece().apply(ship1), new Ship(254, 305, ship1.speed));
  }

  boolean testGo(Tester t) {
    BulletWorld myWorld = new BulletWorld(999);

    return myWorld.bigBang(Global.WIDTH, Global.HEIGHT, 0.035);
  }
}