import java.awt.Color;
import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library   
import javalib.worldcanvas.WorldCanvas;

 
interface ITree { 
  /* see methods below */ 
  WorldImage draw();
  
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
        new RotateImage(new LineImage(new Posn(0, this.length), 
            Color.BLACK).movePinhole(0, -(this.length/2)),
        this.theta * (Math.PI / 180)));
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
  
  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }
  @Override
  public WorldImage draw() {
    // TODO Auto-generated method stub
    return new RotateImage(new LineImage(new Posn(0, this.leftLength), Color.BLACK), 
        this.leftTheta * (Math.PI/180)).movePinhole(this.leftLength / 2 * Math.cos(leftTheta),
            this.leftLength / 2 * Math.sin(leftTheta));
  }
}

class ExampleTree {
  ITree tree1 = new Stem(40, 90, new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), 
      new Leaf(15, Color.blue)));
  
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(tree1.draw(), 250, 250))
        && c.show();
  } 
}