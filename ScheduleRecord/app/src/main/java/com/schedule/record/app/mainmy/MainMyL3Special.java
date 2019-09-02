package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.function.GetFunctions.AuthorityDeleteTask;
import com.schedule.record.app.function.GetFunctions.AuthorityQueryTask;
import com.schedule.record.app.function.PostFunctions;
import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.RemarkSQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.dao.RemarkSQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;
import com.schedule.record.app.sqlite.user.RemarkSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL3Special extends AppCompatActivity {

    @BindView(R.id.specialEditText)
    EditText specialEditText;
    @BindView(R.id.specialButton1)
    Button specialButton1;
    @BindView(R.id.specialButton2)
    Button specialButton2;
    @BindView(R.id.specialTextView2)
    TextView specialTextView2;

    private AuthoritySQLiteUser user;
    private AuthoritySQLiteUserDao dao;
    private String nameid, snameid;

    private String DBName = "authority";
    private String DBName1 = "remark";
    private int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l3special);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid","");
        user = new AuthoritySQLiteUser(nameid,"");

        //普通用户是我，特殊用户是编辑框里面的,查询
        AuthoritySQLite helper = new AuthoritySQLite(MainMyL3Special.this, DBName, null, version);
        dao = new AuthoritySQLiteUserDao(helper);
        specialTextView2.setText(dao.querySpecial(nameid));
    }

    @OnClick({R.id.specialButton1, R.id.specialButton2})
    public void onViewClicked(View view) {
        snameid = specialEditText.getText().toString();
        switch (view.getId()) {
            case R.id.specialButton1:
                if (snameid.length() == 11){

                    //插入特殊用户数据发送到云端
                    user.setSnameId(snameid);
                    new PostFunctions().SaveAuthorityPost(user,uiHandler);

                }else {
                    specialEditText.setText("");
                    Toast.makeText(this,"您输入的ID不正确",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.specialButton2:
                //1、删除本地数据库
                dao.deleteByNameid(nameid,snameid);
                //2、删除云端数据库
                new AuthorityDeleteTask(uiHandler).execute("http://120.77.222.242:10024/authority/delete?snameId=" + snameid + "&gnameId=" + nameid);

                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 22:
                    specialTextView2.setText(dao.querySpecial(nameid));
                    Toast.makeText(MainMyL3Special.this,"删除成功",Toast.LENGTH_SHORT).show();
                    break;
                case 21:
                    //插入本地权限表
                    dao.insert(user);
                    specialTextView2.setText(dao.querySpecial(nameid));

                    //插入备注表
                    RemarkSQLite helper10 = new RemarkSQLite(MainMyL3Special.this,DBName1,null,version);
                    RemarkSQLiteUserDao dao10 = new RemarkSQLiteUserDao(helper10);
                    RemarkSQLiteUser user10 = new RemarkSQLiteUser(snameid,"");
                    dao10.insert(user10);

                    break;
            }
        }
    };
}
