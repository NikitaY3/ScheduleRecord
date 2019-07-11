package com.schedule.record.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.MainCalender1Edit;
import com.schedule.record.app.R;
import com.schedule.record.app.R.drawable;
import com.schedule.record.app.function.CalenderDayItem;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.List;

import static com.schedule.record.app.R.*;

public class CalenderDayAdapter extends BaseAdapter {
    private Context context;
    private List<CalenderDayItem> list;
    //布局填充--
    private LayoutInflater inflater;

    AlertDialog.Builder frame1;
    private DaySQLite helper;
    String DBName="day_1";
    int version = 1;

    CalenderDayItem pb;

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
            convertView = inflater.inflate(layout.main_calendar_mode1_item,null);
            holder = new ViewHolder();
            holder.tv1= convertView.findViewById(id.mode1ItemCheckBox);
            holder.tv2= convertView.findViewById(id.mode1ItemEditText1);
            holder.tv3= convertView.findViewById(id.mode1ItemEditText2);
            holder.linearLayout = convertView.findViewById(id.mode1ItemLinearLayout);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据
        pb = list.get(position);
        holder.tv2.setText(pb.getTime().substring(11,16));//设置时间
        holder.tv3.setText(pb.getNews());
//        Drawable drawable = com.schedule.record.app.R.drawable.abaa_item_im_no;
        holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
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
                //调用弹框函数
                dayConfirmationDialogs(position,pb.getTime());
                Toast.makeText(context,"删除的Dayid为："+ pb.getTime(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return convertView;
    }

    static class ViewHolder{
        CheckBox tv1;
        TextView tv2,tv3;
        Button btn;
        LinearLayout linearLayout;
    }

    //弹框函数
    private void dayConfirmationDialogs(final int position, final String time) {
        frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除Item
                list.remove(position);
                CalenderDayAdapter.this.notifyDataSetChanged();

                //删除Item对应的数据库
                helper=new DaySQLite(context,DBName,null,version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                dao.delete(time);
//                dao.deleteAll();

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
