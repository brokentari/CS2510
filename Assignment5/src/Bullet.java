import java.awt.Color;

class Bullet extends AGamePiece {
  int theta;

  Bullet() {
    super(250, 305, Global.BULLET_SPEED, Global.BULLET_RADIUS, Global.BULLET_COLOR, 1);
    this.theta = 90;
  }

  Bullet(int x, int y, int speed, int radius, Color color, int theta, int generation) {
    super(x, y, speed, radius, color, generation);
    this.theta = theta;
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
   * this.theta : int
   * 
   * METHODS:
   * this.draw(WorldScene scene) : WorldScene
   * this.inContactWith(AGamePiece gp) : boolean
   * this.move() : Bullet
   * this.explodes() : IList<AGamePiece>
   */

  public Bullet move() {
    double xSpeed = Math.cos((this.theta * Math.PI) / 180) * this.speed;
    double ySpeed = Math.sin((this.theta * Math.PI) / 180) * this.speed;
    return new Bullet(this.x + (int) xSpeed, this.y - (int) ySpeed, this.speed, this.radius,
        this.color, this.theta, this.generation);
  }

  public IList<AGamePiece> explodes() {
    IList<AGamePiece> genBullets = new MtList<AGamePiece>();
    for (int i = 0; i <= this.generation; i++) {
      int newRadius = this.radius + Global.BULLET_RADIUS_EXPL;
      if (newRadius >= Global.BULLET_RADIUS_MAX) {
        newRadius = Global.BULLET_RADIUS_MAX;
      }

      genBullets = genBullets.addElement(new Bullet(this.x, this.y, this.speed, newRadius,
          this.color, (i * (360 / (this.generation + 1))), this.generation + 1));
    }
    return genBullets;
  }
}