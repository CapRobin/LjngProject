/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zfg.org.myexample.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class WorkerDto {
    public Integer ID;
    public String NAME;
    public Integer COMPANYID;
    
    
    public void of(JSONObject data) throws JSONException {
            this.ID = data.getInt("ID");
            this.NAME = data.getString("NAME");
            this.COMPANYID = data.getInt("COMPANYID");
    }
}
