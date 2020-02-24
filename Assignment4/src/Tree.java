import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;

interface ITree {
  // Draws the images of the ITree
  WorldImage draw();

  // Checks if any of the branches are lower than 180 degrees
  boolean isDrooping();

  // Combines two trees and rotates them respectively
  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree);

  // Rotates a tree to the given theta
  ITree changeDegree(double theta);

  // get the total width of a tree
  double getWidth();

  // get width of the left branch of a tree
  double getWidthHelperLeft(double currWidth);

  // get width of the right branch of a tree
  double getWidthHelperRight(double currWidth);

}

// represents a leaf in a tree
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }

  /*- CLASS TEMPLATE:
   * Fields: 
   * ... this.size ... int
   * ... this.color ... Color
   * Methods:
   * ... this.draw() ... WorldImage
   * ... this.isDrooping() ... Boolean
   * ... this.comebine(int, int double, double, ITree) ... ITree
   * ... this.changeDegree(double) .... double
   * ... this.getWidth() ... double
   * ... this.getWidthHelperLeft(double) ... double
   * ... this.getWidthHelperRight(double) ... double
   */

  // draws a leaf
  public WorldImage draw() {
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }

  // a leaf cannot droop
  public boolean isDrooping() {

    return false;
  }

  // combines a leaf with any given tree
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    /*- Fields of Parameters:
     * ... proteins.first ... - String
     * ... proteins.rest ... - ILoString
     * ... proteinsSoFar.first ... - ILoString
     * ... proteinsSoFar.rest ... - ILoLoString
     * 
     * Parameters:
     * ... leftLength ... int
     * ... rightLength .. int
     * ... leftTheta ... double
     * ... rightTheta ... double
     * ... otherTree ... ITree
     * 
     * Methods of Parameters:
     * ... this.draw() ... WorldImage
     * ... this.isDrooping() ... Boolean
     * ... this.comebine(int, int double, double, ITree) ... ITree
     * ... this.changeDegree(double) .... double
     * ... this.getWidth() ... double
     * ... this.getWidthHelperLeft(double) ... double
     * ... this.getWidthHelperRight(double) ... double
     */

    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  // returns this leaf
  public ITree changeDegree(double theta) {
    return this;
  }

  // returns the diameter of the leaf
  public double getWidth() {

    return this.size * 2;
  }

  // returns the radius of a leaf
  public double getWidthHelperLeft(double currWidth) {

    return currWidth - this.size;
  }

  // returns the raidus of a leaf
  public double getWidthHelperRight(double currWidth) {

    return currWidth + this.size;
  }
}

// represent a stem in a tree
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

  /*- CLASS TEMPLATE:-
   * Fields: 
   * ... this.length ... int
   * ... this.theta ... double
   * ... this.tree ... ITree
   * Methods:
   * ... this.draw() ... WorldImage
   * ... this.isDrooping() ... Boolean
   * ... this.comebine(int, int double, double, ITree) ... ITree
   * ... this.changeDegree(double) .... double
   * ... this.getWidth() ... double
   * ... this.getWidthHelperLeft(double) ... double
   * ... this.getWidthHelperRight(double) ... double
   */

  // draws the stem and its attached trees
  public WorldImage draw() {

    return new OverlayImage(this.tree.draw(),
        new RotateImage(
            new LineImage(new Posn(this.length, 0), Color.BLACK).movePinhole((this.length / 2), 0),
            -theta));
  }

  // checks if any of the trees attached are drooping
  public boolean isDrooping() {

    return this.theta > 180 || this.tree.isDrooping();
  }

  // combines the stem with the given tree
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    /*- Fields of Parameters:
     * ... proteins.first ... - String
     * ... proteins.rest ... - ILoString
     * ... proteinsSoFar.first ... - ILoString
     * ... proteinsSoFar.rest ... - ILoLoString
     * 
     * Parameters:
     * ... leftLength ... int
     * ... rightLength .. int
     * ... leftTheta ... double
     * ... rightTheta ... double
     * ... otherTree ... ITree
     * 
     * Methods of Parameters:
     * ... this.draw() ... WorldImage
     * ... this.isDrooping() ... Boolean
     * ... this.comebine(int, int double, double, ITree) ... ITree
     * ... this.changeDegree(double) .... double
     * ... this.getWidth() ... double
     * ... this.getWidthHelperLeft(double) ... double
     * ... this.getWidthHelperRight(double) ... double
     */

    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  // changes the degree of the stem
  public ITree changeDegree(double theta) {
    double newTheta = 0;
    if (this.theta + theta < 0) {
      newTheta = 360 + (this.theta + theta);
    }
    else {
      newTheta = this.theta + theta;
    }
    return new Stem(this.length, newTheta, this.tree.changeDegree(theta));
  }

  // gets the width of the steam and its attached trees
  public double getWidth() {
    return this.tree.getWidth() + (Math.cos(Math.toRadians(this.theta)) * this.length);
  }

  // accumulates the total width traversed so far
  // in the left branch
  public double getWidthHelperLeft(double currWidth) {
    currWidth = currWidth + Math.cos(Math.toRadians(this.theta)) * this.length;
    return this.tree.getWidthHelperLeft(currWidth);
  }

  // accumulates the total width traversed so far
  // in the right branch
  public double getWidthHelperRight(double currWidth) {
    currWidth = currWidth + Math.cos(Math.toRadians(this.theta)) * this.length;
    return this.tree.getWidthHelperRight(currWidth);
  }
}

// represents a branch of a tree
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

  /*- CLASS TEMPLATE:-
   * Fields:
   * ... this.leftLength ... int
   * ... this.rightLength ... int
   * ... this.leftTheta ... double
   * ... this.rightTheta ... double
   * ... this.left ... ITree
   * ... this.right ... ITree
   * Methods: 
   * ... this.relativePinHole ... WorldImage
   * ... this.draw() ... WorldImage
   * ... this.isDrooping() ... Boolean
   * ... this.comebine(int, int double, double, ITree) ... ITree
   * ... this.changeDegree(double) .... double
   * ... this.getWidth() ... double
   * ... this.getWidthHelperLeft(double) ... double
   * ... this.getWidthHelperRight(double) ... double
   */

  // moves the pinhole of the line to the edge
  public WorldImage relativePinhole(WorldImage line, double theta, int length) {
    /*-
     * Parameters:
     * ... line ... WorldImage
     * ... theta ... double
     * ... length ... int
     */
    return new RotateImage(line.movePinhole(length / 2, 0), -theta);
  }

  // draws the branches
  public WorldImage draw() {
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

  // determines if either of the branches has part that is drooping
  public boolean isDrooping() {
    return (this.leftTheta > 180) || (this.rightTheta > 180) || this.left.isDrooping()
        || this.right.isDrooping();
  }

  // rotates both branches and their respective attached trees
  public ITree changeDegree(double theta) {
    double newThetaR = 0;
    double newThetaL = 0;
    if (this.rightTheta + theta < 0) {
      newThetaR = 360 + (this.rightTheta + theta);
    }
    else {
      newThetaR = this.rightTheta + theta;
    }

    if (this.leftTheta + theta < 0) {
      newThetaL = 360 + (this.leftTheta + theta);
    }
    else {
      newThetaL = this.leftTheta + theta;
    }
    return new Branch(this.leftLength, this.rightLength, newThetaL, newThetaR,
        this.left.changeDegree(theta), this.right.changeDegree(theta));
  }

  // combines this branch with the given tree
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    /*- Fields of Parameters:
     * ... proteins.first ... - String
     * ... proteins.rest ... - ILoString
     * ... proteinsSoFar.first ... - ILoString
     * ... proteinsSoFar.rest ... - ILoLoString
     * 
     * Parameters:
     * ... leftLength ... int
     * ... rightLength .. int
     * ... leftTheta ... double
     * ... rightTheta ... double
     * ... otherTree ... ITree
     * 
     * Methods of Parameters:
     * ... this.draw() ... WorldImage
     * ... this.isDrooping() ... Boolean
     * ... this.comebine(int, int double, double, ITree) ... ITree
     * ... this.changeDegree(double) .... double
     * ... this.getWidth() ... double
     * ... this.getWidthHelperLeft(double) ... double
     * ... this.getWidthHelperRight(double) ... double
     */

    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.changeDegree(180 - (leftTheta - rightTheta)),
        otherTree.changeDegree(-(90 - rightTheta)));
  }

  // total width of the tree
  public double getWidth() {
    double left_width = this.leftLength * Math.cos(Math.toRadians(this.leftTheta));

    double right_width = this.rightLength * Math.cos(Math.toRadians(this.rightTheta));

    return Math.abs(this.left.getWidthHelperLeft(left_width))
        + Math.abs(this.right.getWidthHelperRight(right_width));
  }

  // traverse through the width of the left branch
  public double getWidthHelperLeft(double currWidth) {

    double left_width = this.leftLength * Math.cos(Math.toRadians(this.leftTheta));

    currWidth = Math.min(left_width + this.left.getWidthHelperLeft(currWidth),
        left_width + this.right.getWidthHelperRight(currWidth));

    return currWidth;
  }

  // traverse through the width of the right branch
  public double getWidthHelperRight(double currWidth) {
    double right_width = this.rightLength * Math.cos(Math.toRadians(this.rightTheta));

    currWidth = Math.max(right_width + this.right.getWidthHelperRight(currWidth),
        right_width + this.left.getWidthHelperLeft(currWidth));

    return currWidth;
  }
}

// examples of trees
class ExamplesTree {
  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));
  ITree tree3 = new Stem(40, 90, tree1);
  ITree tree4 = new Stem(50, 90, tree2);
  ITree tree5 = tree1.combine(40, 50, 150, 30, tree2);
  ITree tree6 = new Leaf(10, Color.CYAN);
  ITree tree7 = tree6.combine(40, 40, 150, 30, tree6);
  ITree tree8 = tree3.combine(40, 40, 150, 30, tree6);
  ITree tree9 = tree5.combine(40, 40, 150, 30, tree7);
  ITree tree10 = new Stem(100, 90, tree6);
  ITree tree11 = tree5.combine(50, 50, 160, 20, tree5);
  ITree tree12 = tree4.combine(50, 50, 160, 20, tree4);

  // test to draws trees
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(tree11.draw(), 250, 250)) && c.show();
  }

  // tests for combinining trees
  boolean testCombine(Tester t) {
    return t.checkExpect(tree1.combine(40, 50, 150, 30, tree2),
        new Branch(40, 50, 150, 30,
            new Branch(30, 30, 195, 100, new Leaf(10, Color.red), new Leaf(15, Color.blue)),
            new Branch(30, 30, 55, 5, new Leaf(15, Color.green), new Leaf(8, Color.orange))))
        && t.checkExpect(tree6.combine(40, 60, 170, 30, tree6),
            new Branch(40, 60, 170, 30, tree6, tree6));
  }

  boolean testChangeDegree(Tester t) {
    return t.checkExpect(tree1.changeDegree(45),
        new Branch(30, 30, 180, 85, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE)))
        && t.checkExpect(tree2.changeDegree(10),
            new Branch(30, 30, 125, 75, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE)))
        && t.checkExpect(tree10.changeDegree(35), new Stem(100, 125, tree6));
  }

  // test for checking for drooping branches
  boolean testDrooping(Tester t) {
    return t.checkExpect(this.tree1.isDrooping(), false)
        && t.checkExpect(this.tree2.isDrooping(), false)
        && t.checkExpect(this.tree3.isDrooping(), false)
        && t.checkExpect(this.tree4.isDrooping(), false)
        && t.checkExpect(this.tree5.isDrooping(), true)
        && t.checkExpect(this.tree9.isDrooping(), true)
        && t.checkExpect(this.tree11.isDrooping(), true);
  }

  boolean testWidthHelper(Tester t) {
    return t.checkInexact(this.tree1.getWidthHelperLeft(0), -31.21, 0.001)
        && t.checkInexact(this.tree1.getWidthHelperRight(0), 37.98, 0.001)
        && t.checkInexact(this.tree3.getWidthHelperLeft(0), -31.21, 0.001)
        && t.checkInexact(this.tree3.getWidthHelperRight(0), 37.98, 0.001)
        && t.checkInexact(this.tree11.getWidthHelperLeft(0), -113.58, 0.001);
  }

  // test for getting total width of a tree
  boolean testWidth(Tester t) {
    return t.checkInexact(this.tree1.getWidth(), 69.19, 0.005)
        && t.checkInexact(this.tree2.getWidth(), 48.35, 0.005)
        && t.checkInexact(this.tree3.getWidth(), 69.19, 0.005)
        && t.checkInexact(this.tree4.getWidth(), 48.35, 0.005)
        && t.checkInexact(this.tree5.getWidth(), 154.8, 0.005)
        && t.checkInexact(this.tree9.getWidth(), 166.32, 0.005)
        && t.checkInexact(this.tree10.getWidth(), 20.00, 0.005);
  }
}