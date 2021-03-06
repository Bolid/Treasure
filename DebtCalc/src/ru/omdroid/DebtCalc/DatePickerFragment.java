package ru.omdroid.DebtCalc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 7/24/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView textView;
    public DatePickerFragment(TextView textView){
        this.textView = textView;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        textView.setText(String.valueOf(day)+"."+String.valueOf(month + 1)+"."+String.valueOf(year));
    }
}
