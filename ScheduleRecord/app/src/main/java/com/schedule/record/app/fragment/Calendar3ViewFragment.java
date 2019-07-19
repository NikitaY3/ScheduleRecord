package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.aboutmycalendar.CalendarUtil;
import com.schedule.record.app.aboutmycalendar.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Calendar3ViewFragment extends Fragment {
    View view;
    @BindView(R.id.mode3Calendar)
    CalendarView mode3Calendar;
    @BindView(R.id.mode3TextView1)
    TextView mode3TextView1;
    Unbinder unbinder;

    Calendar calCalendar;
    CalendarUtil calendarUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode3_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        //设置标注日期
//        List<Date> markDates = new ArrayList<Date>();
//        markDates.add(new Date());
//        mode3Calendar.setMarkDates(markDates);

        //设置点击操作
        mode3Calendar.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener() {
            @Override
            public void onCalendarItemClick(CalendarView view, Date date) {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
                Toast.makeText(getActivity(), format.format(date) , Toast.LENGTH_SHORT).show();

                calCalendar = Calendar.getInstance();
                calCalendar.setTime(date);
                calendarUtil = new CalendarUtil(calCalendar);
                mode3TextView1.setText(calendarUtil.toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        mode3Calendar.setViewFinishDay();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
