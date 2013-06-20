package com.CC.CreditCalc;


import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Arithmetic {
    public static final String TAG = "com.CC.CreditCalc.Arithmetic";
    Double sumCredit = 0.0;
    int termCredit = 0;
    Double percend = 0.0;

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = Double.valueOf(percend);
        getTerm();
//        Log.d(TAG, "Срок: " + getTest());
    }

    public Double setPlatej(){
        BigDecimal platej = BigDecimal.valueOf(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
        platej = (platej.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        Double result = Double.valueOf(platej.toString());
        //Log.d(TAG, "Платеж: " + result);
        return result;
    }

    public Double setDelta(Double platej){
        BigDecimal delta = BigDecimal.valueOf(Double.valueOf(platej.toString()) * termCredit - sumCredit);
        delta = delta.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        Double result = Double.valueOf(delta.toString());
        Log.d(TAG, ": " + result);
        return result;
    }

    public void getTerm(){
        int i = 0;
        Double constPlat = setPlatej();
        Double allPer = 0.0;
        Double deltaPlatej;
        Double deltaMounth;
        deltaMounth = sumCredit * (percend/100.) / 12;
        deltaPlatej = constPlat - deltaMounth;
        while (sumCredit > 0){
            deltaMounth = sumCredit * (percend/100.) / 12;
            if (sumCredit < deltaPlatej){
                deltaPlatej = sumCredit;
                sumCredit = sumCredit - deltaPlatej;
            }
            else{
                //deltaPlatej = (1 + percend/100./12) * deltaPlatej;
                deltaPlatej = constPlat - deltaMounth;
                sumCredit = sumCredit - deltaPlatej - 20000;
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
        //Log.d(TAG, "Срок: " + i);
        //Log.d(TAG, "Общая переплата: " + allPer);
        //return newTerm;
    }

    int  getTest(){
        BigDecimal result = BigDecimal.valueOf(((Math.log(setPlatej() / (setPlatej() - (percend/100./12) * sumCredit))) / (Math.log(1 + (percend/100./12)))));
        result = result.setScale(0, BigDecimal.ROUND_HALF_DOWN);
        return Integer.valueOf(result.toString());
    }
}
