package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.ProductModel;
import com.diyadahara.orders_manage.model.ReloadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReloadRepo extends JpaRepository<ReloadModel,Long> {
    @Query(value = "SELECT * FROM t_reload WHERE date = :localDate", nativeQuery = true)
    List<ReloadModel> getAllReloadByDate(@Param("localDate") String localDate);
}
