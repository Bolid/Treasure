package ru.omdroid.game.ChaosWord;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainForm extends Activity implements View.OnTouchListener {

    final String TAG = "ru.omdroid.game.ChaosWord.MainForm";

    GridLayout gridLayout;
    Button btnOk, btnNo;
    TextView tvEditedWord;

    HashMap<Integer, TextView> hm = new HashMap<Integer, TextView>();
    ArrayList<TextView> selectedTV = new ArrayList<TextView>();

    int heightScreen = 0,  emptyCage, rowEmptyGate, cellEmptyGate, deltaSwipe, sizeGameField = 6, countSelectedTV = 0;
    float touchDownX = 0, touchUpX = 0, touchDownY = 0, touchUpY = 0;
    boolean editWord = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gridLayout = (GridLayout)findViewById(R.id.container);
        btnOk = (Button)findViewById(R.id.btnWordOk);
        btnNo = (Button)findViewById(R.id.btnWordNo);
        tvEditedWord = (TextView)findViewById(R.id.tvWord);

        gridLayout.setRowCount(sizeGameField);
        gridLayout.setColumnCount(sizeGameField);

        gridLayout.setOnTouchListener(this);

        deltaSwipe = heightScreen = getHeightScreen();
        emptyCage = emptyCage();
        Log.i(TAG, "Пустая клетка " + emptyCage);
        addViewInContainer(gridLayout);

    }

    private void addViewInContainer(GridLayout frameLayout){
        int countCage = 0;
        for (int rows = 0; rows < sizeGameField; rows++)
            for (int cells = 0; cells < sizeGameField; cells++){
                countCage++;
                if (countCage != emptyCage)
                    frameLayout.addView(createTextView(rows, cells, countCage));
                else {
                    rowEmptyGate = rows;
                    cellEmptyGate = cells;
                }
            }
    }

    private TextView createTextView(final int positionInRow, final int positionInCell, final int position){
        final TextView textView = new TextView(getBaseContext());
        GridLayout.Spec cell = GridLayout.spec(positionInCell);
        GridLayout.Spec row = GridLayout.spec(positionInRow);
        final GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(row, cell);
        layoutParams.setMargins(1, 1, 1, 1);
        textView.setLayoutParams(layoutParams);
        textView.setText(getSymbol());
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(getResources().getColor(R.color.background_gate));
        textView.setTextSize(16);
        textView.setHeight(heightScreen / sizeGameField - 20);
        textView.setWidth(heightScreen / sizeGameField - 20);

        textView.setOnTouchListener(this);

        hm.put(position, textView);
        return textView;
    }

    private int getHeightScreen(){
        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        return  dMetrics.widthPixels;
    }

    private int emptyCage(){
        Random random = new Random();
        return random.nextInt(sizeGameField * sizeGameField) + 1;
    }

    private String getSymbol(){
        Random random = new Random();
        return getResources().getStringArray(R.array.symbol)[random.nextInt(33)];
    }

    private GridLayout.LayoutParams changeLayoutParamsToTop(int cell, int row){
        Log.i(TAG, "Проверка координат вверх. Старые координаты " + cell + "  " + row + ". Новые координаты " + cell + "  " + String.valueOf(row));
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage+sizeGameField).setLayoutParams(layoutParams);
        hm.put(emptyCage, hm.get(emptyCage+sizeGameField));
        hm.remove(emptyCage+sizeGameField);
        emptyCage += sizeGameField;
        rowEmptyGate++;
        return layoutParams;
    }

    private GridLayout.LayoutParams changeLayoutParamsToBottom(int cell, int row){
        Log.i(TAG, "Проверка координат вниз. Старые координаты " + cell + "  " + row + ". Новые координаты " + cell + "  " + String.valueOf(row));
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage-sizeGameField).setLayoutParams(layoutParams);
        hm.put(emptyCage, hm.get(emptyCage-sizeGameField));
        hm.remove(emptyCage-sizeGameField);
        emptyCage -= sizeGameField;
        rowEmptyGate--;
        return layoutParams;
    }

    private GridLayout.LayoutParams changeLayoutParamsToLeft(int cell, int row){
        Log.i(TAG, "Проверка координат влево. Старые координаты " + cell + "  " + row + ". Новые координаты " + String.valueOf(cell - 1) + "  " + row);
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage+1).setLayoutParams(layoutParams);
        hm.put(emptyCage, hm.get(emptyCage+1));
        hm.remove(emptyCage+1);
        emptyCage++;
        cellEmptyGate++;
        return layoutParams;
    }

    private GridLayout.LayoutParams changeLayoutParamsToRight(int cell, int row){
        Log.i(TAG, "Проверка координат вправо. Старые координаты " + cell + "  " + row + ". Новые координаты " + String.valueOf(cell + 1) + "  " + row);
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage-1).setLayoutParams(layoutParams);
        hm.put(emptyCage, hm.get(emptyCage-1));
        hm.remove(emptyCage-1);
        emptyCage--;
        cellEmptyGate--;
        return layoutParams;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        TextView tv;

        switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    touchDownX = motionEvent.getX();
                    touchDownY = motionEvent.getY();
                    Log.i(TAG, "Первая координата" + motionEvent.getX());
                    break;
                case MotionEvent.ACTION_UP:
                    touchUpX = motionEvent.getX();
                    touchUpY = motionEvent.getY();
                    Log.i(TAG, "Вторая координата" + motionEvent.getX());

                    if (touchDownX == touchUpX & touchDownY == touchUpY){
                        try{
                            btnOk.setEnabled(true);
                            btnNo.setEnabled(true);

                            editWord = true;
                            tv = (TextView) view;
                            tv.setBackgroundColor(getResources().getColor(R.color.background_select_gate));

                            tvEditedWord.append(tv.getText().toString());
                            selectedTV.add(countSelectedTV, tv);
                            Log.i(TAG, "Буква " + tv.getText().toString());
                            countSelectedTV++;
                        }
                        catch (ClassCastException e){
                            Log.e(TAG, "Ошибка класса:  ", e);
                        }
                        return false;
                    }

                    if (!editWord){
                        if (Math.abs(touchUpY - touchDownY) <= (deltaSwipe / sizeGameField - 20) / 2)
                            if ((touchDownX < touchUpX) & (cellEmptyGate != 0)){
                                Log.i(TAG, "Движение вправо  " + touchDownX + "   " + touchUpX);
                                changeLayoutParamsToRight(cellEmptyGate, rowEmptyGate);
                                return true;
                            }
                            else
                            if ((touchDownX > touchUpX) & cellEmptyGate != sizeGameField - 1)
                            {
                                Log.i(TAG, "Движение влево  " + touchDownX + "   " + touchUpX);
                                changeLayoutParamsToLeft(cellEmptyGate, rowEmptyGate);
                                return true;
                            }

                        if (Math.abs(touchUpX - touchDownX) <= (deltaSwipe / sizeGameField - 20) / 2)
                            if (touchDownY > touchUpY & (rowEmptyGate != sizeGameField - 1)) {
                                Log.i(TAG, "Движение вверх  " + touchDownY + "   " + touchUpY);
                                changeLayoutParamsToTop(cellEmptyGate, rowEmptyGate);
                                return true;
                            }
                            else
                            if (touchDownY < touchUpY &rowEmptyGate != 0){
                                Log.i(TAG, "Движение вниз  " + touchDownY + "   " + touchUpY);
                                changeLayoutParamsToBottom(cellEmptyGate, rowEmptyGate);
                                return true;
                            }
                    }
                    break;
                default:
                    break;
            }
        return true;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnWordNo:
                dontSelectedTV();
                tvEditedWord.setText("");
                editWord = !editWord;
                btnOk.setEnabled(false);
                btnNo.setEnabled(false);
        }
    }

    private void dontSelectedTV(){
        for (TextView tv : selectedTV){
            tv.setBackgroundColor(getResources().getColor(R.color.background_gate));
        }
        selectedTV.clear();
        countSelectedTV = 0;
    }
}
