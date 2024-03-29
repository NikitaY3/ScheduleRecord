package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.clock.AlarmSet;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.function.GetFunctions.TodayDeleteTask;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class TodaySQLiteUserDao {

    private TodaySQLite helper;
    private static final String TABLE = "today";

    private Context context;
    private String today,nameid;
    private int day;
    private Boolean can = true,can2 = true;
    private Cursor cursortt;
    private Handler handler;


    public TodaySQLiteUserDao(TodaySQLite helper) {
        this.helper = helper;
    }

    public void insert(TodaySQLiteUser user,Context context){
        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("myuser", MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid", "");

        SQLiteDatabase db = helper.getWritableDatabase();
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

        if (queryBydayid(dayid) == null) {
            db = helper.getWritableDatabase();
            db.insert(TABLE, null, content);
        }

        //设置闹钟,当前时间小于闹钟时间
        if (!time.equals("XX:XX") && remind && dayid.substring(0, 11).equals(nameid)) {
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

    public void insert(TodaySQLiteUser user){
        SQLiteDatabase db = helper.getWritableDatabase();
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

        if (queryBydayid(dayid) == null) {
            db = helper.getWritableDatabase();
            db.insert(TABLE, null, content);
        }

        db.close();
    }

    public  TodaySQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "day_id =?", new String[]{Dayid}, null, null, null);
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

    //查询所有当前账号的日程
    public List<TodaySQLiteUser> quiryAndSetItem(String nameid) {
        List<TodaySQLiteUser> dataList = new ArrayList<>();
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"day_id like ?", new String[]{nameid + "%"},null,null,"time,important");
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

    //查询插入给特定用户的日程
    public List<TodaySQLiteUser> quiryAndSetInsertItem(String myNameId) {
        List<TodaySQLiteUser> dataList = new ArrayList<>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null,"day_id like ?", new String[]{myNameId + "%"},null,null,"day_id");
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

    //查询所有插入给别人的日程
    public List<TodaySQLiteUser> quiryAndSetAllInsertItem(String NameId) {
        List<TodaySQLiteUser> dataList = new ArrayList<>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null,"day_id not like ?", new String[]{NameId + "%"},null,null,"day_id");
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
    public List<CalenderWeekItem> quiryAndSetWeekItem(String nameid) {
        List<CalenderWeekItem> dataList = new ArrayList<>();
        helper.getReadableDatabase();
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"day_id like ?", new String[]{nameid + "%"},null,null,"time,important");
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
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues content = new ContentValues();
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

        db.update(TABLE,content,"day_id =?",new String[]{user.getDayid()});

        //当日程有提示时间、需要提醒、没有完成时，设置闹钟
        if (!time.equals("XX:XX") && remind && !checkbox) {
            String nowTime = getInternetTime();
            int t = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
            int t1 = Integer.parseInt(nowTime.substring(11, 13) + nowTime.substring(14, 16));
            //设置闹钟,当前时间小于闹钟时间
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

    public int CountBar(String nameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from today where checkbox =1 and day_id like ?" ,new String[]{nameid + "%"});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    public int CountAllBar(String nameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from today where day_id like ?",new String[]{nameid + "%"});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    //将Today插入Finish和Pass的函数
    public void TodayToFinishPass(Context context, String today, int day, String nameid, Handler handler){

        this.context = context;
        this.today = today;
        this.day = day;
        this.nameid = nameid;
        this.handler = handler;

        FinishSQLite helper1;
        String DBName1="finish";
        int version1 = 1;

        SQLiteDatabase db = helper.getWritableDatabase();

        if (can2) {

            cursortt = db.query(TABLE, null, "day_id like ?", new String[]{nameid + "%"}, null, null, null);
        }

        while (can && cursortt.moveToNext()) {
            String dayid = cursortt.getString(0);
            int checkbox1 = cursortt.getInt(1);
            int remind1 = cursortt.getInt(2);
            String time = cursortt.getString(3);
            String title = cursortt.getString(4);
            String important = cursortt.getString(5);
            String diary = cursortt.getString(6);
            String thisday = cursortt.getString(7);

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

                can = false;
                dao.insert(things);

                //数据上传到云端
                String res = new PostFunctions().SaveFinishPost(things,uiHandler);

                PassSQLite helper2;
                String DBName2 = "pass";

                FutureSQLite helper3;
                String DBName3 = "future";

                helper3 = new FutureSQLite(context, DBName3, null, version1);
                FutureSQLiteUserDao daof = new FutureSQLiteUserDao(helper3);
                if (daof.queryBydayid(dayid) != null) {
                    String endday = daof.queryBydayid(dayid).getEndDay();

                    int end = 0;
                    if (!endday.equals("null")){
                        end = Integer.parseInt((endday.substring(0,4)+endday.substring(5,7)+endday.substring(8,10)));
                    }

                    if (end < day && end != 0) {
                        //数据写入数据库
                        PassSQLiteUser things1 = new PassSQLiteUser(dayid, title, today, 1, important);
                        helper2 = new PassSQLite(context, DBName2, null, version1);
                        PassSQLiteUserDao dao1 = new PassSQLiteUserDao(helper2);
                        can = false;
                        dao1.insert(things1);

                        //数据上传到云端
                        new PostFunctions().SavePassPost(things1,uiHandler);
                    }
                } else {
                    //数据写入数据库
                    PassSQLiteUser things1 = new PassSQLiteUser(dayid, title, today, 1, important);
                    helper2 = new PassSQLite(context, DBName2, null, version1);
                    PassSQLiteUserDao dao1 = new PassSQLiteUserDao(helper2);
                    can = false;
                    dao1.insert(things1);

                    //数据上传到云端
                    new PostFunctions().SavePassPost(things1,uiHandler);
                }
                //从数据库删除
                can = false;
                deleteByDayid(dayid,context);

                //删除云端
                new TodayDeleteTask(uiHandler).execute("http://120.77.222.242:10024/today/deletebyid?dayId=" + dayid);
            }

            //如果是最后一条，就发送message
            if (cursortt.isLast()){
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }
        db.close();
    }

    //联网获取当前时间
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 21:
                    can = true;
                    can2 = false;
                    TodayToFinishPass(context,today,day,nameid,handler);
                    //TODO
                    //刷新fragment
                    break;
                case 44:
                    //删除Future成功，不做操作
                    can = true;
                    can2 = false;
                    break;
            }
        }
    };

}
