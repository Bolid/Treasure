package ru.omdroid.DebtCalc;

import android.text.*;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;


public class InputMask implements TextWatcher{
    Pattern pattern = Pattern.compile("[0-9\\s]*");
    NumberFormat numberFormat = new DecimalFormat("###,###,###,###");
    Boolean mEditing;
    public InputMask(){
        mEditing = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {
        if(!mEditing) {
        mEditing = true;
        Log.d("Brrrrrr", "Brrrrr" + s);
        String digits = s.toString().replaceAll("\\d{3}", "\\d{3}\\s");
        NumberFormat nf = NumberFormat.getInstance();
        try{
            String formatted = nf.format(Double.parseDouble(digits));
            s.replace(0, s.length(), formatted);
        } catch (NumberFormatException nfe) {
            s.clear();
        }

        mEditing = false;
    }
    }
}
