package com.zfg.org.myexample.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.zfg.org.myexample.R;
import com.zfg.org.myexample.SystemAPI;
import com.zfg.org.myexample.adapter.ReadDataAdapter;
import com.zfg.org.myexample.model.ReadDataItemModel;
import com.zfg.org.myexample.utils.HttpServiceUtil;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBaseActivity extends BasicActivity {

    protected String checkType;
    protected int dataType = 0;
    protected int varType = 0;
    private String searchTime;
    protected DialogLoading loading;
    protected HttpServiceUtil.CallBack dataCallback;
    protected List<ReadDataItemModel> listdata;
    protected ReadDataAdapter listadapter;
    private ListView itemlist;
    protected ElectricityActivity mElectricityContent;

    public SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base);


        listdata = new ArrayList<ReadDataItemModel>();
        listadapter = new ReadDataAdapter(context,listdata);
//        getDataList_db.setAdapter(listadapter);

        initCallBack();
    }

//    private void setViewData(){
//        listadapter = new ReadDataAdapter(context,listdata);
////      列表适配器绑定
//        itemlist.setAdapter(listadapter);
//        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                if (!scaning) {
////                    loading.show();
////                    connectDevice(data.get(position).getDevice());
////                }
//            }
//        });

//        listadapter.notifyDataSetChanged();
//    }

    /**
     * Describe：抄表数据加载
     * Params:
     * Date：2018-03-31 09:36:42
     */
    
    public void loadData(String meteraddr,String tempJson) {
        searchTime =  df.format(new Date());
        try {
            JSONObject jsobj = new JSONObject();
            //临时使用
            meteraddr = "201700000001";

            jsobj.put("meterAddr", meteraddr);
            jsobj.put("dataType", dataType);
            jsobj.put("varType", varType);
            jsobj.put("searchTime",searchTime);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ngMeter", jsobj.toString());
            map.put("tempJson", tempJson);
            loading = new DialogLoading(this);
            loading.show();
            setDialogLabel("开始抄表请等待...");
            /**
             * 开始查询数据
             */

            initCallBack();
            SystemAPI.meter_realTimeReading(map, dataCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCallBack() {
        listdata.clear();
        dataCallback = new HttpServiceUtil.CallBack() {
            @Override
            public void callback(String json) {
                setDialogLabel("抄表完成");
                loading.dismiss();
                // 解析json
                if (json.length() > 3) {
                    try {

                        JSONObject jsonObject = new JSONObject(json);
                        String jStatus = jsonObject.getString("strBackFlag");
                        if (jStatus.equals("1")) {
                            //解析简单数组
                            JSONArray pages = jsonObject.getJSONArray("dataList");
                            for (int i = 0; i < pages.length(); i++) {
                                ReadDataItemModel dto = new ReadDataItemModel();
                                dto.of(pages.getJSONArray(i));
                                listdata.add(dto);
                            }
                            listadapter.notifyDataSetChanged();
                            mElectricityContent.setViewData();
                        } else {
                            Toast.makeText(context,"数据返回错误，请重新操作",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        };
    }
    
    /**
     * Describe：设置Dialog提示信息
     * Params:
     * Date：2018-03-31 11:01:28
     */
    
    protected void setDialogLabel(String label) {
        if (loading == null) {
            loading = new DialogLoading(context);
        }
        loading.setDialogLabel(label);
    }

    public String tempJson(String fileName){
//		String fileName = "user.java"; // 文件名字
		String getString = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			getString = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getString;
    }

}
