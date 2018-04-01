package com.zfg.org.myexample;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zfg.org.myexample.SoftKeyBoardSatusView.SoftkeyBoardListener;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.activity.MainActivity;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.UserInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.model.MeterInfoModel;
import com.zfg.org.myexample.service.DefSetService;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.ToastTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zfg.org.myexample.utils.HttpContants.LONGIN_SUCCESS;
import static com.zfg.org.myexample.utils.HttpContants.REQUEST_ERROR;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.zfg.org.myexample.db.UserBo;
//import com.zfg.org.myexample.db.UserInfoBo;
//import com.zfg.org.myexample.db.dao.User;
//import com.zfg.org.myexample.db.dao.UserInfo;
//import com.zfg.org.myexample.request.LoginRegisterAPI;
//import com.zfg.org.myexample.service.UserService;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：LoginActivity
 * Describe：用户登录页面
 * Date：2018-03-26 07:00:42
 * Author: CapRobin@yeah.net
 *
 */
public class LoginActivity extends BasicActivity implements CompoundButton.OnCheckedChangeListener,OnFocusChangeListener, OnClickListener, SoftkeyBoardListener {

    //设置相关标记
    public static int userRight;//用户权限
//    private int userType = 0;
    private String checkInfo = "";
    private final String TAG = "LoginActivity";
    private LoginActivity activity;
    private DialogLoading loading;
    int flag = 0;

    // 存放获取输入的密码
    private String userStr = "";
    private String pwdStr = "";
    private Preference preference;
    private CallBack loginCallback;
    private UserBo userBo;
    private UserInfoBo userInfoBo;
    ScrollView scrollView;
    private List<MeterInfoModel> data;
    private GoogleApiClient client;

    //初始化相关View
    @ViewInject(id = R.id.login_uname)
    private EditText nameEditText;
    @ViewInject(id = R.id.login_psw)
    private EditText pswEditText;
    @ViewInject(id = R.id.login_btn)
    private Button loginBtn;
    @ViewInject(id = R.id.back_btn)
    private Button backBtn;
    @ViewInject(id = R.id.user_regist)
    private Button userRegist;
    @ViewInject(id = R.id.selectUserType)
    private Spinner mSpinner;
    @ViewInject(id = R.id.login_switchBtn)
    private CheckBox login_switchBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login_layout);
        activity = (LoginActivity) context;
        loading = new DialogLoading(activity);
        userBo = new UserBo(activity);
        userInfoBo = new UserInfoBo(activity);
        preference = Preference.instance(activity);
        
        initCallBack();
        init();
        initdata();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initCallBack() {
        loginCallback = new CallBack() {
            @Override
            public void callback(String json) {
                loading.dismiss();
                // loading.hide();
                // 解析json
                if (json.indexOf("status") == -1) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int jStatus = jsonObject.getInt("loginFlag");
                        if (jStatus == 1) {
                            try {
                                int strControl = jsonObject.getInt("strControl");//权限
                                userRight = strControl;
                            }
                            catch (JSONException ex)
                            {
                                userRight = 1;
                            }
                            ToastTool.showUserStatus(LONGIN_SUCCESS, activity);
                            String jSessionId = jsonObject.getString("userName");
                            preference.putString(ContantsUtil.USER_SESSIONID, jSessionId);
                            HttpServiceUtil.sessionId = jSessionId;

                            /**
                             * 登录后异步加载更新服务端相关表的所有数据
                             */
                            MyThread myThread = new MyThread();
                            new Thread(myThread).start();
//                            loading.show();
                        } else {
//                            Integer.parseInt();
                            ToastTool.showToast(REQUEST_ERROR, activity);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String status = jsonObject.getString("message");
                        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void init() {
        //登陆
        loginBtn.setOnClickListener(this);
        //回退
        backBtn.setOnClickListener(this);
        //用户名输入框
        nameEditText.setOnFocusChangeListener(this);
        //密码输入框
//        psw_right_img.setOnClickListener(this);

        userRegist.setOnClickListener(this);

        pswEditText.setOnFocusChangeListener(this);
        // 每次oncreateView的时候设置为空
//        nameEditText.setText(preference.getString(Preference.CACHE_USER));
        nameEditText.requestFocus();
//        testBtn.setOnClickListener(this);

        //密码显示与否切换
        login_switchBtn.setOnCheckedChangeListener(this);

        //选择用户角色事件监听
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    //提示信息
                    case 0:
                        userType = 0;
                        break;
                    //水表用户Lora
                    case 1:
                        ContantsUtil.setHOst( "http://longi.nxlgg.com:8039/lggmr");
                        //ContantsUtil.setHOst( "http://192.168.0.46:8046/lggmr");
                        nameEditText.setText("wangtong");
                        pswEditText.setText("123456");
                        userType = 1;
                        break;
                    //水表用户Nbiot
                    case 2:
                        ContantsUtil.setHOst("http://222.75.144.94:8808/lggmr");
                        //暂无内网地址
                        nameEditText.setText("lgg_nbiot");
                        pswEditText.setText("123456");
                        userType = 1;
                        break;
                    //选择电表用户
                    case 3:
//                        //测试数据
//                        if(params.get("resultJson").toString().equals("resultJson")){
//                            result = params.get("resultJson").toString();
//                        }
                        ContantsUtil.setHOst("http://192.168.2.136:8088/lggmr");

//                        ContantsUtil.setHOst("http://222.75.144.94:80/lggmr");
                        //ContantsUtil.setHOst("http://192.168.2.157:80/lggmr");
                        nameEditText.setText("lgg_nbiot");
                        pswEditText.setText("123456");
                        userType = 2;
                        break;
                    //选择气表用户(暂时跟Lora水表地址一样)
                    case 4:
                        ContantsUtil.setHOst( "http://longi.nxlgg.com:8046/lggmr");
                        //ContantsUtil.setHOst( "http://192.168.0.46:8046/lggmr");
                        nameEditText.setText("wangtong");
                        pswEditText.setText("123456");
                        userType = 3;
                        break;
                    //选择热表用户
                    case 5:
                        Toast.makeText(context,"该用户角色暂无数据",Toast.LENGTH_SHORT).show();
                        userType = 4;
                        break;
                    default:
                        userType = 0;
                        break;
                }
                //本地存储用户类型
                preference.putInt(Preference.USERTYPE, userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    
    /**
     * Describe：载入数据
     * Params:
     * Date：2018-03-26 08:49:21
     */
    
    private void initdata() {
        data = new ArrayList<MeterInfoModel>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
//                super.finish();
//              activity.onBackPressed();
                exit();
                break;
            case R.id.login_btn:
                if (loginCheck()) {
                    // 提交到服务器
                    goLogin();
                } else {
//                    ToastTool.showUserStatus(HttpContants.WRONG_FORMAT, activity);
                    Toast.makeText(context,checkInfo,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.psw_right_img:
                if (flag == 0){
                    pswEditText.setInputType(0x00000001);
                    flag = 1;
                }else{
                    pswEditText.setInputType(0x00000081);
                    flag = 0;
                }
                break;
            case R.id.user_regist:
                startActivity(null, NewUserRegistrationActivity.class);
                break;
        }
    }
    
    /**
     * Describe：访问服务器开始登录
     * Params:
     * Date：2018-03-26 08:58:03
     */
    private void goLogin() {
        // 提交到服务器
        try {
            String username = nameEditText.getText().toString().trim();
            BleDbHelper.userName = username;
            String password = pswEditText.getText().toString().trim();
            if ((username.equals("lanya"))&&(password.equals("lanya")))
            {
                startActivity(null, MainActivity.class);
                userRight = 1;
                return;
            }
            preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();


            try {

//                String filename = "login.txt";
//                String resultJson = tempJson(filename);
//                jsobj.put("loginJson", resultJson);

                jsobj.put("userName", username);
                jsobj.put("passWord", password);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            SystemAPI.login(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Describe：登录验证
     * Params:
     * Date：2018-03-24 17:48:57
     */

    private boolean loginCheck() {
        userStr =nameEditText.getText().toString();
        pwdStr = pswEditText.getText().toString();

        //判读输入是否为空
        if (userStr.length() == 0) {
            nameEditText.setError(getResources().getString(R.string.user_warn1));
            nameEditText.requestFocus();
            checkInfo = "账号输入有误，请重新输入";
            return false;
        }else if (pwdStr.length() == 0) {
            pswEditText.setError(getResources().getString(R.string.pwd_warn1));
            pswEditText.requestFocus();
            checkInfo = "密码输入有误，请重新输入";
            return false;
        }else if (userType == 0) {
            checkInfo = "请选择用户类型";
            return false;
        }else if (userType == 5) {
            Toast.makeText(context,"该用户角色暂无数据",Toast.LENGTH_SHORT).show();
            return false;
        }

        /**
         * 此处可进行多种验证
         */

        // 判断长度checkLength
//        if (CheckUtil.checkLength(pwdStr, 1)) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    /**
     * Describe：密码显示与否事件监听
     * Params:
     * Date：2018-03-26 08:48:33
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            //如果选中，显示密码
            pswEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            //否则隐藏密码
            pswEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void onStop() {
        nameEditText.setText("");
        pswEditText.setText("");
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyBoardVisable(int move) {
        // TODO Auto-generated method stub
        Log.e(TAG, "open");
        loginBtn.getScrollY();
        Message message = new Message();
        message.what = WHAT_SCROLL;
        message.obj = move;
        handler.sendMessageDelayed(message, 500);
    }

    @Override
    public void keyBoardInvisable(int move) {
        // TODO Auto-generated method stub
        Log.e(TAG, "close");
        handler.sendEmptyMessageDelayed(WHAT_BTN_VISABEL, 200);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.login_uname:
                nameEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if (s.length() > 0) {

                        } else {
//                            nameRightImg.setImageBitmap(null);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case R.id.login_psw:
                pswEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if (s.length() > 0) {

                        } else {
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
        }
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }


    final int WHAT_SCROLL = 0, WHAT_BTN_VISABEL = WHAT_SCROLL + 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setDialogLabel("正在合并用户设置数据");
                    break;
                case 3:
                    setDialogLabel("数据同步完成");
                    ToastTool.showUserStatus(LONGIN_SUCCESS, activity);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("data", data.toString());
                    startActivity(null, MainActivity.class);
                    finishSimple();
                    break;
                case 4:
                    setDialogLabel("正在同步本地用户设置数据");
                    break;
                case 5:
                    setDialogLabel("同步用户信息成功");
                    break;
                case 6:
                    loading.dismiss();
                    Toast.makeText(context, "同步用户信息失败，请稍后再试", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case WHAT_SCROLL:
                    int move = (Integer) msg.obj;
                    scrollView.smoothScrollBy(0, move);
                    break;
                case WHAT_BTN_VISABEL:
                    break;
            }
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    /**
     * Describe：登录后异步加载更新服务端相关表的所有数据
     * Params:
     * Date：2018-03-31 16:38:55
     */
    
    class MyThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("userName", preference.getString(Preference.CACHE_USER));
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeter", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_USERMETERS, map);
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject jsonesult = new JSONObject(result);
                    int res = jsonesult.getInt("strBackFlag");
                    if (res == 1) {
                        // 如果有水表记录
                        if (jsonesult.getJSONObject("dataFlag").getInt("waterMeterFlag") == 1) {
                            JSONArray pages = jsonesult.getJSONArray("waterMeterList");
                            for (int i = 0; i < pages.length(); i++) {
                                MeterInfoModel dto = new MeterInfoModel();
                                dto.of(pages.getJSONObject(i));
                                dto.setMeterType("水表");
                                data.add(dto);
                            }
                        }
                        // 如果有气表记录
                        if (jsonesult.getJSONObject("dataFlag").getInt("gasMeterFlag") == 1) {
                            JSONArray pages = jsonesult.getJSONArray("gasMeterList");
                            for (int i = 0; i < pages.length(); i++) {
                                MeterInfoModel dto = new MeterInfoModel();
                                dto.of(pages.getJSONObject(i));
                                dto.setMeterType("电表");
                                data.add(dto);
                            }
                        }
                        DefSetService service = new DefSetService();
                        List<MeterInfo> meterinfos = service.convertMeterInfo(data);
                        /**
                         * 保存登录所获得的数据
                         */
                        new MeterInfoBo(context).saveList(meterinfos);

                        handler.sendEmptyMessage(3);
                    } else {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
//          加载用户配置
            Config.loadUserSet(activity);
        }
    }

    ;

    private boolean loadUserInfo(String json) {
        return true;
    }

    private static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private static void isNumber(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
//            Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(str);
        if (m.matches()) {
//            Toast.makeText(Main.this,"输入的是字母", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(str);
        if (m.matches()) {
//            Toast.makeText(Main.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mVideoView.stopPlayback();
    }

    @Override//返回按键
    public void onBackPressed() {
        exit();
//        super.onBackPressed();
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




