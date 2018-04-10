package com.zfg.org.myexample.pay.weipay;

/**
 * Created by Administrator on 2016-10-19.
 */

public class WechatNotSupportException extends Exception {

    private static final long serialVersionUID = 2313964703740528072L;

    public WechatNotSupportException(){
        super("Current wechat APP version too low, not support wechat pay.");
    }
}
