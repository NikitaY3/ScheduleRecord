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
    @BindView(R.id.finButton2)
    Button finButton2;

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

    @OnClick(R.id.finButton2)
    public void onViewClicked() {

        FinishSQLite helper1;
        String DBName1 = "finish";
        int version1 = 1;

        FinishSQLiteUser things = new FinishSQLiteUser("dfffg"+j, "1234555", true,
                true, "12:00", "只是finish", "d", "没有注解");

        helper1 = new FinishSQLite(this, DBName1, null, version1);
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper1);
        dao.insert(things);

        j++;

    }
}
