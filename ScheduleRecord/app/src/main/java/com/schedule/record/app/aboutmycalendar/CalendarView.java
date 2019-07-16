package com.schedule.record.app.aboutmycalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * 日历控件，支持旧历、节气、日期标注、点击操作 （参考网络上的日历控件改写）
 *
 * @author wangcccong
 * @version 1.406 create at: Mon, 03 Sep. 2014
 * update at: Mon, 23 Sep. 2014
 * 新增日期标注和点击操作
 */
public class CalendarView extends LinearLayout implements OnTouchListener, AnimationListener, OnGestureListener {

    // 点击日历
    public interface OnCalendarViewListener {
        void onCalendarItemClick(CalendarView view, Date date);
    }

    /** 顶部控件所占高度 */
    private final static int TOP_HEIGHT = 40;
    /** 日历item中默认id从0xff0000开始 */
    private final static int DEFAULT_ID = 0xff0000;

    // 判断手势用
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    // 屏幕宽度和高度
    private int screenWidth;

    // 动画
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private ViewFlipper viewFlipper;
    private GestureDetector mGesture = null;

    /** 上一月 */
    private GridView gView1;
    /** 当月 */
    private GridView gView2;
    /** 下一月 */
    private GridView gView3;

    boolean bIsSelection = false;// 是否是选择事件发生
    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历
    private CalendarGridViewAdapter gAdapter;
    private CalendarGridViewAdapter gAdapter1;
    private CalendarGridViewAdapter gAdapter3;

    private LinearLayout mMainLayout;
    private TextView mTitle; // 显示年月

    private int iMonthViewCurrentMonth = 0; // 当前视图月
    private int iMonthViewCurrentYear = 0; // 当前视图年

    private static final int caltitleLayoutID = 66; // title布局ID
    private static final int calLayoutID = 55; // 日历布局ID
    private Context mContext;

    /** 标注日期 */
    private final List<Date> markDates;

    private OnCalendarViewListener mListener;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        markDates = new ArrayList<Date>();
        init();
    }
//     初始化相关工作
    protected void init() {
        // 得到屏幕的宽度
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        // 滑动的动画
        slideLeftIn = new TranslateAnimation(screenWidth, 0, 0, 0);
        slideLeftIn.setDuration(400);
        slideLeftIn.setAnimationListener(this);
        slideLeftOut = new TranslateAnimation(0, -screenWidth, 0, 0);
        slideLeftOut.setDuration(400);
        slideLeftOut.setAnimationListener(this);
        slideRightIn = new TranslateAnimation(-screenWidth, 0, 0, 0);
        slideRightIn.setDuration(400);
        slideRightIn.setAnimationListener(this);
        slideRightOut = new TranslateAnimation(0, screenWidth, 0, 0);
        slideRightOut.setDuration(400);
        slideRightOut.setAnimationListener(this);

        // 手势操作
        mGesture = new GestureDetector(mContext, this);

        // 获取到当前日期
        UpdateStartDateForMonth();
        // 绘制界面
        setOrientation(LinearLayout.HORIZONTAL);
        mMainLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams main_params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mMainLayout.setLayoutParams(main_params);
        mMainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mMainLayout.setOrientation(LinearLayout.VERTICAL);
        addView(mMainLayout);

        // 顶部控件
        generateTopView();

        // 中间显示星期
        generateWeekGirdView();

        // 底部显示日历
        viewFlipper = new ViewFlipper(mContext);
        RelativeLayout.LayoutParams fliper_params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        fliper_params.addRule(RelativeLayout.BELOW, caltitleLayoutID);
        mMainLayout.addView(viewFlipper, fliper_params);
        generateClaendarGirdView();

        // 最下方的一条线条
        LinearLayout br = new LinearLayout(mContext);
        br.setBackgroundColor(Color.argb(0xff, 0xc2, 0xa5, 0x3d));
        LinearLayout.LayoutParams params_br = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, 3);
        mMainLayout.addView(br, params_br);
    }

    /** 生成顶部控件 */
    @SuppressWarnings("deprecation")
    private void generateTopView() {
        // 顶部显示上一个下一个，以及当前年月
        RelativeLayout top = new RelativeLayout(mContext);
        mTitle = new TextView(mContext);

        // 左方按钮
        ImageButton mLeftView = new ImageButton(mContext);
//        top.addView(mLeftView);

        // 右方按钮
        ImageButton mRightView = new ImageButton(mContext);
//        mRightView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewFlipper.setInAnimation(slideLeftIn);
//                viewFlipper.setOutAnimation(slideLeftOut);
//                viewFlipper.showNext();
//                setNextViewItem();
//            }
//        });
//        top.addView(mRightView);
    }

    /** 生成中间显示week */
    private void generateWeekGirdView() {
        GridView gridView = new GridView(mContext);
    }

    /** 生成底部日历 */
    private void generateClaendarGirdView() {
        Calendar tempSelected1 = Calendar.getInstance(); // 临时
        Calendar tempSelected2 = Calendar.getInstance(); // 临时
        Calendar tempSelected3 = Calendar.getInstance(); // 临时
        tempSelected1.setTime(calStartDate.getTime());
        tempSelected2.setTime(calStartDate.getTime());
        tempSelected3.setTime(calStartDate.getTime());

        gView1 = new CalendarGridView(mContext);
        tempSelected1.add(Calendar.MONTH, -1);
        gAdapter1 = new CalendarGridViewAdapter(mContext, tempSelected1, markDates);
        gView1.setAdapter(gAdapter1);// 设置菜单Adapter
        gView1.setId(calLayoutID);

        gView2 = new CalendarGridView(mContext);
        gAdapter = new CalendarGridViewAdapter(mContext, tempSelected2, markDates);
        gView2.setAdapter(gAdapter);// 设置菜单Adapter
        gView2.setId(calLayoutID);

        gView3 = new CalendarGridView(mContext);
        tempSelected3.add(Calendar.MONTH, 1);
        gAdapter3 = new CalendarGridViewAdapter(mContext, tempSelected3, markDates);
        gView3.setAdapter(gAdapter3);// 设置菜单Adapter
        gView3.setId(calLayoutID);

        gView2.setOnTouchListener(this);
        gView1.setOnTouchListener(this);
        gView3.setOnTouchListener(this);

        if (viewFlipper.getChildCount() != 0) {
            viewFlipper.removeAllViews();
        }

        viewFlipper.addView(gView2);
        viewFlipper.addView(gView3);
        viewFlipper.addView(gView1);

        String title = calStartDate.get(Calendar.YEAR)
                + "年"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                .get(Calendar.MONTH) + 1) + "月";
        mTitle.setText(title);
    }

    // 上一个月
    private void setPrevViewItem() {
        iMonthViewCurrentMonth--;// 当前选择月--
        // 如果当前月为负数的话显示上一年
        if (iMonthViewCurrentMonth == -1) {
            iMonthViewCurrentMonth = 11;
            iMonthViewCurrentYear--;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1); // 设置日为当月1日
        calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth); // 设置月
        calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear); // 设置年
    }

    // 下一个月
    private void setNextViewItem() {
        iMonthViewCurrentMonth++;
        if (iMonthViewCurrentMonth == 12) {
            iMonthViewCurrentMonth = 0;
            iMonthViewCurrentYear++;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1);
        calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
        calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
    }

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
        iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年

        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        if (iFirstDayOfWeek == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iFirstDayOfWeek == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
    }

    /**
     * 设置标注的日期
     */
    public void setMarkDates(List<Date> markDates) {
        this.markDates.clear();
        this.markDates.addAll(markDates);
        gAdapter.notifyDataSetChanged();
        gAdapter1.notifyDataSetChanged();
        gAdapter3.notifyDataSetChanged();
    }

    /**
     * 设置点击日历监听
     */
    public void setOnCalendarViewListener(OnCalendarViewListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                viewFlipper.setInAnimation(slideLeftIn);
                viewFlipper.setOutAnimation(slideLeftOut);
                viewFlipper.showNext();
                setNextViewItem();
                return true;

            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                viewFlipper.setInAnimation(slideRightIn);
                viewFlipper.setOutAnimation(slideRightOut);
                viewFlipper.showPrevious();
                setPrevViewItem();
                return true;

            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // 得到当前选中的是第几个单元格
        int pos = gView2.pointToPosition((int) e.getX(), (int) e.getY());
        LinearLayout txtDay = (LinearLayout) gView2.findViewById(pos + DEFAULT_ID);
        if (txtDay != null) {
            if (txtDay.getTag() != null) {
                Date date = (Date) txtDay.getTag();
                calSelected.setTime(date);

                gAdapter.setSelectedDate(calSelected);
                gAdapter.notifyDataSetChanged();

                gAdapter1.setSelectedDate(calSelected);
                gAdapter1.notifyDataSetChanged();

                gAdapter3.setSelectedDate(calSelected);
                gAdapter3.notifyDataSetChanged();
                if (mListener != null) {
                    mListener.onCalendarItemClick(this, date);
                }
            }
        }
        return false;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        generateClaendarGirdView();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }
}