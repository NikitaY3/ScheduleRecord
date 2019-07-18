package com.schedule.record.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.schedule.record.app.adapter.MyEffectivesSchAdapter;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyEffectivesSch extends AppCompatActivity {

    @BindView(R.id.effSchedulesButton)
    Button effSchedulesButton;
    @BindView(R.id.effSchedulesListView)
    ListView effSchedulesListView;
    ;
    private List<DaySQLiteUser> dataList;
    private DaySQLite helper;
    String DBName = "day_1";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_effectiveschedules);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.effSchedulesButton)
    public void onViewClicked() {
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }
    public void onResume1() {
        dataList = new ArrayList<>();
        helper = new DaySQLite(MainMyEffectivesSch.this, DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        MyEffectivesSchAdapter adapter = new MyEffectivesSchAdapter(MainMyEffectivesSch.this,dataList);

        effSchedulesListView.setAdapter(adapter);
    }
}
