package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.FinishEdit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.function.ColorImportant;

import java.util.List;

public class CalenderWeekAdapter1 extends BaseAdapter {
    private Context context;
    private List<CalenderWeekItem> list;
    private LayoutInflater inflater;

    public CalenderWeekAdapter1(Context context, List<CalenderWeekItem> list) {
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
    @SuppressLint("SetTextI18n")
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
        holder.btn2.setBackgroundResource(R.color.nullcolor);
        if (pb.isCheckbox()) {
            holder.btn2.setBackgroundResource(R.drawable.abb_mode2_finish);
        }

        holder.tv1.setText((position+1)+"");
        holder.tv2.setText(pb.getTitle());
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderWeekItem pb=list.get(position);
                Intent intent= new Intent(context, FinishEdit.class);
                intent.putExtra("dayid",pb.getDayid());
                context.startActivity(intent);
            }
        });
        new ColorImportant(pb.getImportant(),holder.btn1).LinearLayoutSet();

        return convertView;
    }

    static class ViewHolder{
        TextView tv1,tv2;
        LinearLayout btn1,btn2;
    }
}
