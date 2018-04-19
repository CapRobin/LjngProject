package com.zfg.org.myexample.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.ComBleActivity;
import com.zfg.org.myexample.R;
import com.zfg.org.myexample.ViewInject;
import com.zfg.org.myexample.adapter.DeviceAdapter;
import com.zfg.org.myexample.application.DbApplication;
import com.zfg.org.myexample.ble.Utils.GattAttributes;
import com.zfg.org.myexample.ble.Utils.Utils;
import com.zfg.org.myexample.ble.bean.MDevice;
import com.zfg.org.myexample.ble.bean.MService;
import com.zfg.org.myexample.ble.service.BluetoothLeService;
import com.zfg.org.myexample.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 LJNG All rights reserved.
 * Name：BluetoothReadMeterActivity
 * Describe：蓝牙抄表
 * Date：2018-04-14 17:25:14
 * Author: CapRobin@yeah.net
 */
public class BlueReadMeterActivity extends BasicActivity {

    private Preference preference;
    private DialogLoading loading;
    private List<MDevice> deviceListData;
    private DeviceAdapter deviceAdapter;
    private BluetoothLeScanner bleScanner;
    private static BluetoothAdapter mBluetoothAdapter;
    private Handler hander;
    private boolean scaning;
    private boolean stopFlag;
    //扫描周期时长
    private static final long SCAN_PERIOD = 3000;
    private String currentDevAddress;
    private String currentDevName;

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.scanBowBtn)
    private Button scanBowBtn;
    @ViewInject(id = R.id.devicelist)
    private ListView bluthList;
    public static boolean ComBleActivityIsExsit;//页面是否创建

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_read_meter);
        preference = Preference.instance(context);
        initActivityView();
    }

    /**
     * Describe：初始化ActivityView
     * Params:
     * Date：2018-04-16 16:11:40
     */
    private void initActivityView() {
        pageTitle.setText("蓝牙抄表");
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        scanBowBtn.setOnClickListener(this);
        hander = new Handler();

        deviceListData = new ArrayList<MDevice>();
        deviceAdapter = new DeviceAdapter(context, deviceListData);
        loading = new DialogLoading(this);
        bluthList.setAdapter(deviceAdapter);
        bluthList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!scaning) {
                    stopFlag = false;
                    loading.show();
                    doTimeOut(15, "连接超时,请重试!");//5.24修改
//                    hander.postDelayed(dismssDialogRunnable, 10000);//5.24修改
                    connectDevice(deviceListData.get(position).getDevice());
                }
            }
        });
        //初始化蓝牙设备
        initializeBluetooth();
        //注册服务
        registerReceiver(mGattUpdateReceiver, Utils.makeGattUpdateIntentFilter());
        //启动蓝牙服务
        Intent gattServiceIntent = new Intent(getApplicationContext(), BluetoothLeService.class);
        startService(gattServiceIntent);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
                setToast("蓝牙抄表");
                break;
            case R.id.scanBowBtn:
                deviceListData.clear();
                deviceAdapter.notifyDataSetChanged();
                stopFlag = false;
                doTimeOut(50, "没有扫描到(更多)蓝牙设备!");
                loading.show();
                startScan();//5.24修改
//                onRefresh();//5.24修改
                break;
            default:
                break;
        }
    }


    /**
     * Describe：广播接收器，用于接收GATT通信状态
     * Params:
     * Date：2018-04-16 18:20:25
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取操作类型
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                System.out.println("--------------------->连接成功");
                //搜索服务
                stopFlag = true;
                BluetoothLeService.discoverServices();
            }
            //从GATT服务器发现的服务
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //移除回调
                //hander.removeCallbacks(dismssDialogRunnable);//5.24修改
                prepareGattServices(BluetoothLeService.getSupportedGattServices());
            } else if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)) {
                //connect break (连接断开)
//                showDialog(getString(R.string.conn_disconnected_home));
            }
        }
    };

    /**
     * Describe：获得服务
     * Params:
     * Date：2018-04-16 18:22:22
     */
    private void prepareGattServices(List<BluetoothGattService> gattServices) {
        if (ComBleActivityIsExsit == false) {
            loading.dismiss();
            prepareData(gattServices);
            MService mService = ((DbApplication) getApplication()).getServices().get(0);
            //获取服务
            BluetoothGattService service = mService.getService();
            //设置服务 转到蓝牙命令收发界面
//        Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
//        addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TA‌​SK_RESET);
//        addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"com.app.yourapp"});
            //载入蓝牙通讯界面
            Intent intent = new Intent(this, ComBleActivity.class);
            intent.putExtra("dev_name", currentDevName);
            intent.putExtra("dev_mac", currentDevAddress);
            System.out.println("shitao 001");
            //启动ServicesActivity界面
            startActivity(intent);
            ComBleActivityIsExsit = true;
            //界面切换动画
            overridePendingTransition(0, 0);
        }
    }

    /**
     * Describe：准备GATTE服务的数据
     * Params:
     * Date：2018-04-16 18:25:22
     */
    private void prepareData(List<BluetoothGattService> gattServices) {
        //如果没有gatt服务则退出
        if (gattServices == null)
            return;
        List<MService> list = new ArrayList<>();
        for (BluetoothGattService gattService : gattServices) {
            //获取UUID
            String uuid = gattService.getUuid().toString();
            //如果uuid 等于通用访问
            if (uuid.equals(GattAttributes.GENERIC_ACCESS_SERVICE) || uuid.equals(GattAttributes.GENERIC_ATTRIBUTE_SERVICE))
                continue;
            if (uuid.equals(GattAttributes.USR_SERVICE)) {
                String name = GattAttributes.lookup(gattService.getUuid().toString(), "UnkonwService");
                MService mService = new MService(name, gattService);
                //列表添加服务
                list.add(mService);
            }
        }
        //设置服务表
        ((DbApplication) getApplication()).setServices(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册广播
        unregisterReceiver(mGattUpdateReceiver);
    }

    /**
     * Describe：获得蓝牙适配器
     * Params:
     * Date：2018-04-16 17:26:40
     */
    private void initializeBluetooth() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.device_ble_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //蓝牙适配器获取
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //如果蓝牙适配器为空
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.device_ble_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        //如果蓝牙适配器启用
        if (!mBluetoothAdapter.isEnabled()) {
            //启用蓝牙适配器
            mBluetoothAdapter.enable();
        }
    }

    /**
     * Describe：开始扫描(判断操作系统版本号， 用于处理不同蓝牙设备)
     * Params:
     * Date：2018-04-16 17:41:02
     */
    private void startScan() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            scanPrevious21Version();
        } else {
            scanAfter21Version();
        }
    }

    /**
     * Describe：点击连接设备
     * Params:
     * Date：2018-04-16 17:44:26
     */
    private void connectDevice(BluetoothDevice device) {
        //设备地址
        currentDevAddress = device.getAddress();
        /*设备名称*/
        currentDevName = device.getName();
        //如果蓝牙连接状态不为断开
        if (BluetoothLeService.getConnectionState() != BluetoothLeService.STATE_DISCONNECTED)
            //断开连接
            BluetoothLeService.disconnect();
        //连接设备
        BluetoothLeService.connect(currentDevAddress, currentDevName, this);
    }

    /**
     * Describe：版本号21之前的调用该方法搜索
     * Params:
     * Date：2018-04-16 17:30:27
     */
    private void scanPrevious21Version() {
        hander.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, SCAN_PERIOD);
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }


    /**
     * Describe：版本号21及之后的调用该方法扫描
     * Params:
     * Date：2018-04-16 17:30:37
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scanAfter21Version() {
//        zhdl 启动一个独立线程 ，用于扫描蓝牙设备
        hander.postDelayed(new Runnable() {
            @Override
            public void run() {
                bleScanner.stopScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                    }
                });
            }
        }, SCAN_PERIOD);   //每间隔3s扫描一次


        if (bleScanner == null)
            bleScanner = mBluetoothAdapter.getBluetoothLeScanner();

//      此处需要增加扫描超时
        bleScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
//              此处界面添加内容
//                System.out.println("返回设备结果-->" + result.getDevice() + ":信号-->" + result.getRssi());
                super.onScanResult(callbackType, result);
                MDevice mDev = new MDevice(result.getDevice(), result.getRssi());
                if (deviceListData.contains(mDev))
                    return;
                deviceListData.add(mDev);
                deviceAdapter.notifyDataSetChanged();
                stopFlag = true;
                if (loading.isShowing())
                    loading.dismiss();
//                        loading.hide();
            }
        });
    }

    /**
     * Describe：扫描超时计算
     * Params:
     * Date：2018-04-16 17:16:44
     */

    private void doTimeOut(int second, String tip) {
        try {
            TimeOutThread t = new TimeOutThread(second, tip);
            new Thread(t).start();
        } catch (Exception e
                ) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Describe：超时线程 当超过规定时间,退出loding 并提示"超时"
     * Params:
     * Date：2018-04-16 17:18:23
     */
    class TimeOutThread implements Runnable {
        private int second;
        private String tip;

        public TimeOutThread(int second, String tip) {
            this.second = second;
            this.tip = tip;
        }

        public void run() {
            try {
                int i = 0;
                while (i < second * 10) {
                    if (stopFlag == true)
                        return;
                    Thread.sleep(100);
                    i++;
                }
                if (stopFlag == false) {
                    stopFlag = true;
                    android.os.Message m = handler.obtainMessage();
                    m.arg1 = 1;
                    Bundle bundle = new Bundle();
                    m.what = 2;
                    bundle.putString("title", tip);
                    m.setData(bundle);
                    handler.sendMessage(m);
                    return;
                }
            } catch (Exception e
                    )

            {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Describe：发现蓝牙设备时回调方法
     * Params:
     * Date：2018-04-16 17:39:28
     */

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            System.out.println("--------------------->蓝牙回调");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MDevice mDev = new MDevice(device, rssi);
                    if (deviceListData.contains(mDev))
                        return;
                    deviceListData.add(mDev);
                }
            });
        }
    };
}
