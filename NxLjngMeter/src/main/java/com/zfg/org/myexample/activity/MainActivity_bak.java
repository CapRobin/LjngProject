package com.zfg.org.myexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.BleActivity;
import com.zfg.org.myexample.PlanActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RechargeActivity;
import com.zfg.org.myexample.RemoteControlWaterActivity;
import com.zfg.org.myexample.SettingActivity;
import com.zfg.org.myexample.UploadExceptionActivity;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.db.dao.UserInfo;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.zxing.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//import com.zfg.org.myexample.ble1.PeripheralActivity;
//import com.zfg.org.myexample.ble1.ScanningActivity;
//import com.zfg.org.myexample.db.dao.UserInfo;
//import com.zfg.org.myexample.pay.weipay.PayActivity;
//import com.zfg.org.myexample.pay.weipay.wxapi.WXEntryActivity;


public class MainActivity_bak extends BasicActivity implements OnClickListener {
    public static int controlFlag;// 蓝牙抄表 和 蓝牙参数设置的标志
    @ViewInject(id = R.id.diabetes_listener)
    private LinearLayout diabetesListener;

    //选择测试页面
    @ViewInject(id = R.id.selectPage)
    private Spinner selectPage;


    @ViewInject(id = R.id.param_setting)
    private LinearLayout param_setting;

//    @ViewInject(id =R.id.demo1)
//    private LinearLayout demo1;

//    @ViewInject(id = R.id.news_share)
//    private LinearLayout newsListener;
//    @ViewInject(id = R.id.eat_manager)
//    private LinearLayout eatManager;
//    @ViewInject(id = R.id.sport_manager)
//    private LinearLayout sportManager;

    @ViewInject(id = R.id.evaluate_self)
    private LinearLayout evaluateSelf;

//    //把前台的id注入到后台
//    @ViewInject(id = R.id.evaluate_selfTest)
//    private LinearLayout evaluateSelfTest;
//    @ViewInject(id = R.id.content_report)
//    private LinearLayout contentReport;
//    @ViewInject(id = R.id.drug_manager)
//    private LinearLayout drugManager;
//    @ViewInject(id = R.id.target_manager)
//    private LinearLayout targetManager;

    @ViewInject(id = R.id.set_manager)
    private LinearLayout setManager;

    @ViewInject(id = R.id.recharge_pay)
    private LinearLayout rechargepay;
//
//    @ViewInject(id = R.id.weixin_pay)
//    private LinearLayout weipay;

//    @ViewInject(id = R.id.query_info)
//    private LinearLayout queryinfo;

    @ViewInject(id = R.id.his_info)
    private LinearLayout hisinfo;

    @ViewInject(id = R.id.onoff_info)
    private LinearLayout onoffinfo;

    @ViewInject(id = R.id.task_query)
    private LinearLayout task_query;

    @ViewInject(id = R.id.upload_exception)
    private LinearLayout upload_exception;


//    @ViewInject(id = R.id.error_query)
//    private LinearLayout errinfo;

    /*地图功能*/
//    @ViewInject(id = R.id.map_info)
//    private LinearLayout mapinfo;

    @ViewInject(id = R.id.scance_zxing)
    private ImageButton scancelZxing;

    @ViewInject(id = R.id.logoTitle)
    private ImageView photo;

    @ViewInject(id = R.id.name)
    private TextView nameText;

    @ViewInject(id = R.id.member)
    private ImageButton memberBtn;

    @ViewInject(id = R.id.toast_container)
    private LinearLayout toastCon;

    @ViewInject(id = R.id.toast_text)
    private TextView toastTxt;

    @ViewInject(id = R.id.clock_icon)
    private ImageView clockIcon;

    @ViewInject(id = R.id.pre)
    private ImageButton pre;

    @ViewInject(id = R.id.next)
    private ImageButton next;

    //提示框
    private AlertDialogZfg alert;

    private long lastBackPress = 0;
    //用户信息
    private UserInfo uInfo;
    //
    private final int CAPTURE = 2001;
    //缓存
    private Preference preference;

    private MainActivity_bak activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContantsUtil.MAIN_UPDATE = false;
        preference = Preference.instance(context);
        initActivity();
        checkUpdate();
//        Display display = this.getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height=display.getHeight();
//        Toast.makeText(context, "宽:"+String.valueOf(width) +"高:"+String.valueOf(height), Toast.LENGTH_SHORT).show();



        //选择相关页面
        selectPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    //提示信息
                    case 0:
//                        startActivity(null, ElectricityActivity.class);
//                        Toast.makeText(MainActivity.this,"请选择相关页面",Toast.LENGTH_SHORT).show();
                        break;
                    //水表用户Lora
                    case 1:
//                        Toast.makeText(context,"水表页面",Toast.LENGTH_SHORT).show();
                        startActivity(null, WaterMeterActivity.class);
                        break;
                    case 2:
//                        Toast.makeText(context,"电表页面",Toast.LENGTH_SHORT).show();
                        startActivity(null, MeterReadingActivity.class);
                        break;
                    case 3:
//                        Toast.makeText(context,"气表页面",Toast.LENGTH_SHORT).show();
                        startActivity(null, GasMeterActivity.class);
                        break;
                    case 4:
//                        Toast.makeText(context,"热表页面",Toast.LENGTH_SHORT).show();
                        startActivity(null, HeatMeterActivity.class);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setIconSize()//设置主界面图标大小
    {

    }

    private void initActivity() {
        diabetesListener.setOnClickListener(this);
        param_setting.setOnClickListener(this);
        scancelZxing.setOnClickListener(this);

//        demo1.setOnClickListener(this);
//        newsListener.setOnClickListener(this);
//        eatManager.setOnClickListener(this);
//        sportManager.setOnClickListener(this);
        evaluateSelf.setOnClickListener(this);
//        evaluateSelfTest.setOnClickListener(this);
//        contentReport.setOnClickListener(this);
//        drugManager.setOnClickListener(this);
//        targetManager.setOnClickListener(this);
        setManager.setOnClickListener(this);
        photo.setOnClickListener(this);
        memberBtn.setOnClickListener(this);
        clockIcon.setOnClickListener(this);
        next.setOnClickListener(this);
        pre.setOnClickListener(this);
        /*缴费充值*/
        rechargepay.setOnClickListener(this);
        /*历史查询*/
        hisinfo.setOnClickListener(this);
/*拉合闸*/
        onoffinfo.setOnClickListener(this);

        upload_exception.setOnClickListener(this);

        task_query.setOnClickListener(this);
//        queryinfo.setOnClickListener(this);
//        hisinfo.setOnClickListener(this);
//        errinfo.setOnClickListener(this);
//        mapinfo.setOnClickListener(this);
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
            case R.id.scance_zxing:
//                Toast.makeText(context, "点击了二维码扫描", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(context, CaptureActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, CAPTURE);
                break;
            case R.id.logoTitle:
                int getUserType = preference.getInt(Preference.USERTYPE);
                switch (getUserType){
                    case 0:
                        Toast.makeText(context,"获取用户角色失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        startActivity(null, WaterMeterActivity.class);
                        break;
                    case 2:
                        startActivity(null, WaterMeterActivity.class);
                        break;
                    case 3:
                        startActivity(null, MeterReadingActivity.class);
                        break;
                    case 4:
                        startActivity(null, GasMeterActivity.class);
                        break;
                    case 5:
                        startActivity(null, HeatMeterActivity.class);
                        break;
                }

//                startActivity(null, DemoActivity.class);
                break;
//                    if (checkLogin()) {
            // JCTODO 跳到个人信息中心
//                            Bundle bundle = new Bundle();
//                            bundle.putLong("mid",
//                                            StringUtil.toLong(ContantsUtil.DEFAULT_TEMP_UID));
//
//                    }

//            case R.id.news_share:
//                    Toast.makeText(context, "点击了news_share",Toast.LENGTH_SHORT).show();
////                    startActivity(null, NewListActivity.class);
//                    break;
//            case R.id.sport_manager:
//                    Toast.makeText(context, "点击了sport_manager",Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
//				if ((ContantsUtil.curInfo == null && CheckUtil
//						.isNull(ContantsUtil.curInfo.getSex()))
//						|| ContantsUtil.curInfo.getSex() == -1) {
//					Toast.makeText(context, "您的个人信息未完善，请先完善用户信息",
//							Toast.LENGTH_SHORT).show();
//					Bundle bundle = new Bundle();
//					bundle.putLong("mid",
//							StringUtil.toLong(ContantsUtil.DEFAULT_TEMP_UID));
//					bundle.putBoolean("isedit", true);
//					startActivity(bundle, ManageUsersActivity.class);
//					return;
//				}
//				if (ContantsUtil.curInfo.getExamStatus() == 0) {
//					alert.show();
//					return;
//				}
//                            startActivity(null, SportActivity.class);
//			}
//                    break;
//            case R.id.eat_manager:
//                    Toast.makeText(context, "点击了eat_manager",Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
//				if ((ContantsUtil.curInfo == null && CheckUtil
//						.isNull(ContantsUtil.curInfo.getSex()))
//						|| ContantsUtil.curInfo.getSex() == -1) {
//					Toast.makeText(context, "您的个人信息未完善，请先完善用户信息",
//							Toast.LENGTH_SHORT).show();
//					Bundle bundle = new Bundle();
//					bundle.putLong("mid",
//							StringUtil.toLong(ContantsUtil.DEFAULT_TEMP_UID));
//					bundle.putBoolean("isedit", true);
//					startActivity(bundle, ManageUsersActivity.class);
//					return;
//				}
//				if (ContantsUtil.curInfo.getExamStatus() == 0) {
//					alert.show();
//					return;
//				}
//                            startActivity(null, EatActivity.class);
//			}
//                    break;
            case R.id.evaluate_self:
//                Toast.makeText(context, "远程通信", Toast.LENGTH_SHORT).show();
                startActivity(null, RemoteControlWaterActivity.class);
//                Toast.makeText(context, "点击了远程通信模块",Toast.LENGTH_SHORT).show();
//                startActivity(null, RemoteControlActivity.class);
//			if (!checkLogin()) {
//				return;
//			}
//			if ((ContantsUtil.curInfo == null && CheckUtil
//					.isNull(ContantsUtil.curInfo.getSex()))
//					|| ContantsUtil.curInfo.getSex() == -1) {
//				Toast.makeText(context, "您的个人信息未完善，请先完善用户信息",
//						Toast.LENGTH_SHORT).show();
//				Bundle bundle = new Bundle();
//				bundle.putLong("mid",
//						StringUtil.toLong(ContantsUtil.DEFAULT_TEMP_UID));
//				bundle.putBoolean("isedit", true);
//				startActivity(bundle, ManageUsersActivity.class);
//				return;
//			}
//                    startActivity(null, AssessActivity.class);
                break;
//            zhdl 新增测试按钮事件
//            case R.id.evaluate_selfTest:
//                Toast.makeText(context, "远程通信zhdl显示", Toast.LENGTH_SHORT).show();
//                startActivity(null, RemoteControlWaterTestActivity.class);
//                break;
            case R.id.recharge_pay:
                if (LoginActivity.userRight >=2)//中等权限 不允许开关阀
                {
                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(context, "缴费充值", Toast.LENGTH_SHORT).show();
                startActivity(null, RechargeActivity.class);
                break;
            case R.id.his_info:
//                Toast.makeText(context, "历史记录查询", Toast.LENGTH_SHORT).show();
                startActivity(null, RecordsQueryActivity.class);
                break;
            case R.id.onoff_info:
                if (LoginActivity.userRight >=2)//中等权限 不允许开关阀
                {
                    Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(context, "拉合闸操作", Toast.LENGTH_SHORT).show();
                startActivity(null, SwichPowerActivity.class);
                break;
//            case R.id.query_info:
//                Toast.makeText(context, "点击信息查询模块",Toast.LENGTH_SHORT).show();
//                startActivity(null, QueryInfoActivity.class);
//                break;
            case R.id.upload_exception:
//                Toast.makeText(context, "异常上报", Toast.LENGTH_SHORT).show();
                startActivity(null, UploadExceptionActivity.class);
                break;
//            case R.id.error_query:
//                Toast.makeText(context, "点击故障信息模块",Toast.LENGTH_SHORT).show();
//                startActivity(null, ErrorInfoActivity.class);
//                break;
//            case R.id.map_info:
//                Toast.makeText(context, "点击地图信息模块",Toast.LENGTH_SHORT).show();
//                startActivity(null, MapLocationActivity.class);
//                break;

//            case R.id.content_report:
//                    Toast.makeText(context, "点击了content_report",Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
//				if ((ContantsUtil.curInfo == null && CheckUtil
//						.isNull(ContantsUtil.curInfo.getSex()))
//						|| ContantsUtil.curInfo.getSex() == -1) {
//					Toast.makeText(context, "您的个人信息未完善，请先完善用户信息",
//							Toast.LENGTH_SHORT).show();
//					Bundle bundle = new Bundle();
//					bundle.putLong("mid",
//							StringUtil.toLong(ContantsUtil.DEFAULT_TEMP_UID));
//					bundle.putBoolean("isedit", true);
//					startActivity(bundle, ManageUsersActivity.class);
//					return;
//				}
//				if (ContantsUtil.curInfo.getExamStatus() == 0) {
//					alert.show();
//					return;
//				}
//                            startActivity(null, UserReportActivity.class);
//			}
//                    break;
//            case R.id.drug_manager:
//                    Toast.makeText(context, "点击了drug_manager",Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
//                            startActivity(null, MedicineActivity.class);
//			}
//                    break;
            case R.id.task_query:
//                Toast.makeText(context, "任务查询", Toast.LENGTH_SHORT).show();
                startActivity(null, ReadingTaskActivity.class);
                break;
            case R.id.set_manager:
//                Toast.makeText(context, "系统设置", Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
                startActivity(null, SettingActivity.class);

//                adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        builder.setIcon(R.drawable.ic_launcher);
//                builder.setTitle("提示：");
//                builder.setAdapter(adapter1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(MainActivity.this,"你点击了第"+ i +"个item", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();



//			}
                break;
            case R.id.member:
//                Toast.makeText(context, "登录管理", Toast.LENGTH_SHORT).show();
//			if (checkLogin()) {
                startActivity(null, LoginActivity.class);
//			}
                break;
            case R.id.clock_icon:
//                Toast.makeText(context, "定时提醒", Toast.LENGTH_SHORT).show();
                // if (toastCon.getVisibility() == View.VISIBLE) {
                // toastCon.setVisibility(View.GONE);
                // } else {
                // toastCon.setVisibility(View.VISIBLE);
                // }
//			if (checkLogin()) {
                context.startActivity(null, PlanActivity.class);
                break;
        }
    }
    private ArrayAdapter<String> adapter1;
    private String[] datas={"1","2","3","4","5"};
    private void switchData(String code, String codeType) {
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("type", codeType);
//        if (checkLogin()) {
//            startActivity(bundle, IndicatorActivity.class);
//        } else {
//            startActivity(bundle, LoginActivity.class);
//        }
    }

    /**
     * 检查是否登录
     *
     * @return
     */
    private boolean checkLogin() {
        if (!CheckUtil.isNull(HttpServiceUtil.sessionId)
                && !CheckUtil.isNull(ContantsUtil.DEFAULT_TEMP_UID)) {
            return true;
        } else {
            startActivity(new Bundle(), LoginActivity.class);
        }
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        exit();
    }

    public void onDestroy() {
        super.onDestroy();
    }


    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private void checkUpdate() {
        long lastTime = preference.getLong(Preference.UPDATE_TIME);
        if (System.currentTimeMillis() - lastTime < 24 * 60 * 60 * 1000) {
            return;
        }
        JSONObject jsobj = new JSONObject();
        try {
            jsobj.put("device", 0);
            jsobj.put("current", ContantsUtil.currentVesion);
        } catch (JSONException ex) {
            Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ngMeter", jsobj.toString());
        HttpServiceUtil.request(ContantsUtil.UPDATE_CHECK_URL, "post", params,
                new HttpServiceUtil.CallBack() {
                    @Override
                    public void callback(String json) {
//                        loading.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(json);
                            if (jsonObj.getInt("strBackFlag") == 1) {
                                if (!jsonObj.has("data")) {
                                    Toast.makeText(context, "已经是最新版本", Toast.LENGTH_SHORT).show();
                                } else {
//                                  JSONObject dataJson = jsonObj.getJSONObject("data");
                                    String url = jsonObj.getString("url");
                                    String apkname = jsonObj.getString("soft");
                                    preference.putString(Preference.UPDATE_URL, url);
                                    preference.putString(Preference.UPDATE_APK, apkname);
                                    showUpdateDialog(url);
                                }
                            } else if (jsonObj.getInt("strBackFlag") == -6) {
                                Toast.makeText(context, "当前已是最新版本不需要更新",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "检查版本失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                            preference.putLong(Preference.UPDATE_TIME, System.currentTimeMillis());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showUpdateDialog(final String url) {
        alert = new AlertDialogZfg(context, "有新版本，您确定要更新吗");
        alert.setCallBack(new AlertDialogZfg.CallBack() {
            @Override
            public void cancel() {
            }

            @Override
            public void callBack() {
                Uri uri = Uri.parse(url);
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(downloadIntent);
            }
        });
        alert.show();
    }



    private List<Activity> activityList = new LinkedList<Activity>();

    //向链表中，添加Activity
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //结束整个应用程序
    public void exit() {

        //遍历 链表，依次杀掉各个Activity
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }
}

