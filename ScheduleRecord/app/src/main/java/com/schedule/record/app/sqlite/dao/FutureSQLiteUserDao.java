package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.function.GetFunctions.FutureDeleteTask;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.ArrayList;
import java.util.List;

public class FutureSQLiteUserDao {

    private FutureSQLite helper;
    private static final String TABLE = "future";

    private Boolean can = true,can2 = true;
    private Context context;
    private int today, m;
    private String week,thisday;
    private Cursor cursorft;
    private Handler handler;

    public FutureSQLiteUserDao(FutureSQLite helper) {
        this.helper = helper;
    }

    public void insert(FutureSQLiteUser user){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("day_id",user.getDayId());
        content.put("repeat_type",user.getRepeatType());
        content.put("end_day",user.getEndDay());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        db.insert(TABLE,null,content);
        db.close();

    }

    public  FutureSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "day_id=?", new String[]{Dayid}, null, null, null);
        FutureSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            String repeat = cursor.getString(1);
            String endday = cursor.getString(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String diary = cursor.getString(7);
            boolean remind;
            remind = remind1 > 0;
            user = new FutureSQLiteUser(dayid,repeat,endday,remind,time,title,important,diary);
        }
        db.close();
        return user;
    }

    public List<FutureSQLiteUser> quiryAndSetItem() {
        List<FutureSQLiteUser> dataList = new ArrayList<>();
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null,null, null,null,null,"time,important");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String repeat = cursor.getString(1);
            String endday = cursor.getString(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String diary = cursor.getString(7);
            boolean remind;
            remind = remind1 > 0;
            FutureSQLiteUser things = new FutureSQLiteUser(dayid,repeat,endday,remind,time,title,important,diary);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    //查询Week,根据日期查询
    public List<CalenderWeekItem> quiryAndSetWeekItem(int today, int m, String week, String thisday) {
        List<CalenderWeekItem> dataList = new ArrayList<>();
        //查询数据库并初始化日程列表
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null,null, null,null,null,"time,important");
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            String repeat = cursor.getString(1);
            String endday = cursor.getString(2);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            CalenderWeekItem things = new CalenderWeekItem(dayid, title, important, false);

            int end = 0;
            if (!endday.equals("null")){
                end = Integer.parseInt((endday.substring(0, 4) + endday.substring(5, 7) + endday.substring(8, 10)));
            }
            if (end >= today || end == 0) {
                String repeat1 = repeat.substring(0, 8);
                if (repeat1.equals("everywee")) {
                    String re = repeat.substring(8);
                    while (!re.equals("")) {
                        String a = re.substring(0, 1);
                        if (a.equals(week)) {
                            dataList.add(things);
                        }
                        re = re.substring(1);
                    }
                } else if (repeat1.equals("everyday") || (repeat1.equals("everymou") && m == 1)) {
                    dataList.add(things);
                } else if (repeat1.equals("norepeat") && endday.equals(thisday)){
                    dataList.add(things);
                }
            }
        }
        db.close();
        return dataList;
    }

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();
    }

    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"day_id=?",new String[]{dayid});
        db.close();
    }

    public void updateAll(FutureSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("day_id",user.getDayId());
        content.put("repeat_type",user.getRepeatType());
        content.put("end_day",user.getEndDay());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());

        db.update(TABLE,content,"day_id=?",new String[]{user.getDayId()});
        db.close();
    }

    public int CountAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from future where repeat_type like ?", new String[]{"everyday%"});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    //将Future插入Today的函数
    public void FutureToToday(Context context, int today, int m, String week, String thisday, Handler handler){

        this.context = context;
        this.today = today;
        this.m = m;
        this.week = week;
        this.thisday = thisday;
        this.handler = handler;

        TodaySQLite helper1;
        String DBName="today";
        int version=1;
        SQLiteDatabase db = helper.getWritableDatabase();

        if (can2) {

            cursorft = db.query(TABLE, null, null, null, null, null, null);
        }
        while (can && cursorft.moveToNext()) {
            String dayid = cursorft.getString(0);
            String repeat = cursorft.getString(1);
            String endday = cursorft.getString(2);
            int remind1 = cursorft.getInt(3);
            String time = cursorft.getString(4);
            String title = cursorft.getString(5);
            String important = cursorft.getString(6);
            String diary = cursorft.getString(7);
            boolean remind;
            remind = remind1 > 0;

            int end = 0;
            if (!endday.equals("null")){
               end = Integer.parseInt((endday.substring(0,4)+endday.substring(5,7)+endday.substring(8,10)));
            }
            if (end >= today || end == 0) {
                String repeat1 = repeat.substring(0, 8);
                //数据写入数据库
                TodaySQLiteUser things = new TodaySQLiteUser(dayid, false, remind, time, title, important, diary, thisday);
                helper1 = new TodaySQLite(context, DBName, null, version);
                TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper1);

                if (repeat1.equals("everywee")) {
                    String re = repeat.substring(8);
                    while (!re.equals("")) {
                        String a = re.substring(0, 1);
                        if (a.equals(week)) {
                            can = false;
                            dao.insert(things,context);

                            //数据上传到云端
                            new PostFunctions().SaveTodayPost(things,uiHandler);
                        }
                        re = re.substring(1);
                    }
                } else if (repeat1.equals("everyday") || (repeat1.equals("everymou") && m == 1)) {
                    can = false;
                    dao.insert(things,context);

                    //数据上传到云端
                    new PostFunctions().SaveTodayPost(things,uiHandler);
                } else if (end == today && repeat1.equals("norepeat")){
                    can = false;
                    dao.insert(things,context);

                    //数据上传到云端
                    new PostFunctions().SaveTodayPost(things,uiHandler);
                }
            }
            if (end <= today && end != 0){
                can = false;
                deleteByDayid(dayid);

                //删除云端
                new FutureDeleteTask(uiHandler).execute("http://120.77.222.242:10024/future/deletebyid?dayId=" + dayid);
            }

            //如果是最后一条，就发送message
            if (cursorft.isLast()){
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }
        db.close();
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 21:
                    can = true;
                    can2 = false;
                    FutureToToday(context,today,m,week,thisday,handler);

                    break;
                case 33:
                    //可以向下循环取出，但是不能重新查表
                    can = true;
                    can2 = false;
                    //删除Future成功，不做操作
                    break;
            }
        }
    };
}
