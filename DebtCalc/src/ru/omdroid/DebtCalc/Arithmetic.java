package ru.omdroid.DebtCalc;


import android.util.Log;
import android.widget.Toast;

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
    public static ArrayList <String> allResult;

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
        allResult.add(5, String.valueOf(getDeltaDefault(getPlatej())));
        allResult.add(6, String.valueOf(termCredit));
        allResult.add(7, "");
        allResult.add(8, "");
        allResult.add(9, "");
    }

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend, Double dopPlatej, Boolean indexDopPlatej){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = percend;
        this.indexDopPlatej = indexDopPlatej;
        allResult = new ArrayList<String>();
        allResult.add(0, String.valueOf(dopPlatej)); //исходные данные Дополнительный платеж
        allResult.add(1, String.valueOf(sumCredit)); //исходные данные Сумма кредита
        allResult.add(2, String.valueOf(termCredit)); //исходные данные Срок кредита
        allResult.add(3, String.valueOf(percend)); //исходные данные Процентная ставка
        //данные для вывода
        allResult.add(4, String.valueOf(getPlatej())); //платеж
        allResult.add(5, String.valueOf(getDeltaDefault(getPlatej()))); //переплата
        allResult.add(6, String.valueOf(termCredit)); //срок кредита
        allResult.add(7, "");
        allResult.add(8, "");
        allResult.add(9, "");
        getTermOld(dopPlatej, false);
    }



    private Double getPlatej(){
        BigDecimal platej = BigDecimal.valueOf(0.0);
        Double result = 0.0;
        try{
            platej = BigDecimal.valueOf(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
            platej = (platej.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            result = Double.valueOf(platej.toString());
        }
        catch (NumberFormatException nfe){
            Log.e(TAG, "Ошибка: ", nfe);
            result = Double.valueOf(allResult.get(4));
        }
        return result;
    }

    private Double getDeltaDefault(Double platej){
        BigDecimal delta = BigDecimal.valueOf(Double.valueOf(platej.toString()) * termCredit - sumCredit);
        delta = delta.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        Double result = Double.valueOf(delta.toString());
        return result;
    }

    public Double getDeltaNew(Double platej){

        Double result = Rounding(getPlatej() * getTerm(getPlatej()) - sumCredit, 2);
        allResult.set(5, String.valueOf(result));
        return result;
    }

    public int getTerm(Double platej){
        int n = (int) Math.round(Math.log((platej) / ((platej) - (percend/100./12) * sumCredit)) / Math.log(1 + (percend/100./12)));
        allResult.set(6, String.valueOf(n));
        termCredit = n;
        return n;
    }

    public void getTermOld(Double dopPlatej, Boolean indexDopPlatej){
        overallPlatej = dopPlatej;
        sumCredit = Double.valueOf(allResult.get(1));
        dopPlatej = dopPlatej - getPlatej();
        int i = 0;
        int dopPlatMoutn = 1;
        Double constPlat = 0.0;
        Double deltaMounth = 0.0;
        Double deltaPlatej = getPlatej() - deltaMounth;
        Double allPer = 0.0;
        BigDecimal roundValue;
        while (sumCredit > 0){
            i++;
            deltaMounth = Rounding(sumCredit * (percend/100.) / 12, 2);
            constPlat = getPlatej();
            Log.d(TAG, "Месяц: " + i + ". Платеж: " +getPlatej());
            //if (indexDopPlatej)
                dopPlatej = overallPlatej - getPlatej();/*
            dopPlatej = 0.0;  // для расчета при разной переплате каждый месяц
            if (i == 1) dopPlatej = Rounding(30000 - constPlat);  // для расчета при разной переплате каждый месяц
            if (i == 2) dopPlatej = Rounding(80000 - constPlat);  // для расчета при разной переплате каждый месяц*/
            if (sumCredit < (deltaPlatej + dopPlatej)){
                deltaPlatej = sumCredit;
                sumCredit = sumCredit - deltaPlatej;
            }
            else{
                deltaPlatej = Rounding(getPlatej() - deltaMounth, 2);
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
            allResult.set(7, String.valueOf(constPlat + dopPlatej)); //Ежемесячный платеж с учетом доп. платежа
        else
            allResult.set(7, "Плавающий платеж"); //Ежемесячный платеж с учетом доп. платежа
        allResult.set(8, String.valueOf(allPer)); //Общая переплата
        allResult.set(9, String.valueOf(i)); //Срок погашения
    }

    public Double Rounding(Double value, int i){
        if (i == 0)
            return Double.valueOf(Math.round(value));
        else{
            BigDecimal roundValue = BigDecimal.valueOf(value);
            roundValue = roundValue.setScale(i, BigDecimal.ROUND_HALF_DOWN);
            return Double.valueOf(roundValue.toString());
        }
    }
}
