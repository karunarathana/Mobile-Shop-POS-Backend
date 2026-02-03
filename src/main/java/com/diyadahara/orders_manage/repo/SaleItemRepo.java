package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.SaleItemModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepo extends JpaRepository<SaleItemModel,Long> {
    @Query(value = "SELECT * FROM t_sale_item WHERE sale_id = :saleId", nativeQuery = true)
    List<SaleItemModel> allSaleItemBySaleId(@Param("saleId") Long saleId);

    @Query(value = "SELECT * FROM t_sale_item WHERE product_id = :productId", nativeQuery = true)
    List<SaleItemModel> allSaleItemByProductId(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM t_sale_item WHERE product_id = :productId",
            nativeQuery = true
    )
    void deleteAllByProductId(@Param("productId") Long productId);

}
