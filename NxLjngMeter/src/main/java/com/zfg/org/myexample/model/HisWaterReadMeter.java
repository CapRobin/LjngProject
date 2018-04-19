package com.zfg.org.myexample.model;

/**
 * Describe：水表抄表记录查询实体类
 * Params:
 * Date：2018-04-11 16:08:00
 */

public class HisWaterReadMeter {

	private String DATE_TIME;
	private String CONSU;
	private String STMTCONSU;
	private String RECHARGECOUNTS;

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

	public String getSTMTCONSU() {
		return STMTCONSU;
	}

	public void setSTMTCONSU(String STMTCONSU) {
		this.STMTCONSU = STMTCONSU;
	}

	public String getRECHARGECOUNTS() {
		return RECHARGECOUNTS;
	}

	public void setRECHARGECOUNTS(String RECHARGECOUNTS) {
		this.RECHARGECOUNTS = RECHARGECOUNTS;
	}
}