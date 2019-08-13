package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EquimentFragment extends Fragment {
    View view;
    @BindView(R.id.equButton)
    Button equButton;
    Unbinder unbinder;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_equiment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @OnClick(R.id.equButton)
    public void onViewClicked() {
        //1.判断Future日程是否应该插入到Today

        //2.判断将Today日程插入到Finish日程

        //3.判断Today日程是否Pass
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
