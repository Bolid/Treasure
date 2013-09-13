package ru.omdroid.DebtCalc.Forms;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import ru.omdroid.DebtCalc.Arithmetic;
import ru.omdroid.DebtCalc.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class ResultForm extends Activity {
    final String TAG = "ru.omdroid.DebtCalc.ResultForm";
    static Double sizeWightBar = 0.0;
    static Double newPayment;
    public static Arithmetic arithmetic;
    static boolean paymentUpdate;
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_result);

        String[] params = AppDate.param;
        final NumberFormat numberFormat = new DecimalFormat("###,###,###,###,###,###,##0.##");
        final View view = (View)findViewById(R.id.graphView);

        final TextView textView = (TextView)findViewById(R.id.valuePayment);

        paymentUpdate = true;
        arithmetic = new Arithmetic(Double.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]));
        newPayment = Double.valueOf(Arithmetic.allResult.get(4));
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        textView.setText(numberFormat.format(newPayment));
        seekBar.setMax(1000);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        newPayment = Double.valueOf(Arithmetic.allResult.get(4)) + 100 * i;
                        textView.setText(String.valueOf(numberFormat.format(newPayment)));
                        sizeWightBar = view.getWidth() / ((Double.valueOf(Arithmetic.allResult.get(1)) + arithmetic.getDeltaNew(newPayment))) / Double.valueOf(Arithmetic.allResult.get(1));
                        arithmetic.getDeltaNew(newPayment);
                        view.invalidate();
                        paymentUpdate = true;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
    }
}

