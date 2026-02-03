package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.model.ExpensesModel;
import com.diyadahara.orders_manage.repo.ExpensesRepo;
import com.diyadahara.orders_manage.response.BaseExpensesResponse;
import com.diyadahara.orders_manage.response.BaseReloadResponse;
import com.diyadahara.orders_manage.service.ExpensesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    private final ExpensesRepo expensesRepo;
    private static final Logger logger = LoggerFactory.getLogger(ExpensesServiceImpl.class);

    public ExpensesServiceImpl(ExpensesRepo expensesRepo) {
        this.expensesRepo = expensesRepo;
    }

    @Override
    public BaseExpensesResponse createExpenses(String desc, double price) {
        logger.info("Method execution start in createExpenses |Description={}",desc);
        BaseExpensesResponse baseExpensesResponse = new BaseExpensesResponse();
        List<ExpensesModel> expensesList = new ArrayList<>();

        ExpensesModel expensesModel = new ExpensesModel();
        expensesModel.setDescription(desc);
        expensesModel.setPrice(price);
        try {
            ExpensesModel save = expensesRepo.save(expensesModel);
            expensesList.add(save);
            baseExpensesResponse.setStatus("200");
            baseExpensesResponse.setMsg("Expenses create successfully");
            baseExpensesResponse.setData(expensesList);
            logger.info("Method execution completed in createExpenses |Response={}",baseExpensesResponse);
            return baseExpensesResponse;
        } catch (Exception e) {
            logger.error("Error create createExpenses: {}", e.getMessage(), e);
            baseExpensesResponse.setStatus("400");
            baseExpensesResponse.setMsg(e.getMessage());
            baseExpensesResponse.setData(null);
            return baseExpensesResponse;
        }
    }

    @Override
    public BaseExpensesResponse viewExpenses() {
        logger.info("Method execution start in viewExpenses");
        BaseExpensesResponse baseExpensesResponse = new BaseExpensesResponse();
        List<ExpensesModel> expensesList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        try {
            List<ExpensesModel> allExpensesByDate = expensesRepo.getAllExpensesByDate(today.toString());
            if(allExpensesByDate.isEmpty()){
                logger.info("Method execution completed in viewExpenses |Response={}","Still not have expenses today");
                baseExpensesResponse.setMsg("Still not have expenses today");
                baseExpensesResponse.setStatus("200");
                baseExpensesResponse.setData(new ArrayList<>());
                return baseExpensesResponse;
            }
            expensesList.addAll(allExpensesByDate);
            baseExpensesResponse.setMsg("Fetch all expenses data");
            baseExpensesResponse.setStatus("200");
            baseExpensesResponse.setData(expensesList);
            logger.info("Method execution completed in viewExpenses |Response={}",baseExpensesResponse);
            return baseExpensesResponse;

        } catch (Exception e) {
            logger.error("Error in viewExpenses: {}", e.getMessage(), e);
            baseExpensesResponse.setMsg(e.getMessage());
            baseExpensesResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseExpensesResponse.setData(null);
            return baseExpensesResponse;
        }
    }

    @Override
    public BaseExpensesResponse deleteExpenses(int reloadId) {
        logger.info("Method execution start in deleteExpenses |ReloadId={}",reloadId);
        BaseExpensesResponse baseExpensesResponse = new BaseExpensesResponse();
        try {
            expensesRepo.deleteById((long)reloadId);
            baseExpensesResponse.setStatus("200");
            baseExpensesResponse.setMsg("Expenses delete successfully");
            baseExpensesResponse.setData(new ArrayList<>());
            return baseExpensesResponse;
        } catch (Exception e) {
            logger.error("Error in deleteExpenses: {}", e.getMessage(), e);
            baseExpensesResponse.setMsg(e.getMessage());
            baseExpensesResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseExpensesResponse.setData(null);
            return baseExpensesResponse;
        }
    }

    @Override
    public BaseExpensesResponse viewAllExpensesByDate(String date) {
        logger.info("Method execution start in viewAllExpensesByDate");
        BaseExpensesResponse baseExpensesResponse = new BaseExpensesResponse();
        List<ExpensesModel> expensesList = new ArrayList<>();
        try {
            List<ExpensesModel> allExpensesByDate = expensesRepo.getAllExpensesByDate(date);
            if(allExpensesByDate.isEmpty()){
                logger.info("Method execution completed in viewAllExpensesByDate |Response={}","Still not have expenses today");
                baseExpensesResponse.setMsg("Still not have expenses");
                baseExpensesResponse.setStatus("200");
                baseExpensesResponse.setData(new ArrayList<>());
                return baseExpensesResponse;
            }
            expensesList.addAll(allExpensesByDate);
            baseExpensesResponse.setMsg("Fetch all expenses data");
            baseExpensesResponse.setStatus("200");
            baseExpensesResponse.setData(expensesList);
            logger.info("Method execution completed in viewAllExpensesByDate |Response={}",baseExpensesResponse);
            return baseExpensesResponse;

        } catch (Exception e) {
            logger.error("Error in viewAllExpensesByDate: {}", e.getMessage(), e);
            baseExpensesResponse.setMsg(e.getMessage());
            baseExpensesResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseExpensesResponse.setData(null);
            return baseExpensesResponse;
        }
    }

    @Override
    public BaseExpensesResponse updateExpenses(int expensesId, String desc, String price) {
        logger.info("Method execution start in updateExpenses |Description={}",desc);
        BaseExpensesResponse baseExpensesResponse = new BaseExpensesResponse();
        List<ExpensesModel> expensesList = new ArrayList<>();

        ExpensesModel expensesModel = new ExpensesModel();
        expensesModel.setExpensesId((long)expensesId);
        expensesModel.setDescription(desc);
        expensesModel.setPrice(Double.parseDouble(price));
        try {
            ExpensesModel save = expensesRepo.save(expensesModel);
            expensesList.add(save);
            baseExpensesResponse.setStatus("200");
            baseExpensesResponse.setMsg("Expenses update successfully");
            baseExpensesResponse.setData(expensesList);
            logger.info("Method execution completed in updateExpenses |Response={}",baseExpensesResponse);
            return baseExpensesResponse;
        } catch (Exception e) {
            logger.error("Error create updateExpenses: {}", e.getMessage(), e);
            baseExpensesResponse.setStatus("400");
            baseExpensesResponse.setMsg(e.getMessage());
            baseExpensesResponse.setData(null);
            return baseExpensesResponse;
        }
    }
}
