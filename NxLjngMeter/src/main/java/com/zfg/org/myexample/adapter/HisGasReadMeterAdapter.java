package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.activity.RecordsQueryActivity;
import com.zfg.org.myexample.model.HisGasReadMeter;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisGasReadMeterAdapter
 * Describe：水表抄表记录数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 *
 */
public class HisGasReadMeterAdapter extends MBaseAdapter {
    private RecordsQueryActivity mHisInfoActivity;
    private ListView mListView;
    private int item;

    public HisGasReadMeterAdapter(Context context, List<?> data, ListView listView) {
        super(context, data, R.layout.item_hisgas_readmeter);
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
        holder.hisDate = (TextView) convertView.findViewById(R.id.hisDate);
        holder.yqzlText = (TextView) convertView.findViewById(R.id.yqzlText);
        holder.jszqText = (TextView) convertView.findViewById(R.id.jszqText);
        holder.syzeText = (TextView) convertView.findViewById(R.id.syzeText);
        holder.bdjeText = (TextView) convertView.findViewById(R.id.bdjeText);
        holder.ycjeText = (TextView) convertView.findViewById(R.id.ycjeText);
        holder.bdgqText = (TextView) convertView.findViewById(R.id.bdgqText);
        holder.ycgqText = (TextView) convertView.findViewById(R.id.ycgqText);
        convertView.setTag(holder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        HisGasReadMeter dto = (HisGasReadMeter) itemObject;
        holder.hisDate.setText("记录时间："+dto.getDATE_TIME());
        holder.yqzlText.setText(dto.getGASCONSUMPTION());
        holder.jszqText.setText(dto.getSTMTCONSU());
        holder.syzeText.setText(dto.getCRGBAL());
        holder.bdjeText.setText(dto.getSURPLUSAMTLCL());
        holder.ycjeText.setText(dto.getSURPLUSAMTRMT());
        holder.bdgqText.setText(dto.getCRGCNTLCL());
        holder.ycgqText.setText(dto.getCRGCNTRMT());
    }

    private class ViewHolder {
        TextView hisDate;
        TextView yqzlText;
        TextView jszqText;
        TextView syzeText;
        TextView bdjeText;
        TextView ycjeText;
        TextView bdgqText;
        TextView ycgqText;
    }
}
