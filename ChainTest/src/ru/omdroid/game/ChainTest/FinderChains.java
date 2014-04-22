package ru.omdroid.game.ChainTest;


import android.database.Cursor;
import android.util.Log;

public class FinderChains {
    WorkDB workDB;
    final String TAG = App.getInstance().getPackageName() + ".FinderChains";

    public FinderChains(WorkDB workDB){
        this.workDB = workDB;
    }

    public String fastFindChain(String wordIn, String wordOut, String requestIgnoreWord, String chain, final String chainDefault, Integer a){

        String localIgnore = requestIgnoreWord, requestForFindWord;
        Cursor cursor;
        String word1 = wordIn, fastChain = chain;

        Log.i(TAG, "Строим по слову: " + a +"   "+ word1);


        requestForFindWord = getPartLikeRequestForFastChain(word1, wordOut);

        cursor = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE (" + requestForFindWord + ") AND " + localIgnore);
        Log.i(TAG, "Новое слово: " + a + "   " + requestForFindWord);
        Log.i(TAG, "Игнорируем: " + a +"   "+ localIgnore);

        while (cursor.moveToNext()){
            word1 = cursor.getString(cursor.getColumnIndex(WorkDB.F_WORD));
            Log.i(TAG, "Найденное слово: " + a +"   "+ word1);
            localIgnore = localIgnore + " AND " + WorkDB.F_WORD + " <> '" + word1 + "'";
            fastChain = fastChain + word1 + "\n";
            Log.i(TAG, "Новая цепочка: " + a +"   " + fastChain);
            fastChain = fastFindChain(word1, wordOut, localIgnore, fastChain, chainDefault, a + 1);
            if (fastChain.contains(wordOut))
                return fastChain;
        }

        if (cursor.getCount() == 0 & !word1.equals(wordOut)){
            Log.i(TAG,  + a + "Ничего не найдено. Очищаем ");
            fastChain = chainDefault;
        }
        cursor.close();
        return fastChain;
    }

    public String findChain(String wordIn, String wordOut, String requestIgnoreWord, String chainDefault, String chain, String nextWordOut, Integer a, Integer countIterationProcess){

        String localIgnore = requestIgnoreWord, findWord, nextWord = nextWordOut;
        int countWordDeltaWordNext = a, countWordForCompare, currentIteration = countIterationProcess;
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
            fastChain = fastFindChain(word1, wordOut, localIgnore, fastChain, chainDefault, a + 1);
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
        currentIteration++;

        if (currentIteration > 3)
            return fastChain;

        fastChain = findChain(nextWord, wordOut, localIgnore, chainDefault, fastChain, nextWord, countWordDeltaWordNext, currentIteration);
        return fastChain;
    }

    private int getCountDerivativeWordForNextWord(String s, String requestIgnoreWord){
        int countNextWord;
        Cursor cursorNextWordValid = workDB.readDataFromDatabase("SELECT * FROM " + WorkDB.T_WORDS + " WHERE " + getPartLikeRequest(s) + requestIgnoreWord );
        countNextWord = cursorNextWordValid.getCount();
        cursorNextWordValid.close();
        return countNextWord;
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
}
