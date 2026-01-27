package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<SaleModel,Long> {
}
