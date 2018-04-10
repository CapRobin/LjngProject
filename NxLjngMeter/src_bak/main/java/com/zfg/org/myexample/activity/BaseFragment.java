package com.zfg.org.myexample.activity;

import java.lang.reflect.Field;

import com.zfg.org.myexample.ViewInject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {

	protected String className = BaseFragment.class.getSimpleName();

	protected int lastVisiblePosition = -1;
	protected long lastRefreshTime = 0;
	protected BasicActivity context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = (BasicActivity) getActivity();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 初始化findviewbyid注解
	 */
	protected void fieldView(View view) {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					ViewInject viewInject = field
							.getAnnotation(ViewInject.class);
					if (viewInject != null) {
						int viewId = viewInject.id();
						field.set(this, view.findViewById(viewId));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update(){
		
	}
	
	public boolean onBackKeyPressed(){
		return false;
	}
}
