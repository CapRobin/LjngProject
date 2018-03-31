package com.zfg.org.myexample.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.adapter.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends BasicActivity implements View.OnTouchListener {
	private EditText lbEdit = null;
	private Button publishSupplyBtn = null;
	private View mHolder1, mHolder2, mHolder3, mHolder4, mHolder5, mHolder7;
	private EditText ggEdit = null;
	private EditText cdEdit = null;
	private EditText jyfsEdit = null;
	private EditText btEdit = null;
	private static String gcPinming[] = null;
	private static String steelStyle[] = null;
	private static String steelSpecification[] = null;

	private static String steelAddress[] = null;
	private static String steelAmount[] = null;
	private static String steelGuoBiao[] = null;
	private static String steelDealType[] = null;

	private static String gcPinming_01[] = null;
	private static String gcPinming_02[] = null;
	private static String gcPinming_03[] = null;

	private static String gcPinming_04[] = null;
	private static String gcPinming_05[] = null;
	private static String gcPinming_06[] = null;
	private static String gcPinming_07[] = null;
	private NoScrollGridView lbGridView, pmGridView, ggGridView, cdGridView, slGridView, gbGridView, jyfsGridView = null;
	private List<String> myGridViewList1, myGridViewList2, myGridViewList3, myGridViewList4, myGridViewList5, myGridViewList6, myGridViewList7 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
//		titleBarInitView();
		initVieww();
	}

	/**
	 *
	 * @Describe：初始化
	 * @Throws:
	 * @Date：2014年7月25日 下午4:35:13
	 * @Version v1.0
	 */
	private void initVieww() {
		btEdit = (EditText) findViewById(R.id.btEdit);
		mHolder1 = findViewById(R.id.cbxmAllView);
		mHolder2 = findViewById(R.id.settingView);
		mHolder3 = findViewById(R.id.cxbhAllView);
		mHolder4 = findViewById(R.id.holder4);
		mHolder5 = findViewById(R.id.holder5);
		mHolder7 = findViewById(R.id.holder7);

		lbEdit = (EditText) findViewById(R.id.lbEdit);
		ggEdit = (EditText) findViewById(R.id.ggEdit);
		cdEdit = (EditText) findViewById(R.id.cdEdit);
		jyfsEdit = (EditText) findViewById(R.id.jyfsEdit);

		lbGridView = (NoScrollGridView) findViewById(R.id.lbGridView);
		pmGridView = (NoScrollGridView) findViewById(R.id.pmGridView);
		ggGridView = (NoScrollGridView) findViewById(R.id.ggGridView);
		cdGridView = (NoScrollGridView) findViewById(R.id.cdGridView);
		slGridView = (NoScrollGridView) findViewById(R.id.slGridView);
		jyfsGridView = (NoScrollGridView) findViewById(R.id.jyfsGridView);

		steelStyle = this.getResources().getStringArray(R.array.steelStyle);
		steelSpecification = this.getResources().getStringArray(R.array.gcGuige);
		steelAddress = this.getResources().getStringArray(R.array.gccd);
		steelAmount = this.getResources().getStringArray(R.array.gcShuliang);
		steelGuoBiao = this.getResources().getStringArray(R.array.isGuobiao);
		steelDealType = this.getResources().getStringArray(R.array.jyfs);

		// 设置隐藏GridView数据
		setGridViewData();

		lbEdit.setOnTouchListener(this);
		ggEdit.setOnTouchListener(this);
		cdEdit.setOnTouchListener(this);
		jyfsEdit.setOnTouchListener(this);

//		publishSupplyBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Toast.makeText(context,"~~~~~~~",Toast.LENGTH_SHORT).show();
//			}
//		});
	};



	/**
	 *
	 * @Describe：构造View的数据
	 * @Throws:
	 * @Date：2014年8月19日 上午10:09:09
	 * @Version v1.0
	 */
	private void setGridViewData() {
		// 提前配置隐藏View显示数据
		myGridViewList1 = new ArrayList<String>();
		myGridViewList2 = new ArrayList<String>();
		myGridViewList3 = new ArrayList<String>();
		myGridViewList4 = new ArrayList<String>();
		myGridViewList5 = new ArrayList<String>();
		myGridViewList6 = new ArrayList<String>();
		myGridViewList7 = new ArrayList<String>();

		int lbArray = steelStyle.length;
		// int pmArray = gcPinming.length;
		int ggArray = steelSpecification.length;
		int cdArray = steelAddress.length;
		int slArray = steelAmount.length;
		int gbArray = steelGuoBiao.length;
		int jyfsArray = steelDealType.length;

		// 构造数据
		for (int i = 0; i < lbArray; i++) {
			myGridViewList1.add(steelStyle[i]);
		}
		// for (int i = 0; i < pmArray; i++) {
		// myGridViewList2.add(gcPinming[i]);
		// }
		for (int i = 0; i < ggArray; i++) {
			myGridViewList3.add(steelSpecification[i]);
		}
		for (int i = 0; i < cdArray; i++) {
			myGridViewList4.add(steelAddress[i]);
		}
		for (int i = 0; i < slArray; i++) {
			myGridViewList5.add(steelAmount[i]);
		}
		for (int i = 0; i < gbArray; i++) {
			myGridViewList6.add(steelGuoBiao[i]);
		}
		for (int i = 0; i < jyfsArray; i++) {
			myGridViewList7.add(steelDealType[i]);
		}

		showView(myGridViewList1, 1);
		// showView(myGridViewList2, 2);
		showView(myGridViewList3, 3);
		showView(myGridViewList4, 4);
		showView(myGridViewList5, 5);
//		showView(myGridViewList6, 6);
		showView(myGridViewList7, 7);
	}

	/**
	 *
	 * @Describe：打开显示隐藏View
	 * @param list
	 * @Throws:
	 * @Date：2014年8月20日 上午10:31:19
	 * @Version v1.0
	 */
	public void showView(List<String> list, int viewId) {

		MyLocationAdapter locationAdapter = new MyLocationAdapter(DemoActivity.this, list);
		switch (viewId) {
			case 1: // 显示类别隐藏View
				lbGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				lbGridView.setAdapter(locationAdapter);
				lbGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String string = myGridViewList1.get(i);
						// 设置类别数据
						setPmData(i);
						lbEdit.setText(string);
						popViewisShow(1);
						popViewisShow(2);
					}
				});
				break;
			case 2: // 显示品名隐藏View
				pmGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				pmGridView.setAdapter(locationAdapter);
				pmGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String string = myGridViewList2.get(i);
						popViewisShow(2);
						popViewisShow(3);
						ggEdit.setFocusable(true);
						ggEdit.requestFocus();
						ggEdit.performClick();
					}
				});
//				pmGridView.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//						String string = myGridViewList2.get(position);
//						popViewisShow(2);
//						popViewisShow(3);
//						ggEdit.setFocusable(true);
//						ggEdit.requestFocus();
//						ggEdit.performClick();
//					}
//				});
				break;
			case 3:
				ggGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				ggGridView.setAdapter(locationAdapter);
				ggGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String itemStr = myGridViewList3.get(1);
						ggEdit.setText(itemStr);
						popViewisShow(3);
						popViewisShow(4);
						cdEdit.setFocusable(true);
						cdEdit.requestFocus();
						cdEdit.performClick();
					}
				});
				break;
			case 4:
				cdGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				cdGridView.setAdapter(locationAdapter);
				cdGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String itemStr = myGridViewList4.get(i);
						cdEdit.setText(itemStr);
						popViewisShow(4);
					}
				});
				break;
			case 5:
				slGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				slGridView.setAdapter(locationAdapter);
				slGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String itemStr = myGridViewList5.get(i);
						popViewisShow(5);
						// popViewisShow(6);
					}
				});
				break;
			case 6:
				break;
			case 7:
				jyfsGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				jyfsGridView.setAdapter(locationAdapter);
				jyfsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String itemStr = myGridViewList7.get(i);
						jyfsEdit.setText(itemStr);
						popViewisShow(7);
					}
				});
				break;
		}
	}



	/**
	 *
	 * @Describe：设置钢材类别名称
	 * @param selectId
	 * @Throws:
	 * @Date：2014年8月2日 下午4:15:02
	 * @Version v1.0
	 */
	private void setPmData(int selectId) {
		switch (selectId) {
			case 0:
				gcPinming = gcPinming_01;
				break;
			case 1:
				gcPinming = gcPinming_02;
				break;
			case 2:
				gcPinming = gcPinming_03;
				break;
			case 3:
				gcPinming = gcPinming_04;
				break;
			case 4:
				gcPinming = gcPinming_05;
				break;
			case 5:
				gcPinming = gcPinming_06;
				break;
			case 6:
				gcPinming = gcPinming_07;
				break;
		}

		int pmArray = gcPinming.length;
		myGridViewList2.clear();
		for (int i = 0; i < pmArray; i++) {
			myGridViewList2.add(gcPinming[i]);
		}
		showView(myGridViewList2, 2);
	}

	/**
	 *
	 * @Describe：控制视图是否显示
	 * @Throws:
	 * @Date：2014年8月18日 上午10:39:33
	 * @Version v1.0
	 */
	private void popViewisShow(int id) {

		switch (id) {
			case 1:
				if (View.GONE == mHolder1.findViewById(R.id.cbxmHideView).getVisibility()) {
					animateExpanding(mHolder1.findViewById(R.id.cbxmHideView));
				} else {
					animateCollapsing(mHolder1.findViewById(R.id.cbxmHideView));
				}
				break;
			case 2:
				if (View.GONE == mHolder2.findViewById(R.id.hiddenview2).getVisibility()) {
					animateExpanding(mHolder2.findViewById(R.id.hiddenview2));
				} else {
					animateCollapsing(mHolder2.findViewById(R.id.hiddenview2));
				}
				break;
			case 3:
				if (View.GONE == mHolder3.findViewById(R.id.cxbhHideView).getVisibility()) {
					animateExpanding(mHolder3.findViewById(R.id.cxbhHideView));
				} else {
					animateCollapsing(mHolder3.findViewById(R.id.cxbhHideView));
				}
				break;
			case 4:
				if (View.GONE == mHolder4.findViewById(R.id.hiddenview4).getVisibility()) {
					animateExpanding(mHolder4.findViewById(R.id.hiddenview4));
				} else {
					animateCollapsing(mHolder4.findViewById(R.id.hiddenview4));
				}
				break;
			case 5:
				if (View.GONE == mHolder5.findViewById(R.id.hiddenview5).getVisibility()) {
					animateExpanding(mHolder5.findViewById(R.id.hiddenview5));
				} else {
					animateCollapsing(mHolder5.findViewById(R.id.hiddenview5));
				}
				break;
			case 6:
				break;
			case 7:
				if (View.GONE == mHolder7.findViewById(R.id.hiddenview7).getVisibility()) {
					animateExpanding(mHolder7.findViewById(R.id.hiddenview7));
				} else {
					animateCollapsing(mHolder7.findViewById(R.id.hiddenview7));
				}
				break;
		}
	}



	public static void animateCollapsing(final View view) {
		int origHeight = view.getHeight();

		ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
		animator.addListener(new AnimatorListenerAdapter() {
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
			};
		});
		animator.start();
	}



	public static void animateExpanding(final View view) {
		view.setVisibility(View.VISIBLE);

		final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(widthSpec, heightSpec);

		ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
		animator.start();
	}

	public static ValueAnimator createHeightAnimator(final View view, int start, int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int value = (Integer) valueAnimator.getAnimatedValue();

				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = value;
				view.setLayoutParams(layoutParams);
			}
		});
		return animator;
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.lbEdit: // 点击触动类别项目
				closeInputMethod();
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					popViewisShow(1);
				}
				break;
			case R.id.ggEdit:// 点击触动规格项目
				closeInputMethod();
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					popViewisShow(3);
				}
				break;
			case R.id.cdEdit:// 点击触动规格项目
				closeInputMethod();
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					popViewisShow(4);
				}
				break;
//			break;
			case R.id.jyfsEdit:// 点击触动规格项目
				closeInputMethod();
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					popViewisShow(7);
				}
				break;
		}
		return false;
	}


	private void closeInputMethod() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();

		// isOpen若返回true，则表示输入法打开
		if (isOpen) {
			imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}
}
