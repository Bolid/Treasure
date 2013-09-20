package ru.omdroid.DebtCalc;


import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Arithmetic {
    public static final String TAG = "ru.omdroid.DebtCalc.Arithmetic";
    int termCredit = 0;
    Double sumCredit = 0.0;
    Double percend = 0.0;
    Double dopPlatej;
    Boolean indexDopPlatej;
    private HashMap<Integer, Double> hmPaymentMonth;
    public static ArrayList <String> allResult;
    public static ArrayList <HashMap<String, String>> listResult = null;

    public Arithmetic(Double sumCredit, Integer termCredit, Double percend){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = percend;

        hmPaymentMonth = new HashMap<Integer, Double>();
        allResult = new ArrayList<String>();

        allResult.add(0, String.valueOf(dopPlatej)); //исходные данные Дополнительный платеж
        allResult.add(1, String.valueOf(sumCredit)); //исходные данные Сумма кредита
        allResult.add(2, String.valueOf(termCredit)); //исходные данные Срок кредита
        allResult.add(3, String.valueOf(percend)); //исходные данные Процентная ставка
        //данные для вывода
        allResult.add(4, String.valueOf(getPlatej(sumCredit, termCredit)));
        allResult.add(5, "");
        allResult.set(5, String.valueOf(getDeltaDefault(getPlatej(sumCredit, termCredit), termCredit)));
        allResult.add(6, String.valueOf(termCredit));
        allResult.add(7, "");
        allResult.add(8, "");
        allResult.add(9, "");
        //getOverpaymentAllMonth(getPlatej(sumCredit, termCredit), true);
    }

    /*public Arithmetic(Double sumCredit, Integer termCredit, Double percend, Double dopPlatej, Boolean indexDopPlatej){
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
        allResult.add(4, String.valueOf(getPlatej(sumCredit, termCredit))); //платеж
        allResult.add(5, String.valueOf(getDeltaDefault(getPlatej(sumCredit, termCredit), termCredit))); //переплата
        allResult.add(6, String.valueOf(termCredit)); //срок кредита
        allResult.add(7, "");
        allResult.add(8, "");
        allResult.add(9, "");
       // getOverpaymentAllMonth(dopPlatej, false);
    }*/


    public Double getPlatej(Double sumCredit, int termCredit){
        Double result = Rounding(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
        return Rounding(result);
    }

    public Double getDeltaDefault(Double platej, int termCredit){
        Double delta = Rounding(platej * termCredit - sumCredit);
        allResult.set(5, String.valueOf(delta));
        return delta;
    }

    /*public Double getDeltaNew(Double platej){
        if ((sumCredit <= 0) || (termCredit == 0))
            initializationInParameter();
        Double resultDeltaNew = *//*Rounding*//*(getPlatej(sumCredit, getTerm(platej)) * getTerm(getPlatej(sumCredit, getTerm(platej))) - sumCredit);
        allResult.set(5, String.valueOf(resultDeltaNew));
        Log.v(TAG, "Переплата: " + resultDeltaNew.toString());
        return resultDeltaNew;
    }*/

    /*public int getTerm(Double platej){
        if ((sumCredit <= 0) || (termCredit == 0))
            initializationInParameter();
        int n = (int) Math.round(Math.log((platej) / ((platej) - (percend/100./12) * sumCredit)) / Math.log(1 + (percend/100./12)));
        allResult.set(6, String.valueOf(n));
        termCredit = n;
        return n;
    }*/

   public void getOverpaymentAllMonth(Double dopPlatej, boolean overPayment){
       hmPaymentMonth.clear();
       Double sumCredit = Double.valueOf(allResult.get(1));
        int termCredit = Integer.valueOf(allResult.get(2));
        listResult = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm = null;
        String[] from = new String[]{"Number", "Payment", "Image", "Dolg", "Delta"};
        int i = 0;
        Double constPlat = 0.0;
        Double allPer = 0.0;
        while (sumCredit > 0.0){
            i++;
            constPlat = getPlatej(sumCredit, termCredit);
            Log.d(TAG, "Месяц: " + i + ". Платеж: " +getPlatej(sumCredit, termCredit));

            hm = new HashMap<String, String>();
            if (overPayment)
                hm.put(from[2], String.valueOf(1));
            else
                hm.put(from[2], String.valueOf(0));
            if (i < 10)
                hm.put(from[0], " "+String.valueOf(i)+" ");
            else if (i > 9 & i < 100)
                hm.put(from[0], String.valueOf(i)+" ");
            else
                hm.put(from[0], String.valueOf(i));

            allPer = allPer + (sumCredit * (percend/100.) / 12);
            if (sumCredit < (dopPlatej)){
                hm.put(from[1], setMask(Rounding(dopPlatej)));
                hm.put(from[3], setMask(Rounding(dopPlatej - (sumCredit * (percend/100.) / 12))));
                hm.put(from[4], setMask(Rounding((sumCredit * (percend/100.) / 12))));
                dopPlatej = sumCredit + (sumCredit * (percend/100.) / 12);
                sumCredit = sumCredit - dopPlatej;
            }
            else{
                hm.put(from[1], setMask(Rounding(dopPlatej)));
                hm.put(from[3], setMask(Rounding(dopPlatej - (sumCredit * (percend/100.) / 12))));
                hm.put(from[4], setMask(Rounding((sumCredit * (percend/100.) / 12))));
                sumCredit = Rounding(sumCredit - (dopPlatej - (sumCredit * (percend/100.) / 12)));
            }
            listResult.add(hm);
            termCredit--;
            Log.d(TAG, "========");
            Log.d(TAG, "Сумма долга: " + sumCredit);
            Log.d(TAG, "Доп. платеж: " + dopPlatej);
            Log.d(TAG, "Общий платеж: " + String.valueOf(constPlat + dopPlatej));
            Log.d(TAG, "Переплата: " + (sumCredit * (percend/100.) / 12));
            Log.d(TAG, "________");
        }
        Log.d(TAG, "Общая переплата: " + allPer);
        allPer = Rounding(allPer);
        allResult.set(8, String.valueOf(allPer)); //Общая переплата
        allResult.set(9, String.valueOf(i)); //Срок погашения


        hm = new HashMap<String, String>();
        hm.put(from[0], "Всего");
        hm.put(from[1], setMask(Double.valueOf(allResult.get(1)) + Double.valueOf(allResult.get(5))));
        hm.put(from[3], setMask(Double.valueOf(allResult.get(1))));
        hm.put(from[4], setMask(allPer));
        listResult.add(hm);
    }

    public void getOverpaymentSomeMonth(Double dopPaymentSomeMonth, Double dopPlatej, int j){
        Double sumCredit = Double.valueOf(allResult.get(1));
        int termCredit = Integer.valueOf(allResult.get(9));
        int i = 0;
        Double allPer = 0.0;
        if (j != 0 & !hmPaymentMonth.containsKey(j))
            hmPaymentMonth.put(j, dopPaymentSomeMonth);
        else if (hmPaymentMonth.containsKey(j)){
            hmPaymentMonth.remove(j);
            hmPaymentMonth.put(j, dopPaymentSomeMonth);
        }

        listResult = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm = null;
        String[] from = new String[]{"Number", "Payment", "Image", "Dolg", "Delta"};
        while (sumCredit > 0.0){
            Log.v(TAG, "Пересчитанный платеж: " + getPlatej(sumCredit, termCredit));
            Log.v(TAG, "Сумма: " + sumCredit + ". Срок: " + termCredit);
            Log.v(TAG, "В счет долга: " + (dopPlatej - (sumCredit * (percend/100.) / 12)));
            Log.v(TAG, "Остаток: " + String.valueOf(sumCredit - (dopPlatej - (sumCredit * (percend/100.) / 12))));
            i++;
            allPer = allPer + (sumCredit * (percend/100.) / 12);
            hm = new HashMap<String, String>();
            if (i < 10)
                hm.put(from[0], String.valueOf(i)+"  ");
            else if (i > 9 & i < 100)
                hm.put(from[0], String.valueOf(i)+" ");
            else
                hm.put(from[0], String.valueOf(i));
            if (!hmPaymentMonth.containsKey(i)){
                if (sumCredit < dopPlatej)
                    dopPlatej = sumCredit + (sumCredit * (percend/100.) / 12);
                hm.put(from[1], setMask(dopPlatej));
                hm.put(from[3], setMask(Rounding(dopPlatej - (sumCredit * (percend / 100.) / 12))));
                hm.put(from[4], setMask(Rounding((sumCredit * (percend / 100.) / 12))));
                sumCredit = Rounding(sumCredit - (getPlatej(sumCredit, termCredit) - (sumCredit * (percend/100.) / 12)));
            }
            else{
                hm.put(from[1], setMask(Rounding(hmPaymentMonth.get(i))));
                hm.put(from[2], String.valueOf(1));
                hm.put(from[3], setMask(Rounding(dopPlatej - (sumCredit * (percend / 100.) / 12))));
                hm.put(from[4], setMask(Rounding((sumCredit * (percend / 100.) / 12))));
                sumCredit = sumCredit - (Rounding(hmPaymentMonth.get(i) - (sumCredit * (percend/100.) / 12)));
                dopPlatej = getPlatej(sumCredit, termCredit - 1);
                Log.v(TAG, "Пересчитанный платеж после переплаты: " + getPlatej(sumCredit, termCredit-1));
            }
            termCredit--;
            listResult.add(hm);
        }

        hm = new HashMap<String, String>();
        hm.put(from[0], "Всего");
        hm.put(from[1], setMask(Double.valueOf(allResult.get(1)) + Double.valueOf(allResult.get(5))));
        hm.put(from[3], setMask(Double.valueOf(allResult.get(1))));
        hm.put(from[4], setMask(allPer));
        listResult.add(hm);

    }
    public void initializationInParameter(){
       sumCredit = Double.valueOf(allResult.get(1));
       termCredit = Integer.valueOf(allResult.get(2));
   }

    public Double Rounding(Double value){
        BigDecimal roundValue = BigDecimal.valueOf(value);
        roundValue = roundValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return Double.valueOf(roundValue.toString());
    }

    public String setMask(Double value){
        NumberFormat numberFormat = new DecimalFormat("###,###,###,###.00");
    return String.valueOf(numberFormat.format(value));
    }

}
