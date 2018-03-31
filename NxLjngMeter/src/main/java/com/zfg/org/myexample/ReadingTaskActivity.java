package com.zfg.org.myexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zfg.org.myexample.activity.BasicActivity;
import com.zfg.org.myexample.activity.DialogLoading;
import com.zfg.org.myexample.adapter.ReadingTaskAdapter;
import com.zfg.org.myexample.dialog.AlertDialogZfg;
import com.zfg.org.myexample.model.ReadingTaskItemModel;
import com.zfg.org.myexample.utils.HttpServiceUtil;
import com.zfg.org.myexample.utils.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-21.
 */

public class ReadingTaskActivity extends BasicActivity implements View.OnClickListener {

    @ViewInject(id = R.id.back_btn)
    private Button backBtn;

    @ViewInject(id = R.id.itemlist)
    private ListView itemlist;

    private AlertDialogZfg alert;
    private DialogLoading loading;
    private Preference preference;
    private Map<String, String> maps;
    private ReadingTaskActivity activity;
    private HttpServiceUtil.CallBack dataCallback;

    private ReadingTaskAdapter readingTaskAdapter;
    private List<ReadingTaskItemModel> ReadingTaskdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readingtask);
        loading = new DialogLoading(context);
        preference = Preference.instance(context);
        initActivity();
        initData();
        initCallBack();
        readingTaskAdapter = new ReadingTaskAdapter(context, ReadingTaskdata);
        query_readdata_his();
    }

    private void initActivity() {
        backBtn.setOnClickListener(this);
    }


    private void query_readdata_his() {
        // 提交到服务器
        try {
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("userId", preference.getString(Preference.CACHE_USER));
            } catch (JSONException ex) {
//                    Logger.getLogger(activity.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//                    String jsobj="{\"name\":\"admin\",\"password\":\"admin\"}";
            Map<String, Object> map = new HashMap<String, Object>();
            //"{\"name\":\"admin\",\"password\":admin\"}"
            map.put("ngMeter", jsobj.toString());
            loading.show();
            setDialogLabel("正在查询任务记录......");
//          发起查询请求
            SystemAPI.readingTask(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initCallBack() {
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("查询完成");
                loading.dismiss();
//                loading.hide();
                // 解析json
                if (json.length() > 3) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {
                            //解析简单数组
                            JSONArray pages = jsonObject.getJSONArray("areaRateList");
                            for (int i = 0; i < pages.length(); i++) {
                                if (pages.getJSONArray(i).length()>0) {
                                    for (int j = 0; j < pages.getJSONArray(i).length(); j++) {
                                        ReadingTaskItemModel dto = new ReadingTaskItemModel();
                                        dto.of(pages.getJSONArray(i).getJSONObject(j));
                                        ReadingTaskdata.add(dto);
                                    }
                                }
//                                } else {
//                                    ReadingTaskItemModel dto = new ReadingTaskItemModel();
//                                    dto.of(pages.getJSONObject(i));
//                                    ReadingTaskdata.add(dto);
//                                }
                            }
                            itemlist.setAdapter(readingTaskAdapter);
                            readingTaskAdapter.notifyDataSetChanged();
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

    private void initData() {
        ReadingTaskdata = new ArrayList<ReadingTaskItemModel>();
    }

    private void clearData() {
        ReadingTaskdata.clear();
        readingTaskAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }

    }
}
