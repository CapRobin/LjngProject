package com.zfg.org.myexample.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-24.
 */

public class ReadDataItemModel {

    private String dataname;
    private String datadata;
    private String dataunit;

    public ReadDataItemModel(String dataname, String datadata, String dataunit) {
        this.dataname = dataname;
        this.datadata = datadata;
        this.dataunit = dataunit;
    }

    public ReadDataItemModel() {

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


    public void of(JSONArray data) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            if (i==1){
                dataname = data.getString(i);
            }
            if (i==2){
                datadata = data.getString(i);
            }
            if (i==3){
//                System.out.println(data.get(i).toString());
                if (data.get(i).equals(null)){
                    dataunit = "";
                } else {
                    dataunit = data.getString(i);
                }
            }
        }
    }
}

