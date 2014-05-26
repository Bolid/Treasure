package ru.omdroid.game.ChaosWord.Listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.ManagerPositionMovement;

public class TouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float touchDownX = 0, touchUpX = 0, touchDownY = 0, touchUpY = 0, positionXLastSelectedTV = 0, positionYLastSelectedTV = 0;
        TextView tv;

        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchDownX = motionEvent.getX();
                touchDownY = motionEvent.getY();
                Log.i(TAG, "Первая координата" + motionEvent.getX());
                break;
            case MotionEvent.ACTION_UP:
                touchUpX = motionEvent.getX();
                touchUpY = motionEvent.getY();
                Log.i(TAG, "Вторая координата" + motionEvent.getX());

                if ((touchDownX == touchUpX & touchDownY == touchUpY) || editWord){
                    try{

                        tv = (TextView) view;

                        if (hmActiveTV.containsValue(tv)){
                            hmActiveTV = controlDoublePress(tv);
                        }
                        else{
                            if (controlCurrentPressedTextView(tv.getX(), tv.getY()) || !editWord){
                                hmActiveTV.put(countSelectedTV, tv);
                                positionXLastSelectedTV = tv.getX();
                                positionYLastSelectedTV = tv.getY();
                                selectedTExtView(tv);
                                countSelectedTV++;
                                btnOk.setEnabled(true);
                                btnNo.setEnabled(true);
                            }
                        }
                        writeWord(tvEditedWord);
                        changeNotActiveTextView(tv);
                        editWord = hmActiveTV.size() != 0;
                        if (!editWord)
                            parametersDefault();
                    }
                    catch (ClassCastException e){
                        Log.e(TAG, "Ошибка класса:  ", e);
                    }
                    return false;
                }

                if (!editWord){
                    if (directionMove(touchDownX, touchUpX, touchDownY, touchUpY)){
                        if ((touchDownX < touchUpX) & (ManagerPositionMovement.CELL_EMPTY_GATE != 0)){
                            Log.i(TAG, "Движение вправо  " + touchDownX + "   " + touchUpX);
                            changeLayoutParams.changeLayoutParamsToRight();
                            return true;
                        }
                        else
                        if ((touchDownX > touchUpX) & ManagerPositionMovement.CELL_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)
                        {
                            Log.i(TAG, "Движение влево  " + touchDownX + "   " + touchUpX);
                            changeLayoutParams.changeLayoutParamsToLeft();
                            return true;
                        }
                    }
                    else{
                        if (touchDownY > touchUpY & (ManagerPositionMovement.ROW_EMPTY_GATE != ManagerPositionMovement.SIZE_GAME_FIELD - 1)) {
                            Log.i(TAG, "Движение вверх  " + touchDownY + "   " + touchUpY);
                            changeLayoutParams.changeLayoutParamsToTop();
                            return true;
                        }
                        else
                        if (touchDownY < touchUpY & ManagerPositionMovement.ROW_EMPTY_GATE != 0){
                            Log.i(TAG, "Движение вниз  " + touchDownY + "   " + touchUpY);
                            changeLayoutParams.changeLayoutParamsToBottom();
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return true;;
    }
}
