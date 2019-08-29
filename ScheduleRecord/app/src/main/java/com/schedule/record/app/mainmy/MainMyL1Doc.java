package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMyL1Doc extends AppCompatActivity {

    @BindView(R.id.finNoteTextView1)
    TextView finNoteTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l1doc);
        ButterKnife.bind(this);
    }
    @Override
    protected void onResume() {
        onResume1();
        super.onResume();
    }

    private void onResume1() {
        String DBName = "finish";
        int version = 1;
        FinishSQLite helper = new FinishSQLite(MainMyL1Doc.this, DBName, null, version);
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper);
        finNoteTextView1.setText(dao.queryAllString());
    }

}
