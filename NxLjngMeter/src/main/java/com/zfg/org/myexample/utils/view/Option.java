package com.zfg.org.myexample.utils.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liu on 15/11/23.
 */
public class Option {
    public static final String WATER_INFO = "WATER INFO";
    public static final String GAS_INFO = "WATER INFO";
    public static final String ELEC_INFO = "WATER INFO";
    public static final String HOT_INFO = "WATER INFO";

    public static final String WATER_PRICE_INFO = "WATER INFO";
    public static final String GAS_PRICE_INFO = "WATER INFO";
    public static final String ELEC_PRICE_INFO = "WATER INFO";
    public static final String HOT_PRICE_INFO = "WATER INFO";


    public static Map<String, OPTION_PROPERTY> OPTIONS_MAP = new HashMap<String, OPTION_PROPERTY>();

    static {
        OPTIONS_MAP.put(WATER_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_WATER_INFO);
        OPTIONS_MAP.put(GAS_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_GAS_INFO);
        OPTIONS_MAP.put(ELEC_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_ELEC_INFO);
        OPTIONS_MAP.put(HOT_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_HOT_INFO);
        OPTIONS_MAP.put(WATER_PRICE_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_WATER_PRICE_INFO);
        OPTIONS_MAP.put(GAS_PRICE_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_GAS_PRICE_INFO);
        OPTIONS_MAP.put(ELEC_PRICE_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_ELEC_PRICE_INFO);
        OPTIONS_MAP.put(HOT_PRICE_INFO, OPTION_PROPERTY.PROPERTY_READ_CUR_HOT_PRICE_INFO);

    }

    private String name;
    private OPTION_PROPERTY propertyType;

    public Option() {
    }

    public Option(String name, OPTION_PROPERTY propertyType) {
        this.name = name;
        this.propertyType = propertyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OPTION_PROPERTY getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(OPTION_PROPERTY propertyType) {
        this.propertyType = propertyType;
    }

    public static enum OPTION_PROPERTY {
        //抄读水表当前用水信息
        PROPERTY_READ_CUR_WATER_INFO,
        //抄读气表当前用气信息
        PROPERTY_READ_CUR_GAS_INFO,
        //抄读电表当前用电信息
        PROPERTY_READ_CUR_ELEC_INFO,
        //抄读热表当前用热信息
        PROPERTY_READ_CUR_HOT_INFO,

        //抄读水表当前价格信息
        PROPERTY_READ_CUR_WATER_PRICE_INFO,
        //抄读气表当前价格信息
        PROPERTY_READ_CUR_GAS_PRICE_INFO,
        //抄读电表当前价格信息
        PROPERTY_READ_CUR_ELEC_PRICE_INFO,
        //抄读热表当前价格信息
        PROPERTY_READ_CUR_HOT_PRICE_INFO,

        //抄读水表当前用水信息
        PROPERTY_READ_REAL_TIME_WATER_STATUS,
        //抄读气表当前用气信息
        PROPERTY_READ_REAL_TIME_GAS_STATUS,
        //抄读电表当前用电信息
        PROPERTY_READ_REAL_TIME_ELEC_STATUS,
        //抄读热表当前用热信息
        PROPERTY_READ_REAL_TIME_HOT_STATUS,

        //抄读集中器中数据
        PROPERTY_READ_DATA_IN_CONCENTRATOR,
        //抄读表中的数据
        PROPERTY_READ_DATA_IN_METER,
        //抄读日冻结数据
        PROPERTY_READ_DATA_FREEZING_DATA,
        //抄读月冻结数据
        PROPERTY_READ_DATA_FREEZING_MONTH_DATA,
        //抄读结算日冻结数据
        PROPERTY_READ_DATA_FREEZING_SETTLEMENT_DATA
    }
}
