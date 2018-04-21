package com.zfg.org.myexample.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.zfg.org.myexample.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：MethodUtil
 * Describe：常用方法
 * Date：2018-04-21 14:32:26
 * Author: CapRobin@yeah.net
 */
public class MethodUtil {
    /**
     * Describe：获取网络状态
     * Params:
     * Date：2018-04-21 14:32:13
     */

    public static boolean getNetworkState(Context context) {
        // 检测网络
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWork = connectivity.getActiveNetworkInfo();
        if (netWork != null) {
            return netWork.isAvailable();
        }
        return false;
    }

    /**
     * Describe：判断GPS定位功能是否开启
     * Params:
     * Date：2018-04-21 14:32:02
     */

    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Describe：手机号格式验证
     * Params:
     * Date：2018-04-21 14:31:52
     */

    public static Boolean isMobileNo(String mobile) {
        Boolean isMobileNo1 = false;

        String mobile13 = "13\\d{9}";
        String mobile14 = "(145|147)\\d{8}";
        String mobile15 = "15[0-9]{1}\\d{8}";
        String mobile18 = "(180|182|185|186|187|188|189)\\d{8}";

        if (mobile.matches(mobile13)) {
            isMobileNo1 = true;
        }
        if (mobile.matches(mobile14)) {
            isMobileNo1 = true;
        }
        if (mobile.matches(mobile15)) {
            isMobileNo1 = true;
        }
        if (mobile.matches(mobile18)) {
            isMobileNo1 = true;
        }

        return isMobileNo1;
    }

    /**
     * Describe：邮箱格式验证
     * Params:
     * Date：2018-04-21 14:31:43
     */

    public static boolean isEmail(String mail) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mail);
        return m.find();
    }

    /**
     * Describe：获取字符串的长度，如果有中文，则每个中文字符计为2位
     * Params: 指定的字符串
     * Return：字符串长度
     * Date：2018-04-21 14:30:38
     */

    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
                valueLength += 2;
            } else {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Describe：Pull解析Xml文件
     * Params:
     * Date：2018-04-21 14:30:07
     */

    public static String parse(InputStream is) throws Exception {
        String getResultStr = null;
        String resultStr = null;
        XmlPullParser parser = Xml.newPullParser(); // 由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8"); // 设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("string")) {
                        eventType = parser.next();
                        getResultStr = parser.getText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("string")) {
                        resultStr = getResultStr;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return resultStr;
    }

    /**
     * Describe：打开视图
     * Params:
     * Date：2018-03-30 13:26:31
     */
    public static void animateOpen(final View view, int height,int maxHeight) {
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);
        ValueAnimator animator = null;
        if (height == 0) {
            animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
        } else if (height > 0 && height < maxHeight) {
            animator = createHeightAnimator(view, 0, height);
        } else if (height > maxHeight) {
            animator = createHeightAnimator(view, 0, maxHeight);
        }
        animator.start();
    }

    /**
     * Describe：隐藏视图
     * Params:
     * Date：2018-03-30 13:28:12
     */
    public static void animateClose(final View view) {
        int origHeight = view.getHeight();

        ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            ;
        });
        animator.start();
    }

    public static ValueAnimator createHeightAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        //设置动画过度时间
//         animator.setDuration(1000);
        return animator;
    }

    /**
     * Describe：dp转px
     * Params:
     * Date：2018-04-13 17:13:39
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

