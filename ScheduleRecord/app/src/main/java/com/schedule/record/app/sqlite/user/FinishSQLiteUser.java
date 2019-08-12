package com.schedule.record.app.sqlite.user;

public class FinishSQLiteUser {

    String finishid,dayid,time,title,important,diary,nameid;
    Boolean checkbox,remind;

    public FinishSQLiteUser(String finishid, String dayid, Boolean checkbox, Boolean remind, String time, String title, String important, String diary, String nameid) {
        this.finishid = finishid;
        this.dayid = dayid;
        this.time = time;
        this.title = title;
        this.important = important;
        this.diary = diary;
        this.nameid = nameid;
        this.checkbox = checkbox;
        this.remind = remind;
    }

    public String getFinishid() {
        return finishid;
    }

    public String getDayid() {
        return dayid;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getImportant() {
        return important;
    }

    public String getDiary() {
        return diary;
    }

    public String getNameid() {
        return nameid;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public Boolean getRemind() {
        return remind;
    }

    @Override
    public String toString() {
        return "FinishSQLiteUser{" +
                "finishtime='" + finishid + '\'' +
                ", dayid='" + dayid + '\'' +
                ", checkbox='" + checkbox + '\'' +
                ", remind='" + remind + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", important='" + important + '\'' +
                ", diary='" + diary + '\'' +
                ", nameid='" + nameid + '\'' +
                '}';
    }
}
