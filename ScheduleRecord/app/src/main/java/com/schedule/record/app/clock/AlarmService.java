package com.schedule.record.app.clock;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.AlarmDTT;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AlarmService extends Service {

    private final IBinder mBinder = new LocalBinder();

    AlertDialog.Builder frame1;
    private DaySQLite helper;
    private String DBName="day_1";
    private int version=1;
    private String Dayid,Dayidbutton;

    private Thread thread;

    public class LocalBinder extends Binder{
        AlarmService getService(){
            return AlarmService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(AlarmService.this, "本地绑定", Toast.LENGTH_SHORT).show();
        thread.start();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(AlarmService.this, "取消绑定", Toast.LENGTH_SHORT).show();
        return false;
    }


    private MediaPlayer player;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        playMusic();

        String dayid = intent.getStringExtra("music");

        DaySQLiteUser a = timeFromSet(dayid);
        dayConfirmationDialogs(a.getTitle(),a);
    }

    public void playMusic() {
        if(player == null) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            try {
                player = new MediaPlayer();
                player.setDataSource(this, uri);
                final AudioManager audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    player.setAudioStreamType(AudioManager.STREAM_ALARM);
                    player.setLooping(true);
                    player.prepare();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!player.isPlaying()) {
            player.start();
        }
    }
    public void stopMusic() {
        if (player != null) {
            player.stop();
            try {
                // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
                player.prepare();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private DaySQLiteUser timeFromSet(String dayid){

        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        helper=new DaySQLite(getBaseContext(),DBName,null,version);
        helper.getReadableDatabase();
        return dao.queryBydayid(dayid);
    }

    private void dayConfirmationDialogs(String title, final DaySQLiteUser aa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(title);
        builder.setNegativeButton("日程延后", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopMusic();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                helper=new DaySQLite(getBaseContext(),DBName,null,version);
                helper.getReadableDatabase();
                Calendar c =Calendar.getInstance();
                c.add(Calendar.HOUR,1);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String c1 = timesimple.format(c);
                aa.setTime(c1);
                dao.updateAll(aa);
            }
        });
        builder.setPositiveButton("标记完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopMusic();

                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                helper=new DaySQLite(getBaseContext(),DBName,null,version);
                helper.getReadableDatabase();
                aa.setCheckbox(true);
                dao.updateAll(aa);
            }
        });
        final AlertDialog dialog = builder.create();
        //在dialog  show方法之前添加如下代码，表示该dialog是一个系统的dialog**
        dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));

        dialog.show();

//        new Thread(){
//            public void run() {
//                SystemClock.sleep(4000);
//                hanlder.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.show();
//                    }
//                });
//            };
//        }.start();

    }

}