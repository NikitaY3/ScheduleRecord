package com.schedule.record.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.schedule.record.app.Mode3CompletionRatio;
import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Calendar3Fragment extends Fragment {

    @BindView(R.id.mode3Button1)
    Button mode3Button1;
    @BindView(R.id.mode3Button2)
    Button mode3Button2;
    @BindView(R.id.mode3Button3)
    Button mode3Button3;
    @BindView(R.id.mode3Button4)
    Button mode3Button4;
    @BindView(R.id.mode3Button5)
    Button mode3Button5;
    @BindView(R.id.mode3Button7)
    Button mode3Button7;
    @BindView(R.id.mode3Button6)
    Button mode3Button6;
    Unbinder unbinder;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode3, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mode3Button1, R.id.mode3Button2, R.id.mode3Button3, R.id.mode3Button4, R.id.mode3Button5, R.id.mode3Button7, R.id.mode3Button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mode3Button1:
                Intent intent = new Intent(getActivity(), Mode3CompletionRatio.class);
                startActivity(intent);
                break;
            case R.id.mode3Button2:
                break;
            case R.id.mode3Button3:
                break;
            case R.id.mode3Button4:
                break;
            case R.id.mode3Button5:
                break;
            case R.id.mode3Button7:
                break;
            case R.id.mode3Button6:
                break;
        }
    }
}
