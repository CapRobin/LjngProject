package com.zfg.org.myexample.activity.alarm;



import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.db.AlarmBo;
import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.utils.StringUtil;

/**
 * 闹钟弹出提示框，当到时间时触发
 * @author hua
 *
 */
public class AlarmAlert extends BasicActivity {

	protected static final String SCREEN_OFF = "screen_off";

	private AlarmBo bo;
	protected Alarm mAlarm;

	// 监听闹钟取消
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(AlarmBo.ALARM_DISMISS_ACTION)) {
				dismiss(false);
			} else {
				Alarm alarm = intent
						.getParcelableExtra(AlarmBo.ALARM_INTENT_EXTRA);
				if (alarm != null && mAlarm.getId() == alarm.getId()) {
					dismiss(true);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		bo = new AlarmBo(context);
		mAlarm = getIntent().getParcelableExtra(AlarmBo.ALARM_INTENT_EXTRA);
		mAlarm = bo.getById(mAlarm.getId());

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		if (!getIntent().getBooleanExtra(SCREEN_OFF, false)) {
			win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
		}

		updateLayout();

		IntentFilter filter = new IntentFilter(AlarmBo.ALARM_KILLED);
		filter.addAction(AlarmBo.ALARM_DISMISS_ACTION);
		registerReceiver(mReceiver, filter);
	}

	private void setTitle() {
		String label = mAlarm.getTitle();
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(label);
		TextView date = (TextView) findViewById(R.id.date);
		date.setText(DateUtil.parseToString(System.currentTimeMillis()));
		if (mAlarm.getMessage() != null) {
//			TextView contentView = (TextView) findViewById(R.id.alert_content);
//			contentView.setText(mAlarm.getMessage());
		}
	}

	private void updateLayout() {
		LayoutInflater inflater = LayoutInflater.from(this);
//		setContentView(inflater.inflate(R.layout.activity_clock_alert, null));
		findViewById(R.id.cancel_btn).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View v) {
						dismiss(false);
					}
				});
		setTitle();
	}

	private NotificationManager getNotificationManager() {
		return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	// 关闭弹出框，顺便删除提示
	private void dismiss(boolean killed) {
		if (!killed) {
			NotificationManager nm = getNotificationManager();
			nm.cancel(StringUtil.toInt(mAlarm.getId()));
			stopService(new Intent(AlarmBo.ALARM_ALERT_ACTION));
		}
		finishSimple();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mAlarm = intent.getParcelableExtra(AlarmBo.ALARM_INTENT_EXTRA);
		setTitle();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// Do this on key down to handle a few of the system keys.
		boolean up = event.getAction() == KeyEvent.ACTION_UP;
		switch (event.getKeyCode()) {
		// Volume keys and camera keys dismiss the alarm
		case KeyEvent.KEYCODE_VOLUME_UP:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_CAMERA:
		case KeyEvent.KEYCODE_FOCUS:
			if (up) {
				switch (9) {
				// TO_DO
				case 2:
					dismiss(false);
					break;

				default:
					break;
				}
			}
			return true;
		default:
			break;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onBackPressed() {
		return;
	}
}
