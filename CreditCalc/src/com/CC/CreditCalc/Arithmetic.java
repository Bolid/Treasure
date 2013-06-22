package com.CC.CreditCalc;


import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Arithmetic {
    public static final String TAG = "com.CC.CreditCalc.Arithmetic";
    int termCredit = 0;
    Double sumCredit = 0.0;
    Double percend = 0.0;
    Double dopPlatej = 0.0;
    List <String> allResult;

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = Double.valueOf(percend);
        allResult = new ArrayList<String>();
        allResult.add(0, String.valueOf(getPlatej()));
        allResult.add(1, String.valueOf(getDelta(getPlatej())));
        Log.d(TAG, "List_0: " + allResult.get(0));
        Log.d(TAG, "List_1: " + allResult.get(1));
//        Log.d(TAG, "Срок: " + getTest());
    }

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend, Double dopPlatej){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = percend;
        this.dopPlatej = dopPlatej;
        allResult = new ArrayList<String>();
        allResult.add(0, String.valueOf(getPlatej()));
        allResult.add(1, String.valueOf(getDelta(getPlatej())));
        getTerm();
    }



    private Double getPlatej(){
        BigDecimal platej = BigDecimal.valueOf(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
        platej = (platej.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        Double result = Double.valueOf(platej.toString());
        //Log.d(TAG, "Платеж: " + result);
        return result;
    }

    private Double getDelta(Double platej){
        BigDecimal delta = BigDecimal.valueOf(Double.valueOf(platej.toString()) * termCredit - sumCredit);
        delta = delta.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        Double result = Double.valueOf(delta.toString());
        //Log.d(TAG, ": " + result);
        return result;
    }

    private void getTerm(){
        int i = 0;
        Double constPlat = Double.valueOf(allResult.get(0));
        Double deltaMounth = sumCredit * (percend/100.) / 12;;
        Double deltaPlatej = constPlat - deltaMounth;
        Double allPer = 0.0;
        while (sumCredit > 0){
            deltaMounth = sumCredit * (percend/100.) / 12;
            if (sumCredit < deltaPlatej){
                deltaPlatej = sumCredit;
                sumCredit = sumCredit - deltaPlatej;
            }
            else{
                //deltaPlatej = (1 + percend/100./12) * deltaPlatej;
                deltaPlatej = constPlat - deltaMounth;
                sumCredit = sumCredit - deltaPlatej - dopPlatej;
            }
            i++;
            termCredit--;
            if (i==4){
                //sumCredit = sumCredit - 20000;
                Log.d(TAG, "Доп. платеж: " + 20000);
                //constPlat = setPlatej(); в счет погашения срока
            }
            allPer = allPer + deltaMounth;
            Log.d(TAG, "Месяц: " + i);
            Log.d(TAG, "Сумма долга: " + sumCredit);
            Double deltaPlatej1 = deltaPlatej + deltaMounth;
            Log.d(TAG, "Платеж: " + deltaPlatej1);
            Log.d(TAG, "Переплата: " + deltaMounth);
            Log.d(TAG, "Общая переплата: " + allPer);
            Log.d(TAG, "________");
        }
        BigDecimal roundValue = BigDecimal.valueOf(allPer);
        roundValue = roundValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        allPer = Double.valueOf(roundValue.toString());
        allResult.add(2, String.valueOf(allPer));
        allResult.add(3, String.valueOf(i));
        //Log.d(TAG, "Срок: " + i);
        //Log.d(TAG, "Общая переплата: " + allPer);
        //return newTerm;
    }

    /*int  getTest(){
        BigDecimal result = BigDecimal.valueOf(((Math.log(setPlatej() / (setPlatej() - (percend/100./12) * sumCredit))) / (Math.log(1 + (percend/100./12)))));
        result = result.setScale(0, BigDecimal.ROUND_HALF_DOWN);
        return Integer.valueOf(result.toString());
    }*/
}
