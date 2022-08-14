import java.util.*;   // for Random 
 
public class Hipster extends Critter { 
  private final int INTERVAL = 200;
  private Random  rand;
  private static int edgyBarX; 
  private static int edgyBarY; 
  private int nextT; // time until next inspiration
  private int t;
    
    public Hipster() { 
        rand = new Random();
        t = 0;
        nextT = rand.nextInt(INTERVAL); 
    } 
     
    public Direction getMove(String[][] grid) {
      t ++;
      if (t == nextT) {
        edgyBarX = rand.nextInt( getWidth() ); 
        edgyBarY = rand.nextInt( getHeight() ); 
        t = 0;
        nextT = rand.nextInt(INTERVAL); 
      }
       
      if (getY() != edgyBarY) { 
        return Direction.NORTH; 
      } else if (getX() != edgyBarX) { 
        return Direction.EAST; 
      } else { 
        return Direction.CENTER; 
      } 
    } 
  
    public String toString() {
      return "H(" + edgyBarX + "," +edgyBarY + ")";
    }
} 
