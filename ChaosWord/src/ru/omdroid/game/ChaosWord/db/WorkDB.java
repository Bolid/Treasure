package ru.omdroid.game.ChaosWord.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WorkDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbChaosWord";

    /*Таблица сохраненной игры*/
    public static final String T_SAVE_GAME = "t_save_game";
    public static final String F_ID = "_id";
    public static final String F_PARAGRAPH = "f_paragraph";
    public static final String F_X_POS = "f_x_pos";
    public static final String F_Y_POS = "f_y_pos";
    public static final String F_TIME_MIN = "f_time_min";
    public static final String F_TIME_SEC = "f_time_min";

    public static final String REQUEST_CREATE_SAVE_GAME_TABLE = "CREATE TABLE " +
                                                                    T_SAVE_GAME + " (" +
            F_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
            F_PARAGRAPH + " STRING, " +
            F_X_POS + " INTEGER, " +
            F_Y_POS + " INTEGER, " +
            F_TIME_MIN + " INTEGER, " +
            F_TIME_SEC + " INTEGER)";


    public WorkDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(REQUEST_CREATE_SAVE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
