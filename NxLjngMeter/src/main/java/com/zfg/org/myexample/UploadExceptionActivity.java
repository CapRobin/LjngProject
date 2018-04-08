package com.zfg.org.myexample;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zfg.org.myexample.activity.BaseFragment;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;
import com.zfg.org.myexample.widget.SelectPicPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//import rx.Observable;
//import rx.functions.Func1;

/**
 * Created by Administrator on 2016-11-15.
 * 异常上报
 */

public class UploadExceptionActivity extends BasicActivity implements View.OnClickListener {


//    @ViewInject(id = R.id.tab_bottom)
//    private RadioGroup tabBottom;

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @ViewInject(id = R.id.picImg)
    private ImageView picImg;
//    private TextView picImg;

    @ViewInject(id = R.id.save_btn)
    private Button save_btn;

    @ViewInject(id = R.id.meter_addr)
    private EditText meter_addr;

    @ViewInject(id = R.id.exceptiontitle)
    private EditText exceptiontitle;


    @ViewInject(id = R.id.exceptionreason)
    private EditText exceptionreason;

    @ViewInject(id = R.id.raw_value)
    private EditText raw_value;

    @ViewInject(id = R.id.photoname)
    private EditText photoname;





    private BaseFragment curentFragment;

    private Preference preference;

    private DialogLoading loading;

    private HttpServiceUtil.CallBack dataCallback;

    private UploadExceptionActivity activity;

    private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框

    private Uri photoUri;

    private String mPictureFile;

    private String filePath;

    /** 获取到的图片路径 */
    private String picPath = "";
    private static ProgressDialog pd;
    private String resultStr = "";	// 服务端返回结果集
    private String imgUrl = "";
    public String mImagePath;
    private Uri imageFileUri;

    private String timestamp;
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadexception_bak);
        activity = (UploadExceptionActivity) context;
        loading = new DialogLoading(activity);
        preference = Preference.instance(context);
        initActivity();
        initCallBack();
    }

    private void initActivity() {
//        tabBottom.setOnCheckedChangeListener(this);
        backBtn.setOnClickListener(this);
        picImg.setOnClickListener(this);
        save_btn.setOnClickListener(this);

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.picImg:// 添加图片点击事件
                // 从页面底部弹出一个窗体，选择拍照还是从相册选择已有图片
                menuWindow = new SelectPicPopupWindow(activity, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.uploadLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.save_btn:
                if (meter_addr.getText().toString().equals("")){
                    Toast.makeText(context, "请输入要上报的表地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exceptiontitle.getText().toString().equals("")){
                    Toast.makeText(context, "请输入要上报的标题", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (photoname.getText().toString().equals("")){
                    Toast.makeText(context, "请输入联系的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exceptionreason.getText().toString().equals("")){
                    Toast.makeText(context, "请输入要上报的内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (raw_value.getText().toString().equals("")){
                    Toast.makeText(context, "请输入当前故障异常表表地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                UploadException();
//                MyThread myThread = new MyThread();
//                new Thread(myThread).start();
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            menuWindow.dismiss();

            switch (v.getId()) {
                case R.id.takePhotoBtn:// 拍照
                    takePhoto();
                    break;
                case R.id.pickPhotoBtn:// 相册选择图片
                    pickPhoto();
                    break;
                case R.id.cancelBtn:// 取消
                    break;
                default:
                    break;
            }
        }
    };

    public void replaceFragment(String tag, BaseFragment tempFragment,
                                boolean isAdd) {
        curentFragment = tempFragment;
        FragmentTransaction tran = getSupportFragmentManager()
                .beginTransaction();
        tran.replace(R.id.content_fragment, tempFragment, tag);
        if (!isAdd) {
            tran.addToBackStack(tag);
        }
        tran.commitAllowingStateLoss();
    }

    //拉闸
    private void UploadException() {
        // 提交到服务器
        try {
//异常上报人

//异常表号
            String meterAddr = meter_addr.getText().toString();
//异常标题
            String exceptionTitle =exceptiontitle.getText().toString();
//异常原因
            String exceptionReason = exceptionreason.getText().toString();

            String eventId ="2000";

            String phone =photoname.getText().toString();

            String photoName = timestamp;

            String rawValue =raw_value.getText().toString();

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", meterAddr);
                jsobj.put("eventId",eventId);
                jsobj.put("exceptionReason", exceptionReason);
                jsobj.put("photoName",photoName);
                jsobj.put("phone",phone);
                jsobj.put("userId",preference.getString(Preference.CACHE_USER));
                jsobj.put("exceptionTime", DateUtil.getNowTime());
                jsobj.put("rawValue", rawValue);
            } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("正在异常上报");
            SystemAPI.uploadexception(map, picPath ,activity , dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("异常上报完成");
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.toString();
                        if (jsonObject.get("strBackFlag").equals("1")) {
                            Toast.makeText(context, "异常上报成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "异常上报失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        };
    }

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
//            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DCIM"+"/Camera/";

            String imageFilePath = getPhotoPath();
//            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            timestamp = formatter.format(new Date())+".jpg";
            File imageFile = new File(imageFilePath,timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
//            photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri.getPath());
//            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);

//            ContentValues value = new ContentValues();
//            value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//            photoUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri.getPath());
//            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    /**
     * 上传图片
     */
    class MyThread implements Runnable {
        public void run() {
            // 提交到服务器
            try {
//异常表号
                String meterAddr = "000000001235";
//异常标题
                String exceptionTitle ="气水表异常";
//异常原因
                String exceptionReason = "气表异常计数";
                String eventId ="1111";
                String photoName ="1.jpg";
                String rawValue ="23.1";
                JSONObject jsobj = new JSONObject();
                try {
                    jsobj.put("meterAddr", meterAddr);
                    jsobj.put("eventId",eventId);
                    jsobj.put("exceptionReason", exceptionReason);
                    jsobj.put("photoName",photoName);
                    jsobj.put("userId",preference.getString(Preference.CACHE_USER));
                    jsobj.put("exceptionTime", DateUtil.getNowTime());
                } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
                }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
                Map<String, Object> map = new HashMap<String, Object>();
                //"{\"name\":\"admin\",\"password\":admin\"}"
                map.put("ngMeter", jsobj.toString());
//                loading.show();
                setDialogLabel("正在异常上报");
                SystemAPI.uploadexception(map, picPath ,activity , dataCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 点击取消按钮
        if(resultCode == RESULT_CANCELED){
            return;
        }
        // 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            imageFileUri = data.getData();
            if (imageFileUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }

//        String[] pojo = { MediaStore.MediaColumns.DATA };
        // The method managedQuery() from the type Activity is deprecated
        //Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
//        Cursor cursor = getContentResolver().query(imageFileUri, pojo, null, null, null);

//        Cursor cursor = context.getContentResolver().query(photoUri, pojo, null, null, null);

        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageFileUri, pojo, null, null,null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        } else {
            picPath = mImagePath;
        }

        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (	picPath.endsWith(".png") ||
                picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") ||
                picPath.endsWith(".JPG"))) {

            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
            picImg.setImageBitmap(bm);

//            pd = ProgressDialog.show(context, null, "正在上传图片，请稍候...");
//            new Thread(uploadImageRunnable).start();
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 解决小米等定制ROM手机返回的绝对路径错误的问题
     * @param context
     * @param intent
     * @return uri 拼出来的URI
     */
    public static Uri getUri(Context context , Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.i("urishi", uri.toString());
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 获得照片路径
     *
     * @return
     */
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("filePath", filePath);
//        Log.d(TAG, "onSaveInstanceState");
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if (isEmpty(filePath)) {
//            filePath = savedInstanceState.getString("filePath");
//        }
////        Log.d(TAG, "onRestoreInstanceState");
//    }


    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     */
    public static String getImageAbsolutePath19(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }

        // MediaStore (and general)
        if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
