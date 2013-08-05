package ru.omdroid.DebtCalc.Forms;

import android.app.Activity;

import android.app.DialogFragment;
import android.content.Intent;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.*;
import ru.omdroid.DebtCalc.Arithmetic;
import ru.omdroid.DebtCalc.DatePickerFragment;
import ru.omdroid.DebtCalc.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainForm extends Activity {
    public static final String TAG = "MainForm";

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button butStart = (Button)findViewById(R.id.butStart);
        final EditText etSumCredit = (EditText)findViewById(R.id.edSumCr);
        final EditText etTermCredit = (EditText)findViewById(R.id.etTermCredit);
        final EditText etPercend = (EditText)findViewById(R.id.edPercend);
        final EditText etDopPlatej = (EditText)findViewById(R.id.etDopPlatej);
        final CheckBox overallDopPlatej = (CheckBox)findViewById(R.id.cbIndexDopPlatej);
        final TextView dateStart = (TextView)findViewById(R.id.dateStart);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Log.v("Размер экрана: ", displayMetrics.widthPixels + " на " + displayMetrics.heightPixels);

        Calendar calendar = Calendar.getInstance();
        dateStart.setText(calendar.get(Calendar.DATE)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.YEAR));
        //etSumCredit.addTextChangedListener(new InputMask());

        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Arithmetic arithmetic = null;
                String notify = "Пожалуйста, введите следующие параметры для кредита:";
                if (etSumCredit.getText().toString().equals("") || etSumCredit.getText().toString().equals("0"))
                    notify = notify + "\nСумму кредита.";
                if (etTermCredit.getText().toString().equals("") || etTermCredit.getText().toString().equals("0"))
                    notify = notify + "\nСрок кредита.";
                if (etPercend.getText().toString().equals("") || etPercend.getText().toString().equals("0"))
                    notify = notify + "\nПроцентную ставку.";
                if (notify.length() > 55)
                    Toast.makeText(getBaseContext(), notify, Toast.LENGTH_LONG).show();
                else {
                    if (etDopPlatej.getText().toString().equals("") || Double.valueOf(etDopPlatej.getText().toString()) == 0)
                        arithmetic = new Arithmetic(Double.valueOf(etSumCredit.getText().toString()), Integer.valueOf(etTermCredit.getText().toString()), Double.valueOf(etPercend.getText().toString()));
                    else
                        arithmetic = new Arithmetic(Double.valueOf(etSumCredit.getText().toString()), Integer.valueOf(etTermCredit.getText().toString()), Double.valueOf(etPercend.getText().toString()), Double.valueOf(etDopPlatej.getText().toString()), overallDopPlatej.isChecked());
                    Intent intent = new Intent(getBaseContext(), ContextWindow.class);
                    intent.putStringArrayListExtra(TAG, (ArrayList<String>) arithmetic.allResult);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        final DialogFragment dialogFragment = new DatePickerFragment(dateStart);
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getFragmentManager(), "date");
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_menu_analitic_item:
                Intent intent = new Intent(this, AnaliticForm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}
