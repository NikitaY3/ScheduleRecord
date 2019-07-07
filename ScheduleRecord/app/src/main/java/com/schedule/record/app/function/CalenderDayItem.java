package com.schedule.record.app.function;

public class CalenderDayItem {
    private String news,time;

    public CalenderDayItem(  String time,String news) {
        this.time = time;
        this.news = news;
    }

    public String getNews() {
        return news;
    }
    public String getTime() {
        return time;
    }

    public void setNews(String name) {
        this.news = news;
    }
    public void setTime(String name) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "现在是"+time+news;
    }
}
