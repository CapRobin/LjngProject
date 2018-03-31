package com.zfg.org.myexample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.ViewInject;

public class LoadingFragment extends BaseFragment {
	
	@ViewInject(id = R.id.loading)
	private ProgressBar loading;

	public static LoadingFragment getInstance() {
		return new LoadingFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_tran_loading, container,
				false);
		fieldView(view);
		return view;
	}
	
	public void loadFaril(){
		loading.setVisibility(View.GONE);
	}

}
