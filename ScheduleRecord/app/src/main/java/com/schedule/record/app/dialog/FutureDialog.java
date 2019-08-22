package com.schedule.record.app.dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyFutureAdapter;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class FutureDialog extends Dialog {
    private Context context;
    private LinearLayout myFInputLinearLayout1;
    private ListView calendar1ListView;
    private ArrayAdapter<String> arrayAdapter;
    private List<FutureSQLiteUser> dataList;

    private FutureSQLite helper;
    private String DBName="future";
    private int version=1;
    private String Dayid,Dayidbutton;

    private EditText myFInputEditText1,myFInputEditText2;
    private Button myFInputButton,myFInputButton23;
    private Spinner myFInputButton21,myFInputButton22,myFInputButton24;
    private List<String> button21List,button22List,button24List;

    public String radio2;
    private final Calendar cale1 = Calendar.getInstance();

    private String important,endday,repeat,diary;
    private boolean remind = true;

    public FutureDialog(Context context, ListView calendar1ListView, List<FutureSQLiteUser> dataList) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.calendar1ListView = calendar1ListView;
        this.dataList = dataList;

        updateDiaLog();
    }

    private void updateDiaLog() {
        important = "a";
        endday = "0000-00-00";
        repeat = "everyday";
        remind = true;
        diary = "无";
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_future_inputitem);

        myFInputLinearLayout1 = findViewById(R.id.myFInputLinearLayout1);
        myFInputEditText1 = findViewById(R.id.myFInputEditText1);
        myFInputEditText2 = findViewById(R.id.myFInputEditText2);
        myFInputButton = findViewById(R.id.myFInputButton);
        myFInputButton21 = findViewById(R.id.myFInputButton21);
        myFInputButton22 = findViewById(R.id.myFInputButton22);
        myFInputButton23 = findViewById(R.id.myFInputButton23);
        myFInputButton24 = findViewById(R.id.myFInputButton24);

        //软键盘的弹出和焦点获取
        myFInputEditText2.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },50);

        myFInputEditText1.setText("XX:XX");
        myFInputEditText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<10 && hourOfDay<10){
                            radio2 = "0"+hourOfDay + ":0" + minute;
                        }else if(minute<10) {
                            radio2 = hourOfDay + ":0" + minute;
                        }else if(hourOfDay<10) {
                            radio2 = "0"+hourOfDay + ":" + minute;
                        }else {
                            radio2 = hourOfDay+":"+minute ;
                        }
                        myFInputEditText1.setText(radio2);
                    }
                },cale1.get(Calendar.HOUR_OF_DAY),cale1.get(Calendar.MINUTE),true).show();
            }
        });

        myFInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //延时函数
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!myFInputEditText2.getText().toString().equals("")) {
                            insertDataBase();
                        } else {
                            Toast.makeText(context,"请输入日程标题",Toast.LENGTH_SHORT).show();
                        }
                    }
                },10);
                //延时函数
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myFInputLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                        myFInputEditText1.setText("XX:XX");
                        myFInputEditText2.setText("");
                        updateDiaLog();
                        myFInputButton21.setSelection(0);
                        myFInputButton22.setSelection(0);
                        myFInputButton23.setText("截止日期");
                        myFInputButton24.setSelection(0);
                    }
                },100);
            }
        });
        myFInputButton23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String radio;
                        if ((month+1)<10 && dayOfMonth<10){
                            radio = year+"-0"+(month+1)+"-0"+dayOfMonth;
                        }else if ((month+1)<10){
                            radio = year+"-0"+(month+1)+"-"+dayOfMonth;
                        }else if (dayOfMonth<10){
                            radio = year+"-"+(month+1)+"-0"+dayOfMonth;
                        }else {
                            radio = year+"-"+(month+1)+"-"+dayOfMonth;
                        }
                        String c = getInternetTime();
                        int todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                        int todayint2 = Integer.parseInt(radio.substring(0,4)+ radio.substring(5,7)+radio.substring(8,10));
                        if (todayint2<todayint){
                            Toast.makeText(context,"截止日期无效",Toast.LENGTH_SHORT).show();
                            myFInputButton23.setText("0000-00-00");
                        }
                        else {
                            myFInputButton23.setText(radio);
                            endday = radio;
                        }
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

        String dayTitle = myFInputEditText2.getText().toString();
        String time = myFInputEditText1.getText().toString();

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("myuser",MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid","");
        String dayid = nameid+Dayidbutton;

        FutureSQLiteUser things = new FutureSQLiteUser(dayid,repeat,endday,remind,time,dayTitle,important,diary);

        //数据写入数据库
        helper=new FutureSQLite(context,DBName,null,version);
        helper.getReadableDatabase();
        FutureSQLiteUserDao dao=new FutureSQLiteUserDao(helper);
        dao.insert(things);

        //刷新所有Item
        dataList = new ArrayList<FutureSQLiteUser>();
        helper.getReadableDatabase();
        dataList = (List<FutureSQLiteUser>) dao.quiryAndSetItem();
        final MyFutureAdapter adapter = new MyFutureAdapter(context, dataList);
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
        button22List.add("不重复");
        button22List.add("每天");
        button22List.add("每周");
        button22List.add("每月");

        button24List = new ArrayList<>();
        button24List.add("重要程度");
        button24List.add("等级一");
        button24List.add("等级二");
        button24List.add("等级三");
        button24List.add("等级四");
        button24List.add("等级五");
        button24List.add("等级六");
        button24List.add("等级七");

        MySpinner(button21List,myFInputButton21);
        MySpinner(button22List,myFInputButton22);
        MySpinner(button24List,myFInputButton24);
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
                important = new ColorImportant(s,myFInputLinearLayout1).ImportantSet();
                switch (s) {
                    case "提醒":
                        remind = true;
                        break;
                    case "不提醒":
                        remind = false;
                        break;
                    case "不重复":
                        repeat = "norepeat";
                        break;
                    case "每天":
                        repeat = "everyday";
                        break;
                    case "每周":
                        repeat = "everywee"+"1";
                        break;
                    case "每月":
                        repeat = "everymou"+"1";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
