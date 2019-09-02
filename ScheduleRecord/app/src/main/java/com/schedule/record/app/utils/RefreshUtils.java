package com.schedule.record.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.schedule.record.app.function.CalculationWeek;
import com.schedule.record.app.function.GetFunctions.AuthorityQueryTask;
import com.schedule.record.app.function.GetFunctions.FinishFindAllTask;
import com.schedule.record.app.function.GetFunctions.FutureFindAllTask;
import com.schedule.record.app.function.GetFunctions.PassFindAllTask;
import com.schedule.record.app.function.GetFunctions.TodayFindAllTask;
import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class RefreshUtils {

    private Context context;
    private String nameid;
    private AuthoritySQLiteUserDao dao;

    private String today;
    private TodaySQLite helper2;
    private String DBName2 = "today";
    private FutureSQLite helper3;
    private String DBName3 = "future";
    private AuthoritySQLite helper0;
    private String DBName0 = "authority";
    private int version = 1;

    public RefreshUtils(Context context) {
        this.context = context;

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("myuser", MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid", "");

        Toast.makeText(context,"正在刷新，请勿操作",Toast.LENGTH_SHORT).show();

        //开始获取云端数据，不会删除本地数据
        RefreshUtilsGet();
    }

    //刷新显示页面
    private void RefreshUtilsMain(){

        today = getInternetTime();
        int todayint = Integer.parseInt((today.substring(0,4)+today.substring(5,7)+today.substring(8,10)));
        String week = new CalculationWeek(today).getWeek();

        //1.判断将Today日程插入到Finish日程,判断Today日程是否Pass
        String todayb = getDay(-1);
        int todaybint = Integer.parseInt((todayb.substring(0,4)+todayb.substring(5,7)+todayb.substring(8,10)));
        helper2 = new TodaySQLite(context, DBName2, null, version);
        TodaySQLiteUserDao dao2 = new TodaySQLiteUserDao(helper2);
        dao2.TodayToFinishPass(context,todayb,todaybint,nameid,uiHandler);

        //2.判断Future日程是否应该插入到Today
        helper3 = new FutureSQLite(context, DBName3, null, version);
        FutureSQLiteUserDao dao3 = new FutureSQLiteUserDao(helper3);
        dao3.FutureToToday(context,todayint, Integer.parseInt(today.substring(9,10)),week,today,uiHandler);
    }


    //获取联网数据
    private void RefreshUtilsGet() {

        // 1、整理当前账号表（先查询云端有没有）
        String DBName = "authority";
        int version = 1;
        AuthoritySQLite helper = new AuthoritySQLite(context, DBName, null, version);
        dao = new AuthoritySQLiteUserDao(helper);
        dao.deleteAll();

        helper0 = new AuthoritySQLite(context, DBName0, null, version);
        AuthoritySQLiteUserDao dao = new AuthoritySQLiteUserDao(helper0);
        dao.deleteAll();

        //2、获取用户的各个表的数据
        AuthorityQueryTask authorityQueryTask = new AuthorityQueryTask(context, uiHandler);
        authorityQueryTask.execute("http://120.77.222.242:10024/authority/query?gnameId=" + nameid);

        AuthorityQueryTask authorityQueryTask1 = new AuthorityQueryTask(context, uiHandler);
        authorityQueryTask1.execute("http://120.77.222.242:10024/authority/query?snameId=" + nameid);

        FutureFindAllTask futureFindAllTask = new FutureFindAllTask(context, uiHandler);
        futureFindAllTask.execute("http://120.77.222.242:10024/future/findall?dayId=" + nameid);

        TodayFindAllTask todayFindAllTask = new TodayFindAllTask(context, uiHandler);
        todayFindAllTask.execute("http://120.77.222.242:10024/today/findall?dayId=" + nameid);

        FinishFindAllTask finishFindAllTask = new FinishFindAllTask(context, uiHandler);
        finishFindAllTask.execute("http://120.77.222.242:10024/finish/findall?dayId=" + nameid);

        PassFindAllTask passFindAllTask = new PassFindAllTask(context, uiHandler);
        passFindAllTask.execute("http://120.77.222.242:10024/pass/findall?dayId=" + nameid);
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        int a = 0;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    a++;
                    break;
                case 2:
                    a++;
                    break;
                case 3:
                    a++;
                    break;
                case 4:
                    a++;
                    break;
                case 5:
                    a++;
                    break;
                case 6:
                    a++;
                    break;
            }
            if (a == 6){
                //当联网数据获取完就刷新本地数据
                RefreshUtilsMain();
            }
            if (a == 8){

                //TODO
                //刷新完毕，取消弹框

                Toast.makeText(context,"刷新完成",Toast.LENGTH_SHORT).show();


            }
        }
    };


    //联网获取当前时间yyyy-MM-dd
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    //获取当前天的前后日期
    @SuppressLint("SimpleDateFormat")
    private String getDay(int i) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 + i);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }
}
