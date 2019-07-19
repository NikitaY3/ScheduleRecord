package com.schedule.record.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainMyFinishSchNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_finishschedules_note);

        Intent intent = getIntent();
        String dayid = intent.getStringExtra("dayid");
    }
}