package com.easysolutions.dod;

public class FileUpload {
    String token;
    String url;

    public FileUpload(String token, String url) {
        this.token = token;
        this.url = url;
    }
    public FileUpload() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
