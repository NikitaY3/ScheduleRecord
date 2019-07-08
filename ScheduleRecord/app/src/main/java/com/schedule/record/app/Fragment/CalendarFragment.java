package com.schedule.record.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.CalenderDayItem;
import com.schedule.record.app.function.FragmentCalendarController;
import com.schedule.record.app.function.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CalendarFragment extends Fragment {

    @BindView(R.id.calenderTextView1)
    TextView calenderTextView1;
    @BindView(R.id.calendarButton1)
    Button calendarButton1;
    @BindView(R.id.calendarButton2)
    Button calendarButton2;

    Unbinder unbinder;

    private View view;
    private ListView calendarListView;
    private List<String> teamList;
    private Spinner calendarSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private List<CalenderDayItem> dataList;

    private FragmentCalendarController controller;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);

//        Adapter();

        controller = FragmentCalendarController.getInstance(this, R.id.calenderFrameLayout);
        controller.showFragment(0);

//        teamList = new ArrayList<>();
//        teamList.add("罗马");
//        teamList.add("那不勒斯");
//        teamList.add("国际米兰");
//        teamList.add("AC米兰");
//        calendarSpinner = view.findViewById(R.id.calendarSpinner);
//        arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.main_calendar_item,teamList);
        //设置下拉列表的风格
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
//        calendarSpinner.setAdapter(arrayAdapter);
//        //设置点击事件
//        calendarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                controller.showFragment(1);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        return view;
    }



    @OnClick({R.id.calendarButton1, R.id.calendarButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calendarButton1:
                controller.showFragment(1);
                break;
            case R.id.calendarButton2:
                controller.showFragment(2);
                break;
        }
    }
}
