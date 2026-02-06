package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.dto.SaleDto;
import com.diyadahara.orders_manage.dto.SaleItemDTO;
import com.diyadahara.orders_manage.model.*;
import com.diyadahara.orders_manage.repo.*;
import com.diyadahara.orders_manage.response.BaseSaleResponse;
import com.diyadahara.orders_manage.response.CustomSaleItems;
import com.diyadahara.orders_manage.response.CustomSaleResponse;
import com.diyadahara.orders_manage.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {
    private final CustomerRepo customerRepo;
    private final WarrantyRepo warrantyRepo;
    private final AccessoryRepo accessoryRepo;
    private final PhoneRepo phoneRepo;
    private final ProductRepo productRepo;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    public SaleServiceImpl(CustomerRepo customerRepo, WarrantyRepo warrantyRepo, AccessoryRepo accessoryRepo, PhoneRepo phoneRepo, ProductRepo productRepo, SaleRepo saleRepo, SaleItemRepo saleItemRepo) {
        this.customerRepo = customerRepo;
        this.warrantyRepo = warrantyRepo;
        this.accessoryRepo = accessoryRepo;
        this.phoneRepo = phoneRepo;
        this.productRepo = productRepo;
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
            return e.getMessage();
        }

    }


    @Override
    public BaseSaleResponse viewAllSale() {
        logger.info("Method execution started in viewAllSale");

        BaseSaleResponse baseSaleResponse = new BaseSaleResponse();
        List<CustomSaleResponse> allSaleResponse = new ArrayList<>();

        try {
            List<SaleModel> allSale = saleRepo.findAll();

            for (SaleModel sale : allSale) {

                CustomSaleResponse customSaleResponse = new CustomSaleResponse();
                List<CustomSaleItems> relativeItem = new ArrayList<>();

                customSaleResponse.setCustomerName(sale.getCustomer().getCustomerName());
                customSaleResponse.setEmailAddress(sale.getCustomer().getCustomerEmail());
                customSaleResponse.setPhoneNumber(sale.getCustomer().getPhoneNumber());
                customSaleResponse.setReturnMoney("No Money");
                customSaleResponse.setTotalPayment(String.valueOf(sale.getTotalAmount()));

                List<SaleItemModel> saleItems =
                        saleItemRepo.allSaleItemBySaleId(sale.getSaleId());

                for (SaleItemModel saleItem : saleItems) {
                    CustomSaleItems customSaleItems = new CustomSaleItems();

                    Optional<ProductModel> byId =
                            productRepo.findById(Long.parseLong(saleItem.getProductId()));

                    if (byId.isPresent()) {
                        customSaleItems.setProductName(byId.get().getProductName());
                    }

                    customSaleItems.setQty(String.valueOf(saleItem.getQuantity()));
                    customSaleItems.setUnitPrice(String.valueOf(saleItem.getUnitPrice()));
                    customSaleItems.setWarrantyDays("1Day");

                    relativeItem.add(customSaleItems);
                }

                customSaleResponse.setCustomSaleItems(relativeItem);
                allSaleResponse.add(customSaleResponse);
            }

            baseSaleResponse.setStatusCode("200");
            baseSaleResponse.setMsg("Fetched All Sales Data");
            baseSaleResponse.setSaleItems(allSaleResponse);
            return baseSaleResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

            // ===== COMMON FIELDS =====
            saleItemModel.setQuantity(data.getQuantity());
            saleItemModel.setSale(saveResponse);
            saleItemModel.setProductType(data.getProductType());
            saleItemModel.setDiscountAmount(data.getDiscountAmount());
            saleItemModel.setWarrantyDuration(data.getWarrantyDuration());
            saleItemModel.setUnitPrice(data.getUnitPrice());
            saleItemModel.setProductId(data.getProductId().toString());

            saleItems.add(saleItemModel);

        }
        return saleItems;
    }


}
