package com.zfg.org.myexample.activity;

import java.lang.reflect.Field;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

public class BasicFragmentDialog extends DialogFragment {
	
	protected BasicActivity context;
	private boolean state = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setStyle(DialogFragment.STYLE_NO_TITLE,
				android.R.style.Theme_Translucent_NoTitleBar);			
		context = (BasicActivity) getActivity();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_left_right_anim;
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
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setOnKeyListener(new OnKeyListener() {			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
					if(state){
						onBackPressed();
					}
					state = false;
					return true;
				}else if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
					state = true;
				}
				return false;
			}
		});
		return dialog;
	}
	
	protected void onBackPressed(){
		Log.v("back", "back pressed");
		dismiss();
	}
	
}
