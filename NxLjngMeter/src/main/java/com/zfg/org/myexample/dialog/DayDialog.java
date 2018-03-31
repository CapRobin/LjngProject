package com.zfg.org.myexample.dialog;

import java.util.Calendar;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.widget.wheel.NumericWheelAdapter;
import com.zfg.org.myexample.widget.wheel.OnWheelChangedListener;
import com.zfg.org.myexample.widget.wheel.WheelView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DayDialog extends Dialog implements
		View.OnClickListener {

	private Calendar calendar;
	private Context context;
	private String title;

	private CallBack callBack;
	private TextView titleView;
	private Button okBtn;

	private WheelView yearView;
	private WheelView monthView;
	private WheelView dayView;

	public DayDialog(Context context, String title) {
		super(context, R.style.Dialog);
		this.context = context;
		this.title = title;
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_today_layout);
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
		monthView = (WheelView) findViewById(R.id.month);
		yearView = (WheelView) findViewById(R.id.year);
		dayView = (WheelView) findViewById(R.id.day);
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(yearView, monthView, dayView);
			}
		};
		// month
		int curMonth = calendar.get(Calendar.MONTH);
		monthView.setAdapter(new NumericWheelAdapter(1, 12, "00"));
		monthView.setCurrentItem(curMonth);
		monthView.addChangingListener(listener);
		monthView.setCyclic(true);
		monthView.setVisibleItems(3);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		yearView.setAdapter(new NumericWheelAdapter(1900, 2100));
		yearView.setBackgroundColor(context.getResources().getColor(
				R.color.transparent));
		yearView.setCurrentItem(curYear);
		yearView.addChangingListener(listener);
		yearView.setCyclic(true);
		yearView.setVisibleItems(3);


		// day
		updateDays(yearView, monthView, dayView);
		dayView.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setAdapter(new NumericWheelAdapter(1, maxDays));
		day.setCyclic(true);
		day.setVisibleItems(3);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
     */
	public void show(int year, int month, int day) {
		super.show();
		yearView.setCurrentItem(year - 1900);
		monthView.setCurrentItem(month - 1);
		dayView.setCurrentItem(day - 1);
	}

	public void show(long timeMini) {
		super.show();
		Calendar tcalendar = Calendar.getInstance();
		tcalendar.setTimeInMillis(timeMini);
		yearView.setCurrentItem(tcalendar.get(Calendar.YEAR) - 1900);
		monthView.setCurrentItem(tcalendar.get(Calendar.MONTH));
		dayView.setCurrentItem(tcalendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public void show() {
		super.show();
		Calendar tcalendar = Calendar.getInstance();
		tcalendar.setTimeInMillis(System.currentTimeMillis());
		yearView.setCurrentItem(tcalendar.get(Calendar.YEAR) - 1900);
		monthView.setCurrentItem(tcalendar.get(Calendar.MONTH));
		dayView.setCurrentItem(tcalendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public void setCallBack(CallBack cal) {
		this.callBack = cal;
	}

	public interface CallBack {
		boolean callBack(int year, int month, int day);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.ok_btn:
			if(callBack != null){
				boolean state = callBack.callBack(1900 + yearView.getCurrentItem(),
						1 + monthView.getCurrentItem(),
						1 + dayView.getCurrentItem());
				if (state) {
					dismiss();
				}
			}
			break;
		case R.id.cancel_btn:
			dismiss();
			break;
		}
	}
}