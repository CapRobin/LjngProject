package com.zfg.org.myexample.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 2016-10-15.
 */
public class SwichPowerHisModel {

    public String getSwichpowerdate() {
        return swichpowerdate;
    }

    public void setSwichpowerdate(String swichpowerdate) {
        this.swichpowerdate = swichpowerdate;
    }

    public String getSwichpowertype() {
        return swichpowertype;
    }

    public void setSwichpowertype(String swichpowertype) {
        this.swichpowertype = swichpowertype;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSwichreason() {
        return swichreason;
    }

    public void setSwichreason(String swichreason) {
        this.swichreason = swichreason;
    }

    //  开关闸时间
    private String swichpowerdate;
    //  开关闸操作类型 0合闸 1拉闸
    private String swichpowertype;
    //  操作员
    private String operator;
    //  拉合闸原因
    private String swichreason;
//    {"custAddr":"[光明路25号]","custId":"[000000000040]","custName":"[成龙]","custPhone":"[14789582658]",
//    "msgResult":[{"CATEGORY":4,"OPERATE_TIME":"2016-10-17 16:09:37","ACTION_KEY":"关闭阀门","PARAMS":"[000000000040]","USER_ID":"admin"},
//                    {"CATEGORY":4,"OPERATE_TIME":"2016-10-17 16:25:01","ACTION_KEY":"打开阀门","PARAMS":"表号：[000000000040]","USER_ID":"admin"},
//                {"CATEGORY":4,"OPERATE_TIME":"2016-10-17 16:28:29","ACTION_KEY":"关闭阀门","PARAMS":"表号：[000000000040]","USER_ID":"admin"},
//        {"CATEGORY":4,"OPERATE_TIME":"2016-10-17 16:28:52","ACTION_KEY":"打开阀门","PARAMS":"表号：[000000000040]","USER_ID":"admin"},
//        {"CATEGORY":4,"OPERATE_TIME":"2016-10-17 18:57:33","ACTION_KEY":"打开阀门：失败！","PARAMS":"表号：[000000000040]","USER_ID":"admin"}],
//        "strBackFlag":"1","strMeterAddr":"000000000040"}
    public  void of(JSONObject data) throws JSONException {
        this.setSwichpowerdate("操作时间："+data.getString("OPERATE_TIME"));
        this.setSwichpowertype("操作项："+data.getString("ACTION_KEY"));
        this.setOperator("操作员："+data.getString("USER_ID"));
    }

}
