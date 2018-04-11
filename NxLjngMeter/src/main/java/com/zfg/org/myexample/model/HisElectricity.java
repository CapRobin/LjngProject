package com.zfg.org.myexample.model;

/**
 * Describe：电表记录查询实体类
 * Params:
 * Date：2018-04-11 16:08:00
 */

public class HisElectricity {
	//	时间
	private String DATE_TIME;
	//	正向总功
	private String ZHZONG;
	//	尖值
	private String ZHJIAN;
	//	峰值
	private String ZHFENG;
	//	平值
	private String ZHPING;
	//	谷值
	private String ZHGU;

	//	反向总功
	private String FZONG;
	//	尖值
	private String FJIAN;
	//	峰值
	private String FFENG;
	//	平值
	private String FPING;
	//	谷值
	private String FGU;

	public String getDATE_TIME() {
		return DATE_TIME;
	}

	public void setDATE_TIME(String DATE_TIME) {
		this.DATE_TIME = DATE_TIME;
	}

	public String getZHZONG() {
		return ZHZONG;
	}

	public void setZHZONG(String ZHZONG) {
		this.ZHZONG = ZHZONG;
	}

	public String getZHJIAN() {
		return ZHJIAN;
	}

	public void setZHJIAN(String ZHJIAN) {
		this.ZHJIAN = ZHJIAN;
	}

	public String getZHFENG() {
		return ZHFENG;
	}

	public void setZHFENG(String ZHFENG) {
		this.ZHFENG = ZHFENG;
	}

	public String getZHPING() {
		return ZHPING;
	}

	public void setZHPING(String ZHPING) {
		this.ZHPING = ZHPING;
	}

	public String getZHGU() {
		return ZHGU;
	}

	public void setZHGU(String ZHGU) {
		this.ZHGU = ZHGU;
	}

	public String getFZONG() {
		return FZONG;
	}

	public void setFZONG(String FZONG) {
		this.FZONG = FZONG;
	}

	public String getFJIAN() {
		return FJIAN;
	}

	public void setFJIAN(String FJIAN) {
		this.FJIAN = FJIAN;
	}

	public String getFFENG() {
		return FFENG;
	}

	public void setFFENG(String FFENG) {
		this.FFENG = FFENG;
	}

	public String getFPING() {
		return FPING;
	}

	public void setFPING(String FPING) {
		this.FPING = FPING;
	}

	public String getFGU() {
		return FGU;
	}

	public void setFGU(String FGU) {
		this.FGU = FGU;
	}
}