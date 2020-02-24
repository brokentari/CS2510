import java.awt.Color;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;

abstract class AGamePiece implements IGamePiece {
  int radius;
  int speed;
  Color color;
  int x;
  int y;
  int generation;

  AGamePiece(int x, int y, int speed, int radius, Color color, int generation) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.radius = radius;
    this.color = color;
    this.generation = generation;
  }

  /*- TEMPLATE:
  *
  * FIELDS:
  * this.radius : int
  * this.speed : int
  * this.color : Color
  * this.x : int
  * this.y : int
  * this.gneration : int
  *
  * METHODS:
  * this.draw(WorldScene scene) : WorldScene
  * this.move() : AGamePiece
  * this.inContactWith(AGamePiece gp) : boolean
  */

  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), this.x,
        this.y);
  }

  public AGamePiece move() {
    return new Ship(this.x + this.speed, y, this.speed, this.radius, this.color);
  }

  public boolean inContactWith(AGamePiece gp) {
    return (Math.sqrt(Math.pow((gp.x - this.x), 2) + (Math.pow((gp.y - this.y), 2))))
        - (this.radius + gp.radius) < 0.001;
  }

  public abstract IList<AGamePiece> explodes();
}
