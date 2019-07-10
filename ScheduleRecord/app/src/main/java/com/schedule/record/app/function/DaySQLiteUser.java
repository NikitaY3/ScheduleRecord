package com.schedule.record.app.function;

public class DaySQLiteUser {
//    dayid varchar(50) primary key, checkbox bit,time datetime,title varchar(150),important char(2),repeat varchar(50),endday date,diary text,picture char(50);
    private String dayid;
    private boolean checkbox;
    private String time,title,repeat,endday,diary,picture;
    private String important;

    public DaySQLiteUser(String dayid, boolean checkbox, String time, String title, String repeat, String endday, String diary, String picture, String important) {
        this.dayid = dayid;
        this.checkbox = checkbox;
        this.time = time;
        this.title = title;
        this.repeat = repeat;
        this.endday = endday;
        this.diary = diary;
        this.picture = picture;
        this.important = important;
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

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    @Override
    public String toString() {
        return "DaySQLiteUser{" +
                "dayid='" + dayid + '\'' +
                ", checkbox=" + checkbox +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", repeat='" + repeat + '\'' +
                ", endday='" + endday + '\'' +
                ", diary='" + diary + '\'' +
                ", picture='" + picture + '\'' +
                ", important='" + important + '\'' +
                '}';
    }
}
