package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dto.QuestionDto;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

	private List<QuestionDto> data;
	private LayoutInflater inflater;

	public NewsAdapter(Context context, List<QuestionDto> data) {
		this.data = data;
		this.inflater = LayoutInflater.from(context);
	}

	public void setData(List<QuestionDto> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public QuestionDto getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.item_txt, null);
			holder = new ViewHolder();
			holder.titleText = (TextView) view.findViewById(R.id.title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.titleText.setText(data.get(position).name);
		return view;
	}

	class ViewHolder {
		TextView titleText;
	}
}
