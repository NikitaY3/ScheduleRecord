package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class MainMyL2General extends AppCompatActivity {


    @BindView(R.id.generalEditText)
    EditText generalEditText;
    @BindView(R.id.generalButton1)
    Button generalButton1;
    @BindView(R.id.generalButton2)
    Button generalButton2;
    @BindView(R.id.generalTextView2)
    TextView generalTextView2;

    private AuthoritySQLiteUser user;
    private AuthoritySQLite helper;
    private String DBName = "authority";
    private int version = 1;

    private TodaySQLite helper1;
    private String DBName1="today";

    private AuthoritySQLiteUserDao dao;
    private String nameid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l2general);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid","");
        user = new AuthoritySQLiteUser(null, nameid);
//        user.setSnameid(nameid);

        //特殊用户是我，普通用户是编辑框里面的
        helper = new AuthoritySQLite(MainMyL2General.this, DBName, null, version);
        dao = new AuthoritySQLiteUserDao(helper);
        generalTextView2.setText(dao.queryGeneral(nameid));
    }

    @OnClick({R.id.generalButton1, R.id.generalButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.generalButton1:

                String gnameid = generalEditText.getText().toString();
                if (gnameid.length() == 11){
                    Boolean a = dao.queryByGSNameid(gnameid, nameid);
                    if (a){
                        //执行对对方的日程指派,跳转到对应于该用户的日程指派页面
                        Intent intent = new Intent(MainMyL2General.this, MainMyL2GInsert.class);
                        startActivity(intent);

//                    //数据写入数据库
//                    String dayidbutton = getInternetTime();
//                    String dayid = nameid + dayidbutton;
//                    TodaySQLiteUser things = new TodaySQLiteUser(dayid,false,remind,time,dayTitle,important,diary,Dayidbutton.substring(0,10));
//                    helper=new TodaySQLite(context,DBName,null,version);
//                    helper.getReadableDatabase();
//                    TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
//                    dao.insert(things,context);

                    }else {
                        //告知不能指派对方
                        Toast.makeText(this,"您未获得对该用户的指派权限",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"您输入的ID不正确",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.generalButton2:
                //刷新数据
                generalTextView2.setText(dao.queryGeneral(nameid));
                break;
        }
    }


    //联网获取当前时间
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String time = timesimple.format(new Date());
        return time;
    }

}
