package ru.omdroid.game.ChaosWord.Forms;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import ru.omdroid.game.ChaosWord.*;
import ru.omdroid.game.ChaosWord.AppDB.AppWordDB;
import ru.omdroid.game.ChaosWord.ControlWriteWord.ControllerWriteWord;
import ru.omdroid.game.ChaosWord.Dictionaries.InitializationDictionaries;
import ru.omdroid.game.ChaosWord.Listener.TouchListener;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicChangeLayoutParams;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.LogicSelectedView;
import ru.omdroid.game.ChaosWord.LogicChangedFieldGame.ParamForChangingFieldGame;
import ru.omdroid.game.ChaosWord.LogicFillingFieldGame.LogicTeamSymbols;
import ru.omdroid.game.ChaosWord.Process;

import java.util.HashMap;
import java.util.Random;

public class PlayProcessForm extends Activity {

    GridLayout gridLayout;
    Button btnOk, btnNo, btnEndGame;
    TextView tvEditedWord, tvTimer, tvCountWord, tvScore;
    LinearLayout llEndGame;

    LogicTeamSymbols logicTeamSymbols;
    LogicChangeLayoutParams changeLayoutParams;
    LogicSelectedView logicSelectedView;
    ЛогикаЗавершениеИгрыПриОтсутствииОчков логикаЗавершениеИгрыПриОтсутствииОчков;
    ParamForChangingFieldGame paramChangingFieldGame;
    ControllerWriteWord controllerWriteWord;
    CreatedPlayingField createdPlayingField;
    ru.omdroid.game.ChaosWord.Process process;
    HashMap<Integer, TextView> hmActiveTV = new HashMap<Integer, TextView>();

    AppAnimation appAnimation;
    TimerProgress timerProgress;
    TouchListener touchListener;

    AppWordDB appWordDB;

    String currentUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        currentUser = getIntent().getExtras().getString("CurrentUser");

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
        tvTimer = (TextView)findViewById(R.id.timerTV);
        tvCountWord = (TextView)findViewById(R.id.tvCountWord);
        tvScore = (TextView)findViewById(R.id.tvScore);
        llEndGame = (LinearLayout)findViewById(R.id.layoutEndGame);
        gridLayout.setRowCount(ManagerPositionMovement.SIZE_GAME_FIELD);
        gridLayout.setColumnCount(ManagerPositionMovement.SIZE_GAME_FIELD);

        logicTeamSymbols = new LogicTeamSymbols();
        paramChangingFieldGame = new ParamForChangingFieldGame(hmActiveTV, tvEditedWord, btnOk, btnNo);
        logicSelectedView = new LogicSelectedView(getBaseContext(), paramChangingFieldGame, tvEditedWord, appAnimation);
        process = new Process();
        changeLayoutParams = new LogicChangeLayoutParams(getBaseContext(), appAnimation);
        логикаЗавершениеИгрыПриОтсутствииОчков = new ЛогикаЗавершениеИгрыПриОтсутствииОчков();
        touchListener = new TouchListener(hmActiveTV, paramChangingFieldGame, logicSelectedView, changeLayoutParams, логикаЗавершениеИгрыПриОтсутствииОчков);
        createdPlayingField = new CreatedPlayingField(getBaseContext(), paramChangingFieldGame, touchListener);

        gridLayout.setLayoutAnimation(appAnimation.getAnimationController());
        gridLayout.startLayoutAnimation();
        gridLayout.setOnTouchListener(touchListener);

        ManagerPositionMovement.HEIGHT_SCREEN = getHeightScreen();
        ManagerPositionMovement.EMPTY_GATE = emptyCage();
        timerProgress = new TimerProgress(llEndGame, tvTimer);
        createdPlayingField.addViewInContainer(gridLayout);

        changeLayoutParams.setCreateView(createdPlayingField.getHashMapCreatedView());
        paramChangingFieldGame.setListCreatedTV(createdPlayingField.getListCreatedView());

        timerProgress.nextTimer();

    }

    @Override
    public void onResume(){
        super.onResume();
        appWordDB = AppWordDB.getInstance();
        tvCountWord.setText(process.getCountSelectedWords(currentUser));
        tvScore.setText(process.getScoreFromDB(currentUser));
    }

    @Override
    public void onStop(){
        super.onStop();
        timerProgress.stopTimer();
    }

    /*@Override
    public void onDestroy(){
        super.onDestroy();
        process.deleteProcess();
        //appWordDB.dbClose();
    }*/


    private int getHeightScreen(){
        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        return  dMetrics.widthPixels;
    }

    private int emptyCage(){
        Random random = new Random();
        return random.nextInt(ManagerPositionMovement.SIZE_GAME_FIELD * ManagerPositionMovement.SIZE_GAME_FIELD) + 1;
    }
    //TODO Реализовать обработку очков в классе ЛогикаЗавершениеИгрыПриОтсутствииОчков и бделать запись этих данных в БД
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
                    process.saveWordToDB(tvEditedWord.getText().toString(), currentUser);
                    process.updateScore((1 + tvEditedWord.getText().toString().length() - 3) * tvEditedWord.getText().toString().length(), currentUser);
                    логикаЗавершениеИгрыПриОтсутствииОчков.увеличитьОчкиПослеСженияСлова((1 + tvEditedWord.getText().toString().length() - 3) * tvEditedWord.getText().toString().length());
                    tvCountWord.setText(process.getCountSelectedWords(currentUser));
                    tvScore.setText(process.getScoreFromDB(currentUser));
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

    private boolean controlWriteWord(String word){
        return  (controllerWriteWord.controlWordInDictionaries("SELECT " + ControllerWriteWord.F_WORD + " FROM " + ControllerWriteWord.T_WORDS + " WHERE " + ControllerWriteWord.F_WORD + " = '" + word + "'"));
    }

    private void ignoreSelectWord(String word){
        for (int i = 0; i < paramChangingFieldGame.getHmActiveTV().size(); i++){
            paramChangingFieldGame.getHmActiveTV().get(i).startAnimation(appAnimation.getAnimationNotValidWord());
        }
        Toast.makeText(getBaseContext(), "Слова " + word + " нет в словаре", Toast.LENGTH_LONG).show();
    }
}
