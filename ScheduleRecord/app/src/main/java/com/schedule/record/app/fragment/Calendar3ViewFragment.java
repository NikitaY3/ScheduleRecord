package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalendarDateAdapter;
import com.schedule.record.app.utils.CalendarDateUtils;

public class Calendar3ViewFragment extends AppCompatActivity implements View.OnClickListener {
    private GridView record_gridView;//定义gridView
    private CalendarDateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//初始化日期数据
        initData();
//初始化组件
        initView();
//组件点击监听事件
        initEvent();
    }

    private void initData() {
        year = CalendarDateUtils.getYear();
        month = CalendarDateUtils.getMonth();
    }

    private void initEvent() {
        record_left.setOnClickListener(this);
        record_right.setOnClickListener(this);
    }

    private void initView() {
/**
 * 以下是初始化GridView
 */
        record_gridView = (GridView) findViewById(R.id.record_gridView);
        days = CalendarDateUtils.getDayOfMonthFormat(2016, 8);
        dateAdapter = new CalendarDateAdapter(this, days, year, month);//传入当前月的年
        record_gridView.setAdapter(dateAdapter);
        record_gridView.setVerticalSpacing(60);
        record_gridView.setEnabled(false);
/**
 * 以下是初始化其他组件
 */
        record_left = (ImageView) findViewById(R.id.record_left);
        record_right = (ImageView) findViewById(R.id.record_right);
        record_title = (TextView) findViewById(R.id.record_title);
    }


    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = CalendarDateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = CalendarDateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 设置标题
     */
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_left:
                days = prevMonth();
                dateAdapter = new CalendarDateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
            case R.id.record_right:
                days = nextMonth();
                dateAdapter = new CalendarDateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
        }
    }
}



//extends Fragment {
//    View view;
//
//    @BindView(R.id.mode3TextView1)
//    TextView mode3TextView1;
//    Unbinder unbinder;
//
//    Calendar calCalendar;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.main_calendar_mode3_view, container, false);
//        unbinder = ButterKnife.bind(this, view);
//
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//}
