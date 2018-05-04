package com.zfg.org.myexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.dto.readmeterModel;
import com.zfg.org.myexample.pay.alipay.AlipayAPI;
import com.zfg.org.myexample.pay.alipay.AuthResult;
import com.zfg.org.myexample.pay.alipay.PayResult;
import com.zfg.org.myexample.pay.weipay.Constants;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.OrderInfoUtil2_0;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.zfg.org.myexample.pay.alipay.AlipayConfig.APPID;
import static com.zfg.org.myexample.pay.alipay.AlipayConfig.RSA_PRIVATE;
import static com.zfg.org.myexample.pay.weipay.Constants.WX_APP_ID;

/**
 * Created by Administrator on 2016-10-11.  zhdl 充值类型选择界面
 */
public class MyPayActivity extends BasicActivity implements View.OnClickListener {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int WEISDK_AUTH_FLAG = 3;

    @ViewInject(id = R.id.btn_alipay)
    private Button alipaybtn;

    @ViewInject(id = R.id.btn_wechat_pay)
    private Button wechatpaybtn;

    @ViewInject(id = R.id.btn_union_pay)
    private Button unionpaybtn;

    @ViewInject(id = R.id.product_subject)
    private TextView productsubject;

    @ViewInject(id = R.id.product_price)
    private TextView productprice;

//    @ViewInject(id = R.id.back_btn)
//    private Button backbtn;

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;

    @ViewInject(id = R.id.product_subject_num)
    private TextView productsubjectnum;


    private DialogLoading loading;
    private CallBack loginCallback;

    private MyPayActivity activity;
    private Preference preference;

    private HttpServiceUtil.CallBack dataCallback;

    private HttpServiceUtil.CallBack weipaydataCallback;

    private HttpServiceUtil.CallBack alipaydataCallback;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypay);
        activity = (MyPayActivity) context;
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        pageTitle.setText("支付详情");
        settingBtn.setVisibility(View.GONE);
        initActivity();
        initCallBack();
//      微信支付的回调
        weipayinitCallBack();
        alipayinitCallBack();
//      注册微信
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID);
    }


    private void initActivity() {
        backHome.setOnClickListener(this);
        alipaybtn.setOnClickListener(this);
        wechatpaybtn.setOnClickListener(this);
        unionpaybtn.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        productsubjectnum.setText(bundle.getString("meteraddr"));
        productprice.setText(String.valueOf(bundle.getDouble("money")));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            // 支付宝充值
            case R.id.btn_alipay:
//                alipayrechargeData();
                new AliPayThread().start();
                break;
            // 微信充值
            case R.id.btn_wechat_pay:
                //先检查微信是否支持
                weipayrechargeData();
                break;
            // 银联充值
            case R.id.btn_union_pay:
                Toast.makeText(context, "暂未开通银联支付功能，请使用支付宝支付功能或微信支付功能。", Toast.LENGTH_SHORT).show();
//                new UnionPayThread().start();
                break;
        }
    }

    /**
     * 支付宝支付异步任务
     *
     * @author Kylin
     */
    private class AliPayThread extends Thread {
        @Override
        public void run() {
            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,productprice.getText().toString());
            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
            String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
            final String orderInfo = orderParam + "&" + sign;

            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(context);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        }
    }

//    String result = AlipayAPI.pay(MyPayActivity.this, "测试的商品", "测试商品的详细描述", productprice.getText().toString());
//    Message msg = new Message();
//    msg.what = SDK_PAY_FLAG;
//    msg.obj = result;
//    mHandler.sendMessage(msg);


    //  微信支付线程
    public class WePayThread extends Thread {
        @Override
        public void run() {
            Message msg = WePayHandler.obtainMessage();
            msg.what = 0;
//            msg.obj = result;
            WePayHandler.sendMessage(msg);
        }
    }

    //  微信支付线程
    public class UnionPayThread extends Thread {
        @Override
        public void run() {
            Toast.makeText(context, "暂未开通银联支付功能，请使用支付宝支付功能或微信支付功能。", Toast.LENGTH_SHORT).show();
//            final String trade_no = System.currentTimeMillis()+""; // 每次交易号不一样
//            final String total_fee = productprice.getText().toString();
//            final String subject = "测试的商品";
//            String result = WechatPay.createOrder(trade_no, total_fee, subject);
//            Message msg = WePayHandler.obtainMessage();
//            msg.what = 0;
//            msg.obj = result;
//            UnionHandler.sendMessage(msg);
        }
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        rechargeData(productsubjectnum.getText().toString(), productprice.getText().toString());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    Handler WePayHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    break;
                }
            }
        }

        ;
    };

//    Handler UnionHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            if (msg.what == 0) {
//                String result = (String) msg.obj;
//                WechatPay.pay(activity, result);
//            }
//        };
//    };

    //  充值
    // {"strBackFlag":"0","strMeterAddr":"000000000040"}
    private void rechargeData(String meteraddr, String money) {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
                jsobj.put("chargeAmount", money);
                jsobj.put("userid",preference.getString(Preference.CACHE_USER));
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("开始向表中充值请等待...");
            SystemAPI.meter_rechargewater(map, dataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("操作完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (Integer.parseInt(jStatus) == 1) {
                            Toast.makeText(context, "表充值成功。", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "表充值失败。", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            //刷界面之
            private void SetDate(readmeterModel parmedata) {

            }
        };
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,productprice.getText().toString());
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void alipayrechargeData() {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
//                bizkeyValue.put("timeout_express","30m")
//                bizkeyValue.put("product_code","QUICK_MSECURITY_PAY")
//                bizkeyValue.put("total_amount","0.01")
//                bizkeyValue.put("subject","演示商品")
//                bizkeyValue.put("body","我是测试数据")
//                bizkeyValue.put("out_trade_no",getOutTradeNo() )
                final String trade_no = System.currentTimeMillis() + ""; // 每次交易号不一样
                final String total_fee = productprice.getText().toString();
                final String subject = "表缴费充值";
                jsobj.put("trade_no", trade_no);
                jsobj.put("total_fee", total_fee);
                jsobj.put("subject", subject);
                jsobj.put("timeout_express","30m");
                jsobj.put("body","表缴费充值");
                jsobj.put("product_code","QUICK_MSECURITY_PAY");
                jsobj.put("meterAddr", productsubjectnum.getText().toString());
//                jsobj.put("meterType","水表");
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterPay", jsobj.toString());

            loading.show();
            setDialogLabel("开始获取订单中请等待");
            SystemAPI.meter_alipay_rechargewater(map, alipaydataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void alipayinitCallBack() {
        alipaydataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("获取订单完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.indexOf("status") == -1) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("return_code");
                        if (jStatus.equals("SUCCESS")) {
//                            PayTask alipay = new PayTask(context);
//                            Map<String, String> result = alipay.payV2(orderInfo, true);
//                            Log.i("msp", result.toString());
////
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
//                            Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
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

    //  充值  zhdl 微信充值发起流程
    private void weipayrechargeData() {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                final String trade_no = System.currentTimeMillis() + ""; // 每次交易号不一样
                final String total_fee = productprice.getText().toString();
                final String subject = "测试的商品";
                jsobj.put("trade_no", trade_no);
                jsobj.put("total_fee", total_fee);
                jsobj.put("subject", subject);
                jsobj.put("meterAddr", productsubjectnum.getText().toString());

//                jsobj.put("meterType","水表");
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterPay", jsobj.toString());

            loading.show();
            setDialogLabel("开始获取订单中请等待");
            SystemAPI.meter_weipay_rechargewater(map, weipaydataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //返回带签名信息
    private void weipayinitCallBack() {
        weipaydataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("获取订单完成");
//                loading.hide();
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("return_code");
                        if (jStatus.equals("SUCCESS")) {
                            PayReq req = new PayReq();
                            req.appId = jsonObject.getString("appId");  // 测试用appId
                            req.partnerId = jsonObject.getString("partnerid");
                            req.prepayId = jsonObject.getString("prepayid");
                            req.nonceStr = jsonObject.getString("noncestr");
                            req.timeStamp = jsonObject.getString("timestamp");
                            req.packageValue = jsonObject.getString("package");
                            req.sign = jsonObject.getString("sign");
                            req.extData = "app"; // optional
                            Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);// 将该app注册到微信
                            msgApi.registerApp(Constants.WX_APP_ID);
                            api.sendReq(req);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}