package com.zfg.org.myexample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.HisInfoActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.SwichPowerHisAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.dialog.DayDialog;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dialog.TimeDialog;
import com.zfg.org.myexample.dto.CheckModel;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.model.SwichPowerHisModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.R;
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
public class SwichPowerHisFragment extends BaseFragment implements View.OnClickListener {


    //        数据输入框和确认按钮行

    @ViewInject(id = R.id.meterAddr)
    private EditText meterAddr;

    @ViewInject(id = R.id.select_addr)
    private EditText select_addr;

    @ViewInject(id = R.id.day)
    private LinearLayout day;

    @ViewInject(id = R.id.eday)
    private LinearLayout eday;

    //起始日期

    @ViewInject(id = R.id.add_entry_day)
    private TextView entryDay;

    //结束日期
    @ViewInject(id = R.id.eadd_entry_day)
    private TextView eentryDay;


    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;

    @ViewInject(id = R.id.meterAddrs)
    private LinearLayout meterAddrs;

    @ViewInject(id = R.id.btn_ok)
    private Button query;


    private HisInfoActivity activity;

    /*適配器*/
    private SwichPowerHisAdapter adapter;

    private Date selectDate;

    private DialogLoading loading;
    private DayDialog dayDialog;
    private DayDialog dayDialog2;
    private AlertDialogZfg alert;
    private DecimalFormat format;
    private DecimalFormat totalFormat;
    private List<SwichPowerHisModel> data;
    private Handler hander;
    private InputMethodManager imm;
    private HttpServiceUtil.CallBack dataCallback;
    private String meteraddrs;

    private MeterInfoDialog meterInfoDialog;

    //查询最大时限
    private static final long QUERY_PERIOD = 10000;

    public static SwichPowerHisFragment getInstance() {
        SwichPowerHisFragment fragment = new SwichPowerHisFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swichpower_his, container, false);
        activity = (HisInfoActivity) context;
        loading = new DialogLoading(activity);
        fieldView(view);
        initView(view);
        initData();
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initCallBack();
        adapter = new SwichPowerHisAdapter(context, data);
//      列表适配器绑定
        itemlist.setAdapter(adapter);
//      单击项目
        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (!scaning) {
//                    loading.show();
//                    connectDevice(data.get(position).getDevice());
//                }
            }
        });
        return view;
    }

    //  初始化功能
    private void initData() {
        data = new ArrayList<SwichPowerHisModel>();
    }

    public void onRefresh() {
        // Prepare list view and initiate scanning
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        query.setOnClickListener(this);
        meterAddrs.setOnClickListener(this);
        select_addr.setOnClickListener(this);
        day.setOnClickListener(this);
        eday.setOnClickListener(this);
        entryDay.setText(DateUtil.parseToString(selectDate, DateUtil.yyyymmdd));
        eentryDay.setText(DateUtil.parseToString(selectDate, DateUtil.yyyymmdd));

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
                loadData(CommonUtil.AddZeros(meterAddr.getText().toString()));
                break;

        }
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
                    entryDay.setText(year + "-" + month + "-"
                            + day);
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
                    calendar.set(Calendar.MILLISECOND,0);
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
//          String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("开始查询开关阀记录请等待......");
            SystemAPI.query_swich_his(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("开关阀历史记录查询完成");
                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {
                            //解析简单数组
                            JSONArray pages = jsonObject.getJSONArray("msgResult");
                            for (int i = 0; i < pages.length(); i++) {
                                SwichPowerHisModel dto = new SwichPowerHisModel();
                                dto.of(pages.getJSONObject(i));
                                data.add(dto);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

//            //刷界面之
//            private void SetDate(readmeterModel parmedata) {
//
//            }
        };
    }

    //  变更标签内容
    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    private void clearData() {
        data.clear();
        adapter.notifyDataSetChanged();
    }
}
