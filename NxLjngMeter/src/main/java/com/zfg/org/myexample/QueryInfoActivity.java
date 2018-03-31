package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;

import com.zfg.org.myexample.activity.BasicActivity;

/**
 * Created by Administrator on 2016-06-02.
 * 树状列表 根据当前用户信息处理
 */
public class QueryInfoActivity  extends BasicActivity implements View.OnClickListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      界面资源
        setContentView(R.layout.activity_querymeterinfo);
    }


    @Override
    public void onClick(View v) {

    }
}
