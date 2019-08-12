package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;
import com.schedule.record.app.sqlite.FutureSQLite;

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
        content.put("dayid",user.getDayid());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("nameid",user.getNameid());
        db.insert(TABLE,null,content);
        db.close();
    }


    public  FutureSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
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
            String nameid = cursor.getString(8);
            boolean remind;
            remind = remind1 > 0;
            user = new FutureSQLiteUser(dayid,repeat,endday,remind,time,title,important,diary,nameid);
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
            String nameid = cursor.getString(8);
            boolean remind;
            remind = remind1 > 0;
            FutureSQLiteUser things = new FutureSQLiteUser(dayid,repeat,endday,remind,time,title,important,diary,nameid);
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
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"today"},null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            CalenderWeekItem things = new CalenderWeekItem(dayid,title,important,false);
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

    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{dayid});
        db.close();
    }

    public void updateAll(FutureSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());
        content.put("nameid",user.getNameid());

        db.update(TABLE,content,"dayid=?",new String[]{user.getDayid()});
        db.close();
    }


    public int CountAllBar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish",null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
