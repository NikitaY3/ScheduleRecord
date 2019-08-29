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
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important1);
                break;
            case "b":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important2);
                break;
            case "c":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important3);
                break;
            case "d":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important4);
                break;
            case "e":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important5);
                break;
            case "f":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important6);
                break;
            case "g":
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important7);
                break;
        }
    }

    public String ImportantSet() {
        String imp = "a";
        switch (important) {
            case "等级一":
                imp = "a";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important1);
                break;
            case "等级二":
                imp = "b";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important2);
                break;
            case "等级三":
                imp = "c";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important3);
                break;
            case "等级四":
                imp = "d";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important4);
                break;
            case "等级五":
                imp = "e";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important5);
                break;
            case "等级六":
                imp = "f";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important6);
                break;
            case "等级七":
                imp = "g";
                linearLayout.setBackgroundResource(R.drawable.abaa_item_important7);
                break;
        }
        return imp;
    }
}
