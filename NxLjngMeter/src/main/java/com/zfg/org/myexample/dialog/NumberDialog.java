package com.zfg.org.myexample.dialog;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.widget.wheel.NumericWheelAdapter;
import com.zfg.org.myexample.widget.wheel.WheelView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class NumberDialog extends Dialog implements View.OnClickListener {
	
	private TextView titleView;
	
	private WheelCallBack callBack;
	private WheelView catalogWheel;
	
	private View okBtn;
	private int max;
	private int min;
	private String union;
	private String title;

	public NumberDialog(Context context,WheelCallBack callBack,int max,int min,String union,String title) {
		super(context, R.style.Dialog);
		this.callBack = callBack;
		this.max = max;
		this.min = min;
		this.union = union;
		this.title = title;
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_height_layout);
		initView();
	}

	private void initView() {
		catalogWheel = (WheelView) findViewById(R.id.height_integer);
		catalogWheel.setAdapter(new NumericWheelAdapter(min, max));
		catalogWheel.setCyclic(true);
		
		catalogWheel.setVisibleItems(5);				
		catalogWheel.setLabel(union);
		
		titleView = (TextView) findViewById(R.id.dialog_operate_name);
		titleView.setText(title);
		
		okBtn = findViewById(R.id.ok_btn);
		okBtn.setOnClickListener(this);
	}
	
	public void show(int initValue){
		super.show();
		catalogWheel.setCurrentItem(initValue - min);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.ok_btn:
			dismiss();
			// 实现下ui的刷新
			callBack.item(catalogWheel.getCurrentItem() + min);
			break;
		}
	}

}
