package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-15.
 */

public class ReportusActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @ViewInject(id = R.id.save_btn)
    private Button save_btn;


    @ViewInject(id = R.id.feedbacktitle)
    private EditText feedbacktitle;

    @ViewInject(id = R.id.feedbackreason)
    private EditText feedbackreason;

    private ReportusActivity activity;
    private DialogLoading loading;

    private Preference preference;

    private HttpServiceUtil.CallBack dataCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportus);
        activity = (ReportusActivity) context;
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        initCallBack();
        initActivity();
    }

    private void initActivity() {
        backBtn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn://
                FeedBack();
                break;
            case R.id.back_btn://
                finish();
                break;
        }
    }


    private void FeedBack() {
        // 提交到服务器
        try {
            if (feedbacktitle.getText().toString().equals("")) {
                Toast.makeText(context, "请输入反馈的标题", Toast.LENGTH_SHORT).show();
                return;
            }
            if (feedbackreason.getText().toString().equals("")) {
                Toast.makeText(context, "请输入反馈的详细内容", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("feedBackTitle", feedbacktitle.getText());
                jsobj.put("feedBackReason", feedbackreason.getText());
                jsobj.put("userId", preference.getString(Preference.CACHE_USER));
                jsobj.put("feedBackTime", DateUtil.getNowTime());
            } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("正在反馈");
            SystemAPI.feedBack(map,dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("反馈完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        if (jsonObject.get("feedFlag").equals("1")) {
                            Toast.makeText(context, "反馈上报成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "反馈上报失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        };
    }


    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }
}
