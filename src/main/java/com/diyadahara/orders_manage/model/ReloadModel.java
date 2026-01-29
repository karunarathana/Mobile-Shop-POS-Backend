package com.diyadahara.orders_manage.model;

import com.diyadahara.orders_manage.config.ProductStatus;
import com.diyadahara.orders_manage.config.ReloadStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_reload")
public class ReloadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reload_id")
    private Long reloadId;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String simType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReloadStatus status;


    @Positive
    @Column(nullable = false)
    private double price;

    // Audit fields
    @CreationTimestamp
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @CreatedDate
    @Column(name = "date", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    public Long getReloadId() {
        return reloadId;
    }

    public void setReloadId(Long reloadId) {
        this.reloadId = reloadId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String type) {
        this.simType = type;
    }
    public ReloadStatus getStatus() {
        return status;
    }

    public void setStatus(ReloadStatus status) {
        this.status = status;
    }


}
