package com.schedule.record.app.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class TodayDialog extends Dialog {

    private Context context;
    private LinearLayout inputItemLinearLayout1;
    private ListView calendar1ListView;
    private List<TodaySQLiteUser> dataList;

    private EditText inputItemEditText1,inputItemEditText2;
    private Spinner inputItemButton21,inputItemButton24;

    private String radio,important,diary;
    private final Calendar cale1 = Calendar.getInstance();
    private ProgressBar mode1ProgressBar;

    private boolean remind = true;
    private TodaySQLiteUser things;

    public TodayDialog(Context context, ListView calendar1ListView, List<TodaySQLiteUser> dataList, ProgressBar mode1ProgressBar) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.calendar1ListView = calendar1ListView;
        this.dataList = dataList;
        this.mode1ProgressBar = mode1ProgressBar;

        updateDiaLog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_inputitem);

        Button inputItemButton = findViewById(R.id.inputItemButton);
        inputItemLinearLayout1 = findViewById(R.id.inputItemLinearLayout1);
        inputItemEditText1 = findViewById(R.id.inputItemEditText1);
        inputItemEditText2 = findViewById(R.id.inputItemEditText2);
        inputItemButton21 = findViewById(R.id.inputItemButton21);
        inputItemButton24 = findViewById(R.id.inputItemButton24);

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

        inputItemEditText1.setText("XX:XX");
        inputItemEditText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<10 && hourOfDay<10){
                            radio = "0"+hourOfDay + ":0" + minute;
                        }else if(minute<10) {
                            radio = hourOfDay + ":0" + minute;
                        }else if(hourOfDay<10) {
                            radio = "0"+hourOfDay + ":" + minute;
                        }else {
                            radio = hourOfDay+":"+minute ;
                        }
                        inputItemEditText1.setText(radio);
                    }
                },cale1.get(Calendar.HOUR_OF_DAY),cale1.get(Calendar.MINUTE),true).show();
            }
        });

        inputItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断标题是否为空
                if (!inputItemEditText2.getText().toString().equals("")) {
                    insertDataBase();
                } else {
                    Toast.makeText(context,"请输入日程标题",Toast.LENGTH_SHORT).show();
                }
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

    //确认添加按钮点击后创建Item及将数据写入数据库
    private void insertDataBase() {
        String dayidbutton = getInternetTime();
        String dayTitle = inputItemEditText2.getText().toString();
        String time = inputItemEditText1.getText().toString();

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("myuser",MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid","");
        String dayid = nameid + dayidbutton;

        things = new TodaySQLiteUser(dayid,false,remind,time,dayTitle,important,diary, dayidbutton.substring(0,10));

        //数据上传到云端
        PostFunctions postFunctions = new PostFunctions();
        postFunctions.SaveTodayPost(things,uiHandler);

    }

    //联网获取当前时间
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    //设置下拉框的列表内容
    private void SpinnerList() {
        List<String> button21List = new ArrayList<>();
        button21List.add("提醒");
        button21List.add("不提醒");

        List<String> button24List = new ArrayList<>();
        button24List.add("重要程度");
        button24List.add("等级一");
        button24List.add("等级二");
        button24List.add("等级三");
        button24List.add("等级四");
        button24List.add("等级五");
        button24List.add("等级六");
        button24List.add("等级七");

        MySpinner(button21List,inputItemButton21);
        MySpinner(button24List,inputItemButton24);
    }

    private void MySpinner(List<String> teamList,Spinner spinner) {
        //下拉列表函数
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.main_calendar_item, teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String s=((TextView)view).getText().toString();
                important = new ColorImportant(s,inputItemLinearLayout1).ImportantSet();
                switch (s) {
                    case "提醒":
                        remind = true;
                        break;
                    case "不提醒":
                        remind = false;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateDiaLog() {
        important = "a";
        remind = true;
        diary = "无";
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            String DBName = "today";
            int version = 1;
            switch (msg.what) {
                case 21:

                    //取得登录用户的ID
                    SharedPreferences sharedPreferences;
                    sharedPreferences = context.getSharedPreferences("myuser", MODE_PRIVATE);
                    String nameid = sharedPreferences.getString("nameid", "");

                    //数据写入数据库
                    TodaySQLite helper = new TodaySQLite(context, DBName, null, version);
                    helper.getReadableDatabase();
                    TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
                    dao.insert(things,context);


                    //刷新所有Item
                    dataList = new ArrayList<>();
                    dataList = dao.quiryAndSetItem(nameid);
                    final CalenderDayAdapter adapter = new CalenderDayAdapter(context, dataList,mode1ProgressBar);
                    new Mode1ProgressBar(dao.CountBar(nameid),dataList.size(),mode1ProgressBar);
                    calendar1ListView.setAdapter(adapter);

                    //重置输入
                    inputItemLinearLayout1.setBackgroundResource(R.drawable.abaa_item_important1);
                    inputItemEditText1.setText("XX:XX");
                    inputItemEditText2.setText("");
                    updateDiaLog();
                    inputItemButton21.setSelection(0);
                    inputItemButton24.setSelection(0);

                    break;
            }
        }
    };
}
