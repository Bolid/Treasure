package ru.omdroid.game.ChainTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainForm extends Activity implements View.OnClickListener {

    final String TAG = App.getInstance().getPackageName() + ".MainForm";
    WorkDB workDB;
    FinderChains finderChains;
    TextView tv;
    EditText etWord1, etWord2, etAddMyWord;

    String word1, word2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        InitializationDB initializationDB = new InitializationDB(getBaseContext());
        initializationDB.Initialized();
        initializationDB.close();

        etWord1 = (EditText)findViewById(R.id.word1);
        etWord2 = (EditText)findViewById(R.id.word2);
        etAddMyWord = (EditText)findViewById(R.id.etAddMyWord);

        Button but = (Button)findViewById(R.id.button);
        Button butAddMyWord = (Button)findViewById(R.id.butAddMyWord);

        tv = (TextView)findViewById(R.id.tvChain);

        but.setOnClickListener(this);
        butAddMyWord.setOnClickListener(this);

        workDB = new WorkDB();
        workDB.dbConnect();
        finderChains = new FinderChains(workDB);
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "Начало метода");
        switch (view.getId()){
            case R.id.button:
                tv.setText("");
                processStart();
                break;
            case R.id.butAddMyWord:
                writeMyWord(tv.getText().toString());
                break;
        }
        Log.i(TAG, "Конец метода");
    }

    private void processStart(){
        word1 = etWord1.getText().toString();
        word2 = etWord2.getText().toString();
        if (wordControl(word1))
            return;

        String requestIgnoreWord = WorkDB.F_WORD + " <> '" + word1 + "'";
        String chain =  finderChains.fastFindChain(word1, word2, requestIgnoreWord, "", 1);

        if (chain.equals(""))
            chain = finderChains.findChain(word1, word2, requestIgnoreWord, "", "", 0, 0);

        tv.setText(word1 + "\n" + tv.getText() + chain);
    }

    private void writeMyWord(String str){

        if (wordControl(etAddMyWord.getText().toString()))
            return;
        getRegExForControlMyWord(str);

    }

    private void getRegExForControlMyWord(String word1){
        String latestWord = convertChainStringToList(word1)[convertChainStringToList(word1).length-1];
        String regEx = "";
        for (int i = 0; i < latestWord.length(); i++){
            regEx = regEx + latestWord.substring(0, i) + "[А-Я]" + latestWord.substring(i+1) ;
            if (i < latestWord.length() - 1)
                regEx = regEx + "|";
        }
    }

    private String[] convertChainStringToList(String chain){
        return chain.split("\n");
        }

    private boolean wordControl(String word1){
        if (word1.length() != word2.length()){
            Toast.makeText(getBaseContext(), "Длина слов должна быть равной", Toast.LENGTH_LONG).show();
            return true;
        }
        if (word1.equals("") || word2.equals("")){
            Toast.makeText(getBaseContext(), "Введите слова", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}


