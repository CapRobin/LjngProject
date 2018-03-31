package com.zfg.org.myexample.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-17.
 */

public class BleDataModel {

    private String dataname;
    private String datadata;
    private String dataunit;

    public BleDataModel(String dataname, String datadata, String dataunit) {
        this.dataname = dataname;
        this.datadata = datadata;
        this.dataunit = dataunit;
    }

    public BleDataModel(){

    }

    public String getDataunit() {
        return dataunit;
    }

    public void setDataunit(String dataunit) {
        this.dataunit = dataunit;
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public String getDatadata() {
        return datadata;
    }

    public void setDatadata(String datadata) {
        this.datadata = datadata;
    }


    public  void of(String data) {
        String[] strarray =data.split("\\|");
        for (int i = 0; i < strarray.length; i++){
            if (i == 0){
                this.dataname = strarray[i].toString();
            }
            if (i == 1){
                this.datadata = strarray[i].toString();
            }
            if (i == 2){
                this.dataunit = strarray[i].toString();
            }
        }
    }

}
