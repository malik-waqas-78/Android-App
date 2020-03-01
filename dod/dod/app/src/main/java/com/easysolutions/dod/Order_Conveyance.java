package com.easysolutions.dod;

public class Order_Conveyance {
    String pickup_point, drop_Point, transport_Type, seats, pickup_Time, pickup_Date, extr_Details, current_Time_milies, status, type;

    String time, date, order_no, cusNo, name,proNo,bill,cusVis,proVis;

    public String getBill() {
        return bill;
    }

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

    public Order_Conveyance(String pickup_point, String drop_Point, String transport_Type, String seats, String pickup_Time, String pickup_Date, String extr_Details, String current_Time_milies, String status, String type, String time, String date, String order_no, String cusNo, String name, String proNo, String bill) {
        this.pickup_point = pickup_point;
        this.drop_Point = drop_Point;
        this.transport_Type = transport_Type;
        this.seats = seats;
        this.pickup_Time = pickup_Time;
        this.pickup_Date = pickup_Date;
        this.extr_Details = extr_Details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.date = date;
        this.order_no = order_no;
        this.cusNo = cusNo;
        this.name = name;
        this.proNo = proNo;
        this.bill = bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public Order_Conveyance(String pickup_point, String drop_Point, String transport_Type, String seats, String pickup_Time, String pickup_Date, String extr_Details, String current_Time_milies, String status, String type, String time, String date, String order_no, String cusNo, String name, String proNo) {
        this.pickup_point = pickup_point;
        this.drop_Point = drop_Point;
        this.transport_Type = transport_Type;
        this.seats = seats;
        this.pickup_Time = pickup_Time;
        this.pickup_Date = pickup_Date;
        this.extr_Details = extr_Details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.date = date;
        this.order_no = order_no;
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

    public Order_Conveyance(String pickup_point, String drop_Point, String transport_Type, String seats, String pickup_Time, String pickup_Date, String extr_Details, String current_Time_milies, String status, String type, String time, String date, String order_no, String cusNo, String name) {
        this.pickup_point = pickup_point;
        this.drop_Point = drop_Point;
        this.transport_Type = transport_Type;
        this.seats = seats;
        this.pickup_Time = pickup_Time;
        this.pickup_Date = pickup_Date;
        this.extr_Details = extr_Details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.date = date;
        this.order_no = order_no;
        this.cusNo = cusNo;
        this.name = name;
    }

    public Order_Conveyance(String pickup_point, String drop_Point, String transport_Type, String seats,
                            String pickup_Time, String pickup_Date, String extr_Details, String current_Time_milies,
                            String status, String type, String time, String date, String order_no) {
        this.pickup_point = pickup_point;
        this.drop_Point = drop_Point;
        this.transport_Type = transport_Type;
        this.seats = seats;
        this.pickup_Time = pickup_Time;
        this.pickup_Date = pickup_Date;
        this.extr_Details = extr_Details;
        this.current_Time_milies = current_Time_milies;
        this.status = status;
        this.type = type;
        this.time = time;
        this.date = date;
        this.order_no = order_no;
    }

    public Order_Conveyance() {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public String getPickup_point() {
        return pickup_point;
    }

    public void setPickup_point(String pickup_point) {
        this.pickup_point = pickup_point;
    }

    public String getDrop_Point() {
        return drop_Point;
    }

    public void setDrop_Point(String drop_Point) {
        this.drop_Point = drop_Point;
    }

    public String getTransport_Type() {
        return transport_Type;
    }

    public void setTransport_Type(String transport_Type) {
        this.transport_Type = transport_Type;
    }

    public String getSeats() {
        return seats;
    }


    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPickup_Time() {
        return pickup_Time;
    }

    public void setPickup_Time(String pickup_Time) {
        this.pickup_Time = pickup_Time;
    }

    public String getPickup_Date() {
        return pickup_Date;
    }

    public void setPickup_Date(String pickup_Date) {
        this.pickup_Date = pickup_Date;
    }

    public String getExtr_Details() {
        return extr_Details;
    }

    public void setExtr_Details(String extr_Details) {
        this.extr_Details = extr_Details;
    }

    public boolean validate() {
        if (pickup_Date != null && pickup_Time != null && (!pickup_Date.equalsIgnoreCase("Pickup Date")) && (!pickup_point.isEmpty())
                && (!pickup_Time.equalsIgnoreCase("PickUp Time")) && (!seats.isEmpty())
                && (!drop_Point.isEmpty()) && validateSeats()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSeats() {
        int no_of_seats = Integer.parseInt(seats);
        if (transport_Type.equals("Motor Bike") && no_of_seats <= 2 && no_of_seats != 0) {
            return true;
        } else if (transport_Type.equals("Rikshaw") && no_of_seats <= 6 && no_of_seats != 0) {
            return true;
        } else if (transport_Type.equals("Carry Dba") && no_of_seats <= 9 && no_of_seats != 0) {
            return true;
        } else {
            return false;
        }
    }
}
