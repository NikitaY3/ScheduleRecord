package com.schedule.record.app.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.schedule.record.app.Mode1Edit;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.DaySQLite;

import java.util.Calendar;
import java.util.List;

import static com.schedule.record.app.R.*;

public class CalenderDayAdapter extends BaseAdapter {
    private Context context;
    private List<DaySQLiteUser> list;
    //布局填充--
    private LayoutInflater inflater;

    AlertDialog.Builder frame1;
    private DaySQLite helper;
    String DBName="day_1";
    int version = 1;

    public String radio2;
    final Calendar cale1 = Calendar.getInstance();
    private ProgressBar mode1ProgressBar;

    public CalenderDayAdapter(Context context, List<DaySQLiteUser> list, ProgressBar mode1ProgressBar) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.mode1ProgressBar = mode1ProgressBar;
//        CalenderDayAdapter.this.notifyDataSetChanged();
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
        final DaySQLiteUser pb = list.get(position);
        holder.tv1.setOnCheckedChangeListener(null);
        if(pb.isCheckbox()){
            holder.tv1.setChecked(true);
        }else{
            holder.tv1.setChecked(false);
        }
        holder.tv2.setText(pb.getTime().substring(11,16));//设置时间
        holder.tv3.setText(pb.getTitle());

        holder.tv1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pb.setCheckbox(true);
//                    CalenderDayAdapter.this.notifyDataSetInvalidated();
//                    Toast.makeText(context,"点击checkBox:"+pb.isCheckbox(),Toast.LENGTH_SHORT).show();
//                    CalenderDayAdapter.this.notifyDataSetChanged();
//                    dao.updateAll(pb);
                }else{
                    pb.setCheckbox(false);
//                    Toast.makeText(context,"点击checkBox:"+pb.isCheckbox(),Toast.LENGTH_SHORT).show();
//                    CalenderDayAdapter.this.notifyDataSetChanged();
                }
                helper=new DaySQLite(context,DBName,null,version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                Toast.makeText(context,"Day"+pb.isCheckbox()+pb.getDayid(),Toast.LENGTH_SHORT).show();
                dao.updateAll(pb);
                new Mode1ProgressBar(dao.CountBar(),dao.CountAllBar(),mode1ProgressBar);
//                CalenderDayAdapter.this.notifyDataSetInvalidated();
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
                        helper=new DaySQLite(context,DBName,null,version);
                        helper.getReadableDatabase();
                        DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                        CalenderDayAdapter.this.notifyDataSetChanged();
                        dao.updateAll(pb);
                    }
                },cale1.get(Calendar.HOUR),cale1.get(Calendar.MINUTE),true).show();
            }
        });
            switch (pb.getImportant()) {
                case "a":
                    holder.linearLayout.setBackgroundResource(drawable.abaa_item_im_em);
                    break;
                case "b":
                    holder.linearLayout.setBackgroundResource(drawable.abaa_item_im_no);
                    break;
                case "c":
                    holder.linearLayout.setBackgroundResource(drawable.abaa_item_no_em);
                    break;
                case "d":
                    holder.linearLayout.setBackgroundResource(drawable.abaa_item_no_no);
                    break;
            }

        holder.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaySQLiteUser pb = list.get(position);
                Intent intent= new Intent(context, Mode1Edit.class);
                intent.putExtra("dayid",pb.getDayid());
                context.startActivity(intent);
            }
        });
        holder.tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DaySQLiteUser pb = list.get(position);
                //调用弹框函数
                dayConfirmationDialogs(position,pb.getDayid());
                Toast.makeText(context,"删除的Dayid为："+ pb.getDayid(),Toast.LENGTH_SHORT).show();
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
                //删除Item
                list.remove(position);
                CalenderDayAdapter.this.notifyDataSetChanged();
                //删除Item对应的数据库
                helper=new DaySQLite(context,DBName,null,version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
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
