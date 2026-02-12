package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.dto.SaleDto;
import com.diyadahara.orders_manage.response.BaseSaleResponse;
import com.diyadahara.orders_manage.response.CustomSaleResponse;

public interface SaleService {
    String createSale(SaleDto saleDto);
    BaseSaleResponse viewAllSale();
    BaseSaleResponse viewAllSaleBaseLocalDate(String date);
}
