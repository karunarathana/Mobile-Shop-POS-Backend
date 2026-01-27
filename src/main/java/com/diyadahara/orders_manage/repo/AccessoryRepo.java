package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.AccessoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepo extends JpaRepository<AccessoryModel,Integer> {
}
