package com.zfg.org.myexample.model;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisGasReadMeter
 * Describe：气表开关阀返回状态
 * Date：2018-04-02 14:45:31
 * Author: CapRobin@yeah.net
 *
 */
public class ValveStatusInfo {


//"ID": 15533023,
//"TASK_PARAM": "182530|180506145005|1",
//"JOB_ACTION_DEF_ID": 109,
//"I18N": "job.action.commandReconn",
//"IS_LOCKED": 0,
//"START_TIME": "2018-05-05T06:50:02Z",
//"METER_SERIAL_NUM": "201803295555",
//"CUSTOMER_NAME": "赵四",
//"AREAID": 182447,
//"METERID": 182530

	//任务编号
	private String ID;
	//任务参数(最后一位1:打开阀门；0:关闭阀门)
	private String TASK_PARAM;
	//任务名称(109:打开阀门)
	private String JOB_ACTION_DEF_ID;
	//
	private String I18N;
	//任务状态(0:等待执行；1:正在执行；2:成功；3:失败)
	private String IS_LOCKED;

	//执行时间
	private String START_TIME;
	//表号
	private String METER_SERIAL_NUM;
	//客户姓名
	private String CUSTOMER_NAME;
	//
	private String AREAID;
	//
	private String METERID;


	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getTASK_PARAM() {
		return TASK_PARAM;
	}

	public void setTASK_PARAM(String TASK_PARAM) {
		this.TASK_PARAM = TASK_PARAM;
	}

	public String getJOB_ACTION_DEF_ID() {
		return JOB_ACTION_DEF_ID;
	}

	public void setJOB_ACTION_DEF_ID(String JOB_ACTION_DEF_ID) {
		this.JOB_ACTION_DEF_ID = JOB_ACTION_DEF_ID;
	}

	public String getI18N() {
		return I18N;
	}

	public void setI18N(String i18N) {
		I18N = i18N;
	}

	public String getIS_LOCKED() {
		return IS_LOCKED;
	}

	public void setIS_LOCKED(String IS_LOCKED) {
		this.IS_LOCKED = IS_LOCKED;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String START_TIME) {
		this.START_TIME = START_TIME;
	}

	public String getMETER_SERIAL_NUM() {
		return METER_SERIAL_NUM;
	}

	public void setMETER_SERIAL_NUM(String METER_SERIAL_NUM) {
		this.METER_SERIAL_NUM = METER_SERIAL_NUM;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
		this.CUSTOMER_NAME = CUSTOMER_NAME;
	}

	public String getAREAID() {
		return AREAID;
	}

	public void setAREAID(String AREAID) {
		this.AREAID = AREAID;
	}

	public String getMETERID() {
		return METERID;
	}

	public void setMETERID(String METERID) {
		this.METERID = METERID;
	}
}