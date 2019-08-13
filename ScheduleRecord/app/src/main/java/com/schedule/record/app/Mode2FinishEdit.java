package com.schedule.record.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public
class Mode2FinishEdit extends AppCompatActivity {

    @BindView(R.id.fEditCheckBox1)
    CheckBox fEditCheckBox1;
    @BindView(R.id.fEditEditText0)
    EditText fEditEditText0;
    @BindView(R.id.fEditEditText1)
    EditText fEditEditText1;
    @BindView(R.id.fEditLinearLayout1)
    LinearLayout fEditLinearLayout1;

    @BindView(R.id.fEditButton21)
    TextView fEditButton21;

    @BindView(R.id.fEditEditText2)
    EditText fEditEditText2;


    private FinishSQLite helper;
    FinishSQLiteUserDao dao;
    FinishSQLiteUser user;
    String DBName = "finish";
    int version = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode2_edit_finish);
        ButterKnife.bind(this);
        layoutFilling();

    }

    @SuppressLint("ResourceType")
    private void layoutFilling() {
        //获取DayItem传递的Dayid
        helper = new FinishSQLite(this, DBName, null, version);
        helper.getReadableDatabase();
        dao = new FinishSQLiteUserDao(helper);
        Intent intent = getIntent();
        String finishid = intent.getStringExtra("finishid");
        //查询Dayid对应数据
        FinishSQLiteUser d = dao.queryByFinishid(finishid);
        //设置当前布局填充
        if (d.getCheckbox()) {
            fEditCheckBox1.setChecked(true);
        } else {
            fEditCheckBox1.setChecked(false);
        }
        fEditEditText0.setText(d.getTime());
        fEditEditText1.setText(d.getTitle());
        fEditEditText2.setText(d.getDiary());
        switch (d.getImportant()) {
            case "a":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case "e":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case "f":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case "g":
                fEditLinearLayout1.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
        user = d;//获取当前Dayid的数据的内容
    }
}
