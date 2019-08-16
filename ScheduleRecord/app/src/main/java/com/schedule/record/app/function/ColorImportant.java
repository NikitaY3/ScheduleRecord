package com.schedule.record.app.function;

import android.widget.LinearLayout;

import com.schedule.record.app.R;

public class ColorImportant {

    private String important;
    private LinearLayout linearLayout;

    public ColorImportant(String important, LinearLayout linearLayout) {
        this.important = important;
        this.linearLayout = linearLayout;
    }

    public void LinearLayoutSet() {
        switch (important) {
            case "a":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case "e":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case "f":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case "g":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
    }

    public String ImportantSet() {
        String imp = "a";
        switch (important) {
            case "等级一":
                imp = "a";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "等级二":
                imp = "b";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "等级三":
                imp = "c";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "等级四":
                imp = "d";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case "等级五":
                imp = "e";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case "等级六":
                imp = "f";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case "等级七":
                imp = "g";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }
        return imp;
    }
}
