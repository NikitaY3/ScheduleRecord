package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;

import java.util.List;

public class MyPassAdapter extends BaseAdapter {
    private Context context;
    private List<PassSQLiteUser> list;
    private LayoutInflater inflater;

    private FinishSQLite helperf;
    private String DBName="finish";
    private int version = 1;

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
        final PassSQLiteUser pb = list.get(position);

        holder.tv1.setText(pb.getTitle());

        helperf=new FinishSQLite(context,DBName,null,version);
        FinishSQLiteUserDao dao=new FinishSQLiteUserDao(helperf);
        int countFinish = dao.CountFinishByDayid(pb.getDayid());

        holder.tv2.setText(countFinish+" 天");
        holder.tv3.setText("失效日期："+pb.getPassday());

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainMy2FinishSchNote.class);
//                intent.putExtra("dayid",pb.getDayid());
//                context.startActivity(intent);
//            }
//        });

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
        TextView tv1,tv2,tv3;
        LinearLayout linearLayout;
    }
}

