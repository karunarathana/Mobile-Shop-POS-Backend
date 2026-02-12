package com.diyadahara.orders_manage.response;

import java.util.List;

public class CustomSaleResponse {
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String totalPayment;
    private String payMoney;
    private String returnMoney;
    private List<CustomSaleItems> customSaleItems;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public List<CustomSaleItems> getCustomSaleItems() {
        return customSaleItems;
    }

    public void setCustomSaleItems(List<CustomSaleItems> customSaleItems) {
        this.customSaleItems = customSaleItems;
    }
}
