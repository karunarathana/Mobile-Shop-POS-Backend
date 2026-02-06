package com.diyadahara.orders_manage.response;

public class CustomSaleItems {
    private String productName;
    private String qty;
    private String unitPrice;
    private String warrantyDays;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getWarrantyDays() {
        return warrantyDays;
    }

    public void setWarrantyDays(String warrantyDays) {
        this.warrantyDays = warrantyDays;
    }
}
