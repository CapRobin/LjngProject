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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.HisInfoActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.activity.HisInfoActivity_bak;
import com.zfg.org.myexample.adapter.ReadDataGasHisAdapter;
import com.zfg.org.myexample.adapter.ReadDataWaterHisAdapter;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.dialog.DayDialog;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dialog.TimeDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.model.ReadDataHisGasItemModel;
import com.zfg.org.myexample.model.ReadDataHisWaterItemModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016-10-14.
 */
public class ReadDataHisFragment extends BaseFragment implements View.OnClickListener {


    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @ViewInject(id = R.id.btn_ok)
    private Button query;

    @ViewInject(id = R.id.meterAddrs)
    private LinearLayout meterAddrs;

    @ViewInject(id = R.id.select_addr)
    private EditText select_addr;

    @ViewInject(id = R.id.meterAddr)
    private EditText meterAddr;

    @ViewInject(id = R.id.day)
    private LinearLayout day;

    @ViewInject(id = R.id.eday)
    private LinearLayout eday;



    @ViewInject(id = R.id.add_entry_day)
    private TextView entryDay;


    //结束日期
    @ViewInject(id = R.id.eadd_entry_day)
    private TextView eentryDay;

    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;

    private HisInfoActivity_bak activity;

    private Date selectDate;
    private Date selectDate1;

    private DayDialog dayDialog;
    private DayDialog dayDialog2;
    private TimeDialog timeDialog;
    private AlertDialogZfg alert;
    private DecimalFormat format;
    private DecimalFormat totalFormat;
    private DialogLoading loading;
    private InputMethodManager imm;
    private String meteraddrs;

    private List<ReadDataHisWaterItemModel> Waterdata;
    private List<ReadDataHisGasItemModel> Gasdata;
    /*適配器*/
    private ReadDataWaterHisAdapter wateradapter;
    private ReadDataGasHisAdapter gasadaper;

    private HttpServiceUtil.CallBack dataCallback;

    private MeterInfoDialog meterInfoDialog;

    public static ReadDataHisFragment getInstance() {
        ReadDataHisFragment fragment = new ReadDataHisFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readdata_his, container, false);
        activity = (HisInfoActivity_bak) context;
        loading = new DialogLoading(activity);
        fieldView(view);
        initView(view);
        initData();
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initCallBack();
        wateradapter = new ReadDataWaterHisAdapter(context,Waterdata);
        gasadaper = new ReadDataGasHisAdapter(context,Gasdata);


//
//        aTextViewAdapter = new AutoTextViewAdapter<String>(this,R.layout.list_item,0,str);
//        meterAddr.setAdapter();

//      列表适配器绑定

//      单击项目
//        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                if (!scaning) {
////                    loading.show();
////                    connectDevice(data.get(position).getDevice());
////                }
//            }
//        });
        return view;
    }

    //  初始化功能
    private void initData(){
        Waterdata = new ArrayList<ReadDataHisWaterItemModel>();
        Gasdata = new ArrayList<ReadDataHisGasItemModel>();
    }

    private void initView(View view) {
        query.setOnClickListener(this);
        day.setOnClickListener(this);
        eday.setOnClickListener(this);
        entryDay.setText(DateUtil.parseToString(selectDate, DateUtil.yyyymmdd));
        eentryDay.setText(DateUtil.parseToString(selectDate, DateUtil.yyyymmdd));
        meterAddrs.setOnClickListener(this);
        select_addr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
//                dismiss();
                break;
//            case R.id.meterAddrs:
            case R.id.select_addr:
                if (meterInfoDialog == null) {
                    meterInfoDialog = new MeterInfoDialog(activity);
                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
                        @Override
                        public void callBack(List<MeterInfoCheckModel> data) {
                            for (MeterInfoCheckModel item : data) {
                                if (item.isCheck) {
                                    meterAddr.setText(item.value);
                                }
                            }
                        }
                    });
                }
                meterInfoDialog.show();
                break;
            case R.id.day:
                openDayDialog();
                break;
            case R.id.eday:
                openDayDialog2();
                break;
            case R.id.btn_ok:
                if (CheckUtil.isNull(meterAddr.getText().toString())) {
                    Toast.makeText(activity, "请输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckUtil.isNull(entryDay.getText().toString())) {
                    Toast.makeText(activity, "请选择查询开始日期！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckUtil.isNull(eentryDay.getText().toString())) {
                    Toast.makeText(activity, "请选择查询结束日期！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearData();
//                adjustTimeData(meteraddrs);
//              开关阀
//                swichData(meterAddr.getText().toString());
//              充值
//                rechargeData(meterAddr.getText().toString());
//              历史记录查询
                loadData(CommonUtil.AddZeros(meterAddr.getText().toString()));
                break;
        }
    }

//{"adjustTimeFlag":"1","strBackFlag":""}
    private void adjustTimeData(String meteraddr) {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
//                jsobj.put("userid", userId);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterWater", jsobj.toString());

            loading.show();
            setDialogLabel("开始抄表请等待");
            SystemAPI.adjustTime(map, dataCallback);
//            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadData(String meteraddr) {
        try {
//            String userId = ContantsUtil.curUser.getId().toString();
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meteraddr);
//                jsobj.put("userid", userId);
                jsobj.put("bdate", entryDay.getText().toString());
                jsobj.put("edate", eentryDay.getText().toString());
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }

//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());

            loading.show();
            setDialogLabel("开始抄表请等待");
//            SystemAPI.query_recharge_his(map, dataCallback);
            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//用水量记录
//{"consuList":[{"DATE_TIME":"2016-10-15 17:59:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:27:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:25:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:24:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:13:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:11:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:09:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:07:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:05:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:03:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 17:01:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 16:59:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2},{"DATE_TIME":"2016-10-15 16:58:00","CONSU":24.7,"STMTCONSU":6.2,"RECHARGECOUNTS":2}],"custAddr":"[000000000040]","custId":"[000000000040]","custName":"[000000000040]","custPhone":"[null]","strBackFlag":"1","strMeterAddr":"000000000040"}
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
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")){
                            if  (jsonObject.getString("meterType").equals("Gas")) {
                                //解析简单数组
                                JSONArray pages = jsonObject.getJSONArray("consuList");
                                for (int i = 0; i < pages.length(); i++) {
                                    ReadDataHisGasItemModel dto = new ReadDataHisGasItemModel();
                                    dto.of(pages.getJSONObject(i));
                                    Gasdata.add(dto);
                                }
                                itemlist.setAdapter(gasadaper);
                                gasadaper.notifyDataSetChanged();
                            } else if  (jsonObject.getString("meterType").equals("Water")){
                                JSONArray pages = jsonObject.getJSONArray("consuList");
                                for (int i = 0; i < pages.length(); i++) {
                                    ReadDataHisWaterItemModel dto = new ReadDataHisWaterItemModel();
                                    dto.of(pages.getJSONObject(i));
                                    Waterdata.add(dto);
                                }
                                itemlist.setAdapter(wateradapter);
                                wateradapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        };
    }

    private void openDayDialog() {
        if (dayDialog == null) {
            dayDialog = new DayDialog(activity, "日期选择");
            dayDialog.setCallBack(new DayDialog.CallBack() {
                @Override
                public boolean callBack(int year, int month, int day) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month - 1);
                    calendar.set(Calendar.DATE, day);
                    selectDate = calendar.getTime();
                    if (selectDate.compareTo(new Date()) > 0) {
                        Toast.makeText(context, R.string.toast_time_after,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    entryDay.setText(year + "-" + month + "-" + day);
                    return true;
                }
            });
        }
        dayDialog.show();
    }

    private void openDayDialog2() {
        if (dayDialog2 == null) {
            dayDialog2 = new DayDialog(activity, "日期选择");
            dayDialog2.setCallBack(new DayDialog.CallBack() {
                @Override
                public boolean callBack(int year, int month, int day) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month - 1);
                    calendar.set(Calendar.DATE, day);
                    selectDate = calendar.getTime();
                    if (selectDate.compareTo(new Date()) > 0) {
                        Toast.makeText(context, R.string.toast_time_after,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    eentryDay.setText(year + "-" + month + "-" + day);
                    return true;
                }
            });
        }
        dayDialog2.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //查询抄表记录
    private void query_readdata_his() {
        // 提交到服务器
        try {
            if (meterAddr.getText().toString() == "") {

            }
//          表号
            String Addr = meterAddr.getText().toString();
//          操作员ID
            String operationId = "1";
//          起始日期
            String bdate = entryDay.getText().toString();
//          结束日期
            String edate = eentryDay.getText().toString();

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", Addr);
//                jsobj.put("operationId", operationId);
//              起始日期
                jsobj.put("bdate", entryDay.getText().toString());
//              结束日期
                jsobj.put("edate", eentryDay.getText().toString());

            } catch (JSONException ex) {
                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterWater", jsobj.toString());
            loading.show();
            setDialogLabel("正在查询数抄表记录......");
//          发起查询请求
            SystemAPI.query_readdata_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(String tag, Fragment tempFragment, boolean isAdd) {
        FragmentTransaction tran = getChildFragmentManager().beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

    //  变更标签内容
    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    private void clearData() {
        Waterdata.clear();
        Gasdata.clear();
        gasadaper.notifyDataSetChanged();
        wateradapter.notifyDataSetChanged();
    }

    class MyThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("jsparam", HttpServiceUtil.sessionId);
//			String result = HttpServiceUtil.post(ContantsUtil.URL_Storage_View, map);
//			if (!CheckUtil.isNull(result)) {
//					handler.sendEmptyMessage(3);
//			}
//			Config.loadUserSet(activity);
        }
    };
}
