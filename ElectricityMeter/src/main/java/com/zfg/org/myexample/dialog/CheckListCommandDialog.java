package com.zfg.org.myexample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.ComBleActivity;
import com.zfg.org.myexample.MainActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.dto.CheckModel;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

public class CheckListCommandDialog extends Dialog implements
		OnItemClickListener {

	private TextView titleView;
	private ListView dataList;
	private Button okBtn;

	private Context context;
	private CallBack callBack;
	protected List<CheckModel> data;
	private CheckAdapter adapter;

	public CheckListCommandDialog(Context context) {
		super(context, R.style.Dialog);
		this.context = context;
		setContentView(R.layout.dialog_commandlist_check);
		this.setCanceledOnTouchOutside(true);
		data = new ArrayList<CheckModel>();
//		initData(data);
		initView();
	}

	private void initView() {
		adapter = new CheckAdapter(context, data);
		titleView = (TextView) findViewById(R.id.title);
		dataList = (ListView) findViewById(R.id.content_list);
		okBtn = (Button) findViewById(R.id.ok_btn);
		dataList.setOnItemClickListener(this);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (callBack != null) {
					callBack.callBack(data);
				}
				dismiss();
			}
		});
		dataList.setAdapter(adapter);
	}

	public void initData() {
		data.clear();
		CheckModel model;
//				= new CheckModel("0", "抄控器无线模块参数设置", false);
//		data.add(model);
//		model = new CheckModel("1", "抄控器唤醒参数设置", false);
//		data.add(model);
//		model = new CheckModel("2", "抄控器模式设置", false);
//		data.add(model);
//		model = new CheckModel("3", "抄控器唤醒地址设置", false);
//		data.add(model);
		//model = new CheckModel("4","气表身份认证",false);
		//data.add(model);
		String meterType =(String)ComBleActivity.activity.meterTypeSelect.getSelectedItem();
		 if (meterType.equals("中继器"))
		{
			model = new CheckModel("7", "上报解析", false);
			data.add(model);
			model = new CheckModel("7", "地址设置", false);
			data.add(model);
			model = new CheckModel("7", "更改频率", false);
			data.add(model);
			model = new CheckModel("7", "抄表当前用量", false);
			data.add(model);

		}
		if (MainActivity.controlFlag ==1) {
			if (meterType.equals("气表"))
			{
				model = new CheckModel("4", "气表开阀", false);
				data.add(model);
				model = new CheckModel("5", "气表关阀", false);
				data.add(model);
				model = new CheckModel("5", "编气表时间", false);
				data.add(model);
				model = new CheckModel("6", "编气表当前套气价信息", false);
				data.add(model);
				model = new CheckModel("6", "编气表备用套气价信息", false);
				data.add(model);
				model = new CheckModel("6", "编气表参数信息", false);
				data.add(model);
				model = new CheckModel("7", "更改频率", false);
				data.add(model);
			}
			else if (meterType.equals("水表"))
			{
				model = new CheckModel("7", "更改频率", false);
				data.add(model);
			}


		}

		if (MainActivity.controlFlag ==0) {
			if (meterType.equals("气表")) {
				model = new CheckModel("6", "抄读气表时间", false);
				data.add(model);
				model = new CheckModel("11", "抄读当前套气价信息", false);
				data.add(model);
				model = new CheckModel("12", "抄读备用套气价信息", false);
				data.add(model);
				model = new CheckModel("10", "抄读气表参数信息", false);//气表参数信息
				data.add(model);
				model = new CheckModel("7", "抄读气表当前使用量", false);
				data.add(model);
				model = new CheckModel("8", "抄读气表当前气价信息", false);//7003FF00
				data.add(model);
				model = new CheckModel("9", "抄读气表实时状态查询", false);
				data.add(model);


//		model = new CheckModel("13", "抄通讯地址", false);
//		data.add(model);
//		model = new CheckModel("14", "抄无线信息", false);
//		data.add(model);
//		model = new CheckModel("15", "抄设置量", false);
//		data.add(model);
//		model = new CheckModel("16", "抄关阀水量", false);
//		data.add(model);
//		model = new CheckModel("17", "抄出厂标志", false);
//		data.add(model);
//		model = new CheckModel("18", "抄阀门自清理天数", false);
//		data.add(model);
//		model = new CheckModel("19", "抄版本号", false);
//		data.add(model);
//		model = new CheckModel("20", "抄开阀门次数", false);
//		data.add(model);
//		model = new CheckModel("21", "抄关阀门次数", false);
//		data.add(model);


//		model = new CheckModel("23", "编当前表时间", false);
//		data.add(model);
//		model = new CheckModel("24", "编通讯地址", false);
//		data.add(model);
//		model = new CheckModel("25", "编设置量", false);
//		data.add(model);
//		model = new CheckModel("26", "水表充值", false);
//		data.add(model);
//		model = new CheckModel("27", "编出厂标志", false);
//		data.add(model);
//		model = new CheckModel("28", "编阀门自清理天数", false);
//		data.add(model);
//		model = new CheckModel("29", "编机电同步", false);
//		data.add(model);

				model = new CheckModel("26", "抄读气表上1月冻结数据", false);
				data.add(model);
				model = new CheckModel("27", "抄读气表上2月冻结数据", false);
				data.add(model);
				model = new CheckModel("28", "抄读气表上3月冻结数据", false);
				data.add(model);
				model = new CheckModel("29", "抄读气表上4月冻结数据", false);
				data.add(model);
				model = new CheckModel("30", "抄读气表上5月冻结数据", false);
				data.add(model);
				model = new CheckModel("30", "抄读气表上1结算数据", false);//7000FF01
				data.add(model);
				model = new CheckModel("31", "抄读气表上2结算数据", false);//7000FF01
				data.add(model);
				model = new CheckModel("32", "抄读气表上3结算数据", false);//7000FF01
				data.add(model);
				model = new CheckModel("33", "抄读气表上4结算数据", false);//7000FF01
				data.add(model);
				model = new CheckModel("34", "抄读气表上5结算数据", false);//7000FF01
				data.add(model);
				model = new CheckModel("40", "抄读气表上1次调价信息", false);//7001FF01
				data.add(model);
				model = new CheckModel("41", "抄读气表上2次调价信息", false);//7001FF01
				data.add(model);
				model = new CheckModel("42", "抄读气表上3次调价信息", false);//7001FF01
				data.add(model);
				model = new CheckModel("43", "抄读气表上4次调价信息", false);//7001FF01
				data.add(model);
				model = new CheckModel("44", "抄读气表上5次调价信息", false);//7001FF01
				data.add(model);

				model = new CheckModel("46", "抄读气表开阀次数", false);//72010000
				data.add(model);
				model = new CheckModel("47", "抄读气表上1开阀事件", false);//7201FF01
				data.add(model);
				model = new CheckModel("48", "抄读气表上2开阀事件", false);//7201FF01
				data.add(model);
				model = new CheckModel("49", "抄读气表上3开阀事件", false);//7201FF01
				data.add(model);
				model = new CheckModel("50", "抄读气表上4开阀事件", false);//7201FF01
				data.add(model);
				model = new CheckModel("51", "抄读气表上5开阀事件", false);//7201FF01
				data.add(model);
				model = new CheckModel("57", "抄读气表关阀次数", false);//72020000
				data.add(model);
				model = new CheckModel("58", "抄读气表上1关阀事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("59", "抄读气表上2关阀事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("60", "抄读气表上3关阀事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("61", "抄读气表上4关阀事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("62", "抄读气表上5关阀事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("68", "抄读气表超大流量次数", false);//7202FF01
				data.add(model);
				model = new CheckModel("69", "抄读气表上1超大流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("70", "抄读气表上2超大流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("71", "抄读气表上3超大流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("72", "抄读气表上4超大流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("73", "抄读气表上5超大流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("79", "抄读气表超小流量次数", false);//7202FF01
				data.add(model);
				model = new CheckModel("80", "抄读气表上1超小流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("81", "抄读气表上2超小流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("82", "抄读气表上3超小流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("83", "抄读气表上4超小流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("84", "抄读气表上5超小流量事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("90", "抄读气表磁场干扰次数", false);//7202FF01
				data.add(model);
				model = new CheckModel("91", "抄读气表上1磁场干扰事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("92", "抄读气表上2磁场干扰事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("93", "抄读气表上3磁场干扰事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("94", "抄读气表上4磁场干扰事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("95", "抄读气表上5磁场干扰事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("101", "抄读气表阀门异常次数", false);//7202FF01
				data.add(model);
				model = new CheckModel("102", "抄读气表上1阀门异常事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("103", "抄读气表上2阀门异常事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("104", "抄读气表上3阀门异常事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("105", "抄读气表上4阀门异常事件", false);//7202FF01
				data.add(model);
				model = new CheckModel("106", "抄读气表上5阀门异常事件", false);//7202FF01
				data.add(model);
			}
			else if (meterType.equals("水表"))
			{
				model = new CheckModel("4", "抄读水表当前使用量", false);
				data.add(model);
				model = new CheckModel("5", "抄读水表结算周期数据", false);
				data.add(model);
				model = new CheckModel("6", "抄读水表实时状态查询", false);
				data.add(model);
			}
		}







//		model = new CheckModel("7", "现场水表关阀", false);
//		data.add(model);
//		model = new CheckModel("8", "现场水表开阀", false);
//		data.add(model);
	}

	public interface CallBack {
		void callBack(List<CheckModel> data);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for (int i=0;i<data.size();i++){
			data.get(i).isCheck = false;
		}
		data.get(position).isCheck = !data.get(position).isCheck;
		adapter.notifyDataSetChanged();
	}

	public void setTitle(String title) {
		this.titleView.setText(title);
	}

	public void setCall(CallBack callBack) {
		this.callBack = callBack;
	}
}
