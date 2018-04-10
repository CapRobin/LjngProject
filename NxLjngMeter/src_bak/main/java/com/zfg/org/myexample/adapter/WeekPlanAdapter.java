package com.zfg.org.myexample.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.SBaseAdapter;
import com.zfg.org.myexample.model.ClockModel;

public class WeekPlanAdapter extends SBaseAdapter{
	
	private String[] week = {"一","二","三","四","五","六","日"};

	public WeekPlanAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_plan_week);
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.date = (TextView) convertView.findViewById(R.id.date);
		holder.clock = (ImageView) convertView.findViewById(R.id.date_bg);
		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject,int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		ClockModel model = (ClockModel) itemObject;
		if(model.isLabel()){
			holder.date.setVisibility(View.VISIBLE);
			holder.clock.setVisibility(View.GONE);
			holder.date.setText(week[model.getWeek()-1]);
			convertView.setBackgroundResource(R.color.page_content_color);
		}else{
			holder.date.setText("");
			holder.date.setVisibility(View.VISIBLE);
			holder.clock.setVisibility(View.VISIBLE);
			if(model.getEnable() == 1){
				holder.clock.setImageResource(R.mipmap.icon_clock_on);
			}else if(model.getEnable() == 0){
				holder.clock.setImageResource(R.mipmap.icon_clock_of);
			}else{
				holder.clock.setVisibility(View.GONE);
			}
			convertView.setBackgroundResource(R.color.white);
		}			
	}


	class ViewHolder{
		TextView date;
		ImageView clock;
	}
}
