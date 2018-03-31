package com.zfg.org.myexample.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.ComBleActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.dto.CheckModel;

public class CheckListDialog extends Dialog implements
		OnItemClickListener {

	private TextView titleView;
	private ListView dataList;
	private Button okBtn;

	private Context context;
	private CallBack callBack;
	protected List<CheckModel> data;
	private CheckAdapter adapter;
	public boolean isSetFraq;//是否要设置抄控器频率

	public CheckListDialog(Context context) {
		super(context, R.style.Dialog);
		this.context = context;
		setContentView(R.layout.dialog_list_check);
		this.setCanceledOnTouchOutside(true);
		data = new ArrayList<CheckModel>();
		initData(data);
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
				for (CheckModel item : data) {
					if (item.isCheck) {
//						Toast.makeText(context, "通信频率:" + item.value, Toast.LENGTH_SHORT).show();
						ComBleActivity.setfraqvalueValue = item.value;
						if(isSetFraq) {
							ComBleActivity.setFraq(ComBleActivity.setfraqvalueValue);
						}
						break;
					}
				}
//						setfraqvalue.setText(item.value);
				dismiss();
			}
		});
		dataList.setAdapter(adapter);
	}

	private void initData(List<CheckModel> data) {
		CheckModel model = new CheckModel("0", "(1)470MHz", false);
		data.add(model);
		model = new CheckModel("1", "(2)470.5MHz", false);
		data.add(model);
		model = new CheckModel("2", "(3)471MHz", false);
		data.add(model);
		model = new CheckModel("3", "(4)471.5MHz", false);
		data.add(model);
		model = new CheckModel("4", "(5)472MHz", false);
		data.add(model);
		model = new CheckModel("5", "(6)472.5MHz", false);
		data.add(model);
		model = new CheckModel("6", "(7)473MHz", false);
		data.add(model);
		model = new CheckModel("7", "(8)473.5MHz", false);
		data.add(model);
		model = new CheckModel("8", "(9)474MHz", false);
		data.add(model);
		model = new CheckModel("9", "(10)474.5MHz", false);
		data.add(model);
		model = new CheckModel("10", "(11)475MHz", false);
		data.add(model);
		model = new CheckModel("11", "(12)475.5MHz", false);
		data.add(model);
		model = new CheckModel("12", "(13)476MHz", false);
		data.add(model);
		model = new CheckModel("13", "(14)476.5MHz", false);
		data.add(model);
		model = new CheckModel("14", "(15)477MHz", false);
		data.add(model);
		model = new CheckModel("15", "(16)477.5MHz", false);
		data.add(model);
		model = new CheckModel("16", "(17)478MHz", false);
		data.add(model);
		model = new CheckModel("17", "(18)478.5MHz", false);
		data.add(model);
		model = new CheckModel("18", "(19)479MHz", false);
		data.add(model);
		model = new CheckModel("19", "(20)479.5MHz", false);
		data.add(model);
		model = new CheckModel("20", "(21)480MHz", false);
		data.add(model);
		model = new CheckModel("21", "(22)480.5MHz", false);
		data.add(model);
		model = new CheckModel("22", "(23)481MHz", false);
		data.add(model);
		model = new CheckModel("23", "(24)481.5MHz", false);
		data.add(model);
		model = new CheckModel("24", "(25)482MHz", false);
		data.add(model);
		model = new CheckModel("24", "(26)482.5MHz", false);
		data.add(model);
		model = new CheckModel("26", "(27)483MHz", false);
		data.add(model);
		model = new CheckModel("27", "(28)483.5MHz", false);
		data.add(model);
		model = new CheckModel("28", "(29)484MHz", false);
		data.add(model);
		model = new CheckModel("29", "(30)484.5MHz", false);
		data.add(model);
		model = new CheckModel("30", "(31)485MHz", false);
		data.add(model);
		model = new CheckModel("31", "(32)485.5MHz", false);
		data.add(model);
		model = new CheckModel("32", "(33)486MHz", false);
		data.add(model);
		model = new CheckModel("33", "(34)486.5MHz", false);
		data.add(model);
		model = new CheckModel("34", "(35)487MHz", false);
		data.add(model);
		model = new CheckModel("35", "(36)487.5MHz", false);
		data.add(model);
		model = new CheckModel("36", "(37)488MHz", false);
		data.add(model);
		model = new CheckModel("37", "(38)488.5MHz", false);
		data.add(model);
		model = new CheckModel("38", "(39)489MHz", false);
		data.add(model);
		model = new CheckModel("39", "(40)489.5MHz", false);
		data.add(model);
		model = new CheckModel("40", "(41)490MHz", false);
		data.add(model);
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
