package com.zfg.org.myexample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.HisEleOptionAdapter;
import com.zfg.org.myexample.adapter.HisEleReadMeterAdapter;
import com.zfg.org.myexample.adapter.MyLocationAdapter2;
import com.zfg.org.myexample.adapter.NoScrollGridView;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.model.HisEleOption;
import com.zfg.org.myexample.model.HisEleReadMeter;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：HisInfoActivity
 * Describe：仪表记录查询
 * Date：2018-04-03 19:11:36
 * Author: CapRobin@yeah.net
 */
public class HisInfoActivity extends MyBaseActivity {

    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.queryTypeEdit)
    private EditText queryTypeEdit;
    @ViewInject(id = R.id.queryNumEdit)
    private EditText queryNumEdit;
    @ViewInject(id = R.id.starTimetEdit)
    private EditText startTimetEdit;
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
    private List<HisEleReadMeter> eleHisReadMeterList;
    private List<HisEleOption> eleHisOptionList;
    private List<MeterInfo> meterinfos;
    @ViewInject(id = R.id.typeHideInnerView)
    private NoScrollGridView typeHideInnerView;

    @ViewInject(id = R.id.numberHideInnerView)
    private ListView numberHideInnerView;
    @ViewInject(id = R.id.settingView)
    private RelativeLayout settingView;


    private static String recordType[] = null;
    private TimePickerView pvTime;
    private int timeFlag = 0;
    private int queryType = -1;


    @ViewInject(id = R.id.getDataList_db)
    private ListView getDataList_db;
    private HisEleReadMeterAdapter mHisEleReadMeterAdapter;
    private HisEleOptionAdapter HisEleOptionAdapter;
    private HttpServiceUtil.CallBack optionCallback;
    private HttpServiceUtil.CallBack readMeterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      界面资源
        setContentView(R.layout.activity_hisinfo);
        pageTitle.setText("记录查询");
//        activity = (HisInfoActivity) context;
        initOptionCallBack();
        initReadMeterCallBack();
//        loading = new DialogLoading(activity);
//        preference = Preference.instance(context);
//        initActivity();
//        replaceFragment("readrechargehisdata", ReadDataHisFragment.getInstance(), false);
        showListData1 = new ArrayList<String>();
        showListData2 = new ArrayList<MeterInfoCheckModel>();

        eleHisReadMeterList = new ArrayList<HisEleReadMeter>();
        eleHisOptionList = new ArrayList<HisEleOption>();

        loading = new DialogLoading(this);
        settingView.setVisibility(View.VISIBLE);
        getDataList_db.setVisibility(View.GONE);

        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        queryTypeEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        startTimetEdit.setOnClickListener(this);
        endTimeEdit.setOnClickListener(this);
        query_submit.setOnClickListener(this);

        setData(1);
        //初始化时间控件
        setdate();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
//                popViewisShow(3);
                if (View.GONE == settingView.getVisibility()) {
                    settingView.setVisibility(View.VISIBLE);
                    getDataList_db.setVisibility(View.GONE);
                } else {
                    if (getDataList_db.getCount() > 0) {
                        settingView.setVisibility(View.GONE);
                        getDataList_db.setVisibility(View.VISIBLE);
                    } else {
                        setToast("请先设置相关查询条件，再进行查询！");
                    }
                }
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
                timeFlag = 0;
                pvTime.show();
                break;
            case R.id.endTimeEdit:
                timeFlag = 1;
                pvTime.show();
                break;
            case R.id.query_submit:

                if (CheckUtil.isNull(queryNumEdit.getText().toString())) {
                    setToast("请输入表地址！");
                    return;
                }
                if (CheckUtil.isNull(startTimetEdit.getText().toString())) {
                    setToast("请选择查询开始日期！");
                    return;
                }
                if (CheckUtil.isNull(endTimeEdit.getText().toString())) {
                    setToast("请选择查询结束日期！");
                    return;
                }
//                clearData();

                String meterNum = CommonUtil.AddZeros(queryNumEdit.getText().toString());
                //判断查询类型(抄表记录|操作记录)
                if (queryType == 0) {
                    //抄表记录
                    queryReadMeterRecord(meterNum);
                } else {
                    //操作记录

                    queryOptionRecord(meterNum);
                }
                break;
        }
    }


    /**
     * Describe：抄表记录查询
     * Params:
     * Date：2018-04-12 15:09:25
     */
    private void queryReadMeterRecord(String meteraddr) {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
//                jsobj.put("userid", userId);
                jsobj.put("bdate", startTimetEdit.getText().toString());
                jsobj.put("edate", endTimeEdit.getText().toString());
            } catch (JSONException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());

            //获取本地测试数据使用
            if (isTest) {
                map.put("hisele", tempJson("hisele.txt"));
            }
            loading.show();
            setDialogLabel("抄表记录查询中...");
//            initReadMeterCallBack();
            SystemAPI.query_readdata_his(map, readMeterCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Describe：抄表记录查询回调函数
     * Params:
     * Date：2018-04-12 15:09:51
     */
    private void initReadMeterCallBack() {
        readMeterCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("抄表记录查询完成");
                //取消等待框
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");

                        if (jStatus.equals("1")) {
                            String meterType = jsonObject.getString("meterType");
                            if (meterType.equals("Elec")) {

                                String consuList = jsonObject.getString("consuList");
                                GsonBuilder gsonB = new GsonBuilder();
                                Gson gson = gsonB.create();
                                eleHisReadMeterList = gson.fromJson(consuList, new TypeToken<ArrayList<HisEleReadMeter>>() {
                                }.getType());

                                if (eleHisReadMeterList != null || eleHisReadMeterList.size() > 0) {
                                    settingView.setVisibility(View.GONE);
                                    getDataList_db.setVisibility(View.VISIBLE);

                                    mHisEleReadMeterAdapter = new HisEleReadMeterAdapter(context, eleHisReadMeterList);
                                    getDataList_db.setAdapter(mHisEleReadMeterAdapter);
                                    mHisEleReadMeterAdapter.notifyDataSetChanged();
                                    getDataList_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            setToast("没有其他数据啦");
                                        }
                                    });
                                } else {
                                    //此处提示用户查询数据失败
                                    setToast("获取数据失败，请重新尝试！");
                                }
                            }else {
                                //水表、气表等抄表查询
                            }
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
     * Describe：操作记录查询
     * Params:
     * Date：2018-04-12 15:10:40
     */

    private void queryOptionRecord(String meteraNum) {
        try {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraNum);
                jsobj.put("bdate", startTimetEdit.getText().toString());
                jsobj.put("edate", endTimeEdit.getText().toString());
            } catch (JSONException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeter", jsobj.toString());

            if (isTest) {
                map.put("hiseleoptionmeter", tempJson("hiseleoptionmeter.txt"));
            }
            loading.show();
            setDialogLabel("操作记录查询中...");

//            initOptionCallBack();
            SystemAPI.query_swich_his(map, optionCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Describe：操作记录查询回调函数
     * Params:
     * Date：2018-04-12 15:11:02
     */

    private void initOptionCallBack() {
        optionCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("操作记录查询完成");
                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {

                            String consuList = jsonObject.getString("msgResult");
                            GsonBuilder gsonB = new GsonBuilder();
                            Gson gson = gsonB.create();
                            eleHisOptionList = gson.fromJson(consuList, new TypeToken<ArrayList<HisEleOption>>() {
                            }.getType());

                            if (eleHisOptionList != null || eleHisOptionList.size() > 0) {
                                settingView.setVisibility(View.GONE);
                                getDataList_db.setVisibility(View.VISIBLE);

                                HisEleOptionAdapter = new HisEleOptionAdapter(context, eleHisOptionList,getDataList_db);
                                getDataList_db.setAdapter(HisEleOptionAdapter);
                                HisEleOptionAdapter.notifyDataSetChanged();
                                getDataList_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        setToast("没有其他数据啦");
                                    }
                                });
                            } else {
                                //此处提示用户查询数据失败
                                setToast("获取数据失败，请重新尝试！");
                            }
                            String toastStr = jsonObject.getString("strBackFlag");
                            setToast(toastStr);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

//            //刷界面之
//            private void SetDate(readmeterModel parmedata) {
//
//            }
        };
    }

    /**
     * Describe：构造View的数据
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData(int userType) {
        //setToast("登录用户为：" + userType);
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
                queryType = i;
                checkType =showListData1.get(i);
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

    /**
     * Describe：初始化时间控件
     * Params:
     * Date：2018-04-04 12:45:54
     */

    private void setdate() {

        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(date);
                if (timeFlag == 0) {
                    startTimetEdit.setText(time);
                } else if (timeFlag == 1) {
                    endTimeEdit.setText(time);
                }
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setSubCalSize(20)
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.rgb(27, 150, 199))//设置选中项的颜色
                .setTitleColor(Color.rgb(27, 150, 199))//标题文字颜色
                .setSubmitColor(Color.rgb(27, 150, 199))//确定按钮文字颜色
                .setCancelColor(Color.rgb(27, 150, 199))//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                .setBgColor(Color.rgb(238, 238, 238))//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
    }
}
