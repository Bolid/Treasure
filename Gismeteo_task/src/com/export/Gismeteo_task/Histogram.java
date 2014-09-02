package com.export.Gismeteo_task;


import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import android.support.v4.view.ViewCompat;

import java.math.BigDecimal;

public class Histogram extends View {

    private final String TAG = "TESTING_PROGRAM";

    private Context context;

    private OverScroller scroller;

    private GestureDetector gDetector;

    private Paint paint = new Paint();

    private Paint paintForBar = new Paint();


    private int heightScreen, widthScreen;

    private int[] masTemp;

    private float widthBar, stepBar, posZero, xOffset = 0;

    private PositionElementHistogram posElem = new PositionElementHistogram();

    private String orientationScreen = "";

    public Histogram(Context context, int[] masTemp) {
        super(context);
        init(context);
        this.context = context;
        this.masTemp = masTemp;
    }

    private void init(Context context){
        gDetector = new GestureDetector(context, new MyDetectorListener());
        scroller = new OverScroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (gDetector.onTouchEvent(event)) return true;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        boolean needsInvalidate = false;
        if (scroller.computeScrollOffset()) {
            xOffset = scroller.getCurrX();
        }

        if (!scroller.isFinished()) {
            needsInvalidate = true;
        }

        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        testColor(canvas);
        /*if (scroller.computeScrollOffset())
            xOffset = scroller.getCurrX();
        calculateSizeScreen(canvas);
        getWidthForBar();
        getStepBar();
        calculateZero();
        posElem.setStartXBar(-xOffset);
        posElem.setStartYBar(heightScreen / 2 - ((masTemp[0] - posZero) * stepBar));

        for (int i = 0; i < 24; i++){
            if (masTemp[i] > 0)
                paintBar(canvas, "+" + masTemp[i]);
            else if (masTemp[i] < 0)
                paintBar(canvas, "-" + masTemp[i]);
            else if (masTemp[i] == 0)
                paintBar(canvas, "0");
            if (i != 24*//*masTemp.length *//*- 1){
                paintBorder(canvas, masTemp[i], masTemp[i + 1]);
            }
        }
        canvas.save();
        canvas.translate(-xOffset, 0);
        canvas.restore();*/
    }

    private void paintBar(Canvas canvas, String temp){
        paintForBar.setStyle(Paint.Style.STROKE);
        paintForBar.setStrokeWidth(8);
        paintForBar.setColor(Color.RED);
        canvas.drawLine(posElem.getStartXBar(), posElem.getStartYBar(), posElem.getStartXBar() + widthBar, posElem.getStartYBar(), paintForBar);
        canvas.restore();

        paintForBar.setStrokeWidth(2);
        paintForBar.setTextSize(24);
        paintForBar.setTextAlign(Paint.Align.CENTER);
        paintForBar.setColor(Color.WHITE);
        canvas.drawText(temp, posElem.getStartXBar() + (widthBar / 2), posElem.getStartYBar() - 15, paintForBar);

        posElem.setStartXBorder(posElem.getStartXBar() + widthBar);
        posElem.setStartYBorder(posElem.getStartYBar());
    }

    private void paintBorder(Canvas canvas, int temp1, int temp2){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        paint.setShader(new LinearGradient(posElem.getStartXBorder(), posElem.getStartYBorder(), posElem.getStartXBorder(), posElem.getStartYBorder() + (temp1 - temp2) * stepBar, Color.RED, Color.GREEN, Shader.TileMode.CLAMP));
        //paint.setColor(Color.GREEN);
        canvas.drawLine(posElem.getStartXBorder(), posElem.getStartYBorder(), posElem.getStartXBorder(), posElem.getStartYBorder() + (temp1 - temp2) * stepBar, paint);

        canvas.restore();

        posElem.setStartXBar(posElem.getStartXBorder());
        posElem.setStartYBar(posElem.getStartYBorder() + (temp1 - temp2) * stepBar);
    }

    private void calculateSizeScreen(Canvas canvas){
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
    }

    private void getOrientation(){
        if (heightScreen > widthScreen)
            orientationScreen = "VERTICAL";
        else
            orientationScreen = "HORIZONTAL";
    }

    private void getWidthForBar(){
        if (orientationScreen.equals("VERTICAL"))
            widthBar = heightScreen / 4;
        else
            widthBar = widthScreen / 6;

    }

    private void getStepBar(){
        int[] masMinAndMaxTemp = getMaxAndMinTemp();
        stepBar = heightScreen / 2 / (masMinAndMaxTemp[0] - masMinAndMaxTemp[1] / masTemp.length);
        Log.i(TAG, "Шаг границы = " + stepBar);

    }

    private int[] getMaxAndMinTemp(){
        int maxTemp = masTemp[0];
        int minTemp = masTemp[0];
        for (int aMasTemp : masTemp) {
            if (maxTemp < aMasTemp)
                maxTemp = aMasTemp;
            if (minTemp > aMasTemp)
                minTemp = aMasTemp;
        }

        return new int[]{maxTemp, minTemp};

    }

    private void calculateZero(){
        float temp = 0;
        for (int aMasTemp : masTemp){
            temp = temp + aMasTemp;
        }
        posZero =  temp / masTemp.length;
    }


    public class MyDetectorListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onDown(MotionEvent e) {
            scroller.forceFinished(true);
            invalidate();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
            xOffset += distanceX;
            if (xOffset < 0)
                xOffset = 0;
            if (Math.abs(xOffset) > 24 * widthBar - getMeasuredWidth())
                xOffset = 24 * widthBar - getMeasuredWidth();
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            scroller.fling((int) xOffset, 0, (int) -velocityX, 0, 0, (int) (24 * widthBar - getMeasuredWidth()), 0, 0);
            return true;
        }
    }

    private void testColor(Canvas canvas){
        //new Color((int)(r1*(1-x)+r2*x),(int)(g1*(1-x)+g2*x),(int)(b1*(1-x)+b2*x));
        int[] masRED = context.getResources().getIntArray(R.array.colorRED);
        int[] masGREEN = context.getResources().getIntArray(R.array.colorGREEN);
        int[] masBLUE = context.getResources().getIntArray(R.array.colorBLUE);

        int[][] masGradient = new int[masRED.length][masRED.length];

        int[] colorRed = new int[masRED.length];
        int[] colorGreen = new int[masRED.length];
        int[] colorBlue = new int[masRED.length];

        float koef;
        int posX = 20;
        int posY = 20;
        for (int i = 0; i < masRED.length; i++){
            if (i == masRED.length - 1)
                return;
            for(int j = 0; j < masRED.length; j++){
                koef = Float.valueOf(String.valueOf(new BigDecimal(j).divide(BigDecimal.valueOf(10))));
                colorRed[j] = (int) (masRED[i] * (1-koef)+masRED[i+1]*koef);
                colorGreen[j] = (int)(masGREEN[i]*(1-koef)+masGREEN[i+1]*koef);
                colorBlue[j] = (int)(masBLUE[i]*(1-koef)+masBLUE[i+1]*koef);
            }
            for (int k = 0; k < colorRed.length; k++){
                paintPoint(colorRed[k], colorGreen[k], colorBlue[k], canvas, posX, posY);
                posY += 30;
            }
            posY = 20;
            posX += 30;
        }

    }

    private void paintPoint(int RED, int GREEN, int BLUE, Canvas canvas, int posX, int posY){
        Log.i(TAG,  "Цвета: " + RED + " " + GREEN + " " + BLUE);
        paint.setARGB(100, RED, GREEN, BLUE);
        paint.setStrokeWidth(20);
        canvas.drawPoint(posX, posY, paint);
        canvas.restore();
    }
}
