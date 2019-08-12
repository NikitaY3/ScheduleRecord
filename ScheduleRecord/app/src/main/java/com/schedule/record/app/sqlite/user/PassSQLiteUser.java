package com.schedule.record.app.sqlite.user;

public class PassSQLiteUser {

    private String dayid,passday;
    private int completion;
    private String important,nameid;

    public PassSQLiteUser(String dayid, String passday, int completion, String important, String nameid) {
        this.dayid = dayid;
        this.passday = passday;
        this.completion = completion;
        this.important = important;
        this.nameid = nameid;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
    }

    public String getPassday() {
        return passday;
    }

    public void setPassday(String passday) {
        this.passday = passday;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    @Override
    public String toString() {
        return "PassSQLiteUser{" +
                "dayid='" + dayid + '\'' +
                ", passday='" + passday + '\'' +
                ", completion=" + completion +
                ", important='" + important + '\'' +
                ", nameid='" + nameid + '\'' +
                '}';
    }
}
