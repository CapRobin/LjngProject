package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.model.ReadDataHisGasItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016-10-24.
 */

public class ReadDataGasHisAdapter extends MBaseAdapter {

    public ReadDataGasHisAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_readgasdatahis);
    }

    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.GasConsumption = (TextView) convertView.findViewById(R.id.GasConsumption);
        holder.StmtConsu = (TextView) convertView.findViewById(R.id.StmtConsu);
        holder.CrgBal = (TextView) convertView.findViewById(R.id.CrgBal);
        holder.SurplusAmtLcl = (TextView) convertView.findViewById(R.id.SurplusAmtLcl);
        holder.SurplusAmtRmt = (TextView) convertView.findViewById(R.id.SurplusAmtRmt);
        holder.CrgCntLcl = (TextView) convertView.findViewById(R.id.CrgCntLcl);
        holder.CrgCntRmt = (TextView) convertView.findViewById(R.id.CrgCntRmt);
        holder.DATE_TIME = (TextView) convertView.findViewById(R.id.DATE_TIME);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, Object itemObject) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ReadDataHisGasItemModel dto = (ReadDataHisGasItemModel) itemObject;
        holder.GasConsumption.setText("用气量示值" + dto.getGASCONSUMPTION()+"m³");
        holder.StmtConsu.setText("结算周期量示值" + dto.getSTMTCONSU()+"m³");
        holder.CrgBal.setText("剩余金额(总)" + dto.getCRGBAL() +"元");
        holder.SurplusAmtLcl.setText("剩余金额(本地)" + dto.getSURPLUSAMTLCL() +"元");
        holder.SurplusAmtRmt.setText("剩余金额(远程)" + dto.getSURPLUSAMTRMT() +"元");
        holder.CrgCntLcl.setText("购气次数(本地)" + dto.getCRGCNTLCL() +"次");
        holder.CrgCntRmt.setText("购气次数(远程)" + dto.getCRGCNTRMT() +"次");
        holder.DATE_TIME.setText("时间" + dto.getDATE_TIME());
    }


//        用气量示值    ——GasConsumption,
//        结算周期量市值——StmtConsu,
//        剩余金额总    ——CrgBal,
//        剩余金额本地  ——SurplusAmtLcl ,
//        剩余金额远程  ——SurplusAmtRmt,
//        购气次数本地  ——CrgCntLcl,
//        购气次数远程  ——CrgCntRmt

    class ViewHolder {
        TextView GasConsumption;
        TextView StmtConsu;
        TextView CrgBal;
        TextView SurplusAmtLcl;
        TextView SurplusAmtRmt;
        TextView CrgCntLcl;
        TextView CrgCntRmt;
        TextView DATE_TIME;
    }
}

