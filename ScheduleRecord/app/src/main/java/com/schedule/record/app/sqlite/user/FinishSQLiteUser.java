package com.schedule.record.app.sqlite.user;

public class FinishSQLiteUser {

    private String finishid,dayid;
    private Boolean checkbox,remind;
    private String time,title,important,diary;

    public FinishSQLiteUser(String finishid, String dayid, Boolean checkbox, Boolean remind, String time, String title, String important, String diary) {
        this.finishid = finishid;
        this.dayid = dayid;
        this.checkbox = checkbox;
        this.remind = remind;
        this.time = time;
        this.title = title;
        this.important = important;
        this.diary = diary;
    }

    public String getFinishid() {
        return finishid;
    }

    public void setFinishid(String finishid) {
        this.finishid = finishid;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
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

    @Override
    public String toString() {
        return "当前日程Finishid为：" + finishid + '\n' +
                "当前日程Dayid为：" + dayid + '\n' +
                "是否已经完成：" + checkbox + '\n' +
                "是否提醒：" + remind + '\n' +
                "日程开始时间：" + time + '\n' +
                "日程标题：" + title + '\n' +
                "日程重要程度：" + important + '\n'+
                "日程详细记录：" + diary + '\n';
    }
}
