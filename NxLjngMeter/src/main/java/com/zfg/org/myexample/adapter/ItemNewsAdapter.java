package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.dto.QuestionDto;
import com.zfg.org.myexample.fragment.HelpFragment;

import java.util.List;

public class ItemNewsAdapter extends BaseAdapter {

	private List<List<QuestionDto>> data;
	private BasicActivity context;
	private LayoutInflater inflater;

	public ItemNewsAdapter(Context context, List<List<QuestionDto>> data) {
		this.data = data;
		this.context = (BasicActivity) context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		NewsAdapter infoAdapter;
		List<QuestionDto> adapterViewData = data.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.item_information_layout, null);
			holder = new ViewHolder();
			holder.date = (TextView) view.findViewById(R.id.infor_date);
			holder.information = (ListView) view.findViewById(R.id.infor_list);
			infoAdapter = new NewsAdapter(context, adapterViewData);
			holder.information.setAdapter(infoAdapter);
			holder.date.setText(adapterViewData.get(0).parent);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			// 这里为了重用item中listview中的item内容，选择了重用adater，提高了性能
			infoAdapter = (NewsAdapter) holder.information.getAdapter();
			holder.date.setText(adapterViewData.get(0).parent);
			infoAdapter.setData(adapterViewData);
			infoAdapter.notifyDataSetChanged();
		}
		holder.information.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView,
					View convertView, int position, long id) {
				NewsAdapter adapter = (NewsAdapter) adapterView.getAdapter();
				QuestionDto news = adapter.getItem(position);
				showAboutDialog(news.index + "");
			}
		});
		return view;
	}

	private void showAboutDialog(String position) {
		String tag = "help_dialog";
		FragmentManager manager = context.getSupportFragmentManager();
		HelpFragment fragment = (HelpFragment) manager.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = HelpFragment.getInstance(position);
		}
		fragment.show(manager, tag);
	}

	class ViewHolder {
		TextView date;
		ListView information;
	}
}
