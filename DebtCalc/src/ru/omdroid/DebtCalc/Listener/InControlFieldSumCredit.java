package ru.omdroid.DebtCalc.Listener;

import android.text.*;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import ru.omdroid.DebtCalc.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;


public class InControlFieldSumCredit implements TextWatcher{
    Pattern pattern = Pattern.compile("[0-9\\s]*");
    NumberFormat numberFormat = new DecimalFormat("###,###,###,###");
    Boolean mEditing;
    ImageView markerCreditSum;
    EditText etSumCredit;
    String form = "";
    boolean run = true;
    int i = 0;
    public InControlFieldSumCredit(ImageView markerCreditSum){
        this.markerCreditSum = markerCreditSum;
        this.etSumCredit = etSumCredit;
        mEditing = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        if (i == 0)
            markerCreditSum.setImageResource(R.drawable.marker_red_one);
        if (i3 != 0)
            markerCreditSum.setImageResource(R.drawable.marker_green_one);
        Log.v("onTextChanged ", charSequence.toString()+" "+i+" "+i2+" "+i3);
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {
        /*if (run)
        {
            run = false;
            form = numberFormat.format(Double.valueOf(s.toString()));
            Log.v("Values ", form);
            s.clear();
        }
        s.append(form, 0, form.length());*/
    }
}
