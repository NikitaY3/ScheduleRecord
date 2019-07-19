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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public
class Mode1Edit extends AppCompatActivity {

    @BindView(R.id.editTextView1)
    TextView editTextView1;
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
    @BindView(R.id.editRadio4)
    RadioButton editRadio4;
    @BindView(R.id.editRadioGroup)
    RadioGroup editRadioGroup;
    @BindView(R.id.editLinearLayout3)
    LinearLayout editLinearLayout3;
    @BindView(R.id.editLinearLayout31)
    LinearLayout editLinearLayout31;
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

    int fr;
    @BindView(R.id.weekItemButton0)
    CheckBox weekItemButton0;
    @BindView(R.id.weekItemButton1)
    CheckBox weekItemButton1;
    @BindView(R.id.weekItemButton2)
    CheckBox weekItemButton2;
    @BindView(R.id.weekItemButton3)
    CheckBox weekItemButton3;
    @BindView(R.id.weekItemButton4)
    CheckBox weekItemButton4;
    @BindView(R.id.weekItemButton5)
    CheckBox weekItemButton5;
    @BindView(R.id.weekItemButton6)
    CheckBox weekItemButton6;

    private DaySQLite helper;
    DaySQLiteUserDao dao;
    DaySQLiteUser user;
    String DBName = "day_1";
    int version = 1;

    public String radio2, radio3;
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
                        if (fr == 1) {
                            editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        break;
                    case R.id.editRadio2:
                        editRadio2.setText("每周");
                        fr = 1;
                        editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                        break;
                    case R.id.editRadio3:
                        if (fr == 1) {
                            editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        radio3 = "每月1日";
                        editRadio3.setText(radio3);
                        break;
                    case R.id.editRadio4:
                        if (fr == 1) {
                            editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                            fr = 0;
                        }
                        break;
                }
            }
        });

        editCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                helper = new DaySQLite(Mode1Edit.this, DBName, null, version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
                if (editCheckBox1.isChecked()) {
                    user.setCheckbox(true);
                    dao.updateAll(user);
                } else {
                    user.setCheckbox(false);
                    dao.updateAll(user);
                }
            }
        });
    }

    @OnClick({R.id.editEditText0, R.id.editEditText1, R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editButton41, R.id.editEditText2, R.id.editImageButton1, R.id.editImageButton2, R.id.editImageButton3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editEditText0:
                new TimePickerDialog(Mode1Edit.this, new TimePickerDialog.OnTimeSetListener() {
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
                        user.setTime(user.getTime().substring(0, 11) + radio2);
                        dao.updateAll(user);
                    }
                }, cale1.get(Calendar.HOUR), cale1.get(Calendar.MINUTE), true).show();
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
                new DatePickerDialog(Mode1Edit.this, new DatePickerDialog.OnDateSetListener() {
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
                        editButton41.setText(radio);
                    }
                }, cale1.get(Calendar.YEAR), cale1.get(Calendar.MONTH), cale1.get(Calendar.DAY_OF_MONTH)).show();
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

    @Override
    public void onPause() {
        user.setTitle(editEditText1.getText().toString());
        if (editRadio1.isChecked()) {
            user.setRepeat("everyday");
        }
        if (editRadio2.isChecked()) {
            String a = getWeekChoice();
            user.setRepeat("everywee" + a);
        }
        if (editRadio3.isChecked()) {
            user.setRepeat("everymou" + "1");
        }
        if (editRadio4.isChecked()) {
            user.setRepeat("norepeat");
            if (!editButton41.getText().equals("0000-00-00"))
            {
                user.setIsfinish("future");
            }
        }
        user.setEndday(editButton41.getText().toString());
        user.setDiary(editEditText2.getText().toString());
        dao.updateAll(user);
        Toast.makeText(Mode1Edit.this, "已保存数据", Toast.LENGTH_SHORT).show();
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
        if (d.isCheckbox()) {
            editCheckBox1.setChecked(true);
        } else {
            editCheckBox1.setChecked(false);
        }
        editEditText0.setText(d.getTime().substring(11, 16));
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
        switch (repeat.substring(0, 8)) {
            case "everyday":
                fr = 0;
                editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
                editRadio1.setChecked(true);
                break;
            case "everywee":
                String re = repeat.substring(8);
                fr = 1;
                editLinearLayout31.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
                editRadio2.setText("每周");
                while (!re.equals("")){
                    int a = Integer.parseInt(re.substring(0,1));
                    setWeekChoice(a);
                    re = re.substring(1);
                }
                editRadio2.setChecked(true);
                break;
            case "everymou":
                fr = 0;
                editRadio3.setText("每月1日");
                editRadio3.setChecked(true);
                break;
            case "norepeat":
                fr = 0;
                editRadio4.setChecked(true);
                break;
        }
        editButton41.setText(d.getEndday());
        user = d;//获取当前Dayid的数据的内容
    }

    private String getWeekChoice() {
        String a = "";
        if (weekItemButton0.isChecked()){
            a = a+"0";
        }
        if (weekItemButton1.isChecked()){
            a = a+"1";
        }
        if (weekItemButton2.isChecked()){
            a = a+"2";
        }
        if (weekItemButton3.isChecked()){
            a = a+"3";
        }
        if (weekItemButton4.isChecked()){
            a = a+"4";
        }
        if (weekItemButton5.isChecked()){
            a = a+"5";
        }
        if (weekItemButton6.isChecked()){
            a = a+"6";
        }
        return a;
    }

    private void setWeekChoice( int i) {
        if (i == 0){
            weekItemButton0.setChecked(true);
        }
        if (i == 1){
            weekItemButton1.setChecked(true);
        }
        if (i == 2){
            weekItemButton2.setChecked(true);
        }
        if (i == 3){
            weekItemButton3.setChecked(true);
        }
        if (i == 4){
            weekItemButton4.setChecked(true);
        }
        if (i == 5){
            weekItemButton5.setChecked(true);
        }
        if (i == 6){
            weekItemButton6.setChecked(true);
        }
    }
}
