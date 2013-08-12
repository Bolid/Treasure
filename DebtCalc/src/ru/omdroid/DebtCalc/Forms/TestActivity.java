package ru.omdroid.DebtCalc.Forms;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import ru.omdroid.DebtCalc.R;


public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        GraphView graphView = new GraphView(this);
        setContentView(graphView);
        SeekBar seekBar = new SeekBar(this);
        seekBar.findViewById(R.id.SB);
    }
}

