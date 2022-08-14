import java.awt.Color;
//************************************************************************
// File: Ant.java         Assignment: 8
// 
// Author: dym7
//
// Class: Ant
// Dependencies: Sub class of Critter.java  
//
// Description  :  Ant template
//  
//  Implements a critter that moves in zigzag alternating between S & E 
//  or N & E. It always eats when it encouters food and scratch when 
//  fighting. It's string representation is "%" and appears as red.
//
//************************************************************************

public class Ant extends Critter {
    protected boolean walkSouth;
    protected Direction previousMove;

    public Ant(boolean walkSouth) {
        this.walkSouth = walkSouth;
        // So that we walk N/S on the first move.
        this.previousMove = Direction.EAST;
    }

    @Override
    // Ant always eats when it encounters food
    public boolean eat() {
        return true;
    }

    @Override
    // Ants always scratch
    public Attack fight(String opponent) {
        return Attack.SCRATCH;
    }
    
    @Override
    // Color always red
    public Color getColor() {
        return Color.RED;
    }

    @Override
    // Starts with a N/S direction, then alternates
    // between that after going eastwards
    public Direction getMove(String[][] grid) {
        if (previousMove == Direction.EAST) {
            if (walkSouth) {
                previousMove = Direction.SOUTH;
            } else {
                previousMove = Direction.NORTH;
            }
            return previousMove;
        } else {
            previousMove = Direction.EAST;
            return previousMove;
        }
    } // End of getMove()

    @Override
    // Ant is "%" in the grid
    public String toString() {
        return "%";
    }
}
