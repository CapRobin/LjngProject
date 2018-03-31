package com.zfg.org.myexample.dto;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MeterInfoCheckModel {

    public String key;
    public String value;
    public String metertype;
    public boolean isCheck;

    public MeterInfoCheckModel(String key, String value,String metertype, boolean isCheck) {
        this.key = key;
        this.value = value;
        this.metertype = metertype;
        this.isCheck = false;
    }
}