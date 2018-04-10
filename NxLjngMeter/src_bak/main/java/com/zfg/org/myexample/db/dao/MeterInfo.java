package com.zfg.org.myexample.db.dao;

/**
 * Created by Administrator on 2016-10-22.
 */

public class MeterInfo {

    //  主键
    private Long id;
    //  表类型
    private String metertype;
    //名称暂时没有用着，备用
    private String name;
    //更新时间
    private long update_time;
    //表地址
    private String comm_address;
    //  户名
    private String customer_name;
    //手机号
    private String phone1;
    //终端地址
    private String terminal_address;

    private String areaname;
//    private String service_mid;
//    private String serverid;
//    private long status;

    public MeterInfo(Long id, String metertype, String name, String comm_address, String customer_name, String phone1, String terminal_address, String areaname, /*String service_mid, String serverid, long status,*/ long update_time) {
        this.id = id;
        this.metertype = metertype;
        this.name = name;
        this.comm_address = comm_address;
        this.customer_name = customer_name;
        this.phone1 = phone1;
        this.terminal_address = terminal_address;
        this.areaname = areaname;
//        this.service_mid = service_mid;
//        this.serverid = serverid;
//        this.status = status;
        this.update_time = update_time;
    }



    public MeterInfo(){

    }

    /**
     * Used to resolve relations
     */
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    private transient MeterInfoDao myDao;



    /**
     * called by internal mechanisms, do not call yourself.
     */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMeterinfoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetertype() {
        return metertype;
    }

    public void setMetertype(String metertype) {
        this.metertype = metertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getTerminal_address() {
        return terminal_address;
    }

    public void setTerminal_address(String terminal_address) {
        this.terminal_address = terminal_address;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

//    public String getService_mid() {
//        return service_mid;
//    }
//
//    public void setService_mid(String service_mid) {
//        this.service_mid = service_mid;
//    }
//
//    public String getServerid() {
//        return serverid;
//    }
//
//    public void setServerid(String serverid) {
//        this.serverid = serverid;
//    }
//
//    public long getStatus() {
//        return status;
//    }
//
//    public void setStatus(long status) {
//        this.status = status;
//    }
}
