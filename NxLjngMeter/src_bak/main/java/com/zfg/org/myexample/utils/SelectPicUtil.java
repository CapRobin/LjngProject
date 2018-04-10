package com.zfg.org.myexample.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
//import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 返回图片路径 由于图片来源有几种，要进行处理。 1、直接选择一个文件，返回的URI为File协议 如:file:///mnt/sdcard/**..*a/a.jpg
 * 
 * 2、相册或者是第三方资源获取时会返回相应的URI 如：content://media/external/images/media/65360
 * 
 * 3、Android4.4则会调用Document Provider选择图片,通过Document Provider选择后返回的URI如下：
 * 图片content://com.android.providers.media.documents/document/image:237068
 * 下载content://com.android.providers.downloads.documents/document/933 云端content://com.google.android.apps.docs.storage/document/acc=1;doc=5
 * 图库content://media/external/images/media/224923 相册content://media/external/images/media/229333
 * 
 * 
 * @author Chanjc@ifenguo.com
 * @createDate 2014年8月4日
 * 
 */
public class SelectPicUtil {

    public static String STR_FILE = "file";
    public static String STR_CONTENT = "content";
    public static String STR_MEDIA = "media";
    public static String filePath;
    public static String scheme;
    public static String authority;
    

    /**
     * 根据uri得到对应资源的文件路径
     * @param uri 资源的uri
     * @param context
     * @return 文件路径
     */
    public static String getPicPath(Uri uri, Context context) {
        scheme = uri.getScheme();
        authority = uri.getAuthority();
        final boolean isContent = STR_CONTENT.equalsIgnoreCase(scheme);

        // 如果在文件管理器中选择图片
        if (STR_FILE.equalsIgnoreCase(scheme)) {

            filePath = uri.getPath();

        //在相册里面选择图片
        } else if (isContent && STR_MEDIA.equalsIgnoreCase(authority)) {

            filePath = getDataColumn(context,uri,null,null);

        //Android4.4 在文档中的相册选择图片
        } else if (isContent && isMediaDocument(uri)) {
            
            final String docId = null;  
            final String[] split = docId.split(":"); 
            
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {split[1]}; 
            
            filePath = getDataColumn(context, contentUri, selection, selectionArgs);
            
        //Android4.4 在文档中的下载文件夹选择图片
        } else if (isContent && isDownloadsDocument(uri)) {
            
            final String id = null;
            final Uri contentUri = ContentUris.withAppendedId(  
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  
            
            filePath = getDataColumn(context, contentUri, null, null);
        }
        
        return filePath;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
            String[] selectionArgs) {

        Cursor cursor = null;
        // 定义索引字段
        final String column = MediaStore.Images.Media.DATA;  
        String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if(cursor !=null && cursor.moveToFirst()){
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
            
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri 检查的uri
     * @return Uri的authority是不是MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri 检查的uri
     * @return Uri的authority是不是ExternalStorageProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    
    /**
     * 判断sd卡是否可读
     * @return 可读返回true
     */
    public static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
