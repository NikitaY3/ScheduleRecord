package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.FutureEdit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.function.GetFunctions.FutureDeleteTask;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import java.util.List;

public class MyInsertAdapter extends BaseAdapter {
    Context context;
    private List<TodaySQLiteUser> list;
    private LayoutInflater inflater;

    private TodaySQLite helper;
    private String DBName="today";
    private int version = 1;

    public MyInsertAdapter(Context context, List<TodaySQLiteUser> list) {
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

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //每一个item调用该方法---视图缓存机制
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_l2general_item, null);
            holder = new ViewHolder();
            holder.tv0 = convertView.findViewById(R.id.generalItemText1);
            holder.tv1 = convertView.findViewById(R.id.generalItemEditText1);
            holder.tv2 = convertView.findViewById(R.id.generalItemEditText2);
            holder.linearLayout = convertView.findViewById(R.id.generalItemLinearLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //数据
        final TodaySQLiteUser pb = list.get(position);

        holder.tv0.setText("用户ID为：" + pb.getDayid().substring(0,11));
        holder.tv1.setText(pb.getTime());
        holder.tv2.setText(pb.getTitle());
        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转跳到查看详情的页面，详情页不可编辑
//                Intent intent = new Intent(context, FutureEdit.class);
//                intent.putExtra("dayid",pb.getDayid());
//                context.startActivity(intent);
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

        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2,tv0;
        LinearLayout linearLayout;
    }

    //弹框函数
    private void dayConfirmationDialogs(final int position, final String time) {
        AlertDialog.Builder frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            //删除Item
            list.remove(position);
            MyInsertAdapter.this.notifyDataSetChanged();

            //删除Item对应的数据库
            helper=new TodaySQLite(context,DBName,null,version);
            helper.getReadableDatabase();
            TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
            dao.deleteByDayid(time,context);

            //删除云端
            new FutureDeleteTask(uiHandler).execute("http://120.77.222.242:10024/future/deletebyid?dayId=" + time);

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

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 33:
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}

