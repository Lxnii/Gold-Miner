package com.example.cmpt276_a3.cmpt276_a3_model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * This class stores and get the game stats from files
 * It will also save game status in the file so the data won't lost
 */
public class Score_Watcher {
    private static final Score_Watcher ourInstance = new Score_Watcher();
    private int numGamesPlayed;
    private String tagName;
    private final String NUM_GAMES_PLAYED = "numGamesPlayed";
    private final int row = 3;
    private final int column = 4;
    private int scores[][];

    public static Score_Watcher getInstance(){
        return ourInstance;
    }

    private Score_Watcher(){
        scores = new int[row][column];
        resetAllValues();
    }

    public int getMaxScore(int userRow, int userColumn) {
        return scores[userRow][userColumn];
    }


    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    public void setMaxScore(int userRow, int userColumn, int score) {
        scores[userRow][userColumn] = score;
    }

    public void incNumGamesPlayed() {
        this.numGamesPlayed++;
    }

    public void resetAllValues(){
        numGamesPlayed = 0;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                scores[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public void saveScore(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo",
                context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                tagName = "" + i + j;
                Log.i("Save", tagName);
                editor.putInt(tagName, scores[i][j]);
            }
        }

        editor.putInt(NUM_GAMES_PLAYED, numGamesPlayed);
        editor.apply();
    }

    public void getSavedScore(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo",
                context.MODE_PRIVATE);

        if(sharedPreferences.getInt("00", -1) != -1){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < column; j++){
                    tagName = "" + i + j;
                    Log.i("Get", tagName);
                    scores[i][j] = sharedPreferences.getInt(tagName, -1);
                }
            }
            numGamesPlayed = sharedPreferences.getInt(NUM_GAMES_PLAYED, -1);
        }
    }
}
