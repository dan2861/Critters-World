import java.awt.Color;
import java.util.Random;
//*******************************************************************
//
//   File: ApexPredator.java         Assignment No.: 9
//
//   Author: dym7             
//
//   Class: ApexPredator
// 
//   Time spent on this problem: ~12hrs
//   --------------------
//    This program implements a critter that is a celebate pacificist,  
//    and therefore avoids violence, as well as mating. Its movement
//    takes the safest path until the number of oppressive critters has 
//    dwindled. After most opressive critters die, ApexPredator starts
//    eating. It also camouflages itself by changing itself into the 
//    other various critters. When encountering danger, it counteracts
//    with the winning movds for the regular critters and uses reverse
//    psychology attack on smart critters that try to predict it's 
//    camouflaged fight moves.
//
//*******************************************************************

public class ApexPredator extends Critter{
  private boolean moveVertical;
  private Direction direction;
  private boolean eating;

  private static String appearance = "%";       // Initially mimics ant
  private static ApexPredator leader = null;    // Leader chooses the appearance
  private static int aliveApexPredator;         // living ApexPredators
  private static int numOfCritters = 7 * 25;    // 7 competitors each with 25 critters

  public ApexPredator() {
    // assign leader to new critter if leader dies
    if (leader == null) {
      leader = this;
    }
    // move vertically at the start
    moveVertical = true;     
    direction = Direction.NORTH;
    aliveApexPredator++;
  }

  @Override
  public boolean eat() {
    // Eat after most critters have died
    if(numOfCritters < 25) {
      return true;
    } else {
      return false;
    }
  }
  

  // Apex predator mimics cobra and attacks the opposite of the critter, but since it
  // mimics these predators, it tries to use reverse psychology on smart competitors,
  // taking advantage of the toString values of the critters
  public Attack fight(String opponent) {
        // move against ants and fire ants
        if(opponent.equals("%")) {
            return Attack.ROAR;
        } else if (isBird(opponent)) { 
            // moves against birds and vultures
            return Attack.SCRATCH;
        } else if(isHippo(opponent)) {
            // moves against hippos
            if(Integer.parseInt(opponent) == 0) {
                return Attack.ROAR;
            } else {
                return Attack.SCRATCH;
            }
        } else if(opponent.equals("S")) {
            return Attack.POUNCE;
        } else {
            if(appearance.equals("%")) {
              return Attack.POUNCE;
            } else if (isHippo(appearance)) {
              if(Integer.parseInt(opponent) == 0) {
                return Attack.POUNCE;
              } else {
                return Attack.ROAR;
              } 
            } else if(isBird(appearance)) {
              return Attack.ROAR;
            } else {  // appears as stone
              return Attack.POUNCE;
            }
          }
    } // end of fight()

  @Override
  // color change to differentiate critter
  public Color getColor() {
    return Color.MAGENTA;
  }

  @Override
  // A pacificist critter that moves in 
  // the direction of least violence
  public Direction getMove(String[][] grid) {
    // elect new leader, when leader loses
    if (leader == null) {
      leader = this;
    }
    if (this == leader) { // leader chooses random camouflage
      switch((int) (Math.random() * 4)) {
        case 0 -> appearance = "%";
        case 1 -> appearance = mimicHippo();
        case 2 -> appearance = mimimcBird(direction);
        default -> appearance = "S";
       }
    }

    // count the total number of living critter, other than ApexPredator
    numOfCritters = countCritters(grid);  

    // after a certain number of critters lose, start eating 
    if(numOfCritters < 25) {
      eating = true;
    } else {
      eating = false;
    }

    if(eating) { // move to eat
      return eatingMovement(grid);
    } else {  // move to evade violence
      if(moveVertical) {

        // next move is horizonatal
        moveVertical = false;
        // Check 5 grids up, and 5 grids down,
        // then go in direction
        direction = moveVertical(getX(), getY(), grid);
        return direction;
        
      } else {  // horizontal case      
        // next move is vertical
        moveVertical = true;
        // handle horizontal case
        direction = moveHorizontal(getX(), getY(), grid);
        return direction;
      }
    }
  }

  @Override
  // Critter camouflages to either ant, hippo, stone or bird/vulture
  public String toString() {
    return appearance;
  }


  @Override
  public void lose() {
    // I have failed my people, steps down as leader
    if (leader == this) {
      leader = null;
    }
    // decrement number of alive critter
    aliveApexPredator--;
  }

  // Helper method to check if selected grid is safe
  private static boolean isSafeGridElement(String element) {
    // Space is safe
    // Food is safe
    return element.equals(" ") || element.equals(".");
  }

  // Helper method to check grid and move in the vertical direction
  private static Direction moveVertical(int x, int y, String[][] grid) {
      // Check 5 grids up, and 5 grids down,
      // then go in direction
      for(int i = 0; i < 5; i++) {
        // direction oppressive critter is spotted
        boolean northSeen = false, southSeen = false; 
        // go down checking the grid, wrap around when necessary
        int southIndex = (y + (i + 1)) % grid[x].length; 
        if(!isSafeGridElement(grid[x][southIndex])) {
          southSeen = true;         // south is not safe
        } 
        // go up checking the grid
        int northIndex  = y - (i + 1); 
        if (northIndex < 0) {
          northIndex += grid[x].length;
        }

        if (!isSafeGridElement(grid[x][northIndex])) {
          northSeen = true;         // north is not safe

        }

        // Go in the direction where there is no nearby critter
        if(northSeen && !southSeen) { // only south is safe
          return Direction.SOUTH;
        } else if(!northSeen && southSeen) {  // only north is safe
          return Direction.NORTH;
        } else if (northSeen && southSeen) {  // both north and south aren't safe
          return Direction.CENTER;
        }
        // not seen in either, search the next grid.
      } // end of for loop
      
      return Direction.CENTER;
  }

  // Helper method to check grid and move across horizontal direction
  private static Direction moveHorizontal(int x, int y, String[][] grid) {
    for(int i = 0; i < 5; i++) {
       // direction oppressive critter is spotted
      boolean eastSeen = false, westSeen = false;
      // go to the right checking the grid
      int eastIndex = (x + (i + 1)) % grid.length;
      if(!isSafeGridElement(grid[eastIndex][y])) {
        eastSeen = true;
      } 
      // go to the left checking the grid
      int westIndex  = x - (i + 1); 
      if (westIndex < 0) {  // wrap around when necessary
        westIndex += grid.length;
      }

      if(!isSafeGridElement(grid[westIndex][y])) {
        westSeen = true;
      }

      // Go in the direction where there is no nearby critter
      if(eastSeen && !westSeen) {
        return Direction.WEST;
      } else if(!eastSeen && westSeen) {
        System.out.println("East");
        return Direction.EAST;
      } else if (eastSeen && westSeen) {
        return Direction.CENTER;
      }
      // not seen in either, search the next grid.
    } // end of for loop
    // sit tight if both directions are equally unsafe
    return Direction.CENTER;
  }

  // Helper method to move towards food when eating window opens
  private Direction eatingMovement(String[][] grid) {
    // move in direction of food if neightbor is food
    if(getNeighbor(Direction.NORTH).equals(".")) {
      return Direction.NORTH;
    } else if(getNeighbor(Direction.EAST).equals(".")) {
      return Direction.EAST;
    } else if(getNeighbor(Direction.SOUTH).equals(".")) {
      return Direction.SOUTH;
    } else if(getNeighbor(Direction.WEST).equals(".")) {
      return Direction.EAST;
    } else {  // move in a random direction
      switch((int) (Math.random() * 4)) {
         case 0 -> direction = Direction.NORTH;
         case 1 -> direction = Direction.EAST;
         case 2 -> direction = Direction.WEST;
         default -> direction = Direction.SOUTH;
        }
        return direction;
    }
  }

  // Helper method to mimic and camouflage as birds/vultures
  private static String mimimcBird(Direction direction) {
    if(direction == Direction.NORTH) {
      return "^";
    } else if(direction == Direction.EAST) {
      return ">";
    } else if(direction == Direction.SOUTH) {
      return "V";
    } else{
      return "<";
    }
  }

  // Helper method to mimic and camouflage as hippo
  private static String mimicHippo(){
    // random string numbers between 0 and 9 both inclsive
    return "" + ((int) (Math.random() * 10));
  }

  // Helper method to determine a critter is a bird/vulture
  private static boolean isBird(String critter) {
    if((critter.equals("^") || critter.equals(">") || critter.equals("V") || critter.equals("<"))) {
      return true;
    } else {
      return false;
    }
  }

  // Helper method to determine whether a critter is hippo
  private static boolean isHippo(String input) {
    // if the string is parseable as int,
    // then it is definitely a hippo
    try {
      Integer.parseInt(input);
      return true;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  // Helper method to count total number of critters alive, other than ApexPredator
  private static int countCritters(String[][] grid) {
    int aliveCritters = 0; // the total number of living critters 
    for(int row = 0; row < grid.length; row++) {
      for(int col = 0; col < grid[0].length; col++) {
        if(!grid[row][col].equals(" ") && !grid[row][col].equals(".") ) {
          aliveCritters++;
        }
      } // inner for loop
    } // outer for loop
    // number of critters alive other than my critters
    return aliveCritters - aliveApexPredator; 
  }
}