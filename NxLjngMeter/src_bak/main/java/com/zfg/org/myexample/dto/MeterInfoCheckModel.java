package com.zfg.org.myexample.dto;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：MeterInfoCheckModel
 * Describe：
 * Date：2018-03-30 17:30:27
 * Author: CapRobin@yeah.net
 *
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