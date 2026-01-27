package com.diyadahara.orders_manage.model;

import com.diyadahara.orders_manage.config.WarrantyStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_warranty")
public class WarrantyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warranty_id")
    private Long warrantyId;

    // Linked Sale Item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_item_id",
            referencedColumnName = "sale_item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_warranty_sale_item")
    )
    private SaleItemModel saleItem;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String terms;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_status", nullable = false, length = 20)
    private WarrantyStatus status;

    public Long getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(Long warrantyId) {
        this.warrantyId = warrantyId;
    }

    public SaleItemModel getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItemModel saleItem) {
        this.saleItem = saleItem;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public WarrantyStatus getStatus() {
        return status;
    }

    public void setStatus(WarrantyStatus status) {
        this.status = status;
    }
}
