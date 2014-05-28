package ru.omdroid.game.ChaosWord.LogicChangedFieldGame;


import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ParamForChangingFieldGame {
    HashMap<Integer, TextView> hmActiveTV;
    ArrayList<TextView> listCreatedTV;

    TextView tvEditedWord;
    Button  btnOk, btnNo;

    boolean editWord = false;
    int countSelectedTV = 0;
    float positionXLastSelectedTV = 0, positionYLastSelectedTV = 0;

    public ParamForChangingFieldGame(HashMap<Integer, TextView> hmActiveTV, TextView tvEditedWord, Button btnOk, Button btnNo){
        this.hmActiveTV = hmActiveTV;
        this.tvEditedWord = tvEditedWord;
        this.btnOk = btnOk;
        this.btnNo = btnNo;
    }

    public boolean getEditWord(){
        return editWord;
    }

    public void setEditWord(boolean param){
        editWord = param;
    }

    public int getCountSelectedTV(){
        return countSelectedTV;
    }

    public void setCountSelectedTV(int param){
        countSelectedTV += param;
    }

    public float getPositionXLastSelectedTV(){
        return positionXLastSelectedTV;
    }

    public void setPositionXLastSelectedTV(float param){
        positionXLastSelectedTV = param;
    }

    public float getPositionYLastSelectedTV(){
        return positionYLastSelectedTV;
    }

    public void setPositionYLastSelectedTV(float param){
        positionYLastSelectedTV = param;
    }

    public void setListCreatedTV(ArrayList<TextView> listCreatedTV){
        this.listCreatedTV = listCreatedTV;
    }

    public ArrayList<TextView> getListCreateTV(){
        return listCreatedTV;
    }

    public HashMap<Integer, TextView> getHmActiveTV(){
        return hmActiveTV;
    }

    public void setHmActiveTV(HashMap<Integer, TextView> hmActiveTV){
        hmActiveTV.clear();
        this.hmActiveTV = hmActiveTV;
    }

    public void putViewToActiveHashMap(int key, TextView view){
        hmActiveTV.put(key, view);
    }

    public void parametersDefault(LogicSelectedView logicSelectedView){
        logicSelectedView.dontSelectedTV(getListCreateTV());
        tvEditedWord.setText("");
        setEditWord(false);
        hmActiveTV.clear();
        setPositionXLastSelectedTV(0);
        setPositionYLastSelectedTV(0);
    }

    public void enabledButton(boolean param){
        btnOk.setEnabled(param);
        btnNo.setEnabled(param);
    }
}
