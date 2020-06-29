package com.example.cmpt276_a3.cmpt276_a3_model;

/**
 * This is a class that holds a single mine/soil and
 * contains a variable says whether this mine is revealed or not
 */
public class Mine {
    public int value;
    public boolean revealed;
    public boolean isGold;
    public boolean isScanned;

    public void Mine(){
        value = 0;
        revealed = false;
        isGold = false;
        isScanned = false;
    }

    @Override
    public String toString() {
        return "[" + value + "," + revealed + "]";
    }
}
