package com.zfg.org.myexample.activity;


import com.zfg.org.myexample.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class DialogLoading extends Dialog{
	
	private TextView loadingLabel;

	public DialogLoading(Context context) {
		super(context,R.style.Dialog);
		setContentView(R.layout.dialog_loading_layout);
		setCanceledOnTouchOutside(false);
		loadingLabel = (TextView) findViewById(R.id.loading_text);
	}
	
	public void setDialogLabel(String label){
		loadingLabel.setText(label);
	}

}
