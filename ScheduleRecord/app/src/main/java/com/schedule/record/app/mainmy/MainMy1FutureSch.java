package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyFutureAdapter;
import com.schedule.record.app.dialog.FutureDialog;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMy1FutureSch extends AppCompatActivity {

    @BindView(R.id.effSchedulesButton)
    Button effSchedulesButton;
    @BindView(R.id.effSchedulesListView)
    ListView effSchedulesListView;
    ;
    private List<FutureSQLiteUser> dataList;
    private FutureSQLite helper;
    String DBName = "future";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_1future);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.effSchedulesButton)
    public void onViewClicked() {
        FutureDialog one = new FutureDialog(this,effSchedulesListView, dataList);
        one.show();
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }

    public void onResume1() {
        helper = new FutureSQLite(MainMy1FutureSch.this, DBName, null, version);
        helper.getReadableDatabase();
        FutureSQLiteUserDao dao = new FutureSQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        MyFutureAdapter adapter = new MyFutureAdapter(MainMy1FutureSch.this,dataList);

        effSchedulesListView.setAdapter(adapter);
    }

}
