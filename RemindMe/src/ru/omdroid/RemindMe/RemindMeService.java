package ru.omdroid.RemindMe;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class RemindMeService extends IntentService {
    public RemindMeService() {
        super("RemindMeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {/*
        NotifyDialog notifyDialog = new NotifyDialog();
        notifyDialog.onCreateDialog();*/
        Log.v("Напоминалка .","Напоминаю.");
    }
}
