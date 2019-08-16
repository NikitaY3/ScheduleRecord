package com.schedule.record.app.sqlite.user;

public class SpecialSQLiteUser {
//    authorization char(12) primary key,gnameid varchar(12),snameid varchar(12)
    private String authorization,gnameid,snameid;

    public SpecialSQLiteUser(String authorization, String gnameid, String snameid) {
        this.authorization = authorization;
        this.gnameid = gnameid;
        this.snameid = snameid;
    }
    public SpecialSQLiteUser(String gnameid, String snameid) {
        this.gnameid = gnameid;
        this.snameid = snameid;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getGnameid() {
        return gnameid;
    }

    public void setGnameid(String gnameid) {
        this.gnameid = gnameid;
    }

    public String getSnameid() {
        return snameid;
    }

    public void setSnameid(String snameid) {
        this.snameid = snameid;
    }
}
