package com.zfg.org.myexample.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zfg.org.myexample.dto.RechargeItemModel;

import java.util.ArrayList;

import com.zfg.org.myexample.R;

/**
 * Created by Administrator on 2016-10-13.
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.BaseViewHolder> implements View.OnClickListener {
    private ArrayList<RechargeItemModel> dataList = new ArrayList<>();
    private int lastPressIndex = -1;

    private OnRecyclerItemClicklistener monRecyclerItemClicklistener = null;

    public static interface OnRecyclerItemClicklistener {
        void onItemClick(View view,String data);
    }

    public void setOnRecyclerItemClicklistener(OnRecyclerItemClicklistener listener){
        this.monRecyclerItemClicklistener = listener;
    }


    public void replaceAll(ArrayList<RechargeItemModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public RechargeAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RechargeAdapter.BaseViewHolder vh;
        switch (viewType) {
            case RechargeItemModel.ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false);
                vh  = new OneViewHolder(view);
                view.setOnClickListener(this);
                return vh;
            case RechargeItemModel.TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false);
                vh  = new OneViewHolder(view);
                view.setOnClickListener(this);
                return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RechargeAdapter.BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position).data);
        holder.itemView.setTag(dataList.get(position).data);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if(monRecyclerItemClicklistener != null){
            monRecyclerItemClicklistener.onItemClick(v,(String)v.getTag());
        }
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data) {
        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private TextView tv;

        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "OneViewHolder: ");
                    int position = getAdapterPosition();
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                tv.setText(text);
                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));

                } else {
                    tv.setSelected(false);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blue_500));
                }
            }
        }
    }

    private class TWoViewHolder extends BaseViewHolder {
        private EditText et;

        public TWoViewHolder(View view) {
            super(view);
            et = (EditText) view.findViewById(R.id.et);
        }

        @Override
        void setData(Object data) {
            super.setData(data);
        }
    }


}
