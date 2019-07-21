package com.schedule.record.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.schedule.record.app.clock.AlarmService;
import com.schedule.record.app.fragment.Calendar1Fragment;
import com.schedule.record.app.fragment.CalendarFragment;
import com.schedule.record.app.function.AlarmDTT;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.function.FragmentController;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final Calendar1Fragment Calendar1Fragment = new Calendar1Fragment();
    private static final CalendarFragment CalendarFragment = new CalendarFragment();
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioButton3)
    RadioButton radioButton3;
    @BindView(R.id.mainRadioGroup)
    RadioGroup mainRadioGroup;

    private FragmentController controller;


    private DaySQLite helper;
    private String DBName="day_1";
    private int version=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        controller = FragmentController.getInstance(this, R.id.mainFrameLayout);
        controller.showFragment(0);


//        helper = new DaySQLite(getActivity(), DBName, null, version);
//        helper.getReadableDatabase();
//        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
//        dataList = (List<DaySQLiteUser>) dao.quiryTodayAndSetItem();


        helper=new DaySQLite(MainActivity.this,DBName,null,version);
        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        helper.getReadableDatabase();
        List<AlarmDTT> list=new ArrayList<>();
        List<DaySQLiteUser> list2 = new ArrayList<DaySQLiteUser>();
        list = dao.quiryTodayTime();
//        list2 = dao.quiryTodayAndSetItem();

        int myhour = 0,myminute = 0;
        int i;
        if (!list.isEmpty()) {
            for (i = 0; i < list.size(); i++) {
                String tim = list.get(i).getTime();
                myhour = Integer.parseInt(tim.substring(0, 2));
                myminute = Integer.parseInt(tim.substring(3, 5));
                Toast.makeText(MainActivity.this, tim.substring(0, 5), Toast.LENGTH_LONG).show();
                myAlarmSet(myhour, myminute,list.get(i).getDayid(),i);
            }
        }

        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        controller.showFragment(0);
                        break;
                    case R.id.radioButton2:
                        controller.showFragment(1);
                        break;
                    case R.id.radioButton3:
                        controller.showFragment(2);
                        break;
                }
            }
        });
    }

    private void myAlarmSet(int myhour, int myminute,String dayid,int i) {
        Calendar calendar = Calendar.getInstance();         //获取日期对象
        calendar.setTimeInMillis(System.currentTimeMillis());           //设置Calendar对象
        calendar.set(Calendar.HOUR_OF_DAY, myhour);          //设置闹钟小时数
        calendar.set(Calendar.MINUTE, myminute);          //设置闹钟的分钟数
        calendar.set(Calendar.SECOND, 0);           //设置闹钟的秒数
        calendar.set(Calendar.MILLISECOND, 0);          //设置闹钟的毫秒数

//        Intent intent = new Intent(MainActivity.this, AlermReceiver.class);
//        intent.putExtra("music", dayid);
        Intent intentService = new Intent(MainActivity.this, AlarmService.class);
        intentService.putExtra("music", dayid);

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getService(MainActivity.this,i,intentService,0);

        //获取系统进程
        AlarmManager am1= (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作

        if (calendar.getTimeInMillis()< System.currentTimeMillis()) {
            am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
        }

//        am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
    }

}
