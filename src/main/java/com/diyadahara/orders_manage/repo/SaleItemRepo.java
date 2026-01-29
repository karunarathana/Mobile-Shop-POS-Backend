package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.ProductModel;
import com.diyadahara.orders_manage.model.SaleItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepo extends JpaRepository<SaleItemModel,Long> {
    @Query(value = "SELECT * FROM t_sale_item WHERE sale_id = :saleId", nativeQuery = true)
    List<SaleItemModel> allSaleItemBySaleId(@Param("saleId") Long saleId);
}
