package com.easysolutions.dod.ui.main;

public class Bill_Conveyance {
    private String fair,driver_name,orderNo,proNo,cusNo,type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bill_Conveyance(String fair, String driver_name) {
        this.fair = fair;
        this.driver_name = driver_name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public Bill_Conveyance() {
    }

    public String getFair() {
        return fair;
    }

    public void setFair(String fair) {
        this.fair = fair;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }
}
