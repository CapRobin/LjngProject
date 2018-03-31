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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zfg.org.myexample.MainActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RemoteControlActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.dto.readmeterModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zfg
 */
public class ReadDataFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    //        数据输入框和确认按钮行
    @ViewInject(id = R.id.meterAddrs)
    private RelativeLayout meterAddrs;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

//    @ViewInject(id = R.id.data_list)
//    private ImageButton datalist;


//    @ViewInject(id = R.id.strBackDatas)
//    private RelativeLayout strBackDatas;
//
//    @ViewInject(id = R.id.strBackData)
//    private EditText strBackData;
//
//
//    @ViewInject(id = R.id.strBackData1s)
//    private RelativeLayout strBackData1s;
//
//    @ViewInject(id = R.id.strBackData1)
//    private EditText strBackData1;
//
//    @ViewInject(id = R.id.strBackData2s)
//    private RelativeLayout strBackData2s;
//
//    @ViewInject(id = R.id.strBackData2)
//    private EditText strBackData2;
//
//    @ViewInject(id = R.id.strBackData3s)
//    private RelativeLayout strBackData3s;
//
//    @ViewInject(id = R.id.strBackData3)
//    private EditText strBackData3;

//    @ViewInject(id = R.id.strBackData4s)
//    private RelativeLayout strBackData4s;
//
//    @ViewInject(id = R.id.strBackData4)
//    private EditText strBackData4;

//    @ViewInject(id = R.id.strBackData5s)
//    private RelativeLayout strBackData5s;
//
//    @ViewInject(id = R.id.strBackData5)
//    private EditText strBackData5;
//
//    @ViewInject(id = R.id.strBackData6s)
//    private RelativeLayout strBackData6s;
//
//    @ViewInject(id = R.id.strBackData6)
//    private EditText strBackData6;
//
//    @ViewInject(id = R.id.strBackData7s)
//    private RelativeLayout strBackData7s;
//
//    @ViewInject(id = R.id.strBackData7)
//    private EditText strBackData7;

    @ViewInject(id = R.id.btn_ok)
    private Button btn_ok;


    private BaseFragment curentfragment;

    private Integer index;

    private RemoteControlActivity activity;

    private CallBack dataCallback;

    private InputMethodManager imm;

    private DialogLoading loading;


    public static ReadDataFragment getInstance() {
        ReadDataFragment fragment = new ReadDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (RemoteControlActivity) context;
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        loading = new DialogLoading(activity);
        initCallBack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readdata, container, false);
        fieldView(view);
        initView(view);
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        backBtn.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        meterAddrs.setOnClickListener(this);
        meterAddr.setText("");
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
            case R.id.btn_ok:
                if (CheckUtil.isNull(meterAddr.getText())) {
                    Toast.makeText(activity, "请输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearData();
                loadData(meterAddr.getText().toString());
                break;
        }
    }

    public void loadData(String meteraddr) {
        try {
            String userId = "01";
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
                jsobj.put("userid", userId);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}"; 
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    map.put("ngMeterGas",URLEncoder.encode(jsobj.toString(),HTTP.UTF_8));
            loading.show();
            setDialogLabel("开始抄表请等待");

            /**
             * 开始抄表请等待
             */
            SystemAPI.meter_read(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("抄表完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();


//                        readmeterModel dto = new readmeterModel();
//                        dto.of(jsonObject);
//                        SetDate(dto);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            //刷界面之
//            private void SetDate(readmeterModel parmedata) {
//                strBackData.setText(parmedata.getStrBackData());
//                strBackData1.setText(parmedata.getStrBackData1());
//                strBackData2.setText(parmedata.getStrBackData2());
//                strBackData3.setText(parmedata.getStrBackData3());
////                strBackData4.setText(parmedata.getStrBackData5());
//                strBackData5.setText(parmedata.getStrBackData6());
//                strBackData6.setText(parmedata.getStrBackData7());
//                strBackData7.setText(parmedata.getStrBackData8());
//            }
        };
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    private void clearData() {
//        strBackData.setText("");
//        strBackData1.setText("");
//        strBackData2.setText("");
//        strBackData3.setText("");
////        strBackData4.setText("");
//        strBackData5.setText("");
//        strBackData6.setText("");
//        strBackData7.setText("");
    }

}
