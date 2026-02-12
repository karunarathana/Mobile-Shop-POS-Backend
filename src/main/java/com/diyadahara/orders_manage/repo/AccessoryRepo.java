package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.AccessoryModel;
import com.diyadahara.orders_manage.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoryRepo extends JpaRepository<AccessoryModel,Integer> {
    @Query(value = "SELECT * FROM t_accessory WHERE product_id = :productId", nativeQuery = true)
    Optional<AccessoryModel> findByProductId(@Param("productId") Long productId);

}
