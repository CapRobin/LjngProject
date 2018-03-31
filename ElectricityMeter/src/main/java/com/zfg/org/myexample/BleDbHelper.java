package com.zfg.org.myexample;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.zfg.org.myexample.db.dao.DaoSession;
import com.zfg.org.myexample.db.dao.UserInfo;
import com.zfg.org.myexample.DbHelper;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.text1;
import static com.zfg.org.myexample.R.id.grid1;


/**
 * Created by Administrator on 2017-02-28.
 */

public  class BleDbHelper {
    public static String userName;
    public boolean delete()
    {
        boolean l = false;
        try {
            DbHelper dbHelper = new DbHelper(BleActivity.activity, "ble_db", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql1 = "delete from  bleData_table";
            String sql2 = "delete from  bleOperate_table" ;
            db.execSQL(sql1);
            db.execSQL(sql2);
            l=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public  boolean insertData(String meterNo,String cmdName,String cmd,int isUpLoad,ArrayList<String> dataList)//参数:表号,命令名称,命令,数据列表
    {
        String userName=this.userName;//用户信息目前获取不了
        DbHelper dbHelper = new DbHelper(ComBleActivity.activity,"ble_db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat formatter = new  SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateStr = formatter.format(curDate);
        Boolean l=false;
        try
        {
            String sql1 = "insert into bleOperate_table(meterNo,cmdName,cmd,createDate,createName,isUpLoad) values (" +
                "'"+meterNo+"',"+
                "'"+cmdName+"',"+
                "'"+cmd+"',"+
                "'"+dateStr+"',"+
                "'"+userName+"',"+
                String.valueOf(isUpLoad)+ ")";
            db.execSQL(sql1);
            String sql2 = "select max(OperateId) id from bleOperate_table";
            Cursor cursor = db.rawQuery(sql2,null);
            cursor.moveToFirst();
            String maxId = cursor.getString(cursor.getColumnIndex("id"));
            for (int i =0;i<dataList.size();i++)
            {
                String dataStr = dataList.get(i);
                String[] StrArray = dataStr.split("\\|");
                String item = "";
                String data = "";
                String unit = "";
                if (StrArray.length>=1)
                {
                    item= StrArray[0];
                }
                if (StrArray.length>=2)
                {
                    data= StrArray[1];
                }
                if (StrArray.length>=3)
                {
                    unit= StrArray[2];
                }
                String sql3 = "insert into bleData_table (OperateId,item,data,unit) values (" +
                        maxId +","+
                        "'"+item +"'"+","+
                        "'"+data +"'"+","+
                        "'"+unit +"'"+")";
                db.execSQL(sql3);
            }
            l=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    private Cursor getCursor(String sql)
    {
        // 以只读的形式打开数据库
        DbHelper dbHelper = new DbHelper(BleActivity.activity,"ble_db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 执行SQL语句，返回一个游标
        return db.rawQuery(sql, null);
    }

    public boolean updateOperateId(int OperateId)//更新记录为已上传
    {
        boolean l = false;
        try {
            DbHelper dbHelper = new DbHelper(BleActivity.activity, "ble_db", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "update bleOperate_table set isUpLoad = 1 where OperateId = " + String.valueOf(OperateId);
            db.execSQL(sql);
            l=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public List<Map<String, Object>> getUnUpLoadOperateInfo()//获取全部未上传的操作表信息 返回一个list<Map>
    {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        try {
            // 查询单条记录
            String sql = "select operT.OperateId,operT.meterNo,operT.cmd,operT.createDate,operT.createName," +
                    "dataT.item,dataT.data,dataT.unit from bleOperate_table operT " +
                    "left join bleData_table dataT on dataT.OperateId = operT.OperateId " +
                    "where operT.isUpLoad = 0 order by operT.OperateId ,dataT.id ";
            Cursor cursor = getCursor(sql);
            int colums = cursor.getColumnCount();
            int count = cursor.getCount();//获取数据项数
            while (cursor.moveToNext()){
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_value == null)
                        cols_value = "";
                    map.put(cols_name, cols_value);
                }
                data_list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_list;
    }

    public List<Map<String, Object>> QueryBleData(int OperateId)//查询明细表
    {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
          String sql = "select item,data,unit from bleData_table where OperateId = " +
                  String.valueOf(OperateId) + " order by id";
        Cursor cursor = getCursor(sql);
        int colums = cursor.getColumnCount();
        int count = cursor.getCount();//获取数据项数

        data_list.clear();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < colums; i++) {
                String cols_name = cursor.getColumnName(i);
                String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                if (cols_value == null)
                    cols_value = "";
                map.put(cols_name, cols_value);
            }
            data_list.add(map);
        }
        return data_list;
    }

    public List<Map<String, Object>> QuerybleOperateData(String[] refStr)
    {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        SQLiteDatabase database = null;
        try {
            String sql = "select operateId,meterNo,cmdName,createDate,createName from bleOperate_table where isUpLoad = 0 order by OperateId";
//                         String sql = "select max (id) aa from stu_table";
            Cursor cursor = getCursor(sql);
            int colums = cursor.getColumnCount();
            int count = cursor.getCount();//获取数据项数
            refStr[0] = "共有"+String.valueOf(count)+"条抄表数据需要上传";
            data_list.clear();
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_value == null)
                        cols_value = "";
                    map.put(cols_name, cols_value);
                }
                data_list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    return  data_list;
    }


}
