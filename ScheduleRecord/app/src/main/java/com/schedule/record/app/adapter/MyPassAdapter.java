package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.function.GetFunctions.PassDeleteTask;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.PassSQLiteUserDao;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;

import java.util.List;

public class MyPassAdapter extends BaseAdapter {
    private Context context;
    private List<PassSQLiteUser> list;
    private LayoutInflater inflater;

    private int version = 1;

    private PassSQLite helper;
    private String DBName1="pass";

    public MyPassAdapter(Context context, List<PassSQLiteUser> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_3pass_item, null);
            holder = new ViewHolder();
            holder.tv1 = convertView.findViewById(R.id.passItemEditText2);
            holder.tv2 = convertView.findViewById(R.id.passTextView2);
            holder.tv3 = convertView.findViewById(R.id.passTextView3);
            holder.linearLayout = convertView.findViewById(R.id.passItemConstraintLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //数据
        final PassSQLiteUser pb = list.get(position);

        String DBName = "finish";
        FinishSQLite helperf = new FinishSQLite(context, DBName, null, version);
        FinishSQLiteUserDao dao=new FinishSQLiteUserDao(helperf);
        int countFinish = dao.CountFinishByDayid(pb.getDayid());

        holder.tv1.setText(pb.getTitle());
        holder.tv2.setText(countFinish+" 天");
        holder.tv3.setText("失效日期：" + pb.getPassday().substring(0,10));
        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();

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
        TextView tv1,tv2,tv3;
        LinearLayout linearLayout;
    }
    //弹框函数
    private void dayConfirmationDialogs(final int position, final String time) {
        AlertDialog.Builder frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //删除Item对应的数据库
                helper = new PassSQLite(context,DBName1,null,version);
                PassSQLiteUserDao dao=new PassSQLiteUserDao(helper);
                dao.deleteByDayid(time);
                //删除Item
                list.remove(position);
                MyPassAdapter.this.notifyDataSetChanged();

                //删除云端
                new PassDeleteTask(uiHandler).execute("http://120.77.222.242:10024/pass/deletebyid?dayId=" + time);

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
                case 66:
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}

