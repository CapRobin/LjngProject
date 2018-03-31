package com.zfg.org.myexample.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.SBaseAdapter;
import com.zfg.org.myexample.ble.bean.MDevice;

public class DeviceAdapter extends SBaseAdapter {

	private DisplayImageOptions options;

	public DeviceAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_device_adapter);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.mipmap.bluetooth_icon_big)
				.showImageForEmptyUri(R.mipmap.bluetooth_icon_big)
				.showImageOnLoading(R.mipmap.bluetooth_icon_big)
				.showImageOnFail(R.mipmap.bluetooth_icon_big).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.deviceName = (TextView) convertView.findViewById(R.id.device);
		holder.deviceDetail = (TextView) convertView.findViewById(R.id.device_addr);
//		holder.imagedevice = (ImageView) convertView.findViewById(R.id.icon);
		convertView.setTag(holder);
	}

//
	@Override
	protected void holderView(View convertView, Object itemObject, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		MDevice devices = (MDevice) itemObject;
		holder.deviceName.setText(devices.getDevice().getName());
		holder.deviceDetail.setText(devices.getDevice().getAddress());
//		ImageLoader.getInstance().displayImage(news.getThumbnail(), holder.iconView, options);
	}


	class ViewHolder{
		TextView deviceName;
		TextView deviceDetail;
//		ImageView imagedevice;
	}



}
