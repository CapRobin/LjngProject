package com.zfg.org.myexample;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.MainActivity;
import com.zfg.org.myexample.utils.Preference;

/**
 * 欢迎界面
 **/
public class WelcomeActivity extends BasicActivity {

	@ViewInject(id = R.id.welcome_viewpager)
	private ViewPager wel_viewPager;

	private PagerAdapter pagerAdapter;
	private ArrayList<View> viewlist;
	private Preference preference;

	private int[] pic_ids = { R.mipmap.welcome_v1, R.mipmap.welcome_v2,
			R.mipmap.welcome_v3 };

	// 位移量
	private int offset;
	// 记录当前的位置
	private int curpos = 0;
	// 记录滑动手势的按下和抬起的位置，用于判断最后一页是否左滑从而进入主页面
	private float lastx = 0, curx = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		// 判断首次使用
		preference = Preference.instance(this);
		initviewpage();
		handler.postDelayed(runnable, 2000);
	}

	/**
	 * Describe：启动引导页设置
	 * Params:
	 * Date：2018-03-27 10:59:06
	 */
	private void initviewpage() {
		viewlist = new ArrayList<View>();

		for (int i = 0; i < pic_ids.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(pic_ids[i]);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.CENTER_CROP);// 图片按等比缩放
			viewlist.add(iv);
		}

		// 设置wel_viewPager的Adapter
		initAdapter();
	}

	/**
	 *
	 */
	private void initAdapter() {
		pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;// 官方提示这样写
			}

			@Override
			public int getCount() {
				return viewlist.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewlist.get(position));
			}

			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewlist.get(position));
				return viewlist.get(position);
			}
		};

		wel_viewPager.setAdapter(pagerAdapter);

		/**
		 * 在最后一页 判断左滑还是右滑
		 */
		wel_viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if ((curpos == viewlist.size() - 1)) {// 是否是最后一页
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						lastx = event.getX();
						break;
					case MotionEvent.ACTION_MOVE:
						curx = event.getX();
						if (lastx - curx > 100) {
							skipActivity();
						}
						break;
					}
				}
				return false;
			}
		});

		wel_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// // 转移到下一个view时 原点移动到下一个位置
				// if (arg0 < viewlist.size()) {
				// moveCurdot(arg0);
				// }
				curpos = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

		});
	}

	//
	// /**
	// * 移动 圆点图片 到下一个位置
	// *
	// * @param position
	// */
	// private void moveCurdot(int position) {
	// TranslateAnimation anim = new TranslateAnimation(offset * curpos,
	// offset * position, 0, 0);
	// anim.setDuration(300);
	// anim.setFillAfter(true);
	// cur_dot.startAnimation(anim);
	// }

	private Handler handler = new Handler();
	private boolean isEnd = false;
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (!isEnd) {
				if (curpos < 2) {
					handler.postDelayed(this, 2000);
					curpos++;
					wel_viewPager.setCurrentItem(curpos);
				} else {
					skipActivity();
					isEnd = true;
				}
			}
		}
	};

	/**
	 * 跳转到指定Activity
	 */
	private void skipActivity() {
		startActivity(null, MainActivity.class); // 跳转到主页面
		startActivity(null, LoginActivity.class); // 强制跳转到登录页面
		preference.putBoolean(Preference.FIRST_IN, true);
		finishSimple();
	}
}
