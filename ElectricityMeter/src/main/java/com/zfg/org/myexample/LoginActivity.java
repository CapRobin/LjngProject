package com.zfg.org.myexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zfg.org.myexample.SoftKeyBoardSatusView.SoftkeyBoardListener;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
//import com.zfg.org.myexample.db.UserBo;
//import com.zfg.org.myexample.db.UserInfoBo;
//import com.zfg.org.myexample.db.dao.User;
//import com.zfg.org.myexample.db.dao.UserInfo;
//import com.zfg.org.myexample.request.LoginRegisterAPI;
//import com.zfg.org.myexample.service.UserService;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.UserInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.model.MeterInfoModel;
import com.zfg.org.myexample.service.DefSetService;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpContants;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.ToastTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.zfg.org.myexample.utils.HttpContants.LONGIN_SUCCESS;
import static com.zfg.org.myexample.utils.HttpContants.REQUEST_ERROR;

/**
 * @author zfg
 * @createDate 2014年7月4日
 */
public class LoginActivity extends BasicActivity implements OnFocusChangeListener, OnClickListener, SoftkeyBoardListener {
    public static int userRight;//用户权限
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
    @ViewInject(id = R.id.name_right_img)
    private ImageView nameRightImg;
    @ViewInject(id = R.id.psw_right_img)
    private ImageView psw_right_img;

    private final String TAG = "LoginActivity";


//    @ViewInject(id = R.id.videoView)
//    private VideoView mVideoView;
//
//    @ViewInject(id = R.id.container)
//    private ViewGroup contianer;

//    public static final String VIDEO_NAME = "welcome_video.mp4";


    private LoginActivity activity;
    private DialogLoading loading;
    // 存放获取输入的密码
    private String psw = "";
    private Preference preference;
    private CallBack loginCallback;
    private UserBo userBo;
    private UserInfoBo userInfoBo;
    ScrollView scrollView;
    private String scanCode;
    private Bundle bundle;
    private List<MeterInfoModel> data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private RadioGroup grpLoginChoose;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////          获取窗体状态
//            Window window = getWindow();
//            window.setFlags(
////                  窗体布局
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        setContentView(R.layout.fragment_login_layout);
        activity = (LoginActivity) context;
        loading = new DialogLoading(activity);
        userBo = new UserBo(activity);
        userInfoBo = new UserInfoBo(activity);
        preference = Preference.instance(activity);
        grpLoginChoose = (RadioGroup)findViewById(R.id.grp_login_choose);
     /*   grpLoginChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (radioButtonId)
                {
                    case R.id.rg1:
                        ContantsUtil.setHOst( "http://192.168.2.91:6608/lggmr");
                        ContantsUtil.setHOst( "http://222.75.144.94:8807/lggmr");
                        break;
                    case R.id.rg2:
                        ContantsUtil.setHOst("http://192.168.2.91:6608/lggmr");
//                        ContantsUtil.setHOst("http://222.75.144.94:8808/lggmr");
                        break;
                }

            }
        });*/
        initCallBack();
        init();
        initdata();

//        File videoFile = getFileStreamPath(VIDEO_NAME);
////如果视频文件存在
//        if (!videoFile.exists()) {
//            videoFile = copyVideoFile();
//        }
////播放视频
//        playVideo(videoFile);
//        playAnim();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initdata() {
        data = new ArrayList<MeterInfoModel>();
    }

    private void init() {
        //登陆
        loginBtn.setOnClickListener(this);
        //回退
        backBtn.setOnClickListener(this);
        //用户名输入框
        nameEditText.setOnFocusChangeListener(this);
        //密码输入框
        psw_right_img.setOnClickListener(this);

        userRegist.setOnClickListener(this);

        pswEditText.setOnFocusChangeListener(this);
        // 每次oncreateView的时候设置为空
//        nameEditText.setText(preference.getString(Preference.CACHE_USER));
        nameEditText.requestFocus();

    }

    /**
     * 检查用户输入的密码
     *
     * @return 是否通过检查
     */
    private boolean checkPsw() {
        psw = pswEditText.getText().toString();
        // 判断长度checkLength
        if (CheckUtil.checkLength(psw, 1)) {
            return true;
        } else {
            return false;
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
    int flag = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
//                super.finish();
//              activity.onBackPressed();
                exit();
                break;
            case R.id.login_btn:
                if (checkPsw()) {
                    // 提交到服务器

                    goLogin();
                } else {
                    ToastTool.showUserStatus(HttpContants.WRONG_FORMAT, activity);
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

    private void goLogin() {
        // 提交到服务器
        try {
            String username = nameEditText.getText().toString().trim();
            BleDbHelper.userName = username;
            String password = pswEditText.getText().toString().trim();
//            if ((username.equals("lanya"))&&(password.equals("lanya")))
//            {
//                startActivity(null, MainActivity.class);
//                userRight = 1;
//                return;
//            }
            preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();
            try {
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
                            // 显示登录状态
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
                            nameRightImg.setImageBitmap(null);
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

    @SuppressLint("HandlerLeak")
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
     * 同步服务端数据
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
                                dto.setMeterType("气表");
                                data.add(dto);
                            }
                        }
                        DefSetService service = new DefSetService();
                        List<MeterInfo> meterinfos = service.convertMeterInfo(data);
//                      数据保存
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

//    @NonNull
//    private File copyVideoFile() {
//        File videoFile;
//        try {
//            FileOutputStream fos = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
//            InputStream in = getResources().openRawResource(R.raw.welcome_video);
//            byte[] buff = new byte[1024];
//            int len = 0;
//            while ((len = in.read(buff)) != -1) {
//                fos.write(buff, 0, len);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        videoFile = getFileStreamPath(VIDEO_NAME);
//        if (!videoFile.exists())
//            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
//        return videoFile;
//    }

//    private void playVideo(File videoFile) {
//        mVideoView.setVideoPath(videoFile.getPath());
//        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//                mediaPlayer.start();
//            }
//        });
//    }

//    private void playAnim() {
//        ObjectAnimator anim = ObjectAnimator.ofFloat(appName, "alpha", 0,1);
//        anim.setDuration(4000);
//        anim.setRepeatCount(1);
//        anim.setRepeatMode(ObjectAnimator.REVERSE);
//        anim.start();
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                appName.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

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




