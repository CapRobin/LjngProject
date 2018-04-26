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
import com.zfg.org.myexample.application.AppApplication;
import com.zfg.org.myexample.model.HisEleOption;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：HisEleOptionAdapter
 * Describe：电表操作记录数据适配
 * Date：2018-04-12 16:00:54
 * Author: CapRobin@yeah.net
 */

public class HisEleOptionAdapter extends MBaseAdapter {
    private RecordsQueryActivity mHisInfoActivity;

    public HisEleOptionAdapter(Context context, List<?> data, ListView view) {
        super(context, data, R.layout.item_hisele_option);
        this.mHisInfoActivity = (RecordsQueryActivity) context;

        //设置ListView线条的颜色
        view.setDivider(new ColorDrawable(Color.GRAY));
        view.setDividerHeight(1);
    }


    @Override
    protected void newView(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.optionMeterText = (TextView) convertView.findViewById(R.id.optionMeterText);
        viewHolder.optionUserText = (TextView) convertView.findViewById(R.id.optionUserText);
        viewHolder.optionContentText = (TextView) convertView.findViewById(R.id.optionContentText);
        viewHolder.optionTypeText = (TextView) convertView.findViewById(R.id.optionTypeText);
        viewHolder.optionDateText = (TextView) convertView.findViewById(R.id.optionDateText);
        convertView.setTag(viewHolder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        HisEleOption dto = (HisEleOption) itemObject;

        String meterNum = dto.getPARAMS();
        int meterType = Integer.parseInt(dto.getCATEGORY());
        if(meterNum == null || meterNum.equals("null")){
            viewHolder.optionMeterText.setText("操作表号：" + dto.getPARAMS());
        }else {
            viewHolder.optionMeterText.setText("操作" + dto.getPARAMS());
        }
        viewHolder.optionUserText.setText("操作员：" + dto.getUSER_ID());
        viewHolder.optionContentText.setText(dto.getACTION_KEY());
//        if(meterType == 4){
//            viewHolder.optionTypeText.setText("仪表类型：电表");
//        }

        switch (AppApplication.userType){
            case 1:
                viewHolder.optionTypeText.setText("仪表类型：水表");
                break;
            case 2:
                viewHolder.optionTypeText.setText("仪表类型：电表");
                break;
            case 3:
                viewHolder.optionTypeText.setText("仪表类型：气表");
                break;
            case 4:
                viewHolder.optionTypeText.setText("仪表类型：热表");
                break;
        }
        viewHolder.optionDateText.setText(dto.getOPERATE_TIME());
    }

    private class ViewHolder {
        TextView optionMeterText;
        TextView optionUserText;
        TextView optionContentText;
        TextView optionTypeText;
        TextView optionDateText;
    }
}