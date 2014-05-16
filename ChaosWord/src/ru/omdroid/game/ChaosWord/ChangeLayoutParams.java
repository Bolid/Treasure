package ru.omdroid.game.ChaosWord;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.HashMap;


public class ChangeLayoutParams {

    Context context;
    Animation anim;
    HashMap<Integer, TextView> hm;

    public ChangeLayoutParams(Context context, HashMap<Integer, TextView> hm){
        this.context = context;
        this.hm = hm;

        anim = AnimationUtils.loadAnimation(context, R.anim.textview_anim);
    }
    public void changeLayoutParamsToTop(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD).startAnimation(anim);
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD));
        hm.remove(ManagerPositionMovement.EMPTY_GATE + ManagerPositionMovement.SIZE_GAME_FIELD);
        ManagerPositionMovement.EMPTY_GATE  += ManagerPositionMovement.SIZE_GAME_FIELD;
        ManagerPositionMovement.ROW_EMPTY_GATE++;
    }

    public void changeLayoutParamsToBottom(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD).startAnimation(anim);
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD));
        hm.remove(ManagerPositionMovement.EMPTY_GATE - ManagerPositionMovement.SIZE_GAME_FIELD);
        ManagerPositionMovement.EMPTY_GATE  -= ManagerPositionMovement.SIZE_GAME_FIELD;
        ManagerPositionMovement.ROW_EMPTY_GATE--;
    }

    public void changeLayoutParamsToLeft(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE +1).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE +1).startAnimation(anim);
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE  + 1));
        hm.remove(ManagerPositionMovement.EMPTY_GATE  + 1);
        ManagerPositionMovement.EMPTY_GATE ++;
        ManagerPositionMovement.CELL_EMPTY_GATE ++;
    }

    public void changeLayoutParamsToRight(){
        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(GridLayout.spec(ManagerPositionMovement.ROW_EMPTY_GATE), GridLayout.spec(ManagerPositionMovement.CELL_EMPTY_GATE));
        layoutParams.setMargins(1, 1, 1, 1);
        hm.get(ManagerPositionMovement.EMPTY_GATE -1).setLayoutParams(layoutParams);
        hm.get(ManagerPositionMovement.EMPTY_GATE -1).startAnimation(anim);
        hm.put(ManagerPositionMovement.EMPTY_GATE , hm.get(ManagerPositionMovement.EMPTY_GATE -1));
        hm.remove(ManagerPositionMovement.EMPTY_GATE -1);
        ManagerPositionMovement.EMPTY_GATE --;
        ManagerPositionMovement.CELL_EMPTY_GATE--;
    }
}
