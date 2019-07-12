package com.schedule.record.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainCalender1Edit extends AppCompatActivity {
    @BindView(R.id.editCheckBox1)
    CheckBox editCheckBox1;
    @BindView(R.id.editEditText1)
    EditText editEditText1;
    @BindView(R.id.editButton21)
    Button editButton21;
    @BindView(R.id.editButton22)
    Button editButton22;
    @BindView(R.id.editButton23)
    Button editButton23;
    @BindView(R.id.editButton24)
    Button editButton24;
    @BindView(R.id.editRadioGroup)
    RadioGroup editRadioGroup;
    @BindView(R.id.editButton41)
    TextView editButton41;
    @BindView(R.id.editEditText2)
    EditText editEditText2;
    @BindView(R.id.editImageButton1)
    ImageButton editImageButton1;
    @BindView(R.id.editImageButton2)
    ImageButton editImageButton2;
    @BindView(R.id.editImageButton3)
    ImageButton editImageButton3;
    @BindView(R.id.editEditText0)
    EditText editEditText0;
    @BindView(R.id.editLinearLayout1)
    LinearLayout editLinearLayout1;
    @BindView(R.id.editRadio1)
    RadioButton editRadio1;
    @BindView(R.id.editRadio2)
    RadioButton editRadio2;
    @BindView(R.id.editRadio3)
    RadioButton editRadio3;
    @BindView(R.id.editButton1)
    Button editButton1;

    private DaySQLite helper;
    DaySQLiteUserDao dao;
    DaySQLiteUser user;
    String DBName = "day_1";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_edit);
        ButterKnife.bind(this);
        layoutFilling();
        editRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.editRadio1:
                        break;
                    case R.id.editRadio2:
                        editRadio2.setText("每周星期一");
                        break;
                    case R.id.editRadio3:
                        editRadio3.setText("每月12号");
                        break;
                }
            }
        });

    }

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
        editEditText0.setText(d.getTime().substring(11, 16));
        editEditText1.setText(d.getTitle());
        editEditText2.setText(d.getDiary());
        user = d;//获取当前Dayid的数据的内容
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
    }

    @OnClick(R.id.editButton1)
    public void onViewClicked() {
//        user.setDayid(user.getDayid());
        user.setDiary(editEditText0.getText().toString());
//        user.setTime(editEditText1.getText().toString());
        user.setTitle(editEditText1.getText().toString());
        dao.updateAll(user);
    }
    @OnClick({R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editButton41, R.id.editImageButton1, R.id.editImageButton2, R.id.editImageButton3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                editButton41.setText("2019年9月1日");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
