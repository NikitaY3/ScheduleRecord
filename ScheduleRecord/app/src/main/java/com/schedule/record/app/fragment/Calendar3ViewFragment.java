package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalendarDateAdapter;
import com.schedule.record.app.utils.CalendarDateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Calendar3ViewFragment extends Fragment {

    @BindView(R.id.record_gridView)
    GridView recordGridView;
    @BindView(R.id.record_title)
    TextView recordTitle;
    private Unbinder unbinder;

    private int year;
    private int month;
    public int[][] days = new int[6][7];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_calendar_mode3_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        //初始化日期数据
        initData();

        return view;
    }


    private void initData() {
        year = CalendarDateUtils.getYear();
        month = CalendarDateUtils.getMonth();
    }

    @Override
    public void onResume() {
        super.onResume();

        //初始化组件
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {

        days = CalendarDateUtils.getDayOfMonthFormat(2016, 8);
        //定义adapter
        CalendarDateAdapter dateAdapter = new CalendarDateAdapter(getActivity(), days, year, month);

        recordGridView.setAdapter(dateAdapter);
        recordGridView.setVerticalSpacing(0);
        recordGridView.setHorizontalSpacing(0);
        recordGridView.setEnabled(false);

        recordTitle.setText(year + "年" + month + "月");
    }

//    /**
//     * 下一个月
//     * @return
//     */
//    private int[][] nextMonth() {
//        if (month == 12) {
//            month = 1;
//            year++;
//        } else {
//            month++;
//        }
//        days = CalendarDateUtils.getDayOfMonthFormat(year, month);
//        return days;
//    }
//
//    /**
//     * 上一个月
//     * @return
//     */
//    private int[][] prevMonth() {
//        if (month == 1) {
//            month = 12;
//            year--;
//        } else {
//            month--;
//        }
//        days = CalendarDateUtils.getDayOfMonthFormat(year, month);
//        return days;
//    }
//
//    /**
//     * 设置标题
//     */
//    private void setTile() {
//        title = year + "年" + (month+1) + "月";
//        recordTitle.setText(title);
//    }

//    @OnClick({R.id.record_left, R.id.record_right})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.record_left:
//                days = prevMonth();
//                dateAdapter = new CalendarDateAdapter(getActivity(), days, year, month);
//                recordGridView.setAdapter(dateAdapter);
//                dateAdapter.notifyDataSetChanged();
//                setTile();
//                break;
//            case R.id.record_right:
//                days = nextMonth();
//                dateAdapter = new CalendarDateAdapter(getActivity(), days, year, month);
//                recordGridView.setAdapter(dateAdapter);
//                dateAdapter.notifyDataSetChanged();
//                setTile();
//                break;
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}