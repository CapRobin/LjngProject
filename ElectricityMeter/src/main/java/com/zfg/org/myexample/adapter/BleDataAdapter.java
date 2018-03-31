package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.BleDataModel;
import com.zfg.org.myexample.model.SwichPowerHisModel;

import java.util.List;

/**
 * Created by Administrator on 2016-10-17.
 */

public class BleDataAdapter extends MBaseAdapter {


    public BleDataAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_bledata);
    }


    @Override
    protected void newView(View convertView) {
        BleDataAdapter.ViewHolder holder = new BleDataAdapter.ViewHolder();
        holder.data_name = (TextView) convertView.findViewById(R.id.data_name);
        holder.data_data = (TextView) convertView.findViewById(R.id.data_data);
        holder.data_unit = (TextView) convertView.findViewById(R.id.data_unit);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        BleDataAdapter.ViewHolder holder = (BleDataAdapter.ViewHolder) convertView.getTag();
        BleDataModel dto = (BleDataModel) itemObject;
        holder.data_name.setText(dto.getDataname());
        holder.data_data.setText(dto.getDatadata());
        holder.data_unit.setText(dto.getDataunit());
    }

    class ViewHolder {
        TextView data_name;
        TextView data_data;
        TextView data_unit;
    }
}
