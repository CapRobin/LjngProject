package com.zfg.org.myexample.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
import com.zfg.org.myexample.adapter.HisWaterReadMeterAdapter;
import com.zfg.org.myexample.adapter.MeterAllInfoAdapter;
import com.zfg.org.myexample.adapter.NoScrollGridView;
import com.zfg.org.myexample.adapter.RcAdapterWholeChange;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.model.HisEleOption;
import com.zfg.org.myexample.model.HisEleReadMeter;
import com.zfg.org.myexample.model.HisWaterReadMeter;
import com.zfg.org.myexample.model.MeterAllInfo;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.MethodUtil;
import com.zfg.org.myexample.utils.Preference;

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
 * Name：RecordsQueryActivity
 * Describe：仪表记录查询
 * Date：2018-04-03 19:11:36
 * Author: CapRobin@yeah.net
 */
public class RecordsQueryActivity extends MyBaseActivity implements OnTouchListener {

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
    @ViewInject(id = R.id.typeHideInnerView)
    private NoScrollGridView typeHideInnerView;
    @ViewInject(id = R.id.numberHideInnerView)
    private ListView numberHideInnerView;
    @ViewInject(id = R.id.meterInfoList)
    private RecyclerView meterInfoList;
    @ViewInject(id = R.id.settingView)
    private RelativeLayout settingView;
    @ViewInject(id = R.id.recordsQueryList_ele)
    private ListView eleRecordsQueryList;
    @ViewInject(id = R.id.recordsQueryList_water)
    private ListView waterRecordsQueryList;

    private List<String> showListData1;
    private List<MeterAllInfo> getListData;
    private List<MeterAllInfo> newList;
    private List<HisEleReadMeter> eleHisReadMeterList;
    private List<HisWaterReadMeter> waterHisReadMeterList;
    private List<HisEleOption> eleHisOptionList;
    private HisEleReadMeterAdapter mHisEleReadMeterAdapter;
    private HisWaterReadMeterAdapter mHisWaterReadMeterAdapter;
    private com.zfg.org.myexample.adapter.HisEleOptionAdapter HisEleOptionAdapter;
    private HttpServiceUtil.CallBack optionCallback;
    private HttpServiceUtil.CallBack readMeterCallback;
    private RcAdapterWholeChange recycleAdapter;
    private List<MeterInfo> meterinfos;
    private TimePickerView pvTime;
    private int timeFlag = 0;
    private int queryType = -1;
    private static String recordType[] = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_query);

        viewInit();
        setData(userType);
        //刷新RecyclerView数据
        refreshUI();
        //设置表号输入监听
        setListener();
        //初始化时间控件
        setdate();
    }

    /**
     * Describe：初始化相关配置
     * Params: []
     * Return: void
     * Date：2018-04-25 13:07:54
     */
    private void viewInit(){
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);
        switch (userType) {
            case 1:
                pageTitle.setText("水表记录查询");
                break;
            case 2:
                pageTitle.setText("电表记录查询");
                break;
            case 3:
                pageTitle.setText("气表记录查询");
                break;
            case 4:
                pageTitle.setText("热表记录查询");
                break;
            default:
                pageTitle.setText("记录查询");
                break;
        }
        initOptionCallBack();
        initReadMeterCallBack();
        showListData1 = new ArrayList<String>();
        getListData = new ArrayList<MeterAllInfo>();
        newList = new ArrayList<MeterAllInfo>();
        eleHisReadMeterList = new ArrayList<HisEleReadMeter>();
        waterHisReadMeterList = new ArrayList<HisWaterReadMeter>();
        eleHisOptionList = new ArrayList<HisEleOption>();

        loading = new DialogLoading(this);
        settingView.setVisibility(View.VISIBLE);
        eleRecordsQueryList.setVisibility(View.GONE);
        waterRecordsQueryList.setVisibility(View.GONE);

        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        query_submit.setOnClickListener(this);
        queryTypeEdit.setOnTouchListener(this);
        startTimetEdit.setOnTouchListener(this);
        endTimeEdit.setOnTouchListener(this);
        queryNumEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    MethodUtil.animateClose(typeHideView);
                    MethodUtil.animateClose(numberHideView);
                }
            }
        });

        //RecyclerView相关设置
        meterInfoList.setLayoutManager(new LinearLayoutManager(this));
        //线条设置
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_bg));
        meterInfoList.addItemDecoration(divider);
    }

    /**
     * Describe：构造View的数据
     * Params: [userType]
     * Return: void
     * Date：2018-04-25 13:11:15
     */
    private void setData(int userType) {
        recordType = getResources().getStringArray(R.array.recordType);

        //加载第一个列表数据
        for (int i = 0; i < recordType.length; i++) {
            showListData1.add(recordType[i].toString());
        }
        //
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
        showView1(showListData1);
        showView2(getListData);
    }

    /**
     * Describe：装载数据列表1
     * Params: [list]
     * Return: void
     * Date：2018-04-25 13:14:42
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
                checkType = showListData1.get(i);
                queryTypeEdit.setText(checkType);
                popViewisShow(1);
                if (TextUtils.isEmpty(queryTypeEdit.getText())) {
                    popViewisShow(2);
                } else {
                    queryTypeEdit.requestFocus();
                }
            }
        });
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
                queryNumEdit.setText(temStr);
                popViewisShow(2);
                meterInfoList.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Describe：刷新RecyclerView列表
     * Params: []
     * Return: void
     * Date：2018-04-25 13:15:37
     */
    private void refreshUI() {
        if (recycleAdapter == null) {
            recycleAdapter = new RcAdapterWholeChange(RecordsQueryActivity.this, newList);
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
        queryNumEdit.addTextChangedListener(new TextWatcher() {
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
                queryNumEdit.setText(newList.get(pos).getCOMM_ADDRESS());
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.queryTypeEdit:
                    meterInfoList.setVisibility(View.GONE);
                    closeInputMethod();
                    popViewisShow(1);
                    break;
                case R.id.starTimetEdit:
                    MethodUtil.animateClose(typeHideView);
                    MethodUtil.animateClose(numberHideView);
                    meterInfoList.setVisibility(View.GONE);
                    timeFlag = 0;
                    pvTime.show();
                    break;
                case R.id.endTimeEdit:
                    MethodUtil.animateClose(typeHideView);
                    MethodUtil.animateClose(numberHideView);
                    meterInfoList.setVisibility(View.GONE);
                    timeFlag = 1;
                    pvTime.show();
                    break;
                default:
                    break;
            }
        }
        return false;
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
                    eleRecordsQueryList.setVisibility(View.GONE);
                    waterRecordsQueryList.setVisibility(View.GONE);
                } else {
                    int eleCount = eleRecordsQueryList.getCount();
                    int waterCount = waterRecordsQueryList.getCount();
                    if (eleCount > 0 || waterCount > 0) {
                        settingView.setVisibility(View.GONE);
                        if (eleCount > 0) {
                            eleRecordsQueryList.setVisibility(View.VISIBLE);
                        } else {
                            waterRecordsQueryList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        setToast("请先设置相关查询条件，再进行查询！");
                    }
                }
                break;
            case R.id.searchNum:
                meterInfoList.setVisibility(View.GONE);
                if (!queryNumEdit.getText().equals("")) {
                    queryNumEdit.setText("");
                    closeInputMethod();
                    popViewisShow(2);
                } else {
                    setToast("请先选择抄表项目！");
                }
                break;
            case R.id.query_submit:
                if (CheckUtil.isNull(queryTypeEdit.getText().toString())) {
                    setToast("请输入查询类型！");
                    return;
                }
                if (CheckUtil.isNull(queryNumEdit.getText().toString())) {
                    setToast("请输入表地址！");
                    return;
                }
                if (queryNumEdit.getText().toString().trim().length() < 12) {
                    setToast("表地址输入有误请重新输入！");
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
     * Params: [meteraddr]
     * Return: void
     * Date：2018-04-25 13:24:21
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
     * Params: []
     * Return: void
     * Date：2018-04-25 13:24:48
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
                                    eleRecordsQueryList.setVisibility(View.VISIBLE);

                                    mHisEleReadMeterAdapter = new HisEleReadMeterAdapter(context, eleHisReadMeterList, eleRecordsQueryList);
                                    eleRecordsQueryList.setAdapter(mHisEleReadMeterAdapter);
                                    mHisEleReadMeterAdapter.notifyDataSetChanged();
                                    eleRecordsQueryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            setToast("没有其他数据啦");
                                        }
                                    });
                                } else {
                                    //此处提示用户查询数据失败
                                    setToast("获取数据失败，请重新尝试！");
                                }
                            } else if (meterType.equals("Water")) {
                                //水表抄表查询~~~~~~~~~~~~~
                                String consuList = jsonObject.getString("consuList");
                                GsonBuilder gsonB = new GsonBuilder();
                                Gson gson = gsonB.create();
                                waterHisReadMeterList = gson.fromJson(consuList, new TypeToken<ArrayList<HisWaterReadMeter>>() {
                                }.getType());

                                if (waterHisReadMeterList != null || waterHisReadMeterList.size() > 0) {
                                    settingView.setVisibility(View.GONE);
                                    eleRecordsQueryList.setVisibility(View.GONE);
                                    waterRecordsQueryList.setVisibility(View.VISIBLE);

                                    mHisWaterReadMeterAdapter = new HisWaterReadMeterAdapter(context, waterHisReadMeterList, waterRecordsQueryList);
                                    waterRecordsQueryList.setAdapter(mHisWaterReadMeterAdapter);
                                    mHisWaterReadMeterAdapter.notifyDataSetChanged();
                                    waterRecordsQueryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            setToast("没有其他数据啦");
                                        }
                                    });
                                } else {
                                    //此处提示用户查询数据失败
                                    setToast("获取数据失败，请重新尝试！");
                                }


                            } else if (meterType.equals("Gas")) {
                                //气表抄表查询~~~~~~~~~~~~~
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
     * Params: [meteraNum]
     * Return: void
     * Date：2018-04-25 13:22:17
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
     * Params: []
     * Return: void
     * Date：2018-04-25 13:22:37
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
                                eleRecordsQueryList.setVisibility(View.VISIBLE);

                                HisEleOptionAdapter = new HisEleOptionAdapter(context, eleHisOptionList, eleRecordsQueryList);
                                eleRecordsQueryList.setAdapter(HisEleOptionAdapter);
                                HisEleOptionAdapter.notifyDataSetChanged();
                                eleRecordsQueryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
     * Describe：控制视图是否显示
     * Params:
     * Date：2018-03-30 13:31:39
     */
    private void popViewisShow(int id) {

        switch (id) {
            case 1:
                if (View.GONE == typeHideView.getVisibility()) {
                    //关闭第二个View
                    MethodUtil.animateClose(numberHideView);
                    //打开第一个View
                    MethodUtil.animateOpen(typeHideView, 0, 0);
                } else {
                    //关闭第一个View
                    MethodUtil.animateClose(typeHideView);
                }
                break;
            case 2:
                if (View.GONE == numberHideView.getVisibility()) {
                    queryNumEdit.clearFocus();
                    //关闭第一个View
                    MethodUtil.animateClose(typeHideView);
                    //打开第二个View
                    int getHeight = MethodUtil.dip2px(this, meterinfos.size() * 60);
                    MethodUtil.animateOpen(numberHideView, getHeight, 700);
                } else {
                    //关闭第二个View
                    MethodUtil.animateClose(numberHideView);
                }
                break;
            case 3:

                break;
        }
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
