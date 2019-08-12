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

import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.Calendar;

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
    TodaySQLiteUserDao dao;
    TodaySQLiteUser user;
    String DBName = "today";
    int version = 1;

    public String radio2, radio3;
    final Calendar cale1 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode1_edit);
        ButterKnife.bind(this);
        layoutFilling();
    }

    @OnClick({R.id.editEditText0, R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editButton25, R.id.editButton26, R.id.editButton27})
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
                    }
                }, cale1.get(Calendar.HOUR), cale1.get(Calendar.MINUTE), true).show();
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
            case R.id.editButton25:
                user.setImportant("e");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case R.id.editButton26:
                user.setImportant("f");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case R.id.editButton27:
                user.setImportant("g");
                dao.updateAll(user);
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
    }

    @Override
    public void onPause() {
        user.setTitle(editEditText1.getText().toString());
        if (editRadio1.isChecked()) {
            user.setRemind(true);
        }
        if (editRadio2.isChecked()) {
            user.setRemind(false);
        }
        user.setTime(editEditText0.getText().toString());
        user.setDiary(editEditText2.getText().toString());
        dao.updateAll(user);
        Toast.makeText(Mode1Edit.this, "已保存数据", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @SuppressLint("ResourceType")
    private void layoutFilling() {
        //获取DayItem传递的Dayid
        helper = new TodaySQLite(this, DBName, null, version);
        helper.getReadableDatabase();
        dao = new TodaySQLiteUserDao(helper);
        Intent intent = getIntent();
        String dayid = intent.getStringExtra("dayid");
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
            case "e":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case "f":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case "g":
                editLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
        boolean remind = d.isRemind();
        if (remind) {
            editRadio1.setChecked(true);
        }else {
            editRadio2.setChecked(true);
        }
        user = d;//获取当前Dayid的数据的内容
    }
}
