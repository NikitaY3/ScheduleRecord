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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

            holder.tv2.setText(pb.getTitle());
            holder.tv3.setText(pb.getRepeat());

        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2,tv3;
        ConstraintLayout linearLayout;
    }
}

