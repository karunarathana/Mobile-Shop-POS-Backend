package com.diyadahara.orders_manage.controller;

import com.diyadahara.orders_manage.constant.APIConst;
import com.diyadahara.orders_manage.dto.OrderDto;
import com.diyadahara.orders_manage.dto.RepairDto;
import com.diyadahara.orders_manage.response.BaseRepairResponse;
import com.diyadahara.orders_manage.service.RepairService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(APIConst.API_ROOT)
public class RepairController {
    private final RepairService repairService;
    private static final Logger logger = LoggerFactory.getLogger(RepairController.class);

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @RequestMapping(value = APIConst.CREATE_REPAIR, method = RequestMethod.POST)
    public ResponseEntity<BaseRepairResponse> createRepair(@Valid @RequestBody RepairDto repairDto) {
        logger.info("Request Started IN createRepair |Request={}", repairDto);
        BaseRepairResponse response = repairService.createRepair(repairDto);
        logger.info("Request Completed IN createRepair |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @RequestMapping(value = APIConst.VIEW_SINGLE_REPAIR, method = RequestMethod.GET)
    public ResponseEntity<BaseRepairResponse> viewAllRepair(@RequestParam("cusId") int cusId) {
        logger.info("Request Started IN viewAllRepair");
        BaseRepairResponse response = repairService.getAllRepairSingleCustomer(cusId);
        logger.info("Request Completed IN viewAllRepair");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
