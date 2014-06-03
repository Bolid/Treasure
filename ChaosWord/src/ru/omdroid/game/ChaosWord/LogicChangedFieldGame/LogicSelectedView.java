package ru.omdroid.game.ChaosWord.LogicChangedFieldGame;


import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.AppAnimation;
import ru.omdroid.game.ChaosWord.R;

import java.util.ArrayList;
import java.util.HashMap;

public class LogicSelectedView {

    Context context;
    ParamForChangingFieldGame paramChangingFieldGame;
    TextView tv;
    AppAnimation appAnimation;


    public LogicSelectedView(Context context, ParamForChangingFieldGame paramChangingFieldGame, TextView tv, AppAnimation appAnimation){
        this.context = context;
        this.paramChangingFieldGame = paramChangingFieldGame;
        this.tv = tv;
        this.appAnimation = appAnimation;
    }

    public void selectedView(TextView view){
        view.setBackground(context.getResources().getDrawable(R.drawable.btn_select));
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public HashMap<Integer, TextView> controlDoublePress(TextView tv, HashMap<Integer, TextView> hmActiveView){

        HashMap<Integer, TextView> hashMap = new HashMap<Integer, TextView>();
        paramChangingFieldGame.setPositionYLastSelectedTV(0);
        paramChangingFieldGame.setPositionXLastSelectedTV(0);
        for (int i = 0; i < hmActiveView.size(); i++){
            if (tv.hashCode() == hmActiveView.get(i).hashCode()){
                paramChangingFieldGame.setCountSelectedTV(hashMap.size());
                return hashMap;
            }
            else{
                paramChangingFieldGame.setPositionYLastSelectedTV(hmActiveView.get(i).getY());
                paramChangingFieldGame.setPositionXLastSelectedTV(hmActiveView.get(i).getX());
                hashMap.put(i, hmActiveView.get(i));
            }
        }
        return hashMap;
    }

    public void writeWord(HashMap<Integer, TextView> hmActiveTV){
        tv.setText("");
        if (hmActiveTV.size() != 0)
            for (int i = 0; i < hmActiveTV.size(); i++)
                tv.append(hmActiveTV.get(i).getText());
    }

    public void dontSelectedTV(ArrayList<TextView> listCreatedTV){
        for (TextView tv : listCreatedTV){
            tv.setBackground(context.getResources().getDrawable(R.drawable.btn));
            tv.setScaleX(1.0f);
            tv.setScaleY(1.0f);
            tv.setTypeface(Typeface.DEFAULT);
        }
        paramChangingFieldGame.setCountSelectedTV(0);
    }

    public void changeNotActiveTextView(ArrayList<TextView> listCreatedTV, HashMap<Integer, TextView> hmActiveTV){
        for (TextView tv : listCreatedTV){
            if (!hmActiveTV.containsValue(tv)){
                {
                    tv.setScaleX(0.8f);
                    tv.setScaleY(0.8f);
                    tv.setBackgroundColor(context.getResources().getColor(R.color.background_not_active_gate));
                    if (!paramChangingFieldGame.getEditWord())
                        tv.startAnimation(appAnimation.getAnimationNotSelectTextView());
                }
            }
        }
    }

}
