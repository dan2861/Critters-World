// This is a simple critter animal that walks west until he eats food,
// then he turns around and walks east.
// The critter needs to keep track of state (a boolean flag) to "remember"
// whether he has eaten.

import java.awt.Color;

public class Cougar extends Critter {
  private boolean fought;
  private boolean west;
  
  public Cougar() {
    fought = false;
    west = true;
  }
  
  public boolean eat() {
    west = !west;
    return true;
  }
  
  public Attack fight(String opponent) {
    fought = true;
    return Attack.ROAR;
  }
  
  // need to override
  public Color getColor() {
    if (!fought)
      return Color.BLUE;
    else
      return Color.RED;
  }
  
  // need to override
  public Direction getMove(String[][] grid) {
    if (west)
      return Direction.WEST;
    else
      return Direction.EAST;
  }
  
  public String toString() {
    return "C";
  }
}
