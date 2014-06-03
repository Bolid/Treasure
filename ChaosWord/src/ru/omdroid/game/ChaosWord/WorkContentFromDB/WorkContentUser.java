package ru.omdroid.game.ChaosWord.WorkContentFromDB;

import android.database.Cursor;
import ru.omdroid.game.ChaosWord.AppDB.AppDB;
import ru.omdroid.game.ChaosWord.AppDB.AppWordDB;


public class WorkContentUser {
    AppWordDB appWordDB = AppWordDB.getInstance();

    public String getLastUsedUser(){
        String lastUsedUser = "";
        Cursor cursor = appWordDB.readDataFromDataBase("SELECT " + AppDB.F_USER_USERS + " FROM " + AppDB.T_USERS + " WHERE " + AppDB.F_LAST_USED_USERS + " = '1'");
        if (cursor.moveToNext())
            lastUsedUser = cursor.getString(cursor.getColumnIndex(AppDB.F_USER_USERS));
        cursor.close();
        return lastUsedUser;
    }

    public void setLastUsedUser(String lastUsedUser){
        appWordDB.updateDataToDataBase("UPDATE " + AppDB.T_USERS + " SET " + AppDB.F_LAST_USED_USERS + " = '0'");
        appWordDB.updateDataToDataBase("UPDATE " + AppDB.T_USERS + " SET " + AppDB.F_LAST_USED_USERS + " = '1' WHERE " + AppDB.F_USER_USERS + " = '" + lastUsedUser + "'");
    }

    public void addUserToDB(String newUser){
        appWordDB.insertDataToDataBase("INSERT INTO " + AppDB.T_USERS + "("+ AppDB.F_USER_USERS+") VALUES ('" + newUser +"')");
    }

    public String[] getArraySavedUsersFromDB(){
        int i = 0;
        Cursor cursor = appWordDB.readDataFromDataBase("SELECT " + AppDB.F_USER_USERS + " FROM " + AppDB.T_USERS);
        String[] arrayUsers = new String[cursor.getCount()];
        while (cursor.moveToNext()){
            arrayUsers[i] = cursor.getString(cursor.getColumnIndex(AppDB.F_USER_USERS));
            i++;
        }
        cursor.close();
        return arrayUsers;
    }

    public boolean userCompareToDB(String user){
        return appWordDB.readDataFromDataBase("SELECT " + AppDB.F_USER_USERS + " FROM " + AppDB.T_USERS + " WHERE " + AppDB.F_USER_USERS + " = '" + user + "'").getCount() == 1;
    }

}
