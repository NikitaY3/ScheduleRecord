package com.schedule.record.app.mainmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.schedule.record.app.R;

public class MainMy2FinishSchNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_2finish_note);

        Intent intent = getIntent();
        String dayid = intent.getStringExtra("dayid");
    }
}