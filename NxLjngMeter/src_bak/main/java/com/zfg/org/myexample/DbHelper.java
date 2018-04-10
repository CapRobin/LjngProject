package com.zfg.org.myexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017-02-28.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;
    //当第一次创建数据库的时候,调用该方法
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql1 = "create table bleOperate_table(" +
            "OperateId integer primary key autoincrement," +
            "meterNo varchar(20)," +
            "cmdName varchar(30)," +
            "cmd varchar(10)," +
            "createDate varchar(30)," +
            "createName varchar(10),"+
            "isUpLoad int"+ ")";
        String sql2 = "create table bleData_table(" +
                "id integer primary key autoincrement," +
                "OperateId int," +
                "item varchar(50)," +
                "data varchar(20)," +
                "unit varchar(20)" + ")";
        Log.i(TAG,"careate Database----------");
        db.execSQL(sql1);
        db.execSQL(sql2);
    }


    //必须要有构造函数
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,VERSION);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG,"update Database --------");
    }
}
