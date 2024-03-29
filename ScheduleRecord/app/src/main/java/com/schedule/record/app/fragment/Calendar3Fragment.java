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
import android.widget.Toast;

import com.schedule.record.app.Mode3CompletionScale;
import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Calendar3Fragment extends Fragment {

    @BindView(R.id.mode3Button1)
    Button mode3Button1;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_calendar_mode3, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mode3Button1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mode3Button1:

                Toast.makeText(getActivity(),"暂不支持查看",Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getActivity(), Mode3CompletionScale.class);
                startActivity(intent1);
                break;
        }
    }
}
