package ru.omdroid.game.ChaosWord.Listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicChangeLayoutParams;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicControlPressedCurrentView;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicSelectedView;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.ParamForChangingFieldGame;
import ru.omdroid.game.ChaosWord.ManagerPositionMovement;

import java.util.HashMap;

public class TouchListener implements View.OnTouchListener {
    float touchDownX = 0, touchUpX, touchDownY = 0, touchUpY;

    LogicControlPressedCurrentView controlDoublePress = new LogicControlPressedCurrentView();
    LogicChangeLayoutParams changeLayoutParams;
    LogicSelectedView logicSelectedView;
    HashMap<Integer, TextView> hmActiveTV;
    ParamForChangingFieldGame paramForChangingFieldGame;

    public TouchListener(HashMap<Integer, TextView> hmActiveTV, ParamForChangingFieldGame paramForChangingFieldGame, LogicSelectedView logicSelectedView, LogicChangeLayoutParams changeLayoutParams){
        this.hmActiveTV = hmActiveTV;
        this.paramForChangingFieldGame = paramForChangingFieldGame;
        this.changeLayoutParams = changeLayoutParams;
        this.logicSelectedView = logicSelectedView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TextView tv;

        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchDownX = motionEvent.getX();
                touchDownY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchUpX = motionEvent.getX();
                touchUpY = motionEvent.getY();

                if ((touchDownX == touchUpX & touchDownY == touchUpY) || paramForChangingFieldGame.getEditWord()){
                    try{

                        tv = (TextView) view;

                        if (paramForChangingFieldGame.getHmActiveTV().containsValue(tv)){
                            paramForChangingFieldGame.setHmActiveTV(logicSelectedView.controlDoublePress(tv, paramForChangingFieldGame.getHmActiveTV()));
                        }
                        else{
                            if (controlDoublePress.controlCurrentPressedTextView(tv.getX(), tv.getY(), paramForChangingFieldGame) || !paramForChangingFieldGame.getEditWord()){
                                paramForChangingFieldGame.putViewToActiveHashMap(paramForChangingFieldGame.getHmActiveTV().size(), tv);
                                paramForChangingFieldGame.setPositionXLastSelectedTV(tv.getX());
                                paramForChangingFieldGame.setPositionYLastSelectedTV(tv.getY());
                                logicSelectedView.selectedView(tv);
                                paramForChangingFieldGame.setCountSelectedTV(1);
                                paramForChangingFieldGame.enabledButton(true);
                            }
                        }
                        logicSelectedView.writeWord(paramForChangingFieldGame.getHmActiveTV());
                        //logicSelectedView.changeNotActiveTextView(paramForChangingFieldGame.getListCreateTV(), paramForChangingFieldGame.getHmActiveTV());
                        paramForChangingFieldGame.setEditWord(paramForChangingFieldGame.getHmActiveTV().size() != 0);
                        if (!paramForChangingFieldGame.getEditWord())
                            paramForChangingFieldGame.parametersDefault(logicSelectedView);
                    }
                    catch (ClassCastException ignored){
                    }
                    return false;
                }

                if (!paramForChangingFieldGame.getEditWord()){
                    if (changeLayoutParams.directionMove(touchDownX, touchUpX, touchDownY, touchUpY)){
                        if ((touchDownX < touchUpX) & (ManagerPositionMovement.CELL_EMPTY_GATE != 0)){
                            changeLayoutParams.changeLayoutParamsToRight();
                            return true;
                        }
                        else
                        if ((touchDownX > touchUpX) & ManagerPositionMovement.CELL_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)
                        {
                            changeLayoutParams.changeLayoutParamsToLeft();
                            return true;
                        }
                    }
                    else{
                        if (touchDownY > touchUpY & (ManagerPositionMovement.ROW_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)) {
                            changeLayoutParams.changeLayoutParamsToTop();
                            return true;
                        }
                        else
                        if (touchDownY < touchUpY & ManagerPositionMovement.ROW_EMPTY_GATE != 0){
                            changeLayoutParams.changeLayoutParamsToBottom();
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }
}
