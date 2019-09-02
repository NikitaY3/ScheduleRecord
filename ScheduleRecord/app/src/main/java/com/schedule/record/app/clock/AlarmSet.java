package com.schedule.record.app.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class AlarmSet {

    private Context context;
    private int myhour, myminute;
    private String dayid;
    private int i;

    public AlarmSet(Context context, int myhour, int myminute, String dayid, int i) {
        this.context = context;
        this.myhour = myhour;
        this.myminute = myminute;
        this.dayid = dayid;
        this.i = i;
    }

    public AlarmSet(Context context, String dayid, int i) {
        this.context = context;
        this.dayid = dayid;
        this.i = i;
    }

    public void myAlarmSet() {
        Calendar calendar = Calendar.getInstance();         //获取日期对象
        calendar.setTimeInMillis(System.currentTimeMillis());           //设置Calendar对象
        calendar.set(Calendar.HOUR_OF_DAY, myhour);          //设置闹钟小时数
        calendar.set(Calendar.MINUTE, myminute);          //设置闹钟的分钟数
        calendar.set(Calendar.SECOND, 0);           //设置闹钟的秒数
        calendar.set(Calendar.MILLISECOND, 0);          //设置闹钟的毫秒数

//        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//        intent.putExtra("music", dayid);
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra("music", dayid);

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        PendingIntent pendingIntent2 = PendingIntent.getService(context,i,intentService,0);
        PendingIntent pendingIntent2 = PendingIntent.getService(context,i,intentService,PendingIntent.FLAG_CANCEL_CURRENT);

        //获取系统进程
        AlarmManager am1= (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作
        Objects.requireNonNull(am1).set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
    }

    public void myAlarmCancel() {

        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra("music", dayid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am;

        //获取系统进程
        am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Objects.requireNonNull(am).cancel(pendingIntent);
    }
}
