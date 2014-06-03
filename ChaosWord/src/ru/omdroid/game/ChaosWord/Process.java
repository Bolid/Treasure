package ru.omdroid.game.ChaosWord;


import android.database.Cursor;
import ru.omdroid.game.ChaosWord.AppDB.AppDB;
import ru.omdroid.game.ChaosWord.AppDB.AppWordDB;

public class Process {
    AppWordDB appWordDB;

    public Process(){
        appWordDB = AppWordDB.getInstance();
        appWordDB.insertDataToDataBase("INSERT INTO " + AppDB.T_PROGRESS + " (" + AppDB.F_SCORE_PROGRESS + ") VALUES ('0')");

    }

    public void saveWordToDB(String str, String currentUser){
        appWordDB.insertDataToDataBase("INSERT INTO " + AppDB.T_STATISTIC + " (" + AppDB.F_WORDS_STAT + ", " + AppDB.F_USER_STAT + ") VALUES ('" + str + "', '" + currentUser + "')");
    }

    public String getCountSelectedWords(String currentUser){
        return String.valueOf(appWordDB.readDataFromDataBase("SELECT * FROM " + AppDB.T_STATISTIC + " WHERE " + AppDB.F_USER_STAT + " = '" + currentUser + "'").getCount());
    }

    public void deleteProcess(){
        appWordDB.deleteDataFromDataBase("DELETE FROM " + AppDB.T_STATISTIC);
        appWordDB.deleteDataFromDataBase("DELETE FROM " + AppDB.T_PROGRESS);
    }

    public void updateScore(int score, String currentUser){
        int scoreLocal = Integer.parseInt(getScoreFromDB(currentUser));
        scoreLocal += score;
        appWordDB.updateDataToDataBase("UPDATE " + AppDB.T_PROGRESS + " SET " + AppDB.F_SCORE_PROGRESS + " = '" + scoreLocal + "' WHERE " + AppDB.F_USER_PROGRESS + " = '" + currentUser + "'");
    }

    public String getScoreFromDB(String currentUser){
        Cursor cursor = appWordDB.readDataFromDataBase("SELECT " + AppDB.F_SCORE_PROGRESS + " FROM " + AppDB.T_PROGRESS + " WHERE " + AppDB.F_USER_PROGRESS + " = '" + currentUser + "'");
        int score = 0;
        while (cursor.moveToNext())
            score = cursor.getInt(cursor.getColumnIndex(AppDB.F_SCORE_PROGRESS));
        cursor.close();
        return String.valueOf(score);
    }
}
