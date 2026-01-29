package com.diyadahara.orders_manage.response;

import com.diyadahara.orders_manage.model.ReloadModel;

import java.util.List;

public class BaseReloadResponse {
    private String msg;
    private String statusCode;
    private List<ReloadModel> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<ReloadModel> getData() {
        return data;
    }

    public void setData(List<ReloadModel> data) {
        this.data = data;
    }
}
