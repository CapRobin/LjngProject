package com.zfg.org.myexample.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-11-21.
 */

public class ReadingTaskItemModel {

    /*
{"areaRateList":
[
[{"AREANAME":"客户专用","PARENT_ID":39851,"METER_TYPE":"120","TOTAL":6,"HADCOUNT":4,"FAILED":2,"RATE":66.67},
{"AREANAME":"客户专用","PARENT_ID":39851,"METER_TYPE":"301","TOTAL":10,"HADCOUNT":10,"FAILED":0,"RATE":100}],
[{"AREANAME":"系统室","PARENT_ID":39225,"METER_TYPE":"120","TOTAL":410,"HADCOUNT":null,"FAILED":null,"RATE":null}
]
],"strBackFlag":"1"}
     */
    private String CONSU;  //小区或台区编号
    private String CONSUNAME;  //小区或台区名称
    private String TOTAL;       //该台区下总表数量
    private String HADCOUNT;    //统计已抄表数量
    private String FAILED;  //统计已抄表数量
    private String RATE;    //成功比例
    private String METER_TYPE; //表类型



    public ReadingTaskItemModel(String CONSU, String CONSUNAME, String TOTAL, String HADCOUNT, String FAILED, String RATE, String METER_TYPE) {
        this.CONSU = CONSU;
        this.CONSUNAME = CONSUNAME;
        this.TOTAL = TOTAL;
        this.HADCOUNT = HADCOUNT;
        this.FAILED = FAILED;
        this.RATE = RATE;
        this.METER_TYPE = METER_TYPE;
    }

    public ReadingTaskItemModel() {
    }

    public  void of(JSONObject data) throws JSONException {
        this.setCONSUNAME(data.getString("AREANAME"));
        this.setCONSU(data.getString("PARENT_ID"));
        this.setMETER_TYPE(data.getString("METER_TYPE"));
        this.setTOTAL(data.getString("TOTAL"));
        this.setHADCOUNT(data.getString("HADCOUNT"));
        this.setFAILED(data.getString("FAILED"));
        this.setRATE(data.getString("RATE"));
    }

    public String getCONSU() {
        return CONSU;
    }

    public void setCONSU(String CONSU) {
        this.CONSU = CONSU;
    }

    public String getCONSUNAME() {
        return CONSUNAME;
    }

    public void setCONSUNAME(String CONSUNAME) {
        this.CONSUNAME = CONSUNAME;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getHADCOUNT() {
        return HADCOUNT;
    }

    public void setHADCOUNT(String HADCOUNT) {
        this.HADCOUNT = HADCOUNT;
    }

    public String getFAILED() {
        return FAILED;
    }

    public void setFAILED(String FAILED) {
        this.FAILED = FAILED;
    }

    public String getRATE() {
        return RATE;
    }

    public void setRATE(String RATE) {
        this.RATE = RATE;
    }

    public String getMETER_TYPE() {
        return METER_TYPE;
    }

    public void setMETER_TYPE(String METER_TYPE) {
        this.METER_TYPE = METER_TYPE;
    }



}
