package com.schedule.record.app.function;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.schedule.record.app.sqlite.DaySQLite;

public class DaySQLiteUserDao {
    //    dayid varchar(50) primary key, checkbox bit,time datetime,title varchar(150),
    // important char(2),repeat varchar(50),endday date,diary text,picture char(50);
    private DaySQLite helper;
    public static final String TABLE = "day_1";

    public DaySQLiteUserDao(DaySQLite helper) {
        this.helper = helper;
    }

    public void insert(DaySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();

        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("diary",user.getDiary());
        content.put("picture",user.getPicture());
        db.insert(TABLE,null,content);
        db.close();
    }

    public void delete(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{dayid});
    }

    public void update(DaySQLiteUser user){
        DaySQLiteUser user1=user;
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{user.getDayid()});

        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("diary",user.getDiary());
        content.put("picture",user.getPicture());
        db.insert(TABLE,null,content);
        db.close();

    }

    public String queryAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            //    dayid varchar(50) primary key, checkbox bit,time datetime,title varchar(150),
            // important char(2),repeat varchar(50),endday date,diary text,picture char(50);
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            String time = cursor.getString(2);
            String title = cursor.getString(3);
            String important = cursor.getString(4);
            String repeat = cursor.getString(5);
            String endday = cursor.getString(6);
            String diary = cursor.getString(7);
            String picture = cursor.getString(8);
            boolean checkbox;
            if(checkbox1>0){checkbox = true;}else {checkbox=false;}
            DaySQLiteUser user=new DaySQLiteUser(dayid,checkbox,time,title,important,repeat,endday,diary,picture);

            sb.append(user.toString()+"\n");
        }
        return sb.toString();
    }

    public  String queryBydayid(String Dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            String time = cursor.getString(2);
            String title = cursor.getString(3);
            String important = cursor.getString(4);
            String repeat = cursor.getString(5);
            String endday = cursor.getString(6);
            String diary = cursor.getString(7);
            String picture = cursor.getString(8);
            boolean checkbox;
            if(checkbox1>0){checkbox = true;}else {checkbox=false;}
            DaySQLiteUser user=new DaySQLiteUser(dayid,checkbox,time,title,important,repeat,endday,diary,picture);
            sb.append(user.toString());
        }
        return sb.toString();
    }
}
