package com.schedule.record.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FutureSQLite extends SQLiteOpenHelper {

    String CREAT_TABLE="create table future(dayid varchar(32) primary key,repeat varchar(50),endday date,remind bit,time datetime,title varchar(128),important char(2), diary text, nameid char(12))";
    public FutureSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}