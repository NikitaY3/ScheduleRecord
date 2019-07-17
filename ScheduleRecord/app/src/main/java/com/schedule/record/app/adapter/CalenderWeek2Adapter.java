package com.schedule.record.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schedule.record.app.R;

import java.util.List;

public class CalenderWeek2Adapter extends BaseAdapter {
    private Context context;
    private List<Integer> list;
    //布局填充--
    private LayoutInflater inflater;

    public CalenderWeek2Adapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
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

    //每一个item调用该方法---视图缓存机制
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
        Integer pb=list.get(position);
        holder.tv1.setText((position+1)+"");
        return convertView;
    }

    static class ViewHolder{
        TextView tv1;
    }
}
