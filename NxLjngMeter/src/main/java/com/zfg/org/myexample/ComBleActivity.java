package com.zfg.org.myexample;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.BleDataAdapter;
import com.zfg.org.myexample.application.DbApplication;
import com.zfg.org.myexample.ble.Utils.Constants;
import com.zfg.org.myexample.ble.Utils.GattAttributes;
import com.zfg.org.myexample.ble.Utils.Utils;
import com.zfg.org.myexample.ble.adapter.CharacteristicsAdapter;
import com.zfg.org.myexample.ble.adapter.MessagesAdapter;
import com.zfg.org.myexample.ble.bean.Message;
import com.zfg.org.myexample.ble.service.BluetoothLeService;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.dialog.CheckListCommandDialog;
import com.zfg.org.myexample.dialog.CheckListDialog;
import com.zfg.org.myexample.dialog.TimeDialog;
import com.zfg.org.myexample.dto.CheckModel;
import com.zfg.org.myexample.model.BleDataModel;
import com.zfg.org.myexample.utils.CheckUtil;
import com.zfg.org.myexample.utils.CommonUtil;
import com.zfg.org.myexample.utils.Config;
import com.zfg.org.myexample.utils.ContantsUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.zfg.org.myexample.utils.ContantsUtil.HOST;
import static java.lang.Thread.sleep;

//import java.util.logging.Handler;


/**
 * Created by Administrator on 2016-05-27. zhdl，蓝牙通讯界面
 */
public class ComBleActivity extends BasicActivity implements View.OnClickListener {

//    static {
//        System.loadLibrary("mycode");
//        System.loadLibrary("myprocotol");
//    }

    //    public native String getStringFromNative();//
//    public native String getString_From_c();
    private HttpServiceUtil.CallBack dataCallback;

    public static ComBleActivity activity;

    private DbApplication myApplication;
    private final List<BluetoothGattCharacteristic> gattlist = new ArrayList<>();
    private CharacteristicsAdapter adapter;

    private final List<Message> list = new ArrayList<>();

    private MessagesAdapter madapter;
    private BluetoothGattCharacteristic notifyCharacteristic;
    private BluetoothGattCharacteristic readCharacteristic;
    private static BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic indicateCharacteristic;

    private DialogLoading loading;
    private boolean isHexSend;
    private boolean nofityEnable;
    private boolean indicateEnable;
    //  是否调试模式
    private boolean isDebugMode;

    private String properties;

    //上次关阀水量
    private String strWaterGfsl;

    //上次购水次数
    private String strWaterGscs;

    //身份认证返回，通讯返回rand
    private String strCmdAutoRand;


    //  操作标识
    private String optionflag;

    private String str645Data;//645数据

    private String rand2;//身份认证随机数2
    private String priceType;//更新气价 身份认证返回的bit位
    private String CMD;//上传CMD
    private int isUpLoad =0;//上传 1已上传 0 未上传

//    //  频率选择输入框
//    @ViewInject(id = R.id.fraq_Data)
//    private EditText fraq;

    //  地址输入框
    @ViewInject(id = R.id.meter_addr)
    private EditText addr;

    //  金额输入框
    @ViewInject(id = R.id.meter_charge)
    private EditText metercharge;

    @ViewInject(id = R.id.set_command_select)
    private EditText set_command_select;

    //  返回
    @ViewInject(id = R.id.back_btn)
    private Button backBtn;
    //  抄表
    @ViewInject(id = R.id.read_btn)
    private Button readBtn;

    @ViewInject(id = R.id.fraq_values)
    private RelativeLayout fraq_value;

    @ViewInject(id = R.id.command_select)
    private RelativeLayout command_select;

    @ViewInject(id = R.id.meter_groupcharge)//编程数据的输入界面
    private RelativeLayout meter_groupcharge;

    @ViewInject(id = R.id.meter_charge)
    private EditText meter_charge;

//    @ViewInject(id = R.id.set_fraq)
//    private Button setBtn;

//    @ViewInject(id = R.id.fraq_Data)
//    private EditText fraqdata;

//    //  当前气量
//    @ViewInject(id = R.id.strBackData1)
//    private EditText strBackData1;
//    //  当前结算周期气量
//    @ViewInject(id = R.id.strBackData2)
//    private EditText strBackData2;
//    // 剩余金额总
//    @ViewInject(id = R.id.strBackData3)
//    private EditText strBackData3;
//    // 剩余金额总
//    @ViewInject(id = R.id.strBackData4)
//    private EditText strBackData4;
//
//    // 购气次数本地
//    @ViewInject(id = R.id.strBackData5)
//    private EditText strBackData5;
//
//    // 剩余金额远程
//    @ViewInject(id = R.id.strBackData6)
//    private EditText strBackData6;
//
//    // 购气次数远程
//    @ViewInject(id = R.id.strBackData7)
//    private EditText strBackData7;

    @ViewInject(id = R.id.set_fraq_value)
    private EditText setfraqvalue;

    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;

    public Spinner meterTypeSelect;

    private String returncmd;

    private StringBuilder sb;

    private String authstr;

    private int fraqint;

    //  弹出框
//    private DinnerTimeDialog stageDialog;
//  频率选择框
    private CheckListDialog listDialog;
    //  操作命令选择
    private CheckListCommandDialog checkListCommandDialog;

    private TimeDialog timeDialog;

    private AlertDialogZfg alert;

    private CallBack authCallback;

//    private CallBack bluetoothoffCallback;
//
//    private CallBack bluetoothauthCallback;
//
//    private CallBack bluetoothonCallback;


    /*適配器*/
    private BleDataAdapter bledataadapter;
    private List<BleDataModel> bledata;

    private EditText set_fraq_value;//频率输入框

    private boolean stopFlag;//停止标志
    public static String setfraqvalueValue = "";

//消息循环shit
    private   android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg){
            if (msg.what ==1) {
                Toast.makeText(context, msg.getData().getString("title"), Toast.LENGTH_SHORT).show();
                loading.dismiss();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comble);
        activity = (ComBleActivity) context;
        loading = new DialogLoading(activity);
        initdata();
        bledataadapter = new BleDataAdapter(context, bledata);
        itemlist.setAdapter(bledataadapter);
        set_fraq_value = (EditText) findViewById(R.id.set_fraq_value);
        meterTypeSelect = (Spinner)findViewById(R.id.meter_type_select);
        initActivity();
        initCharacteristics();
        //initbloothauthCallBack();//气表身份认证
        registerReceiver(mGattUpdateReceiver, Utils.makeGattUpdateIntentFilter());
        prepareBroadcastDataNotify(notifyCharacteristic);
        this.stopFlag = true;//停止标志置为ture 禁止接收数据


    }





    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //There are four basic operations for moving data in BLE: read, write, notify,
            // and indicate. The BLE protocol specification requires that the maximum data
            // payload size for these operations is 20 bytes, or in the case of read operations,
            // 22 bytes. BLE is built for low power consumption, for infrequent short-burst data transmissions.
            // Sending lots of data is possible, but usually ends up being less efficient than classic Bluetooth
            // when trying to achieve maximum throughput.  从google查找的，解释了为什么android下无法解释超过
            //20个字节的数据
            Bundle extras = intent.getExtras();
            if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                // Data Received
                if (extras.containsKey(Constants.EXTRA_BYTE_VALUE)) {
//                    System.out.println("shitao 010");
                    if (extras.containsKey(Constants.EXTRA_BYTE_UUID_VALUE)) {
//                        System.out.println("shitao 011");
                        if (myApplication != null) {
//                            System.out.println("shitao 011");
                            BluetoothGattCharacteristic requiredCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(GattAttributes.USR_SERVICE), -1, -1);
//                            BluetoothGattCharacteristic requiredCharacteristic = myApplication.getCharacteristic();
                            String uuidRequired = requiredCharacteristic.getUuid().toString();

                            String receivedUUID = intent.getStringExtra(Constants.EXTRA_BYTE_UUID_VALUE);

                            byte[] array = intent.getByteArrayExtra(Constants.EXTRA_BYTE_VALUE);
                            Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE, formatMsgContent(array));
                            prasereturn(msg.getContent());
//                            System.out.println("shitao 005");
                        }
                    }
                }
                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE)) {
//                    System.out.println("shitao 006");
                    if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID)) {
                        BluetoothGattCharacteristic requiredCharacteristic = myApplication.getCharacteristic();
                        String uuidRequired = requiredCharacteristic.getUuid().toString();
                        String receivedUUID = intent.getStringExtra(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID);

                        byte[] array = intent.getByteArrayExtra(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE);

                        System.out.println("GattDetailActivity---------------------->descriptor:" + Utils.ByteArraytoHex(array));
                        if (isDebugMode) {

                        } else if (uuidRequired.equalsIgnoreCase(receivedUUID)) {

                        }
                    }
                }
            }

            if (action.equals(BluetoothLeService.ACTION_GATT_DESCRIPTORWRITE_RESULT)) {
//                System.out.println("shitao 007");
                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT)) {
                    int status = extras.getInt(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT);
                    if (status != BluetoothGatt.GATT_SUCCESS) {

                    }
                }
            }

            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_ERROR)) {
//                System.out.println("shitao 008");
                if (extras.containsKey(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE)) {
                    String errorMessage = extras.
                            getString(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE);
                    System.out.println("GattDetailActivity---------------------->err:" + errorMessage);
                }
            }

            //写characteristics成功
            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_WRITE_SUCCESS)) {

                System.out.println("ACTION_GATT_CHARACTERISTIC_WRITE_SUCCESS---------------------->");
            }

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
//                System.out.println("shitao 009");
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                if (state == BluetoothDevice.BOND_BONDING) {
                } else if (state == BluetoothDevice.BOND_BONDED) {
                } else if (state == BluetoothDevice.BOND_NONE) {
                }
            }

            //连接断开
            if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)) {
//                断开连接
            }
        }
    };

    private void initCharacteristics() {
//        BluetoothGattCharacteristic characteristic = myApplication.getCharacteristic();
//        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.fromString(GattAttributes.USR_SERVICE),-1,-1);
//        if (characteristic.getUuid().toString().equals(GattAttributes.USR_SERVICE)){
        isDebugMode = true;
        List<BluetoothGattCharacteristic> characteristics = ((DbApplication) getApplication()).getServices().get(0).getService().getCharacteristics();

        for (BluetoothGattCharacteristic c : characteristics) {
            if (Utils.getPorperties(this, c).equals("Notify")) {
                notifyCharacteristic = c;
                continue;
            }

            if (Utils.getPorperties(this, c).equals("Write")) {
                writeCharacteristic = c;
                continue;
            }
        }

//            properties = "Notify & Write";

//        }else {
//            properties = Utils.getPorperties(this, characteristic);
//
//            notifyCharacteristic = characteristic;
//            readCharacteristic = characteristic;
//            writeCharacteristic = characteristic;
//            indicateCharacteristic = characteristic;
//        }
    }

    private void initActivity() {
//        setBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        readBtn.setOnClickListener(this);
        fraq_value.setOnClickListener(this);
        command_select.setOnClickListener(this);
        set_fraq_value.setOnClickListener(this);
        set_command_select.setOnClickListener(this);
//        listDialog = new CheckListDialog(activity);
        meter_groupcharge.setOnClickListener(this);
        meter_charge.setOnClickListener(this);
        meterTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.white));    //设置颜色
                tv.setTextSize(12.0f);    //设置大小
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void initdata() {
        myApplication = (DbApplication) getApplication();
        sb = new StringBuilder();
        bledata = new ArrayList<BleDataModel>();
        fraqint = 0;
        rand2 = "";
//        List<BluetoothGattCharacteristic> characteristics = ((DbApplication)getApplication()).getServices().get(0).getService().getCharacteristics();
//        gattlist.addAll(characteristics);
//
//        //we create a virtual BluetoothGattCharacteristic just for debug
//        if (getIntent().getBooleanExtra("is_usr_service",false)){
//            BluetoothGattCharacteristic usrVirtualCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(GattAttributes.USR_SERVICE),-1,-1);
//            gattlist.add(usrVirtualCharacteristic);
//        }
    }



    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                stopFlag = true;//全局变量其实不是个好方法
                finish();
                break;
            case R.id.read_btn:

                if (setfraqvalue.getText().toString().equals(""))
                {
                    Toast.makeText(context, "请选择通信频率！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String command = set_command_select.getText().toString();
                if (command.equals(""))
                {
                    Toast.makeText(context, "请选择操作项！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addr.getText().toString().equals("")) {
                    Toast.makeText(context, "请填写需要抄读的表地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearData();
                returncmd = "";
                this.stopFlag = false;

//zhdl 蓝牙发送命令， 在这里触发
                sendCMD(command);





                break;
            /*频率设置*/
            case R.id.set_fraq_value:
            case R.id.fraq_values:
             //   if (listDialog == null) {
                setfraqvalueValue = "";
                optionflag = "setParameters";
                if (listDialog == null)
                listDialog = new CheckListDialog(activity);
                sb.delete(0, sb.length());

//                    listDialog.setCall(new CheckListDialog.CallBack() {
//                        @Override
//                        public void callBack(List<CheckModel> data) {
//                            for (CheckModel item : data) {
//                                if (item.isCheck) {
//                                    setfraqvalue.setText(item.value);
//                                }
//                            }
//                       }
//                   });
              //  }
                this.stopFlag = false;
                listDialog.isSetFraq = true;//是否设置抄控器频率设为true
                listDialog.show();
              //  setfraqvalue.setText(setfraqvalueValue);
                break;
            case R.id.meter_groupcharge:
            case R.id.meter_charge:
                if (listDialog ==null)
                listDialog = new CheckListDialog(activity);
                listDialog.setCall(new CheckListDialog.CallBack() {
                    @Override
                    public void callBack(List<CheckModel> data) {
                        for (CheckModel item : data) {
                            if (item.isCheck) {
                                meter_charge.setText(item.value);
                            }
                        }
                    }
                });
                this.stopFlag = false;
                listDialog.isSetFraq = false;//是否设置抄控器频率设为true
                listDialog.show();
            //    meter_charge.setText(setfraqvalueValue);
                break;
            case R.id.set_command_select:
            case R.id.command_select:
                if (checkListCommandDialog == null) {
                    checkListCommandDialog = new CheckListCommandDialog(activity);
                    checkListCommandDialog.setCall(new CheckListCommandDialog.CallBack() {
                        @Override
                        public void callBack(List<CheckModel> data) {
                            for (CheckModel item : data) {
                                if (item.isCheck) {
                                    set_command_select.setText(item.value);
                                    if (item.value.equals("更改频率"))
                                    {
                                        meter_groupcharge.setVisibility(VISIBLE);
                                    }
                                    else
                                    {
                                        meter_groupcharge.setVisibility(GONE);
                                    }
//                                    if (item.value.equals("编通讯地址")) {
//                                        Toast.makeText(context, "编通讯地址界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
//                                    if (item.value.equals("编设置量")) {
//                                        Toast.makeText(context, "编设置量界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
//                                    if (item.value.equals("编关阀水量")) {
//                                        Toast.makeText(context, "编关阀水量界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
//                                    if (item.value.equals("编出厂标志")) {
//                                        Toast.makeText(context, "编出厂标志界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
//                                    if (item.value.equals("编阀门自清理天数")) {
//                                        Toast.makeText(context, "编阀门自清理天数界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
//                                    if (item.value.equals("编机电同步")) {
//                                        Toast.makeText(context, "编机电同步界面弹出", Toast.LENGTH_SHORT).show();
////此处需要新增界面
//                                    }
                                }
                            }
                        }
                    });
                }
                checkListCommandDialog.initData();//初始化数据 根据选择的气表/水表
                checkListCommandDialog.show();
                break;
        }
    }

    private void sendCMD(String command)//发送命令 抄表 或 编程
    {
        if (LoginActivity.userRight >=2)//中等权限 不允许开关阀
        {
            if ((command.equals("气表开阀")==true)||(command.equals("气表关阀")==true))
            {
                Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (LoginActivity.userRight >=3)//最低权限 只允许抄
        {
            if (command.indexOf("抄")==-1)
            {
                Toast.makeText(context, "您无此权限操作此项命令!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if ((command.indexOf("抄")>-1)||(command.equals("编气表时间")))//设置超时时间
        {
            doTimeOut(15);//设置15s超时
        }
        else
        {
            doTimeOut(30);//设置30s超时
        }
        loading.show();
        returncmd = "";//shtiao用全局变量 容易出bug
        if (command.equals("气表身份认证")) {
            optionflag = "EncControl";
            bluetoothauth();
        }

        if (command.equals("气表开阀")) {
            optionflag = "EncControl";
            bluetoothPull("1");
        }
        if (command.equals("气表关阀")) {
            optionflag = "EncControl";
            bluetoothPull("0");
        }
        if (command.equals("编气表时间")) {
//            SimpleDateFormat    formatter1    =   new    SimpleDateFormat    ("yyMMdd");
//            SimpleDateFormat    formatter2    =   new    SimpleDateFormat    ("HHmmss");
//            Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
//            Calendar c = Calendar.getInstance();
//            String mWay ="0"+ String.valueOf(c.get(Calendar.DAY_OF_WEEK)-1);
//            String dateStr = formatter1.format(curDate);
//            String timeStr = formatter2.format(curDate);
//            setgasdata("0400010C", addr.getText().toString(),dateStr + mWay + timeStr);
            optionflag = "readgasdata";;
            ProgServeDate();
        }
        if (command.equals("更改频率"))
        {
            String progData =  meter_charge.getText().toString().trim();
            int i = progData.indexOf(")");
            progData = progData.substring(1,i);
            progData = Integer.toHexString(Integer.parseInt( progData));
            if (progData.length() < 2)
                progData = "0" + progData;
            progData =  "030409"+progData;
            setgasdata("71050001", addr.getText().toString(),progData);
        }

        if (command.equals("编气表当前套气价信息")) {
            optionflag = "EncControl";
            bluetoothPriceSet("1");
        }
        //编气表参数
        if (command.equals("编气表参数信息")) {
            optionflag = "EncControl";
            bluetoothProg();
        }

        if (command.equals("编气表备用套气价信息")) {
            optionflag = "EncControl";
            bluetoothPriceSet("2");
        }

        if (command.equals("抄读气表时间")) {
            readgasdata("0400010C", addr.getText().toString());
        }
//        if (command.equals("抄读气表月冻结数据")) {
//            readdata("7002FF01", addr.getText().toString());
//        }

        if (command.equals("抄读水表当前使用量")) {
            readdata("7000FF00", addr.getText().toString());
        }
        if (command.equals("抄读水表结算周期数据")) {
            readdata("7002FF01", addr.getText().toString());
        }
        if (command.equals("抄读水表实时状态查询")) {
            readdata("7200FF00", addr.getText().toString());
        }
        if (command.equals("抄读气表当前使用量")) {
            readgasdata("7000FF00", addr.getText().toString());
        }

        if (command.equals("抄读气表实时状态查询")) {
            readgasdata("7200FF00", addr.getText().toString());
        }

        if (command.equals("抄读气表参数信息")) {
            readgasdata("710100FF", addr.getText().toString());
        }//气表参数信息

        if (command.equals("抄读当前套气价信息")) {
            readgasdata("710200FF", addr.getText().toString());
        }

        if (command.equals("抄读备用套气价信息")) {
            readgasdata("710300FF", addr.getText().toString());
        }

        //  if (command.equals("抄读气表结算日冻结总次数")){readgasdata("71000001", addr.getText().toString());}

//        if (command.equals("抄读水表结算周期数据")) {
//            readdata("7002FF01", addr.getText().toString());
//        }
//        if (command.equals("抄读气表当月冻结数据")) {
//            readgasdata("7002FF00", addr.getText().toString());
//        }
        if (command.equals("抄读气表上1月冻结数据")) {
            readgasdata("7002FF01", addr.getText().toString());
        }
        if (command.equals("抄读气表上2月冻结数据")) {
            readgasdata("7002FF02", addr.getText().toString());
        }
        if (command.equals("抄读气表上3月冻结数据")) {
            readgasdata("7002FF03", addr.getText().toString());
        }
        if (command.equals("抄读气表上4月冻结数据")) {
            readgasdata("7002FF04", addr.getText().toString());
        }
        if (command.equals("抄读气表上5月冻结数据")) {
            readgasdata("7002FF05", addr.getText().toString());
        }
        if (command.equals("抄读气表上1结算数据")) {
            readgasdata("7000FF01", addr.getText().toString());
        }
        if (command.equals("抄读气表上2结算数据")) {
            readgasdata("7000FF02", addr.getText().toString());
        }
        if (command.equals("抄读气表上3结算数据")) {
            readgasdata("7000FF03", addr.getText().toString());
        }
        if (command.equals("抄读气表上4结算数据")) {
            readgasdata("7000FF04", addr.getText().toString());
        }
        if (command.equals("抄读气表上5结算数据")) {
            readgasdata("7000FF05", addr.getText().toString());
        }
//        if (command.equals("抄读气表上6结算数据")) {
//            readgasdata("7000FF06", addr.getText().toString());
//        }
//        if (command.equals("抄读气表上7结算数据")) {
//            readgasdata("7000FF07", addr.getText().toString());
//        }
//        if (command.equals("抄读气表上8结算数据")) {
//            readgasdata("7000FF08", addr.getText().toString());
//        }
//        if (command.equals("抄读气表上9结算数据")) {
//            readgasdata("7000FF09", addr.getText().toString());
//        }
//        if (command.equals("抄读气表上10结算数据")) {
//            readgasdata("7000FF0A", addr.getText().toString());
//        }
        if (command.equals("抄读气表上1次调价信息")) {
            readgasdata("7001FF01", addr.getText().toString());
        }//7001FF01

        if (command.equals("抄读气表上2次调价信息")) {
            readgasdata("7001FF02", addr.getText().toString());
        }//7001FF01
        if (command.equals("抄读气表上3次调价信息")) {
            readgasdata("7001FF03", addr.getText().toString());
        }//7001FF01
        if (command.equals("抄读气表上4次调价信息")) {
            readgasdata("7001FF04", addr.getText().toString());
        }//7001FF01
        if (command.equals("抄读气表上5次调价信息")) {
            readgasdata("7001FF05", addr.getText().toString());
        }//7001FF01
        if (command.equals("抄读气表当前气价信息")) {
            readgasdata("7003FF00", addr.getText().toString());
        }//7003FF00
        if (command.equals("抄读气表开阀次数")) {
            readgasdata("72010000", addr.getText().toString());
        }//72010000
        if (command.equals("抄读气表上1开阀事件")) {
            readgasdata("7201FF01", addr.getText().toString());
        }//7201FF01
        if (command.equals("抄读气表上2开阀事件")) {
            readgasdata("7201FF02", addr.getText().toString());
        }//7201FF01
        if (command.equals("抄读气表上3开阀事件")) {
            readgasdata("7201FF03", addr.getText().toString());
        }//7201FF01
        if (command.equals("抄读气表上4开阀事件")) {
            readgasdata("7201FF04", addr.getText().toString());
        }//7201FF01
        if (command.equals("抄读气表上5开阀事件")) {
            readgasdata("7201FF05", addr.getText().toString());
        }//7201FF01
        if (command.equals("抄读气表关阀次数")) {
            readgasdata("72020000", addr.getText().toString());
        }//72020000
        if (command.equals("抄读气表上1关阀事件")) {
            readgasdata("7202FF01", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上2关阀事件")) {
            readgasdata("7202FF02", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上3关阀事件")) {
            readgasdata("7202FF03", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上4关阀事件")) {
            readgasdata("7202FF04", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上5关阀事件")) {
            readgasdata("7202FF05", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表超大流量次数")) {
            readgasdata("72030000", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上1超大流量事件")) {
            readgasdata("7203FF01", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上2超大流量事件")) {
            readgasdata("7203FF02", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上3超大流量事件")) {
            readgasdata("7203FF03", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上4超大流量事件")) {
            readgasdata("7203FF04", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上5超大流量事件")) {
            readgasdata("7203FF05", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表超小流量次数")) {
            readgasdata("72040000", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上1超小流量事件")) {
            readgasdata("7204FF01", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上2超小流量事件")) {
            readgasdata("7204FF02", addr.getText().toString());
        }//7202FF01

        if (command.equals("抄读气表上3超小流量事件")) {
            readgasdata("7204FF03", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上4超小流量事件")) {
            readgasdata("7204FF04", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上5超小流量事件")) {
            readgasdata("7204FF05", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表磁场干扰次数")) {
            readgasdata("72050000", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上1磁场干扰事件")) {
            readgasdata("7205FF01", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上2磁场干扰事件")) {
            readgasdata("7205FF02", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上3磁场干扰事件")) {
            readgasdata("7205FF03", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上4磁场干扰事件")) {
            readgasdata("7205FF04", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上5磁场干扰事件")) {
            readgasdata("7205FF05", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表阀门异常次数")) {
            readgasdata("72060000", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上1阀门异常事件")) {
            readgasdata("7206FF01", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上2阀门异常事件")) {
            readgasdata("7206FF02", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上3阀门异常事件")) {
            readgasdata("7206FF03", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上4阀门异常事件")) {
            readgasdata("7206FF04", addr.getText().toString());
        }//7202FF01
        if (command.equals("抄读气表上5阀门异常事件")) {
            readgasdata("7206FF05", addr.getText().toString());
        }//7202FF01
//        if (command.equals("现场水表关阀")) {
//            swichpower("1A", addr.getText().toString());
//        }
//                if (command.equals("现场水表开阀")) {
//                    swichpower("1B", addr.getText().toString());
//                }
//        if (command.equals("抄控器无线模块参数设置")) {
//            setfraq();
//        }
//        if (command.equals("抄控器唤醒参数设置")) {
//            setwaekup();
//        }
//        if (command.equals("抄控器模式设置")) {
//            modeset();
//        }
//        if (command.equals("抄控器唤醒地址设置")) {
//            setweakupaddr();
//        }
//        if (command.equals("抄通讯地址")) {
//            readaddr("71040001", addr.getText().toString());
//        }
//        if (command.equals("抄无线信息")) {
//            readaddr("710500FF", addr.getText().toString());
//        }
//        if (command.equals("抄设置量")) {
//            readaddr("7B050001", addr.getText().toString());
//        }
//        if (command.equals("抄关阀水量")) {
//            readaddr("7B000002", addr.getText().toString());
//        }
//        if (command.equals("抄出厂标志")) {
//            readaddr("7B020003", addr.getText().toString());
//        }
//        if (command.equals("抄阀门自清理天数")) {
//            readaddr("7B020004", addr.getText().toString());
//        }
        /*
        if (command.equals("抄版本号")) {
            readaddr("7B020007", addr.getText().toString());
        }
        if (command.equals("抄开阀门次数")) {
            readaddr("72010000", addr.getText().toString());
        }
        if (command.equals("抄关阀门次数")) {
            readaddr("72020000", addr.getText().toString());
        }
        if (command.equals("抄当前表时间")) {
            readaddr("0400010C", addr.getText().toString());
        }
        if (command.equals("编当前表时间")) {
            readaddr("0400010C", addr.getText().toString());
        }
        if (command.equals("编通讯地址")) {
            readaddr("71040001", addr.getText().toString());
        }
        if (command.equals("编设置量")) {
            readaddr("7B000001", addr.getText().toString());
        }
       if (command.equals("水表充值")) { //点击通讯按钮， 1，先选择命令 2， 根据选择命令弹出编程框
            // 发起 身份认证 webservice， 子线程
//                    setDialogLabel("正在读取购水次数和关阀水量...");
//                    readWaterMergeCs("7000FF007B000002", addr.getText().toString());


            MyAutoWebThread autoWebThread = new MyAutoWebThread();
            new Thread(autoWebThread).start();
        }
        if (command.equals("编出厂标志")) {
            readaddr("7B000003", addr.getText().toString());
        }
        if (command.equals("编阀门自清理天数")) {
            readaddr("7B000004", addr.getText().toString());
        }
        if (command.equals("编机电同步")) {
            readaddr("7B000005", addr.getText().toString());
        }*/
    }
    private void swichpower(String cmd, String addr) {
        optionflag = "readdata";
    }

    private void gasMeterSwitch(String cmd, String addr)//气表开关阀
    {
        optionflag = "gasMeterSwitch";

    }
//编气表时间
    public void ProgServeDate(){
        try {
            ComBleActivity.GetServeDateThread t = new ComBleActivity.GetServeDateThread();
            new Thread(t).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetServeDateThread implements Runnable {
        public void run() {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
                jsobj.put("strUser",BleDbHelper.userName);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(HOST + "/bluetoothDateTimeGas", map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = c.getString("str645Data");
                        setgasdata("0400010C", addr.getText().toString(),str645Data);
                        //  Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        android.os.Message m = handler.obtainMessage();
                        m.arg1 = 1;
                        Bundle bundle = new Bundle();
                        m.what = 1;
                        bundle.putString("title", "获取服务器时间失败,请重试!");
                        m.setData(bundle);
                        handler.sendMessage(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

//蓝牙气表身份认证
    public void bluetoothauth(){
        try {
            ComBleActivity.MybluetoothauthThread t = new ComBleActivity.MybluetoothauthThread();
            new Thread(t).start();

            // 创建一个子线程, 子线程发起 身份认证webservice

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   //蓝牙身份认证子线程
    class MybluetoothauthThread implements Runnable {
        public void run() {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
                jsobj.put("strUser",BleDbHelper.userName);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_GasAuto, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = c.getString("str645Data");
                        optionflag = "EncControl";
                        returncmd = "";//shtiao用全局变量 容易出bug
                        writeOption(str645Data);//发送命令
                      //  Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        return;
                       ///此处需要通知界面身份认证失败 Toast.makeText(context, "身份认证失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public void bluetoothPull(String operationId){
        try {
            rand2 = "";
            bluetoothauth();//先进行气表蓝牙身份认证

            MybluetoothPullThread t = new MybluetoothPullThread(operationId);
            new Thread(t).start();
            // 创建一个子线程, 子线程发起 身份认证webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class MybluetoothPullThread implements Runnable {//蓝牙气表开关阀//operationId=0 关闭；operationId=1 打开
       private String operationId;
        public MybluetoothPullThread(String operationId)
        {

            this.operationId = operationId;
        }
        public void run()
        {
            int i = 0;
            boolean l = false;
            try {
                while (i < 15)//默认给15s延时进行身份认证
                {
                    Thread.sleep(1000);
                    i++;
                    if (rand2.length() == 8) {
                        l = true;
                        break;
                    }
                }
                if (l == false) {
                    stopFlag = true;
                    android.os.Message m = handler.obtainMessage();
                    m.arg1 = 1;
                    Bundle bundle = new Bundle();
                    m.what = 1;
                    bundle.putString("title", "身份认证失败,请重试!");
                    m.setData(bundle);
                    handler.sendMessage(m);
                    //Toast.makeText(context, "身份认证失败,请重试!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
         catch (Exception e) {
             e.printStackTrace();
             return;
        }


            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
                jsobj.put("meterRand", rand2);
                jsobj.put("operationId", operationId);
                jsobj.put("strUser",BleDbHelper.userName);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_GasPull, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = c.getString("str645Data");
                        optionflag =  "readgasdata";
                        returncmd = "";//shtiao用全局变量 容易出bug
                        writeOption(str645Data);//发送命令
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    //蓝牙更新气价
    public void bluetoothPriceSet(String operationId){//1:当前套气价, 2:备用套气价
        try {
            rand2 = "";
            bluetoothauth();//先进行气表蓝牙身份认证
            MybluetoothPriceSetThread t = new MybluetoothPriceSetThread(operationId);
            new Thread(t).start();
            // 创建一个子线程, 子线程发起 身份认证webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class MybluetoothPriceSetThread implements Runnable {//蓝牙更新气价//priceNo 1:当前套气价, 2:备用套气价
        private String priceNo;
        public MybluetoothPriceSetThread(String priceNo)
        {
            this.priceNo = priceNo;
        }
        public void run()
        {
            int i = 0;
            boolean l = false;
            try {
                while (i < 15)//默认给15s延时进行身份认证
                {
                    Thread.sleep(1000);
                    i++;
                    if (rand2.length() == 8) {
                        l = true;
                        break;
                    }
                }
                if (l == false) {
                    android.os.Message m = handler.obtainMessage();
                    m.arg1 = 1;
                    Bundle bundle = new Bundle();
                    m.what = 1;
                    bundle.putString("title", "身份认证失败,请重试!");
                    m.setData(bundle);
                    handler.sendMessage(m);
                    //Toast.makeText(context, "身份认证失败,请重试!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }


            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
                jsobj.put("priceNo", priceNo);
                jsobj.put("priceType", priceType);
                jsobj.put("meterRand", rand2);
                jsobj.put("strUser",BleDbHelper.userName);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_GasPriceSet, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = c.getString("str645Data");
                        optionflag =  "readgasdata";
                        returncmd = "";//shtiao用全局变量 容易出bug
                        writeOption(str645Data);//发送命令
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    //蓝牙下发表参数
    public void bluetoothProg(){//通用方法
        try {
            rand2 = "";
            bluetoothauth();//先进行气表蓝牙身份认证
            BluetoothProgThread t = new BluetoothProgThread();
            new Thread(t).start();
            // 创建一个子线程, 子线程发起 身份认证webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class BluetoothProgThread implements Runnable {
        public void run()
        {
            int i = 0;
            boolean l = false;
            try {
                while (i < 15)//默认给15s延时进行身份认证
                {
                    Thread.sleep(1000);
                    i++;
                    if (rand2.length() == 8) {
                        l = true;
                        break;
                    }
                }
                if (l == false) {
                    android.os.Message m = handler.obtainMessage();
                    m.arg1 = 1;
                    Bundle bundle = new Bundle();
                    m.what = 1;
                    bundle.putString("title", "身份认证失败,请重试!");
                    m.setData(bundle);
                    handler.sendMessage(m);
                    //Toast.makeText(context, "身份认证失败,请重试!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }


            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
               // jsobj.put("priceNo", priceNo);
//                jsobj.put("priceType", priceType);
                jsobj.put("meterRand", rand2);
                jsobj.put("strUser",BleDbHelper.userName);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_SetParamGas, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = c.getString("str645Data");
                        optionflag =  "readgasdata";
                        returncmd = "";//shtiao用全局变量 容易出bug
                        writeOption(str645Data);//发送命令
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    //入库
    private void insertDataGas(String meterNo,String cmdName,String cmd,int isUpLoad,ArrayList<String> dataList)////参数:表号,命令名称,命令,数据列表
    {
        BleDbHelper helper = new BleDbHelper();
        helper.insertData(meterNo,cmdName,cmd, isUpLoad, dataList);
    }

    //上传蓝牙抄表数据实时上传
    public void UploadDataGas(ArrayList<String> list,String meterAddr,String cmd){//
        try {
            UploadDataGasThread t = new UploadDataGasThread(list,meterAddr,cmd);
            new Thread(t).start();
            // 创建一个子线程, 子线程发起 身份认证webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传
    class UploadDataGasThread implements Runnable {
        private ArrayList<String> list;
        private String meterAddr;
        private String cmd;
        public UploadDataGasThread(ArrayList<String> list,String meterAddr,String cmd)
        {
            this.list = list;
            this.cmd = cmd;
            this.meterAddr = meterAddr;
        }
        public void run()
        {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);//CommonUtil.AddZeros(addr.getText().toString())
                jsobj.put("CMD",cmd);//CMD
                String data = "";
                for (int i = 0; i < list.size(); i++)
                {
                    String dataStr = list.get(i);
                    String[] StrArray = dataStr.split("\\|");
                    data =data +  StrArray[1] + ",";
                }
                data = data.substring(0,data.length()-1);//去掉最后的","
                jsobj.put("data", data);

            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterGas", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_UploadDataGas, map); //
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject c = new JSONObject(result);
                    int jStatus = c.getInt("strBackFlag");
                    if (jStatus == 1) {
                        android.os.Message m = handler.obtainMessage();
                        m.arg1 = 1;
                        Bundle bundle = new Bundle();
                        m.what = 1;
                        bundle.putString("title", "上传数据成功!");
                        m.setData(bundle);
                        handler.sendMessage(m);
                        isUpLoad = 1;
                        //str645Data = c.getString("str645Data");
                       // writeOption(str645Data);//发送命令
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


  /*  private void initbloothauthCallBack() {
        bluetoothauthCallback = new CallBack() {
            @Override
            public void callback(String json) {
//				loading.hide();
                // 解析json
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int jStatus = jsonObject.getInt("strBackFlag");
                    if (jStatus == 1) {
                        str645Data = jsonObject.getString("str645Data");
                        writeOption(str645Data);//发送命令
                        Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
                        MybluetoothauthThread myThread = new MybluetoothauthThread();
                        new Thread(myThread).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
*/
    //  合闸请求
    public void bluetoothopen(){
        try {
            String username = "admin";
            String password = "password";

           // preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("name", username);
                jsobj.put("password", password);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
//            SystemAPI.login(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*   private void initbloothonCallBack() {
        bluetoothonCallback = new CallBack() {
            @Override
            public void callback(String json) {
//				loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
//                                            ToastTool.showToast(jStatus, activity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String jSessionId = json;

                 //   preference.putString(ContantsUtil.USER_SESSIONID,
                 //           jSessionId);
                    HttpServiceUtil.sessionId = jSessionId;
                    // 显示登录状态
                //    MyThread myThread = new MyThread();
               //     new Thread(myThread).start();
//                                        loading.show();
                }
            }
        };
    }
*/

    //  拉闸请求
    public void bluetoothoff(){
        try {
            String username = "admin";
            String password = "password";

         //   preference.putString(Preference.CACHE_USER, username);

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("name", username);
                jsobj.put("password", password);
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeterGas", jsobj.toString());
//                    loading.show();
 //           SystemAPI.login(map, loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    private void initbloothoffCallBack() {
        bluetoothoffCallback = new CallBack() {
            @Override
            public void callback(String json) {
//				loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        Toast.makeText(context, jStatus, Toast.LENGTH_SHORT).show();
//                                            ToastTool.showToast(jStatus, activity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String jSessionId = json;

                    //preference.putString(ContantsUtil.USER_SESSIONID,
                    //        jSessionId);
                    HttpServiceUtil.sessionId = jSessionId;
                    // 显示登录状态
                 //   MyThread myThread = new MyThread();
                 //   new Thread(myThread).start();
//                                        loading.show();
                }
            }
        };
    }
*/
    public void readgasdata(String cmd, String addr) {
        optionflag = "readgasdata";
        String str="";
//        if (cmd.equals("7000FF00"))
//        {
//            String conmand = reverbyte(cmd);
//            str = "680C30" + reverbyte(CommonUtil.AddZeros(addr)) + "68400104" + conmand  + "010401FF0070";
//        }
//        else
//        {
            String conmand = reverbyte(cmd);
            str = "680630" + reverbyte(CommonUtil.AddZeros(addr)) + "68400104" + conmand;
//        }
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        returncmd = "";//shtiao用全局变量 容易出bug
        writeOption(str);
    }
    public void setgasdata(String cmd, String addr,String progData) {
        optionflag = "readgasdata";
        String conmand = reverbyte(cmd);

        String lenStr = Integer.toHexString(progData.length() /2 + 12);//13是长度
        if(lenStr.length() <2)
        {
            lenStr = "0"+lenStr;
        }               //15
        String str = "";
        if (cmd.equals("71050001"))
        {
            str = "681210" + reverbyte(CommonUtil.AddZeros(addr)) + "684002" + lenStr + conmand + "0200000078563412" + reverbyte(progData);
        }
        else
        {
            str = "681530" + reverbyte(CommonUtil.AddZeros(addr)) + "684002" + lenStr + conmand + "0200000078563412" + reverbyte(progData);
        }
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    //  zhdl发送抄表命令
    public void readdata(String cmd, String addr) {
        optionflag = "readdata";
        String conmand = reverbyte(cmd);
        String str = "680610" + reverbyte(CommonUtil.AddZeros(addr)) + "68400104" + conmand;
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }
/*
    public void readaddr(String cmd, String addr) {
        optionflag = "readdata";
        String conmand = reverbyte(cmd);
        String str = "680610" + reverbyte(CommonUtil.AddZeros(addr)) + "68400104" + conmand;
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }
*/
    //命令组合读取 购水次数和表内关阀水量
    public void readWaterMergeCs(String cmd, String addr) {
        optionflag = "readWaterMergeCs";
        String str = "680C10" + reverbyte(CommonUtil.AddZeros(addr)) + "6840" + "010400FF0070" + "01040200007B";
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    // zhdl 水表充值
    public void setWaterCharge(String cmd, String addr, String strData) {
        optionflag = "setWaterCharge";
        String conmand = reverbyte(cmd);
        String str = "68" + "22" + "10" + reverbyte(CommonUtil.AddZeros(addr)) + "684002" + "1C" + conmand + "0200000000000000" + strData;
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    // zhdl 身份认证
    public void setWaterAuto(String cmd, String addr, String sRandData) {
        optionflag = "setWaterAuto";
        String conmand = reverbyte(cmd);
        String sDivData = reverbyte(CommonUtil.AddZeros(addr, 16));
        String str = "68" + "24" + "10" + reverbyte(CommonUtil.AddZeros(addr)) + "684003" + "22" + conmand + "00000000" + sDivData + sRandData;
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    //  设置频率
    public static void setFraq(String value)
    {
        writeOption(setParameters("F1", value.substring(value.indexOf(")")+1, value.length() - 3), "250", "9", "4_7"));
    }
    /*
    public  void setfraq() {
        // 设置频率
        optionflag = "setParameters";
        sb.delete(0, sb.length());
        if (setfraqvalue.getText().length() > 0) {
            String frap1 = setfraqvalue.getText().toString();
            writeOption(setParameters("F1", frap1.substring(frap1.indexOf(")")+1, frap1.length() - 3), "250", "9", "4_7"));
        } else {
            Toast.makeText(context, "请选择需要设置的设备频率！", Toast.LENGTH_SHORT).show();
        }
    }

    public void CircleRead() {
        optionflag = "CircleRead";
        if (fraqint == 0) {
            writeOption(setParameters("F1", String.valueOf(470), "250", "9", "4_7"));
        } else {
            writeOption(setParameters("F1", String.valueOf(470 + (fraqint) * 0.5), "250", "9", "4_7"));
        }
    }


    //  唤醒参数设置
    public void setwaekup() {
//      设置频率
        optionflag = "setParameters";
        sb.delete(0, sb.length());
        writeOption(setParameters("F2", "15", "6", "6", "50"));
    }

    //  模式设置
    public void modeset() {
        optionflag = "setParameters";
        sb.delete(0, sb.length());
        writeOption(setParameters("F3", "0", "5"));
    }

    public void setweakupaddr() {
        optionflag = "setParameters";
        sb.delete(0, sb.length());
        writeOption(setParameters("F4", "123456123456"));
    }

    public String readdata1() {
//
        optionflag = "readdata1";
        sb.delete(0, sb.length());
        String conmand = reverbyte("71010099");
//        byte[] bytes =converStringToByteArray("680630"+ AddZeros(addr.getText().toString()) +"6840014"+conmand);
        String cs = makeChecksum("680630" + reverbyte(CommonUtil.AddZeros(addr.getText().toString())) + "68400104" + conmand);
        String str = "680630" + reverbyte(CommonUtil.AddZeros(addr.getText().toString())) + "68400104" + conmand + cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        return str;
    }

    //  数据上传服务端
    public void uploaddata() {

    }
*/
//    private void notifyOption(){
//        if (nofityEnable){
//            nofityEnable = false;
//            stopBroadcastDataNotify(notifyCharacteristic);
//        }else {
//            nofityEnable = true;
//            prepareBroadcastDataNotify(notifyCharacteristic);
//        }
//    }


    private void indicateOption() {
        if (indicateEnable) {
            indicateEnable = false;
            stopBroadcastDataIndicate(indicateCharacteristic);
        } else {
            nofityEnable = true;
            prepareBroadcastDataIndicate(indicateCharacteristic);
        }
    }

    private void readOption() {
        prepareBroadcastDataRead(readCharacteristic);
    }

    public static void writeOption(String command) {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        String scommond;
        byte[] array;
        while (sb.length() != 0) {
            if (sb.length() > 40) {
                scommond = sb.substring(0, 40);
                sb.delete(0, 40);
                array = Utils.hexStringToByteArray(scommond);
                writeCharacteristic(writeCharacteristic, array);
                try {
                    sleep(50); //暂停，每一秒输出一次
                } catch (InterruptedException e) {
                    return;
                }
            } else {
                scommond = sb.toString();
                sb.delete(0, sb.length());
                array = Utils.hexStringToByteArray(scommond);
                writeCharacteristic(writeCharacteristic, array);
            }

        }
    }

    //        mHandler.postDelayed(mClearMsgRunnable, 1000*5);
//// 接收消息超时处理，通知所有listener超时并清空消息队列
//        private Runnable mClearMsgRunnable = new Runnable() {
//            @Override
//            public void run() {
//                List<MsgListener> list = MsgReqListMgr.getInstance().getListeners();
//                if (list.size() > 0) {
//                    Iterator<MsgListener> iterator = list.iterator();
//                    while (iterator.hasNext()) {
//                        MsgListener msg = iterator.next();
//                        msg.mListener.onFailed(MsgErrCode.ERR_TIME_OUT);
//                    }
//                    ThreadMgr.getInstance().removeAll();
//                    MsgReqListMgr.getInstance().clear();
//                }
//                MsgReqListMgr.getInstance().clear();
//            }
//        };

    /**
     * Preparing Broadcast receiver to broadcast read characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataRead(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            BluetoothLeService.readCharacteristic(characteristic);
        }
    }

    /**
     * Preparing Broadcast receiver to broadcast notify characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataNotify(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            BluetoothLeService.setCharacteristicNotification(characteristic, true);
        }
    }

    /**
     * Stopping Broadcast receiver to broadcast notify characteristics
     *
     * @param characteristic
     */
    void stopBroadcastDataNotify(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            BluetoothLeService.setCharacteristicNotification(characteristic, false);
        }
    }

    /**
     * Preparing Broadcast receiver to broadcast indicate characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataIndicate(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();

        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            BluetoothLeService.setCharacteristicIndication(characteristic, true);
        }
    }

    /**
     * Stopping Broadcast receiver to broadcast indicate characteristics
     *
     * @param characteristic
     */
    void stopBroadcastDataIndicate(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();

        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            BluetoothLeService.setCharacteristicIndication(characteristic, false);
        }
    }

    //  特征写数据
    public static void writeCharacteristic(BluetoothGattCharacteristic characteristic, byte[] bytes) {
        // Writing the hexValue to the characteristics
        try {
            BluetoothLeService.writeCharacteristicGattDb(characteristic, bytes);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        BleActivity.ComBleActivityIsExsit = false;
        super.onDestroy();
        stopNotifyOrIndicate();
        unregisterReceiver(mGattUpdateReceiver);
    }

    private void stopNotifyOrIndicate() {
        if (nofityEnable)
            stopBroadcastDataNotify(notifyCharacteristic);
        if (indicateEnable)
            stopBroadcastDataIndicate(indicateCharacteristic);
    }

    //  格式化解析
    private String formatMsgContent(byte[] data) {
        return "HEX:" + Utils.ByteArraytoHex(data) + "(ASSCII:" + Utils.byteToASCII(data) + ")";
    }


    /**
     * 生成16进制累加和校验码
     *
     * @param data 除去校验位的数据
     * @return
     */
    public static String makeChecksum(String data) {
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
//            System.out.println(s);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        //如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

//    public void prasereturn(String data){
//        if (optionflag == "setfraq"){
//            int i = data.indexOf('-');
//            int j = data.indexOf(')');
//            sb.delete(0,sb.length());
//            sb.append(data.substring(i+3, j));
//            fraqdata.setText(sb.toString());
//            Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
//        }
//        if (optionflag == "readdata") {
//            int i = data.indexOf(':');
//            int j = data.indexOf('(');
//            sb.delete(0,sb.length());
//            sb.append(data.substring(i+1, j));
////            Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
//            if (!sb.substring(sb.length()-3,sb.length()-1).equals("16")){
//                returncmd = returncmd + sb.toString();
//            } else {
//                returncmd = returncmd + sb.toString();
//                sb.delete(0,sb.length());
//                sb.append(returncmd.replaceAll(" ", ""));
////68 22{长度} 30{表类型} 030200000000 68 80{控制码} 01{数据控制码} 20{数据区长度} 00FF0070
//// {数据顺序为正，分相倒置}00000000{当前气量} 00000000{结算周期气量} 00400100{剩余金额总140.00} 00400000{剩余金额本地40
////00} 00000000{购气次数本地} 00000100{剩余金额远程 100.00} 00000000{购气次数远程} B9 16
//                if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
//                    if (sb.substring(6,18).equals(reverbyte(AddZeros(addr.getText().toString())))) {
//                        if ((sb.substring(sb.length()-4,sb.length()-2)).equals(makeChecksum(sb.substring(0,sb.length()-4)).toUpperCase())){
//                            sb.delete(0,sb.indexOf(reverbyte("7000FF00"))+8);
//                            sb.delete(56,sb.length());
////                          当前气量
//                            String b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData1.setText(b);
//                            sb.delete(0,8);
////
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData2.setText(b);
//                            sb.delete(0,8);
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData3.setText(b);
//                            sb.delete(0,8);
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData4.setText(b);
//                            sb.delete(0,8);
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData5.setText(b);
//                            sb.delete(0,8);
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData6.setText(b);
//                            sb.delete(0,8);
//                            b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
//                            strBackData7.setText(b);
//                            sb.delete(0,8);
////                            Toast.makeText(context,sb.toString(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context,"返回信息校验位不正确！", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(context,"返回信息表地址不正确！", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context,"返回信息头不完整！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//        if (optionflag == "readdata1"){
//            int i = data.indexOf(':');
//            int j = data.indexOf('(');
//            sb.delete(0,sb.length());
//            sb.append(data.substring(i+1, j));
//            fraqdata.setText(sb.toString());
//        }
//    }

    //  抄通讯地址
    public void Readfraq(String cmd, String addr) {
        optionflag = "ReadFraq";
        String conmand = reverbyte(cmd);
        String str = "680610" + reverbyte(CommonUtil.AddZeros(addr)) + "68400104" + conmand;
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    public void EncControl(String cmd, String addr, String encData)//远程控制(cmd:数据标识 或 控制码,addr:通讯地址,encData:加密机返回的数据)
    {
        optionflag = "EncControl";
        // byte[] bytes =converStringToByteArray("680630"+
        // AddZeros(addr.getText().toString()) +"6840014"+conmand);
        // System.out.println(AddZeros(addr.getText().toString()));
        // System.out.println(reverbyte(AddZeros(addr.getText().toString())));
        // String addr = addr.getText().toString();
        String str = "";
        switch (cmd) {
            case "7A000001"://身份认证
                str = "682410" + reverbyte(CommonUtil.AddZeros(addr)) + "684003220100007A78563412" + reverbyte(CommonUtil.AddZeros(addr)) + "0000" + encData;
                break;
            case "1A"://关阀
                str = "681E10" + reverbyte(CommonUtil.AddZeros(addr)) + "6840071C0200000078563412" + encData;
                break;
            case "1B"://开阀
                str = "681E10" + reverbyte(CommonUtil.AddZeros(addr)) + "6840071C0200000078563412" + encData;
                break;
        }
        String cs = makeChecksum(str);
        str += cs + "16";
        returncmd = "";
        sb.delete(0, sb.length());
        writeOption(str);
    }

    private String addSign(String inStr, String ym) {
        int i = ym.indexOf('.');
        String outStr = "";
        try {
            if (i >= 0) {
                outStr = String.valueOf(Integer.parseInt(inStr.substring(0, i))) + "." + inStr.substring(i);
            } else {
                if (ym == "YYMMDDhhmm") {
                    outStr = inStr.substring(0, 2) + "-" + inStr.substring(2, 4) + "-" + inStr.substring(4, 6) + " "
                            + inStr.substring(6, 8) + ":" + inStr.substring(8, 10);
                } else if (ym == "YYMMDDWWhhmmss") {
                    String week = inStr.substring(7, 8);
                    if (week.equals("7"))
                        week = "星期日";
                    if (week.equals("1"))
                        week = "星期一";
                    if (week.equals("2"))
                        week = "星期二";
                    if (week.equals("3"))
                        week = "星期三";
                    if (week.equals("4"))
                        week = "星期四";
                    if (week.equals("5"))
                        week = "星期五";
                    if (week.equals("6"))
                        week = "星期六";
                    outStr = inStr.substring(0, 2) + "-" + inStr.substring(2, 4) + "-" + inStr.substring(4, 6) + " " + week
                            + " " + inStr.substring(8, 10) + ":" + inStr.substring(10, 12) + ":" + inStr.substring(12, 14);
                } else if (ym == "MMDDNN") {
                    outStr = inStr.substring(0, 2) + "月" + inStr.substring(2, 4) + "日" + " " + "阶梯编号 " + inStr.substring(4, 6);
                } else if (ym == "YYMMDD") {
                    outStr = inStr.substring(0, 2) + "年" + inStr.substring(2, 4) + "月" + inStr.substring(4, 6) + "日";
                } else if (ym == "MMDD") {
                    outStr = inStr.substring(0, 2) + "月" + inStr.substring(2, 4) + "日";
                } else if (ym == "DDhh") {
                    outStr = inStr.substring(0, 2) + "日" + inStr.substring(2, 4) + "时";
                } else {
                    outStr = String.valueOf(Integer.parseInt(inStr));
                }
            }
        } catch (Exception e) {
            outStr = "";
        }
        return outStr;
    }

    private void returnEncControl(String data,ArrayList<String> list)
    {

        int i = data.indexOf(':');
        int j = data.indexOf('(');
        sb.delete(0, sb.length());
        sb.append(data.substring(i + 1, j));
        if (!sb.substring(sb.length() - 3, sb.length() - 1).equals("16")) {
            returncmd = returncmd + sb.toString();
        } else {
            if (optionflag.equals("EncControl")==false)
                this.stopFlag = true;//接收数据完成后,置标志,再不接收数据
            loading.dismiss();
            returncmd = returncmd + sb.toString();
            clearData();
            sb.delete(0, sb.length());
            sb.append(returncmd.replaceAll(" ", ""));
            String addrs = CommonUtil.AddZeros(addr.getText().toString());
            // String addr = "000000000041";
            if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
                if (sb.substring(6, 18).equals(reverbyte(addrs))) {
                    if ((sb.substring(sb.length() - 4, sb.length() - 2))
                            .equals(makeChecksum(sb.substring(0, sb.length() - 4)).toUpperCase())) {
                        sb.delete(0, 20);// 删除68...68
                        // String meterType = sb.substring(0,2);//80是仪表类型
                        sb.delete(0, 2);
                        String controlCode = sb.substring(0, 2);//01是控制码
                        sb.delete(0, 2);
                        String dataLenth = sb.substring(0, 2);// 数据长度
                        sb.delete(0, 2);
                        String dataZone = sb.substring(0, Integer.parseInt(dataLenth, 16) * 2);// 数据域
                        if (controlCode.equals("03")) {
                            rand2 =dataZone;
                            priceType =sb.substring(10,11);
                            try {
                                list.clear();
//                                        list.add("身份认证|成功|");
                                Toast.makeText(context, "身份认证成功！", Toast.LENGTH_SHORT).show();
                                loading.show();

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        } else if (controlCode.equals("83") || controlCode.equals("87")|| controlCode.equals("82")) {
                            try {
                                list.clear();
                                list.add("编程|失败");
                                Toast.makeText(context, "编程失败！", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        } else if (controlCode.equals("07")||controlCode.equals("02")) {
                            try {
                                list.clear();
                                list.add("编程|成功");//控制成功
                                Toast.makeText(context, "编程成功！", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        } else if (controlCode.equals("01"))//抄数据返回的命令
                        {
                            String cmd = dataZone.substring(0, 8);// 数据标识
                            cmd = reverbyte(cmd);// 倒置
                            String dataStr = dataZone.substring(8);// 数据
                            switch (cmd) {
                                case "7000FF00":// 当前水量
                                    try {
                                        list.clear();
                                        list.add("当前气量|" + addSign(reverbyte(dataStr.substring(0, 8)), "xxxxxx.xx") + "|" + "m³");
                                        list.add("当前结算周期气量|" + addSign(reverbyte(dataStr.substring(8, 16)), "xxxxxx.xx") + "|" + "m³");
//                                                list.add("剩余金额（总）|" + addSign(reverbyte(dataStr.substring(16, 24)), "xxxxxx.xx") + "|" + "元");
                                        // list.add("剩余金额（本地）|" +
                                        // addSign(reverbyte(dataStr.substring(24,32)),"xxxxxx.xx")
                                        // + "|" + "m³");//保留
                                        // list.add("购水次数（本地）|" +
                                        // addSign(reverbyte(dataStr.substring(32,40)),"xxxxxxxx")
                                        // + "|" + "m³"); //保留
                                        list.add("剩余金额（远程）|" + addSign(reverbyte(dataStr.substring(40, 48)), "xxxxxx.xx") + "|" + "m³");
                                        list.add("购气次数（远程）|" + addSign(reverbyte(dataStr.substring(48, 56)), "xxxxxxxx") + "|" + "次");


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "7200FF00":// 实时状态查询
                                    list.clear();
                                    list.add("电池电压|" + addSign(reverbyte(dataStr.substring(0, 4)), "xx.xx") + "|" + "V");
                                    list.add("实时时间|" + addSign(reverbyte(dataStr.substring(4, 18)), "YYMMDDWWhhmmss") + "|" + " ");
                                    String bitStr = reverbyte(dataStr.substring(18, 22));
                                    String bit = CommonUtil.AddZeros(Integer.toBinaryString(Integer.valueOf(bitStr, 16)), 16);
                                    bit = reverbyte1(bit);
                                    //list.add("bit位|" + bit +"|");
                                    if (bit.charAt(0) == '1')
                                        list.add("阀开/关|1:状态|开");
                                    else
                                        list.add("阀开/关|0:状态|关");
                                    if (bit.charAt(1) == '1')
                                        list.add("阀异常|1:状态|是");
                                    else
                                        list.add("阀异常|0:状态|否");
                                    if (bit.charAt(2) == '1')
                                        list.add("电压低|1:状态|是");
                                    else
                                        list.add("电压低|0:状态|否");
                                    if (bit.charAt(3) == '1')
                                        list.add("干簧管坏|1:状态|是");
                                    else
                                        list.add("干簧管坏|0:状态|否");
                                    if (bit.charAt(4) == '1')
                                        list.add("强磁干扰|1:状态|有");
                                    else
                                        list.add("强磁干扰|0:状态|无");
                                    if (bit.charAt(5) == '1')
                                        list.add("EEPROM异常|1:状态|是");
                                    else
                                        list.add("EEPROM异常|0:状态|否");
                                    if (bit.charAt(6) == '1')
                                        list.add("ESAM异常|1:状态|是");
                                    else
                                        list.add("ESAM异常|0:状态|否");
                                    if (bit.charAt(7) == '1')
                                        list.add("余额不足|1:状态|是");
                                    else
                                        list.add("余额不足|0:状态|否");
                                    if (bit.charAt(8) == '1')
                                        list.add("RTC异常|1:状态|是");
                                    else
                                        list.add("RTC异常|0:状态|否");
                                    if (bit.charAt(9) == '1')
                                        list.add("已开户|1:状态|是");
                                    else
                                        list.add("已开户|0:状态|否");
                                    if (bit.charAt(10) == '1')
                                        list.add("剩余金额小于最小扣费金额|1:状态|是");
                                    else
                                        list.add("剩余金额小于最小扣费金额|0:状态|否");
                                    if (bit.charAt(11) == '1')
                                        list.add("保留，卡充钱包剩余为0|1:状态|是");
                                    else
                                        list.add("保留，卡充钱包剩余为0|0:状态|否");
                                    if (bit.charAt(12) == '1')
                                        list.add("网充钱包剩余为0|1:状态|是");
                                    else
                                        list.add("网充钱包剩余为0|0:状态|否");
                                    break;
                                case "7002FF01": // 结算周期水量
                                    list.clear();
                                    int times = Integer.parseInt(cmd.substring(6, 8), 16);
                                    String tipStr;
                                    if (times == 0)
                                        tipStr = "当前月";
                                    else
                                        tipStr = "上" + times + "月";
                                    list.add(tipStr + "累计用气量|" + addSign(reverbyte(dataStr.substring(0, 8)), "xxxxxx.xx") + "|" + "m³");
                                    list.add(tipStr + "累计使用金额|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(8, 16))) * 0.001) + "|" + "m³");
                                    list.add(tipStr + "价格版本号|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(16, 20)), 16)) + "|" + "");
                                    list.add("统计时间|" + addSign(reverbyte(dataStr.substring(20, 30)), "YYMMDDhhmm") + "|" + "");
                                    break;


                                case "71040001":
                                    list.clear();
//                                            680C100000785634126880010A010004710000785634121516
                                    list.add("通讯地址|" + reverbyte(dataStr.substring(0, 12)) + "|" + "");
                                    break;
                                case "7B000002":
                                    String strFh = reverbyte(dataStr.substring(0, 8));
                                    list.clear();
                                    list.add("抄回关阀气量|" + strFh + "|" + "");
                                    break;
                                case "0400010C":
                                    strFh = reverbyte(dataStr.substring(0, 14));
                                    list.clear();
                                    list.add("抄当前表时间|" + addSign(strFh, "YYMMDDWWhhmmss") + "|");
                                    break;
                            }
                        }
                        for (int aa = 0; aa < list.size(); aa++)
                        {
                            BleDataModel dto = new BleDataModel();
                            dto.of(list.get(aa));
                            bledata.add(dto);
                        }
                        bledataadapter.notifyDataSetChanged();
                        stopFlag = true;

                        // String b =
                        // String.valueOf(Integer.parseInt(reverbyte(sb.substring(0,
                        // 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0,
                        // 8)).substring(6,8);
                        // strBackData1.setText(b);
                        // sb.delete(0,8);

                        // Toast.makeText(context,sb.toString(),
                        // Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "返回信息校验位不正确！",
                                Toast.LENGTH_SHORT).show();
                        stopFlag = true;
                        //                            System.out.println("返回信息校验位不正确！");
                    }
                } else {
                    Toast.makeText(context, "返回信息表地址不正确！",
                            Toast.LENGTH_SHORT).show();
                    stopFlag = true;
                    //                        System.out.println("返回信息表地址不正确！");
                }
            } else {
                Toast.makeText(context, "返回信息头不完整！",
                        Toast.LENGTH_SHORT).show();
                stopFlag = true;
                //                    System.out.println("返回信息头不完整！");
            }
        }
    }

    private void returnReadgasdata(String data, ArrayList<String> list) {
        int i = data.indexOf(':');
        int j = data.indexOf('(');
        sb.delete(0, sb.length());
        sb.append(data.substring(i + 1, j));
            if (!sb.substring(sb.length() - 3, sb.length() - 1).equals("16")) {
//                SystemClock.sleep(100);
                returncmd = returncmd + sb.toString();
                System.out.println("接收:" + returncmd);
                return;
            }
        if (optionflag.equals("EncControl") == false)
            this.stopFlag = true;//接收数据完成后,置标志,再不接收数据
        SystemClock.sleep(500);
        returncmd = returncmd + sb.toString();
        System.out.println("完整接收的数据:"+returncmd);
        clearData();
        sb.delete(0, sb.length());
        sb.append(returncmd.replaceAll(" ", ""));
        loading.dismiss();

        String addrs = CommonUtil.AddZeros(addr.getText().toString());
        // String addr = "000000000041";
        if (sb.substring(0, 2).equals("68") == false || sb.substring(18, 20).equals("68") == false) {
            Toast.makeText(context, "返回信息头不完整！",
                    Toast.LENGTH_SHORT).show();
            stopFlag = true;
            return;
        }
        if (sb.substring(6, 18).equals(reverbyte(addrs))) {
            if ((sb.substring(sb.length() - 4, sb.length() - 2)).equals(makeChecksum(sb.substring(0, sb.length() - 4)).toUpperCase()) == false) {
                Toast.makeText(context, "返回信息校验位不正确！",
                        Toast.LENGTH_SHORT).show();
                stopFlag = true;
                return;
            }
        }
        sb.delete(0, 20);// 删除68...68
        // String meterType = sb.substring(0,2);//80是仪表类型
        sb.delete(0, 2);
        String controlCode = sb.substring(0, 2);//01是控制码
        sb.delete(0, 2);
        String dataLenth = sb.substring(0, 2);// 数据长度
        sb.delete(0, 2);
        String dataZone = sb.substring(0, Integer.parseInt(dataLenth, 16) * 2);// 数据域
        if (controlCode.equals("01"))//抄数据返回的命令
        {
            String cmd = dataZone.substring(0, 8);// 数据标识
            cmd = reverbyte(cmd);// 倒置
            CMD = cmd;
            String dataStr = dataZone.substring(8);// 数据
            switch (cmd) {
                case "0400010C":
                    try {
                        list.clear();
                        list.add("抄读气表时间|" + addSign(reverbyte(dataStr.substring(0, 14)), "YYMMDDWWhhmmss") + "|");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "7000FF00":// 当前气量
                    try {
                        list.clear();
//                        String tempBug = sb.substring(90, 98);
//                        tempBug = addSign(reverbyte(tempBug.substring(0, 8)), "xxxxxx.xx");
//                        String temp = addSign(reverbyte(dataStr.substring(0, 8)), "xxxxxx.xx");
//                        double d = Double.parseDouble(temp) - Double.parseDouble(tempBug);
//                        String rightStr = String.format("%.2f", d);//当前结算周期气量不对
                        list.add("当前气量|" + addSign(reverbyte(dataStr.substring(0, 8)), "xxxxxx.xx") + "|" + "m³");
                        list.add("当前结算周期气量|" + addSign(reverbyte(dataStr.substring(8, 16)), "xxxxxx.xx") + "|" + "m³");
//                        list.add("当前结算周期气量|" + rightStr + "|" + "m³");
                        list.add("剩余金额（总）|" + addSign(reverbyte(dataStr.substring(16, 24)), "xxxxxx.xx") + "|" + "元");
                        list.add("剩余金额（本地）|" + addSign(reverbyte(dataStr.substring(24, 32)), "xxxxxx.xx") + "|" + "元");//保留
                        list.add("购气次数（本地）|" + addSign(reverbyte(dataStr.substring(32, 40)), "xxxxxxxx") + "|" + "次"); //保留
                        list.add("剩余金额（远程）|" + addSign(reverbyte(dataStr.substring(40, 48)), "xxxxxx.xx") + "|" + "元");
                        list.add("购气次数（远程）|" + addSign(reverbyte(dataStr.substring(48, 56)), "xxxxxxxx") + "|" + "次");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "7200FF00":// 实时状态查询
                    list.clear();
                    list.add("电池电压|" + addSign(reverbyte(dataStr.substring(0, 4)), "xx.xx") + "|" + "V");
                    list.add("实时时间|" + addSign(reverbyte(dataStr.substring(4, 18)), "YYMMDDWWhhmmss") + "|" + " ");
                    String bitStr = reverbyte(dataStr.substring(18, 22));
                    String bit = CommonUtil.AddZeros(Integer.toBinaryString(Integer.valueOf(bitStr, 16)), 16);
                    bit = reverbyte1(bit);
                    if (bit.charAt(0) == '1')
                        list.add("阀开/关|1:状态|开");
                    else
                        list.add("阀开/关|0:状态|关");
                    if (bit.charAt(1) == '1')
                        list.add("阀异常|1:状态|是");
                    else
                        list.add("阀异常|0:状态|否");
                    if (bit.charAt(2) == '1')
                        list.add("电压低|1:状态|是");
                    else
                        list.add("电压低|0:状态|否");
                    if (bit.charAt(3) == '1')
                        list.add("干簧管坏|1:状态|是");
                    else
                        list.add("干簧管坏|0:状态|否");
                    if (bit.charAt(4) == '1')
                        list.add("强磁干扰|1:状态|有");
                    else
                        list.add("强磁干扰|0:状态|无");
                    if (bit.charAt(5) == '1')
                        list.add("EEPROM异常|1:状态|是");
                    else
                        list.add("EEPROM异常|0:状态|否");
                    if (bit.charAt(6) == '1')
                        list.add("ESAM异常|1:状态|是");
                    else
                        list.add("ESAM异常|0:状态|否");
                    if (bit.charAt(7) == '1')
                        list.add("余额不足|1:状态|是");
                    else
                        list.add("余额不足|0:状态|否");
                    if (bit.charAt(8) == '1')
                        list.add("RTC异常|1:状态|是");
                    else
                        list.add("RTC异常|0:状态|否");
                    if (bit.charAt(9) == '1')
                        list.add("已开户|1:状态|是");
                    else
                        list.add("已开户|0:状态|否");
                    if (bit.charAt(10) == '1')
                        list.add("剩余金额小于最小扣费金额|1:状态|是");
                    else
                        list.add("剩余金额小于最小扣费金额|0:状态|否");
                    if (bit.charAt(11) == '1')
                        list.add("保留，卡充钱包剩余为0|1:状态|是");
                    else
                        list.add("保留，卡充钱包剩余为0|0:状态|否");
                    if (bit.charAt(12) == '1')
                        list.add("网充钱包剩余为0|1:状态|是");
                    else
                        list.add("网充钱包剩余为0|0:状态|否");
                    break;

                case "7002FF00": // 月冻结数据
                case "7002FF01":
                case "7002FF02":
                case "7002FF03":
                case "7002FF04":
                case "7002FF05":
                    list.clear();
                    int times = Integer.parseInt(cmd.substring(6, 8), 16);
                    String tipStr;
                    if (times == 0)
                        tipStr = "当前月";
                    else
                        tipStr = "上" + times + "月";
                    list.add(tipStr + "冻结累计用气量|" + addSign(reverbyte(dataStr.substring(0, 8)), "xxxxxx.xx") + "|" + "m³");
                    list.add(tipStr + "冻结累计使用金额|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(8, 16))) * 0.001) + "|" + "元");
                    list.add(tipStr + "冻结价格版本号|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(16, 20)), 16)) + "|" + "");
                    list.add("统计时间|" + addSign(reverbyte(dataStr.substring(20, 30)), "YYMMDDhhmm") + "|" + "");
                    //  case "70000001"://冻结总次数
                    //      list.clear();
                    //      list.add("冻结总次数|"+reverbyte(dataStr.substring(0, 4))+"|"+"次" );
                    break;
                case "7000FF01"://上1结算数据块
                case "7000FF02":
                case "7000FF03":
                case "7000FF04":
                case "7000FF05":
                case "7000FF06":
                case "7000FF07":
                case "7000FF08":
                case "7000FF09":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "结算";
                    list.add("总次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|" + "次");
                    list.add(tipStr + "时间|" + addSign(reverbyte(dataStr.substring(4, 14)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "总气量|" + addSign(reverbyte(dataStr.substring(14, 22)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "周期气量|" + addSign(reverbyte(dataStr.substring(22, 30)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "剩余金额(总)|" + addSign(reverbyte(dataStr.substring(30, 38)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "剩余金额(本地)|" + addSign(reverbyte(dataStr.substring(38, 46)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "剩余金额(远程)|" + addSign(reverbyte(dataStr.substring(46, 54)), "xxxxxx.xx") + "|元");
                    break;
                case "7001FF01"://上1次调价信息
                case "7001FF02":
                case "7001FF03":
                case "7001FF04":
                case "7001FF05":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "调价";
                    list.add(tipStr + "时间|" + addSign(reverbyte(dataStr.substring(0, 6)), "YYMMDD") + "|");
                    list.add(tipStr + "阶梯气价1|" + addSign(reverbyte(dataStr.substring(6, 14)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "阶梯气价2|" + addSign(reverbyte(dataStr.substring(14, 22)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "阶梯气价3|" + addSign(reverbyte(dataStr.substring(22, 30)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "阶梯气价4|" + addSign(reverbyte(dataStr.substring(30, 38)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "阶梯1累计用气量|" + addSign(reverbyte(dataStr.substring(38, 46)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "阶梯2累计用气量|" + addSign(reverbyte(dataStr.substring(46, 54)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "阶梯3累计用气量|" + addSign(reverbyte(dataStr.substring(54, 62)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "阶梯4累计用气量|" + addSign(reverbyte(dataStr.substring(62, 70)), "xxxxxx.xx") + "|m3");
                    break;
                case "7003FF00"://当前气价信息
                    list.clear();
                    // tipStr = "当前套";
                    list.add("实时气价|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(0, 8)), 16) * 0.01) + "|元");
                    list.add("阶梯气价1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(8, 16)), 16) * 0.01) + "|元");
                    list.add("阶梯气价2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(16, 24)), 16) * 0.01) + "|元");
                    list.add("阶梯气价3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(24, 32)), 16) * 0.01) + "|元");
                    list.add("阶梯气价4|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(32, 40)), 16) * 0.01) + "|元");
                    list.add("阶梯值1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(40, 48)), 16) * 0.01) + "|m3");
                    list.add("阶梯值2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(48, 56)), 16) * 0.01) + "|m3");
                    list.add("阶梯值3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(56, 64)), 16) * 0.01) + "|m3");
                    break;
                case "72010000"://抄读气表开阀次数
                    list.clear();
                    list.add("开阀次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;
                case "7201FF01"://抄读气表上1开阀事件
                case "7201FF02":
                case "7201FF03":
                case "7201FF04":
                case "7201FF05":
                case "7201FF06":
                case "7201FF07":
                case "7201FF08":
                case "7201FF09":
                case "7201FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "开阀";
                    list.add(tipStr + "时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    String temp = reverbyte(dataStr.substring(10, 12));
                    if (temp.equals("00"))
                        list.add(tipStr + "原因|" + "00:各关阀原因恢复" + "|");
                    else if (temp.equals("20"))
                        list.add(tipStr + "原因|" + "20:换电池" + "|");
                    else if (temp.equals("02"))
                        list.add(tipStr + "原因|" + "02:达到报警金额1" + "|");
                    else if (temp.equals("04"))
                        list.add(tipStr + "原因|" + "04:达到透支门限" + "|");
                    else if (temp.equals("08"))
                        list.add(tipStr + "原因|" + "08:ESAM坏" + "|");
                    else if (temp.equals("10"))
                        list.add(tipStr + "原因|" + "10:磁场干扰" + "|");
                    else if (temp.equals("40"))
                        list.add(tipStr + "原因|" + "40:大小流量关阀干簧管坏" + "|");
                    else if (temp.equals("80"))
                        list.add(tipStr + "原因|" + "80:远程开阀" + "|");
                    list.add(tipStr + "时累计用气量|" + addSign(reverbyte(dataStr.substring(12, 20)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "时剩余金额|" + addSign(reverbyte(dataStr.substring(20, 28)), "xxxxxx.xx") + "|元");
                    break;
                case "72020000"://抄读气表关阀次数
                    list.clear();
                    list.add("关阀次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;

                case "7202FF01"://抄读气表上1关阀事件
                case "7202FF02":
                case "7202FF03":
                case "7202FF04":
                case "7202FF05":
                case "7202FF06":
                case "7202FF07":
                case "7202FF08":
                case "7202FF09":
                case "7202FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "关阀";
                    list.add(tipStr + "时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    temp = reverbyte(dataStr.substring(10, 12));
                    if (temp.equals("00"))
                        list.add(tipStr + "原因|" + "00:停用关阀 " + "|");
                    else if (temp.equals("01"))
                        list.add(tipStr + "原因|" + "01:电池电压低" + "|");
                    else if (temp.equals("02"))
                        list.add(tipStr + "原因|" + "02:达到报警金额1" + "|");
                    else if (temp.equals("04"))
                        list.add(tipStr + "原因|" + "04:达到透支门限" + "|");
                    else if (temp.equals("08"))
                        list.add(tipStr + "原因|" + "08:ESAM坏" + "|");
                    else if (temp.equals("10"))
                        list.add(tipStr + "原因|" + "10:磁场干扰" + "|");
                    else if (temp.equals("20"))
                        list.add(tipStr + "原因|" + "20:掉电关阀" + "|");
                    else if (temp.equals("40"))
                        list.add(tipStr + "原因|" + "40:大小流量关阀干簧管坏" + "|");
                    else if (temp.equals("80"))
                        list.add(tipStr + "原因|" + "80:远程关阀" + "|");
                    list.add(tipStr + "时累计用气量|" + addSign(reverbyte(dataStr.substring(12, 20)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "时剩余金额|" + addSign(reverbyte(dataStr.substring(20, 28)), "xxxxxx.xx") + "|元");
                    break;
                case "72030000"://超大流量次数
                    list.clear();
                    list.add("超大流量次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;
                case "7203FF01"://上1超大流量
                case "7203FF02":
                case "7203FF03":
                case "7203FF04":
                case "7203FF05":
                case "7203FF06":
                case "7203FF07":
                case "7203FF08":
                case "7203FF09":
                case "7203FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "超大流量";
                    list.add(tipStr + "发生时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "发生时累计用气量|" + addSign(reverbyte(dataStr.substring(10, 18)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "发生时剩余金额|" + addSign(reverbyte(dataStr.substring(18, 26)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "流量值|" + reverbyte(dataStr.substring(26, 28)) + "|");
                    list.add(tipStr + "结束时间|" + addSign(reverbyte(dataStr.substring(28, 38)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "结束时累计用气量|" + addSign(reverbyte(dataStr.substring(38, 46)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "结束时剩余金额|" + addSign(reverbyte(dataStr.substring(46, 54)), "xxxxxx.xx") + "|元");
                    break;
                case "72040000"://超小流量次数
                    list.clear();
                    list.add("超小流量次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;
                case "7204FF01"://上1超小流量
                case "7204FF02":
                case "7204FF03":
                case "7204FF04":
                case "7204FF05":
                case "7204FF06":
                case "7204FF07":
                case "7204FF08":
                case "7204FF09":
                case "7204FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "超小流量";
                    list.add(tipStr + "发生时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "发生时累计用气量|" + addSign(reverbyte(dataStr.substring(10, 18)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "发生时剩余金额|" + addSign(reverbyte(dataStr.substring(18, 26)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "流量值|" + reverbyte(dataStr.substring(26, 28)) + "|");
                    list.add(tipStr + "结束时间|" + addSign(reverbyte(dataStr.substring(28, 38)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "结束时累计用气量|" + addSign(reverbyte(dataStr.substring(38, 46)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "结束时剩余金额|" + addSign(reverbyte(dataStr.substring(46, 54)), "xxxxxx.xx") + "|元");
                    break;
                case "72050000"://磁场干扰次数
                    list.clear();
                    list.add("磁场干扰次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;
                case "7205FF01"://上1磁场干扰
                case "7205FF02":
                case "7205FF03":
                case "7205FF04":
                case "7205FF05":
                case "7205FF06":
                case "7205FF07":
                case "7205FF08":
                case "7205FF09":
                case "7205FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "磁场干扰";
                    list.add(tipStr + "发生时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "发生时累计用气量|" + addSign(reverbyte(dataStr.substring(10, 18)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "发生时剩余金额|" + addSign(reverbyte(dataStr.substring(18, 26)), "xxxxxx.xx") + "|元");
                    list.add(tipStr + "结束时间|" + addSign(reverbyte(dataStr.substring(26, 36)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "结束时累计用气量|" + addSign(reverbyte(dataStr.substring(36, 44)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "结束时剩余金额|" + addSign(reverbyte(dataStr.substring(44, 52)), "xxxxxx.xx") + "|元");
                    break;
                case "72060000"://阀门异常次数
                    list.clear();
                    list.add("阀门异常次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    break;
                case "7206FF01"://上1阀门异常
                case "7206FF02":
                case "7206FF03":
                case "7206FF04":
                case "7206FF05":
                case "7206FF06":
                case "7206FF07":
                case "7206FF08":
                case "7206FF09":
                case "7206FF0A":
                    list.clear();
                    times = Integer.parseInt(cmd.substring(6, 8), 16);
                    tipStr = "上" + times + "阀门异常";
                    list.add(tipStr + "发生时间|" + addSign(reverbyte(dataStr.substring(0, 10)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "发生时累计用气量|" + addSign(reverbyte(dataStr.substring(10, 18)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "发生时剩余金额|" + addSign(reverbyte(dataStr.substring(18, 26)), "xxxxxx.xx") + "|元");
                    temp = reverbyte(dataStr.substring(26, 28));
                    if (temp.equals("00")) {
                        list.add(tipStr + "状态|" + "00:开" + "|");
                    } else if (temp.equals("01")) {
                        list.add(tipStr + "状态|" + "01:关" + "|");
                    }
                    list.add(tipStr + "结束时间|" + addSign(reverbyte(dataStr.substring(28, 38)), "YYMMDDhhmm") + "|");
                    list.add(tipStr + "结束时累计用气量|" + addSign(reverbyte(dataStr.substring(38, 46)), "xxxxxx.xx") + "|m3");
                    list.add(tipStr + "结束时剩余金额|" + addSign(reverbyte(dataStr.substring(46, 54)), "xxxxxx.xx") + "|元");
                    break;
                case "710100FF"://气表参数信息
                    list.clear();
                    //   list.add("用气类型|"+reverbyte(dataStr.substring(0,2))+"|");
                    list.add("报警限额1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(2, 10)), 16) * 0.01) + "|元");
                    list.add("报警限额2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(10, 18)), 16) * 0.01) + "|元");
                    list.add("充值限额|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(18, 26)), 16) * 0.01) + "|元");
                    list.add("月统计日期|" + addSign(reverbyte(dataStr.substring(26, 30)), "DDhh") + "|");
                    list.add("大流量门限次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(30, 32)), 16)) + "|次");
                    list.add("大流量门限时间|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(32, 34)), 16)) + "|秒");
                    list.add("小流量门限次数|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(34, 36)), 16)) + "|次");
                    list.add("小流量门限时间|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(36, 38)), 16)) + "|秒");
                    list.add("停用关阀时间|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(38, 42)), 16)) + "|天");
                    list.add("燃气表表号|" + reverbyte(dataStr.substring(42, 58)) + "|");
                    list.add("用户号|" + reverbyte(dataStr.substring(58, 70)) + "|");
                    break;
                case "710200FF"://当前套价格
                case "710300FF"://备用套
                    list.clear();
                    list.add("价格版本号|" + String.valueOf(Integer.parseInt(reverbyte(dataStr.substring(0, 4)), 16)) + "|");
                    list.add("第一套阶梯值1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(4, 12)), 16) * 0.01) + "|m3");
                    list.add("第一套阶梯值2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(12, 20)), 16) * 0.01) + "|m3");
                    list.add("第一套阶梯值3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(20, 28)), 16) * 0.01) + "|m3");
                    list.add("第一套阶气价1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(52, 60)), 16) * 0.01) + "|元");
                    list.add("第一套阶气价2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(60, 68)), 16) * 0.01) + "|元");
                    list.add("第一套阶气价3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(68, 76)), 16) * 0.01) + "|元");
                    list.add("第一套阶气价4|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(76, 84)), 16) * 0.01) + "|元");
                    list.add("第二套阶梯值1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(108, 116)), 16) * 0.01) + "|m3");
                    list.add("第二套阶梯值2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(116, 124)), 16) * 0.01) + "|m3");
                    list.add("第二套阶梯值3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(124, 132)), 16) * 0.01) + "|m3");
                    list.add("第二套阶气价1|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(156, 164)), 16) * 0.01) + "|元");
                    list.add("第二套阶气价2|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(164, 172)), 16) * 0.01) + "|元");
                    list.add("第二套阶气价3|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(172, 180)), 16) * 0.01) + "|元");
                    list.add("第二套阶气价4|" + String.format("%.2f", Integer.parseInt(reverbyte(dataStr.substring(180, 188)), 16) * 0.01) + "|元");
                    list.add("时段表1|" + addSign(reverbyte(dataStr.substring(212, 218)), "MMDDNN") + "|");
                    list.add("时段表2|" + addSign(reverbyte(dataStr.substring(218, 224)), "MMDDNN") + "|");
                    list.add("时段表3|" + addSign(reverbyte(dataStr.substring(224, 230)), "MMDDNN") + "|");
                    list.add("时段表4|" + addSign(reverbyte(dataStr.substring(230, 236)), "MMDDNN") + "|");
                    temp = reverbyte(dataStr.substring(236, 238));
                    if (temp.equals("00")) {
                        list.add("结算方式|" + "00:月结算|");
                    } else if (temp.equals("01")) {
                        list.add("结算方式|" + "01:季度结算|");
                    } else if (temp.equals("02")) {
                        list.add("结算方式|" + "02:年结算|");
                    }
                    list.add("月结算日|" + addSign(reverbyte(dataStr.substring(238, 242)), "DDhh") + "|");
                    list.add("季度结算日1|" + addSign(reverbyte(dataStr.substring(242, 246)), "MMDD") + "|");
                    list.add("季度结算日2|" + addSign(reverbyte(dataStr.substring(246, 250)), "MMDD") + "|");
                    list.add("季度结算日3|" + addSign(reverbyte(dataStr.substring(250, 254)), "MMDD") + "|");
                    list.add("季度结算日4|" + addSign(reverbyte(dataStr.substring(254, 258)), "MMDD") + "|");
                    list.add("年结算日|" + addSign(reverbyte(dataStr.substring(258, 262)), "MMDD") + "|");
                    list.add("切换时间|" + addSign(reverbyte(dataStr.substring(262, 268)), "YYMMDD") + "|");
                    break;
            }
            isUpLoad = 0;
            String cmdName = set_command_select.getText().toString();
            String strAddr = CommonUtil.AddZeros(addr.getText().toString());
//                                    UploadDataGas(list,strAddr,CMD);//上传蓝牙抄表数据
            if (list.size() > 1)
                insertDataGas(strAddr, cmdName, CMD, isUpLoad, list);//抄表数据入库
        } else if (controlCode.equals("02")) {
            try {
                list.clear();
                list.add("编写|成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (controlCode.equals("07")) {
            try {
                list.clear();
                list.add("控制|成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                list.clear();
                list.add("编写/控制|失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int aa = 0; aa < list.size(); aa++) {
            BleDataModel dto = new BleDataModel();
            dto.of(list.get(aa));
            bledata.add(dto);
        }
        bledataadapter.notifyDataSetChanged();
        stopFlag = true;
    }

    private void returnReadWaterMergeCs(String data,ArrayList<String> list)
    {

        int i = data.indexOf(':');
        int j = data.indexOf('(');
        sb.delete(0, sb.length());
        sb.append(data.substring(i + 1, j));
        if (!sb.substring(sb.length() - 3, sb.length() - 1).equals("16")) {
            returncmd = returncmd + sb.toString();
        } else {
            loading.dismiss();
            returncmd = returncmd + sb.toString();
            clearData();
            sb.delete(0, sb.length());
            sb.append(returncmd.replaceAll(" ", ""));
            String addrs = CommonUtil.AddZeros(addr.getText().toString());
            // String addr = "000000000041";
            if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
                if (sb.substring(6, 18).equals(reverbyte(addrs))) {
                    if ((sb.substring(sb.length() - 4, sb.length() - 2))
                            .equals(makeChecksum(sb.substring(0, sb.length() - 4)).toUpperCase())) {
                        sb.delete(0, 2);// 删除68...68
                        String strLen = sb.substring(0, 2);
                        int intLen = Integer.parseInt(strLen, 16);
                        sb.delete(0, 2);// 删除总长度
                        sb.delete(0, 16);// 删除总长度---第2个68
                        // String meterType = sb.substring(0,2);//80是仪表类型
                        sb.delete(0, 2);
                        String controlCode = sb.substring(0, 2);//01是控制码
                        sb.delete(0, 2);
                        intLen = intLen - 1;  //去掉控制码长度
                        String dataZone = sb.substring(0, intLen * 2);// 数据域
                        if (controlCode.equals("01"))//抄数据返回的命令
                        {
                            try {
                                strWaterGscs = reverbyte(dataZone.substring(58, 66));
                                int intCs = Integer.parseInt(strWaterGscs);
                                intCs = intCs + 1;
                                strWaterGscs = Integer.toString(intCs);
                                strWaterGscs = CommonUtil.AddZeros(strWaterGscs, 8);
                                //开始计算购买水量
                                strWaterGfsl = reverbyte(dataZone.substring(78, 86));
                                int intScGfsl = Integer.parseInt(strWaterGfsl);

                                //本次输入购买量
                                String strScGfsl = metercharge.getText().toString();
                                int intGfsl = Integer.parseInt(strScGfsl);
                                intGfsl = intGfsl * 100;
                                //购买量 = 本次输入购买量 + 抄回购买量
                                intGfsl = intGfsl + intScGfsl;
                                strWaterGfsl = Integer.toString(intGfsl);
                                strWaterGfsl = CommonUtil.AddZeros(strWaterGfsl, 8);
                                //第二步， 抄回数据之后， 开始webser
                                loading.show();
                                setDialogLabel("正在身份认证...");
                                MyAutoWebThread autoWebThread = new MyAutoWebThread();
                                new Thread(autoWebThread).start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, "返回控制码不正确！",
                                    Toast.LENGTH_SHORT).show();
                            stopFlag = true;
                        }
                    } else {
                        Toast.makeText(context, "返回信息校验位不正确！",
                                Toast.LENGTH_SHORT).show();
                        stopFlag = true;
                    }
                } else {
                    Toast.makeText(context, "返回信息表地址不正确！",
                            Toast.LENGTH_SHORT).show();
                    stopFlag = true;
                }
            } else {
                Toast.makeText(context, "返回信息头不完整！",
                        Toast.LENGTH_SHORT).show();
                stopFlag = true;
            }
        }

    }

    private void returnSetWaterCharge(String data,ArrayList<String> list)
    {

        int i = data.indexOf(':');
        int j = data.indexOf('(');
        sb.delete(0, sb.length());
        sb.append(data.substring(i + 1, j));
        if (!sb.substring(sb.length() - 3, sb.length() - 1).equals("16")) {
            returncmd = returncmd + sb.toString();
        } else {
            loading.dismiss();
            returncmd = returncmd + sb.toString();
            clearData();
            sb.delete(0, sb.length());
            sb.append(returncmd.replaceAll(" ", ""));
            String addrs = CommonUtil.AddZeros(addr.getText().toString());
            // String addr = "000000000041";
            if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
                if (sb.substring(6, 18).equals(reverbyte(addrs))) {
                    if ((sb.substring(sb.length() - 4, sb.length() - 2))
                            .equals(makeChecksum(sb.substring(0, sb.length() - 4)).toUpperCase())) {
                        sb.delete(0, 20);// 删除68...68
                        // String meterType = sb.substring(0,2);//80是仪表类型
                        sb.delete(0, 2); //删除返回控制字
                        String controlCode = sb.substring(0, 2);//01是控制码
                        sb.delete(0, 2);
                        String dataLenth = sb.substring(0, 2);// 数据长度
                        sb.delete(0, 2);
                        String dataZone = sb.substring(0, Integer.parseInt(dataLenth, 16) * 2);// 数据域
                        if (controlCode.equals("02")) {
                            //身份认证成功， 开始进行充值webservice
                            list.clear();
                            list.add("远程气表充值成功");
                            for (int aa = 0; aa < list.size(); aa++) {
                                BleDataModel dto = new BleDataModel();
                                dto.of(list.get(aa));
                                bledata.add(dto);
                            }
                            bledataadapter.notifyDataSetChanged();
                        } else if (controlCode.equals("82")) {
                            list.add("-1");
                        }
                    } else {
                        Toast.makeText(context, "返回信息校验位不正确！",
                                Toast.LENGTH_SHORT).show();
                        stopFlag = true;
                        //                            System.out.println("返回信息校验位不正确！");
                    }
                } else {
                    Toast.makeText(context, "返回信息表地址不正确！",
                            Toast.LENGTH_SHORT).show();
                    stopFlag = true;
                    //                        System.out.println("返回信息表地址不正确！");
                }
            } else {
                Toast.makeText(context, "返回信息头不完整！",
                        Toast.LENGTH_SHORT).show();
                stopFlag = true;
                //                    System.out.println("返回信息头不完整！");
            }
        }

    }
    // 数据返回解析 zhdl

    private void returnSetWaterAuto(String data,ArrayList<String> list)
    {

        int i = data.indexOf(':');
        int j = data.indexOf('(');
        sb.delete(0, sb.length());
        sb.append(data.substring(i + 1, j));
        if (!sb.substring(sb.length() - 3, sb.length() - 1).equals("16")) {
            returncmd = returncmd + sb.toString();
        } else {
            loading.dismiss();
            returncmd = returncmd + sb.toString();
            clearData();
            sb.delete(0, sb.length());
            sb.append(returncmd.replaceAll(" ", ""));
            String addrs = CommonUtil.AddZeros(addr.getText().toString());
            // String addr = "000000000041";
            if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
                if (sb.substring(6, 18).equals(reverbyte(addrs))) {
                    if ((sb.substring(sb.length() - 4, sb.length() - 2))
                            .equals(makeChecksum(sb.substring(0, sb.length() - 4)).toUpperCase())) {
                        sb.delete(0, 20);// 删除68...68
                        // String meterType = sb.substring(0,2);//80是仪表类型
                        sb.delete(0, 2); //删除返回控制字
                        String controlCode = sb.substring(0, 2);//01是控制码
                        sb.delete(0, 2);
                        String dataLenth = sb.substring(0, 2);// 数据长度
                        sb.delete(0, 2);
                        String dataZone = sb.substring(0, Integer.parseInt(dataLenth, 16) * 2);// 数据域
                        if (controlCode.equals("03")) {
                            //身份认证成功， 开始进行充值webservice
                            strCmdAutoRand = dataZone;

                            loading.show();
                            setDialogLabel("正在充值...");



                            ComBleActivity.MyChargeWebThread chargeWebThread = new ComBleActivity.MyChargeWebThread();
                            new Thread(chargeWebThread).start();



                        } else if (controlCode.equals("83") || (controlCode.equals("87"))) {
                            list.add("-1");
                        } else if (controlCode.equals("07")) {
                            list.add("0");//控制成功
                        }
                    } else {
                        Toast.makeText(context, "返回信息校验位不正确！",
                                Toast.LENGTH_SHORT).show();
                        stopFlag = true;
                        //                            System.out.println("返回信息校验位不正确！");
                    }
                } else {
                    Toast.makeText(context, "返回信息表地址不正确！",
                            Toast.LENGTH_SHORT).show();
                    stopFlag = true;
                    //                        System.out.println("返回信息表地址不正确！");
                }
            } else {
                Toast.makeText(context, "返回信息头不完整！",
                        Toast.LENGTH_SHORT).show();
                stopFlag = true;
                //                    System.out.println("返回信息头不完整！");
            }
        }
    }

    private void returnSetParameters(String data,ArrayList<String> list)
    {
        setfraqvalue.setText(setfraqvalueValue);
        int i = data.indexOf('(');
        int j = data.indexOf(')');
        sb.delete(0, sb.length());
        returncmd = data.substring(i + 1, j);
        sb.append(returncmd.replaceAll(" ", ""));
        if (sb.substring(sb.length() - 2, sb.length()).equals("OK")) {
            Toast.makeText(context, "抄控器无线模块参数设置成功！", Toast.LENGTH_SHORT).show();
            stopFlag = true;
//            System.out.println("shitao 003");
        } else {
            Toast.makeText(context, "抄控器无线模块参数设置失败！", Toast.LENGTH_SHORT).show();
            stopFlag = true;
        }
    }
    private void prasereturn(String data) {
        System.out.println("optionflag标志-->"+optionflag);
        if (this.stopFlag ==true)
            return;
        loading.dismiss();
        ArrayList<String> list = new ArrayList<String>();
        try {
            if ((optionflag.equals("readdata")) || (optionflag.equals("EncControl"))) {
                returnEncControl(data, list);
            } else if (optionflag.equals("readgasdata")) {
                returnReadgasdata(data, list);
            } else if (optionflag.equals("readWaterMergeCs")) {
                returnReadWaterMergeCs(data, list);
            } else if (optionflag.equals("setWaterCharge")) {
                returnSetWaterCharge(data, list);
            } else if (optionflag.equals("setWaterAuto")) {
                returnSetWaterAuto(data, list);
            } else if (optionflag.equals("setParameters")) {
                returnSetParameters(data, list);
//                System.out.println("shitao 004");
            }
        } catch (Exception e)
        {
            Toast.makeText(context, "返回数据错误,请重试！", Toast.LENGTH_SHORT).show();
            stopFlag = true;
            return;
        }
    }

    private byte[] converStringToByteArray(String data) {
        byte[] b = data.getBytes();
//        System.out.println(new String(b));
        return b;
    }

    private static String reverbyte(String data) {
        byte[] bytes = data.getBytes();
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i < bytes.length / 2; i++) {
            bytes1[i * 2] = bytes[bytes.length - 1 - i * 2 - 1];
            bytes1[i * 2 + 1] = bytes[bytes.length - 1 - i * 2];
        }
        return new String(bytes1);
    }

    public static String setParameters(String ID, String... value) {
        String data = "";
        String temp = "";
        switch (ID) {
            case "F1"://无线模块参数设置
                // 信道号
                temp = Integer.toHexString((int) ((Double.parseDouble(value[0]) - 470.00) * 2) + 1);
                temp = CommonUtil.AddZeros(temp, 2);
                data = data + temp;
                // 带宽
                if (value[1].equals("7.81"))
                    temp = "01";
                else if (value[1].equals("10.41"))
                    temp = "02";
                else if (value[1].equals("15.62"))
                    temp = "03";
                else if (value[1].equals("20.83"))
                    temp = "04";
                else if (value[1].equals("31.25"))
                    temp = "05";
                else if (value[1].equals("41.66"))
                    temp = "06";
                else if (value[1].equals("62.50"))
                    temp = "07";
                else if (value[1].equals("125"))
                    temp = "08";
                else if (value[1].equals("250"))
                    temp = "09";
                else if (value[1].equals("500"))
                    temp = "0A";
                data = data + temp;
                // 扩频因子
                temp = Integer.toHexString(Integer.parseInt(value[2]) - 5);
                temp = CommonUtil.AddZeros(temp, 2);
                data = data + temp;
                // 编码率
                if (value[3].equals("4_5"))
                    temp = "01";
                else if (value[3].equals("4_6"))
                    temp = "02";
                else if (value[3].equals("4_7"))
                    temp = "03";
                else if (value[3].equals("4_8"))
                    temp = "04";
                data = data + temp;
                break;
            case "F2"://唤醒参数设置
                for (String tmp : value) {//唤醒地址字节数   //前导码字节数 //前导码字节数 //唤醒后等待时间
                    temp = Integer.toHexString(Integer.parseInt(tmp));
                    CommonUtil.AddZeros(temp, 2);
                    data = data + temp;
                }
            case "F3"://模式设置
                for (String tmp : value) {//唤醒地址字节数   //前导码字节数 //前导码字节数 //唤醒后等待时间
                    temp = Integer.toHexString(Integer.parseInt(tmp));
                    CommonUtil.AddZeros(temp, 2);
                    data = data + temp;
                }
            case "F4"://唤醒地址设置
                temp = (value[0]);
                temp = CommonUtil.AddZeros(temp, 12);
                data = data + temp;
        }
        int l = data.length() / 2;
        String len = Integer.toString(l);
        len = CommonUtil.AddZeros(len, 4);
        String str = "68" + ID + reverbyte(len) + data;//长度需要倒置
        String cs = makeChecksum(str);
        str += cs + "16";

        return str;
    }


    private String reverbyte1(String data)//每个字符倒置
    {
        byte[] bytes = data.getBytes();
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i <= bytes.length - 1; i++) {
            bytes1[i] = bytes[bytes.length - i - 1];
        }
        return new String(bytes1);
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    //  身份认证接口
    public void chargeWebAuto(String meteraddr) {
        try {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", addr.getText().toString());
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterWater", jsobj.toString());
            SystemAPI.meter_waterAuto(map, authCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyAutoWebThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterWater", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_WaterAuto, map); //水表身份认证
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject jsonesult = new JSONObject(result);
                    int res = jsonesult.getInt("strBackFlag");
                    if (res == 0) {  // 身份认证返回数据成功
//                        String s RandData = jsonesult.getString("strOutRandAuth");
//                        setWaterAuto("7A000001", addr.getText().toString(), sRandData);
                    } else {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
//          加载用户配置
            Config.loadUserSet(activity);
        }
    }

    /**
     * 同步服务端数据
     */
// 身份认证， webservice， 子线程执行


    // 身份认证， webservice， 子线程执行
    class MyChargeWebThread implements Runnable {
        public void run() {
            // 从服务器获取数据更新
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
                jsobj.put("chargeMoney", strWaterGfsl);
                jsobj.put("strGmcs", strWaterGscs);
                jsobj.put("sPutRand", strCmdAutoRand);
            } catch (JSONException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeterWater", jsobj.toString());
            String result = HttpServiceUtil.post(ContantsUtil.URL_WaterCharge, map); //水表身份认证
            if (!CheckUtil.isNull(result)) {
                try {
                    JSONObject jsonesult = new JSONObject(result);
                    int res = jsonesult.getInt("strBackFlag");
                    if (res == 0) {  // 关阀水量充值， 返回数据成功
                        String strOutCharge = jsonesult.getString("strOutCharge");
                        setWaterCharge("7B000002", addr.getText().toString(), strOutCharge);
                    } else {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
//          加载用户配置
            Config.loadUserSet(activity);
        }
    }

    private void doTimeOut(int second)//超时计算
    {
        try {
            TimeOutThread t = new TimeOutThread(second);
            new Thread(t).start();
        } catch (Exception e
                ) {
            e.printStackTrace();
            return;
        }
    }

    class TimeOutThread implements Runnable {//超时线程 当超过规定时间,退出loding 并提示"超时"
        private int second;

        public TimeOutThread(int second) {
            this.second = second;
        }

        public void run() {
            try {
                int i = 0;
                while (i < second*10)//
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
                    bundle.putString("title", "通信超时,请重试!");
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

    private void clearData() {
        bledata.clear();
        bledataadapter.notifyDataSetChanged();

    }
    @Override//返回按键
    public void onBackPressed() {
        stopFlag = true;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

// 身份认证， webservice， 子线程执行



/*
class gasMeterSwitchThread implements Runnable {
    public void run() {
        // 从服务器获取数据更新
        JSONObject jsobj = new JSONObject();
        try {
            jsobj.put("meterAddr", CommonUtil.AddZeros(addr.getText().toString()));
        } catch (JSONException ex) {
            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("NgMeterBlueGas", jsobj.toString());
        String result = HttpServiceUtil.post(ContantsUtil.URL_WaterAuto, map); //气表身份认证
        if (!CheckUtil.isNull(result)) {
            try {
                JSONObject jsonesult = new JSONObject(result);
                int res = jsonesult.getInt("strBackFlag");
                if (res == 0) {  // 身份认证返回数据成功
                    sb.delete(0, sb.length());
                    writeOption(str);
//                        String s RandData = jsonesult.getString("strOutRandAuth");
//                        setWaterAuto("7A000001", addr.getText().toString(), sRandData);
                } else {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
//          加载用户配置
        Config.loadUserSet(activity);
    }
}*/