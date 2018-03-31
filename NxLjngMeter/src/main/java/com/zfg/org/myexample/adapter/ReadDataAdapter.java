package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.model.ReadDataItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016-10-24.
 */

public class ReadDataAdapter  extends MBaseAdapter {

    public ReadDataAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_readdata);
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.dataname = (TextView) convertView.findViewById(R.id.dataname);
        holder.datadata = (TextView) convertView.findViewById(R.id.datadata);
        holder.dataunit = (TextView) convertView.findViewById(R.id.dataunit);
        convertView.setTag(holder);
    }


    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder =  (ViewHolder) convertView.getTag();
        ReadDataItemModel dto = (ReadDataItemModel) itemObject;

        holder.dataname.setText(dto.getDataname());
        holder.datadata.setText(dto.getDatadata());
        holder.dataunit.setText(dto.getDataunit());
    }

    class ViewHolder{
        TextView dataname;
        TextView datadata;
        TextView dataunit ;
    }
}
