import java.util.Random;
import java.awt.Color;

public class Octopus extends Critter {

    // build this String with each new Critter we encounter
    private String nearby = "";

    // list of possible directions we can look for neighbors
    private final Direction[] moves = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    
    // WE DO: always pounce
    public Attack fight(String opponent) {
		return Attack.POUNCE;
	}

    // WE DO: always yellow
    public Color getColor() {
        return Color.YELLOW;
    }

    // WE DO: scan the area for other Critters and add to String nearby
    public Direction getMove(String[][] grid) {
        int sz = 5;
        nearby = "";
        for (int i = getX() - sz; i < getX() + sz; i++){
            for (int j = getY() - sz; j < getY() + sz; j++){
                int x = Math.floorMod(i, getWidth());
                int y = Math.floorMod(j, getHeight());
                if (isEnemy(grid[x][y])) nearby += grid[x][y]; 
            }
        }
        return Direction.CENTER;
    }

    // WE DO: mimic neighbor, else pose as a random nearby Critter, else "#"
	public String toString() {
        // WE DO: if neighbor is an enemy, mimic them
        for (int i = 0; i < moves.length; i++) {
            String tmp = getNeighbor(moves[i]);
            if (isEnemy(tmp)) return tmp;
        }

        // no critters nearby -> relax
        if (nearby.equals("")) return "#";

        // WE DO: pose as random nearby critter
        Random rand = new Random();
        int index = rand.nextInt(nearby.length());
        return nearby.substring(index, index+1);
	}

    // WE DO: determine if String test contains an enemy Critter
    private boolean isEnemy(String test) {
        return !test.equals(" ") 
                && !test.equals(".")
                && !test.equals("#");
    }

    public void reset() {
        nearby = "";
    }
}


