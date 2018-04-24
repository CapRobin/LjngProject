package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.MeterAllInfo;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：MeterAllInfoAdapter
 * Describe：用户信息数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 */
public class MeterAllInfoAdapter extends MBaseAdapter {
    private Context mHisInfoActivity;
    private ListView mListView;
    private int item;

    public MeterAllInfoAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_meter_info);
        this.mHisInfoActivity = context;
//        this.mListView = listView;
//        //设置ListView线条的颜色
//        view.setDivider(new ColorDrawable(Color.GRAY));
//        view.setDividerHeight(1);
    }

    @Override
    public Object getItem(int position) {
        this.item = position;
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
        viewHolder.meterNum = (TextView) convertView.findViewById(R.id.meterNum);
        viewHolder.meterType = (TextView) convertView.findViewById(R.id.meterType);
        viewHolder.phoneNum = (TextView) convertView.findViewById(R.id.phoneNum);
        viewHolder.userAddress = (TextView) convertView.findViewById(R.id.userAddress);

        convertView.setTag(viewHolder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        MeterAllInfo dto = (MeterAllInfo) itemObject;
        viewHolder.userName.setText(dto.getCUSTOMER_NAME());
        viewHolder.meterNum.setText(dto.getCOMM_ADDRESS());
        viewHolder.meterType.setText(dto.getMETER_TYPE());
        viewHolder.phoneNum.setText(dto.getPHONE1());
        viewHolder.userAddress.setText(dto.getTERMINAL_ADDRESS());
//
//        if (this.item == (getCount() - 1)) {
//            //设置ListView线条的颜色
//            mListView.setDivider(new ColorDrawable(Color.GRAY));
//            mListView.setDividerHeight(1);
//        }
    }

    private class ViewHolder {
        TextView userName;
        TextView meterNum;
        TextView meterType;
        TextView phoneNum;
        TextView userAddress;
    }
}
