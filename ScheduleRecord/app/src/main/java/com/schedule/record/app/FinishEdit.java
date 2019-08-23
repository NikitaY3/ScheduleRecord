package com.schedule.record.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class FinishEdit extends AppCompatActivity {

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
        setContentView(R.layout.main_3finish_edit);
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
        String finishid = intent.getStringExtra("finish_id");
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

        new ColorImportant(d.getImportant(),fEditLinearLayout1).LinearLayoutSet();

        user = d;//获取当前Dayid的数据的内容
    }
}
