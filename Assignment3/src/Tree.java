import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;

interface ITree {
  /* see methods below */
  WorldImage draw();

  boolean isDrooping();

  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree);

  ITree changeDegree(double theta);

  double getWidth();

  double getWidthHelper(double currWidth);

  double getMaxLength();
}

class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }

  @Override
  public WorldImage draw() {
    // TODO Auto-generated method stub
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }

  @Override
  public boolean isDrooping() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    // TODO Auto-generated method stub
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  @Override
  public ITree changeDegree(double theta) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public double getWidth() {
    // TODO Auto-generated method stub
    return 2 * this.size;
  }

  @Override
  public double getWidthHelper(double currWidth) {
    // TODO Auto-generated method stub
    currWidth = currWidth + this.size;

    return currWidth;
  }

  public double getMaxLength() {
    // TODO Auto-generated method stub
    return this.size;
  }
}

class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;

  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }

  public WorldImage draw() {
    // TODO Auto-generated method stub
    return new OverlayImage(this.tree.draw(),
        new RotateImage(
            new LineImage(new Posn(this.length, 0), Color.BLACK).movePinhole((this.length / 2), 0),
            -theta));
  }

  public boolean isDrooping() {
    // TODO Auto-generated method stub
    return this.theta > 180 || this.tree.isDrooping();
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    // TODO Auto-generated method stub
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  public ITree changeDegree(double theta) {
    // TODO Auto-generated method stub
    return new Stem(this.length, this.theta + theta, this.tree);
  }

  public double getWidth() {
    // TODO Auto-generated method stub
    return this.tree.getWidth() 
        + (Math.abs(Math.cos(Math.toRadians(this.theta)) * this.length));
  }

  public double getWidthHelper(double currWidth) {
    // TODO Auto-generated method stub
    currWidth = currWidth + Math.abs(Math.cos(Math.toRadians(this.theta)) * this.length);

    return this.tree.getWidthHelper(currWidth);
  }

  @Override
  public double getMaxLength() {
    // TODO Auto-generated method stub
    return this.length;
  }

}

class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;

  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left,
      ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  public WorldImage relativePinhole(WorldImage line, double theta, int length) {
    return new RotateImage(line.movePinhole(length / 2, 0), -theta);
  }

  @Override
  public WorldImage draw() {
    // TODO Auto-generated method stub
    return new OverlayImage(
        new OverlayImage(this.left.draw(),
            relativePinhole(new LineImage(new Posn(this.leftLength, 0), Color.black),
                this.leftTheta, this.leftLength)).movePinhole(
                    Math.cos(Math.toRadians(180 - leftTheta)) * this.leftLength,
                    (Math.sin(Math.toRadians(180 - leftTheta)) * this.leftLength)),
        new OverlayImage(this.right.draw(),
            relativePinhole(new LineImage(new Posn(this.rightLength, 0), Color.black),
                this.rightTheta, this.rightLength)).movePinhole(
                    -(Math.cos(Math.toRadians(rightTheta)) * this.rightLength),
                    (Math.sin(Math.toRadians(rightTheta)) * this.rightLength)));
  }

  @Override
  public boolean isDrooping() {
    // TODO Auto-generated method stub
    return (this.leftTheta > 180) || (this.rightTheta > 180) || this.left.isDrooping()
        || this.right.isDrooping();
  }

  public ITree changeDegree(double theta) {
    return new Branch(this.leftLength, this.rightLength, this.leftTheta + theta,
        this.rightTheta + theta, this.left.changeDegree(theta), this.right.changeDegree(theta));
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  @Override
  public double getWidth() {    
    // TODO Auto-generated method stub
    double left_width = (Math
        .abs(this.leftLength * Math.cos(Math.toRadians(180 - this.leftTheta))));
    double right_width = (Math.abs(this.rightLength * Math.cos(Math.toRadians(this.rightTheta))));
    return this.left.getWidthHelper(left_width) + this.right.getWidthHelper(right_width);
  }

  @Override
  public double getWidthHelper(double currWidth) {
    // TODO Auto-generated method stub
    
      currWidth = currWidth 
          + (Math.abs(this.getMaxLength() * Math.cos(Math.toRadians(this.getMaxAngle()))));
      
      return this.getMaxTree().getWidthHelper(currWidth);
  }

  @Override
  public double getMaxLength() {
    // TODO Auto-generated method stub
    if (this.leftLength > this.rightLength) {
      return this.leftLength;
    }
    else {
      return this.rightLength;
    }
  }

  public double getMaxAngle() {
    if (this.leftLength > this.rightLength) {
      return 180 - this.leftTheta;
    }
    else {
      return this.rightTheta;
    }
  }

  public ITree getMaxTree() {
    if (this.leftLength > this.rightLength) {
      return this.left;
    }
    else {
      return this.right;
    }
  }
}

class ExamplesTree {
  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));
  ITree tree3 = new Stem(40, 90, tree1);
  ITree tree4 = new Stem(50, 90, tree2);
  ITree tree5 = tree1.combine(40, 50, 150, 30, tree2);

  WorldImage test = new RotateImage(new LineImage(new Posn(60, 0), Color.black), -150);

  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(tree5.draw(), 250, 250)) && c.show();
  }

  boolean testDrooping(Tester t) {
    return t.checkExpect(this.tree1.isDrooping(), false)
        && t.checkExpect(this.tree2.isDrooping(), false)
        && t.checkExpect(this.tree3.isDrooping(), false)
        && t.checkExpect(this.tree4.isDrooping(), false)
        && t.checkExpect(this.tree5.isDrooping(), true);
  }

  boolean testWidth(Tester t) {
    return t.checkInexact(this.tree1.getWidth(), 69.19, 0.005)
        && t.checkInexact(this.tree2.getWidth(), 48.35, 0.005)
        && t.checkInexact(this.tree3.getWidth(), 69.19, 0.005)
        && t.checkInexact(this.tree4.getWidth(), 48.35, 0.005)
        && t.checkInexact(this.tree5.getWidth(), 154.8, 0.005);
  }
}