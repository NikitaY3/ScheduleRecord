package com.schedule.record.app.function;

public class CalenderWeekItem {
    private String dayid,title,important;
    private boolean checkbox;

    public CalenderWeekItem(String dayid, String title, String important, boolean checkbox) {
        this.dayid = dayid;
        this.title = title;
        this.important = important;
        this.checkbox = checkbox;
    }

    public String getDayid() {
        return dayid;
    }

    public String getTitle() {
        return title;
    }

    public String getImportant() {
        return important;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    @Override
    public String toString() {
        return "CalenderWeekItem{" +
                "dayid='" + dayid + '\'' +
                ", title='" + title + '\'' +
                ", important='" + important + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }
}
