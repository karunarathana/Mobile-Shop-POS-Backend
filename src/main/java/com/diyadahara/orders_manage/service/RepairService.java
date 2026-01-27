package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.dto.RepairDto;
import com.diyadahara.orders_manage.dto.UpdateRepairDto;
import com.diyadahara.orders_manage.response.BaseRepairResponse;

public interface RepairService {
    BaseRepairResponse createRepair(RepairDto repairDto);
    BaseRepairResponse deleteSingleRepair(int repairId);
    BaseRepairResponse updateSingleRepair(UpdateRepairDto updateRepairDto);
    BaseRepairResponse getAllRepairSingleCustomer(int customerId);
}
