package com.easysolutions.dod.complaints;

public class MsgDetails {
    String msgId;
    String msgBy;
    String msgText;
    String msgTime;
    int viewType;

    public MsgDetails() {
    }

    public MsgDetails(String msgId, String msgBy, String msgText, String msgTime) {
        this.msgId = msgId;
        this.msgBy = msgBy;
        this.msgText = msgText;
        this.msgTime = msgTime;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgBy() {
        return msgBy;
    }

    public void setMsgBy(String msgBy) {
        this.msgBy = msgBy;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
