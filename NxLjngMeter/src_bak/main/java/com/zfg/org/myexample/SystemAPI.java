package com.zfg.org.myexample;

import android.content.Context;

import java.util.Map;

import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpContants;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.HttpUtil;
import com.zfg.org.myexample.utils.Preference;
//import com.zfg.org.myexample.utils.okhttp.OkHttpUtils;

/**
 * @author Administrator
 */
public class SystemAPI {
    /**
     * http请求：登录
     *
     * @param map
     * @param callBack
     */
    public static void login(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_LOGIN,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void auth(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_LOGIN,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：退出
     *
     * @param map
     * @param callBack
     */
    public static void logout(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_LOGIN,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：抄表
     *
     * @param map
     * @param callBack
     */
    public static void meter_read(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_ReadDatagas, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：拉闸
     *
     * @param map
     * @param callBack
     */
    public static void meter_off(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_pullswitchgas,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：合闸
     *
     * @param map
     * @param callBack
     */
    public static void meter_on(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_pullswitchgas,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：充值
     *
     * @param map
     * @param callBack
     */
    public static void meter_recharge(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_Rechargegas,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：抄表
     *
     * @param map
     * @param callBack
     */
    public static void meter_readwater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_ReadDatawater, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_readgas(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_ReadDatagas, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_readmeter(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_ReadDatameter, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_realTimeReading(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_RealTimeReading, HttpContants.REQUEST_MOTHOD, map, callBack);
    }
    // 水表身份认证
    public static void meter_waterAuto(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_WaterAuto, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    // 水表充值
    public static void meter_waterCharge(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_WaterCharge, HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_gasAuth(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_GasAuto, HttpContants.REQUEST_MOTHOD, map, callBack);
    }
    /**
     * http请求：拉闸
     *
     * @param map
     * @param callBack
     */
    public static void meter_offwater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_pullswitchwater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：合闸
     *
     * @param map
     * @param callBack
     */
    public static void meter_onwater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_pullswitchwater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：充值
     *
     * @param map
     * @param callBack
     */
    public static void meter_rechargewater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_Rechargewater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_weipay_rechargewater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_WeiPay_Rechargewater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_alipay_rechargewater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_AliPay_Rechargewater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    public static void meter_unionpay_rechargewater(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_Rechargewater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /*
    * 查询抄读历史记录
    */
    public static void query_readdata_his(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_QueryReadDataHisWater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /*
    * 查询充值历史记录
    */
    public static void query_recharge_his(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_QueryRechargeHisWater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /*
    * 查询充值历史记录
    */
    public static void query_swich_his(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_QuerySwichHisHisWater,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /*
    * 校时
    */
    public static void adjustTime(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_AdjustTime,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    //  意见反馈
    public static void feedBack(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_FeedBack,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    //  任务
    public static void readingTask(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.URL_ReadTask,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }

    /**
     * http请求：异常上报
     *
     * @param filePath
     * @param map
     * @param callBack
     */

    public static void uploadexception(Map<String, Object> map, String filePath, Context context, CallBack callBack) {
//        String sessionId = Preference.instance(context).getString(ContantsUtil.USER_SESSIONID);
//        map = HttpUtil.putHeader(map, sessionId);
        HttpServiceUtil.subFile(ContantsUtil.URL_UPloadException, filePath, map, callBack);
    }



}
