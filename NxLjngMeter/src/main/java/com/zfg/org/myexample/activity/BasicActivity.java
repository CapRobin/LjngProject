package com.zfg.org.myexample.activity;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

/**
 *
 */
@SuppressWarnings("deprecation")
public class BasicActivity extends FragmentActivity implements OnGestureListener,View.OnClickListener {
	
	protected static final int REQUEST_CODE_CAMERA = 1;
	protected static final int REQUEST_CODE_PHOTO = 2;
	protected static final int REQUEST_CODE_PHOTO_DEAL = 3;

	protected BasicActivity context;
	public int screenWidth = 0;
	public int screenHeight = 0;
	private GestureDetector detector;
	private boolean isGesture = false;
	protected int userType = 0;
	protected Preference preference;

	//读取本地测试数据开关
	public boolean isTest =false;
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Toast.makeText(BasicActivity.this, msg.getData().getString("Msg"), Toast.LENGTH_SHORT).show();
					break;
				case 1:

					break;
				case 2:

					break;
			}
		};
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initStatusBarStyle();
		context = this;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		// 手势
		detector = new GestureDetector(this);
	}

	protected void onStart(){
		super.onStart();
		if(Config.canUpdate()){
			Preference preference = Preference.instance(context);
			if (preference.getBoolean(Preference.HAS_UPDATE)) {
				Intent playAlarm = new Intent(ContantsUtil.SYCN_DATA);
				playAlarm.putExtra("state", true);
				context.startService(playAlarm);
				Config.isUpdate = true;
			}else{
				Config.stopUpdate();
			}
		}
	}

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}
	
	/**
	 * 初始化findviewbyid注解
	 */
	public void initView() {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if (field.get(this) != null)
						continue;
					ViewInject viewInject = field
							.getAnnotation(ViewInject.class);
					if (viewInject != null) {
						int viewId = viewInject.id();
						field.set(this, findViewById(viewId));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean dispatchTouchEvent(MotionEvent event) {
		if (detector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (isGesture) {
			float subX = e2.getX() - e1.getX();
			float subY = e2.getY() - e1.getY();		
			if (subX > 150 && Math.abs(subY) < 170) {
				scrollXBack();
			}else if(subX < -150 && Math.abs(subY) < 170){
				srollYRight();
			}
		}
		return false;
	}

	protected void scrollXBack() {
		finish();
	}
	
	protected void srollYRight(){
		
	}

	/**
	 * 设置为可以侧滑关�?
	 * 
	 * @param state
	 */
	public void setGuesture(boolean state) {
		isGesture = state;
	}

	/**
	 * 覆写finish方法，覆盖默认方法，加入切换动画
	 */
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
	}
	
	public void finishResult(){
		setResult(RESULT_OK);
		this.finish();
	}
	
	public void finishSimple(){
		super.finish();
	}

	/**
	 * 覆写startactivity方法，加入切换动�?
	 */
	public void startActivity(Bundle bundle,Class<?> target) {
		Intent intent = new Intent(this, target);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}
	
	/**
	 * 带回调的跳转
	 * @param bundle
	 * @param requestCode
	 * @param target
	 */
	public void startForResult(Bundle bundle,int requestCode,Class<?> target){
		Intent intent = new Intent(this, target);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isUseTopBarColor() {
		return true;
	}

	public boolean isSetTopPaddding() {
		return true;
	}

	public int getTopBarColor() {
		return R.color.common_top_color;//
	}

	protected int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public void initStatusBarStyle() {
		// 4.4+ 设置状态栏透明、设置状态栏的资源文件、在布局中做适配
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			if (isUseTopBarColor()) {
				SystemBarTintManager sbt = new SystemBarTintManager(this);
				sbt.setStatusBarTintEnabled(true);
				sbt.setStatusBarTintDrawable(getResources().getDrawable(getTopBarColor()));
			}
			if (isSetTopPaddding()) {
				getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, getStatusBarHeight(), 0, 0);
			}
		}
	}

	@Override
	public void onClick(View view) {

	}

	/**
	 * Describe：提示信息设置
	 * Params:
	 * Date：2018-03-31 11:15:00
	 */
	
	public void setToast(String hintInfo){
		Toast.makeText(this,hintInfo,Toast.LENGTH_SHORT).show();
	}

	
	/**
	 * Describe：读取本地文件_临时方法
	 * Params:
	 * Date：2018-04-03 09:34:05
	 */
	
	public String tempJson(String fileName){
//		String fileName = "user.java"; // 文件名字
		String getString = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			getString = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getString;
	}


	/**
	 * Describe：发送消息刷新UI
	 * Params:
	 * Date：2018-04-03 17:12:37
	 */
	
	public void sendMsgUpdateUI(int what, String titleMsg) {
		Message msg = handler.obtainMessage(what);
		Bundle bundle = new Bundle();
		bundle.putString("Msg", titleMsg);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	/**
	 * Describe：关闭输入法
	 * Params:
	 * Date：2018-04-03 19:45:01
	 */
	protected void closeInputMethod() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();
		// isOpen若返回true，则表示输入法打开
		if (isOpen) {
			imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
