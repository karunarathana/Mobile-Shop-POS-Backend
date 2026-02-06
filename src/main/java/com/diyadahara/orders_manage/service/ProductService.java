package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.dto.ProductAccessory;
import com.diyadahara.orders_manage.dto.ProductDto;
import com.diyadahara.orders_manage.dto.UpdateAccessoryProductDto;
import com.diyadahara.orders_manage.dto.UpdatePhoneProductDto;
import com.diyadahara.orders_manage.response.BaseAllProductResponse;
import com.diyadahara.orders_manage.response.BaseProductResponse;

public interface ProductService {
    BaseProductResponse createPhone(ProductDto productDto);
    BaseProductResponse createAccessory(ProductAccessory productDto);
    BaseAllProductResponse viewAllProduct();
    BaseProductResponse viewSingleProduct(int productId);
    BaseAllProductResponse viewAllProductByTypeNotAPhone();
    BaseProductResponse deleteSingleProduct(int productId);
    BaseAllProductResponse viewAllProductByCategory(String type);
    BaseProductResponse updateSingleProduct(UpdateAccessoryProductDto productDto);
    BaseProductResponse updateSingleAccessoryProduct(UpdateAccessoryProductDto accessoryProductDto);
    BaseProductResponse updateSinglePhoneProduct(UpdatePhoneProductDto phoneProductDto);
}
