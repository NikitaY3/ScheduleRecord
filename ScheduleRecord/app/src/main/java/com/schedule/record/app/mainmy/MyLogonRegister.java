package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLogonRegister extends AppCompatActivity {

    @BindView(R.id.registerEditText1)
    EditText registerEditText1;
    @BindView(R.id.registerEditText2)
    EditText registerEditText2;
    @BindView(R.id.registerEditText3)
    EditText registerEditText3;
    @BindView(R.id.registerEditText4)
    EditText registerEditText4;
    @BindView(R.id.registerButton1)
    Button registerButton1;

    public int version = 1;
    public static GeneralUserSQLite helper;
    public static String DBName = "general_user";

    private String nameid,name,password,res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_logon_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton1)
    public void onViewClicked() {
        if (!registerEditText2.getText().toString().equals(registerEditText3.getText().toString())){
            Toast.makeText(this,"密码不一致，请重新确认密码",Toast.LENGTH_SHORT).show();
            registerEditText2.setText("");
            registerEditText3.setText("");
        }else {
            nameid = registerEditText1.getText().toString();
            password = registerEditText3.getText().toString();
            name = registerEditText4.getText().toString();

            //1、将账号信息保存到云端
            res = new PostFunctions().SaveUserPost(nameid,password,name,uiHandler);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 21:
                    //2、将账号信息保存到本地数据库
                    GeneralSQLiteUser things = new GeneralSQLiteUser(nameid,name,password,"男",null,null);
                    helper = new GeneralUserSQLite(MyLogonRegister.this, DBName, null, version);
                    GeneralSQLiteUserDao dao = new GeneralSQLiteUserDao(helper);
                    dao.deleteAll();//保证本地账号唯一性
                    dao.insert(things);

                    //3、保存数据+转跳到登录页面
                    SharedPreferences sharedPreferences = MyLogonRegister.this.getSharedPreferences("myuser", MODE_PRIVATE);
                    SharedPreferences.Editor myuser = sharedPreferences.edit();
                    myuser.putString("nameid", nameid);
                    myuser.putString("password", password);
                    myuser.putString("name", name);
                    myuser.apply();

                    Toast.makeText(MyLogonRegister.this,"账号注册成功" + res,Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(MyLogonRegister.this, MainActivity.class);
                    startActivity(intent2);
                    MyLogonRegister.this.finish();

                    break;
                case 210:

                    Toast.makeText(MyLogonRegister.this,"账号注册失败" + res,Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
}