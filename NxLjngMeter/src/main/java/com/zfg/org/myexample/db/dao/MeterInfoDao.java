package com.zfg.org.myexample.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by Administrator on 2016-10-22.
 * 表信息
 */

public class MeterInfoDao extends AbstractDao<MeterInfo, Long> {

    public static final String TABLENAME = "meterinfo";

    /**
     * Properties of entity Medicine.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property METER_TYPE = new Property(1, String.class, "meter_type", false, "METER_TYPE");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Comm_Address = new Property(3, String.class, "comm_address", false, "COMM_ADDRESS");
        public final static Property Customer_name = new Property(4, String.class, "customer_name", false, "CUSTOMER_NAME");
        public final static Property Terminal_address = new Property(5, String.class, "terminal_address", false, "TERMINAL_ADDRESS");
        public final static Property Phone1 = new Property(6, String.class, "phone1", false, "PHONE1");
        public final static Property Areaname = new Property(7, String.class, "areaname", false, "AREANAME");
        //        public final static Property Service_mid = new Property(8, String.class, "service_mid", false, "SERVICE_MID");
//        public final static Property Serverid = new Property(9, String.class, "serverid", false, "SERVERID");
//        public final static Property Status = new Property(10,short.class, "status", false, "STATUS");
        public final static Property Update_time = new Property(8, String.class, "update_time", false, "UPDATE_TIME");

    }

    ;

    private DaoSession daoSession;


    public MeterInfoDao(DaoConfig config) {
        super(config);
    }

    public MeterInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'meterinfo' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'METER_TYPE' TEXT NOT NULL ," + // 1: type
                "'NAME' TEXT NOT NULL ," + // 2: name
                "'COMM_ADDRESS' TEXT NOT NULL ," + // 3:
                "'CUSTOMER_NAME' TEXT NOT NULL ," + // 4
                "'TERMINAL_ADDRESS' TEXT NOT NULL ," + // 5
                "'PHONE1' TEXT NOT NULL ," + // 6
                "'AREANAME' TEXT NOT NULL ," + // 7
//                "'SERVICE_mid' TEXT," +
//                "'SERVERID' TEXT," +
//                "'STATUS'INTEGER," +
                "'UPDATE_TIME' INTEGER);");
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'meterinfo'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, MeterInfo entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getMetertype());
        stmt.bindString(3, entity.getName());
        stmt.bindString(4, entity.getComm_address());
        stmt.bindString(5, entity.getCustomer_name());
        stmt.bindString(6, entity.getTerminal_address());
        stmt.bindString(7, entity.getPhone1());
        stmt.bindString(8, entity.getAreaname());
//        stmt.bindString(9, entity.getService_mid());
//        stmt.bindString(10, entity.getServerid());
//        stmt.bindLong(11, entity.getStatus());
        stmt.bindLong(9, entity.getUpdate_time());
    }

    @Override
    protected Long updateKeyAfterInsert(MeterInfo entity, long rowId) {
        return null;
    }

    @Override
    protected void attachEntity(MeterInfo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public MeterInfo readEntity(Cursor cursor, int offset) {
        MeterInfo entity = new MeterInfo(
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1),
                cursor.getString(offset + 2),
                cursor.getString(offset + 3),
                cursor.getString(offset + 4),
                cursor.getString(offset + 5),
                cursor.getString(offset + 6),
                cursor.getString(offset + 7),
//                cursor.getString(offset + 8),
//                cursor.getString(offset + 9),
//                cursor.getLong(offset + 10),
                cursor.getLong(offset + 8)
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, MeterInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMetertype(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setComm_address(cursor.getString(offset + 3));
        entity.setCustomer_name(cursor.getString(offset + 4));
        entity.setTerminal_address(cursor.getString(offset + 5));
        entity.setPhone1(cursor.getString(offset + 6));
        entity.setAreaname(cursor.getString(offset + 7));
//        entity.setService_mid(cursor.getString(offset + 8));
//        entity.setServerid(cursor.getString(offset + 9));
//        entity.setStatus(cursor.getLong(offset + 10));
        entity.setUpdate_time(cursor.getLong(offset + 8));

    }


    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(MeterInfo entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
