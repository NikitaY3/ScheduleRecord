package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.schedule.record.app.sqlite.SpecialUserSQLite;
import com.schedule.record.app.sqlite.user.SpecialSQLiteUser;

import java.util.ArrayList;
import java.util.List;

public class SpecialSQLiteUserDao {

    private SpecialUserSQLite helper;
    private static final String TABLE = "special";

    public SpecialSQLiteUserDao(SpecialUserSQLite helper) {
        this.helper = helper;
    }

    public void insert(SpecialSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("authorization",user.getAuthorization());
        content.put("gnameid",user.getGnameid());
        content.put("snameid",user.getSnameid());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  SpecialSQLiteUser queryByNameid(String Nameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "nameid=?", new String[]{Nameid}, null, null, null);
        SpecialSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String authorization = cursor.getString(0);
            String gnameid = cursor.getString(1);
            String snameid = cursor.getString(2);
            user = new SpecialSQLiteUser(authorization,gnameid,snameid);
        }
        db.close();
        return user;
    }

    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String authorization = cursor.getString(0);
            String gnameid = cursor.getString(1);
            String snameid = cursor.getString(2);
            SpecialSQLiteUser user = new SpecialSQLiteUser(authorization,gnameid,snameid);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public String querySpecial(String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"gnameid =?", new String[]{myid},null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String snameid = cursor.getString(2);
            sb.append(snameid).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public String queryGeneral(String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"snameid =?", new String[]{myid},null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String gnameid = cursor.getString(1);
            sb.append(gnameid).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<SpecialSQLiteUser> quiryAll() {
        List<SpecialSQLiteUser> dataList = new ArrayList<SpecialSQLiteUser>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,null);
        while (cursor.moveToNext()){
            String authorization = cursor.getString(0);
            String gnameid = cursor.getString(1);
            String snameid = cursor.getString(2);
            SpecialSQLiteUser things = new SpecialSQLiteUser(authorization,gnameid,snameid);
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

    public void deleteByGNameid(String gnameid, String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"gnameid=? and snameid=?",new String[]{gnameid,myid});
        db.close();
    }

    public void deleteBySNameid(String snameid, String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"snameid=? and gnameid=?",new String[]{snameid, myid});
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
