package com.schedule.record.app.clock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.schedule.record.app.R;

import java.util.Calendar;
import java.util.Objects;

@SuppressLint("Registered")
public class MainActivity1 extends AppCompatActivity {

    private TextView mTextView;

    public AlarmService mathService;
    private boolean isBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test);

        Button startButton = findViewById(R.id.start);
        Button stopButton = findViewById(R.id.stop);


        final Intent serviceIntent = new Intent(this,AlarmService.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(serviceIntent);
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });

        Button unbindButton = findViewById(R.id.unbind);

        unbindButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isBound){
                    isBound = false;
                    unbindService(mConnection);
                    mathService = null;
                }
            }
        });

        old();
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mathService = ((AlarmService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mathService = null;
        }
    };


    private void old() {
        mTextView = this.findViewById(R.id.mText);
        Button msetButton = this.findViewById(R.id.setTimeButton);
        Button mcancelButton = findViewById(R.id.cancelButton);

        msetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar1.get(Calendar.HOUR_OF_DAY);
                int minute = calendar1.get(Calendar.MINUTE);

                new TimePickerDialog(MainActivity1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        Calendar calendar = Calendar.getInstance();         //获取日期对象
                        calendar.setTimeInMillis(System.currentTimeMillis());           //设置Calendar对象
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);          //设置闹钟小时数
                        calendar.set(Calendar.MINUTE, minute);          //设置闹钟的分钟数
                        calendar.set(Calendar.SECOND, 0);           //设置闹钟的秒数
                        calendar.set(Calendar.MILLISECOND, 0);          //设置闹钟的毫秒数

                        Intent intent = new Intent(MainActivity1.this, AlermReceiver.class);
                        intent.putExtra("music", "stop");
                        //Intent intentService = new Intent(MainActivity1.this, AlarmService.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity1.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        //PendingIntent pendingIntent2 = PendingIntent.getService(MainActivity1.this,0,intentService,0);

                        //获取系统进程
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

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

                Objects.requireNonNull(am).cancel(pendingIntent);
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