package com.schedule.record.app.aboutmycalendar;

class NumberHelper {
    static String LeftPad_Tow_Zero(int str) {
        java.text.DecimalFormat format = new java.text.DecimalFormat("00");
        return format.format(str);
    }
}