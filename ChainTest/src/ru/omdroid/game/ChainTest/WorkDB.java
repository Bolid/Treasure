package ru.omdroid.game.ChainTest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class WorkDB {
    final static String DB_NAME = "WordGame.db";
    final static String DB_FOLDER = Environment.getExternalStorageDirectory() + "/ChainTest/";
    final static String DB_PATH = DB_FOLDER + DB_NAME;

    final static String T_WORDS = "words";

    final static String F_WORD = "word";
    final static String F_COUNT_SYMBOL = "count_simbol";



    SQLiteDatabase sql;
    public void dbConnect(){
        sql = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    public Cursor readDataFromDatabase(String request){
        return sql.rawQuery(request, null);
    }

    public void dbDisconnect(){
        sql.close();
    }
}
