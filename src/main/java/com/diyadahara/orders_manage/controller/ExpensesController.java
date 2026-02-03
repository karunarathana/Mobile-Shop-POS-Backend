package com.diyadahara.orders_manage.controller;

import com.diyadahara.orders_manage.constant.APIConst;
import com.diyadahara.orders_manage.response.BaseExpensesResponse;
import com.diyadahara.orders_manage.response.BaseReloadResponse;
import com.diyadahara.orders_manage.service.ExpensesService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(APIConst.API_ROOT)
public class ExpensesController {
    private final ExpensesService expensesService;
    private static final Logger logger = LoggerFactory.getLogger(ExpensesController.class);

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @RequestMapping(value = APIConst.CREATE_EXPENSES, method = RequestMethod.POST)
    public ResponseEntity<BaseExpensesResponse> createExpenses(@Valid @RequestParam("description") String desc,
                                                               @RequestParam("price") String price
    ) {
        logger.info("Request started in createExpenses |Description={} |Price={}", desc, price);
        BaseExpensesResponse response = expensesService.createExpenses(desc, Double.parseDouble(price));
        logger.info("Request Completed IN createExpenses |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = APIConst.VIEW_ALL_EXPENSES, method = RequestMethod.GET)
    public ResponseEntity<BaseExpensesResponse> viewAllExpenses(
    ) {
        logger.info("Request started in viewAllExpenses");
        BaseExpensesResponse response = expensesService.viewExpenses();
        logger.info("Request Completed IN viewAllExpenses |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = APIConst.VIEW_ALL_PREVIOUS_EXPENSES, method = RequestMethod.POST)
    public ResponseEntity<BaseExpensesResponse> viewAllExpensesByDate(@Valid @RequestParam("date") String date
    ) {
        logger.info("Request started in viewAllExpensesByDate |Date={}",date);
        BaseExpensesResponse response = expensesService.viewAllExpensesByDate(date);
        logger.info("Request Completed IN viewAllExpensesByDate |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = APIConst.EDIT_EXPENSES, method = RequestMethod.POST)
    public ResponseEntity<BaseExpensesResponse> updateExpenses(@Valid @RequestParam("description") String desc,
                                                             @RequestParam("price") String price,
                                                             @RequestParam("eId") int eid
    ) {
        logger.info("Request started in updateExpenses |Description={} |Price={}", desc, price);
        BaseExpensesResponse response = expensesService.updateExpenses(eid, desc, price);
        logger.info("Request Completed IN updateExpenses |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = APIConst.DELETE_EXPENSES, method = RequestMethod.DELETE)
    public ResponseEntity<BaseExpensesResponse> deleteExpenses(@Valid @RequestParam("eId") int eid
    ) {
        logger.info("Request started in deleteExpenses |ExpensesId={}", eid);
        BaseExpensesResponse response = expensesService.deleteExpenses(eid);
        logger.info("Request Completed IN deleteExpenses |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
