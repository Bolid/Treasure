package com.export.Gismeteo_task;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class Histogram extends View {
    Paint paint = new Paint();
    int[] masTemp;
    int heightScreen;
    int widthScreen;
    float widthBar;
    float stepBar;

    float startXBorder;
    float startYBorder;

    int startXBar;
    int startYBar;

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
        paintBar(canvas);
        paintBorder(canvas);
    }

    private void paintBar(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        canvas.drawLine(startXBar, 200, widthBar, 200, paint);
        canvas.restore();

        startXBorder = widthBar;
        startYBorder = 200;
    }

    private void paintBorder(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.GREEN);
        canvas.drawLine(startXBorder, startYBorder, startXBorder, startYBorder + (stepBar + 5), paint);
        canvas.restore();
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
        stepBar = heightScreen / 2 / (masMinAndMaxTemp[0] - masMinAndMaxTemp[1]) / masTemp.length;

    }

    private int[] getMaxAndMinTemp(){
        int maxTemp = masTemp[0];
        int minTemp = masTemp[0];
        for (int i = 0; i < masTemp.length; i++){
            if (maxTemp < masTemp[i])
                maxTemp = masTemp[i];
            if (minTemp > masTemp[i])
                minTemp = masTemp[i];
        }

        return new int[]{maxTemp, minTemp};
    }
}
