package com.schedule.record.app.sqlite.user;

public class FutureSQLiteUser {

    private String dayid,repeat,endday;
    private boolean remind;
    private String time,title,important,diary,nameid;

    public FutureSQLiteUser(String dayid, String repeat, String endday, boolean remind, String time, String title, String important, String diary, String nameid) {
        this.dayid = dayid;
        this.repeat = repeat;
        this.endday = endday;
        this.remind = remind;
        this.time = time;
        this.title = title;
        this.important = important;
        this.diary = diary;
        this.nameid = nameid;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
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

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    @Override
    public String toString() {
        return "当前日程Dayid为：" + dayid + '\n' +
                "重复状况：" + repeat + '\n' +
                "截止日期：" + endday + '\n' +
                "是否提醒：" + remind + '\n' +
                "日程创建时间：" + time + '\n' +
                "日程标题：" + title + '\n' +
                "日程详细记录：" + diary + '\n' +
                "日程重要程度：" + important + '\n'+
                "当前用户手机号"+nameid+'\n';
    }
}
