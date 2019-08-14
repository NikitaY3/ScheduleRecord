package com.schedule.record.app.sqlite.user;

public class PassSQLiteUser {
    private String dayid,title,passday;
    private int completion;
    private String important;

    public PassSQLiteUser(String dayid, String title, String passday, int completion, String important) {
        this.dayid = dayid;
        this.title = title;
        this.passday = passday;
        this.completion = completion;
        this.important = important;
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

    @Override
    public String toString() {
        return "PassSQLiteUser{" +
                "dayid='" + dayid + '\'' +
                ", title='" + title + '\'' +
                ", passday='" + passday + '\'' +
                ", completion=" + completion +
                ", important='" + important + '\'' +
                '}';
    }
}
