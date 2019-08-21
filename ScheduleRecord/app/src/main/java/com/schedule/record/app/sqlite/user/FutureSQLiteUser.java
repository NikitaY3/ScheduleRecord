package com.schedule.record.app.sqlite.user;

public class FutureSQLiteUser {

    private String dayId,repeatType,endDay;
    private boolean remind;
    private String time,title,important,diary;

    public FutureSQLiteUser(String dayid, String repeat, String endday, boolean remind, String time, String title, String important, String diary) {
        this.dayId = dayid;
        this.repeatType = repeat;
        this.endDay = endday;
        this.remind = remind;
        this.time = time;
        this.title = title;
        this.important = important;
        this.diary = diary;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
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

    @Override
    public String toString() {
        return "当前日程Dayid为：" + dayId + '\n' +
                "重复状况：" + repeatType + '\n' +
                "截止日期：" + endDay + '\n' +
                "是否提醒：" + remind + '\n' +
                "日程创建时间：" + time + '\n' +
                "日程标题：" + title + '\n' +
                "日程详细记录：" + diary + '\n' +
                "日程重要程度：" + important + '\n';
    }
}
