package com.zfg.org.myexample.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.zfg.org.myexample.activity.MainActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.utils.Preference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-10-26.
 */

public class SoftWareUpdateService extends Service {
    // 标题
    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;
    // 文件存储
    private File updateDir = null;
    private File updateFile = null;

    // 通知栏
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;
    // 通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;

    private Context context;
    private Notification.Builder builder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            updateDir = new File(Environment.getExternalStorageDirectory(), "app/download/");
            updateFile = new File(updateDir.getPath(), "nxlgg.apk");
        }
        updateIntent = new Intent(this, MainActivity.class);
        updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//      构造Notification
        this.updateNotification = new Notification.Builder(context)
                .setTicker("开始下载")
                .setContentTitle("开始下载1")
                .setWhen(System.currentTimeMillis())
                //                .setContentText()
                .setProgress(100, 75, false)
                //                                            .setContent(myRemoteView)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(updatePendingIntent)
                .setContentInfo("Info")
                .setDefaults(Notification.DEFAULT_ALL) //设置默认的提示音，振动方式，灯光
                .setAutoCancel(true)//打开程序后图标消失
                //                .setLargeIcon(aBitmap)
                .build();

//      this.updateNotification = new Notification();

        // 设置<strong>下载</strong>过程中，点击<strong>通知</strong>栏，回到主界面

        // 设置<strong>通知</strong>栏<strong>显示</strong>内容
//      updateNotification.icon = R.mipmap.ic_launcher;
//      updateNotification.tickerText = "开始下载";
//      updateNotification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "下载进度0%", updatePendingIntent);
        // 发出<strong>通知</strong>
        updateNotificationManager.notify(0, updateNotification);
        // 开启一个新的线程<strong>下载</strong>，如果使用Service同步<strong>下载</strong>，会导致ANR问题，Service本身也会阻塞
        new Thread(new updateRunnable()).start();// 这个是<strong>下载</strong>的重点，是<strong>下载</strong>的过程
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressWarnings("deprecation")
    private Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    updateNotification.flags |= updateNotification.FLAG_AUTO_CANCEL;
                    // 点击<strong>安装</strong>PendingIntent
                    Uri uri = Uri.fromFile(updateFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    updatePendingIntent = PendingIntent.getActivity(SoftWareUpdateService.this, 0, installIntent, 0);

//                    updateNotification.setLatestEventInfo(SoftWareUpdateService.this, getResources().getString(R.string.app_name), "下载完成,点击安装。", updatePendingIntent);

                    updateNotificationManager.notify(0, updateNotification);
                    // 停止服务
                    stopService(updateIntent);
                    install();
                    break;
                case DOWNLOAD_FAIL:
                    // <strong>下载</strong>失败

//				        updateNotification.setLatestEventInfo(UpdateService.this,
//						getResources().getString(R.string.app_name), "下载失败。",
//						updatePendingIntent);
                    builder.setTicker("下载失败");
//                    builder.setContentTitle(title); //设置标题
                    builder.setContentIntent(updatePendingIntent);
                    updateNotification = builder.getNotification();
                    updateNotificationManager.notify(0, updateNotification);
                    break;
                default:
                    stopService(updateIntent);
            }
        }
    };

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try {
                // 增加权限<uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE">;
                if (!updateDir.exists()) {
                    updateDir.mkdirs();
                }
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                }
                // <strong>下载</strong>函数，以QQ为例子
                // 增加权限<uses-permission
                // android:name="android.permission.INTERNET">;
                String downUrl = Preference.instance(SoftWareUpdateService.this)
                        .getString(Preference.UPDATE_URL);
                long downloadSize = downloadUpdateFile(downUrl, updateFile);
                if (downloadSize > 0) {
                    // <strong>下载</strong>成功
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                // <strong>下载</strong>失败
                updateHandler.sendMessage(message);
            }
        }
    }

    //  下载更新文件
    @SuppressWarnings("deprecation")
    public long downloadUpdateFile(String downloadUrl, File saveFile)
            throws Exception {
        // 这样的<strong>下载</strong>代码很多，我就不做过多的说明
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                // 为了防止频繁的<strong>通知</strong>导致<strong>应用</strong>吃紧，百分比增加10才<strong>通知</strong>一次
                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 5 > downloadCount) {
                    downloadCount += 5;

//                    updateNotification.setLatestEventInfo(
//                            UpdateService.this,
//                            "正在下载"
//                                    + getResources().getString(
//                                    R.string.app_name), "下载进度"
//                                    + (int) totalSize * 100 / updateTotalSize
//                                    + "%", updatePendingIntent);

                    builder.setTicker("正在下载");
//                    builder.setContentTitle(title); //设置标题
                    builder.setProgress(100, (int) totalSize * 100 / updateTotalSize + 9, false);
                    builder.setContentText("当前下载进度："+(int) totalSize * 100 / updateTotalSize + "%"); //消息内容
                    builder.setContentIntent(updatePendingIntent);
                    updateNotification = builder.getNotification();
                    updateNotificationManager.notify(0, updateNotification);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    //  安装
    public void install() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(updateFile),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}
