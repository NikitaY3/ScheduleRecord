package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.dialog.InforSexDialog;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL5Information extends AppCompatActivity {

    @BindView(R.id.informationButton1)
    Button informationButton1;
    @BindView(R.id.informationButton2)
    Button informationButton2;
    @BindView(R.id.informationButton3)
    Button informationButton3;
    @BindView(R.id.informationButton4)
    Button informationButton4;
    @BindView(R.id.informationButton5)
    Button informationButton5;
    @BindView(R.id.informationText1)
    TextView informationText1;
    @BindView(R.id.informationText2)
    TextView informationText2;
    @BindView(R.id.informationText3)
    TextView informationText3;
    @BindView(R.id.informationText4)
    TextView informationText4;
    @BindView(R.id.informationText5)
    TextView informationText5;
    @BindView(R.id.informationButton6)
    Button informationButton6;
    @BindView(R.id.informationButton)
    Button informationButton;

    final Calendar cale = Calendar.getInstance();

    public GeneralUserSQLite helper;
    public String DBName = "general_user";
    public int version = 1;

    private GeneralSQLiteUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l5information);
        ButterKnife.bind(this);

        //取得登录用户的ID和昵称
        SharedPreferences sharedPreferences = getSharedPreferences("myuser", MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid", "");

        helper = new GeneralUserSQLite(MainMyL5Information.this, DBName, null, version);
        GeneralSQLiteUserDao dao = new GeneralSQLiteUserDao(helper);
        user = dao.queryByNameid(nameid);

        informationText1.setText(user.getName());
        informationText2.setText(user.getHead());
        informationText3.setText(user.getHead());
        informationText4.setText(user.getSex());
        informationText5.setText(user.getBirthday().substring(0,10));

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //保存数据到数据库
                user.setName(informationText1.getText().toString());
                user.setHead(informationText2.getText().toString());
                user.setSex(informationText4.getText().toString());
                user.setBirthday(informationText5.getText().toString());

                //数据保存到云端
                String res = new PostFunctions().UpdateUserPost(user,uiHandler);

                GeneralSQLiteUserDao dao = new GeneralSQLiteUserDao(helper);
                dao.updateAll(user);
            }
        });

    }

    @OnClick({R.id.informationButton1, R.id.informationButton2, R.id.informationButton3, R.id.informationButton4, R.id.informationButton5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.informationButton1:

                Intent intent = new Intent(MainMyL5Information.this, MyL5NameSet.class);
                startActivity(intent);

                finish();

                break;
            case R.id.informationButton2:
                Toast.makeText(this, "不支持设置头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.informationButton3:
                Toast.makeText(this, "不支持设置主页背景", Toast.LENGTH_SHORT).show();
                break;
            case R.id.informationButton4:

                //弹出性别选择框
                InforSexDialog one = new InforSexDialog(MainMyL5Information.this, informationText4);
                one.show();

                break;
            case R.id.informationButton5:

                new DatePickerDialog(MainMyL5Information.this, new DatePickerDialog.OnDateSetListener() {
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
                        informationText5.setText(radio);
                    }
                }, cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH)).show();

                break;
        }
    }

    @OnClick(R.id.informationButton6)
    public void onViewClicked() {
        //转跳到新页面设置密码
        Toast.makeText(this, "不支持更改登录密码", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 21:
                    Toast.makeText(MainMyL5Information.this,"账号信息保存成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainMyL5Information.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 210:
                    Toast.makeText(MainMyL5Information.this,"账号信息保存失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
