//package com.zfg.org.myexample.blecommand;
//
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.zfg.org.myexample.R;
//import com.zfg.org.myexample.ViewInject;
//import com.zfg.org.myexample.activity.BasicActivity;
//import com.zfg.org.myexample.activity.DialogLoading;
//import com.zfg.org.myexample.application.DbApplication;
//import com.zfg.org.myexample.ble.Utils.Constants;
//import com.zfg.org.myexample.ble.Utils.GattAttributes;
//import com.zfg.org.myexample.ble.Utils.Utils;
//import com.zfg.org.myexample.ble.adapter.CharacteristicsAdapter;
//import com.zfg.org.myexample.ble.adapter.MessagesAdapter;
//import com.zfg.org.myexample.ble.bean.Message;
//import com.zfg.org.myexample.ble.service.BluetoothLeService;
//import com.zfg.org.myexample.dialog.CheckListDialog;
//import com.zfg.org.myexample.dto.CheckModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by Administrator on 2016-07-03.
// *
// */
//public class BleCommandActivity  extends BasicActivity implements View.OnClickListener {
//
//    private DbApplication myApplication;
//    private final List<BluetoothGattCharacteristic> gattlist = new ArrayList<>();
//    private CharacteristicsAdapter adapter;
//
//    private final List<Message> list = new ArrayList<>();
//
//    private MessagesAdapter madapter;
//
//    private BluetoothGattCharacteristic notifyCharacteristic;
//    private BluetoothGattCharacteristic readCharacteristic;
//    private BluetoothGattCharacteristic writeCharacteristic;
//    private BluetoothGattCharacteristic indicateCharacteristic;
//
//    private DialogLoading loading;
//
//    private boolean nofityEnable;
//    private boolean indicateEnable;
//
//    private StringBuilder sb;
//    private String returncmd;
//
//    //  地址输入框
//    @ViewInject(id = R.id.meter_addr)
//    private EditText addr;
//
//    //  返回
//    @ViewInject(id = R.id.back_btn)
//    private Button backBtn;
//    //  抄表
//    @ViewInject(id = R.id.read_btn)
//    private Button readBtn;
//
//    BleCommandActivity activity;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_monthfrzeecommand);
//        activity = (BleCommandActivity) context;
//        loading = new DialogLoading(activity);
//        initActivity();
//        initdata();
//        initCharacteristics();
//        registerReceiver(mGattUpdateReceiver, Utils.makeGattUpdateIntentFilter());
//        prepareBroadcastDataNotify(notifyCharacteristic);
//    }
//
//    public void initdata(){
//        myApplication = (DbApplication) getApplication();
//        sb = new StringBuilder();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.back_btn:
//                finish();
//                break;
//            case R.id.read_btn:
//                readdata();
//                break;
//        }
//    }
//
//    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//
//            Bundle extras = intent.getExtras();
//            if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                // Data Received
//                if (extras.containsKey(Constants.EXTRA_BYTE_VALUE)) {
//                    if (extras.containsKey(Constants.EXTRA_BYTE_UUID_VALUE)) {
//                        if (myApplication != null) {
//                            BluetoothGattCharacteristic requiredCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(GattAttributes.USR_SERVICE),-1,-1);
////                            BluetoothGattCharacteristic requiredCharacteristic = myApplication.getCharacteristic();
//                            String uuidRequired = requiredCharacteristic.getUuid().toString();
//                            String receivedUUID = intent.getStringExtra(Constants.EXTRA_BYTE_UUID_VALUE);
//
//                            byte[] array = intent.getByteArrayExtra(Constants.EXTRA_BYTE_VALUE);
//                            Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,formatMsgContent(array));
//                            prasereturn(msg.getContent());
//                        }
//                    }
//                }
//                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE)) {
//                    if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID)) {
//                        BluetoothGattCharacteristic requiredCharacteristic = myApplication.getCharacteristic();
//                        String uuidRequired = requiredCharacteristic.getUuid().toString();
//                        String receivedUUID = intent.getStringExtra(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID);
//
//                        byte[] array = intent.getByteArrayExtra(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE);
//
//                        System.out.println("GattDetailActivity---------------------->descriptor:" + Utils.ByteArraytoHex(array));
////                        if (isDebugMode){
////
////                        }else if (uuidRequired.equalsIgnoreCase(receivedUUID)) {
////
////                        }
//                    }
//                }
//            }
//
//            if (action.equals(BluetoothLeService.ACTION_GATT_DESCRIPTORWRITE_RESULT)){
//                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT)){
//                    int status = extras.getInt(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT);
//                    if (status != BluetoothGatt.GATT_SUCCESS){
//
//                    }
//                }
//            }
//
//            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_ERROR)) {
//                if (extras.containsKey(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE)) {
//                    String errorMessage = extras.
//                            getString(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE);
//                    System.out.println("GattDetailActivity---------------------->err:" + errorMessage);
//                }
//
//            }
//
//            //写characteristics成功
//            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_WRITE_SUCCESS)){
//
//            }
//
////
//            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
////                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
////                if (state == BluetoothDevice.BOND_BONDING) {}
////                else if (state == BluetoothDevice.BOND_BONDED) {}
////                else if (state == BluetoothDevice.BOND_NONE) {}
//            }
//
//            //连接断开
//            if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)){
//
//            }
//        }
//
//    };
//
////  抄读数据
//    private void readdata(){
//
//    };
//
////  解析返回
//    public void prasereturn(String data){
//        int i = data.indexOf(':');
//        int j = data.indexOf('(');
//        sb.delete(0,sb.length());
//        sb.append(data.substring(i+1, j));
////            Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
//        if (!sb.substring(sb.length()-3,sb.length()-1).equals("16")){
//            returncmd = returncmd + sb.toString();
//        } else {
//            returncmd = returncmd + sb.toString();
//            sb.delete(0,sb.length());
//            sb.append(returncmd.replaceAll(" ", ""));
//
//            if (sb.substring(0, 2).equals("68") && sb.substring(18, 20).equals("68")) {
//                if (sb.substring(6,18).equals(reverbyte(AddZeros(addr.getText().toString())))) {
//                    if ((sb.substring(sb.length()-4,sb.length()-2)).equals(makeChecksum(sb.substring(0,sb.length()-4)).toUpperCase())){
//                        sb.delete(0,sb.indexOf(reverbyte("7000FF01"))+8);
//
//                        sb.delete(56,sb.length());
////                          当前气量
//                        String b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData1.setText(b);
//                        sb.delete(0,8);
////
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData2.setText(b);
//                        sb.delete(0,8);
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData3.setText(b);
//                        sb.delete(0,8);
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData4.setText(b);
//                        sb.delete(0,8);
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData5.setText(b);
//                        sb.delete(0,8);
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData6.setText(b);
//                        sb.delete(0,8);
//                        b = String.valueOf(Integer.parseInt(reverbyte(sb.substring(0, 8)).substring(0,6)))+'.'+reverbyte(sb.substring(0, 8)).substring(6,8);
////                        strBackData7.setText(b);
//                        sb.delete(0,8);
////                            Toast.makeText(context,sb.toString(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context,"返回信息校验位不正确！", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context,"返回信息表地址不正确！", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(context,"返回信息头不完整！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
////  编数据
//    private void writedata(){
//
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopNotifyOrIndicate();
//        unregisterReceiver(mGattUpdateReceiver);
//    }
//
//    private void stopNotifyOrIndicate(){
//        if (nofityEnable)
//            stopBroadcastDataNotify(notifyCharacteristic);
//        if (indicateEnable)
//            stopBroadcastDataIndicate(indicateCharacteristic);
//    }
//
//    /**
//     * Stopping Broadcast receiver to broadcast notify characteristics
//     *
//     * @param characteristic
//     */
//    void stopBroadcastDataNotify(
//            BluetoothGattCharacteristic characteristic) {
//        final int charaProp = characteristic.getProperties();
//        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
//            BluetoothLeService.setCharacteristicNotification(characteristic, false);
//        }
//    }
//
//    /**
//     * Stopping Broadcast receiver to broadcast indicate characteristics
//     *
//     * @param characteristic
//     */
//    void stopBroadcastDataIndicate(
//            BluetoothGattCharacteristic characteristic) {
//        final int charaProp = characteristic.getProperties();
//
//        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
//            BluetoothLeService.setCharacteristicIndication(characteristic, false);
//        }
//
//    }
//
//    private byte[] converStringToByteArray(String data){
//        byte[] b = data.getBytes();
////        System.out.println(new String(b));
//        return  b;
//    }
//
//    private String reverbyte(String data) {
//        byte[] bytes = data.getBytes();
//        byte[] bytes1 = new byte[bytes.length];
//        for (int i = 0; i < bytes.length / 2; i++) {
//            bytes1[i * 2] = bytes[bytes.length - 1 - i * 2 - 1];
//            bytes1[i * 2 + 1] = bytes[bytes.length - 1 - i * 2];
//        }
//        return new String(bytes1);
//    }
//
//    /**
//     *  补零12位
//     */
//    private static String AddZeros(String data){
//        String str ="000000000000";
//        String str_m=str.substring(0, 12-data.length())+data;
//        return str_m;
////        System.out.println(str_m);
//    }
//
//    /**
//     * 格式化解析
//     */
//    private String formatMsgContent(byte[] data){
//        return "HEX:"+Utils.ByteArraytoHex(data)+"(ASSCII:"+Utils.byteToASCII(data)+")";
//    }
//
//    /**
//     * 生成16进制累加和校验码
//     *
//     * @param data 除去校验位的数据
//     * @return
//     */
//    public static String makeChecksum(String data) {
//        int total = 0;
//        int len = data.length();
//        int num = 0;
//        while (num < len) {
//            String s = data.substring(num, num + 2);
//            System.out.println(s);
//            total += Integer.parseInt(s, 16);
//            num = num + 2;
//        }
//        /**
//         * 用256求余最大是255，即16进制的FF
//         */
//        int mod = total % 256;
//        String hex = Integer.toHexString(mod);
//        len = hex.length();
//        //如果不够校验位的长度，补0,这里用的是两位校验
//        if (len < 2) {
//            hex = "0" + hex;
//        }
//        return hex;
//    }
//
//    private void initActivity() {
//        backBtn.setOnClickListener(this);
//        readBtn.setOnClickListener(this);
//    }
//
//
//    private void initCharacteristics(){
////        BluetoothGattCharacteristic characteristic = myApplication.getCharacteristic();
////        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.fromString(GattAttributes.USR_SERVICE),-1,-1);
////        if (characteristic.getUuid().toString().equals(GattAttributes.USR_SERVICE)){
//        List<BluetoothGattCharacteristic> characteristics = ((DbApplication)getApplication()).getServices().get(0).getService().getCharacteristics();
//
//        for (BluetoothGattCharacteristic c :characteristics){
//            if (Utils.getPorperties(this,c).equals("Notify")){
//                notifyCharacteristic = c;
//                continue;
//            }
//
//            if (Utils.getPorperties(this,c).equals("Write")){
//                writeCharacteristic = c;
//                continue;
//            }
//        }
//    }
//
//}
