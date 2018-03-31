package com.zfg.org.myexample.dialog;

import java.util.ArrayList;
import java.util.List;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dialog.adapter.TotalPopAdapter;
import com.zfg.org.myexample.model.MapModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 模式弹出选择框
 */
public abstract class GPopDialog implements OnItemClickListener {

	private Context context;
	private PopupWindow popupWindow;
	private LayoutInflater inflater;
	private CallBack callBack;

	// 3个按钮
	private ListView modelList;
	protected TotalPopAdapter adapter;
	protected List<MapModel> data;

	public GPopDialog(Context context) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_total_model, null);

		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_width),
				context.getResources().getDimensionPixelSize(
						R.dimen.popmenu_height));
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		modelList = (ListView) view.findViewById(R.id.model_list);
		modelList.setOnItemClickListener(this);
		data = new ArrayList<MapModel>();
		initData(data);
		adapter = new TotalPopAdapter(context, data);
		modelList.setAdapter(adapter);

		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	protected abstract void initData(List<MapModel> data);

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(
				parent,
				context.getResources().getDimensionPixelSize(
						R.dimen.popmenu_xoff),
				// 保证尺寸是根据屏幕像素密度来的
				context.getResources().getDimensionPixelSize(
						R.dimen.popmenu_yoff));

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	public MapModel getModel(int position) {
		return data.get(position);
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	public int size() {
		return data.size();
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	public interface CallBack {
		void callBack(int model);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (callBack != null) {
			callBack.callBack(position);
		}
		dismiss();
	}
}
