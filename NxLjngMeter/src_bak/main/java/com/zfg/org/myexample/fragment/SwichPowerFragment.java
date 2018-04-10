package com.zfg.org.myexample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RemoteControlActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zfg
 */
public class SwichPowerFragment extends BaseFragment implements View.OnClickListener{
        
    private CallBack dataCallback;  

    @ViewInject(id = R.id.off)
    private Button off;
    
    @ViewInject(id = R.id.on)
    private Button on;
//    @ViewInject(id = R.id.image_View)
//    private ImageView  image_View;
    
    private DialogLoading loading;
    
    public static SwichPowerFragment getInstance() {
            SwichPowerFragment fragment = new SwichPowerFragment();
            return fragment;
    }
    private RemoteControlActivity activity;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            activity = (RemoteControlActivity) context;
            loading = new DialogLoading(activity);
            initCallBack();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_swichpower,container, false);
            fieldView(view);
            initView(view);
            return view;
    }
    
    public void onResume(){
            super.onResume();
    }

    private void initView(View view) {
            off.setOnClickListener(this);
            on.setOnClickListener(this);
    }

    public void replaceFragment(String tag, Fragment tempFragment, boolean isAdd) {
            FragmentTransaction tran = getChildFragmentManager().beginTransaction();
            tran.replace(R.id.content_fragment, tempFragment, tag);
            if (!isAdd) {
                    tran.addToBackStack(tag);
            }
            tran.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()) {
            case R.id.back_btn:
                activity.finish();
                break;
                
            case R.id.off:
                meter_off();
                break
                        ;
            case R.id.on:
                meter_on();
            }
    }
    //合闸
    private void meter_on()  {
            // 提交到服务器
            try{
                String meterAddr = "000000001235";
                String operationId = "1";

                JSONObject jsobj = new JSONObject();
                try {
                    jsobj.put("meterAddr", meterAddr);
                    jsobj.put("operationId", operationId);
                } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
                }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
                Map<String, Object> map = new HashMap<String, Object>();
                //"{\"name\":\"admin\",\"password\":admin\"}"
                map.put("ngMeterGas",jsobj.toString());
                loading.show();
                setDialogLabel("正在开启阀门");
                SystemAPI.meter_on(map, dataCallback);
            }
            catch (Exception e) {
            e.printStackTrace();
         }
    }
        
    //拉闸
    private void meter_off()  {
            // 提交到服务器
            try{
                String meterAddr = "000000001235";
                String operationId = "0";

                JSONObject jsobj = new JSONObject();
                try {
                    jsobj.put("meterAddr", meterAddr);
                    jsobj.put("operationId", operationId);
                } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
                }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
                Map<String, Object> map = new HashMap<String, Object>();
                //"{\"name\":\"admin\",\"password\":admin\"}"
                map.put("ngMeterGas",jsobj.toString());
                loading.show();
                setDialogLabel("正在关闭阀门");
                SystemAPI.meter_off(map, dataCallback);
            }
            catch (Exception e) {
            e.printStackTrace();
         }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
		setDialogLabel("开关阀完成");
                loading.hide();
                // 解析json
                if (json.length()>3){
                    try {
                            JSONObject jsonObject = new JSONObject(json);
                            String jStatus = jsonObject.toString();
                            if (jsonObject.get("strBackFlag").equals("0")){
                                Toast.makeText(context, "开关阀操作成功", Toast.LENGTH_SHORT).show();
//                                     
                            } else {
                                Toast.makeText(context, "开关阀操作失败", Toast.LENGTH_SHORT).show();
                            }
                            //ToastTool.showToast(jStatus, activity);
                            of(jsonObject);
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
    
    public void of(JSONObject data) throws JSONException {
        if (data.getString("strBackflag") == "0"){
            Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }
}
