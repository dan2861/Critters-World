import java.awt.Color;
//************************************************************************
// File: Vulture.java         Assignment: 8
// 
// Author: dym7
//
// Class: Vulture
// Dependencies: Sub class of Bird.java (which extends Critter)
//
// Description  :  Vulture template
//
//  Implements a critter that looks, fights and moves like bird. Returns true 
//  if vulture is hungry, which is always true first time it's born. When fighting, 
//  vulture becomes hungry. Vulture is always black.
//
//************************************************************************

public class Vulture extends Bird {
    private boolean hungry;
    
    public Vulture() {
        this.hungry = true; // Vulture is initially hungry
    }

    @Override
    // Eats when hungry
    public boolean eat() {
        if(hungry) {
            hungry = false;
            return true;
        }
        return false;
    }

    @Override
    // Vulture is always black
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    // Roars if ecounters ant, pounces for the rest,
    // and becomes hungry in either case
    public Attack fight(String opponent) {
        hungry = true;
        if(opponent.equals("%")) {
            return Attack.ROAR;
        } else {
        
            return Attack.POUNCE;
        }
    }
}
