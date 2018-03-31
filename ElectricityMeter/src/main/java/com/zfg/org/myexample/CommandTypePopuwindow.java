package com.zfg.org.myexample;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zfg.org.myexample.dialog.CommandTypeDialog;
import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.model.CheckModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-14.
 */

public class CommandTypePopuwindow extends PopupWindow implements AdapterView.OnItemClickListener {
    private TextView titleView;
    private ListView dataList;
    private Button okBtn;

    private Context context;
    private CommandTypePopuwindow.CallBack callBack;
    protected List<CheckModel> data;
    private CheckAdapter adapter;
    private boolean canceledOnTouchOutside;


    public CommandTypePopuwindow(Context context) {
//        super(context, R.style.Dialog);
        this.context = context;
        setContentView(R.layout.popupwindow);
        this.setCanceledOnTouchOutside(true);
        data = new ArrayList<CheckModel>();
        initData(data);
        initView();
    }

    private void setContentView(int popupwindow) {

    }

    private void initView() {
        adapter = new CheckAdapter(context, data);
        titleView = (TextView) titleView.findViewById(R.id.title1);
        dataList = (ListView) titleView.findViewById(R.id.lsvMore);
        okBtn = (Button) titleView.findViewById(R.id.ok_btn_true);
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

    private void initData(List<CheckModel> data) {
      /*  CheckModel model = new CheckModel("1", "抄读水表当前使用量", false);
        data.add(model);
        model = new CheckModel("2", "抄读水表结算周期数据", false);
        data.add(model);
        model = new CheckModel("3", "抄读水表实时状态查询", false);
        data.add(model);*/
        CheckModel model = new CheckModel("4", "抄读气表当前使用量", false);
        data.add(model);
        model = new CheckModel("5", "抄读气表结算周期数据", false);
        data.add(model);
        model = new CheckModel("6", "抄读气表实时状态查询", false);
        data.add(model);
//        model = new CheckModel("4", "抄读热表当前使用量", false);
//        data.add(model);
//        model = new CheckModel("5", "抄读热表结算周期数据", false);
//        data.add(model);
//        model = new CheckModel("6", "抄读热表实时状态查询", false);
//        data.add(model);
        model = new CheckModel("7", "抄读集中器中数据", false);
        data.add(model);
        model = new CheckModel("8", "抄读表中的数据", false);
        data.add(model);
        model = new CheckModel("9", "抄读日冻结数据", false);
        data.add(model);
        model = new CheckModel("10", "抄读月冻结数据", false);
        data.add(model);
        model = new CheckModel("11", "抄读结算日冻结数据", false);
        data.add(model);
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public interface CallBack {
        void callBack(List<CheckModel> data);
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

    public void setCall(CommandTypePopuwindow.CallBack callBack) {
        this.callBack = callBack;
    }
}
