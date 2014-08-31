package com.export.Gismeteo_task;

import android.app.Activity;
import android.os.Bundle;

import java.util.Random;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] masTemp = createRandomTemp();

        setContentView(new Histogram(this, masTemp));
    }

    private int[] createRandomTemp(){
        Random random = new Random();
        int[] masTemp = new int[7];
        for (int i = 0; i < masTemp.length; i++){
            masTemp[i] = random.nextInt(10)+10;
        }
        return masTemp;
    }




}
