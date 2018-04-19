package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.activity.RecordsQueryActivity;
import com.zfg.org.myexample.model.HisEleReadMeter;
import com.zfg.org.myexample.model.HisWaterReadMeter;
import com.zfg.org.myexample.model.ReadDataHisGasItemModel;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：HisWaterReadMeterAdapter
 * Describe：水表抄表记录数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 */
public class HisWaterReadMeterAdapter extends MBaseAdapter {
    private RecordsQueryActivity mHisInfoActivity;
    private ListView mListView;
    private int item;

    public HisWaterReadMeterAdapter(Context context, List<?> data, ListView listView) {
        super(context, data, R.layout.item_hiswater_readmeter);
        this.mHisInfoActivity = (RecordsQueryActivity) context;
        this.mListView = listView;
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
        ViewHolder holder = new ViewHolder();
        holder.yszlText = (TextView) convertView.findViewById(R.id.yszlText);
        holder.jszqText = (TextView) convertView.findViewById(R.id.jszqText);
        holder.gscsText = (TextView) convertView.findViewById(R.id.gscsText);
        holder.hisDate = (TextView) convertView.findViewById(R.id.hisDate);
        convertView.setTag(holder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        HisWaterReadMeter dto = (HisWaterReadMeter) itemObject;
        holder.yszlText.setText(dto.getCONSU());
        holder.jszqText.setText(dto.getSTMTCONSU());
        holder.gscsText.setText(dto.getRECHARGECOUNTS());
        holder.hisDate.setText("记录时间："+dto.getDATE_TIME());
    }

    private class ViewHolder {
        TextView yszlText;
        TextView jszqText;
        TextView gscsText;
        TextView hisDate;
    }
}
