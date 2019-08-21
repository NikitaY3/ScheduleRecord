package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class MainMyL1Doc extends AppCompatActivity {

    @BindView(R.id.finNoteTextView1)
    TextView finNoteTextView1;


    private List<FinishSQLiteUser> dataList;
    private FinishSQLite helper;
    String DBName = "finish";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l1doc);
        ButterKnife.bind(this);

        helper = new FinishSQLite(MainMyL1Doc.this, DBName, null, version);
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper);
        finNoteTextView1.setText(dao.queryAllString());
    }
}