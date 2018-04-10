package com.zfg.org.myexample.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dialog.adapter.DialogListAdapter;
import com.zfg.org.myexample.model.MapModel;

public abstract class BaseListDialog extends Dialog implements OnItemClickListener{
	
	private TextView titleView;
	private ListView dataList;
	
	private Context context;
	private CallBack callBack;
	protected List<MapModel> data;
	
	public BaseListDialog(Context context) {
		super(context, R.style.Dialog);
		this.context = context;
		setContentView(R.layout.dialog_list_layout);
		this.setCanceledOnTouchOutside(true);
		data = new ArrayList<MapModel>();
		initData(data);
		initView();
	}
	
	private void initView(){
		titleView = (TextView) findViewById(R.id.title);
		dataList = (ListView) findViewById(R.id.content_list);
		dataList.setOnItemClickListener(this);
		dataList.setAdapter(new DialogListAdapter(context, data));
	}
	
	protected abstract void initData(List<MapModel> data);

	public MapModel getModel(int position) {
		return data.get(position);
	}
	
	public String getType(int type){
		return data.get(type).getValue();
	}

	public interface CallBack {
		void callBack(int position, String model);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (callBack != null) {
			callBack.callBack(position,data.get(position).getValue());
		}
		dismiss();
	}
	
	public void setTitle(String title){
		this.titleView.setText(title);
	}
	
	public void setCall(CallBack callBack){
		this.callBack = callBack;
	}
}
