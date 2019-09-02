package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;

import java.util.ArrayList;
import java.util.List;

public class AuthoritySQLiteUserDao {

    private AuthoritySQLite helper;
    private static final String TABLE = "authority";


    public AuthoritySQLiteUserDao(AuthoritySQLite helper) {
        this.helper = helper;
    }

    public void insert(AuthoritySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("gname_id",user.getGnameId());
        content.put("sname_id",user.getSnameId());
        db.insert(TABLE,null,content);
        db.close();
    }

    public  Boolean queryByGSNameid(String gnameid, String snameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "gname_id =? and sname_id =?", new String[]{gnameid,snameid}, null, null, null);
        AuthoritySQLiteUser user = null;
        while (cursor.moveToNext()) {
            String authorization = cursor.getString(0);
            String snameid1 = cursor.getString(1);
            String gnameid1 = cursor.getString(2);
            user = new AuthoritySQLiteUser(authorization,snameid1,gnameid1);
        }
        db.close();

        return user != null;
    }

    public  AuthoritySQLiteUser queryUserByGSNameid(String gnameid, String snameid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "gname_id =? and sname_id =?", new String[]{gnameid,snameid}, null, null, null);

        AuthoritySQLiteUser user = null;
        while (cursor.moveToNext()) {
            String authorization = cursor.getString(0);
            user = new AuthoritySQLiteUser(authorization,gnameid,snameid);
        }
        db.close();

        return user;
    }

    public String querySpecial(String myid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"gname_id =?", new String[]{myid},null,null,null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()){
            String snameid = cursor.getString(1);
            sb.append(snameid).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public String queryGeneral(String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"sname_id =?", new String[]{myid},null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String gnameid = cursor.getString(2);
            sb.append(gnameid).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<AuthoritySQLiteUser> quiryAndSetGeneralItem(String myNameId) {
        List<AuthoritySQLiteUser> dataList = new ArrayList<>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null,"sname_id =?", new String[]{myNameId},null,null,null);
        while (cursor.moveToNext()){
            String authorization = cursor.getString(0);
            String snameid = cursor.getString(1);
            String gnameid = cursor.getString(2);

            AuthoritySQLiteUser things = new AuthoritySQLiteUser(authorization,gnameid,snameid);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }


    public void updateAll(AuthoritySQLiteUser user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("authorization",user.getAuthorization());
        content.put("gname_id",user.getGnameId());
        content.put("sname_id",user.getSnameId());

        db.update(TABLE,content,"authorization =?",new String[]{user.getAuthorization()});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();
    }

    public void deleteByNameid(String gnameid, String snameid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"gname_id=? and sname_id=?",new String[]{gnameid,snameid});
        db.close();
    }

//    public int CountAll(){
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select count(*) from Authority ",null);
//        cursor.moveToFirst();
//        long count = cursor.getLong(0);
//        cursor.close();
//        db.close();
//        return (int) count;
//    }
}
