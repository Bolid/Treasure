package com.export.Gismeteo_task;


public class HistogramColor {
    private int RED;
    private int GREEN;
    private int BLUE;

    public void setColor(int cRED, int cGREEN, int cBLUE){
        RED = cRED;
        GREEN = cGREEN;
        BLUE = cBLUE;
    }

    public int[] getColorRGB(){
        return new int[]{RED, GREEN, BLUE};
    }
}
