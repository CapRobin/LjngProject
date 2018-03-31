package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.R;

import java.util.List;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MeterInfoCheckAdapter extends MBaseAdapter {

    public MeterInfoCheckAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_meterinfocheck_layout);
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.icon = (ImageView) convertView.findViewById(R.id.check);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.metertype = (TextView) convertView.findViewById(R.id.metertype);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        MeterInfoCheckModel model = (MeterInfoCheckModel) itemObject;
        holder.name.setText(model.value);
        holder.metertype.setText(model.metertype);
        if(model.isCheck){
            holder.icon.setImageResource(R.mipmap.icon_check);
        }else{
            holder.icon.setImageResource(R.mipmap.icon_uncheck);
        }
    }

    class ViewHolder{
        ImageView icon;
        TextView name;
        TextView metertype;
    }
}
