package com.zfg.org.myexample;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.RechargeAdapter;
import com.zfg.org.myexample.dialog.MeterInfoDialog;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;
import com.zfg.org.myexample.dto.RechargeItemModel;
import com.zfg.org.myexample.fragment.ReadDataWaterFragment;
import com.zfg.org.myexample.dto.CheckModel;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-12.  zhdl 充值输入金额界面
 */
public class RechargeActivity  extends BasicActivity implements View.OnClickListener {

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
        recyclerView.setAdapter(adapter = new RechargeAdapter());
        adapter.replaceAll(getData());
        adapter.setOnRecyclerItemClicklistener(new RechargeAdapter.OnRecyclerItemClicklistener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                rechargemoney.setText(data);
            }
        });
        initActivity();

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
