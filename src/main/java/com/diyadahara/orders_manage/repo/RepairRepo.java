package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.RepairModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepo extends JpaRepository<RepairModel,Long> {
    @Query(value = "SELECT * FROM t_repair_service WHERE customer_id = :customerId", nativeQuery = true)
    List<RepairModel> allRepairDetailsByCustomerId(@Param("customerId") Long customerId);
}
