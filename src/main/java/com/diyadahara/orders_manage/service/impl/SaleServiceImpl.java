package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.dto.SaleDto;
import com.diyadahara.orders_manage.dto.SaleItemDTO;
import com.diyadahara.orders_manage.model.CustomerModel;
import com.diyadahara.orders_manage.model.PhoneModel;
import com.diyadahara.orders_manage.model.SaleItemModel;
import com.diyadahara.orders_manage.model.SaleModel;
import com.diyadahara.orders_manage.repo.*;
import com.diyadahara.orders_manage.response.BaseSaleResponse;
import com.diyadahara.orders_manage.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final CustomerRepo customerRepo;
    private final WarrantyRepo warrantyRepo;
    private final PhoneRepo phoneRepo;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    public SaleServiceImpl(CustomerRepo customerRepo, WarrantyRepo warrantyRepo, PhoneRepo phoneRepo, SaleRepo saleRepo, SaleItemRepo saleItemRepo) {
        this.customerRepo = customerRepo;
        this.warrantyRepo = warrantyRepo;
        this.phoneRepo = phoneRepo;
        this.saleRepo = saleRepo;
        this.saleItemRepo = saleItemRepo;
    }

    @Override
    public String createSale(SaleDto saleDto) {
        logger.info("Method Execution Started In createSale |SaleDto={}", saleDto);
        try {
            SaleModel saveResponse = saleRepo.save(createSalemodel(saleDto));
            List<SaleItemModel> saleItemModels = saleItemRepo.saveAll(createSaleItemModel(saleDto, saveResponse));
            if (saleItemModels != null) {
                return "Sale Successfully";
            }
            return "Oops some error";
        } catch (Exception e) {
            return "null";
        }

    }

    @Override
    public BaseSaleResponse viewAllSale() {

        BaseSaleResponse baseSaleResponse = new BaseSaleResponse();

        try {
            List<SaleItemModel> saleItems = new LinkedList<>();

            List<SaleModel> allSale = saleRepo.findAll();

            for (SaleModel data : allSale) {
                List<SaleItemModel> saleItemModels =
                        saleItemRepo.allSaleItemBySaleId(data.getSaleId());

                saleItems.addAll(saleItemModels);
            }

            baseSaleResponse.setStatusCode("200");
            baseSaleResponse.setMsg("Fetched All Sale Data Successfully");
            baseSaleResponse.setSaleItems(saleItems);

        } catch (Exception e) {
            baseSaleResponse.setStatusCode("500");
            baseSaleResponse.setMsg("Error fetching sale data");
        }

        return baseSaleResponse;
    }


    private SaleModel createSalemodel(SaleDto saleDto) {
        SaleModel saleModel = new SaleModel();
        CustomerModel customer = customerRepo.findById((long) saleDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        saleModel.setCustomer(customer);
        saleModel.setTotalAmount(saleDto.getTotalAmount());
        saleModel.setPaymentMethod(saleDto.getPaymentMethod());
        saleModel.setPaymentStatus(saleDto.getPaymentStatus());
        saleModel.setPaymentMethod(saleDto.getPaymentMethod());
        return saleModel;
    }

    private List<SaleItemModel> createSaleItemModel(SaleDto saleDto, SaleModel saveResponse) {
        List<SaleItemModel> saleItems = new LinkedList<>();
        for (SaleItemDTO data : saleDto.getSaleItems()) {
            SaleItemModel saleItemModel = new SaleItemModel();
            saleItemModel.setQuantity(data.getQuantity());
            saleItemModel.setSale(saveResponse);
            saleItemModel.setDiscountAmount(data.getDiscountAmount());
            saleItemModel.setWarrantyDuration(data.getWarrantyDuration());
            saleItemModel.setUnitPrice(data.getUnitPrice());

            PhoneModel product = phoneRepo.findById((long) data.getProductId())
                    .orElseThrow(() -> new RuntimeException("product not found"));
            saleItemModel.setProduct(product);
            saleItems.add(saleItemModel);
        }
        return saleItems;
    }
}
