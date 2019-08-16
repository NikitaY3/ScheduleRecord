package com.schedule.record.app;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public
class MyFutureEdit extends AppCompatActivity {

    @BindView(R.id.mfEditEditText0)
    EditText mfEditEditText0;
    @BindView(R.id.mfEditEditText1)
    EditText mfEditEditText1;

    @BindView(R.id.mfEditLinearLayout1)
    LinearLayout mfEditLinearLayout1;

    @BindView(R.id.mfEditButton21)
    Button mfEditButton21;
    @BindView(R.id.mfEditButton22)
    Button mfEditButton22;
    @BindView(R.id.mfEditButton23)
    Button mfEditButton23;
    @BindView(R.id.mfEditButton24)
    Button mfEditButton24;
    @BindView(R.id.mfEditButton25)
    Button mfEditButton25;
    @BindView(R.id.mfEditButton26)
    Button mfEditButton26;
    @BindView(R.id.mfEditButton27)
    Button mfEditButton27;

    @BindView(R.id.mfEditRadio1)
    RadioButton mfEditRadio1;
    @BindView(R.id.mfEditRadio2)
    RadioButton mfEditRadio2;
    @BindView(R.id.mfEditRadioGroup)
    RadioGroup mfEditRadioGroup;

    @BindView(R.id.mfEditRadio41)
    RadioButton mfEditRadio41;
    @BindView(R.id.mfEditRadio42)
    RadioButton mfEditRadio42;
    @BindView(R.id.mfEditRadio43)
    RadioButton mfEditRadio43;
    @BindView(R.id.mfEditRadio44)
    RadioButton mfEditRadio44;
    @BindView(R.id.mfEditRadioGroup4)
    RadioGroup mfEditRadioGroup4;

    @BindView(R.id.mfEditweekItemButton0)
    CheckBox mfEditweekItemButton0;
    @BindView(R.id.mfEditweekItemButton1)
    CheckBox mfEditweekItemButton1;
    @BindView(R.id.mfEditweekItemButton2)
    CheckBox mfEditweekItemButton2;
    @BindView(R.id.mfEditweekItemButton3)
    CheckBox mfEditweekItemButton3;
    @BindView(R.id.mfEditweekItemButton4)
    CheckBox mfEditweekItemButton4;
    @BindView(R.id.mfEditweekItemButton5)
    CheckBox mfEditweekItemButton5;
    @BindView(R.id.mfEditweekItemButton6)
    CheckBox mfEditweekItemButton6;
    @BindView(R.id.mfEditLinearLayout41)
    LinearLayout mfEditLinearLayout41;

    @BindView(R.id.mfEditButton51)
    TextView mfEditButton51;

    @BindView(R.id.mfEditEditText2)
    EditText mfEditEditText2;

    int fr = 1;

    private FutureSQLite helper;
    FutureSQLiteUserDao dao;
    FutureSQLiteUser user;
    String DBName = "future";
    int version = 1;

    public String radio2, radio3;
    final Calendar cale1 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1future_edit);
        ButterKnife.bind(this);
        layoutFilling();
        onRadioGroupChecked();

    }

    private void onRadioGroupChecked() {
        mfEditRadioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mfEditRadio41:
                        if (fr == 1) {
                            mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        break;
                    case R.id.mfEditRadio42:
                        mfEditRadio42.setText("每周");
                        fr = 1;
                        mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                        break;
                    case R.id.mfEditRadio43:
                        if (fr == 1) {
                            mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        radio3 = "每月1日";
                        mfEditRadio43.setText(radio3);
                        break;
                    case R.id.mfEditRadio44:
                        if (fr == 1) {
                            mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.mfEditEditText0, R.id.mfEditButton21, R.id.mfEditButton22, R.id.mfEditButton23, R.id.mfEditButton24,R.id.mfEditButton25,R.id.mfEditButton26,R.id.mfEditButton27, R.id.mfEditButton51})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mfEditEditText0:
                new TimePickerDialog(MyFutureEdit.this, new TimePickerDialog.OnTimeSetListener() {
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
                        mfEditEditText0.setText(radio2);
                    }
                }, cale1.get(Calendar.HOUR), cale1.get(Calendar.MINUTE), true).show();
                break;
            case R.id.mfEditButton21:
                user.setImportant("a");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case R.id.mfEditButton22:
                user.setImportant("b");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case R.id.mfEditButton23:
                user.setImportant("c");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case R.id.mfEditButton24:
                user.setImportant("d");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case R.id.mfEditButton25:
                user.setImportant("e");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case R.id.mfEditButton26:
                user.setImportant("f");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case R.id.mfEditButton27:
                user.setImportant("g");
                dao.updateAll(user);
                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
            case R.id.mfEditButton51:
                new DatePickerDialog(MyFutureEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String radio;
                        if ((month + 1) < 10 && dayOfMonth < 10) {
                            radio = year + "-0" + (month + 1) + "-0" + dayOfMonth;
                        } else if ((month + 1) < 10) {
                            radio = year + "-0" + (month + 1) + "-" + dayOfMonth;
                        } else if (dayOfMonth < 10) {
                            radio = year + "-" + (month + 1) + "-0" + dayOfMonth;
                        } else {
                            radio = year + "-" + (month + 1) + "-" + dayOfMonth;
                        }
                        mfEditButton51.setText(radio);
                    }
                }, cale1.get(Calendar.YEAR), cale1.get(Calendar.MONTH), cale1.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onPause() {
        user.setTitle(mfEditEditText1.getText().toString());
        if (mfEditRadio41.isChecked()) {
            user.setRepeat("everyday");
        }
        if (mfEditRadio42.isChecked()) {
            String a = getWeekChoice();
            user.setRepeat("everywee" + a);
        }
        if (mfEditRadio43.isChecked()) {
            user.setRepeat("everymou" + "1");
        }
        if (mfEditRadio44.isChecked()) {
            user.setRepeat("norepeat");
        }
        user.setTime(mfEditEditText0.getText().toString());
        user.setEndday(mfEditButton51.getText().toString());
        user.setDiary(mfEditEditText2.getText().toString());
        dao.updateAll(user);
        Toast.makeText(MyFutureEdit.this, "已保存数据", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @SuppressLint("ResourceType")
    private void layoutFilling() {
        //获取DayItem传递的Dayid
        helper = new FutureSQLite(this, DBName, null, version);
        helper.getReadableDatabase();
        dao = new FutureSQLiteUserDao(helper);
        Intent intent = getIntent();
        String dayid = intent.getStringExtra("dayid");
        //查询Dayid对应数据
        FutureSQLiteUser d = dao.queryBydayid(dayid);
        //设置当前布局填充
        mfEditEditText0.setText(d.getTime());
        mfEditEditText1.setText(d.getTitle());
        mfEditEditText2.setText(d.getDiary());
//        switch (d.getImportant()) {
//            case "a":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
//                break;
//            case "b":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
//                break;
//            case "c":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
//                break;
//            case "d":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
//                break;
//            case "e":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
//                break;
//            case "f":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
//                break;
//            case "g":
//                mfEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
//                break;
//        }
        new ColorImportant(d.getImportant(),mfEditLinearLayout1).LinearLayoutSet();
        String repeat = d.getRepeat();
        switch (repeat.substring(0, 8)) {
            case "everyday":
                fr = 0;
                mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                mfEditRadio41.setChecked(true);
                break;
            case "everywee":
                String re = repeat.substring(8);
                fr = 1;
                mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                mfEditRadio42.setText("每周");
                while (!re.equals("")) {
                    int a = Integer.parseInt(re.substring(0, 1));
                    setWeekChoice(a);
                    re = re.substring(1);
                }
                mfEditRadio42.setChecked(true);
                break;
            case "everymou":
                fr = 0;
                mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                mfEditRadio43.setText("每月1日");
                mfEditRadio43.setChecked(true);
                break;
            case "norepeat":
                fr = 0;
                mfEditLinearLayout41.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                mfEditRadio44.setChecked(true);
                break;
        }
        mfEditButton51.setText(d.getEndday());
        user = d;//获取当前Dayid的数据的内容
    }

    private String getWeekChoice() {
        String a = "";
        if (mfEditweekItemButton0.isChecked()) {
            a = a + "0";
        }
        if (mfEditweekItemButton1.isChecked()) {
            a = a + "1";
        }
        if (mfEditweekItemButton2.isChecked()) {
            a = a + "2";
        }
        if (mfEditweekItemButton3.isChecked()) {
            a = a + "3";
        }
        if (mfEditweekItemButton4.isChecked()) {
            a = a + "4";
        }
        if (mfEditweekItemButton5.isChecked()) {
            a = a + "5";
        }
        if (mfEditweekItemButton6.isChecked()) {
            a = a + "6";
        }
        return a;
    }

    private void setWeekChoice(int i) {
        if (i == 0) {
            mfEditweekItemButton0.setChecked(true);
        }
        if (i == 1) {
            mfEditweekItemButton1.setChecked(true);
        }
        if (i == 2) {
            mfEditweekItemButton2.setChecked(true);
        }
        if (i == 3) {
            mfEditweekItemButton3.setChecked(true);
        }
        if (i == 4) {
            mfEditweekItemButton4.setChecked(true);
        }
        if (i == 5) {
            mfEditweekItemButton5.setChecked(true);
        }
        if (i == 6) {
            mfEditweekItemButton6.setChecked(true);
        }
    }

}
