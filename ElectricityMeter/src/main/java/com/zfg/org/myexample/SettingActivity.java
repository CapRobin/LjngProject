package com.zfg.org.myexample;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.TranLoading;
//import com.dian.diabetes.request.LoginRegisterAPI;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.ReadAreaFile;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.fragment.AboutFragment;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BasicActivity implements OnClickListener {

    //
    @ViewInject(id = R.id.back_btn)
    private Button backBtn;
    //关于我们
    @ViewInject(id = R.id.about_us)
    private RelativeLayout aboutUs;
    //帮助
    @ViewInject(id = R.id.help)
    private RelativeLayout help;
    //退出
    @ViewInject(id = R.id.exit_user_login)
    private Button logout;
    //检查更新
    @ViewInject(id = R.id.check_update)
    private RelativeLayout checkupdate;
    //意见反馈
    @ViewInject(id = R.id.reportus)
    private RelativeLayout reportus;
    //公告信息
    @ViewInject(id = R.id.officialnews)
    private RelativeLayout officialnews;

    //  二维码
    @ViewInject(id = R.id.user_info)
    private RelativeLayout user_info;


    private AlertDialogZfg alert;
    private TranLoading loading;
    private Preference preference;
    private Map<String, String> maps;
    private SettingActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_layout);
        loading = new TranLoading(context);
        preference = Preference.instance(context);
        initActivity();
    }

    private void initActivity() {
        checkupdate.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        logout.setOnClickListener(this);
        help.setOnClickListener(this);
        reportus.setOnClickListener(this);
        officialnews.setOnClickListener(this);
        user_info.setOnClickListener(this);
        maps = new ReadAreaFile(context).readArea();
        Map<String, String> maps = new ReadAreaFile(context).readArea();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.about_us:
                showAboutDialog();
                break;
            case R.id.check_update:
                checkUpdate();
                break;
            case R.id.exit_user_login:
                startActivity(null,LoginActivity.class);
                break;
            case R.id.help:
                showHelpDialog();
                break;
            case R.id.reportus:
                showReportusDialog();
                break;
            case R.id.officialnews:
                showOfficialNewsDialog();
                break;
            case R.id.user_info:
//                showQRCodeDialog();
                break;

        }
    }

    /**
     * 打开关于
     */
    private void showAboutDialog() {
        String tag = "about_dialog";
        FragmentManager manager = context.getSupportFragmentManager();
        AboutFragment fragment = (AboutFragment) manager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = AboutFragment.getInstance();
        }
        fragment.show(manager, tag);
    }

    /**
     * 打开帮助
     */
    private void showHelpDialog() {
        startActivity(null, HelpActivity.class);
    }

    private void showReportusDialog() {
        startActivity(null, ReportusActivity.class);
    }

    private void showOfficialNewsDialog() {
        startActivity(null, OfficialNewsActivity.class);
    }

    private void showQRCodeDialog() {
        startActivity(null, QRCodeActivity.class);
    }

    private void checkUpdate() {
        loading.show();

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
                        loading.dismiss();
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
//              下载
                Uri uri = Uri.parse(url);
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(downloadIntent);
            }
        });
        alert.show();
    }

    private void logout() {

        HttpServiceUtil.sessionId = null;
        ContantsUtil.curUser = null;
        ContantsUtil.curInfo = null;
        ContantsUtil.DEFAULT_TEMP_UID = null;
        preference.remove(ContantsUtil.USER_SESSIONID);
        preference.remove(ContantsUtil.USER_ID);
        ContantsUtil.MAIN_UPDATE = false;
        logout.setVisibility(View.GONE);
        finish();

//		loading.show();
//		SystemAPI.logout(null, this, new HttpServiceUtil.CallBack() {
//			@Override
//			public void callback(String json) {
//				loading.dismiss();
//				HttpServiceUtil.sessionId = null;
//				ContantsUtil.curUser = null;
//				ContantsUtil.curInfo = null;
//				ContantsUtil.DEFAULT_TEMP_UID = null;
//				preference.remove(ContantsUtil.USER_SESSIONID);
//				preference.remove(ContantsUtil.USER_ID);
//				ContantsUtil.MAIN_UPDATE = false;
//				logout.setVisibility(View.GONE);
//				finish();
//			}
//		});
    }

}
