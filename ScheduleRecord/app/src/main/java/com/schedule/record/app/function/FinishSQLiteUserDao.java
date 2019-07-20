package com.schedule.record.app.function;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.FinishSQLite;

public class FinishSQLiteUserDao {
    private FinishSQLite helper;
    private static final String TABLE = "day_1";

    public FinishSQLiteUserDao(FinishSQLite helper) {
        this.helper = helper;
    }

    public void insert(FinishSQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("finishtime",user.getFinishtime());
        content.put("dayid",user.getDayid());
        db.insert(TABLE,null,content);
        db.close();
    }
    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{dayid});
        db.close();
    }
    public  FinishSQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
        FinishSQLiteUser user = null;
        while (cursor.moveToNext()) {
            String finishtime = cursor.getString(0);
            String dayid = cursor.getString(1);
            user = new FinishSQLiteUser(dayid,finishtime);
        }
        db.close();
        return user;
    }
}
