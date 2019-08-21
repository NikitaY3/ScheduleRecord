package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import java.util.ArrayList;
import java.util.List;

public class GeneralSQLiteUserDao {

    private GeneralUserSQLite helper;
    private static final String TABLE = "general_user";

    public GeneralSQLiteUserDao(GeneralUserSQLite helper) {
        this.helper = helper;
    }

    public void insert(GeneralSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("name_id",user.getNameid());
        content.put("name",user.getName());
        content.put("password",user.getPassword());
        content.put("sex",user.getSex());
        content.put("birthday",user.getBirthday());
        content.put("head",user.getHead());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  GeneralSQLiteUser queryByNameid(String Nameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "name_id=?", new String[]{Nameid}, null, null, null);
        GeneralSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String nameid = cursor.getString(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String sex = cursor.getString(3);
            String birthday = cursor.getString(4);
            String head = cursor.getString(5);
            user = new GeneralSQLiteUser(nameid,name,password,sex,birthday,head);
        }
        db.close();
        return user;
    }

    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String nameid = cursor.getString(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String sex = cursor.getString(3);
            String birthday = cursor.getString(4);
            String head = cursor.getString(5);
            GeneralSQLiteUser user = new GeneralSQLiteUser(nameid,name,password,sex,birthday,head);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<GeneralSQLiteUser> quiryAll() {
        List<GeneralSQLiteUser> dataList = new ArrayList<GeneralSQLiteUser>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,null);
        while (cursor.moveToNext()){
            String nameid = cursor.getString(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String sex = cursor.getString(3);
            String birthday = cursor.getString(4);
            String head = cursor.getString(5);
            GeneralSQLiteUser things = new GeneralSQLiteUser(nameid,name,password,sex,birthday,head);
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

    public void deleteByNameid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"name_id=?",new String[]{dayid});
        db.close();
    }

    public void updateAll(GeneralSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("name_id",user.getNameid());
        content.put("name",user.getName());
        content.put("password",user.getPassword());
        content.put("sex",user.getSex());
        content.put("birthday",user.getBirthday());
        content.put("head",user.getHead());
        db.update(TABLE,content,"name_id=?",new String[]{user.getNameid()});
        db.close();
    }

    public int CountAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from general_user ",null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
