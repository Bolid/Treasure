package ru.omdroid.game.ChaosWord;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimerProgress {
    private final String TAG = "ru.omdroid.game.ChaosWord.TimerProgress";
    private int minute = 0, seconds = 0;
    private boolean stopTimer = false;
    String timer;
    TextView tv;
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat();

    public TimerProgress(TextView tv){
        this.tv = tv;
    }

    public void nextTimer(){
        new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                while (!stopTimer)
                    try {
                        Log.i(TAG, "Процесс идет");
                        Thread.sleep(1000);
                        format.applyPattern("mm:ss");
                        seconds++;
                        if (seconds == 60){
                            minute++;
                            seconds = 0;
                        }
                        date.setMinutes(minute);
                        date.setSeconds(seconds);
                        timer = format.format(date);

                        publishProgress(timer);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Ошибка счетчика хода", e);
                    }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                tv.setText(values[0]);
            }


        }.execute();
    }

    public void stopTimer(){
        stopTimer = !stopTimer;
    }

    public void reStartTimer(){
        stopTimer = !stopTimer;
        minute = 0;
        seconds = 0;
        timer = "";
        tv.setText("00:00");
        nextTimer();
    }
}
