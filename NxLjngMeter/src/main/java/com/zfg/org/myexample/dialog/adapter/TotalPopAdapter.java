package com.zfg.org.myexample.dialog.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.MBaseAdapter;
import com.zfg.org.myexample.model.MapModel;

public class TotalPopAdapter extends MBaseAdapter {

	public TotalPopAdapter(Context context, List<?> data) {
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
		MapModel model = (MapModel) itemObject;
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.modelName.setText(model.getValue());
	}

	public class ViewHolder {
		TextView modelName;
	}

}
