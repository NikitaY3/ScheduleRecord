package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.mainmy.MainMy1FutureSch;
import com.schedule.record.app.mainmy.MainMy2FinishSch;
import com.schedule.record.app.mainmy.MainMy3PassSch;
import com.schedule.record.app.mainmy.MainMyL1Doc;
import com.schedule.record.app.mainmy.MainMyL2General;
import com.schedule.record.app.mainmy.MainMyL3Special;
import com.schedule.record.app.mainmy.MainMyL4BoradcastSet;
import com.schedule.record.app.mainmy.MainMyL5Information;
import com.schedule.record.app.mainmy.MainMyLogonPhone;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends Fragment {

    @BindView(R.id.myImageView1)
    ImageView myImageView1;
    @BindView(R.id.myTextView1)
    TextView myTextView1;
    @BindView(R.id.myTextView2)
    TextView myTextView2;
    @BindView(R.id.myTopButton1)
    Button myTopButton1;
    @BindView(R.id.myTopButton2)
    Button myTopButton2;
    @BindView(R.id.myTopButton3)
    Button myTopButton3;
    @BindView(R.id.myButton1)
    Button myButton1;
    @BindView(R.id.myButton2)
    Button myButton2;
    @BindView(R.id.myButton3)
    Button myButton3;
    @BindView(R.id.myButton4)
    Button myButton4;
    @BindView(R.id.myButton5)
    Button myButton5;

    @BindView(R.id.myImageView2)
    ImageView myImageView2;
    Unbinder unbinder;

    private View view;

    private GeneralUserSQLite helper;
    private String DBName = "general";
    private int version = 1;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_my, container, false);

        unbinder = ButterKnife.bind(this, view);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("myuser", MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid","");
        myTextView2.setText("账号："+nameid);

        //取得用户昵称
        helper=new GeneralUserSQLite(getActivity(),DBName,null,version);
        GeneralSQLiteUserDao dao=new GeneralSQLiteUserDao(helper);
        if (dao.queryByNameid(nameid)!=null) {
            String name = dao.queryByNameid(nameid).getName();
            myTextView1.setText(name);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myTopButton1, R.id.myTopButton2, R.id.myTopButton3, R.id.myButton1, R.id.myButton2, R.id.myButton3, R.id.myButton4, R.id.myButton5, R.id.myImageView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myTopButton1:
                Intent intent1 = new Intent(getActivity(), MainMy1FutureSch.class);
                startActivity(intent1);
                break;
            case R.id.myTopButton2:
                Intent intent2 = new Intent(getActivity(), MainMy2FinishSch.class);
                startActivity(intent2);
                break;
            case R.id.myTopButton3:
                Intent intent3 = new Intent(getActivity(), MainMy3PassSch.class);
                startActivity(intent3);
                break;
            case R.id.myButton1:
                Intent intent4 = new Intent(getActivity(), MainMyL1Doc.class);
                startActivity(intent4);
                break;
            case R.id.myButton2:
                Intent intent5 = new Intent(getActivity(), MainMyL2General.class);
                startActivity(intent5);
                break;
            case R.id.myButton3:
                Intent intent6 = new Intent(getActivity(), MainMyL3Special.class);
                startActivity(intent6);
                break;
            case R.id.myButton4:
                Intent intent7 = new Intent(getActivity(), MainMyL4BoradcastSet.class);
                startActivity(intent7);
                break;
            case R.id.myButton5:
                Intent intent8 = new Intent(getActivity(), MainMyL5Information.class);
                startActivity(intent8);
                break;
            case R.id.myImageView2:
                Intent intent = new Intent(getActivity(), MainMyLogonPhone.class);
                startActivity(intent);

                getActivity().finish();

                break;
        }
    }
}
