package ru.omdroid.game.ChaosWord;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimerProgress {
    private final String TAG = "ru.omdroid.game.ChaosWord.TimerProgress";
    private int minute = 0, seconds = 15;
    private boolean stopTimer = false;
    String timer;
    TextView tv, tvEndGame;
    LinearLayout ll;
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat();

    public TimerProgress(LinearLayout ll, TextView tv){
        this.tv = tv;
        this.ll = ll;
        createMessageEndGame();
    }

    private void createMessageEndGame(){
       /* tvEndGame = new TextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tvEndGame.setLayoutParams(layoutParams);
        tvEndGame.setText("GAME OVER");
        tvEndGame.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tvEndGame.setVisibility(View.INVISIBLE);*/
    }

    public void nextTimer(){
        new AsyncTask<Void, String, Boolean>() {

            protected void onPreExecute(){
                tv.setText(String.valueOf(seconds));
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
                while (seconds > 0)
                    try {
                        Log.i(TAG, "Процесс идет");
                        Thread.sleep(1000);
                        /*format.applyPattern("mm:ss");
                        seconds++;
                        if (seconds == 60){
                            minute++;
                            seconds = 0;
                        }
                        date.setMinutes(minute);
                        date.setSeconds(seconds);
                        timer = format.format(date);*/
                        seconds--;
                        publishProgress(String.valueOf(seconds));

                    } catch (InterruptedException e) {
                        Log.e(TAG, "Ошибка счетчика хода", e);
                    }
                return true;
            }



            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                tv.setText(values[0]);
            }

            protected void onPostExecute(Boolean bool){
                ll.setVisibility(View.VISIBLE);
            }


        }.execute();
    }

    public void stopTimer(){
        stopTimer = !stopTimer;
    }

    public void updateTime(int n){
       /* stopTimer = !stopTimer;
        minute = 0;
        seconds = 0;
        timer = "";
        tv.setText("00:00");
        nextTimer();*/
        seconds += n;
    }

    public void restartTimer(){
        seconds = 15;
        ll.setVisibility(View.INVISIBLE);
        nextTimer();
    }
}
