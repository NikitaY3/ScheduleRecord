package com.schedule.record.app.aboutmycalendar;

import android.content.Context;

import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.Date;
import java.util.List;

public class FinishDayUtil {

    private int allSchedule,finishSchedule;
    private Date date;
    private FinishSQLite helper;
    private static final String DBName = "today";
    private int version = 1;
    private Context context;

    private List<FinishSQLiteUser> dataList;

    public FinishDayUtil(Context context, java.util.Date date) {
        this.context = context;
        this.date = date;

        CountFinish(context);

    }

    private void CountFinish(Context context) {
        helper = new FinishSQLite(context, DBName, null, version);
        helper.getReadableDatabase();
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper);
//        allSchedule = dao.Count();
//        finishSchedule = dao.CountBar();
//        int countBar = dao.CountBar();
        allSchedule = 1;
        finishSchedule = 2;
        int countBar = 2;
//        Toast.makeText(context,"计算日程完成情况："+countBar,Toast.LENGTH_SHORT).show();
    }

    public int getAllSchedule() {
        return allSchedule;
    }

    public void setAllSchedule(int allSchedule) {
        this.allSchedule = allSchedule;
    }

    public int getFinishSchedule() {
        return finishSchedule;
    }

    public void setFinishSchedule(int finishSchedule) {
        this.finishSchedule = finishSchedule;
    }

    @Override
    public String toString() {
        return "共"+allSchedule+"条日程"+"\n"+finishSchedule+"/"+allSchedule;
//        return "";
    }
}
