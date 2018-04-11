package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.model.HisElectricity;

import java.util.List;

public class HisElectricityAdapter extends BaseAdapter {

    private List<HisElectricity> dataList;
    private LayoutInflater mInflater;

    public HisElectricityAdapter(Context context, List<HisElectricity> list){
        this.dataList = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_hisele,null);
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

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.hisDate.setText("抄表时间："+dataList.get(position).getDATE_TIME());
        viewHolder.zxzgText.setText("正向总功："+dataList.get(position).getZHZONG());
        viewHolder.zxjzText.setText("正向尖值："+dataList.get(position).getZHJIAN());
        viewHolder.zxfzText.setText("正向峰值："+dataList.get(position).getZHFENG());
        viewHolder.zxpzText.setText("正向平值："+dataList.get(position).getZHPING());
        viewHolder.zxgzText.setText("正向谷值："+dataList.get(position).getZHGU());
        viewHolder.fxzgText.setText("反向总功："+dataList.get(position).getFZONG());
        viewHolder.fxjzText.setText("反向尖值："+dataList.get(position).getFJIAN());
        viewHolder.fxfzText.setText("反向峰值："+dataList.get(position).getFFENG());
        viewHolder.fxpzText.setText("反向平值："+dataList.get(position).getFPING());
        viewHolder.fxgzText.setText("反向谷值："+dataList.get(position).getFGU());

        return convertView;
    }
    private class ViewHolder{
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