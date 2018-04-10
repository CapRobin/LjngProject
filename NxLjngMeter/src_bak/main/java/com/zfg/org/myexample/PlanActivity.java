package com.zfg.org.myexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.zfg.org.myexample.fragment.PlanTimeFragment;
import com.zfg.org.myexample.model.ClockModel;


import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.adapter.WeekPlanAdapter;
import com.zfg.org.myexample.db.AlarmBo;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.dao.Alarm;
import com.zfg.org.myexample.db.dao.Plan;
import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.dialog.PlanPopDialog;
import com.zfg.org.myexample.service.AlarmService;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.utils.StringUtil;
import com.zfg.org.myexample.widget.NGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户计划界面
 * @author zfg
 */
public class PlanActivity extends BasicActivity implements OnClickListener {

	@ViewInject(id = R.id.clock_plan)
	private NGridView weekGrid;
	@ViewInject(id = R.id.breakfast)
	private TextView breakFast;
	@ViewInject(id = R.id.breakfast_pre)
	private TextView breakFastPre;
	@ViewInject(id = R.id.breakfast_after)
	private TextView breakFastAfter;
	@ViewInject(id = R.id.lunch)
	private TextView lunch;
	@ViewInject(id = R.id.lunch_pre)
	private TextView lunchPre;
	@ViewInject(id = R.id.lunch_after)
	private TextView lunchAfter;
	@ViewInject(id = R.id.dinner)
	private TextView dinner;
	@ViewInject(id = R.id.dinner_pre)
	private TextView dinnerPre;
	@ViewInject(id = R.id.dinner_after)
	private TextView dinnerAfter;
	@ViewInject(id = R.id.sleep)
	private TextView sleep;
	@ViewInject(id = R.id.time_set)
	private RelativeLayout timeSet;
	@ViewInject(id = R.id.back_btn)
	private Button backBtn;
	@ViewInject(id = R.id.plan)
	private TextView planView;
	@ViewInject(id = R.id.save_btn)
	private Button saveBtn;

	private List<ClockModel> data;
	private AlarmBo alarmBo;
	private User user;
	private UserBo userBo;
	private WeekPlanAdapter adapter;
	private Map<String, Alarm> mapData;
	private Preference preference;
	private PlanPopDialog planDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);
		preference = Preference.instance(context);
		userBo = new UserBo(this);
		user = userBo.getUserByServerId(ContantsUtil.DEFAULT_TEMP_UID);
		initActivity();
		initData();
	}

	private void initActivity() {
		breakFast.setOnClickListener(this);
		breakFastPre.setOnClickListener(this);
		breakFastAfter.setOnClickListener(this);
		lunch.setOnClickListener(this);
		lunchPre.setOnClickListener(this);
		lunchAfter.setOnClickListener(this);
		dinner.setOnClickListener(this);
		dinnerPre.setOnClickListener(this);
		dinnerAfter.setOnClickListener(this);
		sleep.setOnClickListener(this);
		timeSet.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		planView.setOnClickListener(this);
		saveBtn.setOnClickListener(this);

		weekGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				if (position % 8 == 0) {
					boolean enable = false, unable = false, delete = false;
					for (int i = position + 1; i < position + 8; i++) {
						if (data.get(i).getEnable() == 1) {
							enable = true;
						} else if (data.get(i).getEnable() == 0) {
							unable = true;
						} else {
							delete = true;
						}
					}
					for (int i = position + 1; i < position + 8; i++) {
						if (!enable && unable && !delete) {
							data.get(i).setEnable(-1);
						} else if (enable && !unable && !delete) {
							data.get(i).setEnable(0);
						} else {
							data.get(i).setEnable(1);
						}
					}
				} else {
					int enable = data.get(position).getEnable();
					if (enable == 1) {
						data.get(position).setEnable(0);
					} else if (enable == 0) {
						data.get(position).setEnable(-1);
					} else {
						data.get(position).setEnable(1);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void initData() {
		data = new ArrayList<ClockModel>();
		alarmBo = new AlarmBo(context);
		mapData = alarmBo.getPlanMapAlarm(ContantsUtil.DEFAULT_TEMP_UID,
				ContantsUtil.SUGAR_ALARM);
		for (int i = 0; i < 56; i++) {
			ClockModel model = new ClockModel();
			int scale = i % 8;
			if (scale == 0) {
				model.setLabel(true);
				model.setWeek(i / 8 + 1);
			} else {
				model.setLabel(false);
				scale = scale - 1;
				int alarmType = StringUtil.toInt("" + (scale / 2) + (scale % 2));
				Alarm alarm = mapData.get("plan" + alarmType);
				model.setWeek(i / 8 + 1);
				if (alarm == null) {
					model.setEnable(-1);
				} else {
					if (alarm.isSet(model.getWeek())) {
						model.setEnable(1);
					} else if (alarm.isSetUn(model.getWeek())) {
						model.setEnable(0);
					} else {
						model.setEnable(-1);
					}
				}
				model.setType(alarmType);
			}
			data.add(model);
		}
		adapter = new WeekPlanAdapter(context, data);
		weekGrid.setAdapter(adapter);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.breakfast:
			selectData1(0);
			break;
		case R.id.breakfast_pre:
			selectData(0, 1);
			break;
		case R.id.breakfast_after:
			selectData(0, 2);
			break;
		case R.id.lunch:
			selectData1(1);
			break;
		case R.id.lunch_pre:
			selectData(1, 1);
			break;
		case R.id.lunch_after:
			selectData(1, 2);
			break;
		case R.id.dinner:
			selectData1(2);
			break;
		case R.id.dinner_pre:
			selectData(2, 1);
			break;
		case R.id.dinner_after:
			selectData(2, 2);
			break;
		case R.id.sleep:
			selectData(3, 1);
			break;
		case R.id.time_set:
			openDialo();
			break;
		case R.id.back_btn:
			finish();
			break;
		case R.id.plan:
			choosePlan(view);
			break;
		case R.id.save_btn:
			updateAlarm();
			Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		}
	}

	private void selectData(int clumn, int subclumn) {
		boolean enable = false, unable = false, delete = false;
		for (int i = 0; i < 7; i++) {
			int ennabel = data.get(i * 8 + clumn * 2 + subclumn).getEnable();
			if (ennabel == 1) {
				enable = true;
			} else if (ennabel == 0) {
				unable = true;
			} else {
				delete = true;
			}
		}
		for (int i = 0; i < 7; i++) {
			int position = i * 8 + clumn * 2 + subclumn;
			if (!enable && unable && !delete) {
				data.get(position).setEnable(-1);
			} else if (enable && !unable && !delete) {
				data.get(position).setEnable(0);
			} else {
				data.get(position).setEnable(1);
			}
		}
		adapter.notifyDataSetChanged();
		planView.setText(getResources().getString(R.string.user_self));
	}

	private void selectData1(int clumn) {
		boolean enable = false, unable = false, delete = false;
		for (int i = 0; i < 7; i++) {
			int ennabel = data.get(i * 8 + clumn * 2 + 1).getEnable();
			int ennabel2 = data.get(i * 8 + clumn * 2 + 2).getEnable();
			if (ennabel == 1 || ennabel2 == 1) {
				enable = true;
			} else if (ennabel == 0 || ennabel2 == 0) {
				unable = true;
			} else {
				delete = true;
			}
		}
		for (int i = 0; i < 7; i++) {
			int position = i * 8 + clumn * 2 + 1;
			int position1 = i * 8 + clumn * 2 + 2;
			if (!enable && unable && !delete) {
				data.get(position).setEnable(-1);
				data.get(position1).setEnable(-1);
			} else if (enable && !unable && !delete) {
				data.get(position).setEnable(0);
				data.get(position1).setEnable(0);
			} else {
				data.get(position).setEnable(1);
				data.get(position1).setEnable(1);
			}
		}
		adapter.notifyDataSetChanged();
		planView.setText(getResources().getString(R.string.user_self));
	}

	/**
	 * update alarm and set next alarm
	 */
	private void updateAlarm() {
		for (int i = 0; i < data.size(); i++) {
			ClockModel model = data.get(i);
			if (!model.isLabel()) {
				Alarm alarm = mapData.get("plan" + data.get(i).getType());
				if (alarm == null) {
					alarm = new Alarm();
					String time = (String) Config.getProperty("clock"
							+ data.get(i).getType());
					String timeArray[] = time.split(":");
					alarm.setHour(StringUtil.toShort(timeArray[0]));
					alarm.setMinite(StringUtil.toShort(timeArray[1]));
					alarm.setMessage("您计划的测试时间到了");
					alarm.setService_mid(ContantsUtil.DEFAULT_TEMP_UID);
//					alarm.setUid(ContantsUtil.curUser.getService_uid());
					alarm.setSub_type((short) model.getType());
					alarm.setType((short) ContantsUtil.SUGAR_ALARM);
					alarm.setTitle("隆基宁光仪表提醒您");
					alarm.setRepeat(-1);
				}
				if (model.getEnable() == -1) {
					alarm.set(model.getWeek(), false);
					alarm.setUn(model.getWeek(), false);
					alarm.setEnable(true);
				} else if (model.getEnable() == 1) {
					alarm.set(model.getWeek(), true);
					alarm.setUn(model.getWeek(), false);
					alarm.setEnable(true);
				} else {
					alarm.set(model.getWeek(), false);
					alarm.setUn(model.getWeek(), true);
					alarm.setEnable(true);
				}
				mapData.put("plan" + data.get(i).getType(), alarm);
			}
		}
//		alarmBo.updateByMap(ContantsUtil.DEFAULT_TEMP_UID, ContantsUtil.curUser.getService_uid(), mapData);
		preference.putBoolean(Preference.HAS_UPDATE, true);
		user.setPlan_update(System.currentTimeMillis());
		userBo.updateUser(user);
	}

	private void choosePlan(View v) {
		if (planDialog == null) {
			planDialog = new PlanPopDialog(context);
			planDialog.setCallBack(new PlanPopDialog.CallBack() {
				@Override
				public void callBack(int model) {
					Plan plan = planDialog.getModel(model);
					planView.setText(plan.getName());
					data.clear();
					data.addAll(new AlarmService().convertClock(plan.getDays()));
					adapter.notifyDataSetChanged();
				}
			});
		}
		planDialog.showAsDropDown(v);
	}

	private void openDialo() {
                Toast.makeText(this, "功能正在开发中....", Toast.LENGTH_LONG).show();
		String tag = "sugar_timeset_fragment";
		FragmentManager manager = context.getSupportFragmentManager();
		PlanTimeFragment tempFragment = (PlanTimeFragment) context
				.getSupportFragmentManager().findFragmentByTag(tag);
		if (tempFragment == null) {
			tempFragment = PlanTimeFragment.Instance();
		}
		tempFragment.show(manager, tag);
	}

	public Preference getPreference() {
		return preference;
	}
}
