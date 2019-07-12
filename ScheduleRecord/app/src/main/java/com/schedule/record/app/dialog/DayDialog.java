package com.schedule.record.app.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;


public class DayDialog extends Dialog {
    private Context mContext;
    private ListView calendar1ListView;
    private ArrayAdapter<String> arrayAdapter;
    private List<DaySQLiteUser> dataList;

    private DaySQLite helper;
    String DBName="day_1";
    int version=1;
    String Dayid,Dayidbutton,Dayidlog;

    private EditText inputItemEditText1,inputItemEditText2;
    private Button inputItemButton;
    private Spinner inputItemButton21,inputItemButton22,inputItemButton23,inputItemButton24;
    private List<String> button21List,button22List,button23List,button24List;

    public DayDialog(Context context, ListView calendar1ListView, List<DaySQLiteUser> dataList) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.calendar1ListView = calendar1ListView;
        this.dataList = dataList;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_inputitem);

        inputItemEditText1 = findViewById(R.id.inputItemEditText1);
        inputItemEditText2 = findViewById(R.id.inputItemEditText2);
        inputItemButton = findViewById(R.id.inputItemButton);
        inputItemButton21 = findViewById(R.id.inputItemButton21);
        inputItemButton22 = findViewById(R.id.inputItemButton22);
        inputItemButton23 = findViewById(R.id.inputItemButton23);
        inputItemButton24 = findViewById(R.id.inputItemButton24);
        //调用获取时间的函数
        Dayidlog = getInternetTime();
        inputItemEditText1.setText(Dayidlog.substring(11,16));
        //软键盘的弹出和焦点获取
        inputItemEditText1.setFocusable(true);
        inputItemEditText1.setFocusableInTouchMode(true);
        inputItemEditText1.requestFocus();
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        inputItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"创建成功",Toast.LENGTH_SHORT).show();
                //延时函数
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Thesday1();
                    }
                },1000);
            }
        });
        SpinnerList();

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);


    }

    private void Thesday1() {
        //确认添加按钮点击后创建Item及将数据写入数据库
        Dayidbutton = getInternetTime();
        Toast.makeText(getContext(),"当前时间为："+Dayidbutton,Toast.LENGTH_SHORT).show();
        //Item适配器的调用及Item的生成
        String dayTitle = String.valueOf(inputItemEditText2.getText());
        DaySQLiteUser things = new DaySQLiteUser(Dayidbutton,false,Dayidbutton,dayTitle,"b","dddd","0000-00-00","this is diary","b");
//        DaySQLiteUser things = new DaySQLiteUser("",false,"","","b","","","","");
        dataList.add(things);
        final CalenderDayAdapter adapter = new CalenderDayAdapter(getContext(), dataList);
        calendar1ListView.setAdapter(adapter);

        //数据写入数据库
        helper=new DaySQLite(getContext(),DBName,null,version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        dao.insert(things);
    }

    private String getInternetTime() {
        //联网获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Dayid = timesimple.format(new Date());
        return Dayid;
    }

    private void SpinnerList() {
        //设置下拉框的列表内容
        button21List = new ArrayList<>();
        button21List.add("是否提醒");
        button21List.add("提醒");
        button21List.add("不提醒");

        button22List = new ArrayList<>();
        button22List.add("重复设置");
        button22List.add("每天");
        button22List.add("每周");
        button22List.add("每月");

        button23List = new ArrayList<>();
        button23List.add("截止日期");
        button23List.add("我的一天");
        button23List.add("我的一周");
        button23List.add("我的一个月");

        button24List = new ArrayList<>();
        button24List.add("重要程度");
        button24List.add("等级一");
        button24List.add("等级二");
        button24List.add("等级三");
        button24List.add("等级四");

        MySpinner(button21List,inputItemButton21);
        MySpinner(button22List,inputItemButton22);
        MySpinner(button23List,inputItemButton23);
        MySpinner(button24List,inputItemButton24);
    }

    private void MySpinner(List<String> teamList,Spinner spinner) {
        //下拉列表函数
        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.main_calendar_item,teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
