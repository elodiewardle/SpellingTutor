package com.elodiewardle.spellingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateListActivity extends AppCompatActivity {
    private static final String BULLET_SYMBOL = "&#8226";
    Button addWord;
    Button finish;
    EditText wordInput;
    TextView wordList;
    String wordListText;
    ArrayList<String> spellingWords = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        Intent intent = getIntent();

        addWord = findViewById(R.id.add_word);
        finish = findViewById(R.id.finish);
        wordInput = (EditText) findViewById((R.id.word_input));
        wordList =  (TextView) findViewById(R.id.wordlist);

        wordListText = "Word List:\n";
        wordList.setText(wordListText);

        addWord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateList();
                wordListText = updateText(wordListText);
                wordInput.getText().clear();
            }
        });

        finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                intent1.putExtra("words", spellingWords);
                startActivity(intent1);
            }
        });
    }

    public void updateList(){
        String input = wordInput.getText().toString();
        spellingWords.add(input);
    }

    public String updateText(String wordListText){
        wordListText += "   " + Html.fromHtml(BULLET_SYMBOL) + " " + spellingWords.get(spellingWords.size() - 1) + "\n";
        wordList.setText(wordListText);
        return wordListText;
    }

}