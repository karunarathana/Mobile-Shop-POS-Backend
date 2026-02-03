package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepo extends JpaRepository<PhoneModel,Long> {
    @Query(value = "SELECT * FROM t_phone WHERE imei_number = :imei", nativeQuery = true)
    PhoneModel existsByProduct(@Param("imei") String imei);

    @Query(value = "SELECT * FROM t_phone WHERE category_id = :categoryId", nativeQuery = true)
    List<PhoneModel> allProductByCategory(@Param("categoryId") Long categoryId);
}
