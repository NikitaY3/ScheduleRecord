package com.schedule.record.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.MainCalender1Edit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.CalenderDayItem;

import java.util.List;

public class CalenderDayAdapter extends BaseAdapter {
    private Context context;
    private List<CalenderDayItem> list;
    //布局填充--
    private LayoutInflater inflater;

    AlertDialog.Builder frame1;

    public CalenderDayAdapter(Context context, List<CalenderDayItem> list) {
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
            convertView = inflater.inflate(R.layout.main_calendar_mode1_item,null);
            holder = new ViewHolder();
            holder.tv1= convertView.findViewById(R.id.mode1ItemCheckBox);
            holder.tv2= convertView.findViewById(R.id.mode1ItemEditText1);
            holder.tv3= convertView.findViewById(R.id.mode1ItemEditText2);

//            holder.btn= convertView.findViewById(R.id.mode1ItemLinearLayout);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据
        CalenderDayItem pb=list.get(position);
//        holder.tv1.setText(pb.getNews());
        holder.tv2.setText(pb.getTime());
        holder.tv3.setText(pb.getNews());
        holder.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MainCalender1Edit.class);
                context.startActivity(intent);
            }
        });
        holder.tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"该位置"+ position,Toast.LENGTH_SHORT).show();
                dayConfirmationDialogs(position);
                return true;
            }
        });
        return convertView;
    }

    static class ViewHolder{
        CheckBox tv1;
        TextView tv2,tv3;
        Button btn;
    }


    private void dayConfirmationDialogs(final int position) {
        frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                //通知数据更新
                CalenderDayAdapter.this.notifyDataSetChanged();
            }
        });
        frame1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        frame1.setMessage("确认删除当前日程？");
        frame1.setTitle("提示");
        frame1.show();
    }
}
