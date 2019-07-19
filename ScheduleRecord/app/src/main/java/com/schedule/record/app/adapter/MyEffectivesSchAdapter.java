package com.schedule.record.app.adapter;

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

public class MyEffectivesSchAdapter extends BaseAdapter {
    Context context;
    private List<DaySQLiteUser> list;
    private LayoutInflater inflater;

    public MyEffectivesSchAdapter (Context context, List<DaySQLiteUser> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //每一个item调用该方法---视图缓存机制
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_effectiveschedules_item, null);
            holder = new ViewHolder();
            holder.tv1 = convertView.findViewById(R.id.effItemEditText2);
            holder.tv2 = convertView.findViewById(R.id.effItemTView2);
            holder.linearLayout = convertView.findViewById(R.id.effItemConstraintLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DaySQLiteUser pb = list.get(position);

        holder.tv1.setText(pb.getTitle());
        holder.tv2.setText("1 天");

        switch (pb.getImportant()) {
            case "a":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
        }
        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2,tv3;
        ConstraintLayout linearLayout;
    }
}

