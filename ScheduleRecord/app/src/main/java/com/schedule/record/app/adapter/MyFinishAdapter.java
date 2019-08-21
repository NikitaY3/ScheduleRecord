package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.Mode2FinishEdit;
import com.schedule.record.app.R;
import com.schedule.record.app.function.ColorImportant;
import com.schedule.record.app.mainmy.MainMy2FinishSchNote;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;

import java.util.List;

public class MyFinishAdapter extends BaseAdapter {
    private Context context;
    private List<FinishSQLiteUser> list;
    private LayoutInflater inflater;

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

    @SuppressLint("InflateParams")
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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Mode2FinishEdit.class);
                intent.putExtra("finishid",pb.getFinishId());
                context.startActivity(intent);
            }
        });

        new ColorImportant(pb.getImportant(),holder.linearLayout).LinearLayoutSet();

        return convertView;
    }
    static class ViewHolder{
        CheckBox tv1;
        TextView tv2,tv3;
        LinearLayout linearLayout;
    }
}

