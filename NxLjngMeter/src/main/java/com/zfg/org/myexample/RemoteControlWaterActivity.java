package com.zfg.org.myexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.dialog.NumberDialog;
import com.zfg.org.myexample.fragment.ReadDataFragment;
import com.zfg.org.myexample.fragment.ReadDataWaterFragment;
import com.zfg.org.myexample.fragment.RechargeFragment;
import com.zfg.org.myexample.fragment.RechargeWaterFragment;
import com.zfg.org.myexample.fragment.SwichPowerFragment;
import com.zfg.org.myexample.fragment.SwichPowerWaterFragment;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.Preference;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author zfg
 */
public class RemoteControlWaterActivity extends BasicActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @ViewInject(id = R.id.tab_bottom)
    private RadioGroup tabBottom;

    private BaseFragment curentFragment;

    private Preference preference;

    private DialogLoading loading;

    private CallBack loginCallback;

    private RemoteControlWaterActivity activity;

    private NumberDialog valueDialog;

    private ScrollView scrollView;

    final int WHAT_SCROLL = 0, WHAT_BTN_VISABEL = WHAT_SCROLL + 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //zhdl 绑定样式
        setContentView(R.layout.activity_remotecontrol);
        //上下文， 扔到变量里面
        activity = (RemoteControlWaterActivity) context;
//        initCallBack();
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        initActivity();
        replaceFragment("readdata", ReadDataWaterFragment.getInstance(), false);
    }

    private void initActivity() {
        tabBottom.setOnCheckedChangeListener(this);
        tabBottom.check(R.id.readdata);
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void goLogin() {
        // 提交到服务器
        try {
//                    String username = nameEditText.getText().toString();
//                    String password = pswEditText.getText().toString();
            String username = "admin";
            String password = "password";

            preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("name", username);
                jsobj.put("password", password);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
            SystemAPI.login(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        // 提交到服务器
        try {
//                    String username = nameEditText.getText().toString();
//                    String password = pswEditText.getText().toString();
            String username = "admin";
            String password = "password";

            preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("name", username);
                jsobj.put("password", password);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
            SystemAPI.login(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //抄表
    private void read(String addr) {
        try {
            String meterAddr = addr;
            String userId = "01";
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("userid", userId);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    map.put("ngMeterGas",URLEncoder.encode(jsobj.toString(),HTTP.UTF_8));
//                    loading.show();
            SystemAPI.meter_read(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //合闸
    private void meter_on(String addr) {
        // 提交到服务器
        try {
            String meterAddr = addr;
            String operationId = "1";

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("operationId", operationId);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
            SystemAPI.meter_on(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拉闸
    private void meter_off(String addr) {
        // 提交到服务器
        try {
            String meterAddr = addr;
            String operationId = "0";

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("userid", operationId);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
            SystemAPI.meter_off(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拉闸
    private void recharge(String addr) {
        // 提交到服务器
        try {
            String meterAddr = "000000001234";
            String chargeAmount = "100";

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("chargeAmount", chargeAmount);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
            SystemAPI.meter_recharge(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        loginCallback = new CallBack() {
            @Override
            public void callback(String json) {
//				loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
//                                            ToastTool.showToast(jStatus, activity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String jSessionId = json;

                    preference.putString(ContantsUtil.USER_SESSIONID,
                            jSessionId);
                    HttpServiceUtil.sessionId = jSessionId;
                    // 显示登录状态
                    MyThread myThread = new MyThread();
                    new Thread(myThread).start();
//                                        loading.show();       
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

    ;


    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    //zhdl  进入该页面后， check（0）触发tab事件， 下面是点击不同的tab标签的事件
    @Override
    public void onCheckedChanged(RadioGroup group, int id) {
        String tag;
        boolean isAdd = true;
        BaseFragment tempFragment;
        switch (id) {
            //抄表
            case R.id.readdata:
                tag = "readdata";
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = ReadDataWaterFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;

            //拉合闸
            case R.id.swichpower:
                tag = "swichPower";
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = SwichPowerWaterFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;
            //充值
            case R.id.recharge:
                tag = "recharge";
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = RechargeWaterFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;
        }
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

    public void show() {
        loading.show();
    }

    public void hide() {
        loading.dismiss();
    }

    public void onBackPressed() {
        if (!curentFragment.onBackKeyPressed()) {
            finish();
        }
    }

    public void checkRadio(int id) {
        tabBottom.check(id);
    }

    public Preference getPreference() {
        return preference;
    }

    public void onClick(View v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
