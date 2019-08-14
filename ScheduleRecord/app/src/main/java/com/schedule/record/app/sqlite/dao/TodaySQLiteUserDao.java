package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.schedule.record.app.function.AlarmDTT;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.util.ArrayList;
import java.util.List;

public class TodaySQLiteUserDao {

    private TodaySQLite helper;
    private static final String TABLE = "today";

    public TodaySQLiteUserDao(TodaySQLite helper) {
        this.helper = helper;
    }

    public void insert(TodaySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("thisday",user.getThisday());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  TodaySQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
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

    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
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
            TodaySQLiteUser user = new TodaySQLiteUser(dayid,checkbox,remind,time,title,important,diary,thisday);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<TodaySQLiteUser> quiryAndSetItem() {
        List<TodaySQLiteUser> dataList = new ArrayList<TodaySQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
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

    //用于闹钟设置时间的查询
    public List<AlarmDTT> quiryTodayTime() {
        List<AlarmDTT> dataList = new ArrayList<AlarmDTT>();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from today where checkbox =0 ", null);
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String time = cursor.getString(3);
            String title = cursor.getString(4);

            if (!time.substring(0, 2).equals("XX")) {
                AlarmDTT things = new AlarmDTT(dayid, time, title);
                dataList.add(things);
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
        db.delete(TABLE,"dayid=?",new String[]{dayid});
        db.close();
    }

    public void updateAll(TodaySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("thisday",user.getThisday());
        db.update(TABLE,content,"dayid=?",new String[]{user.getDayid()});
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

    //将Today插入Finish的函数
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

            if (!thisday.equals(today)) {
                //数据写入数据库
                FinishSQLiteUser things = new FinishSQLiteUser(finishid, dayid, checkbox, remind, time, title, important, diary);
                helper1 = new FinishSQLite(context, DBName1, null, version1);
                FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper1);
                dao.insert(things);

                PassSQLite helper2;
                String DBName2="pass";

                FutureSQLite helper3;
                String DBName3="Future";

                helper3 = new FutureSQLite(context, DBName3, null, version1);
                FutureSQLiteUserDao daof = new FutureSQLiteUserDao(helper3);
                String endday = daof.queryBydayid(dayid).getEndday();
                if (endday != null) {
                    int end = Integer.parseInt((endday.substring(0, 4) + endday.substring(5, 7) + endday.substring(8, 10)));
                    if (end < day) {
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
                deleteByDayid(dayid);
            }
        }
        db.close();
    }
}
