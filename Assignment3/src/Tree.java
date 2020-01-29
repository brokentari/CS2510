import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;

interface ITree {
  /* see methods below */
  WorldImage draw();

  boolean isDrooping();

  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree);
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
    return this;
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

  @Override
  public WorldImage draw() {
    // TODO Auto-generated method stub
    return new OverlayImage(this.tree.draw(),
        new RotateImage(
            new LineImage(new Posn(this.length, 0), Color.BLACK).movePinhole((this.length / 2), 0), -theta));
  }

  @Override
  public boolean isDrooping() {
    // TODO Auto-generated method stub
    if (this.theta > 180 || this.tree.isDrooping()) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    // TODO Auto-generated method stub
    return this;
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
                    Math.sin(Math.toRadians(180 - leftTheta)) * this.leftLength,
                    (Math.cos(Math.toRadians(180 -leftTheta)) * this.leftLength)),
        new OverlayImage(this.right.draw(),
            relativePinhole(new LineImage(new Posn(this.rightLength, 0), Color.black),
                this.rightTheta, this.rightLength)).movePinhole(
                    -(Math.cos(Math.toRadians(rightTheta)) * this.rightLength),
                    (Math.sin(Math.toRadians(rightTheta)) * this.rightLength)));
  }  

  @Override
  public boolean isDrooping() {
    // TODO Auto-generated method stub
    if (this.leftTheta > 180 || this.rightTheta > 180 || 
        this.left.isDrooping() || this.right.isDrooping()) {
      return true;
    }
    else {
      return false;
    }
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, otherTree);
  }

}

class ExamplesTree {
  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(5, Color.RED), new Leaf(5, Color.BLUE));
  ITree tree2 = new Branch(30, 30, 135, 65, new Leaf(5, Color.GREEN), new Leaf(5, Color.ORANGE));
  ITree tree3 = new Stem(40, 90, tree1);
  ITree tree4 = new Stem(50, 90, tree2);
  ITree tree5 = new Branch(40, 50, 150, 30, tree1, tree2);
  
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
        && t.checkExpect(this.tree4.isDrooping(), false);
  }
}