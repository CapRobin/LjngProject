package com.zfg.org.myexample.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：ParamSettingActivity
 * Describe：参数配置
 * Date：2018-04-14 17:24:39
 * Author: CapRobin@yeah.net
 *
 */
public class ParamSettingActivity extends BasicActivity {

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_setting);
        initActivityView();
    }

    /**
     * Describe：初始化ActivityView
     * Params:
     * Date：2018-04-16 16:11:40
     */
    private void initActivityView(){
        pageTitle.setText("参数设置");
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
                setToast("参数设置");
                break;
            default:
                break;
        }
    }
}
