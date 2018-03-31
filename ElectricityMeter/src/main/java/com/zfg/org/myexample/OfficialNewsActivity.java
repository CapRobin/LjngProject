package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;

import com.zfg.org.myexample.activity.BasicActivity;

/**
 * Created by Administrator on 2016-11-15.
 */

public class OfficialNewsActivity extends BasicActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officialnews);
    }


    @Override
    public void onClick(View v) {

    }
}
