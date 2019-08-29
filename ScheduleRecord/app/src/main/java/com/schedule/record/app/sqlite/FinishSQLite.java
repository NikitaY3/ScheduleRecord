package com.schedule.record.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FinishSQLite extends SQLiteOpenHelper {

    public FinishSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAT_TABLE = "create table finish(finish_id varchar(32) primary key, day_id varchar(32), checkbox bit,remind bit,time varchar(8),title varchar(128),important char(2), diary text)";
        db.execSQL(CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
