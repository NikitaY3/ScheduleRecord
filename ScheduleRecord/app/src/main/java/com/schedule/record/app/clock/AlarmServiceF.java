package com.schedule.record.app.clock;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.Toast;

import com.schedule.record.app.R;

import java.io.IOException;

//import android.app.Service;
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.view.WindowManager;

public class AlarmServiceF extends Service {

    private final IBinder mBinder = new LocalBinder();

//    private DaySQLite helper;
//    private String DBName="day_1";
//    private int version=1;
    private String Dayid,Dayidbutton;

    private Thread thread;

    public class LocalBinder extends Binder{
        AlarmServiceF getService(){
            return AlarmServiceF.this;
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
        Toast.makeText(AlarmServiceF.this, "本地绑定", Toast.LENGTH_SHORT).show();
        thread.start();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(AlarmServiceF.this, "取消绑定", Toast.LENGTH_SHORT).show();
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


//        playMusic();

        String dayid = intent.getStringExtra("music");
        if (dayid!=null){
            playMusic();
        }

//        DaySQLiteUser a = timeFromSet(dayid);

//        dayConfirmationDialogs(a.getTitle(),a);
//        showCXBRunning();
//        showDialog();
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

//    private DaySQLiteUser timeFromSet(String dayid){
//
//        helper=new DaySQLite(getBaseContext(),DBName,null,version);
//        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
//        helper.getReadableDatabase();
//        return dao.queryBydayid(dayid);
//    }

//    private void dayConfirmationDialogs(String title, final DaySQLiteUser aa) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, Theme_AppCompat_Light_Dialog);
//        builder.setTitle("提示");
//        builder.setMessage(title);
//        builder.setNegativeButton("日程延后", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                stopMusic();
//                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
//                helper=new DaySQLite(getBaseContext(),DBName,null,version);
//                helper.getReadableDatabase();
//                Calendar c =Calendar.getInstance();
//                c.add(Calendar.HOUR,1);
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String c1 = timesimple.format(c);
//                aa.setTime(c1);
//                dao.updateAll(aa);
//            }
//        });
//        builder.setPositiveButton("标记完成", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                stopMusic();
//
//                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
//                helper=new DaySQLite(getBaseContext(),DBName,null,version);
//                helper.getReadableDatabase();
//                aa.setCheckbox(true);
//                dao.updateAll(aa);
//            }
//        });
//        final AlertDialog dialog = builder.create();
//        //在dialog  show方法之前添加如下代码，表示该dialog是一个系统的dialog**
//        Objects.requireNonNull(dialog.getWindow()).setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
//
////        dialog.show();
//
////        Looper.prepare();
//        new Thread(){
//            public void run() {
//                SystemClock.sleep(4000);
////                dialog.show();
//                Looper.prepare();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.show();
//                    }
//                },100);
//        Looper.loop();
//            };
//        }.start();
//
//    }

    private void showDialog() {

//        ServiceDialog one = new ServiceDialog(getBaseContext());
//        one.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmServiceF.this,R.style.Theme_AppCompat_Light_Dialog );
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