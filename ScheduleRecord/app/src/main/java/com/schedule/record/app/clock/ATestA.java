package com.schedule.record.app.clock;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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

import java.io.IOException;

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

    private MediaPlayer player;

    private TodaySQLite helper;
    private String DBName="today";
    private int version=1;
    private TodaySQLiteUser user;

    private String dayid;
    private int i;
    private TodaySQLiteUserDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode1_alarm);
        ButterKnife.bind(this);

        dayid = getIntent().getStringExtra("dayid");
        i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));

        helper = new TodaySQLite(this, DBName, null, version);
        dao = new TodaySQLiteUserDao(helper);
        user = dao.queryBydayid(dayid);

        mAlaTextView2.setText(user.getTitle());

        playMusic();
    }

    @OnClick({R.id.mAlaButton1, R.id.mAlaButton2})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.mAlaButton1:
                stopMusic();
                user.setCheckbox(true);
                dao.updateAll(user,ATestA.this);
                finish();
                break;
            case R.id.mAlaButton2:
                stopMusic();
                //取得延时设置
                SharedPreferences sharedPreferences;
                sharedPreferences = this.getSharedPreferences("delaytime",MODE_PRIVATE);
                String min = sharedPreferences.getString("time","");

                gettime(user.getTime(),min);

                dao.updateAll(user,ATestA.this);

                finish();
                break;
        }
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

//        //取消闹钟
//        new AlarmSet(this,dayid,i).myAlarmCancel();

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

//        //设置闹钟
//        new AlarmSet(this,minH,minM,dayid,i).myAlarmSet();

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
