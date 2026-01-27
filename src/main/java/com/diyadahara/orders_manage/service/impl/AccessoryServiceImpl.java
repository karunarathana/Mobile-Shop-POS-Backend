package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.dto.AccessoryDto;
import com.diyadahara.orders_manage.dto.UpdateAccessoryDto;
import com.diyadahara.orders_manage.model.AccessoryModel;
import com.diyadahara.orders_manage.repo.AccessoryRepo;
import com.diyadahara.orders_manage.response.BaseAccessoryResponse;
import com.diyadahara.orders_manage.service.AccessoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AccessoryServiceImpl implements AccessoryService {
    private final AccessoryRepo accessoryRepo;
    private static final Logger logger = LoggerFactory.getLogger(AccessoryServiceImpl.class);

    public AccessoryServiceImpl(AccessoryRepo accessoryRepo) {
        this.accessoryRepo = accessoryRepo;
    }

    @Override
    public BaseAccessoryResponse createAccessory(AccessoryDto accessoryDto) {
        logger.info("Method execution start in createAccessory |AccessoryDto={}",accessoryDto);
        BaseAccessoryResponse baseAccessoryResponse = new BaseAccessoryResponse();
        List<AccessoryModel> data = new LinkedList<>();
        try{
            AccessoryModel saveResponse = accessoryRepo.save(createAccessoryModel(accessoryDto));
            baseAccessoryResponse.setMsg("Accessory Saved Successfully");
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
            data.add(saveResponse);
            baseAccessoryResponse.setData(data);
            logger.info("Method execution completed in createAccessory |Response={}",baseAccessoryResponse);
            return baseAccessoryResponse;
        } catch (Exception e) {
            logger.error("Error create accessory: {}", e.getMessage(), e);
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setMsg("Oops System Error "+e.getMessage());
            baseAccessoryResponse.setData(new ArrayList<>());
            return baseAccessoryResponse;
        }
    }

    @Override
    public BaseAccessoryResponse deleteAccessory(int accId) {
        logger.info("Method execution start in deleteAccessory |AccessoryId={}",accId);
        BaseAccessoryResponse baseAccessoryResponse = new BaseAccessoryResponse();
        try{
            accessoryRepo.deleteById(accId);
            baseAccessoryResponse.setMsg("Accessory Delete Successfully");
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseAccessoryResponse.setData(new LinkedList<>());
            logger.info("Method execution completed in deleteAccessory |Response={}",baseAccessoryResponse);
            return baseAccessoryResponse;
        } catch (Exception e) {
            logger.error("Error delete accessory: {}", e.getMessage(), e);
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setMsg("Oops System Error "+e.getMessage());
            baseAccessoryResponse.setData(new ArrayList<>());
            return baseAccessoryResponse;
        }
    }

    @Override
    public BaseAccessoryResponse updateAccessory(UpdateAccessoryDto updateAccessoryDto) {
        logger.info("Method execution start in updateAccessory |UpdateAccessoryDto={}",updateAccessoryDto);
        BaseAccessoryResponse baseAccessoryResponse = new BaseAccessoryResponse();
        try{
            AccessoryModel updateResponse = accessoryRepo.save(updateAccessoryModel(updateAccessoryDto));
            baseAccessoryResponse.setMsg("Accessory Update Successfully");
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseAccessoryResponse.setData(new ArrayList<>());
            logger.info("Method execution completed in updateAccessory |Response={}",baseAccessoryResponse);
            return baseAccessoryResponse;
        } catch (Exception e) {
            logger.error("Error update accessory: {}", e.getMessage(), e);
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setMsg("Oops System Error "+e.getMessage());
            baseAccessoryResponse.setData(new ArrayList<>());
            return baseAccessoryResponse;
        }
    }

    @Override
    public BaseAccessoryResponse viewAllAccessory() {
        logger.info("Method execution start in viewAllAccessory");
        BaseAccessoryResponse baseAccessoryResponse = new BaseAccessoryResponse();
        try{
            List<AccessoryModel> allResponse = accessoryRepo.findAll();
            baseAccessoryResponse.setMsg("Accessory Fetched Successfully");
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseAccessoryResponse.setData(allResponse);
            logger.info("Method execution completed in viewAllAccessory |Response={}",baseAccessoryResponse);
            return baseAccessoryResponse;
        } catch (Exception e) {
            logger.error("Error view all accessory: {}", e.getMessage(), e);
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setMsg("Oops System Error "+e.getMessage());
            baseAccessoryResponse.setData(new ArrayList<>());
            return baseAccessoryResponse;
        }
    }

    @Override
    public BaseAccessoryResponse viewSingleAccessory(int accId) {
        logger.info("Method execution start in viewSingleAccessory |AccessoryId={}",accId);
        BaseAccessoryResponse baseAccessoryResponse = new BaseAccessoryResponse();
        List<AccessoryModel> data = new LinkedList<>();
        try{
            Optional<AccessoryModel> byId = accessoryRepo.findById(accId);
            if(byId.isPresent()){
                baseAccessoryResponse.setMsg("Accessory Fetched Successfully");
                baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
                data.add(byId.get());
                baseAccessoryResponse.setData(data);
                return baseAccessoryResponse;
            }
            baseAccessoryResponse.setMsg("Not Accessory item In Database");
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setData(data);
            logger.info("Method execution completed in viewSingleAccessory |Response={}",baseAccessoryResponse);
            return baseAccessoryResponse;
        } catch (Exception e) {
            logger.error("Error view single accessory: {}", e.getMessage(), e);
            baseAccessoryResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseAccessoryResponse.setMsg("Oops System Error "+e.getMessage());
            baseAccessoryResponse.setData(new ArrayList<>());
            return baseAccessoryResponse;
        }
    }

    private AccessoryModel createAccessoryModel(AccessoryDto accessoryDto){
        AccessoryModel accessoryModel = new AccessoryModel();
        accessoryModel.setName(accessoryDto.getName());
        accessoryModel.setType(accessoryDto.getType());
        accessoryModel.setBrand(accessoryDto.getBrand());
        accessoryModel.setCompatibleWith(accessoryDto.getCompatibleWith());
        accessoryModel.setCostPrice(accessoryDto.getCostPrice());
        accessoryModel.setSellPrice(accessoryDto.getSellPrice());
        accessoryModel.setQuantity(accessoryDto.getQuantity());
        accessoryModel.setRackId(accessoryDto.getRackId());
        return accessoryModel;
    }
    private AccessoryModel updateAccessoryModel(UpdateAccessoryDto accessoryDto){
        AccessoryModel accessoryModel = new AccessoryModel();
        accessoryModel.setAccessoryId((long)accessoryDto.getAccessoryId());
        accessoryModel.setName(accessoryDto.getName());
        accessoryModel.setType(accessoryDto.getType());
        accessoryModel.setBrand(accessoryDto.getBrand());
        accessoryModel.setCompatibleWith(accessoryDto.getCompatibleWith());
        accessoryModel.setCostPrice(accessoryDto.getCostPrice());
        accessoryModel.setSellPrice(accessoryDto.getSellPrice());
        accessoryModel.setQuantity(accessoryDto.getQuantity());
        accessoryModel.setRackId(accessoryDto.getRackId());
        return accessoryModel;
    }
}
