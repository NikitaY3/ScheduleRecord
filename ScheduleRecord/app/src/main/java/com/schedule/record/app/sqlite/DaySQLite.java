package com.schedule.record.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaySQLite extends SQLiteOpenHelper {

    String CREAT_TABLE="create table day_1(dayid datetime primary key, checkbox bit,remind bit,time datetime,title varchar(150),important char(2),repeat varchar(50),endday date,diary text,picture char(50),isfinish char(10))";
    public DaySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
