package com.zfg.org.myexample.dialog;

import java.util.Calendar;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.widget.wheel.NumericWheelAdapter;
import com.zfg.org.myexample.widget.wheel.WheelView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeDialog extends Dialog implements
		View.OnClickListener {

	private Calendar calendar;
	private String title;

	private CallBack callBack;
	private TextView titleView;
	private Button okBtn;

	private WheelView hourView;
	private WheelView miniView;

	public TimeDialog(Context context, String title) {
		super(context, R.style.Dialog);
		this.title = title;
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_hourmin_layout);
		calendar = Calendar.getInstance();
		initView();
	}

	private void initView() {
		titleView = (TextView) findViewById(R.id.dialog_operate_name);
		titleView.setText(title);
		okBtn = (Button) findViewById(R.id.ok_btn);
		okBtn.setOnClickListener(this);
		Button btnCancel = (Button) findViewById(R.id.cancel_btn);
		btnCancel.setOnClickListener(this);
		hourView = (WheelView) findViewById(R.id.hour);
		miniView = (WheelView) findViewById(R.id.mini);

		// hour
		int curHour = calendar.get(Calendar.HOUR);
		hourView.setAdapter(new NumericWheelAdapter(0, 23, "00"));
		hourView.setCurrentItem(curHour);
		hourView.setCyclic(true);
		hourView.setVisibleItems(3);

		// mini
		int curMini = calendar.get(Calendar.MINUTE);
		miniView.setAdapter(new NumericWheelAdapter(0, 59, "00"));
		miniView.setCurrentItem(curMini);
		miniView.setCyclic(true);
		miniView.setVisibleItems(3);

	}
	
	/**
	 * 展现出来
	 * 
	 * @param cal
	 */
	public void show(int hour, int mini) {
		super.show();
		hourView.setCurrentItem(hour);
		miniView.setCurrentItem(mini);
	}

	public void show(long timeMini) {
		super.show();
		Calendar tcalendar = Calendar.getInstance();
		tcalendar.setTimeInMillis(timeMini);
		hourView.setCurrentItem(tcalendar.get(Calendar.HOUR_OF_DAY));
		miniView.setCurrentItem(tcalendar.get(Calendar.MINUTE));
	}

	public void show() {
		super.show();
		Calendar tcalendar = Calendar.getInstance();
		tcalendar.setTimeInMillis(System.currentTimeMillis());
		hourView.setCurrentItem(tcalendar.get(Calendar.HOUR_OF_DAY));
		miniView.setCurrentItem(tcalendar.get(Calendar.MINUTE));
	}

	public void setCallBack(CallBack cal) {
		this.callBack = cal;
	}

	public interface CallBack {
		boolean callBack(int hour, int mini);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.ok_btn:
			boolean state = callBack.callBack(hourView.getCurrentItem(),
					miniView.getCurrentItem());
			if (state) {
				dismiss();
			}
			break;
		case R.id.cancel_btn:
			dismiss();
			break;
		}
	}
}