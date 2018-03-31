package com.zfg.org.myexample.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.content.Context;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;

/**
 * 登录注册API的请求包装类
 * 
 * @author zfg
 * 
 */
public class LoginRegisterAPI {


    /**
     * http请求：登录
     * @param map
     * @param callBack
     */
    public static void login(Map<String, Object> map, CallBack callBack) {
        HttpServiceUtil.request(ContantsUtil.HOST + ContantsUtil.URL_LOGIN,
                HttpContants.REQUEST_MOTHOD, map, callBack);
    }
    
    
    

    
}
