package com.zfg.org.myexample.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.zfg.org.myexample.BleActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RechargeActivity;
import com.zfg.org.myexample.SettingActivity;
import com.zfg.org.myexample.UploadExceptionActivity;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;

//import com.zfg.org.myexample.UploadExceptionActivity;

public class MainActivity_bak_new extends BasicActivity implements OnClickListener {
    public static int controlFlag;// 蓝牙抄表 和 蓝牙参数设置的标志

    //缓存
    private Preference preference;
    private LinearLayout.LayoutParams mParams;
    private View mView;
    private View showView;
    private CustomBanner mBanner;

    @ViewInject(id = R.id.mainLayout)
    private LinearLayout mainLayout;
//    private ImageView logoTitle;

    //    @ViewInject(id = R.id.name)
//    private TextView nameText;
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
    public static MainActivity_bak_new instance = null;
    private LinearLayout diabetes_listener;
    private LinearLayout param_setting;

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

    private void intiViewBanner() {

        ArrayList<String> images = new ArrayList<>();
        images.add(getUriFromDrawableRes(this, R.drawable.banner_01).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_02).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_03).toString());
        images.add(getUriFromDrawableRes(this, R.drawable.banner_04).toString());
        setBean(images);

    }

    /**
     * Describe：设置普通指示器
     * Params:
     * Date：2018-04-10 19:53:51
     */
    
    private void setBean(final ArrayList beans) {
        mBanner = (CustomBanner) mView.findViewById(R.id.banner);
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
        }, beans)
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


    @Override
    protected void onRestart() {
        super.onRestart();
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        userType = preference.getInt(Preference.USERTYPE);

//        initActivity();
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
            intiViewBanner();
//            logoTitle = (ImageView) showView.findViewById(R.id.logoTitle);
//            nameText = (TextView) showView.findViewById(R.id.name);
            evaluateSelf = (LinearLayout) showView.findViewById(R.id.evaluate_self);
            hisinfo = (LinearLayout) showView.findViewById(R.id.his_info);
            onoffinfo = (LinearLayout) showView.findViewById(R.id.onoff_info);
            task_query = (LinearLayout) showView.findViewById(R.id.task_query);
            upload_exception = (LinearLayout) showView.findViewById(R.id.upload_exception);
            setManager = (LinearLayout) showView.findViewById(R.id.set_manager);
            rechargepay = (LinearLayout) showView.findViewById(R.id.recharge_pay);

            diabetes_listener = (LinearLayout) showView.findViewById(R.id.diabetes_listener);
            param_setting = (LinearLayout) showView.findViewById(R.id.param_setting);

            //创建View
//            mainLayout.setLayoutParams(mParams);

//            logoTitle.setOnClickListener(this);
            evaluateSelf.setOnClickListener(this);
            hisinfo.setOnClickListener(this);
            onoffinfo.setOnClickListener(this);
            task_query.setOnClickListener(this);
            upload_exception.setOnClickListener(this);
            setManager.setOnClickListener(this);
            rechargepay.setOnClickListener(this);

            diabetes_listener.setOnClickListener(this);
            param_setting.setOnClickListener(this);
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
//            case R.id.logoTitle:
//                setToast("欢迎进入隆基宁光手持式采集系统");
//                break;
            case R.id.evaluate_self:
//                startActivity(null, RemoteControlWaterActivity.class);
                startActivity(null, MeterReadingActivity.class);
                break;
            case R.id.his_info:
                startActivity(null, RecordsQueryActivity.class);
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
            case R.id.diabetes_listener:
//                Toast.makeText(context, "点击了蓝牙通信模块", Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
                controlFlag = 0;
                startActivity(null, BleActivity.class);
//			}
                break;
            case R.id.param_setting:
                controlFlag = 1;
                startActivity(null,BleActivity.class);
                break;
            default:
                break;
        }
    }

    //获取Drawable文件的URL
    public Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

}

