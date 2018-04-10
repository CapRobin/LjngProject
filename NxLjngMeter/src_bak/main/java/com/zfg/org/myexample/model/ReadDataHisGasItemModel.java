package com.zfg.org.myexample.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-10-24.
 */

public class ReadDataHisGasItemModel {

    //    用气量示值    ——GasConsumption,
//    结算周期量市值——StmtConsu,
//    剩余金额总    ——CrgBal,
//    剩余金额本地  ——SurplusAmtLcl ,
//    剩余金额远程  ——SurplusAmtRmt,
//    购气次数本地  ——CrgCntLcl,
//    购气次数远程  ——CrgCntRmt
    private String DATE_TIME;
    private String GASCONSUMPTION;  //
    private String STMTCONSU;  //
    private String CRGBAL;
    private String SURPLUSAMTLCL;
    private String SURPLUSAMTRMT;
    private String CRGCNTLCL;
    private String CRGCNTRMT;




    public  void of(JSONObject data) throws JSONException {
        this.setSTMTCONSU(data.getString("STMTCONSU"));
        this.setCRGBAL(data.getString("CRGBAL"));
        this.setCRGCNTLCL(data.getString("CRGCNTLCL"));
        this.setCRGCNTRMT(data.getString("CRGCNTRMT"));
        this.setDATE_TIME(data.getString("DATE_TIME"));
        this.setGASCONSUMPTION(data.getString("GASCONSUMPTION"));
        this.setSURPLUSAMTLCL(data.getString("SURPLUSAMTLCL"));
        this.setSURPLUSAMTRMT(data.getString("SURPLUSAMTRMT"));

    }

    public ReadDataHisGasItemModel(String DATE_TIME, String GASCONSUMPTION, String STMTCONSU, String CRGBAL, String SURPLUSAMTLCL, String SURPLUSAMTRMT, String CRGCNTLCL, String CRGCNTRMT) {
        this.DATE_TIME = DATE_TIME;
        this.GASCONSUMPTION = GASCONSUMPTION;
        this.STMTCONSU = STMTCONSU;
        this.CRGBAL = CRGBAL;
        this.SURPLUSAMTLCL = SURPLUSAMTLCL;
        this.SURPLUSAMTRMT = SURPLUSAMTRMT;
        this.CRGCNTLCL = CRGCNTLCL;
        this.CRGCNTRMT = CRGCNTRMT;
    }

    public ReadDataHisGasItemModel(){
    }

    public String getDATE_TIME() {
        return DATE_TIME;
    }

    public void setDATE_TIME(String DATE_TIME) {
        this.DATE_TIME = DATE_TIME;
    }

    public String getGASCONSUMPTION() {
        return GASCONSUMPTION;
    }

    public void setGASCONSUMPTION(String GASCONSUMPTION) {
        this.GASCONSUMPTION = GASCONSUMPTION;
    }

    public String getSTMTCONSU() {
        return STMTCONSU;
    }

    public void setSTMTCONSU(String STMTCONSU) {
        this.STMTCONSU = STMTCONSU;
    }

    public String getCRGBAL() {
        return CRGBAL;
    }

    public void setCRGBAL(String CRGBAL) {
        this.CRGBAL = CRGBAL;
    }

    public String getSURPLUSAMTLCL() {
        return SURPLUSAMTLCL;
    }

    public void setSURPLUSAMTLCL(String SURPLUSAMTLCL) {
        this.SURPLUSAMTLCL = SURPLUSAMTLCL;
    }

    public String getSURPLUSAMTRMT() {
        return SURPLUSAMTRMT;
    }

    public void setSURPLUSAMTRMT(String SURPLUSAMTRMT) {
        this.SURPLUSAMTRMT = SURPLUSAMTRMT;
    }

    public String getCRGCNTLCL() {
        return CRGCNTLCL;
    }

    public void setCRGCNTLCL(String CRGCNTLCL) {
        this.CRGCNTLCL = CRGCNTLCL;
    }

    public String getCRGCNTRMT() {
        return CRGCNTRMT;
    }

    public void setCRGCNTRMT(String CRGCNTRMT) {
        this.CRGCNTRMT = CRGCNTRMT;
    }

}
