package com.diyadahara.orders_manage.response;

import com.diyadahara.orders_manage.model.RepairModel;

import java.util.List;

public class BaseRepairResponse {
    private String statusCode;
    private String msg;
    private List<RepairModel> data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RepairModel> getData() {
        return data;
    }

    public void setData(List<RepairModel> data) {
        this.data = data;
    }
}
