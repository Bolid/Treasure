package ru.omdroid.game.ChaosWord.AppDB;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ru.omdroid.game.ChaosWord.App;

public class AppWordDB {
    public static AppWordDB appWordDB = new AppWordDB();
    AppDB AppDB;
    SQLiteDatabase sqLiteDatabase;

    public AppWordDB(){
        AppDB = new AppDB(App.getInstance());
        sqLiteDatabase = AppDB.getWritableDatabase();
    }

    public static AppWordDB getInstance(){
        return appWordDB;
    }

    public void insertDataToDataBase(String request){
        sqLiteDatabase.execSQL(request);
    }

    public Cursor readDataFromDataBase(String request){
        return sqLiteDatabase.rawQuery(request, null);
    }

    public void deleteDataFromDataBase(String request){
        sqLiteDatabase.execSQL(request);
    }

    public void updateDataToDataBase(String request){
        sqLiteDatabase.execSQL(request);
    }

    public void dbClose(){
        sqLiteDatabase.close();
    }


}
