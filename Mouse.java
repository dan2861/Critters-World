import java.awt.Color;

public class Mouse extends Critter {
    private final Direction[] moves = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    
    private static boolean[][] targets;
    private static boolean foodScarce;
    
    private boolean needsTarget;
    private int nextRow;
    private int nextCol;

    private int waitSteps;
    private int waitTime = 15;

    public Mouse() {        
        needsTarget = true;
    }

    // YOU DO: override eat(), getColor(), toString(), lose() methods
    // QUESTION: what needs updating if this mouse loses a fight?
    public boolean eat() {
        return true;
    }

    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    public String toString() {
        return "x";
    }

    // YOU DO: if a mouse dies, let all mice know their target is up for grabs
    public void lose() {
        targets[nextCol][nextRow] = false;
    }

    public Direction getMove(String[][] grid) {
        // initialize static grid targets
        if (targets == null) targets = new boolean[grid.length][grid[0].length];
        
        // grab nearby food, avoid nearby foes
        Direction moveNOW = baseInstincts();
        if (moveNOW != Direction.CENTER) return moveNOW;
        
        // move towards food target, else shelter in place
        return findFood(grid);
    }
 
    // complete grabFood() and evade() methods for baseInstincts
    private Direction baseInstincts() {
        // if waiting, log wait count
        if (waitSteps > 0) waitSteps--;

        // if neighbor is food, step on it!
        Direction moveNOW = grabFood();
        if (moveNOW != Direction.CENTER) return moveNOW;

        // if neighbor is an enemy, avoid it and shelter in place
        return evade();
    }

    // YOU DO: if food is one cell away, move towards it
    private Direction grabFood() {
        for (int i = 0; i < moves.length; i++) {
            if (getNeighbor(moves[i]).equals(".")) return moves[i];
        }
        return Direction.CENTER;
    }

    // YOU DO: if enemy is one cell away, move in the opposite direction and then wait
    private Direction evade() {
        for (int i = 0; i < moves.length; i++) {
            if (!getNeighbor(moves[i]).equals(" ") && !getNeighbor(moves[i]).equals("x")) {
                waitSteps = waitTime;   // update wait steps
                return moves[(i + 2) % moves.length];
            }
        }
        return Direction.CENTER;
    }

    private Direction findFood(String[][] grid) {
        boolean noTarget = !needsTarget;
        boolean hasTarget = getX() - nextCol == 0 && getY() - nextRow == 0; 
        boolean lateTarget = grid[nextCol][nextRow] != "."; 

        // YOU DO: use booleans above to determine whether we need a new target
        if (noTarget && (hasTarget || lateTarget)) {
            needsTarget = true; 
            targets[nextCol][nextRow] = false;
        }

        // find a new target if we need one
        if (needsTarget) findMin(grid);

        // if no target found: shelter in place!
        if (needsTarget) return Direction.CENTER;   

        return nextMove();
    }

    private void findMin(String[][] grid) {

        // track dist to nearest food if available
        int closest = Integer.MAX_VALUE;
        boolean updated = false;

        for (int i = 0; i < grid.length; i++) 
            for (int j = 0; j < grid[0].length; j++)

                // if food is not scarce, each mouse gets a unique food target
                if (grid[i][j].equals(".") && (!targets[i][j] || foodScarce)) {

                    // compare dist to nearest food
                    int dist = dist(i, getX(), getWidth()) + dist(j, getY(), getHeight()); 
                    if (dist < closest) {
                        updated = true;
                        closest = dist;

                        // update instance vars
                        nextCol = i;
                        nextRow = j;
                    } 
                }

        // found next target
        if (updated) {
            needsTarget = false;
            targets[nextCol][nextRow] = true;
        }
        // no target found: shelter in place!
        else {
            // let all mice know that food has become scarce
            foodScarce = true;
            waitSteps = waitTime;
        }
    }

    // YOU DO: return the minimum distance from mousePos to foodPos
    public int dist(int foodPos, int mousePos, int worldSize) {
        int inFrame = Math.abs(foodPos - mousePos);
        int wrapFrame = Math.min(worldSize - mousePos + foodPos, worldSize - foodPos + mousePos) + 1;
        return Math.min(inFrame, wrapFrame);
      }

    private Direction nextMove() {
        int slopeX = nextCol - getX();
        int slopeY = nextRow - getY();

        // step in x or y towards food pending dx <> dy 
        if (Math.abs(slopeX) > Math.abs(slopeY))
            if (slopeX > 0) return Direction.EAST;
            else return Direction.WEST;
        else
            if (slopeY > 0) return Direction.SOUTH;
            else return Direction.NORTH;
    }

    public void reset() {
        targets = null;
        foodScarce = false;
        needsTarget = true;
        waitSteps = 0;  
    }
}