package ru.omdroid.game.ChainTest;

import android.app.Activity;
import android.database.Cursor;
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
    EditText etWord1, etWord2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(TAG, "Начало метода");
        InitializationDB initializationDB = new InitializationDB(getBaseContext());
        initializationDB.Initialized();
        initializationDB.close();

        etWord1 = (EditText)findViewById(R.id.word1);
        etWord2 = (EditText)findViewById(R.id.word2);
        Button but = (Button)findViewById(R.id.button);
        tv = (TextView)findViewById(R.id.tvChain);

        but.setOnClickListener(this);

        workDB = new WorkDB();
        workDB.dbConnect();
        finderChains = new FinderChains(workDB);
    }

    private String getPartLikeRequest(String s){
        String result = "";
        for(int i = 0; i < s.length(); i++){
            result = result + WorkDB.F_WORD +" LIKE '"+ s.substring(0, i) + "_" + s.substring(i+1) + "'";
            if (i < s.length() - 1)
                result = result + " OR ";
        }
        if (!result.equals(""))
            result = "(" + result + ") AND ";
        Log.i(TAG, "Часть like запроса: " + result);
        return result;
    }

    private String getPartLikeRequestForFastChain(String s1, String s2){
        String result = "";
        for(int i = 0; i < s1.length(); i++){
            result = result + WorkDB.F_WORD +" LIKE '"+ s1.substring(0, i) + s2.charAt(i) + s1.substring(i+1) + "'";
            if (i < s1.length() - 1)
                result = result + " OR ";
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        tv.setText("");
        String word1 = etWord1.getText().toString();
        String word2 = etWord2.getText().toString();

        if (word1.length() != word2.length()){
            Toast.makeText(getBaseContext(), "Длина слов должна быть равной", Toast.LENGTH_LONG).show();
            return;
        }
        if (word1.equals("") || word2.equals("")){
            Toast.makeText(getBaseContext(), "Введите слова", Toast.LENGTH_LONG).show();
            return;
        }
        String requestIgnoreWord = WorkDB.F_WORD + " <> '" + word1 + "'";
        String chain =  finderChains.fastFindChain(word1, word2, requestIgnoreWord, "", 1);

        if (chain.equals(""))
            chain = finderChains.findChain(word1, word2, requestIgnoreWord, "", 0, "");

        tv.setText(word1 + "\n" + tv.getText() + chain);
        Log.i(TAG, "Конец метода");
    }

    private String fastFindChain(String wordIn, String wordOut, String requestIgnoreWord, String chain, Integer a){

        String localIgnore = requestIgnoreWord, findWord;
        Cursor cursor;
        String word1 = wordIn, fastChain = chain;



        findWord = getPartLikeRequestForFastChain(word1, wordOut);

        cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + findWord + ") AND " + localIgnore);
        Log.i(TAG, "Новое слово: " + a +"   "+ findWord);
        Log.i(TAG, "Игнорируем: " + a +"   "+ localIgnore);

        while (cursor.moveToNext()){
            word1 = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
            Log.i(TAG, "Найденное слово: " + a +"   "+ word1);
            localIgnore = localIgnore + " AND " + WorkDB.F_WORD + " <> '" + word1 + "'";
            fastChain = fastChain + word1 + "\n";
            Log.i(TAG, "Новая цепочка: " + a +"   " + fastChain);
            fastChain = fastFindChain(word1, wordOut, localIgnore, fastChain, a + 1);
            if (fastChain.contains(wordOut))
                return fastChain;
        }

        if (cursor.getCount() == 0 & !word1.equals(wordOut)){
            Log.i(TAG,  + a + "Ничего не найдено. Очищаем ");
            fastChain = "";
        }
        cursor.close();
        return fastChain;
    }

    private String findChain(String wordIn, String wordOut, String requestIgnoreWord, String chain, Integer a, String nextWordOut){

        String localIgnore = requestIgnoreWord, findWord, nextWord = nextWordOut;
        int countWordDeltaWordNext = a, countWordForCompare;
        Cursor cursor;
        String word1 = wordIn, fastChain = chain;



        findWord = getPartLikeRequest(word1);

        cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE " + findWord + localIgnore);
        Log.i(TAG, "findChain Новое слово: " + findWord);
        Log.i(TAG, "findChain Игнорируем: " + localIgnore);

        while (cursor.moveToNext()){
            word1 = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
            if (countWordDeltaWordNext < (countWordForCompare = getCountDerivativeWordForNextWord(word1, requestIgnoreWord))){

                nextWord = word1;
                Log.i(TAG, "findChain Следующее слово: " + nextWord);
                countWordDeltaWordNext = countWordForCompare;
            }
            Log.i(TAG, "findChain hhhhhhНайденное слово: " + word1);
            localIgnore = localIgnore + " AND " + WorkDB.F_WORD + " <> '" + word1 + "'";
            fastChain = fastChain + word1 + "\n";
            Log.i(TAG, "findChain Новая цепочка: " + fastChain);
            fastChain = fastFindChain(word1, wordOut, localIgnore, fastChain, a + 1);
            if (fastChain.contains(wordOut))
                return fastChain;
        }

        if (cursor.getCount() == 0){
            Log.i(TAG, "findChain Выходим из процесса ");
            return fastChain;
        }
        cursor.close();
        fastChain = fastChain + nextWord + "\n";
        Log.i(TAG, "findChain Продолжаем с нового слова " + nextWord);
        fastChain = findChain(nextWord, wordOut, localIgnore, fastChain, countWordDeltaWordNext, nextWord);
        return fastChain;
    }

    private int getCountDerivativeWordForNextWord(String s, String requestIgnoreWord){
        int countNextWord;
        Cursor cursorNextWordValid = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE " + getPartLikeRequest(s) + requestIgnoreWord );
        countNextWord = cursorNextWordValid.getCount();
        cursorNextWordValid.close();
        return countNextWord;
    }
}


