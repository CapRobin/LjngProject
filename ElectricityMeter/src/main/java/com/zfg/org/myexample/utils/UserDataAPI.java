package com.zfg.org.myexample.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;

/**
 * 成员资料的API包装类
 * 
 * 
 */
public class UserDataAPI {

    /**
     * http请求：成员列表
     * 
     * @param map
     * @param sessionId
     * @param callBack
     */
    public static void getlist(Map<String, Object> map, Context context,CallBack callBack) {
//        String sessionId = Preference.instance(context).getString(ContantsUtil.USER_SESSIONID);
//        map = HttpUtil.putHeader(map, sessionId);
//        HttpServiceUtil.request(ContantsUtil.URL_WorkerNames_Get,
//                HttpContants.REQUEST_MOTHOD, map , callBack);
    }


}
