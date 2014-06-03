package ru.omdroid.game.ChaosWord.AppDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


public class AppDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = /*Environment.getExternalStorageDirectory() + "/*/"dbChaosWord.db";

    /*Таблица сохраненной игры*/
    public static final String T_SAVE_GAME = "t_save_game";
    public static final String F_ID_SAVE = "_id";
    public static final String F_PARAGRAPH_SAVE = "f_paragraph";
    public static final String F_X_POS = "f_x_pos";
    public static final String F_Y_POS = "f_y_pos";
    public static final String F_TIME_MIN = "f_time_min";
    public static final String F_TIME_SEC = "f_time_sec";
    /*Таблица статистики игровой партии*/
    public static final String T_STATISTIC = "t_statistic";
    public static final String F_ID_STAT = "_id";
    public static final String F_USER_STAT = "f_user";
    public static final String F_WORDS_STAT = "f_words_stat";
    public static final String F_MOVES_STAT = "f_moves_stat";
    public static final String F_TIME_STAT = "f_time_stat";
    /*Таблица прогресса игры*/
    public static final String T_PROGRESS = "t_progress";
    public static final String F_ID_PROGRESS = "_id";
    public static final String F_USER_PROGRESS = "f_user";
    public static final String F_ID_GAME_PROGRESS = "f_id_game";
    public static final String F_SCORE_PROGRESS = "f_score";
    /*Таблица пользователей*/
    public static final String T_USERS = "t_users";
    public static final String F_ID_USERS = "_id";
    public static final String F_USER_USERS = "f_user";
    public static final String F_LAST_USED_USERS = "f_last_used";
    public static final String F_GAME_USERS = "f_game";


    public AppDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String REQUEST_CREATE_SAVE_GAME_TABLE = "CREATE TABLE " +
                T_SAVE_GAME + " (" +
                F_ID_SAVE + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                F_PARAGRAPH_SAVE + " STRING, " +
                F_X_POS + " INTEGER, " +
                F_Y_POS + " INTEGER, " +
                F_TIME_MIN + " INTEGER, " +
                F_TIME_SEC + " INTEGER)";
        sqLiteDatabase.execSQL(REQUEST_CREATE_SAVE_GAME_TABLE);

        String REQUEST_CREATE_STATISTIC_TABLE = "CREATE TABLE " +
                T_STATISTIC + " (" +
                F_ID_STAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                F_USER_STAT + " STRING, " +
                F_WORDS_STAT + " STRING, " +
                F_MOVES_STAT + " INTEGER, " +
                F_TIME_STAT + " INTEGER)";
        sqLiteDatabase.execSQL(REQUEST_CREATE_STATISTIC_TABLE);

        String REQUEST_CREATE_PROGRESS_TABLE = "CREATE TABLE " +
                T_PROGRESS + "(" +
                F_ID_PROGRESS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                F_ID_GAME_PROGRESS + " INTEGER, " +
                F_USER_PROGRESS + " STRING, " +
                F_SCORE_PROGRESS + " INTEGER)";
        sqLiteDatabase.execSQL(REQUEST_CREATE_PROGRESS_TABLE);

        String REQUEST_CREATE_USERS_TABLE = "CREATE TABLE " +
                T_USERS + "(" +
                F_ID_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                F_USER_USERS + " STRING, " +
                F_LAST_USED_USERS + " STRING, " +
                F_GAME_USERS + " INTEGER)";
        sqLiteDatabase.execSQL(REQUEST_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
