package com.dod.DOD_ServiceProviders.ui.complaints;

public class ComplaiintsViewModal {
    String complaintBy,complaint,orderno,status,complaintID,phNo,name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComplaiintsViewModal(String complaintBy, String proNo, String cusNo, String complaint, String orderno, String status) {
        this.complaintBy = complaintBy;

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

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
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
