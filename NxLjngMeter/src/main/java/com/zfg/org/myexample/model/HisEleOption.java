package com.zfg.org.myexample.model;

/**
 * Describe：电表操作记录查询实体类
 * Params:
 * Date：2018-04-11 16:08:00
 */

public class HisEleOption {

	//操作类型
	private String CATEGORY;
	//操作项目
	private String ACTION_KEY;
	//操作表号
	private String PARAMS;
	//操作员
	private String USER_ID;
	//操作时间
	private String OPERATE_TIME;

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	public String getACTION_KEY() {
		return ACTION_KEY;
	}

	public void setACTION_KEY(String ACTION_KEY) {
		this.ACTION_KEY = ACTION_KEY;
	}

	public String getPARAMS() {
		return PARAMS;
	}

	public void setPARAMS(String PARAMS) {
		this.PARAMS = PARAMS;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public String getOPERATE_TIME() {
		return OPERATE_TIME;
	}

	public void setOPERATE_TIME(String OPERATE_TIME) {
		this.OPERATE_TIME = OPERATE_TIME;
	}
}