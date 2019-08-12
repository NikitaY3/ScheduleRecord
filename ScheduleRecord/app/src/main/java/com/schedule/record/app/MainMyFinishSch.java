package com.schedule.record.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schedule.record.app.adapter.MyFinishSchAdapter;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.dao.PassSQLiteUserDao;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMyFinishSch extends AppCompatActivity {

    @BindView(R.id.finSchedulesListView)
    ListView finSchedulesListView;
    private List<PassSQLiteUser> dataList;
    private PassSQLite helper;
    String DBName = "day_1";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_finishschedules);
        ButterKnife.bind(this);

        helper = new PassSQLite(MainMyFinishSch.this, DBName, null, version);
        helper.getReadableDatabase();
        PassSQLiteUserDao dao = new PassSQLiteUserDao(helper);

//        dataList = dao.quiryPassAndSetItem();//TodayAndSetItem();

//        MyFinishSchAdapter adapter = new MyFinishSchAdapter(MainMyFinishSch.this, dataList);

//        finSchedulesListView.setAdapter(adapter);

    }
}
