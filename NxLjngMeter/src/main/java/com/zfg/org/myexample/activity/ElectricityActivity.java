package com.zfg.org.myexample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.zfg.org.myexample.adapter.ReadDataAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.entity.RequestInfo;
import com.zfg.org.myexample.model.ReadDataItemModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：ElectricityActivity
 * Describe：电表数据查询
 * Date：2018-03-30 13:33:20
 * Author: CapRobin@yeah.net
 *
 */
public class ElectricityActivity extends MyBaseActivity {
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
    private NoScrollGridView cxbhInnerView;
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


    private List<String> showListData1;
    private List<MeterInfoCheckModel> showListData2;
//    private List<MeterInfoCheckModel> showListData23;
    private static String dbCheckItemType[] = null;
    private List<MeterInfo> meterinfos;
    public List<MeterInfoCheckModel> tempList;
    private List<RequestInfo> mInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);
        mElectricityContent = this;
        showListData2 = new ArrayList<MeterInfoCheckModel>();
        dbCheckItemType = getResources().getStringArray(R.array.dbCheckItemType);
        //加载数据
        setData();

        pageType.setText("电表数据");
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        cbxmEdit.setOnClickListener(this);
        cxbhEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        startSearch.setOnClickListener(this);

        initCallBack();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
                Toast.makeText(ElectricityActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cbxmEdit:
                closeInputMethod();
                popViewisShow(1);
                break;
            case R.id.cxbhEdit:
                if (View.GONE != cxbhAllView.findViewById(R.id.cxbhHideView).getVisibility()) {
                    animateCollapsing(cxbhAllView.findViewById(R.id.cxbhHideView));
                }
                break;
            case R.id.searchNum:
                cxbhEdit.setText("");
                closeInputMethod();
                popViewisShow(2);
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

                //清楚刷新List数据
                //clearData();

                //开始加载数据
                loadData(CommonUtil.AddZeros(cxbhEdit.getText().toString()));
                break;
        }
    }
    protected void setViewData(){
        listadapter = new ReadDataAdapter(context,listdata);
//      列表适配器绑定
        getDataList_db.setAdapter(listadapter);
        getDataList_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (!scaning) {
//                    loading.show();
//                    connectDevice(data.get(position).getDevice());
//                }
            }
        });
        listadapter.notifyDataSetChanged();
    }










    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("抄表完成");
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {

                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {
                            //解析简单数组
                            JSONArray pages = jsonObject.getJSONArray("dataList");
                            for (int i = 0; i < pages.length(); i++) {
                                ReadDataItemModel dto = new ReadDataItemModel();
                                dto.of(pages.getJSONArray(i));
//                                listdata.add(dto);
                            }
//                            listadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context,"数据返回错误，请重新操作",Toast.LENGTH_SHORT).show();
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
     * Describe：构造View的数据
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData() {


        showListData1 = new ArrayList<String>();
//        showListData23 = new ArrayList<MeterInfoCheckModel>();

        //加载第一个列表数据
        for (int i = 0; i < dbCheckItemType.length; i++) {
            showListData1.add(dbCheckItemType[i].toString());
        }

        //加载第二个列表数据
        //从数据库获取数据并组装成List
        MeterInfoBo meterbo = new MeterInfoBo(context);
        meterinfos = meterbo.listAllData();
        for (int i=0;i<meterinfos.size();i++){
            if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                MeterInfoCheckModel model = new MeterInfoCheckModel(String.valueOf(i), meterinfos.get(i).getComm_address(), meterinfos.get(i).getMetertype(), false);
                showListData2.add(model);
            }
        }
//        for (int i = 0; i < data.size(); i++) {
//            showListData23.add(meterinfos.get(i).getMetertype().toString()+"："+data.get(i).value.toString());
//        }

        showView1(showListData1);
        showView2(showListData2);
        tempList = showListData2;

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
//                popViewisShow(2);
                cxbhEdit.setFocusable(true);
                cxbhEdit.requestFocus();
                cxbhEdit.performClick();
                switch (position){
                    case 0:
                        setRequestInfo(checkType,0,1);
                        break;
                    case 1:
                        setRequestInfo(checkType,1,0);
                        break;
                    case 2:
                        setRequestInfo(checkType,2,0);
                        break;
                    default:
                        Toast.makeText(context,"请重新选择",Toast.LENGTH_SHORT).show();
                        break;
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

                String  temStr = tempList.get(position).value;
                cxbhEdit.setText(temStr);
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
                if (View.GONE == cbxmAllView.findViewById(R.id.cbxmHideView).getVisibility()) {
                    animateExpanding(cbxmAllView.findViewById(R.id.cbxmHideView));
                } else {
                    animateCollapsing(cbxmAllView.findViewById(R.id.cbxmHideView));
                }
                break;
            case 2:
                if (View.GONE == cxbhAllView.findViewById(R.id.cxbhHideView).getVisibility()) {
                    animateExpanding(cxbhAllView.findViewById(R.id.cxbhHideView));
                } else {
                    animateCollapsing(cxbhAllView.findViewById(R.id.cxbhHideView));
                }
                break;
        }
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
     * Describe：打开视图
     * Params:
     * Date：2018-03-30 13:26:31
     */
    public static void animateExpanding(final View view) {
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

    public static void animateCollapsing(final View view) {
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
