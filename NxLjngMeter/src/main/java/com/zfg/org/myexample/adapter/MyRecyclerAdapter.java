package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.TestActivity;
import com.zfg.org.myexample.model.MeterAllInfo;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * <p>
 * Name：MeterAllInfoAdapter
 * Describe：用户信息数据适配
 * Date：2018-04-12 16:00:13
 * Author: CapRobin@yeah.net
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<MeterAllInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<MeterAllInfo> datas){
        this. mContext= context;
        this. mDatas=datas;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.userName.setText( mDatas.get(position).getCUSTOMER_NAME());
        holder.meterNum.setText( mDatas.get(position).getCOMM_ADDRESS());
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_recycler,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder{

        TextView userName;
        TextView meterNum;

        public MyViewHolder(View view) {
            super(view);
            userName=(TextView) view.findViewById(R.id. userName);
            meterNum=(TextView) view.findViewById(R.id. meterNum);
        }

    }
}
