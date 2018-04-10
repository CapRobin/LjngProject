package com.zfg.org.myexample.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zfg.org.myexample.db.dao.News;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table news.
*/
public class NewsDao extends AbstractDao<News, Long> {

    public static final String TABLENAME = "news";

    /**
     * Properties of entity News.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Thumbnail = new Property(1, String.class, "thumbnail", false, "THUMBNAIL");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property Summary = new Property(4, String.class, "summary", false, "SUMMARY");
        public final static Property Day = new Property(5, long.class, "day", false, "DAY");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Sid = new Property(7, long.class, "sid", false, "SID");
    };

    private DaoSession daoSession;


    public NewsDao(DaoConfig config) {
        super(config);
    }
    
    public NewsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'news' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'THUMBNAIL' TEXT NOT NULL ," + // 1: thumbnail
                "'TITLE' TEXT NOT NULL ," + // 2: title
                "'AUTHOR' TEXT NOT NULL ," + // 3: author
                "'SUMMARY' TEXT NOT NULL ," + // 4: summary
                "'DAY' INTEGER NOT NULL ," + // 5: day
                "'CONTENT' TEXT," + // 6: content
                "'SID' INTEGER NOT NULL );"); // 7: sid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'news'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, News entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getThumbnail());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getAuthor());
        stmt.bindString(5, entity.getSummary());
        stmt.bindLong(6, entity.getDay());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindLong(8, entity.getSid());
    }

    @Override
    protected void attachEntity(News entity) {
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
    public News readEntity(Cursor cursor, int offset) {
        News entity = new News( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // thumbnail
            cursor.getString(offset + 2), // title
            cursor.getString(offset + 3), // author
            cursor.getString(offset + 4), // summary
            cursor.getLong(offset + 5), // day
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.getLong(offset + 7) // sid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, News entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setThumbnail(cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setAuthor(cursor.getString(offset + 3));
        entity.setSummary(cursor.getString(offset + 4));
        entity.setDay(cursor.getLong(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSid(cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(News entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(News entity) {
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
