package com.schedule.record.app.function;

public class CalenderWeekItem {
    private String dayid,time,title,important,repeat,endday,isfinish;
    private boolean checkbox;

    public CalenderWeekItem( String dayid, boolean checkbox, String time, String title,String important,String repeat,String endday,String isfinish) {
        this.dayid = dayid;
        this.checkbox = checkbox;
        this.time = time;
        this.title = title;
        this.important = important;
        this.repeat = repeat;
        this.endday = endday;
        this.isfinish = isfinish;
    }

    public String getDayid() {
        return dayid;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }

    @Override
    public String toString() {
        return "CalenderWeekItem{" +
                "dayid='" + dayid + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", important='" + important + '\'' +
                ", repeat='" + repeat + '\'' +
                ", endday='" + endday + '\'' +
                ", isfinish='" + isfinish + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }
}
