package com.diyadahara.orders_manage.response;


import com.diyadahara.orders_manage.model.SaleItemModel;

import java.util.List;

public class BaseSaleResponse {
    private String statusCode;
    private String msg;
    private List<SaleItemModel> saleItems;

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

    public List<SaleItemModel> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItemModel> saleItems) {
        this.saleItems = saleItems;
    }
}
