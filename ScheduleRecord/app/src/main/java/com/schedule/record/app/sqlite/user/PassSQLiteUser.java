package com.schedule.record.app.sqlite.user;

public class PassSQLiteUser {
    private String dayId,title,passDay;
    private int completion;
    private String important;

    public PassSQLiteUser(String dayid, String title, String passday, int completion, String important) {
        this.dayId = dayid;
        this.title = title;
        this.passDay = passday;
        this.completion = completion;
        this.important = important;
    }

    public String getDayid() {
        return dayId;
    }

    public String getTitle() {
        return title;
    }

    public String getPassday() {
        return passDay;
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
                "dayid='" + dayId + '\'' +
                ", title='" + title + '\'' +
                ", passday='" + passDay + '\'' +
                ", completion=" + completion +
                ", important='" + important + '\'' +
                '}';
    }
}
