package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.config.ReloadStatus;
import com.diyadahara.orders_manage.model.ReloadModel;
import com.diyadahara.orders_manage.repo.ReloadRepo;
import com.diyadahara.orders_manage.response.BaseReloadResponse;
import com.diyadahara.orders_manage.service.ReloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReloadServiceImpl implements ReloadService {
    private final ReloadRepo reloadRepo;
    private static final Logger logger = LoggerFactory.getLogger(ReloadServiceImpl.class);

    public ReloadServiceImpl(ReloadRepo reloadRepo) {
        this.reloadRepo = reloadRepo;
    }

    public BaseReloadResponse createReload(String desc, double price, String simType, String status) {
        logger.info("Method execution started in createReload |description={}", desc);
        BaseReloadResponse baseReloadResponse = new BaseReloadResponse();
        try {
            ReloadModel reloadModel = new ReloadModel();
            reloadModel.setPrice(price);
            reloadModel.setDescription(desc);
            reloadModel.setSimType(simType);
            reloadModel.setStatus(ReloadStatus.valueOf(status));

            reloadRepo.save(reloadModel);
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
            baseReloadResponse.setMsg("Reload create successfully");
            baseReloadResponse.setData(new ArrayList<>());
            logger.info("Method execution completed in createReload |response={}",baseReloadResponse);
            return baseReloadResponse;
        } catch (Exception e) {
            logger.error("Error create createReload: {}", e.getMessage(), e);
            baseReloadResponse.setMsg(e.getMessage());
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseReloadResponse.setData(null);
            return baseReloadResponse;
        }
    }

    @Override
    public BaseReloadResponse viewReload() {
        logger.info("Method execution started in viewReload");
        List<ReloadModel> reloadList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        BaseReloadResponse baseReloadResponse = new BaseReloadResponse();
        try {
            List<ReloadModel> all = reloadRepo.getAllReloadByDate(today.toString());
            if(all.isEmpty()){
                baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.OK));
                baseReloadResponse.setMsg("Today not reload data");
                baseReloadResponse.setData(new ArrayList<>());
                logger.info("Method execution completed in viewReload |Response={}","Today not reload data");
                return baseReloadResponse;
            }
            reloadList.addAll(all);
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseReloadResponse.setMsg("Fetched data successfully");
            baseReloadResponse.setData(reloadList);
            logger.info("Method execution completed in viewReload");
            return baseReloadResponse;
        } catch (Exception e) {
            logger.error("Error in viewReload: {}", e.getMessage(), e);
            baseReloadResponse.setMsg(e.getMessage());
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseReloadResponse.setData(null);
            return baseReloadResponse;
        }
    }

    @Override
    public BaseReloadResponse deleteReload(int reloadId) {
        logger.info("Method execution started in deleteReload |ReloadId={}",reloadId);
        BaseReloadResponse baseReloadResponse = new BaseReloadResponse();
        try {
            reloadRepo.deleteById((long)reloadId);
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseReloadResponse.setMsg("Delete data successfully");
            baseReloadResponse.setData(new ArrayList<>());
            return baseReloadResponse;
        } catch (Exception e) {
            logger.error("Error in viewReload: {}", e.getMessage(), e);
            baseReloadResponse.setMsg(e.getMessage());
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseReloadResponse.setData(null);
            return baseReloadResponse;
        }
    }
    @Override
    public BaseReloadResponse viewReloadByDate(String date) {
        logger.info("Method execution started in viewReloadByDate");
        List<ReloadModel> reloadList = new ArrayList<>();
        BaseReloadResponse baseReloadResponse = new BaseReloadResponse();
        try {
            List<ReloadModel> all = reloadRepo.getAllReloadByDate(date);
            if(all.isEmpty()){
                baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.OK));
                baseReloadResponse.setMsg("Reload data not found");
                baseReloadResponse.setData(new ArrayList<>());
                logger.info("Method execution completed in viewReloadByDate |Response={}","Reload data not found");
                return baseReloadResponse;
            }
            reloadList.addAll(all);
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseReloadResponse.setMsg("Fetched data successfully");
            baseReloadResponse.setData(reloadList);
            logger.info("Method execution completed in viewReloadByDate");
            return baseReloadResponse;
        } catch (Exception e) {
            logger.error("Error in viewReloadByDate: {}", e.getMessage(), e);
            baseReloadResponse.setMsg(e.getMessage());
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseReloadResponse.setData(null);
            return baseReloadResponse;
        }
    }

    //
    public BaseReloadResponse updateReload(int reloadId,String desc, String price,String simType,String status) {
        logger.info("Method execution started in updateReload |description={}", desc);
        BaseReloadResponse baseReloadResponse = new BaseReloadResponse();
        try {
            ReloadModel reloadModel = new ReloadModel();
            reloadModel.setReloadId((long)reloadId);
            reloadModel.setPrice(Double.parseDouble(price));
            reloadModel.setDescription(desc);
            reloadModel.setSimType(simType);
            reloadModel.setStatus(ReloadStatus.valueOf(status));

            reloadRepo.save(reloadModel);
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
            baseReloadResponse.setMsg("Reload update successfully");
            baseReloadResponse.setData(new ArrayList<>());
            logger.info("Method execution completed in updateReload |response={}",baseReloadResponse);
            return baseReloadResponse;
        } catch (Exception e) {
            logger.error("Error update reload: {}", e.getMessage(), e);
            baseReloadResponse.setMsg(e.getMessage());
            baseReloadResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            baseReloadResponse.setData(null);
            return baseReloadResponse;
        }
    }
}
