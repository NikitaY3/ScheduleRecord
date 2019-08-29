package com.schedule.record.app.clock;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class AlarmService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private MediaPlayer player;

    class LocalBinder extends Binder{
        AlarmService getService(){
            return AlarmService.this;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String dayid = intent.getStringExtra("music");

        if (dayid.equals("stop")){
            stopMusic();
        }else{
            playMusic();
            showDialog2(dayid);
        }

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(AlarmService.this, "本地绑定", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(AlarmService.this, "取消绑定", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

        //TODO
        // 需要删除
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//        String dayid = intent.getStringExtra("music");
//
//        if (dayid.equals("stop")){
//            stopMusic();
//        }else{
//            playMusic();
//            showDialog2(dayid);
//        }
//    }

    private void showDialog2(final String dayid) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ATestA.class);
                intent.putExtra("dayid",dayid);
                startActivity(intent);
            }
        }, 3 * 1000);
    }

    public void playMusic() {
        if(player == null) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            try {
                player = new MediaPlayer();
                player.setDataSource(this, uri);
                final AudioManager audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
                if (Objects.requireNonNull(audioManager).getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
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

}