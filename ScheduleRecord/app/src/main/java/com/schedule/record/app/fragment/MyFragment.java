package com.schedule.record.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.schedule.record.app.MainMyBoradcastSet;
import com.schedule.record.app.MainMyEffectivesSch;
import com.schedule.record.app.MainMyFinishSch;
import com.schedule.record.app.MainMyInformation;
import com.schedule.record.app.MainMyLogon;
import com.schedule.record.app.MianMyStatisticsSch;
import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends Fragment {
    View view;
    @BindView(R.id.myImageView1)
    ImageView myImageView1;
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
    @BindView(R.id.myButton6)
    Button myButton6;
    @BindView(R.id.myButton7)
    Button myButton7;
    @BindView(R.id.myButton8)
    Button myButton8;
    @BindView(R.id.myImageView2)
    ImageView myImageView2;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_my, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myImageView1, R.id.myButton1, R.id.myButton2, R.id.myButton3, R.id.myButton4, R.id.myButton5, R.id.myButton6, R.id.myButton7, R.id.myButton8, R.id.myImageView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myImageView1:
                break;
            case R.id.myButton1:
                Intent intent1 = new Intent(getActivity(), MainMyInformation.class);
                startActivity(intent1);
                break;
            case R.id.myButton2:
                Intent intent2 = new Intent(getActivity(), MainMyEffectivesSch.class);
                startActivity(intent2);
                break;
            case R.id.myButton3:
                Intent intent3 = new Intent(getActivity(), MainMyFinishSch.class);
                startActivity(intent3);
                break;
            case R.id.myButton4:
                Intent intent4 = new Intent(getActivity(), MianMyStatisticsSch.class);
                startActivity(intent4);
                break;
            case R.id.myButton5:
                break;
            case R.id.myButton6:
                Intent intent6 = new Intent(getActivity(), MainMyBoradcastSet.class);
                startActivity(intent6);
                break;
            case R.id.myButton7:
                break;
            case R.id.myButton8:
                break;
            case R.id.myImageView2:
                Intent intent = new Intent(getActivity(), MainMyLogon.class);
                startActivity(intent);
                break;
        }
    }
}
