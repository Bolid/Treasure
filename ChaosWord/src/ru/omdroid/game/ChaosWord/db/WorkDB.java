package ru.omdroid.game.ChaosWord.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: berliozz
 * Date: 22.05.14
 * Time: 0:10
 * To change this template use File | Settings | File Templates.
 */
public class WorkDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbChaosWord";

    public WorkDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
