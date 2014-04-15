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

    }

    private String checkContinuationChain(String wordIn, String wordOut, String ignoreWord){
        int selectSymbol = 0;
        String word1 = wordIn , findWord, requestIgnoreWord = ignoreWord, chain = "";
        Cursor cursor;
        while (selectSymbol < word1.length()){
            findWord = replaceSymbol(selectSymbol, word1, wordOut.charAt(selectSymbol));
            if (findWord.equals("СОМ"))
                Log.i(TAG, "Stop");
            if (findWord.equals(wordOut)){
                return chain;
            }
            cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + WorkDB.F_WORD + " = '" + findWord + "' AND " + requestIgnoreWord + ")" );
            if (cursor.getCount() == 0){
                selectSymbol++;
            }
            else{
                cursor.moveToNext();
                word1 = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
                requestIgnoreWord = requestIgnoreWord + " AND " + WorkDB.F_WORD + " <> '" + word1 + "'";
                selectSymbol = 0;
                chain = chain + word1 + "\n";
            }
            cursor.close();
        }
        chain = "";
        return chain;
    }

    private String replaceSymbol(int numberSymbol, String strIn, char charReplace){
        return strIn.substring(0, numberSymbol) + charReplace + strIn.substring(numberSymbol + 1);
    }

    private String getPartLikeRequest(String s){
        String result = "";
        for(int i = 0; i < s.length(); i++){
            result = result + WorkDB.F_WORD +" LIKE '"+ s.substring(0, i) + "_" + s.substring(i+1) + "'";
            if (i < s.length() - 1)
                result = result + " OR ";
        }
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
        Cursor cursor;
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
        String findWord;
        tv.setText("Цепочка\n");
        int selectSymbol = 0;
        String contChain;
        String localIgnore;
        String chain =  fastFindChain(word1, word2, requestIgnoreWord, "", 1);

        tv.setText(word1 + "\n" + chain);
        /*
        while (selectSymbol < word1.length()){

            findWord = replaceSymbol(selectSymbol, word1, word2.charAt(selectSymbol));

            cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + WorkDB.F_WORD + " = '" + findWord + "' AND " + requestIgnoreWord + ")" );
            if (cursor.getCount() == 0){
                selectSymbol++;
            }
            else{
                cursor.moveToNext();
                word1 = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
                requestIgnoreWord = requestIgnoreWord + " AND " + WorkDB.F_WORD + " <> '" + word1 + "'";
                selectSymbol = 0;
                tv.append(word1 + "\n");
            }
            cursor.close();
        }

        int loop = 0;
        while (!word1.equals(word2)){
        loop++;
            if (loop == 2)
                return;
        findWord = getPartLikeRequest(word1);
        cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + findWord + ") AND " + requestIgnoreWord);


        Cursor cursorNextWordValid;
        int countWordDeltaWordValid = 0;
        String wordValid = "";

        while (cursor.moveToNext()) {
            cursorNextWordValid = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + getPartLikeRequest(cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD))) + ") AND " + requestIgnoreWord );
            if (cursorNextWordValid.getCount() > countWordDeltaWordValid){
                countWordDeltaWordValid = cursorNextWordValid.getCount();
                wordValid = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
            }
            cursorNextWordValid.close();

            contChain = checkContinuationChain(cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD)), word2, requestIgnoreWord);
            if (contChain.length() > 0){
                tv.append(cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD)) + "\n");
                tv.append(contChain);
                Log.i(TAG, "Слово: " + cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD)) + " подходит");
                tv.append(word2 + "\n");
                return;
            }
            else
                Log.i(TAG, "Слово: " + cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD)) + " не подходит");
        }
            cursor.close();
            word1 = wordValid;
            tv.append(word1 + "\n");
        }*/
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
}


