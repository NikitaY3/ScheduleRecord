package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

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

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
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
}
