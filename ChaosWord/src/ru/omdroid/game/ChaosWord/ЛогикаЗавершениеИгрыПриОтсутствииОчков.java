package ru.omdroid.game.ChaosWord;


public class ЛогикаЗавершениеИгрыПриОтсутствииОчков {
    private int creditScore = 15;

    public boolean очкиОтсутствуют(){
        return creditScore == 0;
    }

    public void уменьшитьОчкиПослеХода(){
        creditScore--;
    }

    public void увеличитьОчкиПослеСженияСлова(int i){
        creditScore += i;
    }

    public int получитьОчки(){
        return creditScore;
    }
}
