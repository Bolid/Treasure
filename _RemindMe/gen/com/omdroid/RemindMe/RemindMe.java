package ru.omdroid.RemindMe;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class RemindMe {
    private String inData;
    private Long notifyTime;
    private Context context;
    public RemindMe(String inData, Long notifyTime, Context context){
        this.inData = inData;
        this.notifyTime = notifyTime;
        this.context = context;
        saveForNotifyData();
        createNotification();
    }

    private void saveForNotifyData(){
        try{
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().toString() + "/NotifyData.txt", true);
            fileWriter.append(inData);
            fileWriter.close();}
        catch (IOException ioe){

        }
    }

    private void createNotification(){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotifyDialog.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), notifyTime, pendingIntent);
    }

}
