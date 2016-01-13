
package com.fydp.myoralvillage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class Level1ActivityGameQA extends ActionBarActivity {

    public int correctAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gameqa);
        startNewRoundGame2();
    }


    public void startNewRoundGame2() {
        generateFinger();
    }

    public void generateFinger() {
        Random r = new Random();
        correctAnswer=r.nextInt(10)+1;

        String filename = "game1_qa_fingers"+correctAnswer;
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());

        displayFinger(img_id);
    }

    public void displayFinger(int img_id) {
        ImageView iv = (ImageView) findViewById(R.id.img_hands);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        iv.requestLayout();
        iv.getLayoutParams().height = (int)(screenHeight*0.5);
        iv.getLayoutParams().width = (int)(screenWidth*0.5);
        iv.setImageResource(img_id);
        iv.setVisibility(View.VISIBLE);

        generateAnswers();
    }

    public void generateAnswers() {
        Random r = new Random();
        int wrongAnswer1 = -1;
        int wrongAnswer2 = -1;
        do {
            wrongAnswer1 = r.nextInt(10) + 1;
        } while(wrongAnswer1==correctAnswer);
        do {
            wrongAnswer2 = r.nextInt(10) + 1;
        } while(wrongAnswer2==correctAnswer || wrongAnswer2==wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game1_qa_answer"+wrongAnswer1;
        filenames[1] = "game1_qa_answer"+wrongAnswer2;
        filenames[2] = "game1_qa_answer"+correctAnswer;

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(filenames, takenPositions);

    }

    public void displayAnswers(String[] filenames, int[] takenPositions) {
        for (int i = 0; i < filenames.length; i++) {

            Random answerR = new Random();
            int answerPosition = -1;
            if (i==0) {
                answerPosition = answerR.nextInt(3);
            } else {
                do {
                    answerPosition = answerR.nextInt(3);
                } while (answerPosition==takenPositions[0]||answerPosition==takenPositions[1]);
            }
            takenPositions[i]=answerPosition;

            int img_id = getResources().getIdentifier(filenames[i], "drawable", getPackageName());
            String imgView_name = "img_answer"+answerPosition;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int screenHeight = metrics.heightPixels;
            int screenWidth = metrics.widthPixels;

            iv.requestLayout();
            iv.getLayoutParams().height = (int)(screenHeight*0.2);
            iv.getLayoutParams().width = (int)(screenWidth*0.3);
            iv.setImageResource(img_id);
            iv.setTag(filenames[i]);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public void checkAnswer(View v) {
        ImageView iv = (ImageView) findViewById(v.getId());
        String thisImage = (iv.getTag()).toString();
        int imgFileNum = Integer.parseInt((thisImage.toString()).substring(15));

        if (imgFileNum==correctAnswer) {
            startNewRoundGame2();
        }
    }
}