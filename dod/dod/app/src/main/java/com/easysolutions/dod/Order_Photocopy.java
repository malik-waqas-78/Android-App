package com.easysolutions.dod;

public class Order_Photocopy {
    String no_of_pages;
    String no_of_copiess;
    String page_sides;
    String pickup_point;
    String pickup_time;
    String extra_details;
    String current_time_milies;
    String status;
    String time;
    String date;
    String orderno;
    String cusNo;
    String name;
    String type,proNo,bill,cusVis,proVis;

    public String getCusVis() {
        return cusVis;
    }

    public void setCusVis(String cusVis) {
        this.cusVis = cusVis;
    }

    public String getProVis() {
        return proVis;
    }

    public void setProVis(String proVis) {
        this.proVis = proVis;
    }

    public Order_Photocopy(String no_of_pages, String no_of_copiess, String page_sides, String pickup_point, String pickup_time, String extra_details, String current_time_milies, String status, String time, String date, String orderno, String cusNo, String name, String type, String proNo, String bill) {
        this.no_of_pages = no_of_pages;
        this.no_of_copiess = no_of_copiess;
        this.page_sides = page_sides;
        this.pickup_point = pickup_point;
        this.pickup_time = pickup_time;
        this.extra_details = extra_details;
        this.current_time_milies = current_time_milies;
        this.status = status;
        this.time = time;
        this.date = date;
        this.orderno = orderno;
        this.cusNo = cusNo;
        this.name = name;
        this.type = type;
        this.proNo = proNo;
        this.bill = bill;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public Order_Photocopy(String no_of_pages, String no_of_copiess, String page_sides, String pickup_point, String pickup_time, String extra_details, String current_time_milies, String status, String time, String date, String orderno, String cusNo, String name, String type, String proNo) {
        this.no_of_pages = no_of_pages;
        this.no_of_copiess = no_of_copiess;
        this.page_sides = page_sides;
        this.pickup_point = pickup_point;
        this.pickup_time = pickup_time;
        this.extra_details = extra_details;
        this.current_time_milies = current_time_milies;
        this.status = status;
        this.time = time;
        this.date = date;
        this.orderno = orderno;
        this.cusNo = cusNo;
        this.name = name;
        this.type = type;
        this.proNo = proNo;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public Order_Photocopy(String no_of_pages, String no_of_copiess, String page_sides, String pickup_point, String pickup_time, String extra_details, String current_time_milies, String status, String time, String date, String orderno, String cusNo, String name, String type) {
        this.no_of_pages = no_of_pages;
        this.no_of_copiess = no_of_copiess;
        this.page_sides = page_sides;
        this.pickup_point = pickup_point;
        this.pickup_time = pickup_time;
        this.extra_details = extra_details;
        this.current_time_milies = current_time_milies;
        this.status = status;
        this.time = time;
        this.date = date;
        this.orderno = orderno;
        this.cusNo = cusNo;
        this.name = name;
        this.type = type;
    }

    public Order_Photocopy() {
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_time_milies() {
        return current_time_milies;
    }

    public void setCurrent_time_milies(String current_time_milies) {
        this.current_time_milies = current_time_milies;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo_of_pages() {
        return no_of_pages;
    }

    public void setNo_of_pages(String no_of_pages) {
        this.no_of_pages = no_of_pages;
    }

    public String getNo_of_copiess() {
        return no_of_copiess;
    }

    public void setNo_of_copiess(String no_of_copiess) {
        this.no_of_copiess = no_of_copiess;
    }

    public String getPage_sides() {
        return page_sides;
    }

    public void setPage_sides(String page_sides) {
        this.page_sides = page_sides;
    }

    public String getPickup_point() {
        return pickup_point;
    }

    public void setPickup_point(String pickup_point) {
        this.pickup_point = pickup_point;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getExtra_details() {
        return extra_details;
    }

    public void setExtra_details(String extra_details) {
        this.extra_details = extra_details;
    }


    public boolean validate_photcopyData() {
        if (pickup_time == null && no_of_copiess == null && no_of_pages == null && pickup_point == null &&
                (pickup_time.equalsIgnoreCase("PickUp Time"))
                && (no_of_pages.isEmpty()) && (no_of_copiess.isEmpty()) && (pickup_point.isEmpty())
        ) {
            return false;
        } else {
            return true;
        }
    }
}
