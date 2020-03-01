package com.easysolutions.dod;

public class Order_Print {
    String no_of_Pages, no_of_Prints, page_Color, pickup_Time, pickup_Point, url, file_add, extra_details, current_Time_milies, status, type;
    String time, order_no, date, cusNo, name,proNo,bill,cusVis,proVis;

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

    public Order_Print(String no_of_Pages, String no_of_Prints, String page_Color, String pickup_Time, String pickup_Point, String url, String file_add, String extra_details, String current_Time_milies, String status, String type, String time, String order_no, String date, String cusNo, String name, String proNo, String bill) {
        this.no_of_Pages = no_of_Pages;
        this.no_of_Prints = no_of_Prints;
        this.page_Color = page_Color;
        this.pickup_Time = pickup_Time;
        this.pickup_Point = pickup_Point;
        this.url = url;
        this.file_add = file_add;
        this.extra_details = extra_details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.order_no = order_no;
        this.date = date;
        this.cusNo = cusNo;
        this.name = name;
        this.proNo = proNo;
        this.bill = bill;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public Order_Print(String no_of_Pages, String no_of_Prints, String page_Color, String pickup_Time, String pickup_Point, String url, String file_add, String extra_details, String current_Time_milies, String status, String type, String time, String order_no, String date, String cusNo, String name, String proNo) {
        this.no_of_Pages = no_of_Pages;
        this.no_of_Prints = no_of_Prints;
        this.page_Color = page_Color;
        this.pickup_Time = pickup_Time;
        this.pickup_Point = pickup_Point;
        this.url = url;
        this.file_add = file_add;
        this.extra_details = extra_details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.order_no = order_no;
        this.date = date;
        this.cusNo = cusNo;
        this.name = name;
        this.proNo = proNo;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public Order_Print(String no_of_Pages, String no_of_Prints, String page_Color, String pickup_Time, String pickup_Point, String url, String file_add, String extra_details, String current_Time_milies, String status, String type, String time, String order_no, String date, String cusNo, String name) {
        this.no_of_Pages = no_of_Pages;
        this.no_of_Prints = no_of_Prints;
        this.page_Color = page_Color;
        this.pickup_Time = pickup_Time;
        this.pickup_Point = pickup_Point;
        this.url = url;
        this.file_add = file_add;
        this.extra_details = extra_details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.order_no = order_no;
        this.date = date;
        this.cusNo = cusNo;
        this.name = name;
    }

    public Order_Print(String no_of_Pages, String no_of_Prints, String page_Color, String pickup_Time,
                       String pickup_Point, String url, String file_add,
                       String extra_details, String current_Time_milies, String status, String type, String time,
                       String order_no, String date) {
        this.no_of_Pages = no_of_Pages;
        this.no_of_Prints = no_of_Prints;
        this.page_Color = page_Color;
        this.pickup_Time = pickup_Time;
        this.pickup_Point = pickup_Point;
        this.url = url;
        this.file_add = file_add;
        this.extra_details = extra_details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.order_no = order_no;
        this.date = date;
    }

    public Order_Print() {
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

    public String getCurrent_Time_milies() {
        return current_Time_milies;
    }

    public void setCurrent_Time_milies(String current_Time_milies) {
        this.current_Time_milies = current_Time_milies;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getNo_of_Pages() {
        return no_of_Pages;
    }

    public void setNo_of_Pages(String no_of_Pages) {
        this.no_of_Pages = no_of_Pages;
    }

    public String getNo_of_Prints() {
        return no_of_Prints;
    }

    public void setNo_of_Prints(String no_of_Prints) {
        this.no_of_Prints = no_of_Prints;
    }

    public String getPage_Color() {
        return page_Color;
    }

    public void setPage_Color(String page_Color) {
        this.page_Color = page_Color;
    }

    public String getPickup_Time() {
        return pickup_Time;
    }

    public void setPickup_Time(String pickup_Time) {
        this.pickup_Time = pickup_Time;
    }

    public String getPickup_Point() {
        return pickup_Point;
    }

    public void setPickup_Point(String pickup_Point) {
        this.pickup_Point = pickup_Point;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile_add() {
        return file_add;
    }

    public void setFile_add(String file_add) {
        this.file_add = file_add;
    }

    public String getExtra_details() {
        return extra_details;
    }

    public void setExtra_details(String extra_details) {
        this.extra_details = extra_details;
    }

    public boolean validateData() {
        if (pickup_Time != null && pickup_Time.equalsIgnoreCase("pickup time") && no_of_Pages.isEmpty()
                && no_of_Prints.isEmpty() && pickup_Point.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
