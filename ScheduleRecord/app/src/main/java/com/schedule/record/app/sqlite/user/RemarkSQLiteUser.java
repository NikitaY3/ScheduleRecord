package com.schedule.record.app.sqlite.user;

public class RemarkSQLiteUser {
    private String nameId,remarkName;

    public RemarkSQLiteUser(String nameId, String remarkName) {
        this.nameId = nameId;
        this.remarkName = remarkName;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}
