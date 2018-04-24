package com.zfg.org.myexample.model;

/**
 * Describe：用户信息实体类
 * Params:
 * Date：2018-04-11 16:08:00
 */

public class MeterAllInfo {
    //用户ID
    private String ID;
    //表号
    private String COMM_ADDRESS;
    //仪表类型
    private String METER_TYPE;
    //用户姓名
    private String CUSTOMER_NAME;
    //电话
    private String PHONE1;
    //
    private String CUSTOMER_ID_NO;
    //用户地址
    private String CUSTOMER_ADDRESS;
    //终端地址
    private String TERMINAL_ADDRESS;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCOMM_ADDRESS() {
        return COMM_ADDRESS;
    }

    public void setCOMM_ADDRESS(String COMM_ADDRESS) {
        this.COMM_ADDRESS = COMM_ADDRESS;
    }

    public String getMETER_TYPE() {
        return METER_TYPE;
    }

    public void setMETER_TYPE(String METER_TYPE) {
        this.METER_TYPE = METER_TYPE;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getPHONE1() {
        return PHONE1;
    }

    public void setPHONE1(String PHONE1) {
        this.PHONE1 = PHONE1;
    }

    public String getCUSTOMER_ID_NO() {
        return CUSTOMER_ID_NO;
    }

    public void setCUSTOMER_ID_NO(String CUSTOMER_ID_NO) {
        this.CUSTOMER_ID_NO = CUSTOMER_ID_NO;
    }

    public String getCUSTOMER_ADDRESS() {
        return CUSTOMER_ADDRESS;
    }

    public void setCUSTOMER_ADDRESS(String CUSTOMER_ADDRESS) {
        this.CUSTOMER_ADDRESS = CUSTOMER_ADDRESS;
    }

    public String getTERMINAL_ADDRESS() {
        return TERMINAL_ADDRESS;
    }

    public void setTERMINAL_ADDRESS(String TERMINAL_ADDRESS) {
        this.TERMINAL_ADDRESS = TERMINAL_ADDRESS;
    }

    public String getAREANAME() {
        return AREANAME;
    }

    public void setAREANAME(String AREANAME) {
        this.AREANAME = AREANAME;
    }

    private String AREANAME;
}