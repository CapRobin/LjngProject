package com.zfg.org.myexample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.zfg.org.myexample.adapter.MyLocationAdapter;
import com.zfg.org.myexample.adapter.MyLocationAdapter2;
import com.zfg.org.myexample.adapter.NoScrollGridView;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.entity.RequestInfo;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：ElectricityActivity
 * Describe：电表数据查询
 * Date：2018-03-30 13:33:20
 * Author: CapRobin@yeah.net
 */
public class ElectricityActivity extends MyBaseActivity {
    private Preference preference;
    @ViewInject(id = R.id.cbxmAllView)
    private LinearLayout cbxmAllView;
    @ViewInject(id = R.id.cxbhAllView)
    private LinearLayout cxbhAllView;
    //    @ViewInject(id = R.id.mAllView)
//    private LinearLayout mAllView;
    @ViewInject(id = R.id.cbxmEdit)
    private EditText cbxmEdit;
    @ViewInject(id = R.id.cxbhEdit)
    private EditText cxbhEdit;
    @ViewInject(id = R.id.cbxmHideInnerView)
    private NoScrollGridView cbxmHideInnerView;
    @ViewInject(id = R.id.cxbhInnerView)
    private ListView cxbhInnerView;
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
    @ViewInject(id = R.id.getDataList_db)
    private ListView getDataList_db;
    @ViewInject(id = R.id.settingView)
    private LinearLayout settingView;

    private List<String> showListData1;
    private List<MeterInfoCheckModel> showListData2;
    private static String dbCheckItemType[] = null;
    private List<MeterInfo> meterinfos;
    private int userType = 0;

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



        setContentView(R.layout.activity_electricity);
        mElectricityContent = this;
        preference = Preference.instance(this);
        userType = preference.getInt(Preference.USERTYPE);
        showListData1 = new ArrayList<String>();
        showListData2 = new ArrayList<MeterInfoCheckModel>();
        //设置ListView线条的颜色
        getDataList_db.setDivider(new ColorDrawable(Color.GRAY));
        getDataList_db.setDividerHeight(1);
        settingView.setVisibility(View.VISIBLE);
        getDataList_db.setVisibility(View.GONE);
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        cbxmEdit.setOnClickListener(this);
        cxbhEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        startSearch.setOnClickListener(this);

        //初始化加载数据
        setData(userType);
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
                closeInputMethod();
                popViewisShow(1);
                break;
            case R.id.cxbhEdit:
                if (View.GONE != cxbhAllView.findViewById(R.id.cxbhHideView).getVisibility()) {
                    animateClose(cxbhAllView.findViewById(R.id.cxbhHideView));
                }
                break;
            case R.id.searchNum:
                if (!cbxmEdit.getText().equals("")) {
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
                //清除刷新List数据
                //clearData();

                //获取本地测试数据(Json文件)使用
                String filename = "electricity.txt";
                String resultJson = tempJson(filename);

                //开始加载数据
                loadData(CommonUtil.AddZeros(cxbhEdit.getText().toString()), resultJson,true);
                break;
        }
    }

    /**
     * Describe：网络返回数据后回调
     * Params:
     * Date：2018-04-01 20:29:17
     */
    protected void setViewData() {
//        listadapter.notifyDataSetChanged();
        if (listdata != null || listdata.size() > 0) {
            settingView.setVisibility(View.GONE);
//            animateClose(settingView);
            getDataList_db.setVisibility(View.VISIBLE);

            getDataList_db.setAdapter(listadapter);
            listadapter.notifyDataSetChanged();
            getDataList_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData(int userType) {
        setToast("登录用户为：" + userType);
        switch (userType) {
            case 1:
                pageType.setText("水表数据");
                dbCheckItemType = getResources().getStringArray(R.array.sbCheckItemType);
                break;
            case 2:
                pageType.setText("电表表数据");
                dbCheckItemType = getResources().getStringArray(R.array.dbCheckItemType);
                break;
            case 3:
                pageType.setText("气表数据");
                dbCheckItemType = getResources().getStringArray(R.array.qbCheckItemType);
                break;
            case 4:
                pageType.setText("热表数据");
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
        MyLocationAdapter locationAdapter = new MyLocationAdapter(this, list);
        //设置ListView线条的颜色
        cbxmHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cbxmHideInnerView.setDividerHeight(1);
        cbxmHideInnerView.setAdapter(locationAdapter);
        cbxmHideInnerView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkType = showListData1.get(position);
                cbxmEdit.setText(checkType);
                popViewisShow(1);
                if (TextUtils.isEmpty(cxbhEdit.getText())) {
                    popViewisShow(2);
                    //模拟点击事件
//                    searchNum.performClick();
                } else {
//                    cxbhEdit.setFocusable(true);
                    cxbhEdit.requestFocus();
                    //设置数据请求类型
                    setType(position);
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
        cxbhInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cxbhInnerView.setDividerHeight(1);
        cxbhInnerView.setAdapter(locationAdapter);
        cxbhInnerView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temStr = showListData2.get(position).value;
                cxbhEdit.setText(temStr);
                popViewisShow(2);
            }
        });
    }

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
        setToast("dataType==" + dataType + "~~~~varType==" + varType);
    }

    /**
     * Describe：控制视图是否显示
     * Params:
     * Date：2018-03-30 13:31:39
     */
    private void popViewisShow(int id) {

        switch (id) {
            case 1:
                if (View.GONE == cbxmAllView.findViewById(R.id.cbxmHideView).getVisibility()) {
                    //关闭第二个View
                    animateClose(cxbhAllView.findViewById(R.id.cxbhHideView));
                    //打开第一个View
                    animateOpen(cbxmAllView.findViewById(R.id.cbxmHideView));
                } else {
                    //关闭第一个View
                    animateClose(cbxmAllView.findViewById(R.id.cbxmHideView));
                }
                break;
            case 2:
                if (View.GONE == cxbhAllView.findViewById(R.id.cxbhHideView).getVisibility()) {
                    //关闭第一个View
                    animateClose(cbxmAllView.findViewById(R.id.cbxmHideView));
                    //打开第二个View
                    animateOpen(cxbhAllView.findViewById(R.id.cxbhHideView));
                } else {
                    //关闭第二个View
                    animateClose(cxbhAllView.findViewById(R.id.cxbhHideView));
                }
                break;
            case 3:
                if (View.GONE == settingView.getVisibility()) {
                    animateClose(getDataList_db);
                    //打开View
                    animateOpen(settingView);
                } else {
                    if (getDataList_db.getCount() > 0) {
                        animateClose(settingView);
                        setViewData();
                        animateOpen(getDataList_db);
                    } else {
                        setToast("请设置相关查询条件，进行抄表！");
                    }
                }


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

    /**
     * @Describe：关闭输入法
     * @Throws:
     * @Date：2014年8月20日 上午11:58:30
     * @Version v1.0
     */
    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        // isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(ElectricityActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
