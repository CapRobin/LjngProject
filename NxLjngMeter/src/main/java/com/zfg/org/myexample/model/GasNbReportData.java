package com.zfg.org.myexample.model;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：GasNbReportData
 * Describe：NB气表上报数据实体类
 * Date：2018-04-02 14:45:31
 * Author: CapRobin@yeah.net
 *
 */
public class GasNbReportData {

	private String ID;
	//小区地址
	private String AREANAME;
	//客户姓名
	private String CUSTOMER_NAME;
	//客户地址
	private String CUSTOMER_ADDRESS;
	//表号
	private String METER_SERIAL_NUM;
	//表内时间
	private String METERTIME;

	//用量
	private String USENUM;
	//费用
	private String USEMONEY;
	//余额
	private String LAVEMONEY;
	//执行价格
	private String BCPRICE;
	//上报时间
	private String DATE_TIME;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getAREANAME() {
		return AREANAME;
	}

	public void setAREANAME(String AREANAME) {
		this.AREANAME = AREANAME;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
		this.CUSTOMER_NAME = CUSTOMER_NAME;
	}

	public String getCUSTOMER_ADDRESS() {
		return CUSTOMER_ADDRESS;
	}

	public void setCUSTOMER_ADDRESS(String CUSTOMER_ADDRESS) {
		this.CUSTOMER_ADDRESS = CUSTOMER_ADDRESS;
	}

	public String getMETER_SERIAL_NUM() {
		return METER_SERIAL_NUM;
	}

	public void setMETER_SERIAL_NUM(String METER_SERIAL_NUM) {
		this.METER_SERIAL_NUM = METER_SERIAL_NUM;
	}

	public String getMETERTIME() {
		return METERTIME;
	}

	public void setMETERTIME(String METERTIME) {
		this.METERTIME = METERTIME;
	}

	public String getUSENUM() {
		return USENUM;
	}

	public void setUSENUM(String USENUM) {
		this.USENUM = USENUM;
	}

	public String getUSEMONEY() {
		return USEMONEY;
	}

	public void setUSEMONEY(String USEMONEY) {
		this.USEMONEY = USEMONEY;
	}

	public String getLAVEMONEY() {
		return LAVEMONEY;
	}

	public void setLAVEMONEY(String LAVEMONEY) {
		this.LAVEMONEY = LAVEMONEY;
	}

	public String getBCPRICE() {
		return BCPRICE;
	}

	public void setBCPRICE(String BCPRICE) {
		this.BCPRICE = BCPRICE;
	}

	public String getDATE_TIME() {
		return DATE_TIME;
	}

	public void setDATE_TIME(String DATE_TIME) {
		this.DATE_TIME = DATE_TIME;
	}
}