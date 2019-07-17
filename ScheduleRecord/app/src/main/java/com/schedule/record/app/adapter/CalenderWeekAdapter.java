package com.schedule.record.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.Mode1Edit;
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
            holder.tv1 = convertView.findViewById(R.id.mode2ItemTextView1);
            holder.tv2 = convertView.findViewById(R.id.mode2ItemTextView2);
            holder.btn1 = convertView.findViewById(R.id.mode2ItemLinear1);
            holder.btn2 = convertView.findViewById(R.id.mode2ItemLinear2);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据
        CalenderWeekItem pb=list.get(position);
        holder.tv1.setText((position+1)+"");
        holder.tv2.setText(pb.getTitle());
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderWeekItem pb=list.get(position);
                Intent intent= new Intent(context, Mode1Edit.class);
                intent.putExtra("dayid",pb.getDayid());
                context.startActivity(intent);
            }
        });
        if (pb.isCheckbox()) {
            holder.btn2.setBackgroundResource(R.drawable.abb_mode2_finish);
        }
        switch (pb.getImportant()) {
            case "a":
                holder.btn1.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                holder.btn1.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                holder.btn1.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                holder.btn1.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
        }
        return convertView;
    }

    static class ViewHolder{
        TextView tv1,tv2;
        LinearLayout btn1,btn2;
    }
}
