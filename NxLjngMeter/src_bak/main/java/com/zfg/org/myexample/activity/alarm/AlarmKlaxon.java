package com.zfg.org.myexample.activity.alarm;

import java.io.IOException;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.db.AlarmBo;
import com.zfg.org.myexample.db.dao.Alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.media.AudioManager.OnAudioFocusChangeListener;

/*
 * 闹钟启动用到的服务，主要用来播放铃声与震动
 */
public class AlarmKlaxon extends Service {

	private static final int ALARM_TIMEOUT_SECONDS = 10 * 60;

	private static final long[] sVibratePattern = new long[] { 500, 500 };

	private boolean mPlaying = false;
	private Vibrator mVibrator;
	private MediaPlayer mMediaPlayer;
	private Alarm mCurrentAlarm;
	private long mStartTime;
	private TelephonyManager mTelephonyManager;
	private int mInitialCallState;
	private AudioManager mAudioManager = null;
	private boolean mCurrentStates = true;

	// Internal messages
	private static final int KILLER = 1;
	private static final int FOCUSCHANGE = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case KILLER:
				sendKillBroadcast((Alarm) msg.obj);
				stopSelf();
				break;
			case FOCUSCHANGE:
				switch (msg.arg1) {
				case AudioManager.AUDIOFOCUS_LOSS:

					if (!mPlaying && mMediaPlayer != null) {
						stop();
					}
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:

					if (!mPlaying && mMediaPlayer != null) {
						mMediaPlayer.pause();
						mCurrentStates = false;
					}
					break;
				case AudioManager.AUDIOFOCUS_GAIN:
					if (mPlaying && !mCurrentStates) {
						// play(mCurrentAlarm);
					}
					break;
				default:

					break;
				}
			default:
				break;

			}
		}
	};

	/**
	 * 电话打进
	 */
	private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String ignored) {
			if (state != TelephonyManager.CALL_STATE_IDLE
					&& state != mInitialCallState) {
				sendKillBroadcast(mCurrentAlarm);
				stopSelf();
			}
		}
	};

	@Override
	public void onCreate() {
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		// Listen for incoming calls to kill the alarm.
		mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyManager.listen(mPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		AlarmAlertWakeLock.acquireCpuWakeLock(this);
	}

	@Override
	public void onDestroy() {
		stop();
		// Stop listening for incoming calls.
		mTelephonyManager.listen(mPhoneStateListener, 0);
		AlarmAlertWakeLock.releaseCpuLock();
		mAudioManager.abandonAudioFocus(mAudioFocusListener);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// No intent, tell the system not to restart us.
		if (intent == null) {
			stopSelf();
			return START_NOT_STICKY;
		}

		final Alarm alarm = intent
				.getParcelableExtra(AlarmBo.ALARM_INTENT_EXTRA);

		if (alarm == null) {
			stopSelf();
			return START_NOT_STICKY;
		}

		if (mCurrentAlarm != null) {
			sendKillBroadcast(mCurrentAlarm);
		}

		// 启动音乐service
		playShot(alarm);

		mVibrator.vibrate(sVibratePattern, 1);

		mCurrentAlarm = alarm;
		mInitialCallState = mTelephonyManager.getCallState();
		return START_STICKY;
	}

	private void sendKillBroadcast(Alarm alarm) {
		long millis = System.currentTimeMillis() - mStartTime;
		int minutes = (int) Math.round(millis / 60000.0);
		Intent alarmKilled = new Intent(AlarmBo.ALARM_KILLED);
		alarmKilled.putExtra(AlarmBo.ALARM_INTENT_EXTRA, alarm);
		alarmKilled.putExtra(AlarmBo.ALARM_KILLED_TIMEOUT, minutes);
		sendBroadcast(alarmKilled);
	}

	private static final float IN_CALL_VOLUME = 0.125f;

	private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
		public void onAudioFocusChange(int focusChange) {
			mHandler.obtainMessage(FOCUSCHANGE, focusChange, 0).sendToTarget();
		}
	};

	/**
	 * 只播放一次
	 * 
	 * @param alarm
	 */
	private void playShot(Alarm alarm) {
		mAudioManager.requestAudioFocus(mAudioFocusListener,
				AudioManager.STREAM_ALARM,
				AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
		stop();
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnErrorListener(new OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mp.stop();
				mp.release();
				mMediaPlayer = null;
				return true;
			}
		});
		mMediaPlayer.setVolume(IN_CALL_VOLUME, IN_CALL_VOLUME);
		try {
			setDataSourceFromResource(getResources(), mMediaPlayer,
					R.raw.in_call_alarm);
			startAlarmShot(mMediaPlayer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		enableKiller(alarm);
		mPlaying = true;
		mStartTime = System.currentTimeMillis();
	}

	private void startAlarmShot(MediaPlayer player) throws IOException,
			IllegalArgumentException, IllegalStateException {
		final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			player.setAudioStreamType(AudioManager.STREAM_ALARM);
			player.prepare();
			player.start();
		}
	}

	private void setDataSourceFromResource(Resources resources,
			MediaPlayer player, int res) throws IOException {
		AssetFileDescriptor afd = resources.openRawResourceFd(res);
		if (afd != null) {
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			afd.close();
		}
	}

	/**
	 * Stops alarm audio and disables alarm if it not snoozed and not repeating
	 */
	public void stop() {
		if (mPlaying) {
			mPlaying = false;

			Intent alarmDone = new Intent(AlarmBo.ALARM_DONE_ACTION);
			sendBroadcast(alarmDone);

			// Stop audio playing
			if (mMediaPlayer != null) {
				mMediaPlayer.stop();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}

			// Stop vibrator
			mVibrator.cancel();
		}
		disableKiller();
	}

	/**
	 * Kills alarm audio after ALARM_TIMEOUT_SECONDS, so the alarm won't run all
	 * day.
	 * 
	 * This just cancels the audio, but leaves the notification popped, so the
	 * user will know that the alarm tripped.
	 */
	private void enableKiller(Alarm alarm) {
		mHandler.sendMessageDelayed(mHandler.obtainMessage(KILLER, alarm),
				1000 * ALARM_TIMEOUT_SECONDS);
	}

	private void disableKiller() {
		mHandler.removeMessages(KILLER);
	}

}
