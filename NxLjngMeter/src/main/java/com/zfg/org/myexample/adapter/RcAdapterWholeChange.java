package com.zfg.org.myexample.adapter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.model.MeterAllInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author txs
 * @date 2018/01/16
 */

public class RcAdapterWholeChange extends RecyclerView.Adapter<RcAdapterWholeChange.MyViewHolder> {
    private Context context;
    /**
     * adapter传递过来的数据集合
     */
    private List<MeterAllInfo> list = new ArrayList<>();
    /**
     * 需要改变颜色的text
     */
    private String text;
    /**
     * 属性动画
     */
    private Animator animator;

    /**
     * 在MainActivity中设置text
     */
    public void setText(String text) {
        this.text = text;
    }

    public RcAdapterWholeChange(Context context, List<MeterAllInfo> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /**如果没有进行搜索操作或者搜索之后点击了删除按钮 我们会在MainActivity中把text置空并传递过来*/
        if (text != null) {
            //设置span
            SpannableString userNameStr = matcherSearchText(Color.rgb(255, 0, 0), list.get(position).getCUSTOMER_NAME(), text);
            SpannableString meterNumStr = matcherSearchText(Color.rgb(255, 0, 0), list.get(position).getCOMM_ADDRESS(), text);
            SpannableString meterTypeStr = matcherSearchText(Color.rgb(255, 0, 0), list.get(position).getMETER_TYPE(), text);
            SpannableString phoneNumStr = matcherSearchText(Color.rgb(255, 0, 0), list.get(position).getPHONE1(), text);
            SpannableString userAddressStr = matcherSearchText(Color.rgb(255, 0, 0), list.get(position).getTERMINAL_ADDRESS(), text);
            holder.userName.setText(userNameStr);
            holder.meterNum.setText(meterNumStr);
            holder.meterType.setText(meterTypeStr);
            holder.phoneNum.setText(phoneNumStr);
            holder.userAddress.setText(userAddressStr);
        } else {
            holder.userName.setText(list.get(position).getCUSTOMER_NAME());
            holder.meterNum.setText(list.get(position).getCOMM_ADDRESS());
            holder.meterType.setText(list.get(position).getMETER_TYPE());
            holder.phoneNum.setText(list.get(position).getPHONE1());
            holder.userAddress.setText(list.get(position).getTERMINAL_ADDRESS());
        }
//        //属性动画
//        animator = AnimatorInflater.loadAnimator(context, R.animator.anim_set);
//        animator.setTarget(holder.mLlItem);
//        animator.start();
//        //点击监听
//        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onItemClickListener.onClick(view, position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Recyclerview的点击监听接口
     */
    public interface onItemClickListener {
        void onClick(View view, int pos);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(RcAdapterWholeChange.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlItem;
//        private SimpleDraweeView mImgvSimple;
        private TextView userName;
        private TextView meterNum;
        private TextView meterType;
        private TextView phoneNum;
        private TextView userAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName=(TextView) itemView.findViewById(R.id. userName);
            meterNum=(TextView) itemView.findViewById(R.id. meterNum);
            phoneNum=(TextView) itemView.findViewById(R.id. phoneNum);
            meterType=(TextView) itemView.findViewById(R.id. meterType);
            userAddress=(TextView) itemView.findViewById(R.id. userAddress);
        }
    }

    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

}
