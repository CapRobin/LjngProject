package com.zfg.org.myexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.adapter.RcAdapterWholeChange;
import com.zfg.org.myexample.db.MeterInfoBo;
import com.zfg.org.myexample.db.dao.MeterInfo;
import com.zfg.org.myexample.model.MeterAllInfo;

import java.util.ArrayList;
import java.util.List;

public class TestActivity3 extends BasicActivity {

    private RecyclerView recyclerView;
    private List<MeterAllInfo> newList;
    private RcAdapterWholeChange recycleAdapter;
    private List<MeterInfo> meterinfos;
    private List<MeterAllInfo> mDatas;
    private EditText testEdit;
//    private RecyclerView dataList;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_demo_03);

        setView();
        initData();
        refreshUI();
        setListener();
    }

    private void setView() {
        testEdit = (EditText) findViewById(R.id.testEdit);
        recyclerView = (RecyclerView) findViewById(R.id.dataList);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.line_bg));
        recyclerView.addItemDecoration(divider);
    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        if (recycleAdapter == null) {
//            recycleAdapter = new RcAdapterWholeChange(this, list);
            recycleAdapter = new RcAdapterWholeChange(TestActivity3.this, newList);
            recyclerView.setAdapter(recycleAdapter);
        } else {
            recycleAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        mDatas = new ArrayList<MeterAllInfo>();
        newList = new ArrayList<MeterAllInfo>();
        MeterInfoBo meterbo = new MeterInfoBo(TestActivity3.this);
        meterinfos = meterbo.listAllData();

        for (int i = 0; i < meterinfos.size(); i++) {
            if (meterinfos.get(i).getComm_address().toString().length() > 6) {
                MeterAllInfo allInfo = new MeterAllInfo();
                allInfo.setCUSTOMER_NAME(meterinfos.get(i).getCustomer_name());
                allInfo.setCOMM_ADDRESS(meterinfos.get(i).getComm_address());
                allInfo.setMETER_TYPE(meterinfos.get(i).getMetertype());
                allInfo.setPHONE1(meterinfos.get(i).getTerminal_address());
                allInfo.setTERMINAL_ADDRESS(meterinfos.get(i).getAreaname());
                mDatas.add(allInfo);
            }
        }
    }


    /**
     * 设置监听
     */
    private void setListener() {
        //edittext的监听
        testEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //每次edittext内容改变时执行 控制删除按钮的显示隐藏
            @Override
            public void afterTextChanged(Editable editable) {
                //匹配文字 变色
                doChangeColor(editable.toString().trim());
            }
        });
        //recyclerview的点击监听
        recycleAdapter.setOnItemClickListener(new RcAdapterWholeChange.onItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(TestActivity3.this, "妹子 pos== " + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 字体匹配方法
     */
    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        newList.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            newList.addAll(mDatas);
            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            recycleAdapter.setText(null);
            refreshUI();
        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (MeterAllInfo i : mDatas) {
                if (i.getCUSTOMER_NAME().contains(text) || i.getCOMM_ADDRESS().contains(text) || i.getPHONE1().contains(text) || i.getTERMINAL_ADDRESS().contains(text)) {
                    newList.add(i);
                }
            }
            //设置要变色的关键字
            recycleAdapter.setText(text);
            refreshUI();
        }
    }
}
