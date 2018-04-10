package com.zfg.org.myexample.application;

import android.content.Context;

import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.UserInfoBo;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 应用入口
 * @author zfg
 *
 */
public class AppApplication extends DbApplication {

	private Preference preference;

	public void onCreate() {
		super.onCreate();
		preference = Preference.instance(getApplicationContext());
		ContantsUtil.DEFAULT_TEMP_UID = preference.getLong(Preference.USER_ID) + "";
                //检查当前用户是否为空
//		if (CheckUtil.isNull(ContantsUtil.curUser)) {
//			ContantsUtil.curUser = new UserBo(getApplicationContext())
//					.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
//			ContantsUtil.curInfo = new UserInfoBo(getApplicationContext())
//					.getUinfoByMid(ContantsUtil.DEFAULT_TEMP_UID);
//		}
		HttpServiceUtil.sessionId = preference
				.getString(ContantsUtil.USER_SESSIONID);
		Config.initConfig(getApplicationContext());
		initImageLoader(getApplicationContext());
	}

	/** 初始化图片加载类配置信息 **/
	private void initImageLoader(Context context) {
//		@SuppressWarnings("deprecation")
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				context).threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
//				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
//				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
//				.writeDebugLogs() // Remove for release app
//				.build();
//		ImageLoader.getInstance().init(config);
	}



}
