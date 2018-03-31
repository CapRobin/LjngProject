package com.zfg.org.myexample.dialog;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.adapter.MeterInfoCheckAdapter;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.dto.CheckModel;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-25.
 */

public class MeterInfoDialog extends Dialog implements
        AdapterView.OnItemClickListener {

    private TextView titleView;
    private ListView dataList;
    private Button okBtn;

    private Context context;
    private MeterInfoDialog.CallBack callBack;
    protected List<MeterInfoCheckModel> data;
    private MeterInfoCheckAdapter adapter;


    private List<MeterInfo> meterinfos;

    public MeterInfoDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        setContentView(R.layout.dialog_meterlist_check);
        this.setCanceledOnTouchOutside(true);
        data = new ArrayList<MeterInfoCheckModel>();
        initData(data);
        initView();
    }

    private void initView() {
        adapter = new MeterInfoCheckAdapter(context, data);
        titleView = (TextView) findViewById(R.id.title);
        dataList = (ListView) findViewById(R.id.content_list);
        okBtn = (Button) findViewById(R.id.ok_btn);
        dataList.setOnItemClickListener(this);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (callBack != null) {
                    callBack.callBack(data);
                }
                dismiss();
            }
        });
        dataList.setAdapter(adapter);
    }
    public String name;
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            name = intent.getExtras().getString("meterType");
            Log.i("Recevier1", "接收到:"+name);
        }
    }
    private void initData(List<MeterInfoCheckModel> data) {
        MeterInfoBo meterbo = new MeterInfoBo(context);
        meterinfos = meterbo.listAllData();
        for (int i=0;i<meterinfos.size();i++){
            if (name == meterinfos.get(i).getMetertype()) {
                if (meterinfos.get(i).getComm_address().toString().length() > 6) {
//                    Log.e("TAG:", meterinfos.get(i).getMetertype());
                    MeterInfoCheckModel model = new MeterInfoCheckModel(String.valueOf(i), meterinfos.get(i).getComm_address(), meterinfos.get(i).getMetertype(), false);
                    data.add(model);
                }
            }else {
                if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                    Log.d("TAG:", meterinfos.get(i).getMetertype());
                    MeterInfoCheckModel model = new MeterInfoCheckModel(String.valueOf(i), meterinfos.get(i).getComm_address(), meterinfos.get(i).getMetertype(), false);
                    data.add(model);
                }
            }
        }
    }

    public interface CallBack {
        void callBack(List<MeterInfoCheckModel> data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        for (int i=0;i<data.size();i++){
            data.get(i).isCheck = false;
        }
        data.get(position).isCheck = !data.get(position).isCheck;
        adapter.notifyDataSetChanged();
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    public void setCall(MeterInfoDialog.CallBack callBack) {
        this.callBack = callBack;
    }

}

