import java.awt.Color;
import java.lang.Math;
//************************************************************************
// File: Hippo.java         Assignment: 8
// 
// Author: dym7
//
// Class: Hippo
// Dependencies: Sub class of Critter.java  
//
// Description  :  Hippo template
//
//  Implements a critter that chooses a random direction (north, south, 
//  east, or west) and moves in that direction for the next 5 steps, then 
//  chooses a new random direction and repeats. It appears gray in color,
//  if it is hungry and white if not, while it's string representation is
//  the number of pieces of food this Hippo still wants to eat.
//
//************************************************************************

public class Hippo extends Critter {
    private int hunger; // maximum number of food eate
    private Direction direction; // random direction taken for 5 steps
    private int steps; // number of steps taken


    public Hippo(int hunger) {
        this.hunger = hunger; 
        steps = 0;  // new hippo has taken no steps
        setToRandomDirection(); // initialize hippo going in radom direction
    }

    @Override
    // Returns true first hunger of times
    public boolean eat() {
        // Hunger decreases when hippo eats
        if(hunger > 0) {
            hunger--;
            return true;
        }
        return false;
    }

    @Override
    public Color getColor() {
        // Hippo is gray when hungry
        if(hunger > 0) {
            return Color.GRAY;
        }
        // Hippo white if not hungry
        return Color.WHITE;
    }

    @Override
    // Hungry hippo, scratches while a non-hungry hippo pounces
    public Attack fight(String opponent) {
        if(hunger > 0) {
            return Attack.SCRATCH;
        }
        return Attack.POUNCE;
    }
    
    @Override
    public Direction getMove(String[][] grid) {
        // Move five times in direction
        if(steps < 5) {
            steps++;
            return direction;
        }

        // Change to random direction.
        setToRandomDirection();
        steps = 1;
        return direction;
    }

    @Override
    // hippo is the number of pieces of food it still wants to eat
    public String toString() {
        return "" + hunger;
    }

    // Helper function that chooses a random direction
    private void setToRandomDirection() {
        switch((int) (Math.random() * 4)) {
            case 0 -> direction = Direction.NORTH;
            case 1 -> direction = Direction.EAST;
            case 2 -> direction = Direction.SOUTH;
            case 3 -> direction = Direction.WEST;
        }
    }
}
