package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.activity.SBaseAdapter;
import com.zfg.org.myexample.model.RechargeHisItemModel;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-10-13.
 */
public class RechargeHisAdapter extends SBaseAdapter {


    public RechargeHisAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_rechargehis);
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.rechargenum = (TextView) convertView.findViewById(R.id.rechargenum);
        holder.rechargedate = (TextView) convertView.findViewById(R.id.rechargedate);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject,int position) {
        ViewHolder holder =  (ViewHolder) convertView.getTag();
        RechargeHisItemModel dto = (RechargeHisItemModel) itemObject;
        holder.rechargenum.setText("充值金额:"+dto.getRechargenum());
        holder.rechargedate.setText("充值日期:"+dto.getRechargedate());
    }

    class ViewHolder{
        TextView rechargenum;
        TextView rechargedate;
    }

}






