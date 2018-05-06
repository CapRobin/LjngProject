package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.activity.MeterReadingActivity;
import com.zfg.org.myexample.activity.RecordsQueryActivity;
import com.zfg.org.myexample.activity.SwichPowerActivity;
import com.zfg.org.myexample.model.HisGasReadMeter;
import com.zfg.org.myexample.model.ValveStatusInfo;

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
public class ValveStatusInfoAdapter extends MBaseAdapter {
    private MeterReadingActivity mMeterReadingActivity;
    private int item;

    public ValveStatusInfoAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_valve_status);
        this.mMeterReadingActivity = (MeterReadingActivity) context;
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
        holder.rwmcText = (TextView) convertView.findViewById(R.id.rwmcText);
        holder.rwztText = (TextView) convertView.findViewById(R.id.rwztText);
        holder.khxmText = (TextView) convertView.findViewById(R.id.khxmText);
        holder.bhdzText = (TextView) convertView.findViewById(R.id.bhdzText);
        holder.rwbhText = (TextView) convertView.findViewById(R.id.rwbhText);
//        holder.rwcsText = (TextView) convertView.findViewById(R.id.rwcsText);
        convertView.setTag(holder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ValveStatusInfo dto = (ValveStatusInfo) itemObject;
        holder.hisDate.setText("记录时间："+dto.getSTART_TIME());

        //任务名称
        String jobStr = dto.getJOB_ACTION_DEF_ID();
        String rwmcStr = "";
        //开关阀操作
        if (jobStr.equals("101")) {
            rwmcStr = "关闭阀门";
        }else if (jobStr.equals("102")){
            rwmcStr = "打开阀门";
        }
        holder.rwmcText.setText(rwmcStr);

        //任务状态(0:等待执行；1:正在执行；2:成功；3:失败)
        int typeStr = Integer.valueOf(dto.getIS_LOCKED());
        String rwztStr = "";
        switch (typeStr){
            case 0:
                rwztStr ="等待执行";
                break;
            case 1:
                rwztStr ="正在执行";
                break;
            case 2:
                rwztStr ="成功";
                break;
            case 3:
                rwztStr ="失败";
                break;
            default:
                rwztStr ="";
                break;
        }
        holder.rwztText.setText(rwztStr);
        holder.khxmText.setText(dto.getCUSTOMER_NAME());
        holder.bhdzText.setText(dto.getMETER_SERIAL_NUM());
        holder.rwbhText.setText(dto.getID());
//        holder.rwcsText.setText(dto.getTASK_PARAM());


    }

    private class ViewHolder {
        TextView hisDate;
        TextView rwmcText;
        TextView rwztText;
        TextView khxmText;

        TextView bhdzText;
        TextView rwbhText;
//        TextView rwcsText;
    }
}
