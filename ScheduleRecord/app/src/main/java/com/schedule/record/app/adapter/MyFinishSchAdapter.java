package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schedule.record.app.MainMyFinishSchNote;
import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.List;

public class MyFinishSchAdapter extends BaseAdapter {
    private Context context;
    private List<FinishSQLiteUser> list;
    private LayoutInflater inflater;

    public MyFinishSchAdapter(Context context, List<FinishSQLiteUser> list) {
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





    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_finishschedules_item, null);
            holder = new ViewHolder();
            holder.tv1 = convertView.findViewById(R.id.finItemEditText2);
            holder.tv2 = convertView.findViewById(R.id.finTextView2);
            holder.tv3 = convertView.findViewById(R.id.finTextView3);
            holder.linearLayout = convertView.findViewById(R.id.finItemConstraintLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FinishSQLiteUser pb = list.get(position);

        holder.tv1.setText(pb.getTitle());
        holder.tv2.setText("1 天");
        holder.tv3.setText(pb.getTime());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainMyFinishSchNote.class);
                intent.putExtra("dayid",pb.getDayid());
                context.startActivity(intent);
            }
        });

        switch (pb.getImportant()) {
            case "a":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_em);
                break;
            case "b":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_im_no);
                break;
            case "c":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_em);
                break;
            case "d":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no);
                break;
            case "e":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_1);
                break;
            case "f":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_2);
                break;
            case "g":
                holder.linearLayout.setBackgroundResource(R.drawable.abaa_item_no_no_3);
                break;
        }

        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2,tv3;
        ConstraintLayout linearLayout;
    }
}

