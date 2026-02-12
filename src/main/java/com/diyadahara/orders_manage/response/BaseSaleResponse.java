package com.diyadahara.orders_manage.response;

import java.util.List;

public class BaseSaleResponse {
    private String statusCode;
    private String msg;
    private List<CustomSaleResponse> saleItems;

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

    public List<CustomSaleResponse> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<CustomSaleResponse> saleItems) {
        this.saleItems = saleItems;
    }
}
