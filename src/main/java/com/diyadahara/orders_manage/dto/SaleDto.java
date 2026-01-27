package com.diyadahara.orders_manage.dto;

import com.diyadahara.orders_manage.config.PaymentMethod;
import com.diyadahara.orders_manage.config.PaymentStatus;

import java.util.List;

public class SaleDto {
    private double totalAmount;
    private PaymentMethod paymentMethod; // CASH, CARD, MOBILE_PAYMENT
    private PaymentStatus paymentStatus; // PAID, PENDING, PARTIALLY_PAID
    private Long customerId;
    private List<SaleItemDTO> saleItems;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<SaleItemDTO> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItemDTO> saleItems) {
        this.saleItems = saleItems;
    }
}
