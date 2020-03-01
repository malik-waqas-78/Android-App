package com.dod.DOD_ServiceProviders.ui.allorders;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Bill_Copying {
    private String copyingprice,procharges,totalbill,orderNo,proNo,cusNo,dodcharges,type;
    private int proown=15,dod=5;
    public Bill_Copying() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bill_Copying(String copyingprice, String procharges, String totalbill, String orderNo, String proNo, String cusNo, int proown) {
        this.copyingprice = copyingprice;
        this.procharges = procharges;
        this.totalbill = totalbill;
        this.orderNo = orderNo;
        this.proNo = proNo;
        this.cusNo = cusNo;
        this.proown = proown;
    }


    public String getDodcharges() {
        return dodcharges;
    }

    public void setDodcharges(String dodcharges) {
        this.dodcharges = dodcharges;
    }

    public int getDod() {
        return dod;
    }

    public void setDod(int dod) {
        this.dod = dod;
    }

    public String getCopyingprice() {
        return copyingprice;
    }

    public void setCopyingprice(String copyingprice) {
        this.copyingprice = copyingprice;
    }

    public String getProcharges() {
        return procharges;
    }

    public void setProcharges(String procharges) {
        this.procharges = procharges;
    }

    public String getTotalbill() {
        return totalbill;
    }

    public void setTotalbill(String totalbill) {
        this.totalbill = totalbill;
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

    public int getProown() {
        return proown;
    }

    public void setProown(int proown) {
        this.proown = proown;
    }

    public boolean validate(Context context){
        if(copyingprice.trim().isEmpty()){
            showPopup("Admin","Enter Copying price",context);
            return false;
        }
        return true;
    }

    public void calculate_Bill(){
        int  price=Integer.parseInt(copyingprice);
        float pcharges=(proown*price)/100;
        float dcharges=(dod*price)/100;
        float total=pcharges+price+dcharges;
        procharges=String.valueOf(pcharges);
        dodcharges=String.valueOf(dcharges);
        totalbill=String.valueOf(total);
    }
    private void showPopup(String otp_error, String s, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(otp_error)
                .setMessage(s)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

    public float ExpendeturesEarnings(){
        return Float.valueOf(totalbill)-Float.valueOf(dodcharges)-Float.valueOf(procharges);
    }
}
