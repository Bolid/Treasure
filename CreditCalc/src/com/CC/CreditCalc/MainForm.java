package com.CC.CreditCalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

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
        final EditText dopPlatej = (EditText)findViewById(R.id.etDopPlatej);

        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arithmetic arithmetic = null;
                if (dopPlatej.getText().toString().equals(""))
                    arithmetic = new Arithmetic(Double.valueOf(sumCredit.getText().toString()), Integer.valueOf(termCredit.getText().toString()), Double.valueOf(percend.getText().toString()));
                else
                    arithmetic = new Arithmetic(Double.valueOf(sumCredit.getText().toString()), Integer.valueOf(termCredit.getText().toString()), Double.valueOf(percend.getText().toString()), Double.valueOf(dopPlatej.getText().toString()));
                Log.d(TAG, "List_0: " + arithmetic.allResult.get(0));
                Log.d(TAG, "List_1: " + arithmetic.allResult.get(1));
                Intent intent = new Intent(getBaseContext(), ContextWindow.class);
                intent.putStringArrayListExtra(TAG, (ArrayList<String>) arithmetic.allResult);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //textViewPlatej.setText(String.valueOf(arithmetic.setPlatej()));
                //textViewDelta.setText(String.valueOf(arithmetic.setDelta(arithmetic.setPlatej())));
                //Log.d(TAG, "DELTA: " + arithmetic.setDelta().toString());
            }
        });
    }

    private void createContextWindow(){
        LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
        View view = layoutInflater.inflate(R.layout.context_window, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle(R.string.labelName);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
