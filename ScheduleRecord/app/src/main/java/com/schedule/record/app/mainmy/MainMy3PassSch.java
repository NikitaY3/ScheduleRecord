package com.schedule.record.app.mainmy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyPassAdapter;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.dao.PassSQLiteUserDao;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMy3PassSch extends AppCompatActivity {

    @BindView(R.id.passListView)
    ListView passListView;
    ;
    private List<PassSQLiteUser> dataList;
    private PassSQLite helper;
    private String DBName = "pass";
    private int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_3pass);
        ButterKnife.bind(this);

        onResume1();
    }

    public void onResume1() {

        helper = new PassSQLite(MainMy3PassSch.this, DBName, null, version);
        helper.getReadableDatabase();
        PassSQLiteUserDao dao = new PassSQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        MyPassAdapter adapter = new MyPassAdapter(MainMy3PassSch.this,dataList);

        passListView.setAdapter(adapter);
    }
}
