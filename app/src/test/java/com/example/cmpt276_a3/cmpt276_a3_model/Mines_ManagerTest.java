package com.example.cmpt276_a3.cmpt276_a3_model;

import java.util.Random;

class Mines_ManagerTest {
    Mines_Manager mines_manager = Mines_Manager.getInstance();

    @org.junit.jupiter.api.Test
    void display2DArrayTest(){
        int randomMines;
        int currentRow = mines_manager.getRow();
        int currentCol = mines_manager.getColumn();
        Random random = new Random();

        mines_manager.generateNewMines();
        mines_manager.display2DArray();
        System.out.println("---------------------------------------------");

        while(true){
            randomMines = random.nextInt(currentRow * currentCol);
            int randomRow = randomMines / currentCol;
            int randomCol = randomMines / currentRow;

            if(mines_manager.isGold(randomRow, randomCol)){
                mines_manager.updateMine(randomRow, randomCol);
                System.out.println("Row and Col is: " + randomRow + ", " + randomCol);
                break;
            }
        }

        mines_manager.display2DArray();
    }

//    @org.junit.jupiter.api.Test
//    void getInstance() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void generate_Mines() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void display_2D_Array() {
//    }
}
