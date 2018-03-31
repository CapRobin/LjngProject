package com.zfg.org.myexample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zfg.org.myexample.dialog.adapter.CheckAdapter;
import com.zfg.org.myexample.model.CheckModel;
import com.zfg.org.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-31.
 */

public class CommandTypeDialog extends Dialog implements AdapterView.OnItemClickListener {

    private TextView titleView;
    private ListView dataList;
    private Button okBtn;

    private Context context;
    private CommandTypeDialog.CallBack callBack;
    protected List<CheckModel> data;
    private CheckAdapter adapter;


    public CommandTypeDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        setContentView(R.layout.dialog_commandlist_check);
        this.setCanceledOnTouchOutside(true);
        data = new ArrayList<CheckModel>();
        initData(data);
        initView();
    }

    private void initView() {
        adapter = new CheckAdapter(context, data);
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

    private void initData(List<CheckModel> data) {
//       CheckModel model = new CheckModel("1", "抄读水表当前使用量", false);
//        data.add(model);
//        model = new CheckModel("2", "抄读水表结算周期数据", false);
//        data.add(model);
//        model = new CheckModel("3", "抄读水表实时状态查询", false);
//        data.add(model);
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

    public void setCall(CommandTypeDialog.CallBack callBack) {
        this.callBack = callBack;
    }

}
