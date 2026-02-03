package com.diyadahara.orders_manage.dto;

public class ProductAccessory {
    private String productName;
    private double purchasePrice;
    private double sellingPrice;
    private String status;
    private int discountPercentage;
    private String type;
    private int stock;
    private AccessoryDto accessoryDto;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccessoryDto getAccessoryDto() {
        return accessoryDto;
    }

    public void setAccessoryDto(AccessoryDto accessoryDto) {
        this.accessoryDto = accessoryDto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
