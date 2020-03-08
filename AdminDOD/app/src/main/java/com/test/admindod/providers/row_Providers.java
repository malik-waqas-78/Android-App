package com.test.admindod.providers;

public class row_Providers {
    String name,no;
    String password;
    int viewType;

    public row_Providers(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public row_Providers() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
