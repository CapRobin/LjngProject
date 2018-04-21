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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.*;
import com.zfg.org.myexample.adapter.MyLocationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：GasMeterActivity
 * Describe：气表数据查询
 * Date：2018-03-28 10:57:43
 * Author: CapRobin@yeah.net
 *
 */
public class GasMeterActivity extends BasicActivity {
    private List<String> showListData1, showListData2 = null;
    private static String qbCheckItemType[] = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_meter);
        pageType.setText("气表数据");
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        cbxmEdit.setOnClickListener(this);
        cxbhEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        startSearch.setOnClickListener(this);
        qbCheckItemType = getResources().getStringArray(R.array.qbCheckItemType);
        //加载数据
        setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
                Toast.makeText(context, "设置", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "提交数据", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Describe：构造View的数据
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData() {
        // 创建第一个GridView数据视图
        showListData1 = new ArrayList<String>();
        // 创建第二个GridView数据视图
        showListData2 = new ArrayList<String>();

        // 构造数据
        for (int i = 0; i < qbCheckItemType.length; i++) {
            showListData1.add(qbCheckItemType[i]);
        }
        for (int i = 0; i < 20; i++) {
            showListData2.add("Item_02_" + (i + 1));
        }

        showView1(showListData1, 1);
        showView1(showListData2, 2);

    }

    /**
     * Describe：装载数据
     * Params:
     * Date：2018-03-30 12:00:40
     */
    public void showView1(List<String> list, int viewId) {

        com.zfg.org.myexample.adapter.MyLocationAdapter locationAdapter = new MyLocationAdapter(this, list);
        switch (viewId) {
            case 1:
                //设置ListView线条的颜色
                cbxmHideInnerView.setDivider(new ColorDrawable(Color.GRAY));
                cbxmHideInnerView.setDividerHeight(1);

                cbxmHideInnerView.setAdapter(locationAdapter);
                cbxmHideInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String string = showListData1.get(i);
                        cbxmEdit.setText(string);
                        popViewisShow(1);
                        popViewisShow(2);
                        cxbhEdit.setFocusable(true);
                        cxbhEdit.requestFocus();
                        cxbhEdit.performClick();
                    }
                });
                break;
            case 2:
                //设置ListView线条的颜色
                cxbhInnerView.setDivider(new ColorDrawable(Color.GRAY));
                cxbhInnerView.setDividerHeight(1);
                cxbhInnerView.setAdapter(locationAdapter);
                cxbhInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String string = showListData2.get(i);
                        cxbhEdit.setText(string);
                        popViewisShow(2);
                    }
                });
                break;
            case 3:

                break;
        }
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
    protected void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();

        // isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(GasMeterActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
