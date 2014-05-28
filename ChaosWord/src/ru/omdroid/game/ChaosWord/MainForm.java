package ru.omdroid.game.ChaosWord;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import ru.omdroid.game.ChaosWord.ControlWriteWord.ControllerWriteWord;
import ru.omdroid.game.ChaosWord.Listener.TouchListener;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicChangeLayoutParams;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicSelectedView;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.ParamForChangingFieldGame;
import ru.omdroid.game.ChaosWord.LogicFillingFieldGame.LogicTeamSymbols;
import ru.omdroid.game.ChaosWord.db.InitializationDictionaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainForm extends Activity {

    GridLayout gridLayout;
    Button btnOk, btnNo, btnEndGame;
    TextView tvEditedWord, timerTV;
    LinearLayout llEndGame;

    LogicTeamSymbols logicTeamSymbols;
    LogicChangeLayoutParams changeLayoutParams;
    LogicSelectedView logicSelectedView;
    ParamForChangingFieldGame paramChangingFieldGame;
    ControllerWriteWord controllerWriteWord;
    CreatedPlayingField createdPlayingField;

    HashMap<Integer, TextView> hmActiveTV = new HashMap<Integer, TextView>();

    AppAnimation appAnimation;
    TimerProgress timerProgress;
    TouchListener touchListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        InitializationDictionaries initializationDB = new InitializationDictionaries(getBaseContext());
        initializationDB.Initialized();
        initializationDB.close();

        controllerWriteWord = new ControllerWriteWord();
        appAnimation = new AppAnimation(getBaseContext());
        ManagerPositionMovement.SIZE_GAME_FIELD = 5;


        gridLayout = (GridLayout)findViewById(R.id.container);
        btnOk = (Button)findViewById(R.id.btnWordOk);
        btnNo = (Button)findViewById(R.id.btnWordNo);
        btnEndGame = (Button)findViewById(R.id.btnEndGame);
        tvEditedWord = (TextView)findViewById(R.id.tvWord);
        timerTV = (TextView)findViewById(R.id.timerTV);
        llEndGame = (LinearLayout)findViewById(R.id.layoutEndGame);
        gridLayout.setRowCount(ManagerPositionMovement.SIZE_GAME_FIELD);
        gridLayout.setColumnCount(ManagerPositionMovement.SIZE_GAME_FIELD);

        logicTeamSymbols = new LogicTeamSymbols();
        paramChangingFieldGame = new ParamForChangingFieldGame(hmActiveTV, tvEditedWord, btnOk, btnNo);
        logicSelectedView = new LogicSelectedView(getBaseContext(), paramChangingFieldGame, tvEditedWord, appAnimation);

        changeLayoutParams = new LogicChangeLayoutParams(getBaseContext(), appAnimation);
        touchListener = new TouchListener(hmActiveTV, paramChangingFieldGame, logicSelectedView, changeLayoutParams);
        createdPlayingField = new CreatedPlayingField(getBaseContext(), paramChangingFieldGame, touchListener);

        gridLayout.setLayoutAnimation(appAnimation.getAnimationController());
        gridLayout.startLayoutAnimation();
        gridLayout.setOnTouchListener(touchListener);

        ManagerPositionMovement.HEIGHT_SCREEN = getHeightScreen();
        ManagerPositionMovement.EMPTY_GATE = emptyCage();
        timerProgress = new TimerProgress(llEndGame, timerTV);
        createdPlayingField.addViewInContainer(gridLayout);

        changeLayoutParams.setCreateView(createdPlayingField.getHashMapCreatedView());
        paramChangingFieldGame.setListCreatedTV(createdPlayingField.getListCreatedView());

        timerProgress.nextTimer();

    }

    @Override
    public void onStop(){
        super.onStop();
        timerProgress.stopTimer();
    }


    private int getHeightScreen(){
        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        return  dMetrics.widthPixels;
    }

    private int emptyCage(){
        Random random = new Random();
        return random.nextInt(ManagerPositionMovement.SIZE_GAME_FIELD * ManagerPositionMovement.SIZE_GAME_FIELD) + 1;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnWordNo:
                paramChangingFieldGame.parametersDefault(logicSelectedView);
                break;
            case R.id.btnWordOk:
                if (controlWriteWord(tvEditedWord.getText().toString())){
                    createdPlayingField.reloadAlphabet(tvEditedWord.getText().toString());
                    for (int i = 0; i < paramChangingFieldGame.getHmActiveTV().size(); i++){
                        paramChangingFieldGame.getHmActiveTV().get(i).setText(logicTeamSymbols.getSymbol(tvEditedWord.getText().toString()));
                        paramChangingFieldGame.getHmActiveTV().get(i).startAnimation(appAnimation.getAnimationValidWord());
                    }
                    timerProgress.updateTime(tvEditedWord.getText().toString().length() * 2);
                    paramChangingFieldGame.parametersDefault(logicSelectedView);
                    timerProgress.stopTimer();
                    timerProgress.updateTime(0);
                }
                else
                    ignoreSelectWord(tvEditedWord.getText().toString());
                break;
            case R.id.btnEndGame:
                timerProgress.restartTimer();
                break;
        }
    }

    private void selectedTExtView(TextView view){
        view.setBackgroundColor(getResources().getColor(R.color.background_select_gate));
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private boolean controlWriteWord(String word){
        return  (controllerWriteWord.controlWordInDictionaries("SELECT " + ControllerWriteWord.F_WORD + " FROM " + ControllerWriteWord.T_WORDS + " WHERE " + ControllerWriteWord.F_WORD + " = '" + word + "'"));
    }

    private void ignoreSelectWord(String word){
        /*for (int i = 0; i < hmActiveTV.size(); i++){
            hmActiveTV.get(i).setBackgroundColor(getResources().getColor(R.color.background_not_valid_word));
        }*/
        for (int i = 0; i < paramChangingFieldGame.getHmActiveTV().size(); i++){
            paramChangingFieldGame.getHmActiveTV().get(i).startAnimation(appAnimation.getAnimationNotValidWord());
        }
        Toast.makeText(getBaseContext(), "Слова " + word + " нет в словаре", Toast.LENGTH_LONG).show();
        /*for (int i = 0; i < hmActiveTV.size(); i++){
            selectedTExtView(hmActiveTV.get(i));
        }*/
    }
}
