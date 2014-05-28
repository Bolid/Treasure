package ru.omdroid.game.ChaosWord;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.Listener.TouchListener;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.ParamForChangingFieldGame;
import ru.omdroid.game.ChaosWord.LogicFillingFieldGame.LogicTeamSymbols;

import java.util.ArrayList;
import java.util.HashMap;


public class CreatedPlayingField {
    LogicTeamSymbols logicTeamSymbols;
    Context context;
    ParamForChangingFieldGame paramChangingFieldGame;
    HashMap<Integer, TextView> hmCreateView = new HashMap<Integer, TextView>();
    ArrayList<TextView> listCreatedTV = new ArrayList<TextView>();
    TouchListener touchListener;

    public CreatedPlayingField(Context context, ParamForChangingFieldGame paramChangingFieldGame, TouchListener touchListener){
        this.context = context;
        this.paramChangingFieldGame = paramChangingFieldGame;
        this.touchListener = touchListener;
    }

    public void addViewInContainer(GridLayout gridLayout){
        logicTeamSymbols = new LogicTeamSymbols();
        int countCage = 0;
        int numberTV = 0;
        for (int rows = 0; rows < ManagerPositionMovement.SIZE_GAME_FIELD; rows++)
            for (int cells = 0; cells < ManagerPositionMovement.SIZE_GAME_FIELD; cells++){
                countCage++;
                if (countCage != ManagerPositionMovement.EMPTY_GATE){
                    gridLayout.addView(createTextView(rows, cells, countCage, numberTV, logicTeamSymbols));
                    numberTV++;
                }
                else {
                    ManagerPositionMovement.ROW_EMPTY_GATE = rows;
                    ManagerPositionMovement.CELL_EMPTY_GATE = cells;
                }
            }
    }

    private TextView createTextView(final int positionInRow, final int positionInCell, final int position, int numberTV, LogicTeamSymbols logicTeamSymbols){
        final TextView textView = new TextView(context);
        GridLayout.Spec cell = GridLayout.spec(positionInCell);
        GridLayout.Spec row = GridLayout.spec(positionInRow);
        final GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(row, cell);
        layoutParams.setMargins(1, 1, 1, 1);
        textView.setLayoutParams(layoutParams);
        textView.setText(logicTeamSymbols.getSymbol(""));
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(context.getResources().getDrawable(R.drawable.btn));
        textView.setTextSize(16);
        textView.setHeight(ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20);
        textView.setWidth(ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20);

        textView.setOnTouchListener(touchListener);

        hmCreateView.put(position, textView);
        listCreatedTV.add(numberTV, textView);

        return textView;
    }

    public HashMap<Integer, TextView> getHashMapCreatedView(){
        return hmCreateView;
    }

    public ArrayList<TextView> getListCreatedView(){
        return listCreatedTV;
    }

    public void reloadAlphabet(String str){
        logicTeamSymbols.reloadSymbolToAlphabet(str);
    }
}
