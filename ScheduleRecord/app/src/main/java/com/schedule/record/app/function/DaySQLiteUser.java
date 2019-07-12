package com.schedule.record.app.function;

public class DaySQLiteUser {
//    dayid varchar(50) primary key, checkbox bit,time datetime,title varchar(150),important char(2),repeat varchar(50),endday date,diary text,picture char(50);
    private String dayid;
    private boolean checkbox;
    private String time,title,important,repeat,endday,diary,picture;

    public DaySQLiteUser(String dayid, boolean checkbox, String time, String title, String important, String repeat, String endday, String diary, String picture) {
        this.dayid = dayid;
        this.checkbox = checkbox;
        this.time = time;
        this.title = title;
        this.important = important;
        this.repeat = repeat;
        this.endday = endday;
        this.diary = diary;
        this.picture = picture;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getEndday() {
        return endday;
    }

    public void setEndday(String endday) {
        this.endday = endday;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "当前日程Dayid为：" + dayid + '\n' +
                "是否已经完成：" + checkbox + '\n' +
                "日程创建时间：" + time + '\n' +
                "日程标题：" + title + '\n' +
                "日程重复" + repeat + '\n' +
                "日程截止时间：" + endday + '\n' +
                "日程详细记录：" + diary + '\n' +
                "日程图片：" + picture + '\n' +
                "日程重要程度：" + important + '\n'+'\n';
    }
}
