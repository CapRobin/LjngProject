package com.zfg.org.myexample.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：ElectricityActivity
 * Describe：电表数据查询
 * Date：2018-03-27 16:56:49
 * Author: CapRobin@yeah.net
 */
public class ElectricityActivity1 extends BasicActivity implements View.OnTouchListener {
    @ViewInject(id = R.id.submit)
    private Button submit;
    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.startSearch)
    private Button testBtn;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView setBtn;
    @ViewInject(id = R.id.settingView)
    private LinearLayout mHolder2;
    private LinearLayout hiddenview2;
//    private LinearLayout searchNumView;
//    private LinearLayout checkItemView;
    @ViewInject(id = R.id.cbxm)
    private LinearLayout cbxm;
    @ViewInject(id = R.id.cbbh)
    private LinearLayout cbbh;
    @ViewInject(id = R.id.checkItemEdit)
    private EditText checkItemEdit;
    @ViewInject(id = R.id.searchNum)
    private ImageView searchNum;

//    private LinearLayout.LayoutParams lp;
//    private int width;
//    private int width1;
//    private int width2;


//    private View mHolder1, mHolder2, mHolder3;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammeter);
        pageTitle.setText("电表数据");
        mHolder2 = (LinearLayout) findViewById(R.id.settingView);
        hiddenview2 = (LinearLayout) findViewById(R.id.hiddenview2);
//        searchNumView = (LinearLayout) findViewById(R.id.searchNumView);
//        checkItemView = (LinearLayout) findViewById(R.id.checkItemView);
//        //TODO：此处加载完数据后获取显示View高度后再隐藏
//        searchNumView.setVisibility(View.GONE);
//        checkItemView.setVisibility(View.GONE);

        submit.setOnClickListener(this);
        backHome.setOnClickListener(this);
        setBtn.setOnClickListener(this);
        checkItemEdit.setOnClickListener(this);
        searchNum.setOnClickListener(this);
        testBtn.setOnClickListener(this);

//        lp=(LinearLayout.LayoutParams)mHolder2.getLayoutParams();
//        width = mHolder2.getWidth();

//        checkItemEdit.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:

//                WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//                int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
//                int height = wm.getDefaultDisplay().getHeight();//屏幕高度
                isShowView(mHolder2);
                break;
            case R.id.submit:
                isShowView(mHolder2);
                break;
            case R.id.checkItemEdit:
//                width = cbxm.getWidth()+width;
//                lp.height=width;
//                mHolder2.setLayoutParams(lp);
                closeInputMethod();
                isShowView(cbxm);
                break;
            case R.id.searchNum:
//                width = cbbh.getWidth()+width;
//                lp.height=width;
//                mHolder2.setLayoutParams(lp);
                closeInputMethod();
                isShowView(cbbh);
                break;
            case R.id.startSearch:
                Toast.makeText(context,"测试按钮",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void isShowView(View view){
        switch (view.getId()){
            case R.id.settingView:
                if (View.GONE == hiddenview2.getVisibility()) {
                    animateExpanding(mHolder2.findViewById(R.id.hiddenview2));
                } else {
                    animateCollapsing(mHolder2.findViewById(R.id.hiddenview2));
                }


//                if (View.GONE == mHolder2.getVisibility()) {
//                    mHolder2.setVisibility(View.VISIBLE);
//                } else {
//                    mHolder2.setVisibility(View.GONE);
//                }
                break;
            case R.id.cbxm:
                if (View.GONE == cbxm.findViewById(R.id.checkItemView).getVisibility()) {
                    animateExpanding(cbxm.findViewById(R.id.checkItemView));
                } else {
                    animateCollapsing(cbxm.findViewById(R.id.checkItemView));
                }
                break;
            case R.id.cbbh:
                if (View.GONE == cbbh.findViewById(R.id.searchNumView).getVisibility()) {
                    animateExpanding(cbbh.findViewById(R.id.searchNumView));
                } else {
                    animateCollapsing(cbbh.findViewById(R.id.searchNumView));
                }
                break;
            default:
                break;
        }
    }

    public static void animateExpanding(final View view) {
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
        animator.start();
    }


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
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

//        if (motionEvent.getAction() == KeyEvent.ACTION_DOWN){
//
//        };
                closeInputMethod();
                if (View.GONE == mHolder2.findViewById(R.id.hiddenview2).getVisibility()) {
                    animateExpanding(mHolder2.findViewById(R.id.hiddenview2));
                } else {
                    animateCollapsing(mHolder2.findViewById(R.id.hiddenview2));
                }
        return false;
    }
}
