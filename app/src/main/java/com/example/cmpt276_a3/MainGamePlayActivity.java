package com.example.cmpt276_a3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276_a3.cmpt276_a3_model.Mines_Manager;
import com.example.cmpt276_a3.cmpt276_a3_model.Score_Watcher;

/*
    Gold
    https://www.mining-technology.com/wp-content/
    uploads/sites/8/2019/10/b5.1024_0_1-e1570195581925.jpg

    Lots of gold
    https://encrypted-tbn0.gstatic.com/images?q=tbn%
    3AANd9GcSWNHz_vIAdCBDdEtuurSMy7w35iNFvgcLGs2RwNf1ZqwBiuSQE
 */


/**
 * Main game activity:
 *      In charge of populate the buttons for main game play as well
 * as showing information of remaining hidden mines, total mines and highest score.
 */
public class MainGamePlayActivity extends AppCompatActivity {
    private static int numRows;
    private static int numCols;
    private boolean isWinned;
    private Button buttons[][];
    private Mines_Manager myMinesManager;
    private Score_Watcher score_watcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gameplay);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide state bar
        isWinned = false;
        myMinesManager = Mines_Manager.getInstance();
        score_watcher = Score_Watcher.getInstance();
        numRows = myMinesManager.getRow();
        numCols = myMinesManager.getColumn();
        buttons = new Button[numRows][numCols];
        myMinesManager.generateNewMines();
        score_watcher.getSavedScore(getApplicationContext());

        populateMines();
        updateTextViewInfo();
    }

    private void updateTextViewInfo() {
        int tempScore;

        TextView textView = findViewById(R.id.numMinesFound_textView);
        textView.setText(String.format(getResources().getString(R.string.numMinesFound),
                myMinesManager.getNumberOfMinesFound(), myMinesManager.getNumberOfMines()));

        textView = findViewById(R.id.scanUsed_textView);
        textView.setText(String.format(getResources().getString(R.string.scansUsed),
                myMinesManager.getNumberOfScans()));

        textView = findViewById(R.id.totalGamePlayed_textView);
        textView.setText(String.format(getResources().getString(R.string.totalGamesPlayed),
                score_watcher.getNumGamesPlayed()));

        textView = findViewById(R.id.minScanUsed_textView);
        switch (myMinesManager.getRow()){
            case 4:
                tempScore = score_watcher.getMaxScore(0, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()));
                break;
            case 5:
                tempScore = score_watcher.getMaxScore(1, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()));
                break;
            case 6:
                tempScore = score_watcher.getMaxScore(2, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()));
                break;
            default:
                 tempScore = Integer.MAX_VALUE;
        }

        if(tempScore == Integer.MAX_VALUE)
            textView.setText(getResources().getString(R.string.minScanUsedNotAvailable));
        else
            textView.setText(String.format(getResources().getString(R.string.minScanUsed),
                tempScore));
    }

    private void setDefaultImageToAllButtons(int row, int col, int drawableID){
                Button myButton = buttons[row][col];
                int newWidth = myButton.getWidth();
                int newHeight = myButton.getHeight();

                Bitmap originalBitMap = BitmapFactory.decodeResource(
                        getResources(), drawableID);
                Bitmap scaledBitMap = Bitmap.createScaledBitmap(
                        originalBitMap, newWidth, newHeight, true);
                Resources resource = getResources();

                myButton.setBackground(new BitmapDrawable(resource, scaledBitMap));
    }

    private void lockButtonSizes() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void populateMines() {
        TableLayout tableLayout = findViewById(R.id.mine2DTable);
        final MediaPlayer mediaPlayerGold = MediaPlayer.create(this, R.raw.gold_sound);
        final MediaPlayer mediaPlayerSoil = MediaPlayer.create(this, R.raw.find_none_mine_sound);

        for(int row = 0; row < numRows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            tableLayout.addView(tableRow);

            for(int col = 0; col < numCols; col++){

                final int FINAL_ROW = row;
                final int FINAL_COL = col;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setPadding(0, 0, 0, 0);
                button.setTextColor(Color.WHITE);
                button.setTypeface(null, Typeface.BOLD);

                if(myMinesManager.isGold(row, col))
                    button.setTextSize(20);
                else
                    button.setTextSize(20);


                // Change button sound
                button.setSoundEffectsEnabled(false);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myMinesManager.isGold(FINAL_ROW, FINAL_COL))
                            mediaPlayerGold.start();
                        else
                            mediaPlayerSoil.start();
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }

        lockButtonSizes();
    }

    private void gridButtonClicked(int tempRow, int tempCol){
        if(!myMinesManager.isRevealed(tempRow, tempCol)){
            myMinesManager.updateMine(tempRow, tempCol);
            updateAllButtons();
            updateTextViewInfo();

            if(myMinesManager.getNumberOfMinesFound() == myMinesManager.getNumberOfMines()
                    && !isWinned){
                isWinned = true;
                FragmentManager manager = getSupportFragmentManager();
                updateSavedData();
                CongratulationsFragment congratulationsFragment = new CongratulationsFragment();
                congratulationsFragment.show(manager, "CongratulationsDialog");
            }
        }
        else if(myMinesManager.isRevealed(tempRow, tempCol) && !myMinesManager.isScanned(tempRow, tempCol)){
            myMinesManager.updateMine(tempRow, tempCol);
            updateAllButtons();
            updateTextViewInfo();
        }
    }

    private void updateAllButtons(){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols ;j++){
                if(myMinesManager.isRevealed(i, j)){
                    Button button = buttons[i][j];

                    if(myMinesManager.isGold(i, j)){
                        lockButtonSizes();
                        setDefaultImageToAllButtons(i, j, R.drawable.gold);

                        if(myMinesManager.isScanned(i, j))
                            button.setText("" + myMinesManager.getValue(i, j));
                    }
                    else{
                        button.setBackgroundColor(Color.TRANSPARENT);
                        button.setText("" + myMinesManager.getValue(i, j));
                    }
                }
            }
        }
    }

    private void updateSavedData() {
        switch (myMinesManager.getRow()){
            case 4:
                if(score_watcher.getMaxScore(0, scoreMinesToNumConverter(myMinesManager.getNumberOfMines())) > myMinesManager.getNumberOfScans())
                    score_watcher.setMaxScore(0, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()), myMinesManager.getNumberOfScans());
                break;
            case 5:
                if(score_watcher.getMaxScore(1, scoreMinesToNumConverter(myMinesManager.getNumberOfMines())) > myMinesManager.getNumberOfScans())
                    score_watcher.setMaxScore(1, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()), myMinesManager.getNumberOfScans());
                break;
            case 6:
                if(score_watcher.getMaxScore(2, scoreMinesToNumConverter(myMinesManager.getNumberOfMines())) > myMinesManager.getNumberOfScans())
                    score_watcher.setMaxScore(2, scoreMinesToNumConverter(myMinesManager.getNumberOfMines()), myMinesManager.getNumberOfScans());
                break;
        }

        score_watcher.incNumGamesPlayed();
        score_watcher.saveScore(getApplicationContext());
    }

    private int scoreMinesToNumConverter(int numMines){
        switch (numMines){
            case 6:
                return 0;
            case 10:
                return 1;
            case 15:
                return 2;
            case 20:
                return 3;
            default:
                return -1;
        }
    }

    public static Intent makeIntentForMainGameActivity(Context context){
        return new Intent(context, MainGamePlayActivity.class);
    }
}
