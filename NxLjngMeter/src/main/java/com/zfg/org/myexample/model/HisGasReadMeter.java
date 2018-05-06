package com.zfg.org.myexample.model;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisGasReadMeter
 * Describe：气表抄表记录查询实体类
 * Date：2018-04-02 14:45:31
 * Author: CapRobin@yeah.net
 *
 */
public class HisGasReadMeter {

	//时间
	private String DATE_TIME;
	//用气量示值
	private String GASCONSUMPTION;
	//结算周期示值
	private String STMTCONSU;
	//剩余金额(总)
	private String CRGBAL;
	//剩余金额(本地)
	private String SURPLUSAMTLCL;
	//剩余金额(远程)
	private String SURPLUSAMTRMT;
	//购气次数(本地)
	private String CRGCNTLCL;
	//购气次数(远程)
	private String CRGCNTRMT;

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



//"date_time": "2018-03-31 16:53:00",
//"gas consumption": 622.7,
//"stmtconsu": 0,
//"crgbal": null,
//"surplusamtlcl": null,
//"surplusamtrmt": null,
//"crgcntlcl": null,
//"crgcntrmt": null
}