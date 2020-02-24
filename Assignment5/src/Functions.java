import javalib.funworld.WorldScene;

class Overlap implements ICompare<AGamePiece> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece bullet, AGamePiece ship) :  boolean
   */
  public boolean apply(AGamePiece bullet, AGamePiece ship) {
    return bullet.inContactWith(ship);
  }
}

class GenerateBullets implements IFunc2<AGamePiece, IList<AGamePiece>, IList<AGamePiece>> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece bullet, AGamePiece ship) :  IList<AGamePiece>
   */
  public IList<AGamePiece> apply(AGamePiece b, IList<AGamePiece> bullets) {
    return bullets.append(b.explodes());
  }
}

class DestroyPiece implements IFunc2<AGamePiece, IList<AGamePiece>, IList<AGamePiece>> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece piece, IList<AGamePiece> list) : IList<AGamePiece>
   */
  public IList<AGamePiece> apply(AGamePiece piece, IList<AGamePiece> list) {
    IList<AGamePiece> debris = list.generate(new Overlap(), piece, new GenerateBullets(),
        new MtList<AGamePiece>());
    list = list.filter2(new Overlap(), piece);
    return list.append(debris);
  }
}

class IsInsideScreen implements IPred<AGamePiece> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece gp) : boolean
   */
  public boolean apply(AGamePiece gp) {
    return (gp.x > -20 && gp.x < 510 && gp.y >= 0 && gp.y <= 305);
  }
}

class DrawPiece implements IFunc2<AGamePiece, WorldScene, WorldScene> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece arg1, WorldScene arg2) : WorldScene
   */
  public WorldScene apply(AGamePiece arg1, WorldScene arg2) {
    return arg1.draw(arg2);
  }
}

class MovePiece implements IFunc<AGamePiece, AGamePiece> {
  /*- TEMPLATE:
   * Methods:
   * this.apply(AGamePiece gp) : AGamePiece
   */
  public AGamePiece apply(AGamePiece gp) {
    return gp.move();
  }
}