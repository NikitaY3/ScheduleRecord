package com.schedule.record.app.function;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.schedule.record.app.sqlite.DaySQLite;

import java.util.ArrayList;
import java.util.List;

public class DaySQLiteUserDao {
    //    dayid varchar(50) primary key, checkbox bit,time datetime,title varchar(150),
    // important char(2),repeat varchar(50),endday date,diary text,picture char(50);
    private DaySQLite helper;
    private static final String TABLE = "day_1";

    public DaySQLiteUserDao(DaySQLite helper) {
        this.helper = helper;
    }

    public void insert(DaySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("diary",user.getDiary());
        content.put("picture",user.getPicture());
        content.put("isfinish",user.getIsfinish());
        db.insert(TABLE,null,content);
        db.close();
    }

    public void deleteByDayid(String dayid){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,"dayid=?",new String[]{dayid});
        db.close();
    }

    public  DaySQLiteUser queryBydayid(String Dayid){
        SQLiteDatabase db = helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE,null, "dayid=?", new String[]{Dayid}, null, null, null);
        DaySQLiteUser user = null;
        while (cursor.moveToNext()) {
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String diary = cursor.getString(8);
            String picture = cursor.getString(9);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            user = new DaySQLiteUser(dayid,checkbox,remind,time,title,important,repeat,endday,diary,picture,isfinish);
        }
        db.close();
        return user;
    }

    public String queryAllString(){
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,null);
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String diary = cursor.getString(8);
            String picture = cursor.getString(9);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            DaySQLiteUser user=new DaySQLiteUser(dayid,checkbox,remind,time,title,important,repeat,endday,diary,picture,isfinish);
            sb.append(user.toString()).append("\n");
        }
        db.close();
        return sb.toString();
    }

    public List<DaySQLiteUser> quiryTodayAndSetItem() {
        List<DaySQLiteUser> dataList = new ArrayList<DaySQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"today"},null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String diary = cursor.getString(8);
            String picture = cursor.getString(9);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            DaySQLiteUser things = new DaySQLiteUser(dayid,checkbox,remind,time,title,important,repeat,endday,diary,picture,isfinish);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }
    public List<DaySQLiteUser> quiryPassAndSetItem() {
        List<DaySQLiteUser> dataList = new ArrayList<DaySQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"pass"},null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String diary = cursor.getString(8);
            String picture = cursor.getString(9);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            DaySQLiteUser things = new DaySQLiteUser(dayid,checkbox,remind,time,title,important,repeat,endday,diary,picture,isfinish);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }
    public List<DaySQLiteUser> quiryFutureAndSetItem() {
        List<DaySQLiteUser> dataList = new ArrayList<DaySQLiteUser>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"future"},null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            int remind1 = cursor.getInt(2);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String diary = cursor.getString(8);
            String picture = cursor.getString(9);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            boolean remind;
            remind = remind1 > 0;
            DaySQLiteUser things = new DaySQLiteUser(dayid,checkbox,remind,time,title,important,repeat,endday,diary,picture,isfinish);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    //查询创建日期是否在day前
    public List<String> quiryPassDayid(int day) {
        List<String> dataList = new ArrayList<String>();//item的list
        //查询数据库并初始化日程列表
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int todayint = Integer.parseInt(dayid.substring(0,4)+ dayid.substring(5,7)+dayid.substring(8,10));
            if (todayint < day){
                dataList.add(dayid);
            }
        }
        db.close();
        return dataList;
    }
    //查询适合放在当天Week里面的日程(等待进一步判断每周和每月日程是否在当天生效)
    public List<CalenderWeekItem> quiryTodayWeek(int day) {
        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null, null,null,null,"important,time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            int end = Integer.parseInt(endday.substring(0,4)+ endday.substring(5,7)+endday.substring(8,10));
            int end1 = Integer.parseInt(dayid.substring(0,4)+ dayid.substring(5,7)+dayid.substring(8,10));
            if ((end1==day && repeat.substring(0,8).equals("norepeat") && endday.equals("0000-00-00"))||(end >= day)||(!repeat.substring(0,8).equals("norepeat"))) {
                CalenderWeekItem things = new CalenderWeekItem(dayid,checkbox,time,title,important,repeat,endday,isfinish);
                dataList.add(things);
            }
        }
        db.close();
        return dataList;
    }
    //查询适合放在day后当前周的日程（day是当天）(等待进一步判断：每周和每月日程是否在day后生效、不重复日程在哪一天生效)
//    public List<CalenderWeekItem> quiryFutureWeek(int day) {
//        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();
//        helper.getReadableDatabase();
//        SQLiteDatabase db=helper.getWritableDatabase();
//        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,"isfinish=?", new String[]{"today"},null,null,"important,time");
//        while (cursor.moveToNext()){
//            String dayid = cursor.getString(0);
//            int checkbox1 = cursor.getInt(1);
//            String time = cursor.getString(3);
//            String title = cursor.getString(4);
//            String important = cursor.getString(5);
//            String repeat = cursor.getString(6);
//            String endday = cursor.getString(7);
//            String isfinish = cursor.getString(10);
//            boolean checkbox;
//            checkbox = checkbox1 > 0;
//            int end = Integer.parseInt(endday.substring(0,4)+ endday.substring(5,7)+endday.substring(8,10));
//            if (end > day) {
//                CalenderWeekItem things = new CalenderWeekItem(dayid,checkbox,time,title,important,repeat,endday,isfinish);
//                dataList.add(things);
//            }
//        }
//        db.close();
//        return dataList;
//    }

    public List<CalenderWeekItem> quiryAndSetWeekItem() {
        List<CalenderWeekItem> dataList = new ArrayList<CalenderWeekItem>();
        helper.getReadableDatabase();
        SQLiteDatabase db=helper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.query(TABLE,null,null,null,null,null,"time");
        while (cursor.moveToNext()){
            String dayid = cursor.getString(0);
            int checkbox1 = cursor.getInt(1);
            String time = cursor.getString(3);
            String title = cursor.getString(4);
            String important = cursor.getString(5);
            String repeat = cursor.getString(6);
            String endday = cursor.getString(7);
            String isfinish = cursor.getString(10);
            boolean checkbox;
            checkbox = checkbox1 > 0;
            CalenderWeekItem things = new CalenderWeekItem(dayid,checkbox,time,title,important,repeat,endday,isfinish);
            dataList.add(things);
        }
        db.close();
        return dataList;
    }

    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();
    }

    public void updateAll(DaySQLiteUser user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("dayid",user.getDayid());
        content.put("checkbox",user.isCheckbox());
        content.put("remind",user.isRemind());
        content.put("time",user.getTime());
        content.put("title",user.getTitle());
        content.put("important",user.getImportant());
        content.put("repeat",user.getRepeat());
        content.put("endday",user.getEndday());
        content.put("diary",user.getDiary());
        content.put("picture",user.getPicture());
        content.put("isfinish",user.getIsfinish());
        db.update(TABLE,content,"dayid=?",new String[]{user.getDayid()});
        db.close();
    }

    public int CountBar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from day_1 where checkbox =1 and isfinish =? " ,new String[]{"today"});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }

    public int CountAllBar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from day_1 where isfinish =?",new String[]{"today"});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();
        return (int) count;
    }
}
