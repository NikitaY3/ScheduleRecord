package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyFinishAdapter;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMy2FinishSch extends AppCompatActivity {

    @BindView(R.id.finSchedulesListView)
    ListView finSchedulesListView;

    private List<FinishSQLiteUser> dataList;
    private FinishSQLite helper;
    private String DBName = "finish";
    private int version = 1, j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_2finish);
        ButterKnife.bind(this);

        onResume1();

    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }

    public void onResume1() {

        helper = new FinishSQLite(MainMy2FinishSch.this, DBName, null, version);
        helper.getReadableDatabase();
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        MyFinishAdapter adapter = new MyFinishAdapter(MainMy2FinishSch.this, dataList);

        Toast.makeText(this, "finish表查询", Toast.LENGTH_SHORT).show();

        finSchedulesListView.setAdapter(adapter);
    }

}
