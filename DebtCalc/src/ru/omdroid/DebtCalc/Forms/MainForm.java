package ru.omdroid.DebtCalc.Forms;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.*;
import ru.omdroid.DebtCalc.Listener.InControlFieldPercendCredit;
import ru.omdroid.DebtCalc.Listener.InControlFieldSumCredit;
import ru.omdroid.DebtCalc.Listener.InControlFieldTermCredit;
import ru.omdroid.DebtCalc.R;

public class MainForm extends Activity {
    public static final String TAG = "ru.omdroid.DebtCalc.MainForm";

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button butStart = (Button)findViewById(R.id.butStart);
        final EditText etSumCredit = (EditText)findViewById(R.id.etCreditSum);
        final EditText etTermCredit = (EditText)findViewById(R.id.etTermCredit);
        final EditText etPercend = (EditText)findViewById(R.id.etPercentCredit);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        final String[] param = new String[3];

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Log.v("Размер экрана: ", displayMetrics.widthPixels + " на " + displayMetrics.heightPixels);

        etSumCredit.addTextChangedListener(new InControlFieldSumCredit((ImageView)findViewById(R.id.markerSumCredit)));
        etPercend.addTextChangedListener(new InControlFieldPercendCredit((ImageView)findViewById(R.id.markerPercentCredit)));
        etTermCredit.addTextChangedListener(new InControlFieldTermCredit((ImageView)findViewById(R.id.markerTermCredit)));

        butStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
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
                    param[0] = etSumCredit.getText().toString();
                    param[1] = etTermCredit.getText().toString();
                    param[2] = etPercend.getText().toString();
                    Intent intent = new Intent(getBaseContext(), ResultForm.class);
                    intent.putExtra(TAG, param);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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
