package com.zfg.org.myexample.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.compent.WaterWaveProgress;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016-10-15.
 */
public class SwichPowerActivity_bak extends BasicActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @ViewInject(id = R.id.btn_close)
    private Button btnclose;

    @ViewInject(id = R.id.btn_open)
    private Button btnopen;

    @ViewInject(id = R.id.waterWaveProgress1)
    private WaterWaveProgress waveProgress;

    @ViewInject(id = R.id.meterAddrs)
    private RelativeLayout meterAddrs;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.select_addr)
    private EditText select_addr;

    private Preference preference;

    private DialogLoading loading;

    private HttpServiceUtil.CallBack loginCallback;

    private SwichPowerActivity activity;

    //不同功能的fragment页面
    private BaseFragment curentFragment;

    private HttpServiceUtil.CallBack dataCallback;

    private MeterInfoDialog meterInfoDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swichpower_bak);
        activity = (SwichPowerActivity) context;
//        ContantsUtil.curUser; 当前用户id
        preference = Preference.instance(context);
        loading = new DialogLoading(activity);
        initActivity();
        initCallBack();

        waveProgress.setShowProgress(false);
        waveProgress.animateWave();
    }

    public void replaceFragment(String tag, BaseFragment tempFragment,
                                boolean isAdd) {
        curentFragment = tempFragment;
        FragmentTransaction tran = getSupportFragmentManager()
                .beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

    private void initActivity() {
        backBtn.setOnClickListener(this);
        btnclose.setOnClickListener(this);
        btnopen.setOnClickListener(this);
        meterAddrs.setOnClickListener(this);
        select_addr.setOnClickListener(this);
    }

    //{"strBackFlag":"0","strMeterAddr":"000000000040","strPassAuthentication":""}
    private void swichData(String meteraddr,Integer stuts) {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
//                jsobj.put("userid", userId);
                jsobj.put("operationId", String.valueOf(stuts));
                jsobj.put("userid",preference.getString(Preference.CACHE_USER));
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());

            loading.show();
            setDialogLabel("开始开关阀请等待");
            SystemAPI.meter_onwater(map, dataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("开关阀完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        String actionFlag = jsonObject.getString("actionFlag");
                        if (jStatus.equals("1") && actionFlag.equals("open")){
                            Toast.makeText(context,"打开阀门成功",Toast.LENGTH_SHORT).show();
                            waveProgress.setProgress(75);
                        } else if (jStatus.equals("1") && actionFlag.equals("close")){
                            Toast.makeText(context,"关闭阀门成功",Toast.LENGTH_SHORT).show();
                            waveProgress.setProgress(0);
                        } else
                        if (jStatus.equals("-1")){
                            Toast.makeText(context,"操作阀门失败",Toast.LENGTH_SHORT).show();
                            waveProgress.setProgress(0);
                        } else
                        if (jStatus.equals("-5")){
                            Toast.makeText(context,"该表地址不存在",Toast.LENGTH_SHORT).show();
                            waveProgress.setProgress(0);
                        } else {
                            Toast.makeText(context,"操作阀门失败",Toast.LENGTH_SHORT).show();
                            waveProgress.setProgress(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        };
    }

    /**
     * 同步服务端数据
     */
    class MyThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("jsparam", HttpServiceUtil.sessionId);
//			String result = HttpServiceUtil.post(ContantsUtil.URL_Storage_View, map);
//			if (!CheckUtil.isNull(result)) {
//					handler.sendEmptyMessage(3);
//			}
//			Config.loadUserSet(activity);
        }
    }

    //  变更标签内容
    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    ;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
//            case R.id.meterAddrs:
            case R.id.select_addr:
                if (meterInfoDialog == null) {
                    meterInfoDialog = new MeterInfoDialog(activity);
                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
                        @Override
                        public void callBack(List<MeterInfoCheckModel> data) {
                            for (MeterInfoCheckModel item : data) {
                                if (item.isCheck) {
                                    meterAddr.setText(item.value);
                                }
                            }
                        }
                    });
                }
                meterInfoDialog.show();
                break;
            case R.id.btn_close:
                if (meterAddr.getText().length() == 0){
                    Toast.makeText(context,"请先输入表地址！",Toast.LENGTH_SHORT).show();
                    return;
                }
                swichData(CommonUtil.AddZeros(meterAddr.getText().toString()),0);
                break;
            case R.id.btn_open:
                if (meterAddr.getText().length() == 0){
                    Toast.makeText(context,"请先输入表地址！",Toast.LENGTH_SHORT).show();
                    return;
                }
                swichData(CommonUtil.AddZeros(meterAddr.getText().toString()),1);
                break;
//            case R.id.switch_main_1:
//                if (switchButton.isChecked()) {
//                    //              开关阀
//                    if (meterAddr.getText().length() == 0){
//                        Toast.makeText(context,"请先输入表号！",Toast.LENGTH_SHORT).show();
//                        return;
//                    } else{
//                        swichData(CommonUtil.AddZeros(meterAddr.getText().toString()),0);
//                        waveProgress.setProgress(75);
//                    }
//
//                } else {
//                    if (meterAddr.getText().length() == 0){
//                        Toast.makeText(context,"请先输入表号！",Toast.LENGTH_SHORT).show();
//                        return;
//                    } else{
//                        swichData(CommonUtil.AddZeros(meterAddr.getText().toString()),1);
//                        waveProgress.setProgress(0);
//                    }
//
//                }
//                break;
        }
    }


}
