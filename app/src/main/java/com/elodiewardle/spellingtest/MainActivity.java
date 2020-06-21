package com.elodiewardle.spellingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> spellingWords;
    ArrayList<Boolean> correct;
    int index;

    Button next;
    EditText et1;
    TextToSpeech tts;
    Button speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        spellingWords = new ArrayList<String>();
        spellingWords.add("because");
        spellingWords.add("definitely");
        spellingWords.add("important");
        correct = new ArrayList<Boolean>();
        for (int i = 0; i < spellingWords.size(); i++){
            correct.add(false);
        }
        index = 0;

        speak = findViewById(R.id.speak);
        next = findViewById((R.id.next));

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener(){
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
        if (input.equals(spellingWords.get(index))){
            correct.set(index, true);
        }
    }

    public void nextWord(){
        et1.getText().clear();
        index++;
        speakWord();
    }

    public void resultsPage(){
        String[] words = spellingWords.toArray(new String[spellingWords.size()]);
        boolean[] results = new boolean[correct.size()];
        for (int i = 0; i < results.length; i++){
            results[i] = correct.get(i);
        }

        Intent intent = new Intent(getBaseContext(), ResultsActivity.class);
        intent.putExtra("words", words);
        intent.putExtra("results", results);
        startActivity(intent);
    }
}