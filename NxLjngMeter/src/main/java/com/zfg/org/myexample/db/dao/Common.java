package com.zfg.org.myexample.db.dao;

import com.zfg.org.myexample.db.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table common.
 */
public class Common {

    private Long id;
    /** Not-null value. */
    private String name;
    private String image;
    private String image_b;
    private short index;
    /** Not-null value. */
    private String group;
    private String serverid;
    private boolean isSelect = false;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CommonDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Common() {
    }

    public Common(Long id) {
        this.id = id;
    }

    public Common(Long id, String name, String image, String image_b, short index, String group, String serverid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.image_b = image_b;
        this.index = index;
        this.group = group;
        this.serverid = serverid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommonDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_b() {
        return image_b;
    }

    public void setImage_b(String image_b) {
        this.image_b = image_b;
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    /** Not-null value. */
    public String getGroup() {
        return group;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGroup(String group) {
        this.group = group;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
	public void select(){
		this.isSelect = !isSelect;
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
