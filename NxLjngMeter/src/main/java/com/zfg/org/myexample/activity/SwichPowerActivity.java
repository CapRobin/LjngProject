package com.zfg.org.myexample.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abysmel.dashspinner.DashSpinner;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.MeterAllInfoAdapter;
import com.zfg.org.myexample.adapter.RcAdapterWholeChange;
import com.zfg.org.myexample.compent.WaterWaveProgress;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.model.MeterAllInfo;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.MethodUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：SwichPowerActivity
 * Describe：用来控制水电的开关
 * Date：2018-04-02 18:56:17
 * Author: CapRobin@yeah.net
 */
public class SwichPowerActivity extends BasicActivity implements DashSpinner.OnDownloadIntimationListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
//    @ViewInject(id = R.id.btn_close)
//    private Button btnclose;

//    @ViewInject(id = R.id.btn_open)
//    private Button btnopen;

//    @ViewInject(id = R.id.waterWaveProgress1)
//    private WaterWaveProgress waveProgress;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.select_addr)
    private Button select_addr;

    private Preference preference;

    private DialogLoading loading;

    private HttpServiceUtil.CallBack loginCallback;

    private SwichPowerActivity activity;

    //不同功能的fragment页面
    private BaseFragment curentFragment;

    private HttpServiceUtil.CallBack dataCallback;

    private MeterInfoDialog meterInfoDialog;


//    @ViewInject(id = R.id.eleLayout)
//    private LinearLayout eleLayout;
    private LinearLayout eleLayout;
    private LinearLayout waterLayout;

//    @ViewInject(id = R.id.waterWaveProgress)
//    private WaterWaveProgress waveProgress;
    @ViewInject(id = R.id.openOrCloseBtn)
    private CheckBox openOrCloseBtn;
    private Drawable bg_a;
    private Drawable bg_b;
    //1：表示关；2表示开
//    private int isOpen = 1;
    private boolean isOpen = true;
    private List<MeterInfo> meterinfos;;
    private List<MeterAllInfo> getListData;
    private List<MeterAllInfo> newList;;
    @ViewInject(id = R.id.numberHideInnerView)
    private ListView numberHideInnerView;
    @ViewInject(id = R.id.meterInfoList)
    private RecyclerView meterInfoList;
    @ViewInject(id = R.id.numberHideView)
    private LinearLayout numberHideView;
    private RcAdapterWholeChange recycleAdapter;





    float mnProgress = 0.0f;
    DashSpinner mDashSpinner = null;
    Handler mHandler = new Handler();

    //Run to 100% and then show Success
    Runnable runnableSuccess = new Runnable() {
        @Override
        public void run() {
            setProgress();

            //SUCCESS
            if (mnProgress <= 1.0) mHandler.postDelayed(this, 30);
            else mDashSpinner.showSuccess();
        }
    };

    //Run to 50% and show failure
    Runnable runnableFailure = new Runnable() {
        @Override
        public void run() {
            setProgress();
            //FAILURE
            if (mnProgress <= 0.5) mHandler.postDelayed(this, 30);
            else mDashSpinner.showFailure();
        }
    };

    //Show Unknown Error
    Runnable runnableUnknown = new Runnable() {
        @Override
        public void run() {
            //UNKNOWN
            mDashSpinner.showUnknown();
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swichpower);
        activity = (SwichPowerActivity) context;
//        ContantsUtil.curUser; 当前用户id
        preference = Preference.instance(context);
        loading = new DialogLoading(activity);
        settingBtn.setVisibility(View.GONE);
        preference = Preference.instance(this);
        userType = preference.getInt(Preference.USERTYPE);


        waterLayout = (LinearLayout) findViewById(R.id.waterLayout);
        eleLayout = (LinearLayout) findViewById(R.id.eleLayout);

        if(userType == 1|| userType == 3){
            eleLayout.setVisibility(View.GONE);
//            waterLayout.setVisibility(View.VISIBLE);
            initCallBackWater();
//            waveProgress.setShowProgress(false);
//            waveProgress.animateWave();

            mDashSpinner = (DashSpinner) findViewById(R.id.progress_spinner);
            mDashSpinner.setOnDownloadIntimationListener(this);
        }else if(userType == 2){
            waterLayout.setVisibility(View.GONE);
            eleLayout.setVisibility(View.VISIBLE);
            initCallBackEle();

        }
        pageTitle.setText("合闸拉闸");


        eleLayout.setBackgroundResource(R.drawable.dp_k_200);
        bg_a = ContextCompat.getDrawable(context, R.drawable.dp_k_200);
        bg_b = ContextCompat.getDrawable(context, R.drawable.dp_g_200);
        eleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检测表号是否为空
                if (meterAddr.getText().length() == 0) {
                    Toast.makeText(context, "请先输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isOpen) {
                    swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 0);

//                    closeSwitch();
//                    openOrCloseBtn.setChecked(false);
                } else {
                    swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 1);

//                    openSwitch();
//                    openOrCloseBtn.setChecked(true);
                }
//

            }
        });

        openOrCloseBtn.setChecked(isOpen);
        openOrCloseBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //检测表号是否为空
                if (meterAddr.getText().length() == 0) {
                    Toast.makeText(context, "请先输入表地址！", Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(!b);
                    return;
                }


                waterLayout.setVisibility(View.GONE);
                if (b) {
                    swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 1);
//                    openSwitch();
                } else {
                    swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 0);
//                    closeSwitch();
                }
            }
        });

        initActivity();
//        initCallBack();

        getListData = new ArrayList<MeterAllInfo>();
        newList = new ArrayList<MeterAllInfo>();
        setData(userType);
        //刷新RecyclerView数据
        refreshUI();
        //设置表号输入监听
        setListener();
        //初始化时间控件
    }

    private void setProgress() {
        mnProgress += 0.01;
        mDashSpinner.setProgress(mnProgress);
    }








    /**
     * Describe：刷新RecyclerView列表
     * Params: []
     * Return: void
     * Date：2018-04-25 13:15:37
     */
    private void refreshUI() {
        if (recycleAdapter == null) {
            recycleAdapter = new RcAdapterWholeChange(this, newList);
            meterInfoList.setAdapter(recycleAdapter);
        } else {
            recycleAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Describe：询表号输入框监听事件方法
     * Params: []
     * Return: void
     * Date：2018-04-25 10:15:11
     */
    private void setListener() {
        //edittext的监听
        meterAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //EditText内容改变时执行
            @Override
            public void afterTextChanged(Editable editable) {
                //匹配文字 变色
                doChangeColor(editable.toString().trim());
            }
        });
        //Recyclerview的点击监听
        recycleAdapter.setOnItemClickListener(new RcAdapterWholeChange.onItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                meterAddr.setText(newList.get(pos).getCOMM_ADDRESS());
                meterInfoList.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Describe：文字及颜色匹配
     * Params: [text]
     * Return: void
     * Date：2018-04-25 10:15:30
     */
    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        newList.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            newList.addAll(getListData);
            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            recycleAdapter.setText(null);
            refreshUI();
        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (MeterAllInfo i : getListData) {
                if (i.getCUSTOMER_NAME().contains(text) || i.getCOMM_ADDRESS().contains(text) || i.getPHONE1().contains(text) || i.getTERMINAL_ADDRESS().contains(text)) {
                    newList.add(i);
                }
            }
            //设置要变色的关键字
            recycleAdapter.setText(text);
            refreshUI();
            if (newList.size() > 0) {
//                MethodUtil.animateClose(cxbhHideView);
                meterInfoList.setVisibility(View.VISIBLE);
            } else {
                meterInfoList.setVisibility(View.GONE);
            }
        }
    }


    /**
     * Describe：构造View的数据
     * Params: [userType]
     * Return: void
     * Date：2018-04-25 13:11:15
     */
    private void setData(int userType) {
//        recordType = getResources().getStringArray(R.array.recordType);
//
//        //加载第一个列表数据
//        for (int i = 0; i < recordType.length; i++) {
//            showListData1.add(recordType[i].toString());
//        }
//        //
        // 开始加载第二个列表数据(从数据库获取数据并组装成List)
        MeterInfoBo meterbo = new MeterInfoBo(context);
        meterinfos = meterbo.listAllData();
        for (int i = 0; i < meterinfos.size(); i++) {
            if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                MeterAllInfo allInfo = new MeterAllInfo();
                allInfo.setCUSTOMER_NAME(meterinfos.get(i).getCustomer_name());
                allInfo.setCOMM_ADDRESS(meterinfos.get(i).getComm_address());
                allInfo.setMETER_TYPE(meterinfos.get(i).getMetertype());
                allInfo.setPHONE1(meterinfos.get(i).getTerminal_address());
                allInfo.setTERMINAL_ADDRESS(meterinfos.get(i).getAreaname());
                getListData.add(allInfo);
            }
        }
        showView2(getListData);
    }


    /**
     * Describe：装载数据列表2
     * Params: [list]
     * Return: void
     * Date：2018-04-25 13:14:42
     */
    public void showView2(List<MeterAllInfo> list) {
        /**
         * Describe：装载数据列表2
         * Params: [list]
         * Return: void
         * Date：2018-04-25 13:14:42
         */
        MeterAllInfoAdapter locationAdapter = new MeterAllInfoAdapter(this, list);
        //设置ListView线条的颜色
        numberHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
        numberHideInnerView.setDividerHeight(1);
        numberHideInnerView.setAdapter(locationAdapter);
        numberHideInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temStr = getListData.get(i).getCOMM_ADDRESS();
                meterAddr.setText(temStr);
                popViewisShow();
                meterInfoList.setVisibility(View.GONE);
            }
        });
    }


    /**
     * Describe：控制视图是否显示
     * Params:
     * Date：2018-03-30 13:31:39
     */
    private void popViewisShow() {
        if (View.GONE == numberHideView.getVisibility()) {
            meterAddr.clearFocus();
            int getHeight = MethodUtil.dip2px(this, meterinfos.size() * 100);
            MethodUtil.animateOpen(numberHideView, getHeight, 700);
        } else {
            //关闭第二个View
            MethodUtil.animateClose(numberHideView);
        }
    }

    private void closeSwitch() {
        eleLayout.setBackgroundResource(R.drawable.dp_g_200);
        //渐变切换
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{bg_a, bg_b});
        eleLayout.setBackgroundDrawable(td);
        td.startTransition(1000);
        isOpen = false;
    }

    private void openSwitch() {
        eleLayout.setBackgroundResource(R.drawable.dp_k_200);
        //渐变切换
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{bg_b, bg_a});
        eleLayout.setBackgroundDrawable(td);
        td.startTransition(1000);
        isOpen = true;
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
        backHome.setOnClickListener(this);
//        btnclose.setOnClickListener(this);
//        btnopen.setOnClickListener(this);
        select_addr.setOnClickListener(this);

        //RecyclerView相关设置
        meterInfoList.setLayoutManager(new LinearLayoutManager(this));
        //线条设置
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_bg));
        meterInfoList.addItemDecoration(divider);


        meterAddr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    MethodUtil.animateClose(numberHideView);
                }
            }
        });
    }

    //{"strBackFlag":"0","strMeterAddr":"000000000040","strPassAuthentication":""}

    /**
     * Describe：开关阀请求控制(0：关阀；1：开阀)
     * Params:
     * Date：2018-04-03 11:35:18
     */

    private void swichData(String meteraddr, Integer stuts) {
        String titleStr;
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
//                jsobj.put("userid", userId);
                jsobj.put("operationId", String.valueOf(stuts));
                jsobj.put("userid", preference.getString(Preference.CACHE_USER));
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());

            if (stuts == 0) {
                titleStr = "正在执行关阀操作";
            } else {
                titleStr = "正在执行开阀操作";
            }
            loading.show();
            setDialogLabel(titleStr);
            SystemAPI.meter_onwater(map, dataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initCallBackWater() {
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
                        waterLayout.setVisibility(View.VISIBLE);
                        if (jStatus.equals("1") && actionFlag.equals("open")){
                            mDashSpinner.resetValues();
                            mnProgress = 0.0f;
                            mHandler.post(runnableSuccess);
                            isOpen = true;
                        } else if (jStatus.equals("1") && actionFlag.equals("close")){
                            mDashSpinner.resetValues();
                            mnProgress = 0.0f;
                            mHandler.post(runnableFailure);
                            isOpen = false;
                        } else {
                            mDashSpinner.resetValues();
                            mnProgress = 0.0f;
                            mHandler.post(runnableUnknown);
                            openOrCloseBtn.setChecked(isOpen);
                        }

//                        if (jStatus.equals("1") && actionFlag.equals("open")){
//                            Toast.makeText(context,"打开阀门成功",Toast.LENGTH_SHORT).show();
//                            waveProgress.setProgress(75);
//                        } else if (jStatus.equals("1") && actionFlag.equals("close")){
//                            Toast.makeText(context,"关闭阀门成功",Toast.LENGTH_SHORT).show();
//                            waveProgress.setProgress(0);
//                        } else
//                        if (jStatus.equals("-1")){
//                            Toast.makeText(context,"操作阀门失败",Toast.LENGTH_SHORT).show();
//                            waveProgress.setProgress(0);
//                        } else
//                        if (jStatus.equals("-5")){
//                            Toast.makeText(context,"该表地址不存在",Toast.LENGTH_SHORT).show();
//                            waveProgress.setProgress(0);
//                        } else {
//                            Toast.makeText(context,"操作阀门失败",Toast.LENGTH_SHORT).show();
//                            waveProgress.setProgress(0);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        };
    }

    private void initCallBackEle() {
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
                        if (jStatus.equals("1") && actionFlag.equals("open")) {
//                            waveProgress.setProgress(75);

                            openSwitch();
                            openOrCloseBtn.setChecked(true);
                            setToast("打开阀门成功");
                            isOpen = true;
                        } else if (jStatus.equals("1") && actionFlag.equals("close")) {
//                            waveProgress.setProgress(0);

                            closeSwitch();
                            openOrCloseBtn.setChecked(false);
                            setToast("关闭阀门成功");
                            isOpen = false;
                        } else if (jStatus.equals("-1")) {
//                            waveProgress.setProgress(0);

                            openOrCloseBtn.setChecked(isOpen);
                            setToast("操作阀门失败");
                        } else if (jStatus.equals("-5")) {
//                            waveProgress.setProgress(0);

                            openOrCloseBtn.setChecked(isOpen);
                            setToast("该表地址不存在");
                        } else {
//                            waveProgress.setProgress(0);

                            openOrCloseBtn.setChecked(isOpen);
                            setToast("操作阀门失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        };
    }

    @Override
    public void onDownloadIntimationDone(DashSpinner.DASH_MODE dashMode) {
        switch (dashMode) {
            case SUCCESS:
                Toast.makeText(this, "打开阀门成功!", Toast.LENGTH_SHORT).show();
                break;
            case FAILURE:
                Toast.makeText(this, "关闭阀门成功!", Toast.LENGTH_SHORT).show();
                break;
            case UNKNOWN:
                Toast.makeText(this, "系统异常，请重试!", Toast.LENGTH_SHORT).show();
                break;
        }
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
            case R.id.backHome:
                finish();
                break;
            case R.id.select_addr:
//                if (meterInfoDialog == null) {
//                    meterInfoDialog = new MeterInfoDialog(activity);
//                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
//                        @Override
//                        public void callBack(List<MeterInfoCheckModel> data) {
//                            for (MeterInfoCheckModel item : data) {
//                                if (item.isCheck) {
//                                    meterAddr.setText(item.value);
//                                }
//                            }
//                        }
//                    });
//                }
//                meterInfoDialog.show();


                meterInfoList.setVisibility(View.GONE);
                if (!meterAddr.getText().equals("")) {
                    meterAddr.setText("");
                    closeInputMethod();
                    popViewisShow();
                }
                break;
            case R.id.btn_close:
                if (meterAddr.getText().length() == 0) {
                    Toast.makeText(context, "请先输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 0);
                break;
            case R.id.btn_open:
                if (meterAddr.getText().length() == 0) {
                    Toast.makeText(context, "请先输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                swichData(CommonUtil.AddZeros(meterAddr.getText().toString()), 1);
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
