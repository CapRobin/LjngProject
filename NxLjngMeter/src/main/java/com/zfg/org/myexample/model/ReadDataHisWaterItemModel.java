package com.zfg.org.myexample.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016-10-15.
 * 抄表历史记录
 */
public class ReadDataHisWaterItemModel {

//{"consuList":[{"DATE_TIME":"2016-10-15 17:59:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:27:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:25:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:24:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:13:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:11:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:09:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:07:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:05:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:03:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 17:01:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 16:59:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},
// {"DATE_TIME":"2016-10-15 16:58:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2}],
// "custAddr":"[000000000040]","custId":"[000000000040]","custName":"[000000000040]","custPhone":"[null]","strBackFlag":"1","strMeterAddr":"000000000040"}
    private String DATE_TIME;  //抄表时间
    private String CONSU;  //Customer ID

    public String getSTMTCONSU() {
        return STMTCONSU;
    }

    public void setSTMTCONSU(String STMTCONSU) {
        this.STMTCONSU = STMTCONSU;
    }

    public String getDATE_TIME() {
        return DATE_TIME;
    }

    public void setDATE_TIME(String DATE_TIME) {
        this.DATE_TIME = DATE_TIME;
    }

    public String getCONSU() {
        return CONSU;
    }

    public void setCONSU(String CONSU) {
        this.CONSU = CONSU;
    }

    private String STMTCONSU;// Customer Address

    public  void of(JSONObject data) throws JSONException {
        this.setSTMTCONSU(data.getString("STMTCONSU"));
        this.setDATE_TIME(data.getString("DATE_TIME"));
        this.setCONSU(data.getString("CONSU"));
    }

    public ReadDataHisWaterItemModel(){

    }

    public ReadDataHisWaterItemModel(String DATE_TIME, String CONSU, String STMTCONSU){
        this.DATE_TIME = DATE_TIME;
        this.CONSU = CONSU;
        this.STMTCONSU = STMTCONSU;
    }



}
