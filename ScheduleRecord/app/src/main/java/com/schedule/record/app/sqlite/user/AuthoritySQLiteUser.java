package com.schedule.record.app.sqlite.user;

public class AuthoritySQLiteUser {

    private String authorization,gnameId,snameId;

    public AuthoritySQLiteUser(String authorization, String gnameid, String snameid) {
        this.authorization = authorization;
        this.gnameId = gnameid;
        this.snameId = snameid;
    }
    public AuthoritySQLiteUser(String gnameid, String snameid) {
        this.gnameId = gnameid;
        this.snameId = snameid;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getGnameid() {
        return gnameId;
    }

    public void setGnameid(String gnameid) {
        this.gnameId = gnameid;
    }

    public String getSnameid() {
        return snameId;
    }

    public void setSnameid(String snameid) {
        this.snameId = snameid;
    }
}
