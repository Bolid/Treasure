package ru.omdroid.game.ChainTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        String chain = word1 + "\n";

        tv.setText(chain);
        String requestIgnoreWord = WorkDB.F_WORD + " <> '" + word1 + "'";

        chain =  finderChains.fastFindChain(word1, word2, requestIgnoreWord, chain, 1);

        if (chain.equals(word1 + "\n"))
            chain = finderChains.findChain(word1, word2, requestIgnoreWord, chain, "", 0, 0);

        tv.setText(chain);
    }

    private void writeMyWord(String str){

        if (wordControl(etAddMyWord.getText().toString()) & wordControlUser(etAddMyWord.getText().toString(), str))
            return;

        tv.setText(tv.getText() + etAddMyWord.getText().toString());
        Log.i(TAG, getRegExForControlMyWord(str));
        Log.i(TAG, String.valueOf(etAddMyWord.getText().toString().matches(getRegExForControlMyWord(str)) & !str.contains(etAddMyWord.getText().toString())));

    }

    private String getRegExForControlMyWord(String word1){
        String latestWord = convertChainStringToList(word1)[convertChainStringToList(word1).length-1];
        String regEx = "";
        for (int i = 0; i < latestWord.length(); i++){
            regEx = regEx + latestWord.substring(0, i) + "[А-Я]" + latestWord.substring(i+1) ;
            if (i < latestWord.length() - 1)
                regEx = regEx + "|";
        }
        return regEx;
    }

    private String[] convertChainStringToList(String chain){
        return chain.split("\n");
        }

    private String getRequestIgnoreWord(String chain){
        String requestIgnoreWord = "";
        for (String word : convertChainStringToList(chain)){
            requestIgnoreWord = requestIgnoreWord + WorkDB.F_WORD + " <> '" + word + "'";
        }

        return "";
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

    private boolean wordControlUser(String wordUser, String chain){
        if (!wordUser.matches(getRegExForControlMyWord(chain))){
            Toast.makeText(getBaseContext(), "В слове заменено более одной буквы", Toast.LENGTH_LONG).show();
            return true;
        }

        if (chain.contains(wordUser)){
            Toast.makeText(getBaseContext(), "Слово \"" + wordUser + "\" уже присутствует в цепочке", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}


