package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.List;

import butterknife.ButterKnife;

public class MainMyL2GInsert extends AppCompatActivity {

    ;
    private List<TodaySQLiteUser> dataList;
    private TodaySQLite helper;
    private String DBName = "today";
    private int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l21g_insert);
        ButterKnife.bind(this);

    }
}
