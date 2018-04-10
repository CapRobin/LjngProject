package com.zfg.org.myexample.model;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 2016-10-14.
 * 充值历史记录
 */
public class RechargeHisItemModel {
//{"custAddr":"[000000000040]",
// "custId":"[000000000040]",
// "custName":"[000000000040]",
// "custPhone":"[null]",
// "rechareList":[{"BASIC_AMOUNT":100,"USERNAME":"admin","CREATE_DATE":"2016-10-15 19:36:37","SEQNO":8}],
// "strBackFlag":"1","strMeterAddr":"000000000040"}
//    返回参数
//    strBackFlag：'1'代表成功; '-1'不成功；'-4'为传入的表号不能为空；  '-5'传入的表号在数据库中不存在。
//    strMeterAddr：  表号.
//            custName: 用户姓名
//    custPhone:用户手机号（作为参考使用）
//    custId: 用户编号
//    custAddr:用户地址
//    List rechareList=[]  返回的充值记录

    //  充值日期
    private String rechargedate;


    //  充值金额
    private String rechargenum;

    public RechargeHisItemModel(String rechargedate,String rechargenum){
        this.rechargedate = rechargedate;
        this.rechargenum = rechargenum;
    }

    public String getRechargenum() {
        return rechargenum;
    }

    public void setRechargenum(String rechargenum) {
        this.rechargenum = rechargenum;
    }

    public String getRechargedate() {
        return rechargedate;
    }

    public void setRechargedate(String rechargedate) {
        this.rechargedate = rechargedate;
    }


    public  void of(JSONObject data) throws JSONException {
        this.setRechargedate(data.getString("CREATE_DATE"));
        this.setRechargenum(data.getString("BASIC_AMOUNT"));
    }

    public RechargeHisItemModel(){

    }



}
