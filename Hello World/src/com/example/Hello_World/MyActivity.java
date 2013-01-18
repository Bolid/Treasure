package com.example.Hello_World;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import  android.widget.Button;
import  android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


    }
}
