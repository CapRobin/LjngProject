package com.zfg.org.myexample;

import android.os.Bundle;

import com.zfg.org.myexample.activity.BasicActivity;

import butterknife.ButterKnife;

public class TestActivity2 extends BasicActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_demo_02);
        ButterKnife.bind(this);
    }

}
