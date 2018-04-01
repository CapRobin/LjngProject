package com.zfg.org.myexample;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.activity.MainActivity;
import com.zfg.org.myexample.adapter.DeviceAdapter;
import com.zfg.org.myexample.application.DbApplication;
import com.zfg.org.myexample.ble.Utils.GattAttributes;
import com.zfg.org.myexample.ble.Utils.Utils;

import com.zfg.org.myexample.ble.bean.MDevice;
import com.zfg.org.myexample.ble.bean.MService;
import com.zfg.org.myexample.ble.service.BluetoothLeService;
import com.zfg.org.myexample.db.UserBo;
import com.zfg.org.myexample.db.dao.User;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.widget.TabWidget;

public class BleActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.devicelist)
    private ListView bluthList;

    //  回退按钮
    @ViewInject(id = R.id.back_btn)
    private Button backBtn;
    //蓝牙数据上传的回退按钮
    @ViewInject(id = R.id.back_btn_upload)
    private Button backBtnUpload;

    //  搜索按钮
    @ViewInject(id = R.id.serch_btn)
    private Button serchButton;

    private Button BtnBledataShowClose;

    private String currentDevAddress;
    private String currentDevName;
    private List<MDevice> data;
    private static BluetoothAdapter mBluetoothAdapter;
    private DeviceAdapter adapter;
    private static final long SCAN_PERIOD = 3000;
    private boolean scaning;
    private BluetoothLeScanner bleScanner;
    private Handler hander;

    private DbApplication myApplication;

    private final List<BluetoothGattCharacteristic> list = new ArrayList<>();

    private DialogLoading loading;
    public static BleActivity activity;
    private Preference preference;
    private User user;
    private UserBo userBo;
    private SimpleAdapter sim_adapter_grid1;//grid1数据适配器
    private SimpleAdapter sim_adapter_bleDataList;//bleDataList数据适配器
    private GridView grid1;

    private TextView textTip;

    private Button uploadAllBtn;
    private Button btn_delete_all;
    private ListView bleDataList;//grid1的具体数据呈现

    private boolean stopFlag;
    public static boolean ComBleActivityIsExsit;//页面是否创建

    //消息循环shit
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                loading.dismiss();
                Toast.makeText(context, msg.getData().getString("title"), Toast.LENGTH_SHORT).show();
                new tabHostOnTabChangedListener().onTabChanged("tab2");
            }
            else if (msg.what ==2) {
                if (loading.isShowing()) {
                    Toast.makeText(context, msg.getData().getString("title"), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }
            super.handleMessage(msg);
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComBleActivityIsExsit = false;
        if (MainActivity.controlFlag == 0)//蓝牙抄表flag
        {
//        zhdl 蓝牙扫描界面
            setContentView(R.layout.tab_test);

            //获取TabHost对象
            TabHost tabHost = (TabHost) findViewById(R.id.tab_test);
//开始设置tabHost
            tabHost.setup();
//新建一个newTabSpec,设置标签（选项卡名称）和图标(setIndicator),设置内容(setContent)
            tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("蓝牙抄表", getResources().getDrawable(android.R.drawable.stat_sys_data_bluetooth)).setContent(R.id.item1));
            tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("数据上传", getResources().getDrawable(android.R.drawable.ic_menu_upload)).setContent(R.id.item2));
//设置TabHost的背景颜色
//tabHost.setBackgroundColor(Color.argb(150,22,70,150));
//设置TabHost的背景图片资源
//tabHost.setBackgroundResource(R.drawable.blue_button);

            TabWidget tabWidget = tabHost.getTabWidget();
            for (int i = 0; i < 2; i++) {
                View view = tabWidget.getChildTabViewAt(i);
//            view.setBackgroundColor(R.color.white);
                //设置tab背景颜色,对应配置文件的tab_bg.xml,可变化的背景,选中时为白色,未选中为黑色
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button));
            }
            tabHost.setOnTabChangedListener(new tabHostOnTabChangedListener());
            grid1 = (GridView) findViewById(R.id.grid1);
            textTip = (TextView) findViewById(R.id.texttip);
            uploadAllBtn = (Button) findViewById(R.id.btn_upload_all);//全部上传按钮
            btn_delete_all = (Button) findViewById(R.id.btn_delete_all); //全部删除
            BtnBledataShowClose = (Button) findViewById(R.id.BtnBledataShowClose); //关闭按钮

//        bledata = new ArrayList<BleDataModel>();
        }
        else if (MainActivity.controlFlag == 1)//蓝牙编程flag
        {
            setContentView(R.layout.activity_ble);
        }
        hander = new Handler();

        preference = Preference.instance(context);
        activity = (BleActivity) context;
        loading = new DialogLoading(activity);
//        userBo = new UserBo(this);
//        user = userBo.getUserByServerId(ContantsUtil.curUser.getId().toString());

        initActivity(MainActivity.controlFlag);
        initData();

        adapter = new DeviceAdapter(context, data);
        bluthList.setAdapter(adapter);
        bluthList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!scaning) {
                    stopFlag = false;
                    loading.show();
                    doTimeOut(15,"连接超时,请重试!");//5.24修改
//                    hander.postDelayed(dismssDialogRunnable, 10000);//5.24修改
                    connectDevice(data.get(position).getDevice());
                }
            }
        });
        checkBleSupportAndInitialize();
        registerReceiver(mGattUpdateReceiver, Utils.makeGattUpdateIntentFilter());
        Intent gattServiceIntent = new Intent(getApplicationContext(), BluetoothLeService.class);
        startService(gattServiceIntent);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initActivity(int flag) {//是否需要上传按钮的事件监听
        serchButton.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        if (flag ==0) {
            backBtnUpload.setOnClickListener(this);
            uploadAllBtn.setOnClickListener(this);//上传全部抄表数据
            btn_delete_all.setOnClickListener(this);
            grid1.setOnItemClickListener(new ItemClickListener());
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Ble Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class tabHostOnTabChangedListener implements TabHost.OnTabChangeListener//tab页切换事件
    {
        @Override
        public void onTabChanged(String tabId) {
            if (tabId.equals("tab1")) {
                Toast.makeText(context, "蓝牙抄表", Toast.LENGTH_SHORT).show();
            }
            if (tabId.equals("tab2")) {
                Toast.makeText(context, "数据上传", Toast.LENGTH_SHORT).show();
                String[] from = {"meterNo", "cmdName", "createDate", "createName"};
                int[] to = {R.id.text_item0, R.id.text_item1, R.id.text_item2, R.id.text_item3};
                BleDbHelper helper = new BleDbHelper();
                String[] refStr = new String[1];
                refStr[0] = "";
//                data_list = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> data_list;
                data_list = helper.QuerybleOperateData(refStr);
                textTip.setText(refStr[0]);
                sim_adapter_grid1 = new SimpleAdapter(BleActivity.this, data_list, R.layout.ble_upload_item, from, to);
                //配置适配器
                grid1.setAdapter(sim_adapter_grid1);
            }
        }
    }

    //当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class ItemClickListener implements OnItemClickListener {
        private Button BtnBledataShowClose;
        private TextView BledataShowTitle;
        private AlertDialog.Builder builder;
        private View view;
        private Dialog DiaBledataShow;
        public ItemClickListener()
        {

        }
        class CloseClickListener implements View.OnClickListener
        {
            @Override
            public void onClick(View v)
            {
                DiaBledataShow.dismiss();
            }

        }

        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
            builder = new AlertDialog.Builder(BleActivity.this);
            view = LayoutInflater.from(BleActivity.this).inflate(R.layout.activity_bledata_show,null);
            builder.setView(view);
            BtnBledataShowClose = (Button) view.findViewById(R.id.BtnBledataShowClose);
            BledataShowTitle = (TextView)view.findViewById(R.id.title);
            BtnBledataShowClose.setOnClickListener(new CloseClickListener());
            //在本例中arg2=arg3
            HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
            //setTitle((String)item.get("operateId"));

//            builder.setTitle((String)item.get("cmdName"));
            BledataShowTitle.setText((String)item.get("cmdName"));
            String strOperateId = (String)item.get("OperateId");
            int operateId = Integer.parseInt(strOperateId);
            List<Map<String, Object>> list = new BleDbHelper().QueryBleData(operateId);
            String[] from = {"item","data","unit"};
            int[] to = {R.id.data_name, R.id.data_data, R.id.data_unit};
            sim_adapter_bleDataList = new SimpleAdapter(BleActivity.this, list, R.layout.item_bledata, from, to);
            bleDataList = (ListView)view.findViewById(R.id.bleDataList);
            bleDataList.setAdapter(sim_adapter_bleDataList);
            DiaBledataShow =builder.show();


//            bledataadapter.notifyDataSetChanged();


        }

    }

    //  初始化功能
    private void initData() {
        data = new ArrayList<MDevice>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                stopFlag = true;
                finish();
                break;
            case R.id.back_btn_upload:
                finish();
                break;
            case R.id.serch_btn:
                data.clear();
                adapter.notifyDataSetChanged();
                stopFlag = false;
                doTimeOut(15,"没有扫描到(更多)蓝牙设备!");
                loading.show();
                startScan();//5.24修改
//                onRefresh();//5.24修改
                break;
            case R.id.btn_upload_all:
                if (textTip.getText().toString().equals("共有0条抄表数据需要上传")) {
                    Toast.makeText(this, "无可上传的数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadAll();
                break;
            case R.id.btn_delete_all:
                BleDbHelper helper = new BleDbHelper();
                if (helper.delete()==true)
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.BtnBledataShowClose:
//                DiaBledataShow.dismiss();
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果有连接先关闭连接
        disconnectDevice();
        SystemClock.sleep(100);
    }

    private Runnable dismssDialogRunnable = new Runnable() {
        @Override
        public void run() {
            if ( loading!= null)
                loading.dismiss();
            disconnectDevice();
//            Snackbar.make(clContent, R.string.connect_fail, Snackbar.LENGTH_LONG).show();
        }
    };    /**
     * 获得蓝牙适配器
     */
    private void checkBleSupportAndInitialize() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.device_ble_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//      蓝牙适配器获取
        mBluetoothAdapter = bluetoothManager.getAdapter();
//      如果蓝牙适配器为空
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.device_ble_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
//      如果蓝牙适配器启用
        if (!mBluetoothAdapter.isEnabled()) {
//          启用蓝牙适配器
            mBluetoothAdapter.enable();
        }
    }


    public void searchDevice() {
        if (!scaning) {
            scaning = true;
        }
    }

    public void onRefresh() {
        // Prepare list view and initiate scanning
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        startScan();
    }

    private void startScan() {
//        zhdl 判断操作系统版本号， 用于处理不同蓝牙设备
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            scanPrevious21Version();
        } else {
            scanAfter21Version();
        }
    }

    /**
     * 版本号21之前的调用该方法搜索
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
     * Call back for BLE Scan
     * This call back is called when a BLE device is found near by.
     * 发现设备时回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            System.out.println("--------------------->蓝牙回调");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MDevice mDev = new MDevice(device, rssi);
                    if (data.contains(mDev))
                        return;
                    data.add(mDev);
                }
            });
        }
    };

    /**
     * 版本号21及之后的调用该方法扫描
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
                if (data.contains(mDev))
                    return;
                data.add(mDev);
                adapter.notifyDataSetChanged();
                stopFlag = true;
                if (loading.isShowing())
                    loading.dismiss();
//                        loading.hide();
            }
        });
    }

    /*连接设备*/
    private void connectDevice(BluetoothDevice device) {
        /*设备地址*/
        currentDevAddress = device.getAddress();
        /*设备名称*/
        currentDevName = device.getName();
/*如果蓝牙连接状态不为断开*/
        if (BluetoothLeService.getConnectionState() != BluetoothLeService.STATE_DISCONNECTED)
            /*断开连接*/
            BluetoothLeService.disconnect();
        /*连接设备*/
        BluetoothLeService.connect(currentDevAddress, currentDevName, this);
    }

    /*断开连接*/
    private void disconnectDevice() {
        BluetoothLeService.disconnect();
    }

    /**
     * 广播接收器，用于接收GATT通信状态
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*获取操作类型*/
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
//                System.out.println("--------------------->连接成功");
//              搜索服务
                stopFlag = true;
                BluetoothLeService.discoverServices();
            }
            // 从GATT服务器发现的服务
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
//              移除回调
//                hander.removeCallbacks(dismssDialogRunnable);//5.24修改
                prepareGattServices(BluetoothLeService.getSupportedGattServices());
            } else if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)) {
                //connect break (连接断开)
//                showDialog(getString(R.string.conn_disconnected_home));
            }
        }
    };


    /**
     * 获得服务
     *
     * @param gattServices
     */
    private void prepareGattServices(List<BluetoothGattService> gattServices) {
        if (ComBleActivityIsExsit==false) {
        loading.dismiss();
//        loading.hide();
        prepareData(gattServices);
//
        MService mService = ((DbApplication) getApplication()).getServices().get(0);
//      获取服务
        BluetoothGattService service = mService.getService();
//      设置服务 转到蓝牙命令收发界面
//        Intent addAccountIntent = new Intent(Settings.ACTION_ADD_ACCOUNT);
//        addAccountIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TA‌​SK_RESET);
//        addAccountIntent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"com.app.yourapp"});
//zhdl 载入蓝牙通讯界面
        Intent intent = new Intent(this, ComBleActivity.class);
        intent.putExtra("dev_name", currentDevName);
        intent.putExtra("dev_mac", currentDevAddress);
//      启动ServicesActivity界面
//        System.out.println("shitao 001");

            startActivity(intent);
            ComBleActivityIsExsit= true;

//        System.out.println("shitao 002");
//      界面切换动画
        overridePendingTransition(0, 0);
        }
    }


    /**
     * 准备GATTE服务的数据。
     *
     * @param gattServices
     */
    private void prepareData(List<BluetoothGattService> gattServices) {
//      如果没有gatt服务则退出
        if (gattServices == null)
            return;
//
        List<MService> list = new ArrayList<>();
        for (BluetoothGattService gattService : gattServices) {
//          获取UUID
            String uuid = gattService.getUuid().toString();
//          如果uuid 等于通用访问
            if (uuid.equals(GattAttributes.GENERIC_ACCESS_SERVICE) || uuid.equals(GattAttributes.GENERIC_ATTRIBUTE_SERVICE))
                continue;
//
            if (uuid.equals(GattAttributes.USR_SERVICE)) {
                String name = GattAttributes.lookup(gattService.getUuid().toString(), "UnkonwService");
//
                MService mService = new MService(name, gattService);
//          列表添加服务
                list.add(mService);
            }
        }
//      设置服务表
        ((DbApplication) getApplication()).setServices(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//      反注册广播
        unregisterReceiver(mGattUpdateReceiver);
    }

    private void uploadAll()//上传全部入库数据
    {
        loading.show();
        try {
            UploadDataGasThread t = new UploadDataGasThread();
            new Thread(t).start();
            // 创建一个子线程, 子线程发起 身份认证webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UploadDataGasThread implements Runnable {//上传全部入库数据类
        private boolean uploadFlag;

        public UploadDataGasThread() {
            uploadFlag = false;
        }

        public void run() {
            uploadAll();
            if (uploadFlag == true)
                sendMessage("上传数据成功!");
            else
                sendMessage("上传数据失败!");
        }

        private void sendMessage(String msg) {
            Message m = handler.obtainMessage();
            m.arg1 = 1;
            Bundle bundle = new Bundle();
            m.what = 1;
            bundle.putString("title", msg);
            m.setData(bundle);
            handler.sendMessage(m);
        }

        private void uploadAll() {
            BleDbHelper helper = new BleDbHelper();
            List<Map<String, Object>> dataList = helper.getUnUpLoadOperateInfo();
            String id = "";
            String nextId = "";
            ArrayList<String> meterDataList = new ArrayList<>();
            String meterAddr = "";
            String cmd = "";
            String data = "";
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> map = dataList.get(i);
                id = (String) map.get("OperateId");
                if (((id.equals(nextId) == false) && (nextId.equals("") == false)) ||(i==dataList.size()-1))
                {
                    if (i==dataList.size()-1)
                    {
                        data =(String) map.get("data");
                        data =data.replaceAll("[^(0-9.)]", "");
                        meterDataList.add(data);
                    }
                    Map<String, Object> uploadMap = packUploaData(meterAddr, cmd, meterDataList);
                    if (upload(uploadMap) == false)//上传
                    {
                        this.uploadFlag = false;
                        return;
                    } else {
                        helper.updateOperateId(Integer.parseInt(nextId));//更新上传标志
                    }
                    meterDataList.clear();
                    data =(String) map.get("data");
                    data =data.replaceAll("[^(0-9.)]", "");
                    meterDataList.add(data);
                } else {
                    meterAddr = (String) map.get("meterNo");
                    cmd = (String) map.get("cmd");
                    data =(String) map.get("data");
                    data =data.replaceAll("[^(0-9.)]", "");
                    meterDataList.add(data);
                }
                if (id.equals(nextId) == false) {
                    nextId = id;
                }
                // activity.UploadDataGas(listStr);
            }
            this.uploadFlag = true;
        }

        public boolean upload(Map<String, Object> map) {
            boolean flag = false;
            String result = HttpServiceUtil.post(ContantsUtil.URL_UploadDataGas, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        flag = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }

        private Map<String, Object> packUploaData(String meterAddr, String cmd, ArrayList<String> list)//打包全部上传的数据 数据库上传
        {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);//CommonUtil.AddZeros(addr.getText().toString())
                jsobj.put("CMD", cmd);//CMD
                jsobj.put("strUser",BleDbHelper.userName);
                String data = "";
                for (int i = 0; i < list.size(); i++) {
                    String dataStr = list.get(i);
                    data = data + dataStr + ",";
                }
                data = data.substring(0, data.length() - 1);//去掉最后的","
                jsobj.put("data", data);

            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            return map;
        }
    }

    private void doTimeOut(int second,String tip)//超时计算
    {
        try {
            TimeOutThread t = new TimeOutThread(second,tip);
            new Thread(t).start();
        } catch (Exception e
                ) {
            e.printStackTrace();
            return;
        }
    }

    class TimeOutThread implements Runnable {//超时线程 当超过规定时间,退出loding 并提示"超时"
        private int second;
        private String tip;

        public TimeOutThread(int second,String tip) {
            this.second = second;
            this.tip = tip;
        }

        public void run() {
            try {
                int i = 0;
                while (i < second*10)
                {
                    if (stopFlag ==true)
                        return;
                    Thread.sleep(100);
                    i++;
                }
                if (stopFlag==false) {
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


}
