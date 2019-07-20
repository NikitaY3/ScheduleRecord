package com.schedule.record.app.function;

public class FinishSQLiteUser {
//    String CREAT_TABLE="create table day_finish(finishtime datetime primary key,dayid datetime )";
    String finishtime,dayid;

    public FinishSQLiteUser(String finishtime, String dayid) {
        this.finishtime = finishtime;
        this.dayid = dayid;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
    }

    @Override
    public String toString() {
        return "FinishSQLiteUser{" +
                "finishtime='" + finishtime + '\'' +
                ", dayid='" + dayid + '\'' +
                '}';
    }
}
