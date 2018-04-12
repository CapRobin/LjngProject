package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.model.HisEleOption;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisEleOptionAdapter
 * Describe：电表操作记录数据适配
 * Date：2018-04-12 16:00:54
 * Author: CapRobin@yeah.net
 *
 */
public class HisEleOptionAdapter_bak extends BaseAdapter {

    private List<HisEleOption> dataList;
    private LayoutInflater mInflater;

    public HisEleOptionAdapter_bak(Context context, List<HisEleOption> list){
        this.dataList = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_hisele_option,null);
            viewHolder.optionMeterText = (TextView) convertView.findViewById(R.id.optionMeterText);
            viewHolder.optionUserText = (TextView) convertView.findViewById(R.id.optionUserText);
            viewHolder.optionContentText = (TextView) convertView.findViewById(R.id.optionContentText);
            viewHolder.optionTypeText = (TextView) convertView.findViewById(R.id.optionTypeText);
            viewHolder.optionDateText = (TextView) convertView.findViewById(R.id.optionDateText);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.optionMeterText.setText("操作"+dataList.get(position).getPARAMS());
        viewHolder.optionUserText.setText("操作员："+dataList.get(position).getUSER_ID());
        viewHolder.optionContentText.setText(dataList.get(position).getACTION_KEY());
        viewHolder.optionTypeText.setText("操作种类："+dataList.get(position).getCATEGORY());
        viewHolder.optionDateText.setText(dataList.get(position).getOPERATE_TIME());

        return convertView;
    }
    private class ViewHolder{
        TextView optionMeterText;
        TextView optionUserText;
        TextView optionContentText;
        TextView optionTypeText;
        TextView optionDateText;
    }
}