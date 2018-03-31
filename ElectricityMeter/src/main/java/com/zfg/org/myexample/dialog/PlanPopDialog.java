package com.zfg.org.myexample.dialog;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.adapter.PlanPopAdapter;
import com.zfg.org.myexample.db.PlanBo;
import com.zfg.org.myexample.db.dao.Plan;

public class PlanPopDialog implements OnItemClickListener {

	private Context context;
	private PopupWindow popupWindow;
	private LayoutInflater inflater;
	private CallBack callBack;

	// 3个按钮
	private ListView modelList;
	private List<Plan> data;

	public PlanPopDialog(Context context) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_total_model, null);

		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_width), // 这里宽度需要自己指定，使用
																// WRAP_CONTENT
																// 会很大
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		modelList = (ListView) view.findViewById(R.id.model_list);
		modelList.setOnItemClickListener(this);
		data = new PlanBo(context).listData();
		modelList.setAdapter(new PlanPopAdapter(context, data));

		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

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

	public Plan getModel(int position) {
		return data.get(position);
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
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
