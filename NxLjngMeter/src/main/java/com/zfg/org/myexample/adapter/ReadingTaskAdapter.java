package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.ReadingTaskItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016-11-21.
 */

public class ReadingTaskAdapter  extends MBaseAdapter {

    public ReadingTaskAdapter(Context context, List<?> data) {
        super(context, data, R.layout.item_readingtask);
    }


    @Override
    protected void newView(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.CONSU = (TextView) convertView.findViewById(R.id.CONSU);
        holder.CONSUNAME = (TextView) convertView.findViewById(R.id.CONSUNAME);
        holder.TOTAL = (TextView) convertView.findViewById(R.id.TOTAL);
        holder.HADCOUNT = (TextView) convertView.findViewById(R.id.HADCOUNT);
        holder.FAILED = (TextView) convertView.findViewById(R.id.FAILED);
        holder.RATE = (TextView) convertView.findViewById(R.id.RATE);
        holder.METER_TYPE = (TextView) convertView.findViewById(R.id.METER_TYPE);
        convertView.setTag(holder);

    }

    @Override
    protected void holderView(View convertView, Object itemObject) {

        ViewHolder holder =  (ViewHolder) convertView.getTag();
        ReadingTaskItemModel dto = (ReadingTaskItemModel) itemObject;
        holder.CONSU.setText(dto.getCONSU());
        holder.CONSUNAME.setText(dto.getCONSUNAME());
        holder.TOTAL.setText(dto.getTOTAL() +" 个");
        holder.HADCOUNT.setText(dto.getHADCOUNT()+" 个" );
        holder.FAILED.setText(dto.getFAILED() +" 个");
        holder.RATE.setText(dto.getRATE() +"%");
        switch (Integer.parseInt(dto.getMETER_TYPE())) {
            case 120:
                holder.METER_TYPE.setText("水表");
                break;
            case 301:
                holder.METER_TYPE.setText("气表");
                break;
            default:
                holder.METER_TYPE.setText(dto.getMETER_TYPE());
        }

    }

    class ViewHolder{
        ImageView IMG;
        TextView CONSU;//小区或台区编号
        TextView CONSUNAME;//小区或台区名称
        TextView TOTAL ; //该台区下总表数量
        TextView HADCOUNT ;//统计已抄表数量
        TextView FAILED ;//统计已抄表数量
        TextView RATE ; //成功比例
        TextView METER_TYPE ;//表类型
    }


}
