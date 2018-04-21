package com.zfg.org.myexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.LoginActivity;
import com.zfg.org.myexample.model.HisEleOption;

import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 *
 * Name：HisEleOptionAdapter
 * Describe：电表操作记录数据适配
 * Date：2018-04-12 16:00:54
 * Author: CapRobin@yeah.net
 *
 */
public class DialogAdapt extends BaseAdapter {

    private List<String> dataList;
    private LayoutInflater mInflater;
    private LoginActivity mContext;

    public DialogAdapt(Context context, List<String> list){
        this.dataList = list;
        mContext = (LoginActivity) context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.user_type_item,null);
            person = new Person();
            person.userTypeText = (TextView)convertView.findViewById(R.id.userTypeText);
            convertView.setTag(person);
        }else{
            person = (Person)convertView.getTag();
        }
        person.userTypeText.setText(dataList.get(position).toString());
        return convertView;
    }
    class Person{
        TextView userTypeText;
    }
}