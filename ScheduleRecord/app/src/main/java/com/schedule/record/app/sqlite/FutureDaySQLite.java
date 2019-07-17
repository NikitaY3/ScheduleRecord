package com.schedule.record.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FutureDaySQLite extends SQLiteOpenHelper {

    String CREAT_TABLE="create table day_future(dayid3 datetime primary key, checkbox3 bit,remind3 bit,time3 datetime,title3 varchar(150),important3 char(2),repeat3 varchar(50),endday3 date,diary3 text,picture3 char(50))";
    public FutureDaySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
