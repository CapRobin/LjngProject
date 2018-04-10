package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.R;

import java.util.List;

/**
 * Created by Administrator on 2016-10-24.
 * 选择框适配器
 */

public class MeterInfoAdapter  extends MBaseAdapter {

    public MeterInfoAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_bledata);
    }


    @Override
    protected void newView(View convertView) {
        MeterInfoAdapter.ViewHolder holder = new MeterInfoAdapter.ViewHolder();
        holder.data_name = (TextView) convertView.findViewById(R.id.data_name);
        holder.data_data = (TextView) convertView.findViewById(R.id.data_data);
        holder.data_unit = (TextView) convertView.findViewById(R.id.data_unit);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        MeterInfoAdapter.ViewHolder holder = (MeterInfoAdapter.ViewHolder) convertView.getTag();
        MeterInfo dto = (MeterInfo) itemObject;
        holder.data_name.setText(dto.getComm_address());
        holder.data_data.setText(dto.getCustomer_name());
        holder.data_unit.setText(dto.getMetertype());
    }

    class ViewHolder {
        TextView data_name;
        TextView data_data;
        TextView data_unit;
    }
}
