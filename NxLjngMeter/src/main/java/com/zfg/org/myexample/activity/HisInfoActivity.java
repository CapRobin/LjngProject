package com.zfg.org.myexample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.*;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisInfoActivity
 * Describe：仪表记录查询
 * Date：2018-04-03 19:11:36
 * Author: CapRobin@yeah.net
 *
 */
public class HisInfoActivity extends MyBaseActivity{
//
//    //tab标签
//    @ViewInject(id = R.id.tab_bottom)
//    private RadioGroup tabBottom;
//
//    @ViewInject(id = R.id.back_btn)
//    private Button backBtn;
//
//    //不同功能的fragment页面
//    private BaseFragment curentFragment;
//
//    private Preference preference;
//
//    private DialogLoading loading;
//
//    private CallBack loginCallback;
//
//    private HisInfoActivity activity;
//
//    private final String TAG = "HisInfoActivity";




    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.queryTypeEdit)
    private EditText queryTypeEdit;
    @ViewInject(id = R.id.queryNumEdit)
    private EditText queryNumEdit;
    @ViewInject(id = R.id.starTimetEdit)
    private EditText starTimetEdit;
    @ViewInject(id = R.id.endTimeEdit)
    private EditText endTimeEdit;
    @ViewInject(id = R.id.searchNum)
    private ImageView searchNum;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.query_submit)
    private Button query_submit;
    @ViewInject(id = R.id.typeHideView)
    private LinearLayout typeHideView;
    @ViewInject(id = R.id.numberHideView)
    private LinearLayout numberHideView;

    private List<String> showListData1;
    private List<MeterInfoCheckModel> showListData2;
    private List<MeterInfo> meterinfos;
    @ViewInject(id = R.id.typeHideInnerView)
    private NoScrollGridView typeHideInnerView;
    @ViewInject(id = R.id.numberHideInnerView)
    private ListView numberHideInnerView;


    private static String recordType[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      界面资源
        setContentView(R.layout.activity_hisinfo);
        pageTitle.setText("记录查询");
//        activity = (HisInfoActivity) context;
////        initCallBack();
//        loading = new DialogLoading(activity);
//        preference = Preference.instance(context);
//        initActivity();
//        replaceFragment("readrechargehisdata", ReadDataHisFragment.getInstance(), false);
        showListData1 = new ArrayList<String>();
        showListData2 = new ArrayList<MeterInfoCheckModel>();

        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        queryTypeEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        starTimetEdit.setOnClickListener(this);
        endTimeEdit.setOnClickListener(this);
        query_submit.setOnClickListener(this);

        setData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:

                break;
            case R.id.queryTypeEdit:
                closeInputMethod();
                popViewisShow(1);
                break;
            case R.id.searchNum:
                if (!queryNumEdit.getText().equals("")) {
                    queryNumEdit.setText("");
                    closeInputMethod();
                    popViewisShow(2);
                } else {
                    setToast("请先选择抄表项目！");
                }
                break;
            case R.id.starTimetEdit:
                break;
            case R.id.endTimeEdit:
                break;
            case R.id.query_submit:
                break;
        }
    }



    /**
     * Describe：构造View的数据
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData(int userType) {
        setToast("登录用户为：" + userType);
        recordType = getResources().getStringArray(R.array.recordType);

        //加载第一个列表数据
        for (int i = 0; i < recordType.length; i++) {
            showListData1.add(recordType[i].toString());
        }
        //统一(登录默认更新数据)加载第二个列表数据(从数据库获取数据并组装成List)
        MeterInfoBo meterbo = new MeterInfoBo(context);
        meterinfos = meterbo.listAllData();
        for (int i = 0; i < meterinfos.size(); i++) {
            if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                MeterInfoCheckModel model = new MeterInfoCheckModel(String.valueOf(i), meterinfos.get(i).getComm_address(), meterinfos.get(i).getMetertype(), false);
                showListData2.add(model);
            }
        }
        showView1(showListData1);
        showView2(showListData2);
    }



    /**
     * Describe：装载数据
     * Params:
     * Date：2018-03-30 12:00:40
     */
    public void showView1(List<String> list) {
        com.zfg.org.myexample.adapter.MyLocationAdapter locationAdapter = new com.zfg.org.myexample.adapter.MyLocationAdapter(this, list);
        //设置ListView线条的颜色
        typeHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
        typeHideInnerView.setDividerHeight(1);
        typeHideInnerView.setAdapter(locationAdapter);
        typeHideInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String checkType = showListData1.get(i);
                queryTypeEdit.setText(checkType);
                popViewisShow(1);
                if (TextUtils.isEmpty(queryTypeEdit.getText())) {
                    popViewisShow(2);
                    //模拟点击事件
//                    searchNum.performClick();
                } else {
//                    cxbhEdit.setFocusable(true);
                    queryTypeEdit.requestFocus();
                    //设置数据请求类型
//                    setType(i);
                }
            }
        });
    }

    /**
     * Describe：装载数据
     * Params:
     * Date：2018-03-30 12:00:40
     */
    public void showView2(List<MeterInfoCheckModel> list) {
        MyLocationAdapter2 locationAdapter = new MyLocationAdapter2(this, list);
        //设置ListView线条的颜色
        numberHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
        numberHideInnerView.setDividerHeight(1);
        numberHideInnerView.setAdapter(locationAdapter);
        numberHideInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temStr = showListData2.get(i).value;
                queryNumEdit.setText(temStr);
                popViewisShow(2);
            }
        });
    }

    /**
     * Describe：控制视图是否显示
     * Params:
     * Date：2018-03-30 13:31:39
     */
    private void popViewisShow(int id) {

        switch (id) {
            case 1:
                if (View.GONE == typeHideView.getVisibility()) {
                    //关闭第二个View
                    animateClose(numberHideView);
                    //打开第一个View
                    animateOpen(typeHideView);
                } else {
                    //关闭第一个View
                    animateClose(typeHideView);
                }
                break;
            case 2:
                if (View.GONE == numberHideView.getVisibility()) {
                    //关闭第一个View
                    animateClose(typeHideView);
                    //打开第二个View
                    animateOpen(numberHideView);
                } else {
                    //关闭第二个View
                    animateClose(numberHideView);
                }
                break;
            case 3:
//                if (View.GONE == settingView.getVisibility()) {
//                    animateClose(getDataList_db);
//                    //打开View
//                    animateOpen(settingView);
//                } else {
//                    if (getDataList_db.getCount() > 0) {
//                        animateClose(settingView);
//                        setViewData();
//                        animateOpen(getDataList_db);
//                    } else {
//                        setToast("请设置相关查询条件，进行抄表！");
//                    }
//                }


//                //关闭第二个View
//                if (getDataList_db.getCount() > 0) {
//                    getDataList_db.setVisibility(View.GONE);
//                    animateClose(settingView);
//                } else {
//                    setToast("请设置相关查询条件，进行抄表！");
//                }
//                if (View.GONE == settingView.getVisibility()) {
//                    settingView.setVisibility(View.VISIBLE);
//                    getDataList_db.setVisibility(View.GONE);
//                } else {
//                    if (getDataList_db.getCount() > 0) {
//                        settingView.setVisibility(View.GONE);
//                        getDataList_db.setVisibility(View.VISIBLE);
//                    } else {
//                        setToast("请设置相关查询条件，进行抄表！");
//                    }
//                }


                break;
        }
    }
    /**
     * Describe：打开视图
     * Params:
     * Date：2018-03-30 13:26:31
     */
    public static void animateOpen(final View view) {
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
        animator.start();
    }

    /**
     * Describe：隐藏视图
     * Params:
     * Date：2018-03-30 13:28:12
     */

    public static void animateClose(final View view) {
        int origHeight = view.getHeight();

        ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            ;
        });
        animator.start();
    }

    public static ValueAnimator createHeightAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        // animator.setDuration(DURATION);
        return animator;
    }























































//
//    private void initActivity() {
//        tabBottom.setOnCheckedChangeListener(this);
//        backBtn.setOnClickListener(this);
////        tabBottom.check(0);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.back_btn:
//                activity.onBackPressed();
//                break;
//        }
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        String tag;
//        boolean isAdd = true;
//        BaseFragment tempFragment;
//        switch (checkedId) {
//
//            //抄表记录
//            case R.id.readdata:
//                tag = "readdata";
//                tempFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
//                if (tempFragment == null) {
//                    tempFragment = ReadDataHisFragment.getInstance();
//                    isAdd = false;
//                }
//                replaceFragment(tag, tempFragment, isAdd);
//                break;
//            //拉合闸记录
//            case R.id.swichpower:
//                tag = "swichPower";
//                tempFragment = (BaseFragment) getSupportFragmentManager()
//                        .findFragmentByTag(tag);
//                if (tempFragment == null) {
//                    tempFragment = SwichPowerHisFragment.getInstance();
//                    isAdd = false;
//                }
//                replaceFragment(tag, tempFragment, isAdd);
//                break;
//            //充值记录
//
//            case R.id.recharge:
//                tag = "recharge";
//                tempFragment = (BaseFragment) getSupportFragmentManager()
//                        .findFragmentByTag(tag);
//                if (tempFragment == null) {
//                    tempFragment = RechargeHisFragment.getInstance();
//                    isAdd = false;
//                }
//                replaceFragment(tag, tempFragment, isAdd);
//                break;
//        }
//    }
//
//    public void replaceFragment(String tag, BaseFragment tempFragment,
//                                boolean isAdd) {
//        curentFragment = tempFragment;
//        FragmentTransaction tran = getSupportFragmentManager()
//                .beginTransaction();
//        tran.replace(R.id.content_fragment, tempFragment, tag);
//        if (!isAdd) {
//            tran.addToBackStack(tag);
//        }
//        tran.commitAllowingStateLoss();
//    }
//
//    public void show(){
//        loading.show();
//    }
//
//    public void hide(){
//        loading.dismiss();
//    }
//
//    public Preference getPreference(){
//        return preference;
//    }
//
//    public void checkRadio(int id) {
//        tabBottom.check(id);
//    }
//
//    public void onBackPressed() {
//        if (!curentFragment.onBackKeyPressed()) {
//            finish();
//        }
//    }
//
//
//    private void initCallBack() {
//        loginCallback = new CallBack() {
//            @Override
//            public void callback(String json) {
////				loading.hide();
//                // 解析json
//                if (json.length()>3){
//                    try {
//                        JSONObject jsonObject = new JSONObject(json);
//                        String jStatus = jsonObject.toString();
//                        Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
////                                            ToastTool.showToast(jStatus, activity);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    String jSessionId = json;
//
//                    preference.putString(ContantsUtil.USER_SESSIONID,
//                            jSessionId);
//                    HttpServiceUtil.sessionId = jSessionId;
//                    // 重新同步服務器數據
////                    MyThread myThread = new MyThread();
////                    new Thread(myThread).start();
////                                        loading.show();
//                }
//            }
//        };
//    }
//
//    /**
//     * 同步服务端数据
//     */
//    class MyThread implements Runnable {
//        public void run() {
//            // 从服务器获取数据更新
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("jsparam", HttpServiceUtil.sessionId);
////			String result = HttpServiceUtil.post(ContantsUtil.URL_Storage_View, map);
////			if (!CheckUtil.isNull(result)) {
////					handler.sendEmptyMessage(3);
////			}
////			Config.loadUserSet(activity);
//        }
//    };
//
//    private void setDialogLabel(String label) {
//        if (loading == null) {
//            loading = new DialogLoading(context);
//        }
//        loading.setDialogLabel(label);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
}
