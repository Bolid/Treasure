package ru.omdroid.game.ChaosWord.Forms;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import ru.omdroid.game.ChaosWord.R;
import ru.omdroid.game.ChaosWord.WorkContentFromDB.WorkContentGame;
import ru.omdroid.game.ChaosWord.WorkContentFromDB.WorkContentUser;


public class MainForm extends Activity{
    WorkContentUser workContentUser;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_main);
        final Button btnNewGame = (Button)findViewById(R.id.btnStartNewGame);
        final Button btnContinueGame = (Button)findViewById(R.id.btnCotninueGame);

        workContentUser = new WorkContentUser();
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.usedUser);
        autoCompleteTextView.setText(workContentUser.getLastUsedUser());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, workContentUser.getArraySavedUsersFromDB());

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                    btnContinueGame.setEnabled(workContentUser.userCompareToDB(autoCompleteTextView.getText().toString()));
            }
        });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnStartNewGame:
                if (!workContentUser.userCompareToDB(autoCompleteTextView.getText().toString()))
                    workContentUser.addUserToDB(autoCompleteTextView.getText().toString());
                workContentUser.setLastUsedUser(autoCompleteTextView.getText().toString());
                WorkContentGame workContentGame = new WorkContentGame();
                workContentGame.createNewGame(autoCompleteTextView.getText().toString());
                goToGame();
                break;
            case R.id.btnCotninueGame:
                goToGame();
        }

    }

    private void goToGame(){
        Intent intent = new Intent(this, PlayProcessForm.class);
        intent.putExtra("CurrentUser", autoCompleteTextView.getText().toString());
        startActivity(intent);
    }
}
