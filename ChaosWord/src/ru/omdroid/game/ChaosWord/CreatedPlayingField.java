package ru.omdroid.game.ChaosWord;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;
import ru.omdroid.game.ChaosWord.LogicFillingFieldGame.LogicTeamSymbols;


public class CreatedPlayingField {

    Context context;

    public CreatedPlayingField(Context context){
        this.context = context;
    }

    private void addViewInContainer(GridLayout gridLayout, LogicTeamSymbols logicTeamSymbols){
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
        textView.setBackgroundColor(context.getResources().getColor(R.color.background_gate));
        textView.setTextSize(16);
        textView.setHeight(heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20);
        textView.setWidth(heightScreen / ManagerPositionMovement.SIZE_GAME_FIELD - 20);

        textView.setOnTouchListener(this);

        hm.put(position, textView);
        listCreatedTV.add(numberTV, textView);

        return textView;
    }
}
