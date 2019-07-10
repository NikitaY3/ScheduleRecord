package com.schedule.record.app.function;

public class CalenderWeekItem {
    private String Count,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday;

    public CalenderWeekItem(String Count, String Sunday, String Monday, String Tuesday, String Wednesday, String Thursday, String Friday, String Saturday) {
        this.Count = Count;
        this.Sunday = Sunday;
        this.Monday = Monday;
        this.Tuesday = Tuesday;
        this.Wednesday = Wednesday;
        this.Thursday = Thursday;
        this.Friday = Friday;
        this.Saturday = Saturday;
    }

    public String getCount() {
        return Count;
    }
    public void setCount(String count) {
        Count = count;
    }

    public String getSunday() {
        return Sunday;
    }
    public void setSunday(String sunday) {
        Sunday = sunday;
    }

    public String getMonday() {
        return Monday;
    }
    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }
    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednesday() {
        return Wednesday;
    }
    public void setWednesday(String wednesday) {
        Wednesday = wednesday;
    }

    public String getThursday() {
        return Thursday;
    }
    public void setThursday(String thursday) {
        Thursday = thursday;
    }

    public String getFriday() {
        return Friday;
    }
    public void setFriday(String friday) {
        Friday = friday;
    }

    public String getSaturday() {
        return Saturday;
    }
    public void setSaturday(String saturday) {
        Saturday = saturday;
    }


    @Override
    public String toString() {
        return "CalenderWeekItem{" +
                "Count='" + Count + '\'' +
                ", Sunday='" + Sunday + '\'' +
                ", Monday='" + Monday + '\'' +
                ", Tuesday='" + Tuesday + '\'' +
                ", Wednesday='" + Wednesday + '\'' +
                ", Thursday='" + Thursday + '\'' +
                ", Friday='" + Friday + '\'' +
                ", Saturday='" + Saturday + '\'' +
                '}';
    }
}
