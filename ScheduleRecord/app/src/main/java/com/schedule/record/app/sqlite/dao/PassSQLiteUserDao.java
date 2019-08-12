package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.user.PassSQLiteUser;
import com.schedule.record.app.sqlite.PassSQLite;

import java.util.ArrayList;
import java.util.List;

public class PassSQLiteUserDao {

    private PassSQLite helper;
    private static final String TABLE = "pass";

    public PassSQLiteUserDao(PassSQLite helper) {
        this.helper = helper;
    }


    public void insert(PassSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("passday",user.getPassday());
        content.put("completion",user.getCompletion());
        content.put("important",user.getImportant());
        content.put("nameid",user.getNameid());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  PassSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
        PassSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            String passday = cursor.getString(1);
            int completion = cursor.getInt(2);
            String important = cursor.getString(3);
            String nameid = cursor.getString(4);
            user = new PassSQLiteUser(dayid,passday,completion,important,nameid);
        }
        db.close();
        return user;
    }

    //    private String dayid,passday;
//    private int completion;
//    private String important,nameid;
    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String passday = cursor.getString(1);
            int completion = cursor.getInt(2);
            String important = cursor.getString(3);
            String nameid = cursor.getString(4);
            PassSQLiteUser user = new PassSQLiteUser(dayid,passday,completion,important,nameid);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<PassSQLiteUser> quiryAndSetItem() {
        List<PassSQLiteUser> dataList = new ArrayList<PassSQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"today"},null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String passday = cursor.getString(1);
            int completion = cursor.getInt(2);
            String important = cursor.getString(3);
            String nameid = cursor.getString(4);
            PassSQLiteUser things = new PassSQLiteUser(dayid,passday,completion,important,nameid);
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

    public void updateAll(PassSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("passday",user.getPassday());
        content.put("completion",user.getCompletion());
        content.put("important",user.getImportant());
        content.put("nameid",user.getNameid());
        db.update(TABLE,content,"dayid=?",new String[]{user.getDayid()});
        db.close();
    }

    public int CountAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from pass ",null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
