package com.example.cmpt276_a3.cmpt276_a3_model;

import java.util.Random;

/**
 * A manager class that create the mines based on the rows and columns
 * User can use getters and setters to set and get number of rows, columns,
 * mines found.
 */
public class Mines_Manager {
    private static final Mines_Manager ourInstance = new Mines_Manager();
    private int row;
    private int column;
    private int numberOfScans;
    private int numberOfMines;
    private int numberOfMinesFound;
    private Mine myMines[][];

    public static Mines_Manager getInstance() {
        return ourInstance;
    }

    private Mines_Manager(){
        row = 4;
        column = 6;
        numberOfMines = 6;
        numberOfScans = 0;

        numberOfMinesFound = 0;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNumberOfScans() {
        return numberOfScans;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getNumberOfMinesFound() {
        return numberOfMinesFound;
    }

    public void setMineProperties(int userRow, int userColumn, int userNumberOfMines){
        row = userRow;
        column = userColumn;
        numberOfMines = userNumberOfMines;
    }

    private void resetMines(){
        numberOfScans = 0;
        numberOfMinesFound = 0;

        // Initialize all elements of 2D array to 0
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                myMines[i][j].value = 0;
                myMines[i][j].revealed = false;
            }
        }
    }

    public void generateNewMines(){
        int tempMinesCount = numberOfMines;
        myMines = new Mine[row][column];
        Random random = new Random();
        int randomRow;
        int randomCol;
        int randomMines;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                myMines[i][j] = new Mine();
            }
        }

        resetMines();

        // Random assign mines to the 2D array
        while(tempMinesCount > 0){
            randomMines = random.nextInt(row * column);
            randomRow = randomMines / column;
            randomCol = randomMines % column;
            if(!myMines[randomRow][randomCol].isGold){
                myMines[randomRow][randomCol].isGold = true;
                tempMinesCount--;
            }
        }

        scanMines();
    }

    private void scanMines(){
        // Populate number of mines to each non-mine cell
        int count;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                count = 0;

                // Check mines for the corresponding row
                for(int temp = 0; temp < row; temp++){
                    if (myMines[temp][j].isGold && !myMines[temp][j].revealed)
                        count++;
                }

                // Check mines for the corresponding column
                for(int temp = 0; temp < column; temp++){
                    if (myMines[i][temp].isGold&& !myMines[i][temp].revealed)
                        count++;
                }

                myMines[i][j].value = count;
            }
        }
    }

    public boolean isGold(int userRow, int userColumn){
        return myMines[userRow][userColumn].isGold;
    }

    public boolean isRevealed(int userRow, int userColumn){
        return myMines[userRow][userColumn].revealed;
    }

    public boolean isScanned(int userRow, int userColumn){
        return myMines[userRow][userColumn].isScanned;
    }

    public int getValue(int userRow, int userColumn){
        return myMines[userRow][userColumn].value;
    }

    public void updateMine(int userRow, int userColumn){
        if(!myMines[userRow][userColumn].revealed){
            myMines[userRow][userColumn].revealed = true;

            if(myMines[userRow][userColumn].isGold)
                numberOfMinesFound++;
            else
                numberOfScans++;
        }
        else if(isGold(userRow, userColumn) && !myMines[userRow][userColumn].isScanned){
            myMines[userRow][userColumn].isScanned = true;
            numberOfScans++;
        }

        scanMines();
    }

    // Print 2D array for debug purpose
    public void display2DArray()
    {
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                System.out.print(myMines[i][j].toString() + " ");
            }
            System.out.println();
        }
    }
}
