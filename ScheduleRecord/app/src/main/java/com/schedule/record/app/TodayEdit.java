package com.schedule.record.app;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.clock.AlarmSet;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class TodayEdit extends AppCompatActivity {

    @BindView(R.id.editCheckBox1)
    CheckBox editCheckBox1;
    @BindView(R.id.editEditText0)
    EditText editEditText0;
    @BindView(R.id.editEditText1)
    EditText editEditText1;

    @BindView(R.id.editLinearLayout1)
    LinearLayout editLinearLayout1;

    @BindView(R.id.editButton21)
    Button editButton21;
    @BindView(R.id.editButton22)
    Button editButton22;
    @BindView(R.id.editButton23)
    Button editButton23;
    @BindView(R.id.editButton24)
    Button editButton24;
    @BindView(R.id.editButton25)
    Button editButton25;
    @BindView(R.id.editButton26)
    Button editButton26;
    @BindView(R.id.editButton27)
    Button editButton27;

    @BindView(R.id.editRadio1)
    RadioButton editRadio1;
    @BindView(R.id.editRadio2)
    RadioButton editRadio2;
    @BindView(R.id.editRadioGroup)
    RadioGroup editRadioGroup;

    @BindView(R.id.editEditText2)
    EditText editEditText2;

    private TodaySQLite helper;
    private TodaySQLiteUserDao dao;
    private TodaySQLiteUser user;
    private String DBName = "today";
    private int version = 1;

    private String radio2;
    private final Calendar cale1 = Calendar.getInstance();

    private boolean remind;
    private String dayid;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_2today_edit);
        ButterKnife.bind(this);
        layoutFilling();
    }

    @OnClick({R.id.editEditText0, R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editButton25, R.id.editButton26, R.id.editButton27})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editEditText0:
                new TimePickerDialog(TodayEdit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute < 10 && hourOfDay < 10) {
                            radio2 = "0" + hourOfDay + ":0" + minute;
                        } else if (minute < 10) {
                            radio2 = hourOfDay + ":0" + minute;
                        } else if (hourOfDay < 10) {
                            radio2 = "0" + hourOfDay + ":" + minute;
                        } else {
                            radio2 = hourOfDay + ":" + minute;
                        }
                        editEditText0.setText(radio2);
                        //设置闹钟
                        if (!editEditText0.getText().toString().equals("XX:XX") && remind) {
                            int t = hourOfDay + minute;
                            String dayid1 = getInternetTime();
                            int t1 = Integer.parseInt(dayid1.substring(0, 2) + dayid1.substring(3, 5));
                            if (t > t1) {
                                new AlarmSet(TodayEdit.this, hourOfDay, minute, dayid, i).myAlarmSet();
                            }
                        }
                    }
                }, cale1.get(Calendar.HOUR), cale1.get(Calendar.MINUTE), true).show();
                break;
            case R.id.editButton21:
                user.setImportant("a");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case R.id.editButton22:
                user.setImportant("b");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case R.id.editButton23:
                user.setImportant("c");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case R.id.editButton24:
                user.setImportant("d");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case R.id.editButton25:
                user.setImportant("e");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case R.id.editButton26:
                user.setImportant("f");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case R.id.editButton27:
                user.setImportant("g");
                dao.updateAll(user,TodayEdit.this);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
    }

    @Override
    public void onPause() {
        user.setTitle(editEditText1.getText().toString());
        String time = editEditText0.getText().toString();
        user.setTime(time);
        if (editRadio1.isChecked()) {
            user.setRemind(true);
            //删除Item对应的闹钟
            new AlarmSet(this,dayid,i).myAlarmCancel();
        }
        if (editRadio2.isChecked()) {
            user.setRemind(false);
            //设置闹钟
            if (!time.equals("XX:XX") && remind) {
                int t = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
                String dayid1 = getInternetTime();
                int t1 = Integer.parseInt(dayid1.substring(0, 2) + dayid1.substring(3, 5));
                if (t > t1) {
                    new AlarmSet(this, Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)), dayid, i).myAlarmSet();
                }
            }
        }
        if (editCheckBox1.isChecked()) {
            user.setCheckbox(true);
            //删除Item对应的闹钟
            new AlarmSet(this,dayid,i).myAlarmCancel();
        }else {
            user.setCheckbox(false);
//            //设置闹钟
//            if (!time.equals("XX:XX") && remind) {
//                int t = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
//                String dayid1 = getInternetTime();
//                int t1 = Integer.parseInt(dayid1.substring(0, 2) + dayid1.substring(3, 5));
//                if (t > t1) {
//                    new AlarmSet(this, Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)), dayid, i).myAlarmSet();
//                }
//            }
        }
        user.setDiary(editEditText2.getText().toString());
        dao.updateAll(user,TodayEdit.this);
        Toast.makeText(TodayEdit.this, "已保存数据", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @SuppressLint("ResourceType")
    private void layoutFilling() {
        //获取DayItem传递的Dayid
        helper = new TodaySQLite(this, DBName, null, version);
        dao = new TodaySQLiteUserDao(helper);
        Intent intent = getIntent();
        dayid = intent.getStringExtra("dayid");
        i = Integer.parseInt(dayid.substring(22,24)+dayid.substring(25,27)+dayid.substring(28,30));
        //查询Dayid对应数据
        TodaySQLiteUser d = dao.queryBydayid(dayid);
        //设置当前布局填充
        if (d.isCheckbox()) {
            editCheckBox1.setChecked(true);
        } else {
            editCheckBox1.setChecked(false);
        }
        editEditText0.setText(d.getTime());
        editEditText1.setText(d.getTitle());
        editEditText2.setText(d.getDiary());

//        switch (d.getImportant()) {
//            case "a":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
//                break;
//            case "b":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
//                break;
//            case "c":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
//                break;
//            case "d":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
//                break;
//            case "e":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
//                break;
//            case "f":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
//                break;
//            case "g":
//                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
//                break;
//        }

        new ColorImportant(d.getImportant(),editLinearLayout1).LinearLayoutSet();

        remind = d.isRemind();
        if (remind) {
            editRadio1.setChecked(true);
        }else {
            editRadio2.setChecked(true);
        }
        user = d;//获取当前Dayid的数据的内容
    }


    //联网获取当前时间
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String Dayid = timesimple.format(new Date());
        return Dayid;
    }
}
