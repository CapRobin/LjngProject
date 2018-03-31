/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zfg.org.myexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import com.zfg.org.myexample.MainActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RemoteControlActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.dialog.NumberDialog;
import com.zfg.org.myexample.dialog.WheelCallBack;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class RechargeFragment extends BaseFragment implements View.OnClickListener{
    
        
    @ViewInject(id = R.id.back_btn)
	private Button backBtn;
        //充值按钮
    @ViewInject(id = R.id.recharge_btn)
	private Button recharge_btn;
        
    @ViewInject(id = R.id.recharge_edts)
	private RelativeLayout recharge_edts;
        
        //充值输入框
    @ViewInject(id = R.id.recharge_edt)
	private EditText recharge_edt;
       
    private NumberDialog maxvalueDialog;
    private CallBack dataCallback;
    //传入的表号参数
    private String meterAddr;
    private BaseFragment curentfragment;
    private Integer index;
    private RemoteControlActivity activity;
    private DialogLoading loading;
    private InputMethodManager imm;

    public static RechargeFragment getInstance() {
        RechargeFragment fragment = new RechargeFragment();
        return fragment;
	}
        
        @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
                activity = (RemoteControlActivity) context;
                loading = new DialogLoading(activity);
                imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                initCallBack();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recharge,container, false);
		fieldView(view);
		initView(view);
		return view;
	}
	
	public void onResume(){
		super.onResume();
	}

	private void initView(View view) {
		backBtn.setOnClickListener(this);
        recharge_btn.setOnClickListener(this);
        recharge_edts.setOnClickListener(this);
        recharge_edt.setText("100");
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
                case R.id.recharge_edts:
                    if (maxvalueDialog == null) {
                        maxvalueDialog = new NumberDialog(activity, new WheelCallBack() {
                                @Override
                                public void item(int value) {
                                        recharge_edt.setText(value + "");
                                }
                        }, 200, 100, "元", "充值金额");
                    }
                    maxvalueDialog.show();
                    break;   
                case R.id.recharge_btn:
                    if (CheckUtil.isNull(recharge_edt.getText())) {
                            Toast.makeText(activity, "请选择充值金额！", Toast.LENGTH_SHORT).show();
                            return;
                    }
                    recharge();
                    break;
		}
	}
        
        //充值
        private void recharge()  {
		// 提交到服务器
		try{
            String meterAddr = "000000001235";
            String chargeAmount = recharge_edt.getText().toString();

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("chargeAmount", chargeAmount);
            } catch (JSONException ex) {
//                        Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas",jsobj.toString());
            loading.show();
            setDialogLabel("开始充值请等待");
            SystemAPI.meter_recharge(map, dataCallback);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

        private void initCallBack() {
		dataCallback = new HttpServiceUtil.CallBack() {
			@Override
			public void callback(String json) {
                setDialogLabel("充值完成");
                loading.hide();
                // 解析json
                if (json.length()>3){
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        if (jsonObject.get("strBackFlag").equals("0")){
                            Toast.makeText(context, "充值成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "充值失败", Toast.LENGTH_SHORT).show();
                        }
    //                      ToastTool.showToast(jStatus, activity);
                    } catch (JSONException e) {
                            e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "充值失败", Toast.LENGTH_SHORT).show();
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
