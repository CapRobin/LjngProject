package com.zfg.org.myexample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.MyLocationAdapter2;
import com.zfg.org.myexample.adapter.RechargeAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.dto.RechargeItemModel;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.MethodUtil;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-12.  zhdl 充值输入金额界面
 */
public class RechargeActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.recylerview)
    private RecyclerView recyclerView;
    //充值金额

    private RechargeAdapter adapter;

    @ViewInject(id = R.id.tvPay)
    private TextView tvPay;

    @ViewInject(id = R.id.meter_addr)
    private EditText meterAddr;

    @ViewInject(id = R.id.meter_addrs)
    private LinearLayout meterAddrs;

    @ViewInject(id = R.id.recharge_money)
    private EditText rechargemoney;

    @ViewInject(id = R.id.select_addr)
    private ImageView select_addr;

    private CallBack callBack;

////////////~~~~~~~~~~~~~~~~~~~~~~

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;

    private Preference preference;
    private DialogLoading loading;
    private CallBack loginCallback;

    private RechargeActivity activity;
    private MeterInfoDialog meterInfoDialog;


    private double money;
    @ViewInject(id = R.id.cxbhHideView)
    private LinearLayout cxbhHideView;
    @ViewInject(id = R.id.cxbhInnerView)
    private ListView cxbhInnerView;
    private List<MeterInfo> meterinfos;
    private List<MeterInfoCheckModel> showListData2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        activity = (RechargeActivity) context;
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);

        pageTitle.setText("充值缴费");
        settingBtn.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        showListData2 = new ArrayList<MeterInfoCheckModel>();
        recyclerView.setAdapter(adapter = new RechargeAdapter());
        adapter.replaceAll(getData());
        adapter.setOnRecyclerItemClicklistener(new RechargeAdapter.OnRecyclerItemClicklistener() {
            @Override
            public void onItemClick(View view, String data) {
//                Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                rechargemoney.setText(data);
            }
        });
        initActivity();
        setData();

//        replaceFragment("readdata", ReadDataWaterFragment.getInstance(), false);
    }

    // 显示充值金额
    public ArrayList<RechargeItemModel> getData() {
        ArrayList<RechargeItemModel> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            String count = i*10 + ".00";
            list.add(new RechargeItemModel(RechargeItemModel.ONE, count));
        }
//        list.add(new RechargeItemModel(RechargeItemModel.TWO, null));
        return list;
    }

    private void initActivity(){
        backHome.setOnClickListener(this);
        tvPay.setOnClickListener(this);
//        meterAddrs.setOnClickListener(this);
        meterAddr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    MethodUtil.animateClose(cxbhHideView);
                }
            }
        });
        rechargemoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    MethodUtil.animateClose(cxbhHideView);
                }
            }
        });
        select_addr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backHome:
                finish();
                break;
//            case R.id.meter_addrs:
            case R.id.select_addr:
//                if (meterInfoDialog == null) {
//                    meterInfoDialog = new MeterInfoDialog(activity);
//                    meterInfoDialog.setCall(new MeterInfoDialog.CallBack() {
//                        @Override
//                        public void callBack(List<MeterInfoCheckModel> data) {
//                            for (MeterInfoCheckModel item : data) {
//                                if (item.isCheck) {
//                                    meterAddr.setText(item.value);
//                                }
//                            }
//                        }
//                    });
//                }
//                meterInfoDialog.show();

                if(meterinfos.size() > 0){
                    closeInputMethod();
                    popViewisShow();
                }else {
                    setToast("获取表号失败");
                }

                break;
            case R.id.tvPay:
                if (meterAddr.getText().length() == 0){
                    Toast.makeText(this, "请输入表地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rechargemoney.getText().toString().length()==0){
                    Toast.makeText(this, "请输入选择或输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("meteraddr",
                        CommonUtil.AddZeros(meterAddr.getText().toString()));
                bundle.putDouble("money", Double.valueOf(rechargemoney.getText().toString()));//石涛改成1分钱
//                bundle.putDouble("money", Double.valueOf("0.01"));
                startActivity(bundle, MyPayActivity.class);
                break;
        }
    }






    /**
     * Describe：控制视图是否显示
     * Params:
     * Date：2018-03-30 13:31:39
     */
    private void popViewisShow() {
        if (View.GONE == cxbhHideView.getVisibility()) {
            meterAddr.clearFocus();
            rechargemoney.clearFocus();
            //打开第二个View
            int getHeight = MethodUtil.dip2px(this, meterinfos.size() * 60);
            MethodUtil.animateOpen(cxbhHideView, getHeight,400);
        } else {
            //关闭第二个View
            MethodUtil.animateClose(cxbhHideView);
        }
    }





    /**
     * Describe：构造View的数据
     * Params:
     * Date：2018-03-30 12:00:22
     */
    private void setData() {
        //统一(登录默认更新数据)加载第二个列表数据(从数据库获取数据并组装成List)
        MeterInfoBo meterbo = new MeterInfoBo(context);
        meterinfos = meterbo.listAllData();
        for (int i = 0; i < meterinfos.size(); i++) {
            if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                MeterInfoCheckModel model = new MeterInfoCheckModel(String.valueOf(i), meterinfos.get(i).getComm_address(), meterinfos.get(i).getMetertype(), false);
                showListData2.add(model);
            }
        }
        showView2(showListData2);
    }

    /**
     * Describe：装载数据
     * Params:
     * Date：2018-03-30 12:00:40
     */
    public void showView2(List<MeterInfoCheckModel> list) {
        MyLocationAdapter2 locationAdapter = new MyLocationAdapter2(this, list);
        //设置ListView线条的颜色
        cxbhInnerView.setDivider(new ColorDrawable(Color.GRAY));
        cxbhInnerView.setDividerHeight(1);
        cxbhInnerView.setAdapter(locationAdapter);
        cxbhInnerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temStr = showListData2.get(i).value;
                meterAddr.setText(temStr);
                popViewisShow();
            }
        });

    }










    public interface CallBack {
        void callBack(int model);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
