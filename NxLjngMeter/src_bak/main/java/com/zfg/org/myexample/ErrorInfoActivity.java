package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;

import com.zfg.org.myexample.activity.BasicActivity;

/**
 * Created by Administrator on 2016-06-02.
 */
public class ErrorInfoActivity extends BasicActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      界面资源
        setContentView(R.layout.activity_errorinfo);
    }

    @Override
    public void onClick(View v) {

    }
}
