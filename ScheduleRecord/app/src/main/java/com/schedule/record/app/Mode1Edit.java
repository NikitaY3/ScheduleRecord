package com.schedule.record.app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public
class Mode1Edit extends AppCompatActivity {

    @BindView(R.id.editCheckBox1)
    CheckBox editCheckBox1;
    @BindView(R.id.editEditText0)
    EditText editEditText0;
    @BindView(R.id.editEditText1)
    EditText editEditText1;
    @BindView(R.id.editLinearLayout1)
    LinearLayout editLinearLayout1;
    @BindView(R.id.editTextView2)
    TextView editTextView2;
    @BindView(R.id.editButton21)
    Button editButton21;
    @BindView(R.id.editButton22)
    Button editButton22;
    @BindView(R.id.editButton23)
    Button editButton23;
    @BindView(R.id.editButton24)
    Button editButton24;
    @BindView(R.id.editLinearLayout2)
    LinearLayout editLinearLayout2;
    @BindView(R.id.editTextView3)
    TextView editTextView3;
    @BindView(R.id.editRadio1)
    RadioButton editRadio1;
    @BindView(R.id.editRadio2)
    RadioButton editRadio2;
    @BindView(R.id.editRadio3)
    RadioButton editRadio3;
    @BindView(R.id.editRadioGroup)
    RadioGroup editRadioGroup;
    @BindView(R.id.editLinearLayout3)
    LinearLayout editLinearLayout3;
    @BindView(R.id.editTextView4)
    TextView editTextView4;
    @BindView(R.id.editButton41)
    TextView editButton41;
    @BindView(R.id.editLinearLayout4)
    LinearLayout editLinearLayout4;
    @BindView(R.id.editEditText2)
    EditText editEditText2;
    @BindView(R.id.editImageButton1)
    ImageButton editImageButton1;
    @BindView(R.id.editImageButton2)
    ImageButton editImageButton2;
    @BindView(R.id.editImageButton3)
    ImageButton editImageButton3;
    @BindView(R.id.editLinearLayout5)
    LinearLayout editLinearLayout5;

    private DaySQLite helper;
    DaySQLiteUserDao dao;
    DaySQLiteUser user;
    String DBName = "day_1";
    int version = 1;

    public String radio2,radio3;
    final Calendar cale1 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode1_edit);
        ButterKnife.bind(this);
        layoutFilling();
        onRadioGroupChecked();

    }

    private void onRadioGroupChecked() {
        editRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.editRadio1:
                        break;
                    case R.id.editRadio2:
                        new DatePickerDialog(Mode1Edit.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                radio2 = "每周" + dayOfMonth+"日";
                                editRadio2.setText(radio2);
                            }
                        },cale1.get(Calendar.YEAR),cale1.get(Calendar.MONTH),cale1.get(Calendar.DAY_OF_WEEK)).show();
                        break;
                    case R.id.editRadio3:
                        new DatePickerDialog(Mode1Edit.this,new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                radio3 = "每月" + dayOfMonth+"日";
                                editRadio3.setText(radio3);
                            }
                        },cale1.get(Calendar.YEAR),cale1.get(Calendar.MONTH),cale1.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                }
            }
        });

        editCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                helper=new DaySQLite(Mode1Edit.this,DBName,null,version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                if( editCheckBox1.isChecked()){
                    user.setCheckbox(true);
                    dao.updateAll(user);
                }else{
                    user.setCheckbox(false);
                    dao.updateAll(user);
                }
            }
        });
    }

    @OnClick({ R.id.editEditText0, R.id.editEditText1, R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editButton41, R.id.editEditText2, R.id.editImageButton1, R.id.editImageButton2, R.id.editImageButton3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editEditText0:
                new TimePickerDialog(Mode1Edit.this, new TimePickerDialog.OnTimeSetListener() {
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
                        editEditText0.setText(radio2);
                        user.setTime(user.getTime().substring(0,11)+radio2);
                        dao.updateAll(user);
                    }
                },cale1.get(Calendar.HOUR),cale1.get(Calendar.MINUTE),true).show();
                break;
            case R.id.editEditText1:
                break;
            case R.id.editButton21:
                user.setImportant("a");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case R.id.editButton22:
                user.setImportant("b");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case R.id.editButton23:
                user.setImportant("c");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case R.id.editButton24:
                user.setImportant("d");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case R.id.editButton41:
                new DatePickerDialog(Mode1Edit.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        radio3 = year+"年"+(month+1)+"月"+dayOfMonth+"日";
                        editButton41.setText(radio3);
                        user.setRepeat(radio3);
                        dao.updateAll(user);
                    }
                },cale1.get(Calendar.YEAR),cale1.get(Calendar.MONTH),cale1.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.editEditText2:
                break;
            case R.id.editImageButton1:
                break;
            case R.id.editImageButton2:
                break;
            case R.id.editImageButton3:
                break;
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Mode1Edit.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        }, year, month, day);

        datePickerDialog.setCancelable(true);
        DatePicker dp = datePickerDialog.getDatePicker();
        //设置当天为最小值
        dp.setMinDate(calendar.getTimeInMillis());
        //设置最大值是７天
        calendar.set(Calendar.DAY_OF_MONTH, day + 6);
        dp.setMaxDate(calendar.getTimeInMillis());
        try {
            //获取指定的字段
            Field field = dp.getClass().getDeclaredField("mYearSpinner");
            //解封装
            field.setAccessible(true);
            //获取当前实例的值
            NumberPicker np = ((NumberPicker) field.get(dp));
            np.setVisibility(View.GONE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        datePickerDialog.show();
    }
    private void showWeekPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        DatePickerDialog datePickerDialog = new DatePickerDialog(Mode1Edit.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK));
//
//        datePickerDialog.setCancelable(true);
//
//        DatePicker dp = datePickerDialog.getDatePicker();
//        //设置当天为最小值
//        dp.setMinDate(calendar.getTimeInMillis());
//        //设置最大值是７天
//        calendar.set(Calendar.DAY_OF_WEEK, day + 6);
//        dp.setMaxDate(calendar.getTimeInMillis());
//        try {
//            //获取指定的字段
//            Field field = dp.getClass().getDeclaredField("mYearSpinner");
//            //解封装
//            field.setAccessible(true);
//            //获取当前实例的值
//            NumberPicker np = ((NumberPicker) field.get(dp));
//            np.setVisibility(View.GONE);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        datePickerDialog.show();
    }

    @Override
    public void onPause() {
//        user.setTime(editEditText0.getText().toString());
        user.setTitle(editEditText1.getText().toString());
        if(editRadio1.isChecked()){
            user.setRepeat("everyday");
        }
        if(editRadio2.isChecked()){
            user.setRepeat("everywee"+editRadio2.getText().toString());
        }
        if (editRadio3.isChecked()){
            user.setRepeat("everymou"+editRadio3.getText().toString());
        }
        user.setEndday(editButton41.getText().toString());
        user.setDiary(editEditText2.getText().toString());
        dao.updateAll(user);
        Toast.makeText(Mode1Edit.this,"已保存数据",Toast.LENGTH_SHORT).show();
        super.onPause();
    }
    @SuppressLint("ResourceType")
    private void layoutFilling() {
        //获取DayItem传递的Dayid
        helper = new DaySQLite(this, DBName, null, version);
        helper.getReadableDatabase();
        dao = new DaySQLiteUserDao(helper);
        Intent intent = getIntent();
        String dayid = intent.getStringExtra("dayid");
        //查询Dayid对应数据
        DaySQLiteUser d = dao.queryBydayid(dayid);
        //设置当前布局填充
        if(d.isCheckbox()){
            editCheckBox1.setChecked(true);
        }else{
            editCheckBox1.setChecked(false);
        }
        editEditText0.setText(d.getTime().substring(11,16));
        editEditText1.setText(d.getTitle());
        editEditText2.setText(d.getDiary());
        switch (d.getImportant()) {
            case "a":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
        }
        String repeat = d.getRepeat();
        switch (repeat.substring(0,8)) {
            case "everyday":
                editRadio1.setChecked(true);
                break;
            case "everywee":
                editRadio2.setText(repeat.substring(8));
                editRadio2.setChecked(true);
                break;
            case "everymou":
                editRadio3.setText(repeat.substring(8));
                editRadio3.setChecked(true);
                break;
        }
        editButton41.setText(d.getEndday());
        user = d;//获取当前Dayid的数据的内容
    }

}
