package com.export.Gismeteo_task;


import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.View;

public class Histogram extends View {
    String TAG = "TESTING_PROGRAM";
    Paint paint = new Paint();
    Paint paintForBar = new Paint();
    int[] masTemp;
    int heightScreen;
    int widthScreen;
    float widthBar;
    float stepBar;
    float posZero;

    PositionElementHistogram posElem = new PositionElementHistogram();

    String orientationScreen = "";
    public Histogram(Context context, int[] masTemp) {
        super(context);
        this.masTemp = masTemp;
    }

    @Override

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        calculateSizeScreen(canvas);
        getWidthForBar();
        getStepBar();
        calculateZero();
        //central(canvas);
        posElem.setStartXBar(0);
        posElem.setStartYBar(heightScreen / 2 - ((masTemp[0] - posZero) * stepBar));

        for (int i = 0; i < masTemp.length; i++){
            if (masTemp[i] > 0)
                paintBar(canvas, "+" + masTemp[i]);
            else if (masTemp[i] < 0)
                paintBar(canvas, "-" + masTemp[i]);
            else if (masTemp[i] == 0)
                paintBar(canvas, "0");
            if (i != masTemp.length - 1){
                paintBorder(canvas, masTemp[i], masTemp[i + 1]);
            }
        }
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

    private void getStartPosition(){

    }

    private void calculateZero(){
        float temp = 0;
        for (int aMasTemp : masTemp){
            temp = temp + aMasTemp;
        }
        posZero =  temp / masTemp.length;

        Log.i(TAG, "Среднее значение = " + posZero);
    }

    private void central(Canvas canvas){
        paint.setStrokeWidth(1);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(0, heightScreen / 2, widthScreen, heightScreen / 2, paint);
    }
}
