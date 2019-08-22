package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyFinishAdapter;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMy2FinishSch extends AppCompatActivity {

    @BindView(R.id.finSchedulesListView)
    ListView finSchedulesListView;

    private List<FinishSQLiteUser> dataList;
    private FinishSQLite helper;
    String DBName = "finish";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_2finish);
        ButterKnife.bind(this);

    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }

    public void onResume1() {

        helper = new FinishSQLite(MainMy2FinishSch.this, DBName, null, version);
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        MyFinishAdapter adapter = new MyFinishAdapter(MainMy2FinishSch.this,dataList);

        finSchedulesListView.setAdapter(adapter);
    }
}
