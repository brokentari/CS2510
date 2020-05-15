import java.util.ArrayList;
import java.util.Random;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Cell {
  int numOfNeighboringMines;
  ArrayList<Cell> surroundingCells = new ArrayList<Cell>();
  boolean isMine;
  boolean clickedOn = false;
  boolean flagged = false;

  Cell(int numOfNeighboringMines, ArrayList<Cell> surroundingCells, boolean isMine) {
    this.numOfNeighboringMines = numOfNeighboringMines;
    this.surroundingCells = surroundingCells;
    this.isMine = isMine;
  }

  Cell() {
    this(0, new ArrayList<Cell>(), false);
  }

  // creates a mine for testing
  Cell(boolean isMine) {
    this(0, new ArrayList<Cell>(), isMine);
  }

  /*- TEMPLATE
   * Fields:
   *  this.numOfNeighboringMines....... int
   *  this.surroundingCells............ ArrayList<Cell>
   *  this.isMine...................... boolean
   *  this.clickedOn................... boolean
   *  this.flagged..................... false
   *  
   * Methods:
   *  this.drawConcealed(int row, int col, WorldScene background).. WorldScene
   *  this.drawCell(int row, int col, WorldScene background)..... WorldScene
   *  this.updateNeighbors(Cell update)........... void
   *  this.numberOfSurroundingMines()............. int
   *  this.click()................................ void
   *  this.flag()................................. void
   */

  // draw method for when the cell is clicked on
  WorldScene drawUnconcealed(int row, int col, WorldScene background) {
    WorldImage value = new TextImage("", 13, Color.BLACK);
    if (this.isMine) {
      value = new CircleImage(6, OutlineMode.SOLID, Color.RED);
    }
    else if (this.numberOfSurroundingMines() > 0) {
      value = new TextImage(Integer.toString(this.numberOfSurroundingMines()), 13, Color.BLACK);
    }
    WorldImage cell = new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK)
        .overlayImages(value);

    background.placeImageXY(cell,
        ((background.width - (background.width / 2)) / 2) + (row * 20) + 10,
        ((background.height - (background.height / 2)) / 2) + (col * 20) + 10);
    return background;
  }

  // default draw method that hides the cell's information
  WorldScene drawConcealed(int row, int col, WorldScene background) {
    WorldImage cell = new RectangleImage(15, 15, OutlineMode.SOLID, Color.GRAY)
        .overlayImages(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK));

    if (this.flagged) {
      cell = new OverlayImage(new EquilateralTriangleImage(10, OutlineMode.SOLID, Color.BLUE),
          cell);
    }

    background.placeImageXY(cell,
        ((background.width - (background.width / 2)) / 2) + (row * 20) + 10,
        ((background.height - (background.height / 2)) / 2) + (col * 20) + 10);

    return background;
  }

  // calls appropriate draw method based on if the cell was clicked on
  WorldScene drawCell(int row, int col, WorldScene background) {
    if (this.clickedOn) {
      return this.drawUnconcealed(row, col, background);
    }
    return this.drawConcealed(row, col, background);
  }

  // links the cell with all the surrounding cells
  void updateNeighbors(Cell update) {
    this.surroundingCells.add(update);
    update.surroundingCells.add(this);
  }

  // gets the number of surrounding mines
  int numberOfSurroundingMines() {
    int count = 0;
    for (Cell c : this.surroundingCells) {
      if (c.isMine) {
        this.numOfNeighboringMines++;
        count++;
      }
    }
    return count;
  }

  // sets the cell's status to clicked (once true, it can't be turned to false)
  void click() {
    this.clickedOn = true;
    if (this.numberOfSurroundingMines() == 0) {
      for (Cell neighbor : this.surroundingCells) {
        if (neighbor.numOfNeighboringMines == 0 && !neighbor.clickedOn) {
          neighbor.click();
        }
      }
    }
  }

  // flips the flagged status of the cell
  void flag() {
    this.flagged = !this.flagged;
  }
}

class Minesweeper extends World {
  int row;
  int col;
  int numOfMines;
  Random rand = new Random();
  ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(this.row);
  Cell clickedCell;

  Minesweeper(int rows, int columns, int mines) {

    if (mines >= (rows * columns)) {
      throw new IllegalArgumentException(
          "Number of mines cannot be greater than or equal to the total number of cells");
    }
    this.row = rows;
    this.col = columns;
    this.numOfMines = mines;
    int i = 0;
    int j = 0;

    // creates the unlinked cells array
    for (i = 0; i < col; i++) {
      ArrayList<Cell> currentColumn = new ArrayList<Cell>(this.row);
      for (j = 0; j < row; j++) {
        currentColumn.add(new Cell());
      }
      this.cells.add(currentColumn);
    }

    i = 0;
    j = 0;
    // goes to the cells and links them, with the
    // appropriate action for edge and corner cells

    for (ArrayList<Cell> column : this.cells) {
      j = 0;
      for (Cell c : column) {
        if (i <= col - 1 && j <= row - 1) {
          if (i == col - 1 && j < row - 1) {
            c.updateNeighbors(this.cells.get(i).get(j + 1));
            c.updateNeighbors(this.cells.get(i - 1).get(j + 1));
          }
          else if (i < col - 1 && j == row - 1) {
            c.updateNeighbors(this.cells.get(i + 1).get(j));
          }
          else if (i < col - 1 && j < row - 1) {
            c.updateNeighbors(this.cells.get(i + 1).get(j));
            c.updateNeighbors(this.cells.get(i + 1).get(j + 1));
            c.updateNeighbors(this.cells.get(i).get(j + 1));
            if (i > 0) {
              c.updateNeighbors(this.cells.get(i - 1).get(j + 1));
            }
          }
        }

        // the modulo operation spreads out the random possibility of creating
        // a mine (this could be used for difficulty settings, since not having
        // this constraint clumps all mines to the left hand of the grid
        if (this.numOfMines > 0 && this.rand.nextBoolean() && (i % 2) == 0) {
          c.isMine = true;
          this.numOfMines--;
        }
        j++;
      }
      i++;
    }

    while (this.numOfMines > 0) {
      for (ArrayList<Cell> column : this.cells) {
        for (Cell c : column) {
          if (!c.isMine && this.rand.nextBoolean()) {
            c.isMine = true;
            this.numOfMines--;
          }
        }
      }
    }

    this.clickedCell = this.cells.get(0).get(0);
  }

  Minesweeper() {
    this.row = 19;
    this.col = 30;
    this.numOfMines = 99;
  }

  /*- TEMPLATE
   * Fields:
   *  this.row............ int
   *  this.col............ int
   *  this.numOfMines..... int
   *  this.rand........... Random
   *  this.cells.......... ArrayList<ArrayList<Cell>>
   *  
   * Methods (inhereted):
   *  this.makeScene().... WorldScene
   *  this.onMouseClicked(Posn pos, String buttonName).. void
   *  this.lastScene(String msg)........ WorldScene
   */

  // draws the game board by drawing cells individually
  public WorldScene makeScene() {
    WorldScene currentScene = this.getEmptyScene();

    int i = 0;
    int j = 0;

    // goes through every cells and draws it
    for (ArrayList<Cell> column : this.cells) {
      j = 0;
      for (Cell c : column) {
        c.drawCell(i, j, currentScene);
        j++;
      }
      i++;
    }
    return currentScene;
  }

  public void onMouseClicked(Posn pos, String buttonName) {
    int x = pos.x;
    int y = pos.y;
    int row = -1;
    int col = -1;
    int counter = 20;
    int xStep = ((this.getEmptyScene().width - (this.getEmptyScene().width / 2)) / 2);
    int yStep = ((this.getEmptyScene().height - (this.getEmptyScene().height / 2)) / 2);

    while (x >= xStep) {
      if (counter == 20) {
        col++;
        counter = 0;
      }
      counter++;
      x--;
    }

    counter = 20;
    while (y >= yStep) {
      if (counter == 20) {
        row++;
        counter = 0;
      }
      counter++;
      y--;
    }

    if (0 <= row && row < this.row && 0 <= col && col < this.col) {
      this.clickedCell = this.cells.get(col).get(row);
      if (buttonName.equals("LeftButton")) {
        this.clickedCell.click();
        if (this.clickedCell.isMine) {
          this.endOfWorld("lost");
        }
      }
      else if (buttonName.equals("RightButton")) {
        this.clickedCell.flag();
      }
    }

    boolean allNonMinesClicked = true;
    for (ArrayList<Cell> column : this.cells) {
      for (Cell c : column) {
        if (!c.isMine) {
          allNonMinesClicked = allNonMinesClicked && c.clickedOn;
        }
      }
    }

    if (allNonMinesClicked) {
      this.endOfWorld("win");
    }

  }

  public WorldScene lastScene(String msg) {
    WorldScene currentScene = this.getEmptyScene();
    int i = 0;
    int j = 0;
    int topEdge = ((currentScene.height - (currentScene.height / 2)) / 2);
    int bottomEdge = topEdge + (currentScene.height / 2);

    if (msg.contentEquals("win")) {
      currentScene.placeImageXY(new TextImage("YOU WON!", 20, Color.BLACK), currentScene.width / 2,
          currentScene.height / 2);
    }

    else if (msg.contentEquals("lost")) {
      // goes through every cells and draws it
      for (ArrayList<Cell> column : this.cells) {
        j = 0;
        for (Cell c : column) {
          c.drawUnconcealed(i, j, currentScene);
          j++;
        }
        i++;
      }
      currentScene.placeImageXY(new TextImage("YOU LOST.", 20, Color.BLACK), currentScene.width / 2,
          bottomEdge + (currentScene.height - bottomEdge) / 2);
    }

    return currentScene;
  }
}

class MinesweeperExample {
  // test drawUnconcealed
  void testDrawUnconcealed(Tester t) {
    int row = 19;
    int col = 30;
    WorldScene background = new WorldScene(300, 500);

    Cell c1 = new Cell();
    Cell c2 = new Cell(4, new ArrayList<Cell>(), false);
    t.checkExpect(c1.drawUnconcealed(row, col, new WorldScene(300, 500)), background);
    t.checkExpect(c2.drawUnconcealed(row, col, new WorldScene(300, 500)), background);
    c1.updateNeighbors(c2); // update neighbor
    t.checkExpect(c1.drawUnconcealed(row, col, new WorldScene(300, 500)), background);
  }

  // test drawConcealed
  void testDrawConcealed(Tester t) {
    int row = 19;
    int col = 30;
    WorldScene background = new WorldScene(300, 500);

    Cell c1 = new Cell();
    Cell c2 = new Cell(true);
    Cell c3 = new Cell(4, new ArrayList<Cell>(), false);
    t.checkExpect(c1.drawConcealed(row, col, background), background);
    t.checkExpect(c3.drawConcealed(row, col, background), background);
    c1.updateNeighbors(c2);
    c1.updateNeighbors(c3);// update neighbors
    background.placeImageXY(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK)
        .overlayImages(new TextImage(Integer.toString(0), 13, Color.BLACK)), 90, 70);
    t.checkExpect(c1.drawConcealed(row, col, background), background);
  }

  // test the function to updates a cell's neighbors and
  // given cell
  void testUpdateNeighbors(Tester t) {
    Cell emptyCell = new Cell();
    Cell secondCell = new Cell();
    ArrayList<Cell> emptyCellNeighbors = emptyCell.surroundingCells;

    t.checkExpect(emptyCellNeighbors.size(), 0);

    emptyCell.updateNeighbors(secondCell);
    t.checkExpect(emptyCellNeighbors.size(), 1);
    t.checkExpect(emptyCellNeighbors.get(0), secondCell);
    t.checkExpect(secondCell.surroundingCells.get(0), emptyCell);
  }

  // tests if the correct number of neighbors
  // that are mines are being returned and updated
  void testNumberOfSurroundingMines(Tester t) {
    Cell mine1 = new Cell(true);
    Cell mine2 = new Cell(true);
    Cell testingCell = new Cell();
    Cell testingCell2 = new Cell();

    t.checkExpect(testingCell.surroundingCells.size(), 0);
    t.checkExpect(testingCell.numberOfSurroundingMines(), 0);

    testingCell.updateNeighbors(mine1);
    t.checkExpect(testingCell.surroundingCells.size(), 1);
    t.checkExpect(testingCell.numberOfSurroundingMines(), 1);

    testingCell.updateNeighbors(testingCell2);
    t.checkExpect(testingCell.surroundingCells.size(), 2);
    t.checkExpect(testingCell.numberOfSurroundingMines(), 1);

    testingCell.updateNeighbors(mine2);
    t.checkExpect(testingCell.surroundingCells.size(), 3);
    t.checkExpect(testingCell.numberOfSurroundingMines(), 2);
  }

  // drawCell is pretty much a call to drawConcealed (so equal functions),
  // later drawCell would be used to call a function that draws a
  // clicked cell (drawUnconcealed)
  void testDrawCell(Tester t) {
    Cell testingCell = new Cell();
    Cell testingMine = new Cell(true);

    WorldScene background = new Minesweeper().getEmptyScene();
    background.placeImageXY(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK)
        .overlayImages(new TextImage(Integer.toString(0), 13, Color.BLACK)), 90, 70);

    t.checkExpect(testingCell.drawCell(3, 2, new Minesweeper().getEmptyScene()), background);

    background.placeImageXY(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK)
        .overlayImages(new TextImage("M", 13, Color.RED)), 110, 130);

    t.checkExpect(testingMine.drawCell(4, 5, background), background);

  }

  void testMakeScence(Tester t) {
    Minesweeper m1 = new Minesweeper();
    Minesweeper m2 = new Minesweeper(2, 2, 1);

    WorldScene background = new Minesweeper().getEmptyScene();
    t.checkExpect(m1.makeScene(), background);
    background.placeImageXY(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK)
        .overlayImages(new TextImage("M", 13, Color.RED)), 110, 130);
    t.checkExpect(m2.makeScene(), background);
    t.checkExpect(m1.makeScene(), background);
  }

  void testLastScene(Tester t) {
      
  }

  // tests the creating of the game board
  void testMinesweeper(Tester t) {
    int rows = 5;
    int col = 8;
    int mines = 10;
    Minesweeper game = new Minesweeper(rows, col, mines);

    game.bigBang((col * 40), (rows * 40));
  }
}