package com.schedule.record.app.function;

public class CalenderDayItem {
    private String news,time,check;

    public CalenderDayItem(String time,String news) {
        this.time = time;
        this.news = news;
    }
    public CalenderDayItem(String check,String time,String news) {
        this.check = check;
        this.time = time;
        this.news = news;
    }

    public String getCheck() {
        return check;
    }
    public String getNews() {
        return news;
    }
    public String getTime() {
        return time;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public void setNews(String news) {
        this.news = news;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "现在是"+time+news;
    }
}
