package com.zfg.org.myexample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.MeterAllInfoAdapter;
import com.zfg.org.myexample.adapter.RcAdapterWholeChange;
import com.zfg.org.myexample.adapter.RechargeAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.RechargeItemModel;
import com.zfg.org.myexample.model.MeterAllInfo;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.MethodUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：RechargeActivity
 * Describe：充值输入金额界面
 * Date：2018-04-25 14:21:00
 * Author: CapRobin@yeah.net
 */
public class RechargeActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.recylerview)
    private RecyclerView recyclerView;
    //充值金额
    private RechargeAdapter adapter;

    @ViewInject(id = R.id.tvPay)
    private TextView tvPay;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.meter_addrs)
    private LinearLayout meterAddrs;

    @ViewInject(id = R.id.recharge_money)
    private EditText rechargemoney;

    @ViewInject(id = R.id.select_addr)
    private ImageView select_addr;

    private CallBack callBack;

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.selectSumLayout)
    private LinearLayout selectSumLayout;

    private Preference preference;
    private DialogLoading loading;
    private CallBack loginCallback;

    private RechargeActivity activity;
    private MeterInfoDialog meterInfoDialog;


    private double money;
    @ViewInject(id = R.id.cxbhHideView)
    private LinearLayout cxbhHideView;
    @ViewInject(id = R.id.cxbhInnerView)
    private ListView cxbhInnerView;
    @ViewInject(id = R.id.meterInfoList)
    private RecyclerView meterInfoList;
    private List<MeterInfo> meterinfos;
    private List<MeterAllInfo> getListData;
    private List<MeterAllInfo> newList;
    private RcAdapterWholeChange recycleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        activity = (RechargeActivity) context;
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);

        pageTitle.setText("充值缴费");
        settingBtn.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        getListData = new ArrayList<MeterAllInfo>();
        newList = new ArrayList<MeterAllInfo>();
        recyclerView.setAdapter(adapter = new RechargeAdapter());
        adapter.replaceAll(getData());
        adapter.setOnRecyclerItemClicklistener(new RechargeAdapter.OnRecyclerItemClicklistener() {
            @Override
            public void onItemClick(View view, String data) {
//                Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                rechargemoney.setText(data);
            }
        });
        initActivity();
        setData();
        //刷新RecyclerView数据
        refreshUI();
        //设置表号输入监听
        setListener();

//        replaceFragment("readdata", ReadDataWaterFragment.getInstance(), false);
    }

    /**
     * Describe：刷新RecyclerView列表
     * Params: []
     * Return: void
     * Date：2018-04-25 13:15:37
     */
    private void refreshUI() {
        if (recycleAdapter == null) {
            recycleAdapter = new RcAdapterWholeChange(RechargeActivity.this, newList);
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
                selectSumLayout.setVisibility(View.VISIBLE);
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
                selectSumLayout.setVisibility(View.GONE);
            } else {
                selectSumLayout.setVisibility(View.VISIBLE);
                meterInfoList.setVisibility(View.GONE);

            }
        }
    }

    /**
     * Describe：显示充值金额
     * Params: []
     * Return: java.util.ArrayList<com.zfg.org.myexample.dto.RechargeItemModel>
     * Date：2018-04-25 14:24:35
     */
    public ArrayList<RechargeItemModel> getData() {
        ArrayList<RechargeItemModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            String count = i * 10 + ".00";
            list.add(new RechargeItemModel(RechargeItemModel.ONE, count));
        }
//        list.add(new RechargeItemModel(RechargeItemModel.TWO, null));
        return list;
    }

    /**
     * Describe：初始化相关数据
     * Params: []
     * Return: void
     * Date：2018-04-25 14:24:59
     */
    private void initActivity() {
        backHome.setOnClickListener(this);
        tvPay.setOnClickListener(this);
//        meterAddrs.setOnClickListener(this);
        meterAddr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    MethodUtil.animateClose(cxbhHideView);
                }
            }
        });
        rechargemoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    MethodUtil.animateClose(cxbhHideView);
                    meterInfoList.setVisibility(View.GONE);
                    selectSumLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        select_addr.setOnClickListener(this);

        //RecyclerView相关设置
        meterInfoList.setLayoutManager(new LinearLayoutManager(this));
        //线条设置
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_bg));
        meterInfoList.addItemDecoration(divider);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.select_addr:
                meterInfoList.setVisibility(View.GONE);
                if (meterinfos.size() > 0) {
                    closeInputMethod();
                    popViewisShow();
                } else {
                    setToast("获取表号失败");
                }

                break;
            case R.id.tvPay:
                if (meterAddr.getText().length() == 0) {
                    Toast.makeText(this, "请输入表地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (meterAddr.getText().toString().trim().length() < 12) {
                    setToast("表地址输入有误请重新输入！");
                    return;
                }
                if (rechargemoney.getText().toString().length() == 0) {
                    Toast.makeText(this, "请输入选择或输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("meteraddr",
                        CommonUtil.AddZeros(meterAddr.getText().toString()));
                bundle.putDouble("money", Double.valueOf(rechargemoney.getText().toString()));//石涛改成1分钱
//                bundle.putDouble("money", Double.valueOf("0.01"));
                startActivity(bundle, MyPayActivity.class);
                break;
        }
    }

    /**
     * Describe：控制视图是否显示
     * Params: []
     * Return: void
     * Date：2018-04-25 14:22:23
     */
    private void popViewisShow() {
        if (View.GONE == cxbhHideView.getVisibility()) {
            meterAddr.clearFocus();
            rechargemoney.clearFocus();
            int getHeight = MethodUtil.dip2px(this, meterinfos.size() * 60);
            MethodUtil.animateOpen(cxbhHideView, getHeight, 900);
            selectSumLayout.setVisibility(View.GONE);
        } else {
            selectSumLayout.setVisibility(View.VISIBLE);
            MethodUtil.animateClose(cxbhHideView);
        }
    }

    /**
     * Describe：构造View的数据
     * Params: []
     * Return: void
     * Date：2018-04-25 14:22:47
     */
    private void setData() {
        //统一(登录默认更新数据)加载第二个列表数据(从数据库获取数据并组装成List)
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
        showView(getListData);
    }

    /**
     * Describe：装载数据
     * Params: [list]
     * Return: void
     * Date：2018-04-25 14:23:27
     */
    public void showView(List<MeterAllInfo> list) {
        MeterAllInfoAdapter locationAdapter = new MeterAllInfoAdapter(this, list);
        //设置ListView线条的颜色
        cxbhInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cxbhInnerView.setDividerHeight(1);
        cxbhInnerView.setAdapter(locationAdapter);
        cxbhInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temStr = getListData.get(i).getCOMM_ADDRESS();
                meterAddr.setText(temStr);
                popViewisShow();
                meterInfoList.setVisibility(View.GONE);
            }
        });
    }

    public interface CallBack {
        void callBack(int model);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
