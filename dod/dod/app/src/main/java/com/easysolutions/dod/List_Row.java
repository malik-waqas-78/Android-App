package com.easysolutions.dod;

public class List_Row {
    String CusNo,ProNo,ProName,id,name,time,status,cusVis,proVis;

    public List_Row() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProVis() {
        return proVis;
    }

    public void setProVis(String proVis) {
        this.proVis = proVis;
    }

    public String getCusVis() {
        return cusVis;
    }

    public void setCusVis(String cusVis) {
        this.cusVis = cusVis;
    }

    public List_Row(String cusNo, String proNo, String proName, String id, String name, String time, String status) {
        CusNo = cusNo;
        ProNo = proNo;
        ProName = proName;
        this.id = id;
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public List_Row(String cusNo, String proNo, String proname, String name, String time, String id) {
        CusNo = cusNo;
        ProNo = proNo;
        ProName = proname;
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public String getCusNo() {
        return CusNo;
    }

    public void setCusNo(String cusNo) {
        CusNo = cusNo;
    }

    public String getProNo() {
        return ProNo;
    }

    public void setProNo(String proNo) {
        ProNo = proNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
