package com.omdroid.RemindMe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class NotifyDialog {
    public NotifyDialog(){
        onCreateDialog();
    }

    protected Dialog onCreateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(null);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(Environment.getExternalStorageDirectory().toString() + "/NotifyData.txt");
            builder.setMessage(fileReader.read());
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        builder.setPositiveButton("Вспомнил", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                 dialog.cancel();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
}
