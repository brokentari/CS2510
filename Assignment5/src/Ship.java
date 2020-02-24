import java.awt.Color;
import java.util.Random;

class Ship extends AGamePiece {
  Ship() {
    super((new Random().nextInt(13) - 20), (new Random().nextInt(214) + 34), Global.SHIP_SPEED,
        Global.SHIP_RADIUS, Global.SHIP_COLOR, 1);
  }

  Ship(int x, int y, int speed) {
    this();
    this.speed = speed;
    this.x = x;
    this.y = y;
  }

  Ship(int x, int y, int speed, int radius, Color color) {
    super(x, y, speed, radius, color, 1);
  }

  /*- TEMPLATE:
   *
   * FIELDS:
   * this.radius : int
   * this.speed : int
   * this.color : Color
   * this.x : int
   * this.y : int
   * this.generation : int
   *
   * METHODS:
   * this.draw(WorldScene scene) : WorldScene
   * this.move() : Ship
   * this.inContactWith(AGamePiece gp) : boolean
   * this.explodes() : IList<AGamePiece>
   */

  public IList<AGamePiece> explodes() {
    return new MtList<AGamePiece>();
  }
}