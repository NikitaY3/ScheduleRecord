package com.schedule.record.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.MyFutureEdit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.util.List;

public class MyFutureAdapter extends BaseAdapter {
    Context context;
    private List<FutureSQLiteUser> list;
    private LayoutInflater inflater;

    private AlertDialog.Builder frame1;
    private FutureSQLite helper;
    private String DBName="future";
    private int version = 1;

    public MyFutureAdapter (Context context, List<FutureSQLiteUser> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //每一个item调用该方法---视图缓存机制
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_1future_item, null);
            holder = new ViewHolder();
            holder.tv1 = convertView.findViewById(R.id.effItemEditText1);
            holder.tv2 = convertView.findViewById(R.id.effItemEditText2);
            holder.linearLayout = convertView.findViewById(R.id.effItemConstraintLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FutureSQLiteUser pb = list.get(position);

        holder.tv1.setText(pb.getTime());
        holder.tv2.setText(pb.getTitle());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MyFutureEdit.class);
                intent.putExtra("dayid",pb.getDayid());
                context.startActivity(intent);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调用弹框函数
                dayConfirmationDialogs(position,pb.getDayid());
                return true;
            }
        });

        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();
//        switch (pb.getImportant()) {
//            case "a":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_em);
//                break;
//            case "b":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
//                break;
//            case "c":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_em);
//                break;
//            case "d":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no);
//                break;
//            case "e":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_1);
//                break;
//            case "f":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_2);
//                break;
//            case "g":
//                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_3);
//                break;
//        }
        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2;
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
                MyFutureAdapter.this.notifyDataSetChanged();
                //删除Item对应的数据库
                helper=new FutureSQLite(context,DBName,null,version);
                helper.getReadableDatabase();
                FutureSQLiteUserDao dao=new FutureSQLiteUserDao(helper);
                dao.deleteByDayid(time);
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

