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
    private List<DaySQLiteUser> dataList1;
    private List<DaySQLiteUser> dataList2;
    private DaySQLite helper;
    String DBName = "day_1";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_effectiveschedules);
        ButterKnife.bind(this);

//        dataList1 = new ArrayList<>();
        helper = new DaySQLite(MainMyEffectivesSch.this, DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        dataList1 = dao.quiryTodayAndSetItem();
//        dataList2 = dao.quiryFutureAndSetItem();
        dataList1.addAll(dao.quiryFutureAndSetItem());
        MyEffectivesSchAdapter adapter = new MyEffectivesSchAdapter(MainMyEffectivesSch.this,dataList1);

        effSchedulesListView.setAdapter(adapter);
    }

    @OnClick(R.id.effSchedulesButton)
    public void onViewClicked() {
    }

}
