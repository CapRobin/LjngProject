package com.zfg.org.myexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.dto.MeterInfoCheckModel;

import java.util.List;

public class MyLocationAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MeterInfoCheckModel> itemList = null;
    private Context mContext;

    public MyLocationAdapter2(Context context, List<MeterInfoCheckModel> item) {
        this.mContext = context;
        itemList = item;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final HeadView head;
        if (convertView == null) {
            head = new HeadView();
            convertView = mInflater.inflate(R.layout.cbxm_item_view2, null);
            head.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            head.meterType = (TextView) convertView.findViewById(R.id.meterType);
            head.cxbhText = (TextView) convertView.findViewById(R.id.cxbhText);
            convertView.setTag(head);
        } else {
            head = (HeadView) convertView.getTag();
        }

        String cxbhTextStr = itemList.get(position).value;
        head.cxbhText.setText(cxbhTextStr);

        String meterTypeStr = itemList.get(position).metertype;
        head.meterType.setText(meterTypeStr);

        // head.itemText.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // head.layout.setBackgroundResource(R.drawable.view_item_bg);
        // }
        // });

        head.layout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                head.layout.setBackgroundResource(R.drawable.selector_list_item);
                return false;
            }
        });
        return convertView;
    }

    public class HeadView {
        public TextView meterType;
        public TextView cxbhText;
        public RelativeLayout layout;
    }
}
