package com.schedule.record.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.schedule.record.app.Mode3CompletionLine;
import com.schedule.record.app.Mode3CompletionScale;
import com.schedule.record.app.Mode3TimeDelayed;
import com.schedule.record.app.R;
import com.schedule.record.app.aboutmycalendar.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Calendar3Fragment extends Fragment {

    @BindView(R.id.mode3Button1)
    Button mode3Button1;
    @BindView(R.id.mode3Button2)
    Button mode3Button2;
    @BindView(R.id.mode3Button3)
    Button mode3Button3;
    @BindView(R.id.mode3Button4)
    Button mode3Button4;
    @BindView(R.id.mode3Button5)
    Button mode3Button5;
    @BindView(R.id.mode3Button7)
    Button mode3Button7;
    @BindView(R.id.mode3Button6)
    Button mode3Button6;
    Unbinder unbinder;
    @BindView(R.id.Mode3Calendar)
    CalendarView Mode3Calendar;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode3, container, false);
        unbinder = ButterKnife.bind(this, view);


        //设置标注日期
        List<Date> markDates = new ArrayList<Date>();
        markDates.add(new Date());
        Mode3Calendar.setMarkDates(markDates);

        //设置点击操作
        Mode3Calendar.setOnCalendarViewListener(new CalendarView.OnCalendarViewListener() {
            @Override
            public void onCalendarItemClick(CalendarView view, Date date) {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
                Toast.makeText(getActivity(), format.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mode3Button1, R.id.mode3Button2, R.id.mode3Button3, R.id.mode3Button4, R.id.mode3Button5, R.id.mode3Button7, R.id.mode3Button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mode3Button1:
                Intent intent1 = new Intent(getActivity(), Mode3CompletionScale.class);
                startActivity(intent1);
                break;
            case R.id.mode3Button2:
                Intent intent2 = new Intent(getActivity(), Mode3CompletionLine.class);
                startActivity(intent2);
                break;
            case R.id.mode3Button3:
                Intent intent3 = new Intent(getActivity(), Mode3TimeDelayed.class);
                startActivity(intent3);
                break;
            case R.id.mode3Button4:
                break;
            case R.id.mode3Button5:
                break;
            case R.id.mode3Button7:
                break;
            case R.id.mode3Button6:
                break;
        }
    }
}
