package com.elodiewardle.spellingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    private static final String BULLET_SYMBOL = "&#8226";
    String[] spellingWords;
    boolean[] correct;
    int numCorrect;
    TextView tvr;
    TextView tvc;
    TextView tvi;
    Button restart;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        spellingWords = intent.getStringArrayExtra("words");
        correct = intent.getBooleanArrayExtra("results");

        numCorrect = calculateResults();
        tvr = (TextView) findViewById(R.id.results);
        String resultsMessage = "You got " + numCorrect + " out of " + correct.length + " of the answers correct.";
        tvr.setText(resultsMessage);

        tvc = (TextView) findViewById(R.id.correct_words);
        tvi = (TextView) findViewById(R.id.incorrect_words);
        String correctWords = "Correct Words:\n";
        String incorrectWords = "Incorrect Words:\n";
        for (int i = 0; i < correct.length; i++){
            if (correct[i] == true){
                correctWords += "   " + Html.fromHtml(BULLET_SYMBOL) + " " + spellingWords[i] + "\n";
            }
            else{
                incorrectWords += "   " + Html.fromHtml(BULLET_SYMBOL) + " " + spellingWords[i] + "\n";
            }
        }
        tvc.setText(correctWords);
        tvi.setText(incorrectWords);
    }

    public int calculateResults(){
        int numCorrect = 0;
        for (int i = 0; i < spellingWords.length; i++){
            if (correct[i] == true){
                numCorrect++;
            }
        }
        return numCorrect;
    }
}