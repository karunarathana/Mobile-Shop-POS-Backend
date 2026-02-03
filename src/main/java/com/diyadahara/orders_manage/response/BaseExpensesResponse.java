package com.diyadahara.orders_manage.response;

import com.diyadahara.orders_manage.model.ExpensesModel;

import java.util.List;

public class BaseExpensesResponse {
    private String status;
    private String msg;
    private List<ExpensesModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ExpensesModel> getData() {
        return data;
    }

    public void setData(List<ExpensesModel> data) {
        this.data = data;
    }
}
