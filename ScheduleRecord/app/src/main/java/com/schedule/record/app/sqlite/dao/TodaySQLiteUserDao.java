package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.schedule.record.app.clock.AlarmSet;
import com.schedule.record.app.function.AlarmDTT;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.utils.HttpPostUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TodaySQLiteUserDao {

    private TodaySQLite helper;
    private static final String TABLE = "today";

    public TodaySQLiteUserDao(TodaySQLite helper) {
        this.helper = helper;
    }

    public void insert(TodaySQLiteUser user,Context context){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();

        String time = user.getTime();
        boolean remind = user.isRemind();
        String dayid = user.getDayid();

        content.put("day_id",dayid);
        content.put("checkbox",user.isCheckbox());
        content.put("remind",remind);
        content.put("time",time);
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("this_day",user.getThisday());

        db.insert(TABLE,null,content);

        //设置闹钟,当前时间小于闹钟时间
        if (!time.equals("XX:XX") && remind) {
            String nowTime = getInternetTime();
            int t = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
            int t1 = Integer.parseInt(nowTime.substring(11, 13) + nowTime.substring(14, 16));
            if (t1 < t ) {
                int i = Integer.parseInt(dayid.substring(22, 24) + dayid.substring(25, 27) + dayid.substring(28, 30));
                new AlarmSet(context, Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)), dayid, i).myAlarmSet();
            }
        }

        db.close();
    }

    public  TodaySQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "day_id=?", new String[]{Dayid}, null, null, null);
        TodaySQLiteUser user = null;
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String diary = cursor.getString(6);
            String thisday = cursor.getString(7);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new TodaySQLiteUser(dayid,checkbox,remind,time,title,important,diary,thisday);
        }
        db.close();
        return user;
    }

    public List<TodaySQLiteUser> quiryAndSetItem() {
        List<TodaySQLiteUser> dataList = new ArrayList<TodaySQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"time,important");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String diary = cursor.getString(6);
            String thisday = cursor.getString(7);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            TodaySQLiteUser things = new TodaySQLiteUser(dayid,checkbox,remind,time,title,important,diary,thisday);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    //查询Week
    public List<CalenderWeekItem> quiryAndSetWeekItem() {
        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            CalenderWeekItem things = new CalenderWeekItem(dayid,title,important,checkbox);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();
    }

    public void deleteByDayid(String dayid, Context context){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"day_id=?",new String[]{dayid});

        //删除dayid对应的闹钟
        int i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));
        new AlarmSet(context,dayid,i).myAlarmCancel();

        db.close();
    }

    public void updateAll(TodaySQLiteUser user,Context context){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        String time = user.getTime();
        boolean remind = user.isRemind();
        String dayid = user.getDayid();
        boolean checkbox = user.isCheckbox();

        content.put("day_id",dayid);
        content.put("checkbox",user.isCheckbox());
        content.put("remind",remind);
        content.put("time",time);
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("this_day",user.getThisday());

        db.update(TABLE,content,"day_id=?",new String[]{user.getDayid()});

        //设置闹钟,当前时间小于闹钟时间
        if (!time.equals("XX:XX") && remind && !checkbox) {
            String nowTime = getInternetTime();
            int t = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
            int t1 = Integer.parseInt(nowTime.substring(11, 13) + nowTime.substring(14, 16));
            if (t1 < t ) {
                int i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));
                new AlarmSet(context, Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)), dayid, i).myAlarmSet();
            }else {
                //删除dayid对应的闹钟
                int i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));
                new AlarmSet(context,dayid,i).myAlarmCancel();
            }
        }else {
            //删除dayid对应的闹钟
            int i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));
            new AlarmSet(context,dayid,i).myAlarmCancel();
        }

        db.close();
    }

    public int CountBar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from today where checkbox =1 " ,null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    public int CountAllBar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from today",null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    //将Today插入Finish和Pass的函数
    public void TodayToFinishPass(Context context, String today, int day){

        FinishSQLite helper1;
        String DBName1="finish";
        int version1=1;

        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String diary = cursor.getString(6);
            String thisday = cursor.getString(7);

            String finishid = dayid.substring(0,11)+today+dayid.substring(21);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;

            int thisday1 = Integer.parseInt(thisday.substring(0,4)+thisday.substring(5,7)+thisday.substring(8,10));

            //如果日程的插入日期小于或等于昨天，finish_id就是昨天
            if (thisday1 <= day) {

                //数据写入数据库——Finish
                FinishSQLiteUser things = new FinishSQLiteUser(finishid, dayid, checkbox, remind, time, title, important, diary);
                helper1 = new FinishSQLite(context, DBName1, null, version1);
                FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper1);
                dao.insert(things);

                PassSQLite helper2;
                String DBName2="pass";

                FutureSQLite helper3;
                String DBName3="future";

                helper3 = new FutureSQLite(context, DBName3, null, version1);
                FutureSQLiteUserDao daof = new FutureSQLiteUserDao(helper3);
                if (daof.queryBydayid(dayid) != null) {
                    String endday = daof.queryBydayid(dayid).getEndDay();
                    int end = Integer.parseInt((endday.substring(0, 4) + endday.substring(5, 7) + endday.substring(8, 10)));
                    if (end < day && end != 0) {
                        //数据写入数据库
                        PassSQLiteUser things1 = new PassSQLiteUser(dayid, title, today, 1, important);
                        helper2 = new PassSQLite(context, DBName2, null, version1);
                        PassSQLiteUserDao dao1 = new PassSQLiteUserDao(helper2);
                        dao1.insert(things1);
                    }
                } else {
                    //数据写入数据库
                    PassSQLiteUser things1 = new PassSQLiteUser(dayid, title, today, 1, important);
                    helper2 = new PassSQLite(context, DBName2, null, version1);
                    PassSQLiteUserDao dao1 = new PassSQLiteUserDao(helper2);
                    dao1.insert(things1);
                }
                //从数据库删除
                deleteByDayid(dayid,context);
            }
        }
        db.close();
    }

    //联网获取当前时间
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String Dayid = timesimple.format(new Date());
        return Dayid;
    }

}
