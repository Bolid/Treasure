package ru.omdroid.game.ChaosWord;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
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
    ArrayList<TextView> listSelectedTV = new ArrayList<TextView>();
    ArrayList<TextView> listCreatedTV = new ArrayList<TextView>();
    Animation anim;
    Animation animNotSelectTextView;

    int heightScreen = 0,  emptyCage, rowEmptyGate, cellEmptyGate, deltaSwipe, sizeGameField = 5, countSelectedTV = 0;
    float touchDownX = 0, touchUpX = 0, touchDownY = 0, touchUpY = 0;
    boolean editWord = false;
    String hashCodeActiveTextView = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        anim = createAnimation();
        animNotSelectTextView = createAnimationForNotSelectTextView();


        gridLayout = (GridLayout)findViewById(R.id.container);
        btnOk = (Button)findViewById(R.id.btnWordOk);
        btnNo = (Button)findViewById(R.id.btnWordNo);
        tvEditedWord = (TextView)findViewById(R.id.tvWord);

        gridLayout.setRowCount(sizeGameField);
        gridLayout.setColumnCount(sizeGameField);

        LayoutAnimationController glAnimController = AnimationUtils.loadLayoutAnimation(this, R.anim.grid_animation);
        gridLayout.setLayoutAnimation(glAnimController);
        gridLayout.startLayoutAnimation();
        gridLayout.setOnTouchListener(this);

        deltaSwipe = heightScreen = getHeightScreen();
        emptyCage = emptyCage();
        addViewInContainer(gridLayout);

    }

    private void addViewInContainer(GridLayout frameLayout){
        int countCage = 0;
        int numberTV = 0;
        for (int rows = 0; rows < sizeGameField; rows++)
            for (int cells = 0; cells < sizeGameField; cells++){
                countCage++;
                if (countCage != emptyCage){
                    frameLayout.addView(createTextView(rows, cells, countCage, numberTV));
                    numberTV++;
                }
                else {
                    rowEmptyGate = rows;
                    cellEmptyGate = cells;
                }
            }
    }

    private TextView createTextView(final int positionInRow, final int positionInCell, final int position, int numberTV){
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
        listCreatedTV.add(numberTV, textView);

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
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage+sizeGameField).setLayoutParams(layoutParams);
        hm.get(emptyCage+sizeGameField).startAnimation(anim);
        hm.put(emptyCage, hm.get(emptyCage+sizeGameField));
        hm.remove(emptyCage+sizeGameField);
        emptyCage += sizeGameField;
        rowEmptyGate++;
        return layoutParams;
    }

    private GridLayout.LayoutParams changeLayoutParamsToBottom(int cell, int row){Log.i(TAG, "Проверка координат вниз. Старые координаты " + cell + "  " + row + ". Новые координаты " + cell + "  " + String.valueOf(row));
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(cell));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(emptyCage-sizeGameField).setLayoutParams(layoutParams);
        hm.get(emptyCage-sizeGameField).startAnimation(anim);
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
        hm.get(emptyCage+1).startAnimation(anim);
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
        hm.get(emptyCage-1).startAnimation(anim);
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

                    if ((touchDownX == touchUpX & touchDownY == touchUpY) || editWord){
                        try{
                            btnOk.setEnabled(true);
                            btnNo.setEnabled(true);

                            editWord = true;
                            tv = convertViewToTextView(view);

                            if (controlPreSelectedTextView(tv.hashCode())) {
                                tvEditedWord.setText(tvEditedWord.getText().toString().replace(tv.getText().toString(), ""));
                            }
                            else{
                                tvEditedWord.append(tv.getText().toString());
                            }

                            changeNotActiveTextView(tv);
                            //listSelectedTV.add(countSelectedTV, tv);
                            Log.i(TAG, "Буква " + tv.getText().toString());
                            countSelectedTV++;
                        }
                        catch (ClassCastException e){
                            Log.e(TAG, "Ошибка класса:  ", e);
                        }
                        return false;
                    }

                    if (!editWord){
                        if (directionMove(touchDownX, touchUpX, touchDownY, touchUpY)){
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
                        }
                        else{
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
        for (TextView tv : listCreatedTV){
            tv.setBackgroundColor(getResources().getColor(R.color.background_gate));
            tv.setScaleX(1.0f);
            tv.setScaleY(1.0f);
        }
        listSelectedTV.clear();
        countSelectedTV = 0;
        hashCodeActiveTextView = "";
    }

    private boolean directionMove(float touchDownX, float touchUpX, float touchDownY, float touchUpY){
        return Math.abs(touchDownX - touchUpX) > Math.abs(touchDownY - touchUpY);
    }

    private Animation createAnimation(){
        return AnimationUtils.loadAnimation(getBaseContext(), R.anim.textview_anim);
    }

    private Animation createAnimationForNotSelectTextView(){
        return AnimationUtils.loadAnimation(getBaseContext(), R.anim.not_selected_textview_anim);
    }

    private TextView convertViewToTextView(View view){
        view.setBackgroundColor(getResources().getColor(R.color.background_select_gate));
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        return (TextView) view;
    }

    private void changeNotActiveTextView(TextView activeTV){
        for (TextView tv : listCreatedTV){
            if (tv.hashCode() != activeTV.hashCode()){
                if (!hashCodeActiveTextView.contains(String.valueOf(tv.hashCode())))
                {
                    tv.setScaleX(0.8f);
                    tv.setScaleY(0.8f);
                    tv.setBackgroundColor(getResources().getColor(R.color.background_not_active_gate));
                    tv.startAnimation(animNotSelectTextView);
                }
            }
            else
                if (hashCodeActiveTextView.contains(String.valueOf(tv.hashCode()))){
                    tv.setScaleX(0.8f);
                    tv.setScaleY(0.8f);
                    tv.setBackgroundColor(getResources().getColor(R.color.background_not_active_gate));
                    tv.startAnimation(animNotSelectTextView);
                    hashCodeActiveTextView = hashCodeActiveTextView.replace(String.valueOf(activeTV.hashCode()), "");
                }
                else
                    hashCodeActiveTextView += tv.hashCode();

        }
    }

    private void addHash(int hash){
        hashCodeActiveTextView += hashCodeActiveTextView + hash;
    }

    private boolean controlPreSelectedTextView(int hash){
        return hashCodeActiveTextView.contains(String.valueOf(hash));
    }
}
