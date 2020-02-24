import java.awt.Color;

interface Global {
  int WIDTH = 500;
  int HEIGHT = 300;
  // seconds / frame
  double TICK_RATE = 1 / 28;
  // time in between spawn
  int SHIP_FREQ = 1;
  int NUM_OF_SHIPS_MIN = 2;
  int NUM_OF_SHIPS_MAX = 5;
  int BULLET_RADIUS = 4;
  int BULLET_RADIUS_EXPL = 2;
  int BULLET_RADIUS_MAX = 12;
  Color BULLET_COLOR = Color.PINK;
  // pixels per tick
  int BULLET_SPEED = 8;
  int SHIP_RADIUS = HEIGHT / 30;
  Color SHIP_COLOR = Color.CYAN;
  int SHIP_SPEED = BULLET_SPEED / 2;
  int UPPER_BOUND = HEIGHT / 7;
  int LOWER_BOUND = HEIGHT - UPPER_BOUND;
  Color FONT_COLOR = Color.BLACK;
  int FONT_SIZE = 13;
}