package com.zfg.org.myexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.LoginActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RechargeActivity;
import com.zfg.org.myexample.SettingActivity;
import com.zfg.org.myexample.SwichPowerActivity;
//import com.zfg.org.myexample.UploadExceptionActivity;
import com.zfg.org.myexample.UploadExceptionActivity;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.Preference;

public class MainActivity extends BasicActivity implements OnClickListener {
    public static int controlFlag;// 蓝牙抄表 和 蓝牙参数设置的标志

    //缓存
    private Preference preference;
    private LinearLayout.LayoutParams mParams;
    private View mView;
    private View showView;


    @ViewInject(id = R.id.mainLayout)
    private LinearLayout mainLayout;
    private ImageView logoTitle;
    //    @ViewInject(id = R.id.logoTitle)
//    private ImageView logoTitle;
//    @ViewInject(id = R.id.name)
    private TextView nameText;
    //    @ViewInject(id = R.id.evaluate_self)
    private LinearLayout evaluateSelf;
    //    @ViewInject(id = R.id.his_info)
    private LinearLayout hisinfo;
    //    @ViewInject(id = R.id.onoff_info)
    private LinearLayout onoffinfo;
    //    @ViewInject(id = R.id.task_query)
    private LinearLayout task_query;
    //    @ViewInject(id = R.id.upload_exception)
    private LinearLayout upload_exception;
    //    @ViewInject(id = R.id.set_manager)
    private LinearLayout setManager;
    //    @ViewInject(id = R.id.recharge_pay)
    private LinearLayout rechargepay;
    public static MainActivity instance = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);
        instance = this;
        initActivity();
//        checkUpdate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);

        initActivity();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    /**
     * Describe：设置主界面的模块
     * Params:
     * Date：2018-04-02 13:01:23
     */
    private View showModule(int userType) {
        //设置View的宽和高的属性
        mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(this);

        switch (userType) {
            case 1:
                mView = inflater.inflate(R.layout.main_view_sb, null);
                break;
            case 2:
                mView = inflater.inflate(R.layout.main_view_db, null);
                break;
            case 3:
                mView = inflater.inflate(R.layout.main_view_qb, null);
                break;
            case 4:
                mView = inflater.inflate(R.layout.main_view_rb, null);
                break;
        }
        mView.setLayoutParams(mParams);
        return mView;
    }

    private void initActivity() {
        showView = showModule(userType);
        //首页判断用户所属权限的功能模块
        if (showView != null) {
            logoTitle = (ImageView) showView.findViewById(R.id.logoTitle);
            nameText = (TextView) showView.findViewById(R.id.name);
            evaluateSelf = (LinearLayout) showView.findViewById(R.id.evaluate_self);
            hisinfo = (LinearLayout) showView.findViewById(R.id.his_info);
            onoffinfo = (LinearLayout) showView.findViewById(R.id.onoff_info);
            task_query = (LinearLayout) showView.findViewById(R.id.task_query);
            upload_exception = (LinearLayout) showView.findViewById(R.id.upload_exception);
            setManager = (LinearLayout) showView.findViewById(R.id.set_manager);
            rechargepay = (LinearLayout) showView.findViewById(R.id.recharge_pay);

            //创建View
//            mainLayout.setLayoutParams(mParams);

            logoTitle.setOnClickListener(this);
            evaluateSelf.setOnClickListener(this);
            hisinfo.setOnClickListener(this);
            onoffinfo.setOnClickListener(this);
            task_query.setOnClickListener(this);
            upload_exception.setOnClickListener(this);
            setManager.setOnClickListener(this);
            rechargepay.setOnClickListener(this);
        } else {
            setToast("页面加载失败，请稍后再试！");
        }
        mainLayout.addView(showView);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoTitle:
                setToast("欢迎进入隆基宁光手持式采集系统");
                break;
            case R.id.evaluate_self:
//                startActivity(null, RemoteControlWaterActivity.class);
                startActivity(null, ElectricityActivity.class);
                break;
            case R.id.his_info:
                startActivity(null, HisInfoActivity.class);
                break;
            case R.id.onoff_info:
                if (LoginActivity.userRight >= 2)//中等权限 不允许开关阀
                {
                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(context, "拉合闸操作", Toast.LENGTH_SHORT).show();
                startActivity(null, SwichPowerActivity.class);
//                startActivity(null, MyTestActivity.class);
                break;
            case R.id.task_query:
                startActivity(null, ReadingTaskActivity.class);
                break;
            case R.id.upload_exception:
                startActivity(null, UploadExceptionActivity.class);
                break;
            case R.id.set_manager:
                startActivity(null, SettingActivity.class);
                break;
            case R.id.recharge_pay:
                if (LoginActivity.userRight >= 2)//中等权限 不允许开关阀
                {
                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(context, "缴费充值", Toast.LENGTH_SHORT).show();
                startActivity(null, RechargeActivity.class);
                break;
            default:
                break;
        }
    }

}

