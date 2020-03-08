package com.easysolutions.dod.complaints;

public class ComplaiintsViewModal {
    String complaintBy,complaint,orderno,status,phNo,complaintID,name;



    public String getPhNo() {
        return phNo;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public ComplaiintsViewModal() {
    }

    public String getComplaintBy() {
        return complaintBy;
    }

    public void setComplaintBy(String complaintBy) {
        this.complaintBy = complaintBy;
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
