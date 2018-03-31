package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.ReadDataHisWaterItemModel;
import com.zfg.org.myexample.R;

import java.util.List;

/**
 * Created by Administrator on 2016-10-14.
 */
public class ReadDataWaterHisAdapter extends MBaseAdapter {

    public ReadDataWaterHisAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_readwaterdatahis);
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.DATE_TIME = (TextView) convertView.findViewById(R.id.DATE_TIME);
        holder.CONSU = (TextView) convertView.findViewById(R.id.CONSU);
        holder.STMTCONSU = (TextView) convertView.findViewById(R.id.STMTCONSU);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder =  (ViewHolder) convertView.getTag();
        ReadDataHisWaterItemModel dto = (ReadDataHisWaterItemModel) itemObject;

        holder.DATE_TIME.setText("抄表日期"+dto.getDATE_TIME());
        holder.CONSU.setText("当前用水量 "+dto.getCONSU() +" m³");
        holder.STMTCONSU.setText("结算日用水量 "+dto.getSTMTCONSU() +" m³");
    }

    class ViewHolder{
        TextView DATE_TIME;
        TextView CONSU;
        TextView STMTCONSU ;
    }
}
