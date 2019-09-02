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

    public String getGnameId() {
        return gnameId;
    }

    public void setGnameId(String gnameId) {
        this.gnameId = gnameId;
    }

    public String getSnameId() {
        return snameId;
    }

    public void setSnameId(String snameId) {
        this.snameId = snameId;
    }
}
