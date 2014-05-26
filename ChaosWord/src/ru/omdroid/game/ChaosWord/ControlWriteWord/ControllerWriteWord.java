package ru.omdroid.game.ChaosWord.ControlWriteWord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;


public class ControllerWriteWord {
    public static final String T_WORDS = "words";
    public static final String F_WORD = "word";

    private final String DB__FOLDER = Environment.getExternalStorageDirectory() + "/ChaosOfWord/";
    private static final String DB_NAME = "DictionariesWords.db";
    private final String DB_PATH = DB__FOLDER + DB_NAME;
    SQLiteDatabase connDictionaries;

    public ControllerWriteWord(){
        dictionariesDBConnect();
    }

    public void dictionariesDBConnect(){
        connDictionaries = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    public boolean controlWordInDictionaries(String request){
        return connDictionaries.rawQuery(request, null).getCount() > 0;

    }
}
