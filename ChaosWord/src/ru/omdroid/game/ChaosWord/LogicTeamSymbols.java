package ru.omdroid.game.ChaosWord;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogicTeamSymbols {
    String alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    Pattern pattern = Pattern.compile("А");
    Matcher matcher = pattern.matcher(alphabet);
    int count = 0;
    public void getCountSymbolToArray(){
        while (matcher.find())
            count++;

        Log.i("Количество буквы А: ", String.valueOf(count));
    }

    private int ShareSelectedCharacter(int count){
        return 25 * count / 100;
    }
}
