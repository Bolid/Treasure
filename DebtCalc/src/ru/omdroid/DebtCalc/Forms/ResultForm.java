package ru.omdroid.DebtCalc.Forms;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import ru.omdroid.DebtCalc.Arithmetic;
import ru.omdroid.DebtCalc.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class ResultForm extends Activity {
    final String TAG = "ru.omdroid.DebtCalc.ResultForm";
    static Double sizeWightBar = 0.0;
    Double newPayment;
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        String[] params = getIntent().getExtras().getStringArray("ru.omdroid.DebtCalc.MainForm");
        final NumberFormat numberFormat = new DecimalFormat("###,###,###,###,###,###,##0.##");
        final View view = (View)findViewById(R.id.graphView);
        final TextView textView = (TextView)findViewById(R.id.valuePayment);
        final Arithmetic arithmetic = new Arithmetic(Double.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]));

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        textView.setText(numberFormat.format(Double.valueOf(arithmetic.allResult.get(4))));
        seekBar.setMax(1000);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                newPayment = Double.valueOf(arithmetic.allResult.get(4)) + 100 * i;
                textView.setText(String.valueOf(numberFormat.format(newPayment)));
                Log.d(TAG + " Новые данные","---------------------------------");
                Log.d(TAG + " Новый платеж ", + i +"  "+newPayment.toString());
                Log.d(TAG + " Новая срок", String.valueOf(arithmetic.getTerm(newPayment)));
                Log.d(TAG + " Новая переплата", String.valueOf(arithmetic.getDeltaNew(newPayment)));
                Log.d(TAG + " Новые данные","=================================");
                sizeWightBar = view.getWidth()/((Double.valueOf(arithmetic.allResult.get(1)) + arithmetic.getDeltaNew(newPayment)))/Double.valueOf(arithmetic.allResult.get(1));
                arithmetic.getDeltaNew(newPayment);
                view.invalidate();

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

