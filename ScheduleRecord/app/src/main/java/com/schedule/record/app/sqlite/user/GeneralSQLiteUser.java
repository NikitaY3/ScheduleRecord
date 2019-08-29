package com.schedule.record.app.sqlite.user;

public class GeneralSQLiteUser {

    private String nameId,name,password,sex,birthday,head;

    public GeneralSQLiteUser(String nameid, String name, String password, String sex, String birthday, String head) {
        this.nameId = nameid;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.birthday = birthday;
        this.head = head;
    }

    public String getNameid() {
        return nameId;
    }

    public void setNameid(String nameid) {
        this.nameId = nameid;
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

    @Override
    public String toString() {
        return "GeneralSQLiteUser{" +
                "nameid='" + nameId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
