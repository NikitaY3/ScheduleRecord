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

        String finishId = user.getFinishId();

        content.put("finish_id",finishId);
        content.put("day_id",user.getDayId());
        content.put("checkbox",user.getCheckbox());
        content.put("remind",user.getRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("diary",user.getDiary());

        if (queryByFinishid(finishId) == null) {
            db = helper.getWritableDatabase();
            db.insert(TABLE, null, content);
        }

        db.insert(TABLE,null,content);
        db.close();
    }

    //没有云端删除
    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"day_id=?",new String[]{dayid});
        db.close();
    }

    public void deleteByFinishid(String finishid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"finish_id=?",new String[]{finishid});
        db.close();
    }

    public  FinishSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "day_id=?", new String[]{Dayid}, null, null, null);
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
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy);
        }
        db.close();
        return user;
    }

    public  FinishSQLiteUser queryByFinishid(String Finishid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "finish_id=?", new String[]{Finishid}, null, null, null);
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
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy);
        }
        db.close();
        return user;
    }

    public  List<FinishSQLiteUser> quiryAndSetItem(){
        List<FinishSQLiteUser> dataList = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, null, null, null, null, "finish_id");
        while (cursor.moveToNext()) {
            String finishid = cursor.getString(0);
            String dayid = cursor.getString(1);
            int checkbox1 = cursor.getInt(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String dairy = cursor.getString(7);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            FinishSQLiteUser things = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    //查询Week,根据日期查询
    public List<CalenderWeekItem> quiryAndSetWeekItem(String day) {
        List<CalenderWeekItem> dataList = new ArrayList<>();
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"time,important");
        while (cursor.moveToNext()){
            String finishid = cursor.getString(0);
            int checkbox1 = cursor.getInt(2);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            if (finishid.length() >= 21){
                if (finishid.substring(11, 21).equals(day)){
                    CalenderWeekItem things = new CalenderWeekItem(finishid,title,important,checkbox);
                    dataList.add(things);
                }
            }
        }
        db.close();
        return dataList;
    }

    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String finishid = cursor.getString(0);
            String dayid = cursor.getString(1);
            int checkbox1 = cursor.getInt(2);
            int remind1 = cursor.getInt(3);
            String time = cursor.getString(4);
            String title = cursor.getString(5);
            String important = cursor.getString(6);
            String dairy = cursor.getString(7);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            FinishSQLiteUser user = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,dairy);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public int CountFinishByDayid(String dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where checkbox = 1 and day_id like ? ", new String[]{dayid});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    //根据日期查询当天完成日程数量
    public int CountFinishByDay(String day){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where checkbox = 1 and finish_id like ? ", new String[]{'%'+day+'%'});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    //根据日期查询当天日程总数量
    public int CountByDay(String day){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where finish_id like ? ", new String[]{'%'+day+'%'});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    public int CountAllByDayid(String dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from finish where day_id =? ", new String[]{dayid});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
