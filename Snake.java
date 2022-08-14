import java.awt.Color;
import java.util.Random;

public class Snake extends Critter {
  protected int cycleLength;   // # steps in curr. Horiz. cycle 
  protected int steps; 
    
 public Snake() {
   cycleLength = 1; 
   steps = 0; 
 }
 
 public boolean eat() {
   return false;
 }
 
 public Attack fight(String opponent) {
   // 
   Random rand = new Random();
   int choice = rand.nextInt(2);
   if (choice == 0)
     return Attack.POUNCE;
   else
     return Attack.ROAR;
 }
 
 public Color getColor() {
  return new Color(20, 128, 50);
 }
 
 public Direction getMove(String[][] grid) {
   if (steps < cycleLength) { 
          steps++; 
          if (cycleLength % 2 == 1) { 
                return Direction.EAST; 
            } else { 
                return Direction.WEST; 
            } 
        } else { 
           steps = 0; 
           cycleLength ++; 
           return Direction.SOUTH; 
        } 
 }

 public String toString() {
  return "S";
 }
}