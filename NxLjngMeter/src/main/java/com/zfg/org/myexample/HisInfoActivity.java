package com.zfg.org.myexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.fragment.ReadDataFragment;
import com.zfg.org.myexample.fragment.ReadDataHisFragment;
import com.zfg.org.myexample.fragment.RechargeFragment;
import com.zfg.org.myexample.fragment.RechargeHisFragment;
import com.zfg.org.myexample.fragment.SwichPowerFragment;
import com.zfg.org.myexample.fragment.SwichPowerHisFragment;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-06-02.
 */
public class HisInfoActivity extends BasicActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    //tab标签
    @ViewInject(id = R.id.tab_bottom)
    private RadioGroup tabBottom;

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    //不同功能的fragment页面
    private BaseFragment curentFragment;

    private Preference preference;

    private DialogLoading loading;

    private CallBack loginCallback;

    private HisInfoActivity activity;

    private final String TAG = "HisInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      界面资源
        setContentView(R.layout.activity_hisinfo);
        activity = (HisInfoActivity) context;
//        initCallBack();
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        initActivity();
        replaceFragment("readrechargehisdata", ReadDataHisFragment.getInstance(), false);
    }


    private void initActivity() {
        tabBottom.setOnCheckedChangeListener(this);
        backBtn.setOnClickListener(this);
//        tabBottom.check(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String tag;
        boolean isAdd = true;
        BaseFragment tempFragment;
        switch (checkedId) {

            //抄表记录
            case R.id.readdata:
                tag = "readdata";
                tempFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = ReadDataHisFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;
            //拉合闸记录
            case R.id.swichpower:
                tag = "swichPower";
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = SwichPowerHisFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;
            //充值记录

            case R.id.recharge:
                tag = "recharge";
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = RechargeHisFragment.getInstance();
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

    public void show(){
        loading.show();
    }

    public void hide(){
        loading.dismiss();
    }

    public Preference getPreference(){
        return preference;
    }

    public void checkRadio(int id) {
        tabBottom.check(id);
    }

    public void onBackPressed() {
        if (!curentFragment.onBackKeyPressed()) {
            finish();
        }
    }


    private void initCallBack() {
        loginCallback = new CallBack() {
            @Override
            public void callback(String json) {
//				loading.hide();
                // 解析json
                if (json.length()>3){
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
                    // 重新同步服務器數據
//                    MyThread myThread = new MyThread();
//                    new Thread(myThread).start();
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
    };

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
