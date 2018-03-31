package com.zfg.org.myexample.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.TimeModel;
import com.zfg.org.myexample.utils.Config;

public class PlanTimeAdapter extends MBaseAdapter {

	public PlanTimeAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_plan_time);
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.itemDelta = (TextView) convertView.findViewById(R.id.time_delta);
		holder.time = (TextView) convertView.findViewById(R.id.time);
		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		TimeModel set = (TimeModel) itemObject;
		holder.itemDelta.setText(set.getName());
		final String key = "clock" + set.getType() + set.getSubType();
		holder.time.setText(Config.getProperty(key) + "");
	}

	class ViewHolder {
		TextView itemDelta;
		TextView time;
	}
}
