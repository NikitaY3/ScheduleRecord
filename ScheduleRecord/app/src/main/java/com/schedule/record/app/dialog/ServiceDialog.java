package com.schedule.record.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;

import com.schedule.record.app.R;

import java.util.Objects;

public class ServiceDialog extends Dialog {

    public ServiceDialog(Context context) {
        super(context,R.style.Theme_AppCompat_Light_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_inputitem);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
    }
}
