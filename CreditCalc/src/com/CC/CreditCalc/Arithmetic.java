package com.CC.CreditCalc;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Arithmetic {
    public static final String TAG = "com.CC.CreditCalc.Arithmetic";
    int sumCredit = 0;
    int termCredit = 0;
    Double percend = 0.0;
    public Arithmetic(Integer sumCredit, Integer termCredit, Integer percend){
        this.sumCredit = sumCredit;
        this.termCredit = termCredit;
        this.percend = Double.valueOf(percend);
    }

    public BigDecimal setPlatej(){
        BigDecimal platej = BigDecimal.valueOf(sumCredit * ((percend/100./12) * Math.pow((1+(percend/100./12)), termCredit)) / (Math.pow((1+(percend/100./12)),termCredit) - 1));
        return (platej.setScale(2, RoundingMode.HALF_DOWN));
    }
    public BigDecimal setDelta(){
        BigDecimal delta = BigDecimal.valueOf(Double.valueOf(setPlatej().toString()) * termCredit - sumCredit);
        return delta.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }
}
