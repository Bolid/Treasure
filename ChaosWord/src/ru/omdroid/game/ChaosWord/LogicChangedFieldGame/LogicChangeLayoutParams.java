package ru.omdroid.game.ChaosWord.LogicChangedFieldGame;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.AppAnimation;
import ru.omdroid.game.ChaosWord.ManagerPositionMovement;

import java.util.HashMap;


public class LogicChangeLayoutParams {

    Context context;
    HashMap<Integer, TextView> hm;
    AppAnimation appAnimation;

    public LogicChangeLayoutParams(Context context, AppAnimation appAnimation){
        this.context = context;
        this.appAnimation = appAnimation;
    }

    public boolean directionMove(float touchDownX, float touchUpX, float touchDownY, float touchUpY){
        return Math.abs(touchDownX - touchUpX) > Math.abs(touchDownY - touchUpY);
    }

    public void changeLayoutParamsToTop(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD).startAnimation(appAnimation.getAnimationChangeLayoutParam());
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD));
        hm.remove(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD);
        ManagerPositionMovement.EMPTY_GATE  += ManagerPositionMovement.SIZE_GAME_FIELD;
        ManagerPositionMovement.ROW_EMPTY_GATE++;
    }

    public void changeLayoutParamsToBottom(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD).startAnimation(appAnimation.getAnimationChangeLayoutParam());
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD));
        hm.remove(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD);
        ManagerPositionMovement.EMPTY_GATE  -= ManagerPositionMovement.SIZE_GAME_FIELD;
        ManagerPositionMovement.ROW_EMPTY_GATE--;
    }

    public void changeLayoutParamsToLeft(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE +1).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE +1).startAnimation(appAnimation.getAnimationChangeLayoutParam());
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE  + 1));
        hm.remove(ManagerPositionMovement.EMPTY_GATE  + 1);
        ManagerPositionMovement.EMPTY_GATE ++;
        ManagerPositionMovement.CELL_EMPTY_GATE ++;
    }

    public void changeLayoutParamsToRight(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE -1).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE -1).startAnimation(appAnimation.getAnimationChangeLayoutParam());
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE -1));
        hm.remove(ManagerPositionMovement.EMPTY_GATE -1);
        ManagerPositionMovement.EMPTY_GATE --;
        ManagerPositionMovement.CELL_EMPTY_GATE--;
    }

    public void setCreateView(HashMap<Integer, TextView> hm){
        this.hm = hm;
    }
}
