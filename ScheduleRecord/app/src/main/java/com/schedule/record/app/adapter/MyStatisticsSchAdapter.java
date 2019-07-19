package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.function.DaySQLiteUser;

import java.util.List;

public class MyStatisticsSchAdapter extends BaseAdapter {
    private Context context;
    private List<DaySQLiteUser> list;
    private LayoutInflater inflater;

    public MyStatisticsSchAdapter(Context context, List<DaySQLiteUser> list) {
        this.context = context;
        this.list = list;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_statisticsschedules_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DaySQLiteUser pb = list.get(position);

        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2,tv3;
        ConstraintLayout linearLayout;
    }
}

