package com.schedule.record.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.dialog.MyDialog;
import com.schedule.record.app.function.CalenderDayItem;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class Calendar1Fragment extends Fragment {

    @BindView(R.id.calendar1Button2)
    Button calendar1Button2;
    Unbinder unbinder;

    private View view;
    private ListView calendar1ListView;
    private List<CalenderDayItem> dataList;

    private DaySQLite helper;
    String DBName="day_1";
    int version=1;

    AlertDialog.Builder frame1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode1, container, false);
        unbinder = ButterKnife.bind(this, view);

        Adapter();

        return view;
    }

    private void Adapter() {
        calendar1ListView = view.findViewById(R.id.calendar1ListView);
        //准备数据
        dataList = new ArrayList<CalenderDayItem>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.calendar1Button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calendar1Button2:

//                dayConfirmationDialogs();
                MyDialog one = new MyDialog(getActivity(),calendar1ListView);
                one.show();
//                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                Objects.requireNonNull(imm).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                Thesday1();

                break;
        }
    }
    private void Thesday1() {
        //联网获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String Dayid = timesimple.format(new Date());
        Toast.makeText(getActivity(),"创建时间/主键为："+Dayid,Toast.LENGTH_SHORT).show();

        //Item适配器的调用及Item的生成
        CalenderDayItem things = new CalenderDayItem(Dayid,"This is test text. And it whill have three lines. And it while have suspension points.");
        dataList.add(things);
        final CalenderDayAdapter adapter = new CalenderDayAdapter(getActivity(), dataList);
        calendar1ListView.setAdapter(adapter);

        //数据写入数据库
        helper=new DaySQLite(getActivity(),DBName,null,version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        dao.insert(new DaySQLiteUser(Dayid,false,Dayid,
                "ddddddddd","12","dddd","0000-00-00","this is diary","test"));
    }

    //弹框函数
    private void dayConfirmationDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getActivity(), R.layout.main_calendar_mode1_inputitem, null);   // 账号、密码的布局文件，自定义
        builder.setIcon(R.mipmap.ic_launcher);//设置对话框icon

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        Window dialogWindow = dialog.getWindow();//获取window对象
        dialogWindow.setGravity(Gravity.BOTTOM);//设置对话框位置
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().getDecorView().setPadding(0,0,0,0);
        dialogWindow.setAttributes(layoutParams);
//        dialogWindow.getDecorView().setPadding(0,0,0,0);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.horizontalMargin = 0;
//        lp.verticalMargin = 0;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialogWindow.setAttributes(lp);
        //dialogWindow.setWindowAnimations(R.style.share_animation);//设置动画   见（4）资源文件
    }
}
