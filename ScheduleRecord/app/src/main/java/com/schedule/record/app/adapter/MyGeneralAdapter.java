package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.mainmy.MainMyL2GInsert;
import com.schedule.record.app.sqlite.RemarkSQLite;
import com.schedule.record.app.sqlite.dao.RemarkSQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;

import java.util.List;

public class MyGeneralAdapter extends BaseAdapter {
    Context context;
    private List<AuthoritySQLiteUser> list;
    private LayoutInflater inflater;

    private RemarkSQLite helper;
    private String DBName="remark";
    private int version = 1;

    public MyGeneralAdapter(Context context, List<AuthoritySQLiteUser> list) {
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

    //每一个item调用该方法---视图缓存机制
    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_l2general_user_item, null);
            holder = new ViewHolder();

            holder.tv1 = convertView.findViewById(R.id.myGUserText1);
            holder.tv2 = convertView.findViewById(R.id.myGUserText2);
            holder.linearLayout = convertView.findViewById(R.id.myGUserLinearLayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //数据
        final AuthoritySQLiteUser pb = list.get(position);

        String gnameId = pb.getGnameId();

        //查询备注名
        helper = new RemarkSQLite(context,DBName,null,version);
        RemarkSQLiteUserDao dao = new RemarkSQLiteUserDao(helper);
        String name = dao.queryRemarkName(gnameId);

        if (!name.equals("")) {
            holder.tv1.setText(name);
        } else {
            holder.tv1.setText("第" + (position + 1) + "个用户");
        }

        holder.tv2.setText(gnameId);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainMyL2GInsert.class);
                intent.putExtra("gnameid",pb.getGnameId());

                context.startActivity(intent);
            }
        });

        return convertView;
    }
    static class ViewHolder{
        TextView tv1,tv2;
        LinearLayout linearLayout;
    }
}

