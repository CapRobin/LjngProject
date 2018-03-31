package com.zfg.org.myexample.utils;

/**
 * 常量
 *
 * @author longbh
 */

import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.db.dao.UserInfo;

public class ContantsUtil {

    public final static String currentVesion = "3.3.0";

    public final static String SYCN_URL = "";
    public final static String SYCN_DATA = "org.nxlgg.com";

    public final static String host = "";

    // 请求状态码
    public static final int GET_KEY_FAILED = 100024;
    public static final int limit = 20;

    // 用户id,作为sharedPreference的key
    public static String USER_ID = "user_id";
    // 判断首次使用，作为sharedPreference的key
    public final static String FIRST_USE = "first_use";
    // 登录成功后存放mid在sharedPreference,key
    public final static String USER_MID = "user_mid";
    // 登录成功后存放sessionId在sharedPreference,key
    public final static String USER_SESSIONID = "user_sessionid";

    //系统参数是否已经同步
    public static boolean isSycnSystem = false;

    // 数据状态码
    public final static short DELETE = 1;
    public final static short NO_SERVER = 0;
    public final static short SERVER = 2;


    // 四状态
    public final static int BRECKFAST = 0;
    public final static int LAUNCH = 1;
    public final static int DINNER = 2;
    public final static int SLEEP_PRE = 3;

    //控制页面刷新的常量
    public static boolean ENTRY_UPDATE = false;
    public static boolean EFFECT_UPDATE = false;
    public static boolean TOTAL_UPDATE = false;
    public static boolean TOTAL_AVG_UPDATE = false;
    public static boolean TOTAL_HIGH_UPDATE = false;
    public static boolean TOTAL_LOW_UPDATE = false;
    public static boolean TOTAL_COUNT_UPDATE = false;
    public static boolean UPDATE_AVTAR = false;


    //日期常量
    public static long deltaWeek = 7 * 24 * 60 * 60 * 1000;
    public static long deltaDay = 24 * 60 * 60 * 1000;

    //蓝牙序列号
//    public static final String bluthUserId = "372758946@qq.com";
    public static final String clientID = "";
    public static final String clientSecret = "";

    // 饭前 饭后
    public final static int EAT_PRE = 0;
    public final static int EAT_AFTER = 1;

    //上升 下降
    public final static int UP = 0;
    public final static int DOWN = 1;

    // 枚举分类group标识
    // 伴随状态
    public static final String DIABETES_PLUG_STATE = "";
    /**
     * 用户主成员的mid
     */
    public static String DEFAULT_TEMP_UID = "";
    public static User curUser;
    public static UserInfo curInfo;
    public static float height = 1.7f;

    // 闹钟类型标志
    public static final int SUGAR_ALARM = 1;
    public static final int MEDICINE_ALARM = 2;
    public static final int SPORT_ALARM = 3;
    public static final int EAT_ALARM = 4;

    //公用模板分类常量
    public static final int EAT_FOOD = -1;
    public static final int SPORT_TYPE = -2;
    public static final int MEDICINE = -3;

    //首页
    public static boolean MAIN_UPDATE = false;
    public static boolean ADD_MEMBER = false;

    //外网使用
    public static String HOST = "http://192.168.2.136:8088/lggmr";
//    public static String HOST = "http://longi.nxlgg.com:8039/lggmr";
//    public static String HOST = "http://longi.nxlgg.com:8046/lggmr";
//    public static String HOST = "http://192.168.0.39:80/lggmr";
//    public static String HOST = "http://192.168.0.111:6608/lggmr";
//    public static String HOST = "http://192.168.0.46:8046/lggmr";

//    public static String HOST = "http://222.75.144.94:80/lggmr";

//    public static String HOST = "http://222.75.144.94:8807/lggmr";
//    public static String HOST = "http://222.75.144.94:8808/lggmr";


    public static String URL_LOGIN = HOST + "/appLogin";

    public static String URL_USERMETERS = HOST + "/appUserMeters";

    public static String URL_AUTH = HOST + "/bluetoothAuthenticationWater";

    public static String URL_LOGOUT = HOST + "/logout";

    public static String URL_ReadDatagas = HOST + "/realpowergas";

    public static String URL_ReadDatawater = HOST + "/realpowerwater";

    public static String URL_ReadDatameter = HOST + "/realPowerReading";

    public static String URL_Rechargegas = HOST + "/rechargegas";

    public static String URL_Rechargewater = HOST + "/rechargeMeter";

    public static String URL_WeiPay_Rechargewater = HOST + "/payment";

    public static String URL_AliPay_Rechargewater = HOST + "/alipay";
    //
    public static String URL_pullswitchgas = HOST + "/pullswitchgas";

    public static String URL_pullswitchwater = HOST + "/pullSwitchMeter";

    //  抄读记录历史数据
    public static String URL_QueryReadDataHisWater = HOST + "/appQueryMeter";

    //  充值记录历史数据
    public static String URL_QueryRechargeHisWater = HOST + "/appRechargeQry";

    //  充值拉合闸历史数据
    public static String URL_QuerySwichHisHisWater = HOST + "/appReconnOrDisconnRecdList";

    //  校时
    public static String URL_AdjustTime = HOST + "/adjustTime";

    // 气表查询
    public static String URL_QueryGas = HOST + "/appQueryGas";

    //   全功能抄表接口
    public static String URL_RealTimeReading = HOST + "/realTimeReading";

    //  更新
    public static String UPDATE_CHECK_URL = HOST + "/appUpdate";

    //    意见反馈
    public static String URL_FeedBack = HOST + "/appFeedBack";

    // 抄表任务
    public static String URL_ReadTask = HOST + "/readRateStatistic";

    // 公告信息
    public static String URL_OFFNews = HOST + "/officialNews";

    //异常上报
    public static String URL_UPloadException = HOST + "/appUploadException";

    //抄表导航
    public static String URL_ReadingNavigation = HOST + "/readingNavigation";

    //  抄表拍照
    public static String URL_UPloadMeterRecord = HOST + "/uploadMeterRecord";

    //水表身份认证
    public static String URL_WaterAuto = HOST + "/bluetoothAuthenticationWater";

    //水表充值
    public static String URL_WaterCharge = HOST + "/bluetoothChargeWater";

    //水表开关阀门
    public static String URL_WaterPull = HOST + "/bluetoothPullWater";

    //气表身份认证
    public static String URL_GasAuto = HOST + "/bluetoothAuthenticationGas";
    //关阀 关阀
    public static String URL_GasPull = HOST + "/bluetoothPullGas";
    //设置气价
    public static String URL_GasPriceSet = HOST + "/bluetoothSetPriceGas";
    //上传
    public static String URL_UploadDataGas = HOST + "/appUploadDataGas";
    //下发气表参数
    public static String URL_SetParamGas = HOST + "/bluetoothSetParamGas";

    public static void setHOst(String host) {
        HOST = host;
        URL_LOGIN = HOST + "/appLogin";

        URL_USERMETERS = HOST + "/appUserMeters";

        URL_AUTH = HOST + "/bluetoothAuthenticationWater";

        URL_LOGOUT = HOST + "/logout";

        URL_ReadDatagas = HOST + "/realpowergas";

        URL_ReadDatawater = HOST + "/realpowerwater";

        URL_ReadDatameter = HOST + "/realPowerReading";

        URL_Rechargegas = HOST + "/rechargegas";

        URL_Rechargewater = HOST + "/rechargeMeter";

        URL_WeiPay_Rechargewater = HOST + "/payment";

        URL_AliPay_Rechargewater = HOST + "/alipay";
        //
        URL_pullswitchgas = HOST + "/pullswitchgas";

        URL_pullswitchwater = HOST + "/pullSwitchMeter";

        //  抄读记录历史数据
        URL_QueryReadDataHisWater = HOST + "/appQueryMeter";

        //  充值记录历史数据
        URL_QueryRechargeHisWater = HOST + "/appRechargeQry";

        //  充值拉合闸历史数据
        URL_QuerySwichHisHisWater = HOST + "/appReconnOrDisconnRecdList";

        //  校时
        URL_AdjustTime = HOST + "/adjustTime";

        // 气表查询
        URL_QueryGas = HOST + "/appQueryGas";

        //   全功能抄表接口
        URL_RealTimeReading = HOST + "/realTimeReading";

        //  更新
        UPDATE_CHECK_URL = HOST + "/appUpdate";

        //    意见反馈
        URL_FeedBack = HOST + "/appFeedBack";

        // 抄表任务
        URL_ReadTask = HOST + "/readRateStatistic";

        // 公告信息
        URL_OFFNews = HOST + "/officialNews";

        //异常上报
        URL_UPloadException = HOST + "/appUploadException";

        //抄表导航
        URL_ReadingNavigation = HOST + "/readingNavigation";

        //  抄表拍照
        URL_UPloadMeterRecord = HOST + "/uploadMeterRecord";

        //水表身份认证
        URL_WaterAuto = HOST + "/bluetoothAuthenticationWater";

        //水表充值
        URL_WaterCharge = HOST + "/bluetoothChargeWater";

        //水表开关阀门
        URL_WaterPull = HOST + "/bluetoothPullWater";

        //气表身份认证
        URL_GasAuto = HOST + "/bluetoothAuthenticationGas";
        //关阀 关阀
        URL_GasPull = HOST + "/bluetoothPullGas";
        //设置气价
        URL_GasPriceSet = HOST + "/bluetoothSetPriceGas";
        //上传
        URL_UploadDataGas = HOST + "/appUploadDataGas";
        //下发气表参数
        URL_SetParamGas = HOST + "/bluetoothSetParamGas";

    }

}
