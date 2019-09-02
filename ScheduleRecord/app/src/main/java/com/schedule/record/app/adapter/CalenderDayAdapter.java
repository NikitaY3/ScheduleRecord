package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.schedule.record.app.TodayEdit;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.function.GetFunctions.TodayDeleteTask;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.schedule.record.app.R.*;

public class CalenderDayAdapter extends BaseAdapter {
    private Context context;
    private List<TodaySQLiteUser> list;
    private LayoutInflater inflater;

    private TodaySQLite helper;
    private String DBName="today";
    private int version = 1;

    private String radio2;
    private final Calendar cale1 = Calendar.getInstance();
    private ProgressBar mode1ProgressBar;

    public CalenderDayAdapter(Context context, List<TodaySQLiteUser> list, ProgressBar mode1ProgressBar) {
        this.context = context;
        this.list = list;
        //布局填充
        inflater = LayoutInflater.from(context);
        this.mode1ProgressBar = mode1ProgressBar;
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
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
        //获取数据
        final TodaySQLiteUser pb = list.get(position);
        final String dayid = pb.getDayid();
        final String time = pb.getTime();

        holder.tv1.setOnCheckedChangeListener(null);
        if(pb.isCheckbox()){ holder.tv1.setChecked(true); }else{ holder.tv1.setChecked(false); }
        holder.tv2.setText(time);
        holder.tv3.setText(pb.getTitle());

        holder.tv1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ pb.setCheckbox(true); }else{ pb.setCheckbox(false); }

                //更新数据库
                helper = new TodaySQLite(context,DBName,null,version);
                TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
                dao.updateAll(pb,context);

                //取得登录用户的ID
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences("myuser", MODE_PRIVATE);
                String nameid = sharedPreferences.getString("nameid", "");

                new Mode1ProgressBar(dao.CountBar(nameid),dao.CountAllBar(nameid),mode1ProgressBar);
            }
        });

        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<9 && hourOfDay<9){
                            radio2 = "0"+hourOfDay + ":0" + minute;
                        }else if(minute<9) {
                            radio2 = hourOfDay + ":0" + minute;
                        }else if(hourOfDay<9) {
                            radio2 = "0"+hourOfDay + ":" + minute;
                        }else {
                            radio2 = hourOfDay+":"+minute ;
                        }
                        holder.tv2.setText(radio2);
                        pb.setTime(radio2);

                        //更新数据库
                        helper = new TodaySQLite(context,DBName,null,version);
                        TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
                        CalenderDayAdapter.this.notifyDataSetChanged();
                        dao.updateAll(pb,context);

                    }
                },cale1.get(Calendar.HOUR),cale1.get(Calendar.MINUTE),true).show();
            }
        });

        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();

        holder.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, TodayEdit.class);
                intent.putExtra("dayid",dayid);
                context.startActivity(intent);
            }
        });

        holder.tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调用弹框函数
                dayConfirmationDialogs(position,dayid);
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
        AlertDialog.Builder frame1 = new AlertDialog.Builder(context);
        frame1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            //删除Item
            list.remove(position);
            CalenderDayAdapter.this.notifyDataSetChanged();

            //删除Item对应的数据库
            helper=new TodaySQLite(context,DBName,null,version);
            TodaySQLiteUserDao dao=new TodaySQLiteUserDao(helper);
            dao.deleteByDayid(time,context);

            //删除云端
            new TodayDeleteTask(uiHandler).execute("http://120.77.222.242:10024/today/deletebyid?dayId=" + time);
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
                case 44:
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
