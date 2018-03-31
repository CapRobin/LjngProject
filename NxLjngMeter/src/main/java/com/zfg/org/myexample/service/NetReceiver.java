package com.zfg.org.myexample.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.NetworkUtil;

/**
 * 监听网络变化用于同步数据
 * 
 * @author longbh
 * 
 */
public class NetReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)
				&& NetworkUtil.checkConnection(context)) {
			Preference preference = Preference.instance(context);
			if (preference.getBoolean(Preference.HAS_UPDATE) && Config.canUpdate()) {
				Intent playAlarm = new Intent(ContantsUtil.SYCN_DATA);
				playAlarm.putExtra("state", true);
				context.startService(playAlarm);
			}
		}
	}
}
