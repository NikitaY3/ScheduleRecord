package com.schedule.record.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.CalenderDayItem;

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
    private List<CalenderDayItem> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar, container, false);

        Adapter();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void Adapter() {

        //1获取Listview
        calendarListView = view.findViewById(R.id.calendarListView);
        //2准备数据
        dataList = new ArrayList<CalenderDayItem>();
        //4文字资源
        String[] timeList = {"12:00", "12:01", "12:02", "12:03", "12:04"};
        String[] newsList = {"news1oneoneone", "news2twotwotwo", "news3threethreethree", "news4fourfourfour", "news5fivefivefive"};
        for (int i = 0; i < timeList.length; i++) {
            CalenderDayItem things = new CalenderDayItem(timeList[i], newsList[i]);
            dataList.add(things);
        }
        //5创建适配器
        final CalenderDayAdapter adapter = new CalenderDayAdapter(getActivity(), dataList);
        //6ListView关联适配器
        calendarListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.calendarButton1, R.id.calendarButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calendarButton1:
                break;
            case R.id.calendarButton2:
                break;
        }
    }
}
