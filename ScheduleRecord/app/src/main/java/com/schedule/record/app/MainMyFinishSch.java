package com.schedule.record.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schedule.record.app.adapter.MyFinishSchAdapter;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMyFinishSch extends AppCompatActivity {

    @BindView(R.id.finSchedulesListView)
    ListView finSchedulesListView;
    private List<DaySQLiteUser> dataList;
    private DaySQLite helper;
    String DBName = "day_1";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_finishschedules);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }
    public void onResume1() {
        helper = new DaySQLite(MainMyFinishSch.this, DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        dataList = (List<DaySQLiteUser>) dao.quiryAndSetItem();
        MyFinishSchAdapter adapter = new MyFinishSchAdapter(MainMyFinishSch.this, dataList);

        finSchedulesListView.setAdapter(adapter);
    }
}
