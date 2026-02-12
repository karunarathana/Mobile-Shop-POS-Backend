package com.diyadahara.orders_manage.controller;

import com.diyadahara.orders_manage.constant.APIConst;
import com.diyadahara.orders_manage.dto.RepairDto;
import com.diyadahara.orders_manage.dto.SaleDto;
import com.diyadahara.orders_manage.model.SaleModel;
import com.diyadahara.orders_manage.response.BaseRepairResponse;
import com.diyadahara.orders_manage.response.BaseSaleResponse;
import com.diyadahara.orders_manage.response.CustomSaleResponse;
import com.diyadahara.orders_manage.service.SaleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(APIConst.API_ROOT)
public class SaleController {
    private final SaleService saleService;
    private static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    public SaleController(SaleService service) {
        this.saleService = service;
    }

    @RequestMapping(value = APIConst.CREATE_SALE, method = RequestMethod.POST)
    public ResponseEntity<?> createSale(@Valid @RequestBody SaleDto saleDto) {
        logger.info("Request Started IN createSale |Request={}", saleDto);
        String response = saleService.createSale(saleDto);
        logger.info("Request Completed IN createSale |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @RequestMapping(value = APIConst.VIEW_ALL_SALE, method = RequestMethod.GET)
    public ResponseEntity<BaseSaleResponse> viewAllSale() {
        logger.info("Request Started IN viewAllSale");
        BaseSaleResponse response = saleService.viewAllSale();
        logger.info("Request Completed IN viewAllSale |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
