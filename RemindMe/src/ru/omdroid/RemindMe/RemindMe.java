package ru.omdroid.RemindMe;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class RemindMe {
    final String TAG = "ru.omdroid.RemindMe.RemindMe";
    private String inData;
    private Long notifyTime;
    private Context context;
    private PendingIntent pendingIntent;
    public RemindMe(String inData, Long notifyTime, Context context){
        this.inData = inData;
        this.notifyTime = notifyTime;
        this.context = context;
        Intent intent = new Intent(context, RemindMeService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        saveForNotifyData();
        createNotification();
    }

    private void saveForNotifyData(){
        try{
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().toString() + "/NotifyData.txt", true);
            fileWriter.append(inData);
            fileWriter.close();}
        catch (IOException ioe){
            Log.e(TAG, "Ошибка: " + ioe);
        }
    }

    private void createNotification(){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+ 30 * 1000, notifyTime, pendingIntent);
    }

    public void deleteNotification(){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
