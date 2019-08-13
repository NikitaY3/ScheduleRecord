package com.schedule.record.app.sqlite.user;

public class GeneralSQLiteUser {

//    (nameid varchar(12) primary key,name vchar(32)
// ,password vchar(32),sex char(2),birthday date,head vchar(128))
    private String nameid,name,password,sex,birthday,head;

    public GeneralSQLiteUser(String nameid, String name, String password, String sex, String birthday, String head) {
        this.nameid = nameid;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.birthday = birthday;
        this.head = head;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
