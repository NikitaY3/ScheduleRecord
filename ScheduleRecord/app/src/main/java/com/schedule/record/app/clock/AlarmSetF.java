package com.schedule.record.app.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.schedule.record.app.fragment.Calendar1Fragment;
import com.schedule.record.app.fragment.CalendarFragment;
import com.schedule.record.app.function.AlarmDTT;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmSetF {

    private static final com.schedule.record.app.fragment.Calendar1Fragment Calendar1Fragment = new Calendar1Fragment();
    private static final com.schedule.record.app.fragment.CalendarFragment CalendarFragment = new CalendarFragment();
    Context context;

    private TodaySQLite helper;
    private String DBName="today";
    private int version=1;

    public AlarmSetF(Context context) {
        this.context=context;

//        helper = new TodaySQLite(context, DBName, null, version);
//        helper.getReadableDatabase();
//        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper);
//        dataList = (List<TodaySQLiteUser>) dao.quiryAndSetItem();


        helper=new TodaySQLite(context,DBName,null,version);
        TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
        helper.getReadableDatabase();
        List<AlarmDTT> list=new ArrayList<>();
        List<TodaySQLiteUser> list2 = new ArrayList<TodaySQLiteUser>();
        list = dao.quiryTodayTime();

        list2 = dao.quiryAndSetItem();

        int myhour = 0,myminute = 0;
        int i;
        if (!list.isEmpty()) {
            for (i = 0; i < list.size(); i++) {
                String tim = list.get(i).getTime();
                myhour = Integer.parseInt(tim.substring(0, 2));
                myminute = Integer.parseInt(tim.substring(3, 5));
                Toast.makeText(context, tim.substring(0, 5), Toast.LENGTH_LONG).show();
                myAlarmSet(myhour, myminute,list.get(i).getDayid(),i);
            }
        }
    }

    private void myAlarmSet(int myhour, int myminute, String dayid, int i) {
        Calendar calendar = Calendar.getInstance();         //获取日期对象
        calendar.setTimeInMillis(System.currentTimeMillis());           //设置Calendar对象
        calendar.set(Calendar.HOUR_OF_DAY, myhour);          //设置闹钟小时数
        calendar.set(Calendar.MINUTE, myminute);          //设置闹钟的分钟数
        calendar.set(Calendar.SECOND, 0);           //设置闹钟的秒数
        calendar.set(Calendar.MILLISECOND, 0);          //设置闹钟的毫秒数

//        Intent intent = new Intent(MainActivity.this, AlermReceiver.class);
//        intent.putExtra("music", dayid);
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra("music", dayid);

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getService(context,i,intentService,0);

        //获取系统进程
        AlarmManager am1= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作

        if (calendar.getTimeInMillis()< System.currentTimeMillis()) {
            am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
        }

//        am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
    }
}
