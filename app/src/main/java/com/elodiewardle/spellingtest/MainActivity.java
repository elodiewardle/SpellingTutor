package com.elodiewardle.spellingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> spellingWords;
    ArrayList<Boolean> correct;
    ArrayList<String> incorrectWords;
    int index;

    Button next;
    EditText et1;
    TextToSpeech tts;
    Button listen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.hasExtra("words")) {
            spellingWords = intent.getStringArrayListExtra("words");
        } else {
            spellingWords = new ArrayList<String>();
            spellingWords.add("because");
            spellingWords.add("definitely");
            spellingWords.add("important");
        }
        correct = new ArrayList<Boolean>();
        for (int i = 0; i < spellingWords.size(); i++){
            correct.add(false);
        }
        incorrectWords = new ArrayList<String>();
        index = 0;

        listen = findViewById(R.id.listen);
        next = findViewById((R.id.next));

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                }
                speakWord();
            }
        });

        listen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                speakWord();
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkCorrect();
                if (index < spellingWords.size() - 1){
                    nextWord();
                }
                else{
                    resultsPage();
                }
            }
        });
    }

    public void speakWord(){
        String wordToSpeak = spellingWords.get(index);
        tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void checkCorrect(){
        et1 = (EditText)findViewById(R.id.editText1);
        String input = et1.getText().toString();
        input = input.toLowerCase();
        if (input.equals(spellingWords.get(index))){
            correct.set(index, true);
            incorrectWords.add("");
        }
        else{
            incorrectWords.add(input);
        }
    }

    public void nextWord(){
        et1.getText().clear();
        index++;
        speakWord();
    }

    public void resultsPage(){
        String[] words = spellingWords.toArray(new String[spellingWords.size()]);
        String[] wordsMissed = incorrectWords.toArray(new String[incorrectWords.size()]);
        boolean[] results = new boolean[correct.size()];
        for (int i = 0; i < results.length; i++){
            results[i] = correct.get(i);
        }

        Intent intent = new Intent(getBaseContext(), ResultsActivity.class);
        intent.putExtra("words", words);
        intent.putExtra("wordsMissed", wordsMissed);
        intent.putExtra("results", results);
        startActivity(intent);
    }
}