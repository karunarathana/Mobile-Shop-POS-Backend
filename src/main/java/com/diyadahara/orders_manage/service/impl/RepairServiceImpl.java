package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.config.RepairStatus;
import com.diyadahara.orders_manage.dto.RepairDto;
import com.diyadahara.orders_manage.dto.UpdateRepairDto;
import com.diyadahara.orders_manage.model.CustomerModel;
import com.diyadahara.orders_manage.model.RepairModel;
import com.diyadahara.orders_manage.repo.CustomerRepo;
import com.diyadahara.orders_manage.repo.RepairRepo;
import com.diyadahara.orders_manage.response.BaseRepairResponse;
import com.diyadahara.orders_manage.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {
    private final RepairRepo repairRepo;
    private final CustomerRepo customerRepo;

    private static final Logger logger = LoggerFactory.getLogger(RepairServiceImpl.class);

    public RepairServiceImpl(RepairRepo repairRepo, CustomerRepo customerRepo) {
        this.repairRepo = repairRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public BaseRepairResponse createRepair(RepairDto repairDto) {
        logger.info("Method Execution Start In createRepair |RepairDto={} |RepairModel={}",repairDto,repairDto.getDeviceModel());
        List<RepairModel> repairModels = new LinkedList<>();
        BaseRepairResponse baseRepairResponse = new BaseRepairResponse();
        try {
            RepairModel saveResponse = repairRepo.save(createRepairModel(repairDto));
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
            baseRepairResponse.setMsg("Create Repair");
            repairModels.add(saveResponse);
            baseRepairResponse.setData(repairModels);
            logger.info("Method Execution Completed In createRepair |Response={}",baseRepairResponse);
            return baseRepairResponse;

        } catch (Exception e) {
            logger.error("Error create repair device: {}", e.getMessage(), e);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseRepairResponse.setMsg("Oops System Error "+e.getMessage());
            baseRepairResponse.setData(new ArrayList<>());
            return baseRepairResponse;
        }
    }

    @Override
    public BaseRepairResponse deleteSingleRepair(int repairId) {
        logger.info("Method Execution Started In deleteSingleRepair |RepairId={}",repairId);
        BaseRepairResponse baseRepairResponse = new BaseRepairResponse();
        try {
            repairRepo.deleteById((long)repairId);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseRepairResponse.setMsg("Successfully Deleted");
            baseRepairResponse.setData(new ArrayList<>());
            logger.info("Method Execution Completed In deleteSingleRepair |Response={}","Successfully Deleted");
            return baseRepairResponse;
        } catch (Exception e) {
            logger.error("Error delete repair device: {}", e.getMessage(), e);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseRepairResponse.setMsg("Oops System Error "+e.getMessage());
            baseRepairResponse.setData(new ArrayList<>());
            return baseRepairResponse;
        }
    }

    @Override
    public BaseRepairResponse updateSingleRepair(UpdateRepairDto updateRepairDto) {
        logger.info("Method Execution Start In updateSingleRepair |UpdateRepairDto={} |RepairModel={}",updateRepairDto,updateRepairDto.getDeviceModel());
        List<RepairModel> repairModels = new LinkedList<>();
        BaseRepairResponse baseRepairResponse = new BaseRepairResponse();
        try {
            RepairModel saveResponse = repairRepo.save(updateRepairModel(updateRepairDto));
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.CREATED));
            baseRepairResponse.setMsg("Update Successfully");
            repairModels.add(saveResponse);
            baseRepairResponse.setData(repairModels);
            logger.info("Method Execution Completed In updateSingleRepair |Response={}",baseRepairResponse);
            return baseRepairResponse;

        } catch (Exception e) {
            logger.error("Error create repair device: {}", e.getMessage(), e);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseRepairResponse.setMsg("Oops System Error "+e.getMessage());
            baseRepairResponse.setData(new ArrayList<>());
            return baseRepairResponse;
        }
    }

    @Override
    public BaseRepairResponse getAllRepairSingleCustomer(int customerId) {
        logger.info("Method Execution Started In getAllRepairSingleCustomer |CustomerId={}",customerId);
        BaseRepairResponse baseRepairResponse = new BaseRepairResponse();
        try {
            List<RepairModel> repairList = repairRepo.allRepairDetailsByCustomerId((long) customerId);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.OK));
            baseRepairResponse.setMsg("Fetch data successfully");
            baseRepairResponse.setData(repairList);
            logger.info("Method Execution Completed In getAllRepairSingleCustomer |Response={}",baseRepairResponse);
            return baseRepairResponse;
        } catch (Exception e) {
            logger.error("Error View single customer repair device: {}", e.getMessage(), e);
            baseRepairResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseRepairResponse.setMsg("Oops System Error "+e.getMessage());
            baseRepairResponse.setData(new ArrayList<>());
            return baseRepairResponse;
        }
    }

    private RepairModel createRepairModel(RepairDto repairDto){
        RepairModel repairModel = new RepairModel();
        CustomerModel customer = customerRepo.findById((long) repairDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        repairModel.setCustomer(customer);
        repairModel.setDeviceModel(repairDto.getDeviceModel());
        repairModel.setImei(repairDto.getImei());
        repairModel.setIssueDescription(repairDto.getIssueDescription());
        repairModel.setEstimatedCost(repairDto.getEstimatedCost());
        repairModel.setStatus(RepairStatus.RECEIVED);
        repairModel.setActualCost(repairDto.getActualCost());
        repairModel.setStartDate(LocalDate.parse(repairDto.getStartDate()));
        repairModel.setCompletionDate(LocalDate.parse(repairDto.getCompletionDate()));
        return repairModel;
    }
    private RepairModel updateRepairModel(UpdateRepairDto repairDto){
        RepairModel repairModel = new RepairModel();
        CustomerModel customer = customerRepo.findById((long) repairDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        repairModel.setRepairId(repairModel.getRepairId());
        repairModel.setCustomer(customer);
        repairModel.setDeviceModel(repairDto.getDeviceModel());
        repairModel.setImei(repairDto.getImei());
        repairModel.setIssueDescription(repairDto.getIssueDescription());
        repairModel.setEstimatedCost(repairDto.getEstimatedCost());
        repairModel.setStatus(RepairStatus.RECEIVED);
        repairModel.setActualCost(repairDto.getActualCost());
        repairModel.setStartDate(LocalDate.parse(repairDto.getStartDate()));
        repairModel.setCompletionDate(LocalDate.parse(repairDto.getCompletionDate()));
        return repairModel;
    }
}
