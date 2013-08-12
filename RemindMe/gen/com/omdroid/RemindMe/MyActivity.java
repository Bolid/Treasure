package com.omdroid.RemindMe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.omdroid.RemindMe.R;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button butDataSave = (Button)findViewById(R.id.butSaveNotify);
        final EditText etDataNotify = (EditText)findViewById(R.id.etDataNotify);
        butDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemindMe remindMe = new RemindMe(etDataNotify.getText().toString(), (long) 124124232, getBaseContext());
            }
        });
    }
}
