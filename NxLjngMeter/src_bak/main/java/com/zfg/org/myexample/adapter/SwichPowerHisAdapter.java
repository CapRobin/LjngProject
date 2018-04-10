package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfg.org.myexample.R;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.SwichPowerHisModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-10-14.
 */
public class SwichPowerHisAdapter extends MBaseAdapter {


    public SwichPowerHisAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_swichpowerhis);
    }


    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.swichpowertype = (TextView) convertView.findViewById(R.id.swichpowertype);
        holder.swichpowerdate = (TextView) convertView.findViewById(R.id.swichpowerdate);
        holder.operator = (TextView) convertView.findViewById(R.id.operator);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder =  (ViewHolder) convertView.getTag();
        SwichPowerHisModel dto = (SwichPowerHisModel) itemObject;
        holder.swichpowertype.setText(dto.getSwichpowertype());
        holder.swichpowerdate.setText(dto.getSwichpowerdate());
        holder.operator.setText(dto.getOperator());
    }

    class ViewHolder{
        TextView swichpowertype;
        TextView operator;
        TextView swichpowerdate;
    }
}
