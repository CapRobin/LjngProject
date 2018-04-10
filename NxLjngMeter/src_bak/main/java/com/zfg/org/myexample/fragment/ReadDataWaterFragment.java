package com.zfg.org.myexample.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.CommandTypePopuwindow;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.RemoteControlActivity;
import com.zfg.org.myexample.RemoteControlWaterActivity;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.MeterInfoAdapter;
import com.zfg.org.myexample.adapter.OptionsSelectAdapter;
import com.zfg.org.myexample.adapter.ReadDataAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.CommandTypeDialog;
import com.zfg.org.myexample.dialog.ListDialog;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.dto.readmeterModel;

import com.zfg.org.myexample.model.CheckModel;
import com.zfg.org.myexample.model.ReadDataItemModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.view.Option;
import com.zfg.org.myexample.utils.view.OptionsMenuManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.OnClick;

import static android.R.attr.data;
import static android.R.attr.foreground;
import static android.R.layout.simple_dropdown_item_1line;

/**
 * @author zfg
 */
public class ReadDataWaterFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    //        数据输入框和确认按钮行
    @ViewInject(id = R.id.meterAddrs)
    private RelativeLayout meterAddrs;

    @ViewInject(id = R.id.select_addr)
    private EditText select_addr;
    private String[] datas = {"选项1", "选项2", "选项3", "选项4", "选项5", "选项6", "选项7", "选项8", "选项9", "选项10","选项11", "选项12", "选项13", "选项14", "选项15", "选项16", "选项17", "选项18", "选项19", "选项20","选项21", "选项22", "选项23", "选项24", "选项25", "选项26", "选项27", "选项28", "选项29", "选项30"};
    protected CheckModel m1;
    @ViewInject(id = R.id.ok_btn_true)
    private Button ok_btn_true;
    private CommandTypePopuwindow.CallBack callBack1;
    protected List<CheckModel> checkdata;
    protected CheckAdapter checkAdapter;
    private CallBack callBack;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.btn_options)
    private EditText btnOptions;

    public String meterType;

    public Spinner meterSelectType;

    @ViewInject(id = R.id.textViewReadMeterItem)
    private TextView textViewReadMeterItem;//    @ViewInject(id = R.id.strBackDatas)

    @ViewInject(id = R.id.relativeMeterItem)
    private RelativeLayout relativeMeterItem;

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
//
//
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
//
//    @ViewInject(id = R.id.strBackData8s)
//    private RelativeLayout strBackData8s;
//
//    @ViewInject(id = R.id.strBackData8)
//    private EditText strBackData8;
//
//    @ViewInject(id = R.id.strBackData9s)
//    private RelativeLayout strBackData9s;
//
//    @ViewInject(id = R.id.strBackData9)
//    private EditText strBackData9;

    @ViewInject(id = R.id.btn_ok)
    private Button btn_ok;

    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;



    private BaseFragment curentfragment;

    private Integer index;

    private RemoteControlWaterActivity activity;

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

    public static ReadDataWaterFragment getInstance() {
        ReadDataWaterFragment fragment = new ReadDataWaterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_readdata, container, false);
        activity = (RemoteControlWaterActivity) context;
        select_addr = (EditText) view.findViewById(R.id.select_addr);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        meterSelectType = (Spinner) view.findViewById(R.id.meter_select_type);
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



//        checkdata=new ArrayList<CheckModel>();
//        for(int i=0;i<datas.length;i++){
//            m1=new CheckModel((i+1)+"","水表"+(i+1),false);
//            checkdata.add(m1);
//        }
    }

    private void initView(View view) {
        backBtn.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        meterAddrs.setOnClickListener(this);
        select_addr.setOnClickListener(this);
        btnOptions.setOnClickListener(this);
        textViewReadMeterItem.setOnClickListener(this);
        relativeMeterItem.setOnClickListener(this);

        //抄表类型选择设置
        meterSelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.white));    //设置颜色
                tv.setTextSize(15.0f);    //设置大小
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                meterType = (String) meterSelectType.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void replaceFragment(String tag, Fragment tempFragment, boolean isAdd) {
        FragmentTransaction tran = getChildFragmentManager().beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

//    R.id.dialog_first_menu
    private void testViewtext(int id){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        searchTime =  df.format(new Date());
        dialog.dismiss();
        Window window = dialog.getWindow();
        TextView dialog_Num_menu = (TextView) window.findViewById(id);
        String str1 = "";
        str1 = dialog_Num_menu.getText().toString();
        btnOptions.setText(str1.toCharArray(),0,str1.length());
    }
    private void showMenuDialog() {
        dialog = new ListDialog(activity);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_two_menu);
        TextView dialog_first_menu = (TextView) window.findViewById(R.id.dialog_first_menu);
        dialog_first_menu.setText("抄读水表当前用水信息");

        TextView dialog_second_menu = (TextView) window.findViewById(R.id.dialog_second_menu);
        dialog_second_menu.setText("抄读水表当前价格信息");

        TextView dialog_three_menu = (TextView) window.findViewById(R.id.dialog_three_menu);
        dialog_three_menu.setText("抄读水表实时状态");

        TextView dialog_four_menu = (TextView) window.findViewById(R.id.dialog_four_menu);
        dialog_four_menu.setText("抄读气表当前用气信息");

        TextView dialog_five_menu = (TextView) window.findViewById(R.id.dialog_five_menu);
        dialog_five_menu.setText("抄读气表当前价格信息");

        TextView dialog_six_menu = (TextView) window.findViewById(R.id.dialog_six_menu);
        dialog_six_menu.setText("抄读气表实时状态");

        TextView dialog_seven_menu = (TextView) window.findViewById(R.id.dialog_seven_menu);
//        dialog_seven_menu.setText("抄读集中器中数据");

        TextView dialog_eight_menu = (TextView) window.findViewById(R.id.dialog_eight_menu);
        dialog_eight_menu.setText("抄读电表当前用电信息");
        TextView dialog_nine_menu = (TextView) window.findViewById(R.id.dialog_nine_menu);
        dialog_nine_menu.setText("抄读电表当前价格信息");
        TextView dialog_ten_menu = (TextView) window.findViewById(R.id.dialog_ten_menu);
        dialog_ten_menu.setText("抄读电表实时状态");

        TextView dialog_eleven_menu = (TextView) window.findViewById(R.id.dialog_eleven_menu);
//        dialog_eleven_menu.setText("抄读结算日冻结数据");

        //003
        if (meterType.equals("电表")){
            dialog_eight_menu.setVisibility(View.VISIBLE);
            dialog_nine_menu.setVisibility(View.VISIBLE);
            dialog_ten_menu.setVisibility(View.VISIBLE);
        } else if (meterType.equals("水表")){//120
            dialog_first_menu.setVisibility(View.VISIBLE);
            dialog_second_menu.setVisibility(View.GONE);
            dialog_three_menu.setVisibility(View.GONE);
        } else if (meterType.equals("气表")){//103
            dialog_four_menu.setVisibility(View.VISIBLE);
            dialog_five_menu.setVisibility(View.VISIBLE);
            dialog_six_menu.setVisibility(View.VISIBLE);
//            meterType="水表";
        } else{
            dialog_eleven_menu.setVisibility(View.VISIBLE);
            dialog_seven_menu.setVisibility(View.VISIBLE);
            dialog_six_menu.setVisibility(View.VISIBLE);
        }
        dialog_first_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "201";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_first_menu);
            }
        });
        dialog_second_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "202";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_second_menu);
            }
        });
        dialog_three_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "203";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_three_menu);
            }
        });

        dialog_four_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "201";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_four_menu);
            }
        });


        dialog_five_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "202";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_five_menu);
            }
        });

        dialog_six_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "5";
                varType = "203";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_six_menu);
            }
        });
        dialog_seven_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "0";
                varType = "0";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_seven_menu);
            }
        });
        dialog_eight_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "0";
                varType = "1";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_eight_menu);
            }
        });
        dialog_nine_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "1";
                varType = "0";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_nine_menu);
            }
        });
        dialog_ten_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "2";
                varType = "0";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                searchTime =  df.format(new Date());
                testViewtext(R.id.dialog_ten_menu);
            }
        });
        dialog_eleven_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType = "4";
                varType = "0";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
                searchTime =  df.format(new Date())+"-01";
                testViewtext(R.id.dialog_eleven_menu);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                activity.finish();
                break;
            case R.id.btn_options:
            case R.id.textViewReadMeterItem:
                //选择抄表项
            case R.id.relativeMeterItem:
                if (activity.hasWindowFocus() && imm.isActive()) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), 0);
                }
                showMenuDialog();

//                if (commandTypeDialog == null) {
//                    commandTypeDialog = new CommandTypeDialog(activity);
//                    commandTypeDialog.setCall(new CommandTypeDialog.CallBack() {
//                        @Override
//                        public void callBack(List<CheckModel> data) {
//                            for (CheckModel item : data) {
//                                if (item.isCheck) {
//                                    if (item.value.equals("抄读水表当前用水信息")){
//                                        dataType = "5";
//                                        varType = "217";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读水表当前价格信息")){
//                                        dataType = "5";
//                                        varType = "";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读水表实时状态")){
//                                        dataType = "5";
//                                        varType = "203";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读气表当前用气信息")){
//                                        dataType = "5";
//                                        varType = "201";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读气表当前价格信息")){
//                                        dataType = "5";
//                                        varType = "202";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读气表实时状态")){
//                                        dataType = "5";
//                                        varType = "203";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读集中器中数据")){
//                                        dataType = "0";
//                                        varType = "0";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    }  else if (item.value.equals("抄读表中的数据")){
//                                        dataType = "0";
//                                        varType = "1";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    } else if (item.value.equals("抄读日冻结数据")){
//                                        dataType = "1";
//                                        varType = "0";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    }  else if (item.value.equals("抄读月冻结数据")){
//                                        dataType = "2";
//                                        varType = "0";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//                                        searchTime =  df.format(new Date());
//                                    }  else if (item.value.equals("抄读结算日冻结数据")){
//                                        dataType = "4";
//                                        varType = "0";
//                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
//                                        searchTime =  df.format(new Date())+"-01";
//                                    }
//                                }
//                            }
//                        }
//                    });
//                }
//                commandTypeDialog.show();
                break;
//            case R.id.meterAddrs:
            //点击选择表号
            case R.id.select_addr:
                Intent intent = new Intent(meterType);
                activity.sendBroadcast(intent);
                if (meterInfoDialog == null) {
                    meterInfoDialog = new MeterInfoDialog(activity);
                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
                        @Override
                        public void callBack(List<MeterInfoCheckModel> data) {
                            for (MeterInfoCheckModel item : data) {
                                if (item.isCheck) {
                                    meterAddr.setText(item.value);
                                    meterType = item.metertype;
//                                    btnOptions.callOnClick();  //选择表地址调用的抄表项
                                }
                            }
                        }
                    });
                }
                meterInfoDialog.show();
                break;
//            case R.id.select_addr:
//                actionAlertDialog();
//                break;
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

                //开始加载数据
                loadData(CommonUtil.AddZeros(meterAddr.getText().toString()));
                break;
        }
    }

    private  PopupWindow window;

    private void actionAlertDialog() {
//        checkAdapter = new CheckAdapter(context, fs);
        final View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow, null);
        ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
        ok_btn_true = (Button) popupView.findViewById(R.id.ok_btn_true);
        Log.i("list","listsize"+datas.length);
        lsvMore.setAdapter(new ArrayAdapter<String>(context, R.layout.my_simple_list_item_1, datas));

        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvItem = (TextView) popupView.findViewById(R.id.tvItem);
//                EditText meterAddr = (EditText) popupView.findViewById(R.id.meter_addr);
//                for (i = 0; i<datas.length; i++){
//                    fs.get(i).isCheck = false;
                    meterAddr.setText(""+datas[i]);
                    window.dismiss();
//                }
//                fs.get(i).isCheck = !fs.get(i).isCheck;
//                checkAdapter.notifyDataSetChanged();
            }
        });

        window = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.setFocusable(true);
                if (window.isShowing()){
                    window.dismiss();
                }else {

                }
            }
        });
//        ok_btn_true.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////              if (callBack1 != null) {
////                    callBack1.callBack(fs);
////                }
//                window.dismiss();
//            }
//        });
//        lsvMore.setAdapter(checkAdapter);
        window.setAnimationStyle(R.style.popup_window_anim);
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        getContext().getResources().getColor(R.color.black);
        window.setOutsideTouchable(true);
        window.update();
//        popupView.setTextColor(getResources().getColor(R.color.white));    //设置颜色
//        tvItem.setTextSize(12.0f);    //设置大小
//        tvItem.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
        // 以下拉的方式显示，并且可以设置显示的位置
//        window.showAsDropDown(select_addr, 0, 20);
        View rootView =  LayoutInflater.from(getContext()).inflate(R.layout.activity_remotecontrol,null);
        window.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }
//    public interface CallBack1 {
//        void callBack1(List<CheckModel> tr);
//    }

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

            /**
             * 开始查询数据
             */
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
