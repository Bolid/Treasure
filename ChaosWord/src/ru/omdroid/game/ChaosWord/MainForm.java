package ru.omdroid.game.ChaosWord;

import android.app.Activity;
import android.graphics.Typeface;
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
import java.util.Iterator;
import java.util.Random;

public class MainForm extends Activity implements View.OnTouchListener {

    final String TAG = "ru.omdroid.game.ChaosWord.MainForm";

    GridLayout gridLayout;
    Button btnOk, btnNo;
    TextView tvEditedWord;
    ChangeLayoutParams changeLayoutParams;

    HashMap<Integer, TextView> hm = new HashMap<Integer, TextView>();
    HashMap<Integer, TextView> hmActiveTV = new HashMap<Integer, TextView>();

    ArrayList<TextView> listSelectedTV = new ArrayList<TextView>();
    ArrayList<TextView> listCreatedTV = new ArrayList<TextView>();
    Animation animNotSelectTextView;

    int heightScreen = 0, deltaSwipe, countSelectedTV = 0;
    float touchDownX = 0, touchUpX = 0, touchDownY = 0, touchUpY = 0, positionXLastSelectedTV = 0, positionYLastSelectedTV = 0;
    boolean editWord = false;
    String hashCodeActiveTextView = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ManagerPositionMovement.SIZE_GAME_FIELD = 5;

        animNotSelectTextView = createAnimationForNotSelectTextView();


        gridLayout = (GridLayout)findViewById(R.id.container);
        btnOk = (Button)findViewById(R.id.btnWordOk);
        btnNo = (Button)findViewById(R.id.btnWordNo);
        tvEditedWord = (TextView)findViewById(R.id.tvWord);

        gridLayout.setRowCount(ManagerPositionMovement.SIZE_GAME_FIELD);
        gridLayout.setColumnCount(ManagerPositionMovement.SIZE_GAME_FIELD);

        LayoutAnimationController glAnimController = AnimationUtils.loadLayoutAnimation(this, R.anim.grid_animation);
        gridLayout.setLayoutAnimation(glAnimController);
        gridLayout.startLayoutAnimation();
        gridLayout.setOnTouchListener(this);

        deltaSwipe = heightScreen = getHeightScreen();
        ManagerPositionMovement.EMPTY_GATE = emptyCage();
        addViewInContainer(gridLayout);
        changeLayoutParams = new ChangeLayoutParams(getBaseContext(), hm);
    }

    private void addViewInContainer(GridLayout frameLayout){
        int countCage = 0;
        int numberTV = 0;
        for (int rows = 0; rows < ManagerPositionMovement.SIZE_GAME_FIELD; rows++)
            for (int cells = 0; cells < ManagerPositionMovement.SIZE_GAME_FIELD; cells++){
                countCage++;
                if (countCage != ManagerPositionMovement.EMPTY_GATE){
                    frameLayout.addView(createTextView(rows, cells, countCage, numberTV));
                    numberTV++;
                }
                else {
                    ManagerPositionMovement.ROW_EMPTY_GATE = rows;
                    ManagerPositionMovement.CELL_EMPTY_GATE = cells;
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
        textView.setHeight(heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20);
        textView.setWidth(heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20);

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
        return random.nextInt(ManagerPositionMovement.SIZE_GAME_FIELD * ManagerPositionMovement.SIZE_GAME_FIELD) + 1;
    }

    private String getSymbol(){
        Random random = new Random();
        return getResources().getStringArray(R.array.symbol)[random.nextInt(33)];
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

                            tv = (TextView) view;

                            if (hmActiveTV.containsValue(tv))
                                hmActiveTV = controlDoublePress(tv);
                            else{
                                if (controlCurrentPressedTextView(tv.getX(), tv.getY()) || !editWord){
                                    hmActiveTV.put(countSelectedTV, tv);
                                    positionXLastSelectedTV = tv.getX();
                                    positionYLastSelectedTV = tv.getY();
                                    selectedTExtView(tv);
                                    countSelectedTV++;

                                    editWord = true;
                                    btnOk.setEnabled(true);
                                    btnNo.setEnabled(true);
                                }
                            }
                            changeNotActiveTextView(tv);
                        }
                        catch (ClassCastException e){
                            Log.e(TAG, "Ошибка класса:  ", e);
                        }
                        return false;
                    }

                    if (!editWord){
                        if (directionMove(touchDownX, touchUpX, touchDownY, touchUpY)){
                            if ((touchDownX < touchUpX) & (ManagerPositionMovement.CELL_EMPTY_GATE != 0)){
                                Log.i(TAG, "Движение вправо  " + touchDownX + "   " + touchUpX);
                                changeLayoutParams.changeLayoutParamsToRight();
                                return true;
                            }
                            else
                            if ((touchDownX > touchUpX) & ManagerPositionMovement.CELL_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)
                            {
                                Log.i(TAG, "Движение влево  " + touchDownX + "   " + touchUpX);
                                changeLayoutParams.changeLayoutParamsToLeft();
                                return true;
                            }
                        }
                        else{
                            if (touchDownY > touchUpY & (ManagerPositionMovement.ROW_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)) {
                                Log.i(TAG, "Движение вверх  " + touchDownY + "   " + touchUpY);
                                changeLayoutParams.changeLayoutParamsToTop();
                                return true;
                            }
                            else
                            if (touchDownY < touchUpY & ManagerPositionMovement.ROW_EMPTY_GATE != 0){
                                Log.i(TAG, "Движение вниз  " + touchDownY + "   " + touchUpY);
                                changeLayoutParams.changeLayoutParamsToBottom();
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
                positionXLastSelectedTV = 0;
                positionYLastSelectedTV = 0;
                btnOk.setEnabled(false);
                btnNo.setEnabled(false);
        }
    }

    private void dontSelectedTV(){
        for (TextView tv : listCreatedTV){
            tv.setBackgroundColor(getResources().getColor(R.color.background_gate));
            tv.setScaleX(1.0f);
            tv.setScaleY(1.0f);
            tv.setTypeface(Typeface.DEFAULT);
        }
        listSelectedTV.clear();
        countSelectedTV = 0;
        hashCodeActiveTextView = "";
    }

    private boolean directionMove(float touchDownX, float touchUpX, float touchDownY, float touchUpY){
        return Math.abs(touchDownX - touchUpX) > Math.abs(touchDownY - touchUpY);
    }

    private Animation createAnimationForNotSelectTextView(){
        return AnimationUtils.loadAnimation(getBaseContext(), R.anim.not_selected_textview_anim);
    }

    private void selectedTExtView(TextView view){
        view.setBackgroundColor(getResources().getColor(R.color.background_select_gate));
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void changeNotActiveTextView(TextView activeTV){
        for (TextView tv : listCreatedTV){
            if (!hmActiveTV.containsValue(tv)){
                {
                    tv.setScaleX(0.8f);
                    tv.setScaleY(0.8f);
                    tv.setBackgroundColor(getResources().getColor(R.color.background_not_active_gate));
                    if (!editWord)
                        tv.startAnimation(animNotSelectTextView);
                }
            }
        }
    }

    private HashMap<Integer, TextView> controlDoublePress(TextView tv){

        HashMap<Integer, TextView> hashMap = new HashMap<Integer, TextView>();
        positionYLastSelectedTV = 0;
        positionXLastSelectedTV = 0;
        for (int i = 0; i < hmActiveTV.size(); i++){
            if (tv.hashCode() == hmActiveTV.get(i).hashCode())
                return hashMap;
            else{
                positionYLastSelectedTV = hmActiveTV.get(i).getY();
                positionXLastSelectedTV = hmActiveTV.get(i).getX();
                hashMap.put(i, hmActiveTV.get(i));
            }
        }
        return hmActiveTV;
    }

    private boolean controlPreSelectedTextView(int hash){
        return hashCodeActiveTextView.contains(String.valueOf(hash));
    }

    private boolean controlCurrentPressedTextView(float posX, float posY){
        return (posX == (positionXLastSelectedTV + (heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posY == positionYLastSelectedTV) ||
               (posX == (positionXLastSelectedTV - (heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posY == positionYLastSelectedTV) ||
               (posY == (positionYLastSelectedTV + (heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posX == positionXLastSelectedTV) ||
               (posY == (positionYLastSelectedTV - (heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posX == positionXLastSelectedTV);
    }
}
