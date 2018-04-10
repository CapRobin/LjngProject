//package com.zfg.org.myexample;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//import com.zfg.org.myexample.activity.BasicActivity;
//import com.zfg.org.myexample.utils.Bimp;
//import com.zfg.org.myexample.utils.ImageItem;
//import com.zfg.org.myexample.utils.PublicWay;
//import com.zfg.org.myexample.utils.Res;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import rx.Observable;
////import rx.functions.Func1;
//
///**
// * Created by Administrator on 2016-11-15.
// * 异常上报
// */
//
//public class UploadExceptionActivity extends BasicActivity implements View.OnClickListener {
//
//    private GridView gv_add_picture;
//    private MyGVAdater myGVAdater;
//    List<Integer> datas = new ArrayList<Integer>();
//    private View parentView;
//    public static Bitmap bimap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Res.init(this);
//        bimap = BitmapFactory.decodeResource(getResources(),R.drawable.btn_jpg_add);
//        PublicWay.activityList.add(this);
//        parentView = getLayoutInflater().inflate(R.layout.activity_uploadexception_jd, null);
//        setContentView(parentView);
//
//        initUI();
//    }
//
//    private void initUI() {
//        findViewById(R.id.img_back).setOnClickListener(this);
//        findViewById(R.id.setting_btn_confirm).setOnClickListener(this);
//        gv_add_picture = (GridView) findViewById(R.id.gv_add_picture);
//        gv_add_picture.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        myGVAdater = new MyGVAdater(this);
//        myGVAdater.update();
//        gv_add_picture.setAdapter(myGVAdater);
//
//        gv_add_picture.setOnItemClickListener(new OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                if (position == Bimp.tempSelectBitmap.size()) {
//                    showMyDialog();
//                } else {
//                    Intent intent = new Intent(UploadExceptionActivity.this,
//                            GalleryActivity.class);
//                    intent.putExtra("position", "3");
//                    intent.putExtra("ID", position);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//
//    public void photo() {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);
//    }
//
//    private Uri tempUri;
//    private void showMyDialog() {
//        new ActionSheetDialog(this)
//                .builder()
//                .setCancelable(true)
//                .setCanceledOnTouchOutside(true)
//                .addSheetItem("拍照", SheetItemColor.Blue,
//                        new OnSheetItemClickListener() {
//                            @Override
//                            public void onClick(int which) {
//                                photo();
//                            }
//                        })
//                .addSheetItem("相册", SheetItemColor.Blue,
//                        new OnSheetItemClickListener() {
//                            @Override
//                            public void onClick(int which) {
//                                Intent intent = new Intent(HelprActivity.this,
//                                        AlbumActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
//                            }
//                        }).show();
//    }
//
//    @SuppressLint("HandlerLeak")
//    class MyGVAdater extends BaseAdapter {
//        private LayoutInflater inflater;
//        private int selectedPosition = -1;
//        private boolean shape;
//
//        public boolean isShape() {
//            return shape;
//        }
//
//        public void setShape(boolean shape) {
//            this.shape = shape;
//        }
//
//        public MyGVAdater(Context context) {
//            inflater = LayoutInflater.from(context);
//        }
//
//        public void update() {
//            loading();
//        }
//
//        public int getCount() {
//            if(Bimp.tempSelectBitmap.size() == 9){
//                return 9;
//            }
//            return (Bimp.tempSelectBitmap.size() + 1);
//        }
//
//        public Object getItem(int arg0) {
//            return null;
//        }
//
//        public long getItemId(int arg0) {
//            return 0;
//        }
//
//        public void setSelectedPosition(int position) {
//            selectedPosition = position;
//        }
//
//        public int getSelectedPosition() {
//            return selectedPosition;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                convertView = getLayoutInflater().inflate(R.layout.list_item_gridview_help_addpicture,
//                        parent, false);
//                holder = new ViewHolder();
//                holder.image = (ImageView) convertView
//                        .findViewById(R.id.img_add_picture);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            if (position ==Bimp.tempSelectBitmap.size()) {
//                holder.image.setImageBitmap(BitmapFactory.decodeResource(
//                        getResources(), R.drawable.btn_jpg_add));
//                if (position == 9) {
//                    holder.image.setVisibility(View.GONE);
//                }
//            } else {
//                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
//            }
//
//            return convertView;
//        }
//
//        public class ViewHolder {
//            public ImageView image;
//        }
//
//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 1:
//                        myGVAdater.notifyDataSetChanged();
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//        public void loading() {
//            new Thread(new Runnable() {
//                public void run() {
//                    while (true) {
//                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                            break;
//                        } else {
//                            Bimp.max += 1;
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                        }
//                    }
//                }
//            }).start();
//        }
//    }
//
//    public String getString(String s) {
//        String path = null;
//        if (s == null)
//            return "";
//        for (int i = s.length() - 1; i > 0; i++) {
//            s.charAt(i);
//        }
//        return path;
//    }
//
//    protected void onRestart() {
//        myGVAdater.update();
//        super.onRestart();
//    }
//
//    private static final int TAKE_PICTURE = 0x000001;
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case TAKE_PICTURE:
//                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
//
//                    String fileName = String.valueOf(System.currentTimeMillis());
//                    Bitmap bm = (Bitmap) data.getExtras().get("data");
//                    FileUtils.saveBitmap(bm, fileName);
//
//                    ImageItem takePhoto = new ImageItem();
//                    takePhoto.setBitmap(bm);
//                    Bimp.tempSelectBitmap.add(takePhoto);
//                }
//                break;
//        }
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (Bimp.tempSelectBitmap.size() > 0) {
//                Bimp.tempSelectBitmap.clear();
//                Bimp.max = 0;
//            }
//            finish();
//        }
//        return true;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_back:
//                if (Bimp.tempSelectBitmap.size() > 0) {
//                    Bimp.tempSelectBitmap.clear();
//                    Bimp.max = 0;
//                }
//                finish();
//                break;
//            case R.id.setting_btn_confirm:
//                //TODO 确定按钮
//                break;
//            default:
//                break;
//        }
//    }
//}
