package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyGeneralAdapter;
import com.schedule.record.app.function.GetFunctions.AuthorityQueryTask;
import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL2General extends AppCompatActivity {

    @BindView(R.id.generalButton1)
    Button generalButton1;
    @BindView(R.id.generalButton2)
    Button generalButton2;
    @BindView(R.id.generalList)
    ListView generalList;

    private AuthoritySQLiteUserDao dao;
    private String nameid;
    private List<AuthoritySQLiteUser> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l2general);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser", MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid", "");

        //特殊用户是我，普通用户是编辑框里面的
        String DBName = "authority";
        int version = 1;
        AuthoritySQLite helper = new AuthoritySQLite(MainMyL2General.this, DBName, null, version);
        dao = new AuthoritySQLiteUserDao(helper);

        //放入Adapter
        dataList = dao.quiryAndSetGeneralItem(nameid);
        MyGeneralAdapter adapter = new MyGeneralAdapter(MainMyL2General.this, dataList);
        generalList.setAdapter(adapter);

    }

    @OnClick({R.id.generalButton1, R.id.generalButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.generalButton1:

                //转跳页面查看所有指派给别人的日程
                Intent intent = new Intent(MainMyL2General.this, MainMyL2GInsert.class);
                intent.putExtra("gnameid","all");
                startActivity(intent);

                break;
            case R.id.generalButton2:

                //刷新数据，查询云端并更新本地，Hander
                AuthorityQueryTask authorityQueryTask = new AuthorityQueryTask(MainMyL2General.this, uiHandler);
                authorityQueryTask.execute("http://120.77.222.242:10024/authority/query?gnameId=" + nameid);

                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    //放入Adapter
                    dataList = dao.quiryAndSetGeneralItem(nameid);
                    MyGeneralAdapter adapter = new MyGeneralAdapter(MainMyL2General.this, dataList);
                    generalList.setAdapter(adapter);
                    break;
            }
        }
    };
}
