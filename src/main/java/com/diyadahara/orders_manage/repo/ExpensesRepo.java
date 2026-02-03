package com.diyadahara.orders_manage.repo;

import com.diyadahara.orders_manage.model.ExpensesModel;
import com.diyadahara.orders_manage.model.ReloadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpensesRepo extends JpaRepository<ExpensesModel,Long> {
    @Query(value = "SELECT * FROM t_expenses WHERE date = :localDate", nativeQuery = true)
    List<ExpensesModel> getAllExpensesByDate(@Param("localDate") String localDate);
}
