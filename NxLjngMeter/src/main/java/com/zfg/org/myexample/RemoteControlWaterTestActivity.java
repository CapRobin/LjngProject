package com.zfg.org.myexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.fragment.ReadDataWaterFragment;
import com.zfg.org.myexample.fragment.ReadDataWaterTestFragment;
import com.zfg.org.myexample.utils.Preference;


/**
 * @author zfg
 */
public class RemoteControlWaterTestActivity extends BasicActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @ViewInject(id = R.id.tab_bottom)
    private RadioGroup tabBottom;

    private BaseFragment curentFragment;

    private Preference preference;

    private DialogLoading loading;

    private RemoteControlWaterTestActivity activity;

    final int WHAT_SCROLL = 0, WHAT_BTN_VISABEL = WHAT_SCROLL + 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //zhdl 绑定样式
        setContentView(R.layout.activity_remotecontroltest);
        //上下文， 扔到变量里面
        activity = (RemoteControlWaterTestActivity) context;
//        initCallBack();
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        initActivity();
        replaceFragment("zhdl", ReadDataWaterTestFragment.getInstance(), false);
    }

    private void initActivity() {
        tabBottom.setOnCheckedChangeListener(this);
        tabBottom.check(R.id.zhdl);
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 同步服务端数据
     */
    class MyThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新

        }
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    //zhdl  进入该页面后， check（0）触发tab事件， 下面是点击不同的tab标签的事件
    @Override
    public void onCheckedChanged(RadioGroup group, int id) {
        String tag;
        boolean isAdd = true;
        BaseFragment tempFragment;
        switch (id) {
            //抄表
            case R.id.zhdl:
                tag = "zhdl";
                //开启事务，fragment的控制是由事务来实现的
                tempFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(tag);
                if (tempFragment == null) {
                    tempFragment = ReadDataWaterTestFragment.getInstance();
                    isAdd = false;
                }
                replaceFragment(tag, tempFragment, isAdd);
                break;
        }
    }

    public void replaceFragment(String tag, BaseFragment tempFragment,
                                boolean isAdd) {
        curentFragment = tempFragment;
        FragmentTransaction tran = getSupportFragmentManager()
                .beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

    public void show() {
        loading.show();
    }

    public void hide() {
        loading.dismiss();
    }

    public void onBackPressed() {
        if (!curentFragment.onBackKeyPressed()) {
            finish();
        }
    }

    public void checkRadio(int id) {
        tabBottom.check(id);
    }

    public Preference getPreference() {
        return preference;
    }

    public void onClick(View v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
