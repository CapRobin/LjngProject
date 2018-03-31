package com.zfg.org.myexample.dialog;

import java.lang.reflect.Field;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BaseDialog extends Dialog {

	protected Context context;

	public BaseDialog(Context context) {
		super(context, R.style.Dialog);
		this.context = context;
	}

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		fieldView();
	}

	/**
	 * 初始化findviewbyid注解
	 */
	public void fieldView() {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if (field.get(this) != null)
						continue;
					ViewInject viewInject = field
							.getAnnotation(ViewInject.class);
					int viewId = viewInject.id();
					field.set(this, findViewById(viewId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 覆写startactivity方法，加入切换动画
	 */
	protected void startActivity(Bundle bundler, Class<?> target) {
		Intent intent = new Intent(context, target);
		if (bundler != null) {
			intent.putExtras(bundler);
		}
		context.startActivity(intent);
	}

}
