package ru.omdroid.game.ChaosWord.WorkContentFromDB;

import ru.omdroid.game.ChaosWord.AppDB.AppDB;
import ru.omdroid.game.ChaosWord.AppDB.AppWordDB;

import java.util.Random;

public class WorkContentGame {
    AppWordDB appWordDB = AppWordDB.getInstance();

    public void createNewGame(String user){
        appWordDB.updateDataToDataBase("UPDATE " + AppDB.T_USERS + " SET " + AppDB.F_GAME_USERS + " = '" + generateNumCredit() + "'");
        deleteOldData(user);
        addEmptyData(user);
    }

    private void deleteOldData(String user){
        appWordDB.deleteDataFromDataBase("DELETE FROM " + AppDB.T_PROGRESS + " WHERE " + AppDB.F_USER_PROGRESS + " = '" + user + "'");
        appWordDB.deleteDataFromDataBase("DELETE FROM " + AppDB.T_STATISTIC + " WHERE " + AppDB.F_USER_STAT + " = '" + user + "'");
    }

    private void addEmptyData(String user){
        appWordDB.insertDataToDataBase("INSERT INTO " + AppDB.T_PROGRESS  + "(" + AppDB.F_USER_PROGRESS + ", " + AppDB.F_SCORE_PROGRESS + ") VALUES ('" + user + "', '0')");
    }

    private int generateNumCredit(){
        Random random = new Random();
        return 1000 + random.nextInt(9000 - 1000 + 1);
    }


}
