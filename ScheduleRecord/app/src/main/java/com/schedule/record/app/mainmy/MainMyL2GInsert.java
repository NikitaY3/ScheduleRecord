package com.schedule.record.app.mainmy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.MyInsertAdapter;
import com.schedule.record.app.dialog.GInsertDialog;
import com.schedule.record.app.sqlite.RemarkSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.RemarkSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.RemarkSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL2GInsert extends AppCompatActivity {

    @BindView(R.id.gInsertButton)
    Button gInsertButton;
    @BindView(R.id.gInsertListView)
    ListView gInsertListView;
    @BindView(R.id.gInsertEditText)
    EditText gInsertEditText;
    @BindView(R.id.gInsertButton1)
    Button gInsertButton1;
    @BindView(R.id.gInsertButton2)
    Button gInsertButton2;
    @BindView(R.id.gInsertLinearLayout)
    LinearLayout gInsertLinearLayout;

    private List<TodaySQLiteUser> dataList;
    private String DBName = "today";
    private int version = 1;

    private RemarkSQLite helper2;
    private String DBName2 ="remark";

    private String gnameid,nameid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l21g_insert);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gnameid = Objects.requireNonNull(bundle).getString("gnameid");

        gInsertEditText.setClickable(false);
        gInsertEditText.setFocusable(false);
        gInsertEditText.setFocusableInTouchMode(false);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("myuser", MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid", "");

        if (Objects.requireNonNull(gnameid).equals("all")) {
            gInsertLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0));
            onResume2();
        } else {
            gInsertLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            onResume1();
        }

    }

    @OnClick(R.id.gInsertButton)
    public void onViewClicked() {

        GInsertDialog one = new GInsertDialog(this, gInsertListView, dataList, gnameid);
        one.show();
    }

    //查询特定用户
    public void onResume1() {

        //查询备注名
        helper2 = new RemarkSQLite(MainMyL2GInsert.this,DBName2,null,version);
        RemarkSQLiteUserDao dao2 = new RemarkSQLiteUserDao(helper2);
        gInsertEditText.setText(dao2.queryRemarkName(gnameid));

        //查询本地对方Today日程
        TodaySQLite helper = new TodaySQLite(MainMyL2GInsert.this, DBName, null, version);
        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper);
        dataList = dao.quiryAndSetInsertItem(gnameid);
        //设置日程Item
        MyInsertAdapter adapter = new MyInsertAdapter(MainMyL2GInsert.this, dataList);
        gInsertListView.setAdapter(adapter);

    }

    //查询所有
    public void onResume2() {
        //隐藏插入日程按钮
        gInsertButton.setFocusable(false);
        gInsertButton.setLayoutParams(new ConstraintLayout.LayoutParams(0,0));
        //查询Today日程中不属于登录者的日程并设置Item
        TodaySQLite helper = new TodaySQLite(MainMyL2GInsert.this, DBName, null, version);
        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper);
        dataList = dao.quiryAndSetAllInsertItem(nameid);
        MyInsertAdapter adapter = new MyInsertAdapter(MainMyL2GInsert.this, dataList);
        gInsertListView.setAdapter(adapter);

    }

    @OnClick({R.id.gInsertButton1, R.id.gInsertButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //编辑
            case R.id.gInsertButton1:
                //弹出键盘，可编辑输入框
                gInsertEditText.setFocusableInTouchMode(true);
                gInsertEditText.setFocusable(true);
                gInsertEditText.requestFocus();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                },50);

                break;
            //确认
            case R.id.gInsertButton2:

                //1、输入框不能输入，软键盘消失
                gInsertEditText.setClickable(false);
                gInsertEditText.setFocusable(false);
                gInsertEditText.setFocusableInTouchMode(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //2、数据存入数据库,先判断是否输入了备注名
                String gname = gInsertEditText.getText().toString();
                if (!gname.equals("")){

                    //更新备注名
                    helper2 = new RemarkSQLite(MainMyL2GInsert.this,DBName2,null,version);
                    RemarkSQLiteUserDao dao2 = new RemarkSQLiteUserDao(helper2);
                    RemarkSQLiteUser user2 = new RemarkSQLiteUser(gnameid,gname);

                    dao2.updateAll(user2);
                }
                break;
        }
    }
}

