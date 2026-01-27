package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.dto.AccessoryDto;
import com.diyadahara.orders_manage.dto.UpdateAccessoryDto;
import com.diyadahara.orders_manage.response.BaseAccessoryResponse;

public interface AccessoryService {
    BaseAccessoryResponse createAccessory(AccessoryDto accessoryDto);
    BaseAccessoryResponse deleteAccessory(int accId);
    BaseAccessoryResponse updateAccessory(UpdateAccessoryDto updateAccessoryDto);
    BaseAccessoryResponse viewAllAccessory();
    BaseAccessoryResponse viewSingleAccessory(int accId);
}
