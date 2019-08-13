package com.schedule.record.app.clock;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
//import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
//import android.support.v7.app.AlertDialog;
//import android.view.WindowManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.schedule.record.app.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.schedule.record.app.R.style.Theme_AppCompat_Light_Dialog;

public class AlarmService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private String Dayid,Dayidbutton;

    private Thread thread;

    public class LocalBinder extends Binder{
        AlarmService getService(){
            return AlarmService.this;
        }
    }


    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
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

        String dayid = intent.getStringExtra("music");
        if (dayid!=null){
            playMusic();
        }

        showDialog2();
    }
    //传入notifycation对象的作用是，当将服务设为前台服务后，会在状态栏显示一条通知
    public void showCXBRunning() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification builder = new Notification.Builder(this).setTicker("xxx").setSmallIcon(R.drawable.abaa_item_im_em).build();
        startForeground(1, builder);
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


    private void showDialog() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmService.this,R.style.Theme_AppCompat_Light_Dialog );
                builder.setTitle("title");
                builder.setMessage("这是一个由service弹出的对话框");
                builder.setCancelable(false);
                builder.setPositiveButton("button confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();
            }
        }, 3 * 1000);

    }
    private void showDialog2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ATestA.class);
                startActivity(intent);
            }
        }, 3 * 1000);
    }
}