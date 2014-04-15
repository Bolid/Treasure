package ru.omdroid.game.ChainTest;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.*;


public class InitializationDB extends SQLiteOpenHelper{
    private final String TAG = InitializationDB.class.getName();
    private final String DB__FOLDER = Environment.getExternalStorageDirectory() + "/ChainTest/";
    private static final String DB_NAME = "WordGame.db";
    private final String DB_PATH = DB__FOLDER + DB_NAME;
    private static final int DB_VERSION = 1;

    public InitializationDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void Initialized(){
        if (!isInitialized())
            copyDBFromAssets();
    }

    private boolean isInitialized(){
        SQLiteDatabase checkDB = null;
        boolean dbCurrentVersion = false;
        try{
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
            dbCurrentVersion = checkDB.getVersion() == DB_VERSION;
        }catch (SQLException e){
            Log.w(TAG, e.getMessage());
        }finally {
            if (checkDB != null)
                checkDB.close();
        }
        return checkDB != null && dbCurrentVersion;
    }

    private void copyDBFromAssets(){
        Context context = App.getInstance().getBaseContext();
        InputStream iStream;
        OutputStream oStream;
        try{
            String DB_ASSETS_PATH = "db/" + DB_NAME;
            int BUFFER_FOR_COPY = 1024;
            iStream = new BufferedInputStream(context.getAssets().open(DB_ASSETS_PATH), BUFFER_FOR_COPY);
            File mkDir = new File(DB__FOLDER);
            if (!mkDir.exists())
                mkDir.mkdir();
            oStream = new BufferedOutputStream(new FileOutputStream(DB_PATH), BUFFER_FOR_COPY);
            byte[] bytes = new byte[BUFFER_FOR_COPY];
            int length;
            while ((length = iStream.read(bytes)) > 0){
                oStream.write(bytes, 0, length);
            }
            oStream.flush();
            oStream.close();
            iStream.close();
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        throw new SQLException("Для работы вызовите WordDB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        throw new SQLException("Для работы вызовите WordDB");
    }
}
