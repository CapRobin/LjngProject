package com.zfg.org.myexample.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


/**
 * http请求的工具类
 * 
 * 
 */
public class HttpUtil {

    /**
     * 加header，服务器确认用户是否登录的凭证
     * 
     * @param map
     * @param sessionId
     * @return
     */
    public static Map<String, Object> putHeader(Map map, String sessionId) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put("dean_usession", sessionId);
        return map;
    }

}
