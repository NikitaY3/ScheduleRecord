package com.schedule.record.app.sqlite.user;

public class TodaySQLiteUser {

    private String dayId;
    private boolean checkbox,remind;
    private String time,title,important,diary,thisDay;

    public TodaySQLiteUser(String dayid, boolean checkbox, boolean remind, String time, String title,
                           String important, String diary, String thisday) {
        this.dayId = dayid;
        this.checkbox = checkbox;
        this.remind = remind;
        this.time = time;
        this.title = title;
        this.important = important;
        this.diary = diary;
        this.thisDay = thisday;
    }

    public String getDayid() {
        return dayId;
    }

    public void setDayid(String dayid) {
        this.dayId = dayid;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
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

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getThisday() {
        return thisDay;
    }

    public void setThisday(String thisday) {
        this.thisDay = thisday;
    }

    @Override
    public String toString() {
        return "当前日程Dayid为：" + dayId + '\n' +
                "是否已经完成：" + checkbox + '\n' +
                "是否提醒：" + remind + '\n' +
                "日程开始时间：" + time + '\n' +
                "日程标题：" + title + '\n' +
                "日程详细记录：" + diary + '\n' +
                "日程重要程度：" + important + '\n'+
                "当前日期"+thisDay+'\n';
    }
}
