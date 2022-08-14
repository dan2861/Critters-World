//************************************************************************
// File: FireAnt.java         Assignment: 8
// 
// Author: dym7
//
// Class: FireAnt
// Dependencies: Sub class of Ant.java (which extends Critter)
//
// Description  :  Fire Ant template
//
//  Implements a critter of ant species so looks like ants and mimics ant 
//  fighting behaviour. However, fire ants report to a mound regularly and
//  mimic ant movement when they're not going to the mound.
// 
//************************************************************************

import java.awt.Color;

public class FireAnt extends Ant{
    // The maximum number of ants that could and have mated
    private static int maxNumToMate; 
    // Number of unmated fireants
    private static int numToMate; 
    // x-position of the mound
    private static int moundX;  
    // y-position of the mound
    private static int moundY;  
    // the alternating path to the mound
    private static int cycleLength = 1; 
    // Running sum number of visits to the mound
    private static double visits; 
    // No mound exists at the beginning
    private static boolean thereIsMound = false; 

    // Steps each fireant takes
    private int steps; 
    // default false, haven't mated when constructed
    private boolean mated;
    // Is on journey to mound
    private boolean toMound;

    public FireAnt(boolean walkSouth) {
        super(walkSouth);   // call ant constructor
        mated = false;      // fire ant is born a virgin
        toMound = false;    // initially mimics ant behaviors for some number of steps
        maxNumToMate++;     // the total number of virgin ants that have ever existed
        numToMate++;        // every new ant is a virgin
        steps = 1;          // takes a single step at birth
    }

    @Override
    public boolean eat() {
        return mated;
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    } 

    @Override
    public Direction getMove(String[][] grid) {

        // Handles initial case where there is no mound 
        if(!thereIsMound) {
            moundX = (int) (Math.random() * getWidth()) + 1;//rand.nextInt(getWidth());
            moundY = (int) (Math.random() * getHeight()) + 1;//rand.nextInt(getHeight());
            thereIsMound = true;
        }
        
        //The mound location will move to a new random location when the running sum number 
        //of visits is at least half of numToMate.
        if( visits >= (numToMate / 2.0)) {
            // generate random coordinates in the critter world
            moundX = (int) (Math.random() * getWidth()) + 1; 
            moundY = (int) (Math.random() * getHeight()) + 1;
            visits = 0; // reset the visits to the new mound
        }

        // Fire ants that have mated are called to report back to the 
        // mound every 1.5 * maxNumToMate steps.
        int matedToMoundSteps = (int)1.5 * maxNumToMate;

        // Fire ants that have not mated are called to report to the mound 
        // every numToMate steps, but at least every 2 steps.
        int unmatedToMoundSteps = Math.max(numToMate, 2);
        
        // Compute if going to mound
        if(mated && numToMate >= 4) {
            // mated ants are called every 1.5 * maxNumToMate steps
            if(steps % matedToMoundSteps == 0) {
                toMound = true;
            } 

            // if mound is reached, then stop to mimic ant behaviour
            if(moundX == getX() && moundY == getY()) {
                visits = visits + 0.5;
                toMound = false;
            }
        } else if(!mated && numToMate >= 4) {
            // unmated ants are called every numToMate steps, and at least twice
            if(steps % unmatedToMoundSteps == 0) {
                toMound = true;
            } 

            // if mound is reached, then stop to mimic ant behaviour
            if(moundX == getX() && moundY == getY()) {
                visits++;
                toMound = false;
            }
        } else { 
            // when there are less than 4 ants to mate, stop going to mound
            toMound = false;
        }

        // Fire ants going to the mound
        if(toMound) {
            return toMound(moundX, moundY);
        } else { // fire ants not going to the mound mimic ant behavior
            steps++;
            return super.getMove(grid);
        }
    }   // End of getMove()

    @Override
    public void mate() {
        // the number of unmated ants decreases
        mated = true;
        numToMate--;
    }

    @Override
    public void lose() {
        // Virgin fire ant that lost can't mate
        if (!mated) {
            numToMate--;
        }
    }

    // Helper function to go in the direction of the mound
    public Direction toMound(int moundX, int moundY) {
        if(cycleLength % 2 == 1) {
            cycleLength++;
            if(moundX - getX() > 0) {
                return Direction.EAST;
            } else if(moundX - getX() < 0) {
                return Direction.WEST;
            } else {
                return Direction.CENTER;
            }
        } else {
            cycleLength = 1;
            if(moundY - getY() > 0) {
                return Direction.SOUTH;
            } else if(moundY - getY() < 0) {
                return Direction.NORTH;
            } else {
                return Direction.CENTER;
            }
        }
    }
}