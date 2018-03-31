package com.zfg.org.myexample.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zfg.org.myexample.R;

public class TranLoading  extends Dialog{
	
	private TextView loadingTxt;
	
	public TranLoading(Context context) {
		super(context,R.style.TranDialog);
		setContentView(R.layout.dialog_tran_loading);
		setCanceledOnTouchOutside(false);
//		loadingTxt = (TextView) findViewById(R.id.time_text);
	}
	
	public void setLabel(String text){
		loadingTxt.setText(text);
		loadingTxt.setVisibility(View.VISIBLE);
	}
	
	public void hideLabel(){
		loadingTxt.setVisibility(View.INVISIBLE);
	}

}
