package com.zfg.org.myexample.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class readmeterModel {

    private String meterAddr;	//表地址
    private String strBackData;	//抄表时间
    private String strBackData1;	//当前水量
    private String strBackData2;	//结算周期水量
    private String strBackData3;	//购水次数
//    private String strBackData4;	//
    private String strBackData5;	//阀门开关状态
    private String strBackData6;	//阀门异常状态
    private String strBackData7;	//电池状态
    private String strBackData8;	//电池状态
    private String strBackData9;	//强磁探测

//    {"strBackData":"2016-10-16 17:59:00.0",
//            "strBackData1":"24.7","strBackData2":"6.2","s" +
//            "trBackData3":"7","strBackData4":"Sun Oct 16 17:59:42 CST 2016","strBackData5":"0","strBackData6":"0","strBackData7":"0","strBackData8":"0","strBackData9":"0","strBackFlag":"0","strMeterAddr":"000000000040","strName":"RealTimePower","strRandom":"0"}
//    strBackFlag：'0':代表成功  '-1' 失败
//    strBackFlag:  成功失败标志，0代表成功
//    strBackData:  抄表时间
//    sstrBackData1：当前水量
//    strBackData2：结算周期水量
//    strBackData3：购水次数
//    strBackData5：阀门开关状态，0—关，1—开
//    strBackData6：阀门异常状态，0—正常，1—异常
//    strBackData7：电池状态    0—正常，1—异常
//    strBackData8：干簧管状态  0—正常，1—异常
//    strBackData9：强磁探测    0—正常，1—干扰


    public readmeterModel(String meterAddr,String strBackData,String strBackData1,
                        String strBackData2,String strBackData3,//String strBackData4,
                        String strBackData5,String strBackData6,String strBackData7,String strBackData8,String strBackData9){
            this.meterAddr = meterAddr;
            this.strBackData = strBackData;
            this.strBackData1 = strBackData1;
            this.strBackData2 = strBackData2;
            this.strBackData3 = strBackData3;
//            this.strBackData4 = strBackData4;
            this.strBackData5 = strBackData5;
            this.strBackData6 = strBackData6;
            this.strBackData7 = strBackData7;
            this.strBackData8 = strBackData8;
            this.strBackData9 =strBackData9;
    }

    public readmeterModel(){

    }


    /*
 sstrBackData1：当前水量
strBackData2：结算周期水量
strBackData3：购水次数
strBackData5：阀门开关状态，0—关，1—开
strBackData6：阀门异常状态，0—正常，1—异常
strBackData7：电池状态    0—正常，1—异常
strBackData8：干簧管状态  0—正常，1—异常
strBackData9：强磁探测    0—正常，1—干扰
    * */
    
    public void of(JSONObject data) throws JSONException {
        this.setMeterAddr(data.getString("strMeterAddr"));
        this.setStrBackData(data.getString("strBackData"));
        this.setStrBackData1(data.getString("strBackData1"));
        this.setStrBackData2(data.getString("strBackData2"));
        this.setStrBackData3(data.getString("strBackData3"));
//        this.setStrBackData4(data.getString("strBackData4"));
//{"strBackData5":"1","strBackData6":"0","strBackData7":"0","strBackData8":"0","strBackData9":"0","strBackFlag":"0","strMeterAddr":"000000000023","strName":"RealTimePower","strRandom":"0"}
        if (Integer.parseInt(data.getString("strBackData5").toString()) == 0)
            this.setStrBackData5("关");
        else if (Integer.parseInt(data.getString("strBackData5").toString()) == 1)
            this.setStrBackData5("开");

        if (Integer.parseInt(data.getString("strBackData6").toString()) == 0)
            this.setStrBackData6("正常");
        else if (Integer.parseInt(data.getString("strBackData6").toString()) == 1)
            this.setStrBackData6("异常");

        if (Integer.parseInt(data.getString("strBackData7").toString()) == 0)
            this.setStrBackData7("正常");
        else if (Integer.parseInt(data.getString("strBackData7").toString()) == 1)
            this.setStrBackData7("异常");

        if (Integer.parseInt(data.getString("strBackData8").toString()) == 0)
            this.setStrBackData8("正常");
        else  if (Integer.parseInt(data.getString("strBackData8").toString()) == 1)
            this.setStrBackData8("异常");

        if (Integer.parseInt(data.getString("strBackData9").toString()) == 0)
            this.setStrBackData9("正常");
        else if (Integer.parseInt(data.getString("strBackData9").toString()) == 1)
            this.setStrBackData9("异常");
    }
    /**
     * @return the meterAddr
     */
    public String getMeterAddr() {
        return meterAddr;
    }

    /**
     * @param meterAddr the meterAddr to set
     */
    public void setMeterAddr(String meterAddr) {
        this.meterAddr = meterAddr;
    }

    /**
     * @return the strBackData
     */
    public String getStrBackData() {
        return strBackData;
    }

    /**
     * @param strBackData the strBackData to set
     */
    public void setStrBackData(String strBackData) {
        this.strBackData = strBackData;
    }

    /**
     * @return the strBackData1
     */
    public String getStrBackData1() {
        return strBackData1;
    }

    /**
     * @param strBackData1 the strBackData1 to set
     */
    public void setStrBackData1(String strBackData1) {
        this.strBackData1 = strBackData1;
    }

    /**
     * @return the strBackData2
     */
    public String getStrBackData2() {
        return strBackData2;
    }

    /**
     * @param strBackData2 the strBackData2 to set
     */
    public void setStrBackData2(String strBackData2) {
        this.strBackData2 = strBackData2;
    }

    /**
     * @return the strBackData3
     */
    public String getStrBackData3() {
        return strBackData3;
    }

    /**
     * @param strBackData3 the strBackData3 to set
     */
    public void setStrBackData3(String strBackData3) {
        this.strBackData3 = strBackData3;
    }

    /**
     * @return the strBackData4
     */
//    public String getStrBackData4() {
//        return strBackData4;
//    }
//
//    /**
//     * @param strBackData4 the strBackData4 to set
//     */
//    public void setStrBackData4(String strBackData4) {
//        this.strBackData4 = strBackData4;
//    }

    /**
     * @return the strBackData5
     */
    public String getStrBackData5() {
        return strBackData5;
    }

    /**
     * @param strBackData5 the strBackData5 to set
     */
    public void setStrBackData5(String strBackData5) {
        this.strBackData5 = strBackData5;
    }

    /**
     * @return the strBackData6
     */
    public String getStrBackData6() {
        return strBackData6;
    }

    /**
     * @param strBackData6 the strBackData6 to set
     */
    public void setStrBackData6(String strBackData6) {
        this.strBackData6 = strBackData6;
    }

    /**
     * @return the strBackData7
     */
    public String getStrBackData7() {
        return strBackData7;
    }

    /**
     * @param strBackData7 the strBackData7 to set
     */
    public void setStrBackData7(String strBackData7) {
        this.strBackData7 = strBackData7;
    }

    public String getStrBackData9() {
        return strBackData9;
    }

    public void setStrBackData9(String strBackData9) {
        this.strBackData9 = strBackData9;
    }

    public String getStrBackData8() {
        return strBackData8;
    }

    public void setStrBackData8(String strBackData8) {
        this.strBackData8 = strBackData8;
    }


    public String tostring(){
        return meterAddr+':'+strBackData1+':'+strBackData2+':'+strBackData3+':'+""+':'+strBackData5+':'+strBackData6+':'+strBackData7;
    }
}
