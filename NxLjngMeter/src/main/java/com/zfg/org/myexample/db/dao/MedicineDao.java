package com.zfg.org.myexample.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zfg.org.myexample.db.dao.Medicine;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table medicine.
*/
public class MedicineDao extends AbstractDao<Medicine, Long> {

    public static final String TABLENAME = "medicine";

    /**
     * Properties of entity Medicine.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, String.class, "type", false, "TYPE");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property NumType = new Property(3, int.class, "numType", false, "NUM_TYPE");
        public final static Property Stage = new Property(4, int.class, "stage", false, "STAGE");
        public final static Property Weight = new Property(5, String.class, "weight", false, "WEIGHT");
        public final static Property EatDay = new Property(6, int.class, "eatDay", false, "EAT_DAY");
        public final static Property Unit = new Property(7, String.class, "unit", false, "UNIT");
        public final static Property CreateTime = new Property(8, long.class, "createTime", false, "CREATE_TIME");
        public final static Property UpdateTime = new Property(9, long.class, "updateTime", false, "UPDATE_TIME");
        public final static Property IsOut = new Property(10, boolean.class, "isOut", false, "IS_OUT");
        public final static Property Istoast = new Property(11, boolean.class, "istoast", false, "ISTOAST");
        public final static Property RmdBreakfast = new Property(12, String.class, "rmdBreakfast", false, "RMD_BREAKFAST");
        public final static Property RmdLunch = new Property(13, String.class, "rmdLunch", false, "RMD_LUNCH");
        public final static Property Rmdsupper = new Property(14, String.class, "rmdsupper", false, "RMDSUPPER");
        public final static Property Service_mid = new Property(15, String.class, "service_mid", false, "SERVICE_MID");
        public final static Property Serverid = new Property(16, String.class, "serverid", false, "SERVERID");
        public final static Property Status = new Property(17, short.class, "status", false, "STATUS");
        public final static Property RmdSleep = new Property(18, String.class, "rmdSleep", false, "RMDSLEEP");
    };

    private DaoSession daoSession;


    public MedicineDao(DaoConfig config) {
        super(config);
    }
    
    public MedicineDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'medicine' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'TYPE' TEXT NOT NULL ," + // 1: type
                "'NAME' TEXT NOT NULL ," + // 2: name
                "'NUM_TYPE' INTEGER NOT NULL ," + // 3: numType
                "'STAGE' INTEGER NOT NULL ," + // 4: stage
                "'WEIGHT' TEXT NOT NULL ," + // 5: weight
                "'EAT_DAY' INTEGER NOT NULL ," + // 6: eatDay
                "'UNIT' TEXT NOT NULL ," + // 7: unit
                "'CREATE_TIME' INTEGER NOT NULL ," + // 8: createTime
                "'UPDATE_TIME' INTEGER NOT NULL ," + // 9: updateTime
                "'IS_OUT' INTEGER NOT NULL ," + // 10: isOut
                "'ISTOAST' INTEGER NOT NULL ," + // 11: istoast
                "'RMD_BREAKFAST' TEXT," + // 12: rmdBreakfast
                "'RMD_LUNCH' TEXT," + // 13: rmdLunch
                "'RMDSUPPER' TEXT," + // 14: rmdsupper
                "'SERVICE_MID' TEXT NOT NULL ," + // 15: service_mid
                "'SERVERID' TEXT," + // 16: serverid
                "'STATUS' INTEGER NOT NULL," +
                "'RMDSLEEP' TEXT);"); // 17: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'medicine'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Medicine entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getType());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getNumType());
        stmt.bindLong(5, entity.getStage());
        stmt.bindString(6, entity.getWeight());
        stmt.bindLong(7, entity.getEatDay());
        stmt.bindString(8, entity.getUnit());
        stmt.bindLong(9, entity.getCreateTime());
        stmt.bindLong(10, entity.getUpdateTime());
        stmt.bindLong(11, entity.getIsOut() ? 1l: 0l);
        stmt.bindLong(12, entity.getIstoast() ? 1l: 0l);
 
        String rmdBreakfast = entity.getRmdBreakfast();
        if (rmdBreakfast != null) {
            stmt.bindString(13, rmdBreakfast);
        }
 
        String rmdLunch = entity.getRmdLunch();
        if (rmdLunch != null) {
            stmt.bindString(14, rmdLunch);
        }
 
        String rmdsupper = entity.getRmdsupper();
        if (rmdsupper != null) {
            stmt.bindString(15, rmdsupper);
        }
        stmt.bindString(16, entity.getService_mid());
 
        String serverid = entity.getServerid();
        if (serverid != null) {
            stmt.bindString(17, serverid);
        }
        stmt.bindLong(18, entity.getStatus());
        
        String rmdSleep = entity.getRmdSleep();
        if (rmdSleep != null) {
            stmt.bindString(19, rmdSleep);
        }
    }

    @Override
    protected void attachEntity(Medicine entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Medicine readEntity(Cursor cursor, int offset) {
        Medicine entity = new Medicine( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // type
            cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3), // numType
            cursor.getInt(offset + 4), // stage
            cursor.getString(offset + 5), // weight
            cursor.getInt(offset + 6), // eatDay
            cursor.getString(offset + 7), // unit
            cursor.getLong(offset + 8), // createTime
            cursor.getLong(offset + 9), // updateTime
            cursor.getShort(offset + 10) != 0, // isOut
            cursor.getShort(offset + 11) != 0, // istoast
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // rmdBreakfast
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // rmdLunch
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // rmdsupper
            cursor.getString(offset + 15), // service_mid
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // serverid
            cursor.getShort(offset + 17), // status
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // rmdSleep
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Medicine entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setNumType(cursor.getInt(offset + 3));
        entity.setStage(cursor.getInt(offset + 4));
        entity.setWeight(cursor.getString(offset + 5));
        entity.setEatDay(cursor.getInt(offset + 6));
        entity.setUnit(cursor.getString(offset + 7));
        entity.setCreateTime(cursor.getLong(offset + 8));
        entity.setUpdateTime(cursor.getLong(offset + 9));
        entity.setIsOut(cursor.getShort(offset + 10) != 0);
        entity.setIstoast(cursor.getShort(offset + 11) != 0);
        entity.setRmdBreakfast(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setRmdLunch(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setRmdsupper(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setService_mid(cursor.getString(offset + 15));
        entity.setServerid(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setStatus(cursor.getShort(offset + 17));
        entity.setRmdSleep(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Medicine entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Medicine entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
