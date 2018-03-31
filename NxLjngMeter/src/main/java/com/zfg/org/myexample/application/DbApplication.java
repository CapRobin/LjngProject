package com.zfg.org.myexample.application;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zfg.org.myexample.ble.bean.MService;
import com.zfg.org.myexample.db.dao.DaoMaster;
import com.zfg.org.myexample.db.dao.DaoMaster.OpenHelper;
import com.zfg.org.myexample.db.dao.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class DbApplication extends Application {
	private static DbApplication mInstance;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	private static SQLiteDatabase sqLiteDatabase; 
	private static String DB_NAME = "com.zfg.org.myexample";

//
	private final List<MService> services = new ArrayList<>();
//	所有特征
	private final List<BluetoothGattCharacteristic> characteristics = new ArrayList<>();
//	制定特征
	private BluetoothGattCharacteristic characteristic;


	@Override
	public void onCreate() {
		super.onCreate();
		if (mInstance == null)
			mInstance = this;
	}

	/**
	 * 取得DaoMaster
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	/**
	 * 取得Session
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
	
	public static SQLiteDatabase getSQLiteDatabase(Context context){
	    if (sqLiteDatabase == null) {
            OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DB_NAME, null);
            sqLiteDatabase = helper.getWritableDatabase();
        }
	    return sqLiteDatabase;
	}


	public List<MService> getServices() {
		return services;
	}

	public void setServices(List<MService> services) {
		this.services.clear();
		this.services.addAll(services);
	}


	public List<BluetoothGattCharacteristic> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(List<BluetoothGattCharacteristic> characteristics) {
		this.characteristics.clear();
		this.characteristics.addAll(characteristics);
	}


	public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
		this.characteristic = characteristic;
	}

	public BluetoothGattCharacteristic getCharacteristic() {
		return characteristic;
	}
}
