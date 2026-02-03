package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.response.BaseExpensesResponse;

public interface ExpensesService {
    BaseExpensesResponse createExpenses(String desc, double price);
    BaseExpensesResponse viewExpenses();
    BaseExpensesResponse deleteExpenses(int reloadId);
    BaseExpensesResponse viewAllExpensesByDate(String date);//sample 2026-01-29
    BaseExpensesResponse updateExpenses(int expensesId,String desc, String price);
}
