package com.schedule.record.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.FinishEdit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.List;

public class MyFinishAdapter extends BaseAdapter {
    private Context context;
    private List<FinishSQLiteUser> list;
    private LayoutInflater inflater;

    private AlertDialog.Builder frame1;
    private FinishSQLite helper;
    private String DBName="finish";
    private int version = 1;

    public MyFinishAdapter(Context context, List<FinishSQLiteUser> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_2finish_item, null);
            holder = new ViewHolder();
            holder.tv1 = convertView.findViewById(R.id.finItemCheckBox);
            holder.tv2 = convertView.findViewById(R.id.finItemEditText1);
            holder.tv3 = convertView.findViewById(R.id.finItemEditText2);
            holder.linearLayout = convertView.findViewById(R.id.finItemConstraintLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FinishSQLiteUser pb = list.get(position);

        if(pb.getCheckbox()){
            holder.tv1.setChecked(true);
        }else{
            holder.tv1.setChecked(false);
        }
        holder.tv2.setText(pb.getTime());
        holder.tv3.setText(pb.getTitle());
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, FinishEdit.class);
//                intent.putExtra("finish_id",pb.getFinishId());
//                context.startActivity(intent);
//            }
//        });
        holder.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, FinishEdit.class);
                intent.putExtra("finish_id",pb.getFinishId());
                context.startActivity(intent);
            }
        });

        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();

        holder.tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调用弹框函数
                dayConfirmationDialogs(position,pb.getFinishId());
                return true;
            }
        });

        return convertView;
    }
    static class ViewHolder{
        CheckBox tv1;
        TextView tv2,tv3;
        LinearLayout linearLayout;
    }

    //弹框函数
    private void dayConfirmationDialogs(final int position, final String time) {
        frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除Item对应的数据库
                helper=new FinishSQLite(context,DBName,null,version);
                FinishSQLiteUserDao dao=new FinishSQLiteUserDao(helper);
                dao.deleteByFinishid(time);
                //删除Item
                list.remove(position);
                MyFinishAdapter.this.notifyDataSetChanged();
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

