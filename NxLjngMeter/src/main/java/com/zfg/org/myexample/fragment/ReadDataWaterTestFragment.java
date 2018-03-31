package com.zfg.org.myexample.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RemoteControlWaterTestActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.MeterInfoAdapter;
import com.zfg.org.myexample.adapter.ReadDataAdapter;
import com.zfg.org.myexample.dialog.ListDialog;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.model.ReadDataItemModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.view.Option;
import com.zfg.org.myexample.utils.view.OptionsMenuManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zfg
 */
public class ReadDataWaterTestFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    //        数据输入框和确认按钮行
    @ViewInject(id = R.id.meterAddrs)
    private RelativeLayout meterAddrs;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.btn_options)
    private ImageButton btnOptions;

    private String meterType;

    @ViewInject(id = R.id.btn_ok)
    private Button btn_ok;

    @ViewInject(id = R.id.btn_okTest)
    private Button btn_okTest;

    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;


    private BaseFragment curentfragment;

    private Integer index;

    private RemoteControlWaterTestActivity activity;

    private CallBack dataCallback;

    private InputMethodManager imm;

    private DialogLoading loading;


    private List<ReadDataItemModel> listdata;

    private ReadDataAdapter listadapter;
    //表数据
    private MeterInfoDialog meterInfoDialog;

//    private CommandTypeDialog commandTypeDialog;

    private OptionsMenuManager optionsMenuManager;
    private List<Option> options = new ArrayList<>();
    private Option currentOption;

    private String dataType;
    private String varType;
    private String searchTime;

    // 性别选项的对话框
    private Dialog dialog;

    //自动完成适配器
    private MeterInfoAdapter adapter;

    public static ReadDataWaterTestFragment getInstance() {
        ReadDataWaterTestFragment fragment = new ReadDataWaterTestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readdatatest, container, false);
        activity = (RemoteControlWaterTestActivity) context;
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        fieldView(view);
        initView(view);
        loading = new DialogLoading(activity);
        initData();
        listadapter = new ReadDataAdapter(context,listdata);
//      列表适配器绑定
        itemlist.setAdapter(listadapter);
        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (!scaning) {
//                    loading.show();
//                    connectDevice(data.get(position).getDevice());
//                }
            }
        });
        initCallBack();
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    //  初始化功能
    private void initData() {
        listdata = new ArrayList<ReadDataItemModel>();
        meterType = "";
    }

    private void initView(View view) {
        backBtn.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_okTest.setOnClickListener(this);
        meterAddrs.setOnClickListener(this);
        btnOptions.setOnClickListener(this);
    }

    public void replaceFragment(String tag, Fragment tempFragment, boolean isAdd) {
        FragmentTransaction tran = getChildFragmentManager().beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

    private void showMenuDialog() {
        dialog = new ListDialog(activity);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_two_menu);
//        TextView dialog_first_menu = (TextView) window.findViewById(R.id.dialog_first_menu);
//        dialog_first_menu.setText("抄读水表当前用水信息");
//        TextView dialog_second_menu = (TextView) window.findViewById(R.id.dialog_second_menu);
//        dialog_second_menu.setText("抄读水表当前价格信息");
//        TextView dialog_three_menu = (TextView) window.findViewById(R.id.dialog_three_menu);
//        dialog_three_menu.setText("抄读水表实时状态");
        TextView dialog_four_menu = (TextView) window.findViewById(R.id.dialog_four_menu);
        dialog_four_menu.setText("抄读气表当前用气信息");
        TextView dialog_five_menu = (TextView) window.findViewById(R.id.dialog_five_menu);
        dialog_five_menu.setText("抄读气表当前价格信息");
        TextView dialog_six_menu = (TextView) window.findViewById(R.id.dialog_six_menu);
        dialog_six_menu.setText("抄读气表实时状态");
//        TextView dialog_seven_menu = (TextView) window.findViewById(R.id.dialog_seven_menu);
//        dialog_seven_menu.setText("抄读表中的数据");
        TextView dialog_eight_menu = (TextView) window.findViewById(R.id.dialog_eight_menu);
        dialog_eight_menu.setText("抄读集中器中数据");
//        TextView dialog_nine_menu = (TextView) window.findViewById(R.id.dialog_nine_menu);
//        dialog_nine_menu.setText("抄读日冻结数据");
//        TextView dialog_ten_menu = (TextView) window.findViewById(R.id.dialog_ten_menu);
//        dialog_ten_menu.setText("抄读月冻结数据");
//        TextView dialog_eleven_menu = (TextView) window.findViewById(R.id.dialog_eleven_menu);
//        dialog_eleven_menu.setText("抄读结算日冻结数据");

        if (meterType.equals("")){
//            dialog_first_menu.setVisibility(View.VISIBLE);
//            dialog_second_menu.setVisibility(View.VISIBLE);
//            dialog_three_menu.setVisibility(View.VISIBLE);
            dialog_four_menu.setVisibility(View.VISIBLE);
            dialog_five_menu.setVisibility(View.VISIBLE);
            dialog_six_menu.setVisibility(View.VISIBLE);
        } else  if (meterType.equals("水表")){
//            dialog_first_menu.setVisibility(View.VISIBLE);
//            dialog_second_menu.setVisibility(View.VISIBLE);
//            dialog_three_menu.setVisibility(View.VISIBLE);
//            dialog_four_menu.setVisibility(View.GONE);
//            dialog_five_menu.setVisibility(View.GONE);
//            dialog_six_menu.setVisibility(View.GONE);
        } else if (meterType.equals("气表")){
//            dialog_first_menu.setVisibility(View.GONE);
//            dialog_second_menu.setVisibility(View.GONE);
//            dialog_three_menu.setVisibility(View.GONE);
//            dialog_four_menu.setVisibility(View.VISIBLE);
//            dialog_five_menu.setVisibility(View.VISIBLE);
//            dialog_six_menu.setVisibility(View.VISIBLE);
        }

////        dialog_first_menu.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dataType = "5";
////                varType = "201";
////                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
////                searchTime =  df.format(new Date());
////                dialog.dismiss();
//            }
//        });
//        dialog_second_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "5";
//                varType = "202";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                searchTime =  df.format(new Date());
//                dialog.dismiss();
//            }
//        });
//        dialog_three_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "5";
//                varType = "203";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                searchTime =  df.format(new Date());
//                dialog.dismiss();
//            }
//        });

        dialog_four_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "201";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                dialog.dismiss();
            }
        });

        dialog_five_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "202";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                dialog.dismiss();
            }
        });

        dialog_six_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "203";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                dialog.dismiss();
            }
        });
//        dialog_seven_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "0";
//                varType = "0";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                searchTime =  df.format(new Date());
//                dialog.dismiss();
//            }
//        });
        dialog_eight_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "0";
                varType = "1";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                dialog.dismiss();
            }
        });
//        dialog_nine_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "1";
//                varType = "0";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
//                searchTime =  df.format(new Date());
//                dialog.dismiss();
//            }
//        });
//        dialog_ten_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "2";
//                varType = "0";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                searchTime =  df.format(new Date());
//                dialog.dismiss();
//            }
//        });
//        dialog_eleven_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataType = "4";
//                varType = "0";
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
//                searchTime =  df.format(new Date())+"-01";
//                dialog.dismiss();
//            }
//        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                activity.finish();
                break;
            case R.id.btn_options:
                if (activity.hasWindowFocus() && imm.isActive()) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), 0);
                }
                showMenuDialog();
                break;
            case R.id.meterAddrs:
                if (meterInfoDialog == null) {
                    meterInfoDialog = new MeterInfoDialog(activity);
                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
                        @Override
                        public void callBack(List<MeterInfoCheckModel> data) {
                            for (MeterInfoCheckModel item : data) {
                                if (item.isCheck) {
                                    meterAddr.setText(item.value);
                                    meterType = item.metertype;
                                    btnOptions.callOnClick();
                                }
                            }
                        }
                    });
                }
                meterInfoDialog.show();
                break;
            case R.id.btn_ok:
                if (CheckUtil.isNull(meterAddr.getText())) {
                    Toast.makeText(activity, "请输入表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckUtil.isNull(dataType)) {
                    Toast.makeText(activity, "请先选择抄表项！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearData();
//                loadData("000000000040");
                loadData(CommonUtil.AddZeros(meterAddr.getText().toString()));
                break;
            case R.id.btn_okTest :
                if (CheckUtil.isNull(meterAddr.getText())) {
                    Toast.makeText(activity, "请输入表地址Test！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckUtil.isNull(dataType)) {
                    Toast.makeText(activity, "请先选择抄表项！Test", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearData();
                loadData(CommonUtil.AddZeros(meterAddr.getText().toString()));
                break;
        }
    }

    //  抄表新接口
    public void loadData(String meteraddr) {
        try {
                JSONObject jsobj = new JSONObject();
                try {
                    jsobj.put("meterAddr", meteraddr);
                    jsobj.put("dataType", dataType);
                    jsobj.put("varType", varType);
                    jsobj.put("searchTime",searchTime);
                } catch (JSONException ex) {
                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ngMeter", jsobj.toString());
                loading.show();
                setDialogLabel("开始抄表请等待...");
                SystemAPI.meter_realTimeReading(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  抄表老接口
//    public void loadData(String meteraddr) {
//        try {
//                JSONObject jsobj = new JSONObject();
//                try {
//                    jsobj.put("meterAddr", meteraddr);
//                } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
//                }
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("ngMeter", jsobj.toString());
//                loading.show();
//                setDialogLabel("开始抄表请等待...");
//                SystemAPI.meter_readmeter(map, dataCallback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void initCallBack() {
        dataCallback = new CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("抄表完成");
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {

                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {
                            //解析简单数组
                            JSONArray pages = jsonObject.getJSONArray("dataList");
                            for (int i = 0; i < pages.length(); i++) {
                                ReadDataItemModel dto = new ReadDataItemModel();
                                dto.of(pages.getJSONArray(i));
                                listdata.add(dto);
                            }
                            listadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context,"数据返回错误，请重新操作",Toast.LENGTH_SHORT).show();
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

    private void clearData() {
        listdata.clear();
        listadapter.notifyDataSetChanged();
    }

//    private void setOption(Option option){
//        currentOption = option;
//        switch (option.getPropertyType()){
//            case PROPERTY_READ_CUR_WATER_INFO:
//                break;
//            case PROPERTY_READ_CUR_GAS_INFO:
//                break;
//            case PROPERTY_READ_CUR_WATER_PRICE_INFO:
//                break;
//            case PROPERTY_READ_CUR_GAS_PRICE_INFO:
//                break;
//        }
//    }

//    @OnClick(R.id.btn_options)
//    public void onOptionsClick() {
//        optionsMenuManager.toggleContextMenuFromView(options, btnOptions, new OptionsSelectAdapter.OptionsOnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position) {
//                dismissMenu();
//                setOption(options.get(position));
//            }
//        });
//    }

//    private void dismissMenu() {
//        if (optionsMenuManager.getOptionsMenu() != null) {
//            optionsMenuManager.toggleContextMenuFromView(null, null, null);
//        }
//    }



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (optionsMenuManager.getOptionsMenu()!=null){
//            dismissMenu();
//            return false;
//        }
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()){
//            case R.id.menu_cur_water_info:
//                    Toast.makeText(context,"测试",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.menu_water_price_info:
//
//                break;
//            case R.id.menu_real_time_water_status:
//
//                break;
//            case R.id.menu_cur_gas_info:
//
//                break;
//            case R.id.menu_read_cur_gas_price_info:
//
//                break;
//            case R.id.menu_read_real_time_gas_status:
//
//                break;
//            case R.id.menu_read_data_in_concentrator:
//
//                break;
//            case R.id.menu_read_data_in_meter:
//
//                break;
//            case R.id.menu_read_data_freezing_data:
//
//                break;
//            case R.id.menu_read_data_freezing_month_data:
//
//                break;
//            case R.id.menu_read_freezing_settlement_data:
//
//                break;
//        }
//        return false;
//    }


}
