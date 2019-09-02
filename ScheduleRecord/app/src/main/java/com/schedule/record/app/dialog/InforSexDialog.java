package com.schedule.record.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.schedule.record.app.R;

import java.util.Objects;

public class InforSexDialog extends Dialog {

    private TextView textView;

    public InforSexDialog(Context context, TextView textView) {
        super(context, R.style.MyDialog);
        this.textView = textView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l5information_sex);

        TextView inforSexText1 = findViewById(R.id.inforSexText1);
        TextView inforSexText2 = findViewById(R.id.inforSexText2);
        TextView inforSexText3 = findViewById(R.id.inforSexText3);

        inforSexText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("男");
                InforSexDialog.this.dismiss();
            }
        });

        inforSexText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("女");
                InforSexDialog.this.dismiss();
            }
        });

        inforSexText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("无");
                InforSexDialog.this.dismiss();
            }
        });
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
