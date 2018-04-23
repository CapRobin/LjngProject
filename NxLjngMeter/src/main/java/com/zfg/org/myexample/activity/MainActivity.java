package com.zfg.org.myexample.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.zfg.org.myexample.BleActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RechargeActivity;
import com.zfg.org.myexample.SettingActivity;
import com.zfg.org.myexample.TestActivity;
import com.zfg.org.myexample.UploadExceptionActivity;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;

//import com.zfg.org.myexample.UploadExceptionActivity;

public class MainActivity extends BasicActivity implements OnClickListener {
    public static int controlFlag;// 蓝牙抄表 和 蓝牙参数设置的标志
    private Preference preference;
    public static MainActivity instance = null;
//    private long lastTiem = 0;

    private LinearLayout.LayoutParams mParams;
    private CustomBanner mBanner;
    private View mView;
    private LinearLayout diabetes_listener;
    private LinearLayout param_setting;
    private LinearLayout evaluateSelf;
    private LinearLayout rechargepay;
    private LinearLayout hisinfo;
    private LinearLayout onoffinfo;
    private LinearLayout task_query;
    private LinearLayout upload_exception;
    private LinearLayout setManager;

    @ViewInject(id = R.id.contentView)
    private LinearLayout contentView;
    @ViewInject(id = R.id.copyright1)
    private TextView copyright;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);
        instance = this;

        intiViewBanner();
        initMainView(userType);
//        checkUpdate();
    }

    /**
     * Describe：首页轮播设置
     * Params:
     * Date：2018-04-11 10:52:16
     */
    private void intiViewBanner() {
        mBanner = (CustomBanner) findViewById(R.id.banner);
        ArrayList<String> images = new ArrayList<>();
//        images.add(getUriFromDrawableRes(this, R.drawable.banner_01).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_02).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_03).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_04).toString());

        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, images)
//                //设置指示器为普通指示器
//                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                //设置指示器的方向
                .setIndicatorGravity(CustomBanner.IndicatorGravity.RIGHT)
//                //设置指示器的指示点间隔
//                .setIndicatorInterval(20)
                //设置自动翻页
                .startTurning(5000);


    }

    /**
     * Describe：设置主界面的模块
     * Params:
     * Date：2018-04-02 13:01:23
     */
    private void initMainView(int userType) {
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
        setMainContentView(userType, mView);
    }

    /**
     * Describe：设置主界面的模块
     * Params:
     * Date：2018-04-11 10:48:44
     */
    public void setMainContentView(int userType, View view) {
        if (view != null) {
            if (userType != 2) {
                diabetes_listener = (LinearLayout) mView.findViewById(R.id.diabetes_listener);
                param_setting = (LinearLayout) mView.findViewById(R.id.param_setting);
                task_query = (LinearLayout) mView.findViewById(R.id.task_query);
                diabetes_listener.setOnClickListener(this);
                param_setting.setOnClickListener(this);
                task_query.setOnClickListener(this);
            }
            evaluateSelf = (LinearLayout) mView.findViewById(R.id.evaluate_self);
            rechargepay = (LinearLayout) mView.findViewById(R.id.recharge_pay);
            hisinfo = (LinearLayout) mView.findViewById(R.id.his_info);
            onoffinfo = (LinearLayout) mView.findViewById(R.id.onoff_info);
            upload_exception = (LinearLayout) mView.findViewById(R.id.upload_exception);
            setManager = (LinearLayout) mView.findViewById(R.id.set_manager);

            evaluateSelf.setOnClickListener(this);
            rechargepay.setOnClickListener(this);
            hisinfo.setOnClickListener(this);
            onoffinfo.setOnClickListener(this);
            upload_exception.setOnClickListener(this);
            setManager.setOnClickListener(this);
            copyright.setOnClickListener(this);

            contentView.addView(view);
        } else {
            finish();
            setToast("请求参数错误，请稍后再试！");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            exitApp(0);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diabetes_listener:
//                Toast.makeText(context, "是否登录"+isLogin, Toast.LENGTH_SHORT).show();
//			if (isLogin) {
                controlFlag = 0;
                startActivity(null, BleActivity.class);
//			}

//                startActivity(null, BlueReadMeterActivity.class);
                break;
            case R.id.param_setting:
                controlFlag = 1;
                startActivity(null, BleActivity.class);
//                startActivity(null, ParamSettingActivity.class);
                break;
            case R.id.evaluate_self:
//                startActivity(null, RemoteControlWaterActivity.class);
                startActivity(null, MeterReadingActivity.class);
                break;
            case R.id.recharge_pay:
//                if (LoginActivity.userRight >= 2)//中等权限 不允许开关阀
//                {
//                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Toast.makeText(context, "缴费充值", Toast.LENGTH_SHORT).show();
                startActivity(null, RechargeActivity.class);
                break;
            case R.id.his_info:
                startActivity(null, RecordsQueryActivity.class);
                break;
            case R.id.onoff_info:
//                if (LoginActivity.userRight >= 2)//中等权限 不允许开关阀
//                {
//                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                startActivity(null, SwichPowerActivity.class);
//                startActivity(null, MyTestActivity.class);
//                startActivity(null, SwichPowerActivity_bak.class);
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
            case R.id.copyright1:
                startActivity(null, TestActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);

//        initActivity();
    }

    /**
     * Describe：获取Drawable文件的URL
     * Params:
     * Date：2018-04-11 11:01:11
     */

    public Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

}

