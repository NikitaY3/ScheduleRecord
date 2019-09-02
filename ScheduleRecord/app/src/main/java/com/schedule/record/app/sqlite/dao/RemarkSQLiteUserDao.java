package com.schedule.record.app.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.RemarkSQLite;
import com.schedule.record.app.sqlite.user.RemarkSQLiteUser;

public class RemarkSQLiteUserDao {

    private RemarkSQLite helper;
    private static final String TABLE = "remark";


    public RemarkSQLiteUserDao(RemarkSQLite helper) {
        this.helper = helper;
    }

    public void insert(RemarkSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("name_id",user.getNameId());
        content.put("remark_name",user.getRemarkName());
        db.insert(TABLE,null,content);
        db.close();
    }

    public String queryRemarkName(String myid){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"name_id =?", new String[]{myid},null,null,null);
        String sb = null;
        while (cursor.moveToNext()){
            sb  = cursor.getString(1);
        }
        db.close();
        return sb;
    }

    public void updateAll(RemarkSQLiteUser user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("name_id",user.getNameId());
        content.put("remark_name",user.getRemarkName());

        db.update(TABLE,content,"name_id =?",new String[]{user.getNameId()});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();
    }
    
}
