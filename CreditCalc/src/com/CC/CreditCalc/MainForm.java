package com.CC.CreditCalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainForm extends Activity {
    public static final String TAG = "com.CC.CreditCalc.MainForm";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button butStart = (Button)findViewById(R.id.butStart);
        final EditText sumCredit = (EditText)findViewById(R.id.edSumCr);
        final EditText termCredit = (EditText)findViewById(R.id.etTermCredit);
        final EditText percend = (EditText)findViewById(R.id.etPercend);
        final TextView textViewPlatej = (TextView)findViewById(R.id.tvPlatej);
        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arithmetic arithmetic = new Arithmetic(Integer.valueOf(sumCredit.getText().toString()), Integer.valueOf(termCredit.getText().toString()), Integer.valueOf(percend.getText().toString()));
                textViewPlatej.setText(String.valueOf(arithmetic.setPlatej()));
                Log.d(TAG, "DELTA: " + arithmetic.setDelta().toString());
            }
        });
    }
}
