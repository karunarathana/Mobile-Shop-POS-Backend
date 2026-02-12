package com.diyadahara.orders_manage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_sale_item")
public class SaleItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_item_id")
    private Long saleItemId;

    // Parent Sale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_id",
            referencedColumnName = "sale_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sale_item_sale")
    )
    private SaleModel sale;

    @Column(name = "product_type", nullable = false)
    private String productType; // PHONE / ACCESSORY

    @Column(name = "product_id", nullable = false)
    private String productId;

    // Product sold
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "product_id",
//            referencedColumnName = "product_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_sale_item_product")
//    )
//    private ProductModel product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "warranty_duration")
    private int warrantyDuration; // in months

    public Long getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(Long saleItemId) {
        this.saleItemId = saleItemId;
    }

    public SaleModel getSale() {
        return sale;
    }

    public void setSale(SaleModel sale) {
        this.sale = sale;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getWarrantyDuration() {
        return warrantyDuration;
    }

    public void setWarrantyDuration(int warrantyDuration) {
        this.warrantyDuration = warrantyDuration;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}

