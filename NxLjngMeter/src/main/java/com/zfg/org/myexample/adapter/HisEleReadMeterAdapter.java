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

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：HisEleReadMeterAdapter
 * Describe：电表抄表记录数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 */
public class HisEleReadMeterAdapter extends MBaseAdapter {
    private RecordsQueryActivity mHisInfoActivity;
    private ListView mListView;
    private int item;

    public HisEleReadMeterAdapter(Context context, List<?> data, ListView listView) {
        super(context, data, R.layout.item_hisele_readmeter);
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
        ViewHolder viewHolder = new ViewHolder();


//        viewHolder.optionMeterText = (TextView) convertView.findViewById(R.id.optionMeterText);
//        viewHolder.optionUserText = (TextView) convertView.findViewById(R.id.optionUserText);
//        viewHolder.optionContentText = (TextView) convertView.findViewById(R.id.optionContentText);
//        viewHolder.optionTypeText = (TextView) convertView.findViewById(R.id.optionTypeText);
//        viewHolder.optionDateText = (TextView) convertView.findViewById(R.id.optionDateText);

        viewHolder.hisDate = (TextView) convertView.findViewById(R.id.hisDate);
        viewHolder.zxzgText = (TextView) convertView.findViewById(R.id.zxzgText);
        viewHolder.zxjzText = (TextView) convertView.findViewById(R.id.zxjzText);
        viewHolder.zxfzText = (TextView) convertView.findViewById(R.id.zxfzText);
        viewHolder.zxpzText = (TextView) convertView.findViewById(R.id.zxpzText);
        viewHolder.zxgzText = (TextView) convertView.findViewById(R.id.zxgzText);
        viewHolder.fxzgText = (TextView) convertView.findViewById(R.id.fxzgText);
        viewHolder.fxjzText = (TextView) convertView.findViewById(R.id.fxjzText);
        viewHolder.fxfzText = (TextView) convertView.findViewById(R.id.fxfzText);
        viewHolder.fxpzText = (TextView) convertView.findViewById(R.id.fxpzText);
        viewHolder.fxgzText = (TextView) convertView.findViewById(R.id.fxgzText);

        convertView.setTag(viewHolder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        HisEleReadMeter dto = (HisEleReadMeter) itemObject;
        viewHolder.hisDate.setText("抄表时间：" + dto.getDATE_TIME());
        viewHolder.zxzgText.setText("正向总功：" + dto.getZHZONG());
        viewHolder.zxjzText.setText("正向尖值：" + dto.getZHJIAN());
        viewHolder.zxfzText.setText("正向峰值：" + dto.getZHFENG());
        viewHolder.zxpzText.setText("正向平值：" + dto.getZHPING());
        viewHolder.zxgzText.setText("正向谷值：" + dto.getZHGU());
        viewHolder.fxzgText.setText("反向总功：" + dto.getFZONG());
        viewHolder.fxjzText.setText("反向尖值：" + dto.getFJIAN());
        viewHolder.fxfzText.setText("反向峰值：" + dto.getFFENG());
        viewHolder.fxpzText.setText("反向平值：" + dto.getFPING());
        viewHolder.fxgzText.setText("反向谷值：" + dto.getFGU());

        if (this.item == (getCount() - 1)) {
            //设置ListView线条的颜色
            mListView.setDivider(new ColorDrawable(Color.GRAY));
            mListView.setDividerHeight(1);
        }
    }

    private class ViewHolder {
        TextView hisDate;
        TextView zxzgText;
        TextView zxjzText;
        TextView zxfzText;
        TextView zxpzText;
        TextView zxgzText;
        TextView fxzgText;
        TextView fxjzText;
        TextView fxfzText;
        TextView fxpzText;
        TextView fxgzText;
    }
}
