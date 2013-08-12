package ru.omdroid.DebtCalc;


import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Arithmetic {
    public static final String TAG = "ru.omdroid.DebtCalc.Arithmetic";
    private Double overallPlatej;
    int termCredit = 0;
    Double sumCredit = 0.0;
    Double percend = 0.0;
    Double dopPlatej;
    Boolean indexDopPlatej;
    public ArrayList <String> allResult;

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = Double.valueOf(percend);
        allResult = new ArrayList<String>();
        allResult.add(0, String.valueOf(dopPlatej)); //исходные данные Дополнительный платеж
        allResult.add(1, String.valueOf(sumCredit)); //исходные данные Сумма кредита
        allResult.add(2, String.valueOf(termCredit)); //исходные данные Срок кредита
        allResult.add(3, String.valueOf(percend)); //исходные данные Процентная ставка
        //данные для вывода
        allResult.add(4, String.valueOf(getPlatej()));
        allResult.add(5, String.valueOf(getDelta(getPlatej())));
        allResult.add(6, String.valueOf(termCredit));
//        Log.d(TAG, "Срок: " + getTest());
    }

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend, Double dopPlatej, Boolean indexDopPlatej){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = percend;
        this.dopPlatej = dopPlatej;
        this.indexDopPlatej = indexDopPlatej;
        allResult = new ArrayList<String>();
        allResult.add(0, String.valueOf(dopPlatej)); //исходные данные Дополнительный платеж
        allResult.add(1, String.valueOf(sumCredit)); //исходные данные Сумма кредита
        allResult.add(2, String.valueOf(termCredit)); //исходные данные Срок кредита
        allResult.add(3, String.valueOf(percend)); //исходные данные Процентная ставка
        //данные для вывода
        allResult.add(4, String.valueOf(getPlatej())); //платеж
        allResult.add(5, String.valueOf(getDelta(getPlatej()))); //переплата
        allResult.add(6, String.valueOf(termCredit)); //срок кредита
        overallPlatej = Double.valueOf(allResult.get(4)) + Double.valueOf(allResult.get(0));
        Log.v("Доп. платеж. ", String.valueOf(overallPlatej));
        getTerm();
    }



    private Double getPlatej(){
        BigDecimal platej = BigDecimal.valueOf(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
        platej = (platej.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        Double result = Double.valueOf(platej.toString());
        Log.d(TAG, "Платеж: " + result);
        return result;
    }

    private Double getDelta(Double platej){
        BigDecimal delta = BigDecimal.valueOf(Double.valueOf(platej.toString()) * termCredit - sumCredit);
        delta = delta.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        Double result = Double.valueOf(delta.toString());
        Log.d(TAG, ": " + result);
        return result;
    }

    private void getTerm(){
        Log.d(TAG,"Платеж: " +getPlatej());
        int i = 0;
        int dopPlatMoutn = 1;
        Double constPlat = 0.0;
        Double deltaMounth = sumCredit * (percend/100.) / 12;
        Double deltaPlatej = constPlat - deltaMounth;
        Double allPer = 0.0;
        BigDecimal roundValue;
        while (sumCredit > 0){
            i++;
            deltaMounth = Rounding(sumCredit * (percend/100.) / 12);
            constPlat = getPlatej();
            Log.d(TAG, "Месяц: " + i + ". Платеж: " +getPlatej());
            if (indexDopPlatej)
                dopPlatej = overallPlatej - getPlatej();/*
            dopPlatej = 0.0;  // для расчета при разной переплате каждый месяц
            if (i == 1) dopPlatej = Rounding(30000 - constPlat);  // для расчета при разной переплате каждый месяц
            if (i == 2) dopPlatej = Rounding(80000 - constPlat);  // для расчета при разной переплате каждый месяц*/
            if (sumCredit < (deltaPlatej + dopPlatej)){
                deltaPlatej = sumCredit;
                sumCredit = sumCredit - deltaPlatej;
            }
            else{
                deltaPlatej = Rounding(constPlat - deltaMounth);
                sumCredit = sumCredit - deltaPlatej - dopPlatej;
            }

            allPer = allPer + deltaMounth;
            termCredit--;
            Log.d(TAG, "Сумма долга: " + sumCredit);
            Log.d(TAG, "Доп. платеж: " + dopPlatej);
            Log.d(TAG, "В счет долга: " + deltaPlatej);
            Log.d(TAG, "Общий платеж: " + String.valueOf(deltaPlatej + dopPlatej));
            Log.d(TAG, "Переплата: " + deltaMounth);
            Log.d(TAG, "________");
        }
        Log.d(TAG, "Общая переплата: " + allPer);
        roundValue = BigDecimal.valueOf(allPer);
        roundValue = roundValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        allPer = Double.valueOf(roundValue.toString());
        if (indexDopPlatej)
            allResult.add(7, String.valueOf(constPlat + dopPlatej)); //Ежемесячный платеж с учетом доп. платежа
        else
            allResult.add(7, "Плавающий платеж"); //Ежемесячный платеж с учетом доп. платежа
        allResult.add(8, String.valueOf(allPer)); //Общая переплата
        allResult.add(9, String.valueOf(i)); //Срок погашения
    }

    private Double Rounding(Double value){
        BigDecimal roundValue = BigDecimal.valueOf(value);
        roundValue = roundValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return Double.valueOf(roundValue.toString());
    }
}
