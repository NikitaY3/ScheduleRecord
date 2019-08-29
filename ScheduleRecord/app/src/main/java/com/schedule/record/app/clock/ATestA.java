package com.schedule.record.app.clock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ATestA extends AppCompatActivity {

    @BindView(R.id.mAlaTextView2)
    TextView mAlaTextView2;
    @BindView(R.id.mAlaButton1)
    Button mAlaButton1;
    @BindView(R.id.mAlaButton2)
    Button mAlaButton2;

    private TodaySQLiteUser user;
    private TodaySQLiteUserDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode1_alarm);
        ButterKnife.bind(this);

        String dayid = getIntent().getStringExtra("dayid");

        String DBName = "today";
        int version = 1;
        TodaySQLite helper = new TodaySQLite(this, DBName, null, version);
        dao = new TodaySQLiteUserDao(helper);
        user = dao.queryBydayid(dayid);

        mAlaTextView2.setText(user.getTitle());

    }

    @OnClick({R.id.mAlaButton1, R.id.mAlaButton2})
    public void onViewClicked(View view) {

        final Intent serviceIntent = new Intent(this,AlarmService.class);
        serviceIntent.putExtra("music", "stop");

        switch (view.getId()) {
            case R.id.mAlaButton1:
                startService(serviceIntent);
                user.setCheckbox(true);
                dao.updateAll(user,ATestA.this);
                finish();
                break;
            case R.id.mAlaButton2:
                startService(serviceIntent);
                //取得延时设置
                SharedPreferences sharedPreferences;
                sharedPreferences = this.getSharedPreferences("delaytime",MODE_PRIVATE);
                String min = sharedPreferences.getString("time","");
                //计算延时后的时间，获取并设置延时日程
                gettime(user.getTime(),min);
                //更新数据库
                dao.updateAll(user,ATestA.this);
                finish();
                break;
        }
    }

    //获取并设置延时日程
    public void gettime(String beforeTime,String min) {
        String time;
        int minH = Integer.parseInt(beforeTime.substring(0,2));
        int minM = Integer.parseInt(beforeTime.substring(3,5));

        switch (min) {
            case "30分钟":
                minM = minM + 30;
                break;
            case "1小时":
                minH = minH + 1;
                break;
            case "2小时":
                minH = minH + 2;
                break;
            case "3小时":
                minH = minH + 3;
                break;
            case "4小时":
                minH = minH + 4;
                break;
        }

        if (minM >= 60) {
            minH = minH + 1;
            minM = minM - 60;
        }
        if (minH >= 24) {
            minH = minH - 24;
            String thisday = user.getThisday();
            int thisday1 = Integer.parseInt(thisday.substring(8, 10));
            int thisday2 = thisday1 + 1;
            String thisday3 = thisday.substring(0, 8) + thisday2;
            user.setThisday(thisday3);
        }

        //计算闹钟时间
        if (minM < 10 && minH < 10) {
            time = "0" + minH + ":0" + minM;
        } else if (minM < 10) {
            time = minH + ":0" + minM;
        } else if (minH < 10) {
            time = "0" + minH + ":" + minM;
        } else {
            time = minH + ":" + minM;
        }

        user.setTime(time);
    }
}
