import java.awt.Color;
//************************************************************************
// File: Bird.java         Assignment: 8
// 
// Author: dym7
//
// Class: Bird
// Dependencies: Sub class of Critter.java  
//
// Description  :  Bird template
//
//  Implements a critter that moves a clockwise square: first goes north 
//  3 times, then east 3 times, then south 3 times, then west 3 times, 
//  then repeat. This critter  never eats and roars if the opponent looks 
//  like an. Ant ("%"); otherwise pounces. It's color is always blue. The
//  critter looks different when it goes in different direction.
//
//************************************************************************

public class Bird extends Critter {
    protected Direction lastMove; // Direction of movement
    protected int moveCount; 

    // constructor
    public Bird() {
        lastMove = Direction.NORTH;
        moveCount = 0;
    }

    @Override
    // Roars if ecounters ant,
    // pounces for the rest
    public Attack fight(String opponent) {
        if(opponent.equals("%")) {
            return Attack.ROAR;
        } else {
            return Attack.POUNCE;
        }
    }

    @Override
    // Color's always blue
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    //first goes north  3 times, then east 3 times, then south 3 times, 
    // then west 3 times, then repeat
    public Direction getMove(String[][] grid) {
        // repeat every direction 3 times,
        // first 3 moves is north
        if (moveCount < 3) {
            moveCount++;
            return lastMove;
        }

        // second move is east
        if(lastMove.equals(Direction.NORTH)) {
            lastMove = Direction.EAST;
        } else if(lastMove.equals(Direction.EAST)) { // third move is south
            lastMove = Direction.SOUTH;
        } else if(lastMove.equals(Direction.SOUTH)) { // fourth move is west
            lastMove = Direction.WEST;
        } else {   // continues with first move of the next cycle
            lastMove = Direction.NORTH; 
        }
        moveCount = 1;  // reset move to 1 because one move of North already made
        return lastMove;    
    } // End of getMove()

    @Override
    public String toString() {
        if(lastMove.equals(Direction.NORTH)) {
            return "^";
        } else if(lastMove.equals(Direction.EAST)) {
            return ">";
        } else if(lastMove.equals(Direction.SOUTH)) {
            return "V";
        } else {
            return "<";
        } 
    }
}