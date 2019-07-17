package com.schedule.record.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PassDaySQLite extends SQLiteOpenHelper {

    String CREAT_TABLE="create table day_pass(dayid2 datetime primary key, checkbox2 bit,remind2 bit,time2 datetime,title2 varchar(150),important2 char(2),repeat2 varchar(50),endday2 date,diary2 text,picture2 char(50))";
    public PassDaySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
