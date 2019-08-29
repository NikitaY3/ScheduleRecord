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
        content.put("day_id",user.getDayid());
        content.put("title",user.getTitle());
        content.put("pass_day",user.getPassday());
        content.put("completion",user.getCompletion());
        content.put("important",user.getImportant());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  PassSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "day_id=?", new String[]{Dayid}, null, null, null);
        PassSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            String title = cursor.getString(1);
            String passday = cursor.getString(2);
            int completion = cursor.getInt(3);
            String important = cursor.getString(4);
            user = new PassSQLiteUser(dayid,title,passday,completion,important);
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
            String title = cursor.getString(1);
            String passday = cursor.getString(2);
            int completion = cursor.getInt(3);
            String important = cursor.getString(4);
            PassSQLiteUser user = new PassSQLiteUser(dayid,title,passday,completion,important);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<PassSQLiteUser> quiryAndSetItem() {
        List<PassSQLiteUser> dataList = new ArrayList<>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"pass_day,important");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            String title = cursor.getString(1);
            String passday = cursor.getString(2);
            int completion = cursor.getInt(3);
            String important = cursor.getString(4);
            PassSQLiteUser things = new PassSQLiteUser(dayid,title,passday,completion,important);
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
        db.delete(TABLE,"day_id=?",new String[]{dayid});
        db.close();
    }

    public void updateAll(PassSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("day_id",user.getDayid());
        content.put("title",user.getTitle());
        content.put("pass_day",user.getPassday());
        content.put("completion",user.getCompletion());
        content.put("important",user.getImportant());
        db.update(TABLE,content,"day_id=?",new String[]{user.getDayid()});
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
