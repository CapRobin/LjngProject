package com.zfg.org.myexample.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2017-04-14.
 */

public class LogUtils {
    /**
     * 统一打印错误信息
     * @param clazz 当前类 class
     * @param msg  信息
     */
    public static void error(Class clazz,String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Log.e(clazz.getSimpleName(),msg);
    }

    /**
     * 统一打印信息
     * @param clazz 当前类 class
     * @param msg  信息
     */
    public static void info(Class clazz,String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Log.i(clazz.getSimpleName(),msg);
    }
}
