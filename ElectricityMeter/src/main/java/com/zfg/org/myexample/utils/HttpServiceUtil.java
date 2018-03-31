package com.zfg.org.myexample.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

//import com.zfg.org.myexample.utils.okhttp.OkHttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.protocol.HTTP;

/**
 * Http服务工具
 *
 * @author longbh
 */
@SuppressWarnings("rawtypes")
public class HttpServiceUtil {

    private static String TAG = HttpServiceUtil.class.getSimpleName();

    private static int REQUEST_MAX_TIME = 60000;
    private static int RESPONSE_MAX_TIME = 60000;

    // 网络图片下载
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    public static final String DEFAULT_PHOTO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dian/img/";

    public static String sessionId = "";
    public static String cookie = "";

    public static String get(String url, Map<String, Object> params) {
        HttpClient client = new DefaultHttpClient();
        StringBuilder builder = new StringBuilder();
        StringBuilder paramAppend = new StringBuilder();
        paramAppend.append(url);
        if (params != null) {
            paramAppend.append("?");
            Set keySet = params.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = params.get(key) + "";
                paramAppend.append(key + "=" + value + "&");
            }
            paramAppend.deleteCharAt(paramAppend.length() - 1);
        }
        Log.e("get_url", paramAppend.toString());
        HttpGet httpget = new HttpGet(paramAppend.toString());
        if (!CheckUtil.isNull(sessionId)) {
            httpget.setHeader("Cookie", "OPPDEAN_SESSION=dean_session%" + sessionId);
        }
        HttpParams httpParams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_MAX_TIME);
        HttpConnectionParams.setSoTimeout(httpParams, RESPONSE_MAX_TIME);
        try {
            HttpResponse response = client.execute(httpget);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                builder.append(strResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("data", builder.toString());
        return builder.toString();
    }

    /**
     * 处理�?{}/{}之类的请求，使用时用参数用linkedhashmap，参数按顺序传�?
     *
     * @param url
     * @param params
     * @return
     */
    private static String oget(String url, Map<String, Object> params) {
        HttpClient client = new DefaultHttpClient();
        StringBuilder builder = new StringBuilder();
        StringBuilder paramAppend = new StringBuilder();
        paramAppend.append(url);
        if (params != null) {
            List<Object> paramList = new ArrayList<Object>(params.values());
            for (Object obj : paramList) {
                paramAppend.append("/" + obj);
            }
        }
        HttpGet httpget = new HttpGet(paramAppend.toString());
        if (!CheckUtil.isNull(sessionId)) {
            httpget.addHeader("dean_usession", sessionId);
        }
        HttpParams httpParams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_MAX_TIME);
        HttpConnectionParams.setSoTimeout(httpParams, RESPONSE_MAX_TIME);
        try {
            HttpResponse response = client.execute(httpget);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                builder.append(strResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("data", builder.toString());
        return builder.toString();
    }

    /**
     * 请求HTTP服务
     *
     * @param url    请求的URL地址
     * @param params 请求是附带的参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
        StringBuilder builder = new StringBuilder();
        HttpParams httpparams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(httpparams, REQUEST_MAX_TIME);
        HttpConnectionParams.setSoTimeout(httpparams, RESPONSE_MAX_TIME);

        List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
        String key = "";
        String value = "";
        if (params != null) {
            Set keySet = params.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = params.get(key) + "";
                Log.e("msg", "onHttpserverUtil===>>>key=" + key + ",==>value=" + value);
                if (!key.equals("mytag")) {
                    nameValue.add(new BasicNameValuePair(key, value));
                }
            }
        }
        try {
//                if (!CheckUtil.isNull(sessionId)) {
//                    httprequest.addHeader("jsparam", sessionId);
//                }
//            StringEntity se = new StringEntity(nameValue.toString());
            HttpPost httprequest = new HttpPost(url);
            httprequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httprequest.addHeader("charset", HTTP.UTF_8);
            httprequest.setEntity(new UrlEncodedFormEntity(nameValue, HTTP.UTF_8));
//            HttpPost httprequest = new HttpPost(url+"?"+key+"="+value);
//            httprequest.setParams(httpparams);
//            httprequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            Log.i("post方法", httprequest.toString());
            HttpResponse response = client.execute(httprequest);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                builder.append(strResult);
            } else {
                builder.append("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.append("");
        }
        Log.e("data", builder.toString());
        return builder.toString();
    }

    /**
     * URL 请求服务器方�?直接返回数据�?
     */
    public static String urlMethod(String url, Map<String, Object> params) {
        StringBuilder buildContent = new StringBuilder();
        InputStream inputstream = null;
        try {
            URL request = new URL(url);
            inputstream = request.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
            String tempStr = "";
            while ((tempStr = in.readLine()) != null) {
                buildContent.append(tempStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputstream != null) {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return buildContent.toString();
    }

    /**
     * 请求Http服务
     *
     * @param url      请求的URL地址
     * @param method   请求的方法?
     * @param params   请求附带的参数?
     * @param callBack 回调函数
     */
    synchronized public static void request(final String url, final String method,
                                            final Map<String, Object> params, final CallBack callBack) {
        ThreadUtil.executorService.submit(new Runnable() {
            @Override
            public void run() {
                String rs;
                if ("post".equals(method)) {
                    rs = post(url, params);
                } else if ("oget".equals(method)) {
                    rs = oget(url, params);
                } else if ("url".equals(method)) {
                    rs = urlMethod(url, params);
                } else {
                    rs = get(url, params);
                }
                String result = "".equals(rs) ? "{status:\"0\",message:\"访问出错，请检查网络连接\"}" : rs;
                final String content = result;
                ThreadUtil.handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callBack.callback(content);
                    }
                }, 500);
            }
        });
    }

    /**
     * 上传文件流到服务器
     */
    synchronized public static void subFile(final String requestUrl, final String filePath,
                                            final Map<String, Object> param, final CallBack callback) {
        ThreadUtil.executorService.submit(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                HttpClient httpclient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpParams httpparams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpparams, REQUEST_MAX_TIME);
                HttpConnectionParams.setSoTimeout(httpparams, RESPONSE_MAX_TIME);
                // 设置通信协议版本
                httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                HttpPost httppost = new HttpPost(requestUrl);
                String boundary = "-------------" + System.currentTimeMillis();
                httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

                File file = new File(filePath);
                try {
                    MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                    multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipartEntity.setCharset(Charset.defaultCharset());
                    multipartEntity.setBoundary(boundary);

                    ContentBody cbFile = new FileBody(file);
                    multipartEntity.addPart("image", cbFile);
                    List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                    if (param != null) {
                        Set keySet = param.keySet();
                        Iterator iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            String key = (String) iterator.next();
                            Log.d("msg", "key++==" + key);
                            String value = (String) param.get(key);
                            Log.d("msg", "key=" + key + ",,,value==" + value);
                            multipartEntity.addPart(key, new StringBody(value, ContentType.APPLICATION_JSON));
                        }
                    }
                    httppost.setEntity(multipartEntity.build());
//                    HttpResponse response = httpclient.execute(httppost, localContext);
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity resEntity = response.getEntity();
                    int responseStatus = response.getStatusLine().getStatusCode();
                    if (responseStatus == HttpStatus.SC_OK) {
                        String strResult = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                        builder.append(strResult);
                    } else {
                        builder.append("{status:\"0\",message:\"访问出错\"}");
                    }
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    builder.append("{status:\"0\",message:\"访问出错\"}");
                }
                final String statusFinal = builder.toString();
                Log.e("statusFinal", statusFinal);
                ThreadUtil.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.callback(statusFinal);
                    }
                });
            }
        });
    }


    /**
     * 请求HTTP服务后的回调函数接口
     *
     * @author longbh
     */
    public interface CallBack {
        public void callback(String json);
    }

    /**
     * 异步请求图片回调接口
     */
    public interface ImgCallBack {
        public void callBack(Bitmap bitmap);
    }
}