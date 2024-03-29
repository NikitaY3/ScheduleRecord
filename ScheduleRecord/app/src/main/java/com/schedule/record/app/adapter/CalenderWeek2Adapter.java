package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schedule.record.app.R;

import java.util.List;

public class CalenderWeek2Adapter extends BaseAdapter {

    private List<Integer> list;
    private LayoutInflater inflater;

    public CalenderWeek2Adapter(Context context, List<Integer> list) {
        this.list = list;
        //布局填充
        inflater=LayoutInflater.from(context);
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

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.main_calendar_mode2_item2,null);
            holder = new ViewHolder();
            holder.tv1= convertView.findViewById(R.id.mode2ItemButton1);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //数据
        holder.tv1.setText((position+1) + "");
        return convertView;
    }

    static class ViewHolder{
        TextView tv1;
    }
}
