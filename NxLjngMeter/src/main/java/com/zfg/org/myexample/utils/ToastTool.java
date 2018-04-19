package com.zfg.org.myexample.utils;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 类/接口注释
 */
public class ToastTool {

    public static final int SDCardNotFound = 99;
	/**
	 * 状态码toast
	 * 
	 * @param stauts
	 * @param context
	 */
	public static void showToast(int status, Context context) {
		switch (status) {
		case HttpContants.REQUEST_SUCCESS:
			break;
		case HttpContants.SERVER_ERROR:
			Toast.makeText(context, context.getText(R.string.server_error),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.REQUEST_ERROR:
			Toast.makeText(context, context.getText(R.string.request_error),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.PARAM_ERROR:
			Toast.makeText(context, context.getText(R.string.info_illformat),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.USER_NOTFOUND:
			Toast.makeText(context, context.getText(R.string.user_notfound),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.NAMEPSW_ERROR:
			Toast.makeText(context, context.getText(R.string.wrong_input),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.SESSION_ERROR:
			Toast.makeText(context, context.getText(R.string.user_unlogin),
					Toast.LENGTH_SHORT).show();
			// 退出登录，清楚登录的数据 sharedPreference和session
			// mid
			Preference.instance(context).remove(Preference.USER_ID);
            ContantsUtil.DEFAULT_TEMP_UID = "";
            // uid
            Preference.instance(context).remove(ContantsUtil.USER_ID);
            // sessionId
            Preference.instance(context).remove(ContantsUtil.USER_SESSIONID);
            HttpServiceUtil.sessionId = "";
            
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			((BasicActivity) context).finishSimple();
			break;
		case HttpContants.USER_UNLOGIN:
			Toast.makeText(context, context.getText(R.string.user_unlogin),
					Toast.LENGTH_SHORT).show();
			// 退出登录，清楚登录的数据 sharedPreference和session
			// mid
            Preference.instance(context).remove(Preference.USER_ID);
            ContantsUtil.DEFAULT_TEMP_UID = "";
            // uid
            Preference.instance(context).remove(ContantsUtil.USER_ID);
            // sessionId
            Preference.instance(context).remove(ContantsUtil.USER_SESSIONID);
            HttpServiceUtil.sessionId = "";
            
			Intent unloginIntent = new Intent(context, LoginActivity.class);
			context.startActivity(unloginIntent);
			((BasicActivity) context).finishSimple();
			break;
		case HttpContants.WRONG_CONNECTION:// 连接失败
			Toast.makeText(context, context.getText(R.string.wrong_connection),
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 关于用户登录注册修改密码的Toast
	 * 
	 * @param stauts
	 * @param context
	 */
	public static void showUserStatus(int status, Context context) {
		switch (status) {
		case HttpContants.LONGIN_SUCCESS:// 登录成功
			Toast.makeText(context, context.getText(R.string.login_success),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.MODIFY_PSW_SUCCESS:// 修改密码成功
			Toast.makeText(context,
					context.getText(R.string.modify_psw_success),
					Toast.LENGTH_SHORT).show();
			break;
		
		case HttpContants.WRONG_FORMAT:// 输入信息的格式有误
			Toast.makeText(context, context.getText(R.string.wrong_format),
					Toast.LENGTH_SHORT).show();
			break;
		
	
		case HttpContants.REGISTER_SUCCESS:// 注册成功
			Toast.makeText(context, context.getText(R.string.register_success),
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * 关于成员的Toast
	 * 
	 * @param stauts
	 * @param context
	 */
	public static void showMemberStatus(int stauts, Context context) {
		switch (stauts) {
		case HttpContants.LOGOUT_SUCCESS:// 注销成功
			Toast.makeText(context, context.getText(R.string.logout_success),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.MODIFY_SUCCESS:// 修改成功
			Toast.makeText(context, context.getText(R.string.modify_success),
					Toast.LENGTH_SHORT).show();
			break;
		
		case HttpContants.UPDATE_ULIST_SUCCESS:// 上传图片失败
			Toast.makeText(context,
					context.getText(R.string.update_ulist_success),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.SET_MASTER_SUCCESS:// 设置主成员成功
			Toast.makeText(context,
					context.getText(R.string.set_master_success),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.ADD_USER_SUCCESS:// 添加成员成功
			Toast.makeText(context, context.getText(R.string.add_user_success),
					Toast.LENGTH_SHORT).show();
			break;
		case HttpContants.DEL_USER_SUCCESS:// 删除成员成功
			Toast.makeText(context, context.getText(R.string.del_user_success),
					Toast.LENGTH_SHORT).show();
			break;
		
		}
	}

	public static String toastBluth(int status) {
		String msg = "未知错误";
		switch (status) {
		case 1:
			msg = "新用户注册成功";
			break;
		case 2:
			msg = "用户登录成功";
			break;
		case 4:
			msg = "网络异常仅测试用";
			break;
		case 5:
			msg = "验证失败";
			break;
		case 6:
			msg = "应用无此权限";
			break;
		case 7:
			msg = "用户无此权限";
			break;
		case 8:
			msg = "网络异常导致验证失败";
			break;
		}
		return msg;
	}

	public static String toastErrorBluth(int status) {
		String msg = "未知错误";
		switch (status) {
		case 0:
			msg = "设备电池电量低，请先充电";
			break;
		case 1:
			msg = "血糖测量错误，结果超出范围";
			break;
		case 2:
			msg = "未知干扰导致失败，请重新测量";
			break;
		case 3:
			msg = "试条已经被使用，请更换试纸";
			break;
		case 4:
			msg = "数据传输错误，请更换试条再试";
			break;
		case 5:
		case 6:
			msg = "环境温度不适宜测量，请将设备放到室温30分钟再试";
			break;
		case 7:
			msg = "请确认二维码信息是否正确";
			break;
		case 8:
			msg = "通信错误，请重新测量或者扫描二维码";
			break;
		case 9:
			msg = "请插入试条重新测量";
			break;
		case 10:
			msg = "通信错误，请重新连接血糖仪";
			break;
		}
		return msg;
	}
	
	public static String toastPress(int status){
		String msg = "未知错误";
		switch (status) {
		case -1:
			msg = "血压计自动关机";
			break;
		case 0:
			msg = "未找到合适零点";
			break;
		case 1:
			msg = "未找到高压";
			break;
		case 2:
			msg = "未找到低压（或高压值低于低压值）";
			break;
		case 3:
			msg = "加压过快";
			break;
		case 4:
			msg = "加压过慢";
			break;
		case 5:
			msg = "压力超过300mmHg";
			break;
		case 6:
			msg = "压力大于15mmHg 的时间超过160s";
			break;
		case 7:
			msg = "EE 读写错误";
			break;
		case 8:
			msg = "EE 三备份数据错误";
			break;
		case 9:
			msg = "保留";
			break;
		case 10:
			msg = "SPAN 值错误";
			break;
		case 11:
			msg = "CRC 校验错误";
			break;
		case 12:
			msg = "连接错误";
			break;
		case 13:
			msg = "电量过低";
			break;
		case 15:
			msg = "测量结果的高压值或低压值超过设定上限";
			break;
		case 16:
			msg = "测量结果的高压值或低压值超过设定下限";
			break;
		case 17:
			msg = "测量过程中手臂移动超过机内设定值";
			break;
		}
		return msg;
	}
}
