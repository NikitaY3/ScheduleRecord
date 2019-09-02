package com.schedule.record.app.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyInsertAdapter;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class GInsertDialog extends Dialog {
    private Context context;
    private LinearLayout myFInputLinearLayout1;
    private ListView calendar1ListView;
    private List<TodaySQLiteUser> dataList;

    private EditText myInsertEditText1,myInsertEditText2,myInsertEditText3;

    private String radio2;
    private final Calendar cale1 = Calendar.getInstance();
    private String important,diary,nameid;
    private TodaySQLiteUser things;

    public GInsertDialog(Context context, ListView calendar1ListView, List<TodaySQLiteUser> dataList,String nameid) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.calendar1ListView = calendar1ListView;
        this.dataList = dataList;
        this.nameid = nameid;

        updateDiaLog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l2general_insert_item);

        Button myFInputButton = findViewById(R.id.myInsertButton);
        myFInputLinearLayout1 = findViewById(R.id.myInsertLinearLayout1);
        myInsertEditText1 = findViewById(R.id.myInsertEditText1);
        myInsertEditText2 = findViewById(R.id.myInsertEditText2);
        myInsertEditText3 = findViewById(R.id.myInsertEditText3);

        //软键盘的弹出和焦点获取
        myInsertEditText2.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },50);

        myInsertEditText1.setText("XX:XX");
        myInsertEditText1.setOnClickListener(new View.OnClickListener() {
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
                        myInsertEditText1.setText(radio2);
                    }
                },cale1.get(Calendar.HOUR_OF_DAY),cale1.get(Calendar.MINUTE),true).show();
            }
        });

        myFInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断标题是否为空
                if (!myInsertEditText2.getText().toString().equals("")) {
                    insertDataBase();
                } else {
                    Toast.makeText(context,"请输入日程标题",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        String dayTitle = myInsertEditText2.getText().toString();
        String time = myInsertEditText1.getText().toString();
        String dayid = nameid + dayidbutton;
        diary = myInsertEditText3.getText().toString();

        things = new TodaySQLiteUser(dayid,false,true,time,dayTitle,important,diary,dayidbutton.substring(0,10));

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

    private void updateDiaLog() {
        important = "a";
        diary = "无";
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            int version = 1;
            String DBName = "today";
                switch (msg.what) {
                    case 21:

                        //数据写入数据库
                        TodaySQLite helper = new TodaySQLite(context, DBName, null, version);
                        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper);
                        //不给自己设置闹钟的插入
                        dao.insert(things);

                        //刷新所有Item
                        dataList = new ArrayList<>();
                        dataList = dao.quiryAndSetInsertItem(nameid);
                        final MyInsertAdapter adapter = new MyInsertAdapter(context, dataList);
                        calendar1ListView.setAdapter(adapter);

                        //重置输入
                        myFInputLinearLayout1.setBackgroundResource(R.drawable.abaa_item_important1);
                        myInsertEditText1.setText("XX:XX");
                        myInsertEditText2.setText("");
                        myInsertEditText3.setText("");
                        updateDiaLog();

                        break;
            }
        }
    };
}
