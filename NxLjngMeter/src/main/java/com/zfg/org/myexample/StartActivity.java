package com.zfg.org.myexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

//import com.zfg.org.myexample.service.LoadingService;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.NetworkUtil;
//import com.zdp.aseo.content.AseoZdpAseo;

//起始页
public class StartActivity extends FragmentActivity {

	private Preference preference;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_layout);
		context = this;
		preference = Preference.instance(this);
//		AseoZdpAseo.initType(this, AseoZdpAseo.SCREEN_TYPE);
		loadSystemProperty();
	}

	/**
	 * Describe：检查网络状态
	 * Params: 
	 * Date：2018-03-27 08:33:46
	 */
	
	private void loadSystemProperty() {
		if (NetworkUtil.checkConnection(this)) {
			startLoadData();
		} else {
			startHandler();
		}
	}

	/**
	 * Describe：开始加载数据
	 * Params:
	 * Date：2018-03-27 08:35:06
	 */
	private void startLoadData() {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("timestamp", preference.getLong(Preference.SYS_UPDATE_TIME));
//		HttpServiceUtil.request(ContantsUtil.PRED_UPDATE, "post", params,
//				new HttpServiceUtil.CallBack() {
//					@Override
//					public void callback(String json) {
//						LoadingService.sycnData(preference, context, json);
//						ContantsUtil.isSycnSystem = true;
//						if(!CheckUtil.isNull(ContantsUtil.DEFAULT_TEMP_UID)){
//							Intent playAlarm = new Intent(ContantsUtil.SYCN_DATA);
//							playAlarm.putExtra("state", false);
//							context.startService(playAlarm);
//						}
						toMainActivity();
//					}
//				});
	}

	private void startHandler() {
		Handler handle = new Handler();
		handle.postDelayed(new Runnable() {
			@Override
			public void run() {
				toMainActivity();
			}
		}, 2000);
	}
	
	/**
	 * Describe：第一次登录进入向导页，非第一次登录进入登录页面
	 * Params:
	 * Date：2018-03-27 10:51:55
	 */
	private void toMainActivity() {
		if (!preference.getBoolean(Preference.FIRST_IN)) {
			Intent intent = new Intent();
			intent.setClass(context, WelcomeActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			startActivity(intent);
		}
		finish();
	}

	public void onBackPressed() {

	}
}
