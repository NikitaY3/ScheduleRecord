package com.schedule.record.app.sqlite.user;

public class PassSQLiteUser {
//    create table pass(dayid varchar(32) primary key,title varchar(128)
// , passday date, completion int, important char(2), nameid char(12))
    private String dayid,title,passday;
    private int completion;
    private String important,nameid;

    public PassSQLiteUser(String dayid, String title, String passday, int completion, String important, String nameid) {
        this.dayid = dayid;
        this.title = title;
        this.passday = passday;
        this.completion = completion;
        this.important = important;
        this.nameid = nameid;
    }

    public String getDayid() {
        return dayid;
    }

    public String getTitle() {
        return title;
    }

    public String getPassday() {
        return passday;
    }

    public int getCompletion() {
        return completion;
    }

    public String getImportant() {
        return important;
    }

    public String getNameid() {
        return nameid;
    }

    @Override
    public String toString() {
        return "PassSQLiteUser{" +
                "dayid='" + dayid + '\'' +
                ", title='" + title + '\'' +
                ", passday='" + passday + '\'' +
                ", completion=" + completion +
                ", important='" + important + '\'' +
                ", nameid='" + nameid + '\'' +
                '}';
    }
}
