package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleModel,Long> {
    @Query(value = "SELECT * FROM t_sale WHERE DATE(`date`) = :localDate", nativeQuery = true)
    List<SaleModel> getAllSalesByDate(@Param("localDate") String localDate);

}
