package com.schedule.record.app.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculationWeek {

    String time;

    public CalculationWeek(String time) {
        this.time = time;
    }
    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     */
    public String getWeek() {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "0";
        }
        if (wek == 2) {
            Week += "1";
        }
        if (wek == 3) {
            Week += "2";
        }
        if (wek == 4) {
            Week += "3";
        }
        if (wek == 5) {
            Week += "4";
        }
        if (wek == 6) {
            Week += "5";
        }
        if (wek == 7) {
            Week += "6";
        }
        return Week;
    }
}
