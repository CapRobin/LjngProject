package com.zfg.org.myexample;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.ImagePickerAdapter;
import com.zfg.org.myexample.photo.GlideImageLoader;
import com.zfg.org.myexample.photo.SelectDialog;
import com.zfg.org.myexample.utils.DateUtil;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//import rx.Observable;
//import rx.functions.Func1;

/**
 * Created by Administrator on 2016-11-15.
 * 异常上报
 */

public class UploadExceptionActivity extends BasicActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数

    private String timestamp;
    public String mImagePath;
    private Uri imageFileUri;
    private Preference preference;
    private DialogLoading loading;
    ArrayList<ImageItem> images = null;

    private UploadExceptionActivity activity;
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private HttpServiceUtil.CallBack dataCallback;

    @ViewInject(id = R.id.backHome)
    private Button backHome;
    @ViewInject(id = R.id.pageTitle)
    private TextView pageTitle;
    @ViewInject(id = R.id.settingBtn)
    private ImageView settingBtn;
    @ViewInject(id = R.id.reportMeterNum)
    private EditText reportMeterNum;
    @ViewInject(id = R.id.meterValue)
    private EditText meterValue;
    @ViewInject(id = R.id.phoneNum)
    private EditText phoneNum;
    @ViewInject(id = R.id.exceptionTitle)
    private EditText exceptionTitle;
    @ViewInject(id = R.id.exceptionReason)
    private EditText exceptionReason;
    @ViewInject(id = R.id.submitBtn)
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadexception);
        preference = Preference.instance(context);
        loading = new DialogLoading(this);
        activity = (UploadExceptionActivity) context;

        //最好放到 Application oncreate执行
        initWidget();
        initCallBack();
    }

    private void initWidget() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        pageTitle.setText("意见反馈");
        backHome.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHome:
                finish();
                break;
            case R.id.settingBtn:
                startActivity(null, UploadExceptionActivity_ys.class);
                break;
            case R.id.submitBtn:
                if (reportMeterNum.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入要上报的表地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (meterValue.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入上报表的读数", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNum.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入联系的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exceptionTitle.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入要上报的标题", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exceptionReason.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入要上报的内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                UploadException();
                break;
        }
    }


    /**
     * Describe：异常上报
     * Params:
     * Date：2018-04-10 16:11:52
     */
    private void UploadException() {
        try {
            String reportMeterNumStr = reportMeterNum.getText().toString();
            String meterValueStr = meterValue.getText().toString();
            String phoneNumStr = phoneNum.getText().toString();
            String exceptionTitleStr = exceptionTitle.getText().toString();
            String exceptionReasonStr = exceptionReason.getText().toString();

            String eventId = "2000";
            String photoName = timestamp;

            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("meterAddr", reportMeterNumStr);
                jsobj.put("rawValue", meterValueStr);
                jsobj.put("phone", phoneNumStr);
                jsobj.put("exceptionTitle", exceptionTitleStr);
                jsobj.put("exceptionReason", exceptionReasonStr);

                jsobj.put("eventId", eventId);
                jsobj.put("photoName", photoName);
                jsobj.put("userId", preference.getString(Preference.CACHE_USER));
                jsobj.put("exceptionTime", DateUtil.getNowTime());
            } catch (JSONException ex) {
                Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("正在异常上报");

            //调用接口开始上报
            SystemAPI.uploadexception(map, picPath, activity, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Describe：回调函数
     * Params:
     * Date：2018-04-10 16:16:23
     */
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

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机

                                //获取图片路径
                                String imageFilePath = getPhotoPath();
                                //设定时间格式
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                                //根据当前时间生成图片的名称
                                timestamp = formatter.format(new Date()) + ".jpg";
                                File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
                                mImagePath = imageFile.getAbsolutePath();
                                imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri

//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
//                                startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(UploadExceptionActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(UploadExceptionActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);

                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    /**
     * 获得照片路径
     *
     * @return
     */
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                //获取文件路径和文件名
                picPath = images.get(0).path.toString();
                File file1 = new File(picPath);
                timestamp = file1.getName();

                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
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

        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageFileUri, pojo, null, null, null);
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
        if (picPath != null && (picPath.endsWith(".png") ||
                picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") ||
                picPath.endsWith(".JPG"))) {

            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);

            //~~~~~~~~注释掉~~~~~~~~~~
            // 显示在图片控件上
//            picImg.setImageBitmap(bm);

//            pd = ProgressDialog.show(context, null, "正在上传图片，请稍候...");
//            new Thread(uploadImageRunnable).start();
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Describe：设置对话框Lable
     * Params:
     * Date：2018-04-10 16:19:44
     */

    private void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }
}
