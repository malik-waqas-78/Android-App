package com.dod.DOD_ServiceProviders.ui.complaints;

public class ComplaiintsViewModal {
    String complaintBy,proNo,cusNo,complaint,orderno,status;

    public ComplaiintsViewModal(String complaintBy, String proNo, String cusNo, String complaint, String orderno, String status) {
        this.complaintBy = complaintBy;
        this.proNo = proNo;
        this.cusNo = cusNo;
        this.complaint = complaint;
        this.orderno = orderno;
        this.status = status;

    }

    public ComplaiintsViewModal() {
    }

    public String getComplaintBy() {
        return complaintBy;
    }

    public void setComplaintBy(String complaintBy) {
        this.complaintBy = complaintBy;
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

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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


}
