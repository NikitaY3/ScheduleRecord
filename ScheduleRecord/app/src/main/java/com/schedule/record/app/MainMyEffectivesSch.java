package com.schedule.record.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.schedule.record.app.adapter.MyEffectivesSchAdapter;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

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
    private List<FutureSQLiteUser> dataList1;
    private List<FutureSQLiteUser> dataList2;
    private FutureSQLite helper;
    String DBName = "future";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_effectiveschedules);
        ButterKnife.bind(this);

//        dataList1 = new ArrayList<>();
        helper = new FutureSQLite(MainMyEffectivesSch.this, DBName, null, version);
        helper.getReadableDatabase();
        FutureSQLiteUserDao dao = new FutureSQLiteUserDao(helper);
        dataList1 = dao.quiryAndSetItem();
//        dataList2 = dao.quiryFutureAndSetItem();
        dataList1.addAll(dao.quiryAndSetItem());
        MyEffectivesSchAdapter adapter = new MyEffectivesSchAdapter(MainMyEffectivesSch.this,dataList1);

        effSchedulesListView.setAdapter(adapter);
    }

    @OnClick(R.id.effSchedulesButton)
    public void onViewClicked() {
    }

}
