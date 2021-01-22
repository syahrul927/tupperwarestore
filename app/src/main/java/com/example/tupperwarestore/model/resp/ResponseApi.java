package com.example.tupperwarestore.model.resp;

public class ResponseApi<T>{
    private Integer status ;
    private String info;
    private T data;

    public ResponseApi(Integer status, String info, T data, String accessToken) {
        this.status = status;
        this.info = info;
        this.data = data;
        this.accessToken = accessToken;
    }

    private String accessToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseApi() {
    }
}
