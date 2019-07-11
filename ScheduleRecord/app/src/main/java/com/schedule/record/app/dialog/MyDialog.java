package com.schedule.record.app.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
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
import butterknife.OnClick;

public class MyDialog extends Dialog {
    Context mContext;
    private List<CalenderDayItem> dataList;
    private ListView calendar1ListView;
    private ArrayAdapter<String> arrayAdapter;

    private DaySQLite helper;
    String DBName="day_1";
    int version=1;

    private EditText inputItemEditText1,inputItemEditText2;
    private Button inputItemButton;
    private Spinner inputItemButton21,inputItemButton22,inputItemButton23,inputItemButton24;
    private List<String> button21List,button22List,button23List,button24List;

    public MyDialog(Context context,ListView calendar1ListView) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.calendar1ListView = calendar1ListView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_inputitem);
        //准备数据
        dataList = new ArrayList<CalenderDayItem>();

        inputItemEditText1 = findViewById(R.id.inputItemEditText1);
        inputItemEditText2 = findViewById(R.id.inputItemEditText2);
        inputItemButton = findViewById(R.id.inputItemButton);
        inputItemButton21 = findViewById(R.id.inputItemButton21);
        inputItemButton22 = findViewById(R.id.inputItemButton22);
        inputItemButton23 = findViewById(R.id.inputItemButton23);
        inputItemButton24 = findViewById(R.id.inputItemButton24);
        inputItemEditText1.setFocusable(true);
        inputItemEditText1.setFocusableInTouchMode(true);
        inputItemEditText1.requestFocus();
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        inputItemEditText1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        inputItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"创建成功",Toast.LENGTH_SHORT).show();
                Thesday1();

            }
        });
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

        //关闭软键盘
//        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

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

        //联网获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String Dayid = timesimple.format(new Date());
        Toast.makeText(getContext(),"创建时间/主键为："+Dayid,Toast.LENGTH_SHORT).show();

        //Item适配器的调用及Item的生成
        CalenderDayItem things = new CalenderDayItem(Dayid,"This is test text. And it whill have three lines. And it while have suspension points.");
        dataList.add(things);
        final CalenderDayAdapter adapter = new CalenderDayAdapter(getContext(), dataList);
        calendar1ListView.setAdapter(adapter);

        //数据写入数据库
        helper=new DaySQLite(getContext(),DBName,null,version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        dao.insert(new DaySQLiteUser(Dayid,false,Dayid,
                "ddddddddd","12","dddd","0000-00-00","this is diary","test"));
    }


    private void MySpinner(List<String> teamList,Spinner spinner) {
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
