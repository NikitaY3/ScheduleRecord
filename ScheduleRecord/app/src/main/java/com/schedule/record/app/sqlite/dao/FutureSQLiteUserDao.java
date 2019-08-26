package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.schedule.record.app.clock.AlarmSet;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.ArrayList;
import java.util.List;

public class FutureSQLiteUserDao {

    private FutureSQLite helper;
    private static final String TABLE = "future";

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
        List<FutureSQLiteUser> dataList = new ArrayList<FutureSQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
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
        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();//item的list
        //查询数据库并初始化日程列表
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            String repeat = cursor.getString(1);
            String endday = cursor.getString(2);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            CalenderWeekItem things = new CalenderWeekItem(dayid, title, important, false);

            int end = Integer.parseInt((endday.substring(0, 4) + endday.substring(5, 7) + endday.substring(8, 10)));
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
    public void FutureToToday(Context context, int today, int m, String week, String thisday){

        TodaySQLite helper1;
        String DBName="today";
        int version=1;

        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, null, null, null, null, null);

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


            int end = Integer.parseInt((endday.substring(0,4)+endday.substring(5,7)+endday.substring(8,10)));
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
                            dao.insert(things,context);
                        }
                        re = re.substring(1);
                    }
                } else if (repeat1.equals("everyday") || (repeat1.equals("everymou") && m == 1)) {
                    dao.insert(things,context);
                } else if (end == today && repeat1.equals("norepeat")){
                    dao.insert(things,context);
                }
            }
            if (end <= today && end != 0){
                deleteByDayid(dayid);
            }
        }
        db.close();
    }
}
