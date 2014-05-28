package ru.omdroid.game.ChaosWord.LogicFillingFieldGame;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogicTeamSymbols {
    Random random = new Random();

    String alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    String currentStr = "";
    String selectCharacter;
    Pattern pattern;
    Matcher matcher;
    int count;

    ReplaceNotAllowableSymbol repSymbol = new ReplaceNotAllowableSymbol(alphabet);

    public int getCountSymbolToArray(String str){
        pattern = Pattern.compile(str);
        matcher = pattern.matcher(currentStr);
        count = 0;
        while (matcher.find())
            count++;

        return count;
    }

    private float shareSelectedCharacter(int count){
        return count * 100 / 24;
    }

    private void controlSelectCharacter(String str){
        alphabet = repSymbol.deleteCharFromAlphabet(str, shareSelectedCharacter(getCountSymbolToArray(str)));
        currentStr += str;
    }

    public String getSymbol(String str){
        if (!str.equals(""))
            deleteSymbolFromCurrentWord(str);
        selectCharacter = String.valueOf(alphabet.charAt(random.nextInt(alphabet.length())));
        controlSelectCharacter(selectCharacter);
        return selectCharacter;
    }

    public void reloadSymbolToAlphabet(String str){
        alphabet = repSymbol.reloadCharToAlphabet(str);
    }

    private void deleteSymbolFromCurrentWord(String str){
        for (int i = 0; i < str.length(); i++)
            currentStr = currentStr.replaceFirst(str, "");
    }

}
