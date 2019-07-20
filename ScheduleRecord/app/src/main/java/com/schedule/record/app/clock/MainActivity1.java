package com.schedule.record.app.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.schedule.record.app.R;

import java.util.Calendar;

public class MainActivity1 extends AppCompatActivity {
    //Properties
    private Button msetButton;
    private Button mcancelButton;
    private AlermReceiver uIReceiver;
    private TextView mTextView;
    //就用了Java的日历
    private Calendar calendar;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test);
        calendar = Calendar.getInstance();
        mTextView = (TextView)this.findViewById(R.id.mText);
        msetButton = (Button)this.findViewById(R.id.setTimeButton);
        mcancelButton = (Button)findViewById(R.id.cancelButton);
        msetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(MainActivity1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        //set(f, value) changes field f to value.
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        Intent intent = new Intent(MainActivity1.this, AlermReceiver.class);
                        intent.putExtra("music", true);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity1.this, 0, intent, 0);
                        AlarmManager am;
                        //获取系统进程
                        am = (AlarmManager)getSystemService(ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        //设置周期！！
                        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(10*1000), (24*60*60*1000), pendingIntent);
                        String tmps = "设置闹钟时间为："+format(hourOfDay)+":"+format(minute);
                        mTextView.setText(tmps);
                    }
                },hour,minute,true).show();
            }
        });
        mcancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, AlermReceiver.class);
                intent.putExtra("music", true);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity1.this, 0, intent, 0);
                AlarmManager am;
                //获取系统进程
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                //cancel
                am.cancel(pendingIntent);
                mTextView.setText("取消了！");
            }
        });
    }
    private String format(int x) {
        String s = ""+x;
        if(s.length() == 1)
            s = "0"+s;
        return s;
    }
}