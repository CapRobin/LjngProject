package com.zfg.org.myexample.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.db.dao.Plan;

public class PlanPopAdapter extends MBaseAdapter {

	public PlanPopAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_total_model);
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.modelName = (TextView) convertView.findViewById(R.id.textView);
		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject) {
		Plan model = (Plan) itemObject;
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.modelName.setText(model.getName());
	}

	class ViewHolder {
		TextView modelName;
	}

}
