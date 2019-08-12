package com.schedule.record.app.sqlite.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.FinishSQLite;

import java.util.ArrayList;
import java.util.List;

public class FinishSQLiteUserDao {
    private FinishSQLite helper;
    private static final String TABLE = "finish";

    public FinishSQLiteUserDao(FinishSQLite helper) {
        this.helper = helper;
    }

    public void insert(FinishSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("finishid",user.getFinishid());
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.getCheckbox());
        content.put("remind",user.getRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("dairy",user.getDiary());
        content.put("nameid",user.getNameid());
        db.insert(TABLE,null,content);
        db.close();
    }

    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{dayid});
        db.close();
    }

    public void deleteByFinishid(String finishid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"finishid=?",new String[]{finishid});
        db.close();
    }

    public  FinishSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
        FinishSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String finishid = cursor.getString(0);
            String dayid = cursor.getString(1);
            int checkbox1 = cursor.getInt(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String dairy = cursor.getString(7);
            String nameid = cursor.getString(8);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy,nameid);
        }
        db.close();
        return user;
    }

    public  FinishSQLiteUser queryFinishid(String Finishid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "finishid=?", new String[]{Finishid}, null, null, null);
        FinishSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String finishid = cursor.getString(0);
            String dayid = cursor.getString(1);
            int checkbox1 = cursor.getInt(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String dairy = cursor.getString(7);
            String nameid = cursor.getString(8);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy,nameid);
        }
        db.close();
        return user;
    }

    //查询Week
    public List<CalenderWeekItem> quiryAndSetWeekItem() {
        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"today"},null,null,"important,time");
        while (cursor.moveToNext()){
            String finishid = cursor.getString(0);
            int checkbox1 = cursor.getInt(2);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            CalenderWeekItem things = new CalenderWeekItem(finishid,title,important,checkbox);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    public int CountFinish(String dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where checkbox =1 and dayid =? ", new String[]{dayid});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    public int CountAllByDayid(String dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where dayid =? ", new String[]{dayid});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
