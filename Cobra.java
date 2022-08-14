import java.awt.Color;
import java.util.Random;
//************************************************************************
// File: Cobra.java         Assignment: 8
// 
// Author: dym7
//
// Class: Cobra
// Dependencies: Sub class of Snake.java (which extends Critter)
//
// Description  :  Cobra template
//
//  Implements a critter of snake species. Cobras recognize all standard Critters 
//  by their toString display types, and choose the move that is most likely to win
//  against that type of Critter. After winning cobras clebrate by changing their 
//  color.
//
//************************************************************************

public class Cobra extends Snake {
    // Color when not celebrating
    private static final Color STANDARD_COLOR = new Color(128, 50, 20); 
    // Color when celebrating
    private static final Color CELEBRATING_COLOR = new Color(255, 50, 20); 
    // radius of the celebrating grid
    private static final int RADIUS = 9; 
    
    // initialize to -1 instead of 0, so that there is no celebration
    // when there hasn't been a single victory
    private static int latestWinX = -1;  
    private static int latestWinY = -1;
    
    private boolean celebrating;
    
    public Cobra() {
        celebrating = false; // don't celebrate initially
    }

    @Override
    // Cobra recognizes ant, fire ant, bird, vulture and hippo, and chooses
    // the move that is most likely to win against each type of Critter
    public Attack fight(String opponent) {
        // move against ants and fire ants
        if(opponent.equals("%")) {
            return Attack.ROAR;
        } else if(opponent.equals("^") || opponent.equals(">") 
                || opponent.equals("V") || opponent.equals("<")) { 
            // moves against birds and vultures
            return Attack.SCRATCH;
        } else if(isHippo(opponent)) {
            // moves against hippos
            if(Integer.parseInt(opponent) == 0) {
                return Attack.ROAR;
            } else {
                return Attack.SCRATCH;
            }
        } else {
            // random moves for unrecognized critters
            Random rand = new Random();
            int choice = rand.nextInt(2);
            if (choice == 0) {
                return Attack.POUNCE;
            } else {
                return Attack.ROAR;
            }
        }
    } // end of fight()
    
    @Override
    // changes color when celebrating
    public Color getColor() {
        if (celebrating) {
            return CELEBRATING_COLOR;
        } else {
            return STANDARD_COLOR;
        }
    } // end of getColor()
    
    @Override
    // Cobra moves like snake, but celebrates when near the spot 
    // of the latest victorious cobra fight
    public Direction getMove(String[][] grid) {
        // latestWinX is in the Box.
        boolean inBoxX = false;
        // latestWinY is in the Box.
        boolean inBoxY = false;

        // The indices of the 19x19 box.
        int startX = startIndex(getX(), getWidth());
        int endX = endIndex(getX(), getWidth());
        int startY = startIndex(getY(), getHeight());
        int endY = endIndex(getY(), getHeight());
        
        // Checks case where critter world is less than the 
        // 19x19 celebration grid
        if (getWidth() <= 19) { // Small world case x
            inBoxX = true;
        } else if(startX <= endX) {  // non wrap around case for x
            inBoxX = (latestWinX >= startX && latestWinX <= endX);
        } else { // Wrap around case for x
            inBoxX = !(latestWinX > endX && latestWinX < startX);
        }
        
        if (getHeight() <= 19) { // Small world case y
            inBoxY = true;
        } else if(startY <= endY) { // non wrap around case for y
            inBoxY = (latestWinY >= startY && latestWinX <= endY);
        } else { // Wrap around case for x
            inBoxY = !(latestWinY > endY && latestWinY < startY);
        }

        // Celebrate only after there is at least one victory,
        // and cobra is inside the 19x19 box of celebration
        celebrating = (latestWinX >=0) && (latestWinY >= 0) && (inBoxX && inBoxY);
        return super.getMove(grid);
    } // end of getMove()

    @Override
    // When victorious, mark the spot of victory
    public void win() {
        latestWinX = getX();
        latestWinY = getY();
    }

    // Helper method to determine the left/top index of the 19x19 square
    private static int startIndex(int center_index, int grid_length) {
        // Handle wrap around.
        if((center_index - 9) < 0)  {
            return  (center_index - RADIUS) + grid_length;
        } else {
            return (center_index - RADIUS);
        }
    }

    // Helper method to determine the right/bottom index of the 19x19 square
    private static int endIndex(int center_index, int grid_length) {
        // Use mod to handle wrap around.
        return (center_index + RADIUS) % grid_length;
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
}