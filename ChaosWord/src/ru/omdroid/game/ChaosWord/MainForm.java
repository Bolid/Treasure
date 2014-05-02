package ru.omdroid.game.ChaosWord;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainForm extends Activity {
    GridLayout tableLayout;
    int heightScreen = 0;
    int emptyCage = emptyCage();
    final String TAG = "ru.omdroid.game.ChaosWord.MainForm";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tableLayout = (GridLayout)findViewById(R.id.container);
        heightScreen = getHeightScreen();
        Log.i(TAG, "Пустая клетка: " + emptyCage);
        addViewInContainer(tableLayout);
    }

    private void addViewInContainer(GridLayout frameLayout){
        int countCage = 0;
        for (int rows = 0; rows < 5; rows++)
            for (int cells = 0; cells < 5; cells++){
                countCage++;
                if (countCage != emptyCage)
                    frameLayout.addView(createTextView(cells, rows, countCage));
            }
    }

    private TextView createTextView(final int positionInCell, final int positionInRow, final int position){
        final TextView textView = new TextView(getBaseContext());
        GridLayout.Spec row = GridLayout.spec(positionInRow);
        GridLayout.Spec cell = GridLayout.spec(positionInCell);
        final GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(row, cell);
        layoutParams.setMargins(1, 1, 1, 1);
        textView.setLayoutParams(layoutParams);
        textView.setText("A");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(getResources().getColor(R.color.red));
        textView.setTextSize(16);
        textView.setHeight(heightScreen / 5 - 20);
        textView.setWidth(heightScreen / 5 - 20);

        textView.setOnClickListener(new View.OnClickListener() {
            int positionView = position;
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Клик сработал. " + positionView + "  "+ emptyCage);
                if (positionView - 5 == emptyCage){
                    textView.setLayoutParams(changeLayoutParams(layoutParams.columnSpec, positionInRow));
                    Log.i(TAG, "Условие  сработало. ");
                    emptyCage = positionView;
                    positionView = positionView - 5;
                }
            }
        });
        return textView;
    }

    private int getHeightScreen(){
        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        return  dMetrics.widthPixels;
    }

    private int emptyCage(){
        Random random = new Random();
        return random.nextInt(24) + 1;
    }

    private GridLayout.LayoutParams changeLayoutParams(GridLayout.Spec cell, int row){
        Log.i(TAG, "Метод сработал  " + String.valueOf(cell) + "  " + row);
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(cell, GridLayout.spec(row - 1));
        layoutParams.setMargins(1, 1, 1, 1);
        return layoutParams;
    }
}
