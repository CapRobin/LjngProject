package com.zfg.org.myexample.fragment;


import com.zfg.org.myexample.R;
import com.zfg.org.myexample.activity.BasicFragmentDialog;
import com.zfg.org.myexample.utils.FileUtil;
import com.zfg.org.myexample.ViewInject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

/**
 * 帮助界面
 * 
 * @author zfg
 * 
 */
public class HelpFragment extends BasicFragmentDialog implements OnClickListener {

	@ViewInject(id = R.id.back_btn)
	private Button backBtn;
	@ViewInject(id = R.id.web_content)
	private WebView webview;
	@ViewInject(id = R.id.title)
	private TextView titleView;

	private String content = "";
	private String index = "";

	public static HelpFragment getInstance(String index) {
		HelpFragment fragment = new HelpFragment();
		Bundle bundle = new Bundle();
		bundle.putString("index", index);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_left_right_anim;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments().getString("index");
		// content = FileUtil.readTxtFromAsset(context, "html/help.html");
	}

	/**
	 * onCreateView方法中才是真正的绘制Fragment的界面，即这个返回的View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_help_layout, container,
				false);
		fieldView(view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// 网页设置
		WebSettings settings = webview.getSettings();
		// settings.setJavaScriptEnabled(true);
		// settings.setBlockNetworkImage(true);
		settings.setDefaultTextEncodingName("utf-8");
		webview.setBackgroundColor(0);
		webview.getBackground().setAlpha(0);

		String temp = FileUtil.readTxtFromAsset(context, "help/" + index
				+ ".html");
		String meta = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0,max-scale=1.0, user-scalable=no\">";
		content += meta+ "</head><body style=\"color:#444444 !important;\">";
		content += "<div style=\"line-height:120%;letter-spacing: 0.3mm;\">"+ temp + "</div>";
		content += "</body></html>";
		webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",
				"about:blank");
		titleView.setText("帮助中心");
		backBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			dismiss();
			break;
		}
	}
}
