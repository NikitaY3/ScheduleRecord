package com.schedule.record.app.dialog;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.DaySQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;


public class DayDialog extends Dialog {
    private Context context;
    private LinearLayout inputItemLinearLayout1;
    private ListView calendar1ListView;
    private ArrayAdapter<String> arrayAdapter;
    private List<DaySQLiteUser> dataList;

    private DaySQLite helper;
    private String DBName="day_1";
    private int version=1;
    private String Dayid,Dayidbutton;

    private EditText inputItemEditText1,inputItemEditText2;
    private Button inputItemButton,inputItemButton23;
    private Spinner inputItemButton21,inputItemButton22,inputItemButton24;
    private List<String> button21List,button22List,button24List;

    public String radio2;
    private final Calendar cale1 = Calendar.getInstance();
    private ProgressBar mode1ProgressBar;


    private String important,endday,repeat,diary,picture;
    private boolean remind = true;

    public DayDialog(Context context, ListView calendar1ListView, List<DaySQLiteUser> dataList, ProgressBar mode1ProgressBar) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.calendar1ListView = calendar1ListView;
        this.dataList = dataList;
        this.mode1ProgressBar = mode1ProgressBar;

        updateDiaLog();
    }

    private void updateDiaLog() {
        important = "a";
        endday = "0000-00-00";
        repeat = "everyday";
        remind = true;
        diary = "this is diary";
        picture = "picture";
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_inputitem);

        inputItemLinearLayout1 = findViewById(R.id.inputItemLinearLayout1);
        inputItemEditText1 = findViewById(R.id.inputItemEditText1);
        inputItemEditText2 = findViewById(R.id.inputItemEditText2);
        inputItemButton = findViewById(R.id.inputItemButton);
        inputItemButton21 = findViewById(R.id.inputItemButton21);
        inputItemButton22 = findViewById(R.id.inputItemButton22);
        inputItemButton23 = findViewById(R.id.inputItemButton23);
        inputItemButton24 = findViewById(R.id.inputItemButton24);

        inputItemEditText1.setText("XX:XX");
        inputItemEditText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<9 && hourOfDay<9){
                            radio2 = "0"+hourOfDay + ":0" + minute;
                        }else if(minute<9) {
                            radio2 = hourOfDay + ":0" + minute;
                        }else if(hourOfDay<9) {
                            radio2 = "0"+hourOfDay + ":" + minute;
                        }else {
                            radio2 = hourOfDay+":"+minute ;
                        }
                        inputItemEditText1.setText(radio2);
                    }
                },cale1.get(Calendar.HOUR),cale1.get(Calendar.MINUTE),true).show();
            }
        });
        //软键盘的弹出和焦点获取
        inputItemEditText2.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },50);

        inputItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                inputItemEditText1.setText("XX:XX");
                inputItemEditText2.setText("");
                updateDiaLog();
                inputItemButton21.setSelection(0);
                inputItemButton22.setSelection(0);
                inputItemButton23.setText("截止日期");
                inputItemButton24.setSelection(0);
                //延时函数
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        insertDataBase();
                    }
                },100);
            }
        });
        inputItemButton23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String radio = year+"."+(month+1)+"."+dayOfMonth;
                        inputItemButton23.setText(radio);
                        endday = radio;
                    }
                },cale1.get(Calendar.YEAR),cale1.get(Calendar.MONTH),cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        SpinnerList();

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
    }

    private void insertDataBase() {
        //确认添加按钮点击后创建Item及将数据写入数据库
        Dayidbutton = getInternetTime();
        Toast.makeText(context,"当前时间为："+Dayidbutton,Toast.LENGTH_SHORT).show();

        //Item适配器的调用及Item的生成
        String dayTitle = inputItemEditText2.getText().toString();
        String time = inputItemEditText1.getText().toString();

        DaySQLiteUser things = new DaySQLiteUser(Dayidbutton,false,remind,time,dayTitle,important,repeat,endday,diary,picture);

        //数据写入数据库
        helper=new DaySQLite(context,DBName,null,version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
        dao.insert(things);

        //刷新所有Item
        dataList = new ArrayList<DaySQLiteUser>();
        helper=new DaySQLite(context,DBName,null,version);
        helper.getReadableDatabase();
        dataList = (List<DaySQLiteUser>) dao.quiryAndSetItem();
        final CalenderDayAdapter adapter = new CalenderDayAdapter(context, dataList,mode1ProgressBar);
        new Mode1ProgressBar(dao.CountBar(),dataList.size(),mode1ProgressBar);
        calendar1ListView.setAdapter(adapter);

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

        button24List = new ArrayList<>();
        button24List.add("重要程度");
        button24List.add("等级一");
        button24List.add("等级二");
        button24List.add("等级三");
        button24List.add("等级四");

        MySpinner(button21List,inputItemButton21);
        MySpinner(button22List,inputItemButton22);
        MySpinner(button24List,inputItemButton24);
    }

    private void MySpinner(List<String> teamList,Spinner spinner) {
        //下拉列表函数
        arrayAdapter = new ArrayAdapter<String>(context,R.layout.main_calendar_item,teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String s=((TextView)view).getText().toString();
                switch (s) {
                    case "提醒":
                        remind = true;
                        break;
                    case "不提醒":
                        remind = false;
                        break;
                    case "每天":
                        repeat = "everyday";
                        break;
                    case "每周":
                        repeat = "everywee"+"每周一";
                        break;
                    case "每月":
                        repeat = "everymou"+"每月1日";
                        break;
                    case "等级一":
                        important = "a";
                        inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                        break;
                    case "等级二":
                        important = "b";
                        inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                        break;
                    case "等级三":
                        important = "c";
                        inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                        break;
                    case "等级四":
                        important = "d";
                        inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
