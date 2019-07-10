package com.schedule.record.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.function.CalenderWeekItem;

import java.util.List;

public class CalenderWeekAdapter extends BaseAdapter {
    private Context context;
    private List<CalenderWeekItem> list;
    //布局填充--
    private LayoutInflater inflater;

    public CalenderWeekAdapter(Context context, List<CalenderWeekItem> list) {
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
            convertView = inflater.inflate(R.layout.main_calendar_mode2_item,null);
            holder = new ViewHolder();
            holder.btn= convertView.findViewById(R.id.mode2ItemButton1);
            holder.tv1= convertView.findViewById(R.id.mode2ItemTextView1);
            holder.tv2= convertView.findViewById(R.id.mode2ItemTextView2);
            holder.tv3= convertView.findViewById(R.id.mode2ItemTextView3);
            holder.tv4= convertView.findViewById(R.id.mode2ItemTextView4);
            holder.tv5= convertView.findViewById(R.id.mode2ItemTextView5);
            holder.tv6= convertView.findViewById(R.id.mode2ItemTextView6);
            holder.tv7= convertView.findViewById(R.id.mode2ItemTextView7);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据
        CalenderWeekItem pb=list.get(position);
        holder.btn.setText(pb.getCount());
        holder.tv1.setText(pb.getMonday());
        holder.tv2.setText(pb.getTuesday());
        holder.tv3.setText(pb.getWednesday());
        holder.tv4.setText(pb.getThursday());
        holder.tv5.setText(pb.getFriday());
        holder.tv6.setText(pb.getSaturday());
        holder.tv7.setText(pb.getSunday());
        return convertView;
    }

    static class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        Button btn;
    }
}
