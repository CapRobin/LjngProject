package com.zfg.org.myexample.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.MeterAllInfoAdapter;
import com.zfg.org.myexample.adapter.MyLocationAdapter;
import com.zfg.org.myexample.adapter.NoScrollGridView;
import com.zfg.org.myexample.adapter.RcAdapterWholeChange;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.model.MeterAllInfo;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.MethodUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：MeterReadingActivity
 * Describe：实时抄表数据查询
 * Date：2018-03-30 13:33:20
 * Author: CapRobin@yeah.net
 */
public class MeterReadingActivity extends MyBaseActivity {
    private Preference preference;
    @ViewInject(id = R.id.cbxmAllView)
    private LinearLayout cbxmAllView;
    @ViewInject(id = R.id.cxbhAllView)
    private LinearLayout cxbhAllView;
    @ViewInject(id = R.id.cbxmEdit)
    private EditText cbxmEdit;
    @ViewInject(id = R.id.cxbhEdit)
    private EditText cxbhEdit;
    @ViewInject(id = R.id.cbxmHideInnerView)
    private NoScrollGridView cbxmHideInnerView;
    @ViewInject(id = R.id.cxbhInnerView)
    private ListView cxbhInnerView;
    @ViewInject(id = R.id.meterInfoList)
    private RecyclerView meterInfoList;
    @ViewInject(id = R.id.startSearch)
    private Button startSearch;
    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageType;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.searchNum)
    private ImageView searchNum;
    @ViewInject(id = R.id.recordsQueryList_ele)
    private ListView getDataList_db;
    @ViewInject(id = R.id.settingView)
    private LinearLayout settingView;
    @ViewInject(id = R.id.cxbhHideView)
    private LinearLayout cxbhHideView;
    @ViewInject(id = R.id.cbxmHideView)
    private LinearLayout cbxmHideView;

    private List<String> showListData1;
    private List<MeterAllInfo> getListData;
    private List<MeterAllInfo> newList;
    private static String dbCheckItemType[] = null;
    private List<MeterInfo> meterinfos;
    private int userType = 0;
    private MyLocationAdapter locationAdapter;


    private RcAdapterWholeChange recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Window window = this.getWindow();
//        //取消状态栏透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        window.setStatusBarColor(getResources().getColor(R.color.them_color));
//        //设置系统状态栏处于可见状态
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        //让view不根据系统窗口来调整自己的布局
//        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

        setContentView(R.layout.activity_meter_reading);
        mElectricityContent = this;
        preference = Preference.instance(this);
        userType = preference.getInt(Preference.USERTYPE);
        showListData1 = new ArrayList<String>();
        getListData = new ArrayList<MeterAllInfo>();
        newList = new ArrayList<MeterAllInfo>();

        //设置ListView线条的颜色
        getDataList_db.setDivider(new ColorDrawable(Color.GRAY));
        getDataList_db.setDividerHeight(1);
        settingView.setVisibility(View.VISIBLE);
        getDataList_db.setVisibility(View.GONE);
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        cbxmEdit.setOnClickListener(this);
        cxbhEdit.setOnClickListener(this);
        cxbhEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    MethodUtil.animateClose(cxbhHideView);
                    MethodUtil.animateClose(cbxmHideView);
                }
            }
        });
        searchNum.setOnClickListener(this);
        startSearch.setOnClickListener(this);
        //RecyclerView相关设置
        meterInfoList.setLayoutManager(new LinearLayoutManager(this));
        //线条设置
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_bg));
        meterInfoList.addItemDecoration(divider);

        //初始化加载数据
        setData(userType);
        //刷新RecyclerView数据
        refreshUI();
        //设置输入监听
        setListener();
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
                        setToast("请设置相关查询条件，进行抄表！");
                    }
                }
                break;
            case R.id.cbxmEdit:
                meterInfoList.setVisibility(View.GONE);
                closeInputMethod();
                popViewisShow(1);
                break;
            case R.id.cxbhEdit:
//                if (View.GONE != cxbhHideView.getVisibility()) {
//                    MethodUtil.animateClose(cxbhHideView);
//                }
                break;
            case R.id.searchNum:
                if (!cbxmEdit.getText().equals("")) {
                    meterInfoList.setVisibility(View.GONE);
                    cxbhEdit.setText("");
                    closeInputMethod();
                    popViewisShow(2);
                } else {
                    setToast("请先选择抄表项目！");
                }
                break;
            case R.id.startSearch:
                if (CheckUtil.isNull(cbxmEdit.getText())) {
                    Toast.makeText(context, "请选择查表项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckUtil.isNull(cxbhEdit.getText())) {
                    Toast.makeText(context, "请输入查询表号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cxbhEdit.getText().toString().trim().length() < 12) {
                    setToast("表地址输入有误请重新输入！");
                    return;
                }
                //清除刷新List数据
                //clearData();
                //开始加载数据
                loadData(CommonUtil.AddZeros(cxbhEdit.getText().toString()));
                break;
        }
    }

    /**
     * Describe：络返回数据后回调
     * Params: []
     * Return: void
     * Date：2018-04-25 10:13:11
     */
    protected void setViewData() {
//        listadapter.notifyDataSetChanged();
        if (listdata != null || listdata.size() > 0) {
            settingView.setVisibility(View.GONE);
//            animateClose(settingView);
            getDataList_db.setVisibility(View.VISIBLE);

            getDataList_db.setAdapter(listadapter);
            listadapter.notifyDataSetChanged();
            getDataList_db.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setToast("没有其他数据啦");
//                }
                }
            });
        } else {
            //此处提示用户查询数据失败
            setToast("获取数据失败，请重新尝试！");
        }
    }

    /**
     * Describe：构造View的数据
     * Params: [userType]
     * Return: void
     * Date：2018-04-25 10:13:40
     */
    private void setData(int userType) {
        switch (userType) {
            case 1:
                pageType.setText("水表实时数据");
                dbCheckItemType = getResources().getStringArray(R.array.sbCheckItemType);
                break;
            case 2:
                pageType.setText("电表实时数据");
                dbCheckItemType = getResources().getStringArray(R.array.dbCheckItemType);
                break;
            case 3:
                pageType.setText("气表实时数据");
                dbCheckItemType = getResources().getStringArray(R.array.qbCheckItemType);
                break;
            case 4:
                pageType.setText("热表实时数据");
                dbCheckItemType = getResources().getStringArray(R.array.rbCheckItemType);
                break;
            default:
                Toast.makeText(context, "请重新选择", Toast.LENGTH_SHORT).show();
                break;
        }

        //加载第一个列表数据
        for (int i = 0; i < dbCheckItemType.length; i++) {
            showListData1.add(dbCheckItemType[i].toString());
        }
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
        showView1(showListData1);
        showView2(getListData);
    }

    /**
     * Describe：装载数据1
     * Params: [list]
     * Return: void
     * Date：2018-04-25 10:14:04
     */
    public void showView1(List<String> list) {
        locationAdapter = new MyLocationAdapter(this, list);
        //设置ListView线条的颜色
        cbxmHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cbxmHideInnerView.setDividerHeight(1);
        cbxmHideInnerView.setAdapter(locationAdapter);
        cbxmHideInnerView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    setToast("价格信息暂无法查询");
                }
                if (position == 2) {
                    setToast("实时状态暂无法查询");
                }
                String checkType = showListData1.get(0);
                cbxmEdit.setText(checkType);
                popViewisShow(1);
                setType(0);
            }
        });
    }

    /**
     * Describe：装载数据1
     * Params: [list]
     * Return: void
     * Date：2018-04-25 10:14:36
     */
    public void showView2(List<MeterAllInfo> list) {
        MeterAllInfoAdapter locationAdapter = new MeterAllInfoAdapter(this, list);
        //设置ListView线条的颜色
        cxbhInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cxbhInnerView.setDividerHeight(1);
        cxbhInnerView.setAdapter(locationAdapter);
        cxbhInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temStr = getListData.get(i).getCOMM_ADDRESS();
                cxbhEdit.setText(temStr);
                popViewisShow(2);
                meterInfoList.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Describe：刷新RecyclerView列表
     * Params:
     * Date：2018-04-25 09:57:58
     */
    private void refreshUI() {
        if (recycleAdapter == null) {
            recycleAdapter = new RcAdapterWholeChange(MeterReadingActivity.this, newList);
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
        cxbhEdit.addTextChangedListener(new TextWatcher() {
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
//                Toast.makeText(MeterReadingActivity.this, "您点击了：" + pos, Toast.LENGTH_SHORT).show();
                cxbhEdit.setText(newList.get(pos).getCOMM_ADDRESS());
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
     * Describe：设置仪表请求标签
     * Params: [position]
     * Return: void
     * Date：2018-04-25 10:15:49
     */
    private void setType(int position) {
        if (userType == 1 && position == 0) {
            dataType = 5;
            varType = 201;
        } else if (userType == 1 && position == 1) {
            dataType = 5;
            varType = 202;
        } else if (userType == 1 && position == 2) {
            dataType = 5;
            varType = 203;
        } else if (userType == 2 && position == 0) {
            dataType = 0;
            varType = 1;
        } else if (userType == 2 && position == 1) {
            dataType = 1;
            varType = 0;
        } else if (userType == 2 && position == 2) {
            dataType = 2;
            varType = 0;
        } else if (userType == 3 && position == 0) {
            dataType = 5;
            varType = 201;
        } else if (userType == 3 && position == 1) {
            dataType = 5;
            varType = 202;
        } else if (userType == 3 && position == 2) {
            dataType = 5;
            varType = 203;
        } else if (userType == 4 && position == 0) {
            dataType = 0;
            varType = 0;
        } else if (userType == 4 && position == 1) {
            dataType = 0;
            varType = 0;
        } else if (userType == 4 && position == 2) {
            dataType = 0;
            varType = 0;
        }
    }

    /**
     * Describe：控制视图是否显示
     * Params: [id]
     * Return: void
     * Date：2018-04-25 10:16:10
     */
    private void popViewisShow(int id) {
        switch (id) {
            case 1:
                if (View.GONE == cbxmHideView.getVisibility()) {
                    //关闭第二个View
                    MethodUtil.animateClose(cxbhHideView);
                    //打开第一个View
                    MethodUtil.animateOpen(cbxmHideView, 0, 0);
                } else {
                    //关闭第一个View
                    MethodUtil.animateClose(cbxmHideView);
                }
                break;
            case 2:
                if (View.GONE == cxbhHideView.getVisibility()) {
                    //关闭第一个View
                    MethodUtil.animateClose(cbxmHideView);
                    //打开第二个View

//                    locationAdapter.notifyDataSetChanged();
                    int getHeight = MethodUtil.dip2px(this, getListData.size() * 100);
                    MethodUtil.animateOpen(cxbhHideView, getHeight, 900);
                    cxbhEdit.clearFocus();
                } else {
                    //关闭第二个View
                    MethodUtil.animateClose(cxbhHideView);
                }
                break;
            case 3:
                if (View.GONE == settingView.getVisibility()) {
                    MethodUtil.animateClose(getDataList_db);
                    //打开View
                    MethodUtil.animateOpen(settingView, 0, 0);
                } else {
                    if (getDataList_db.getCount() > 0) {
                        MethodUtil.animateClose(settingView);
                        setViewData();
                        MethodUtil.animateOpen(getDataList_db, 0, 0);
                    } else {
                        setToast("请设置相关查询条件，进行抄表！");
                    }
                }
                break;
        }
    }
}