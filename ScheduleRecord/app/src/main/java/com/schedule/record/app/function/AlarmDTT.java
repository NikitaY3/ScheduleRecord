package com.schedule.record.app.function;

public class AlarmDTT {
    String dayid,time,title;

    public AlarmDTT(String dayid, String time, String title) {
        this.dayid = dayid;
        this.time = time;
        this.title = title;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
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
}
