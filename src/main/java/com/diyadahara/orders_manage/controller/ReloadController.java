package com.diyadahara.orders_manage.controller;

import com.diyadahara.orders_manage.constant.APIConst;
import com.diyadahara.orders_manage.response.BaseReloadResponse;
import com.diyadahara.orders_manage.service.ReloadService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(APIConst.API_ROOT)
public class ReloadController {
    private final ReloadService reloadService;
    private static final Logger logger = LoggerFactory.getLogger(ReloadController.class);

    public ReloadController(ReloadService reloadService) {
        this.reloadService = reloadService;
    }

    @RequestMapping(value = APIConst.CREATE_RELOAD, method = RequestMethod.POST)
    public ResponseEntity<BaseReloadResponse> createReload(@Valid @RequestParam("description") String desc,
                                                           @RequestParam("price") double price,
                                                           @RequestParam("simType") String simType,
                                                           @RequestParam("status") String status

    ) {
        logger.info("Request started in createReload |Description={} |Price={}", desc, price);
        BaseReloadResponse response = reloadService.createReload(desc, price, simType, status);
        logger.info("Request Completed IN createReload |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = APIConst.DELETE_RELOAD, method = RequestMethod.DELETE)
    public ResponseEntity<BaseReloadResponse> deleteReload(@Valid @RequestParam("reloadId") int reloadId) {
        logger.info("Request started in deleteReload |ReloadId={}", reloadId);
        BaseReloadResponse response = reloadService.deleteReload(reloadId);
        logger.info("Request Completed IN deleteReload |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = APIConst.EDIT_RELOAD, method = RequestMethod.POST)
    public ResponseEntity<?> editReload(@Valid
                                        @RequestParam("rId") int rid,
                                        @RequestParam("description") String desc,
                                        @RequestParam("price") String price,
                                        @RequestParam("simType") String simType,
                                        @RequestParam("status") String status
    ) {
        logger.info("Request started in editReload |Description={} |Price={} |ReloadId={}", desc, price, rid);
        BaseReloadResponse response = reloadService.updateReload(rid, desc, price, simType, status);
        logger.info("Request Completed IN editReload |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = APIConst.VIEW_ALL_RELOAD, method = RequestMethod.GET)
    public ResponseEntity<?> viewAllTodayReload() {
        logger.info("Request started in viewAllReload");
        BaseReloadResponse response = reloadService.viewReload();
        logger.info("Request Completed IN viewAllReload |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = APIConst.VIEW_ALL_PREVIOUS_RELOAD, method = RequestMethod.GET)
    public ResponseEntity<?> viewAllPreviousReload(@RequestParam("date") String date) {
        logger.info("Request started in viewAllPreviousReload");
        BaseReloadResponse response = reloadService.viewReloadByDate(date);
        logger.info("Request Completed IN viewAllPreviousReload |Response={}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
