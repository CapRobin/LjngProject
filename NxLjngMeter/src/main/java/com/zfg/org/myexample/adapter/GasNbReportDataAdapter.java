package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.activity.RecordsQueryActivity;
import com.zfg.org.myexample.model.GasNbReportData;
import com.zfg.org.myexample.model.HisWaterReadMeter;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：HisWaterReadMeterAdapter
 * Describe：水表抄表记录数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 */
public class GasNbReportDataAdapter extends MBaseAdapter {
    private RecordsQueryActivity mRecordsQueryActivity;
    private int item;

    public GasNbReportDataAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_report_data);
        this.mRecordsQueryActivity = (RecordsQueryActivity) context;
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
        holder.xqmcText = (TextView) convertView.findViewById(R.id.xqmcText);
        holder.khxmText = (TextView) convertView.findViewById(R.id.khxmText);
        holder.bhdzText = (TextView) convertView.findViewById(R.id.bhdzText);
        holder.khdzText = (TextView) convertView.findViewById(R.id.khdzText);
        holder.ylText = (TextView) convertView.findViewById(R.id.ylText);
        holder.fyText = (TextView) convertView.findViewById(R.id.fyText);
        holder.jgText = (TextView) convertView.findViewById(R.id.jgText);
        holder.yeText = (TextView) convertView.findViewById(R.id.yeText);
        convertView.setTag(holder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        GasNbReportData dto = (GasNbReportData) itemObject;
        holder.hisDate.setText("上报时间：" + dto.getDATE_TIME());
        holder.xqmcText.setText(dto.getAREANAME());
        holder.khxmText.setText(dto.getCUSTOMER_NAME());
        holder.bhdzText.setText(dto.getMETER_SERIAL_NUM());
        holder.khdzText.setText(dto.getCUSTOMER_ADDRESS());
        holder.ylText.setText(dto.getUSENUM());
        holder.fyText.setText(dto.getUSENUM());
        holder.jgText.setText(dto.getBCPRICE());
        holder.yeText.setText(dto.getLAVEMONEY());
    }

    private class ViewHolder {
        TextView hisDate;
        TextView xqmcText;
        TextView khxmText;
        TextView bhdzText;
        TextView khdzText;
        TextView ylText;
        TextView fyText;
        TextView jgText;
        TextView yeText;
    }
}
