package com.schedule.record.app.aboutmycalendar;

import android.content.Context;
import android.widget.Toast;

import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.Date;
import java.util.List;

public class FinishDayUtil {

    private int allSchedule,finishSchedule;
    private Date date;
    private DaySQLite helper;
    private static final String DBName = "day_1";
    private int version = 1;
    private Context context;

    private List<DaySQLiteUser> dataList;

    public FinishDayUtil(Context context, java.util.Date date) {
        this.context = context;
        this.date = date;

        CountFinish(context);

    }

    private void CountFinish(Context context) {
        helper = new DaySQLite(context, DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        allSchedule = dao.CountAllBar();
        finishSchedule = dao.CountBar();
        int countBar = dao.CountBar();
        Toast.makeText(context,"计算日程完成情况："+countBar,Toast.LENGTH_SHORT).show();
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
    }
}
