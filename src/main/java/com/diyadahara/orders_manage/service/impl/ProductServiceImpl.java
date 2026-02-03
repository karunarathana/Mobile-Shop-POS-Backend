package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.config.PhoneCondition;
import com.diyadahara.orders_manage.config.ProductStatus;
import com.diyadahara.orders_manage.dto.*;
import com.diyadahara.orders_manage.model.AccessoryModel;
import com.diyadahara.orders_manage.model.CategoryModel;
import com.diyadahara.orders_manage.model.PhoneModel;
import com.diyadahara.orders_manage.model.ProductModel;
import com.diyadahara.orders_manage.repo.CategoryRepo;
import com.diyadahara.orders_manage.repo.PhoneRepo;
import com.diyadahara.orders_manage.repo.ProductRepo;
import com.diyadahara.orders_manage.repo.SaleItemRepo;
import com.diyadahara.orders_manage.response.BaseAllProductResponse;
import com.diyadahara.orders_manage.response.BaseProductResponse;
import com.diyadahara.orders_manage.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final SaleItemRepo saleItemRepo;
    private final ProductRepo productRepo;
    private final PhoneRepo phoneRepo;
    private final CategoryRepo categoryRepo;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(SaleItemRepo saleItemRepo, ProductRepo productRepo, PhoneRepo phoneRepo, CategoryRepo categoryRepo) {
        this.saleItemRepo = saleItemRepo;
        this.productRepo = productRepo;
        this.phoneRepo = phoneRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public BaseProductResponse createPhone(ProductDto productDto) {
        logger.info("Method Execution Started IN createProduct |ProductDto={} |ProductName={}", productDto, productDto.getProductName());

        //check existing product
        if (phoneRepo.existsByProduct(productDto.getPhoneDto().getImeiNumber()) == null) {
            try {

                ProductModel productModel = generateProductModel(productDto);
                PhoneModel phoneModel = generatePhoneModel(productDto.getPhoneDto());

                phoneModel.setProduct(productModel);
                productModel.setPhone(phoneModel);

                ProductModel productSave = productRepo.save(productModel);
                if (productSave != null) {
                    logger.info("Method Execution Completed IN createProduct |SaveData={}", productSave);
                    return createSuccessProductResponse("Save Product Successfully", HttpStatus.CREATED, productSave);
                }
                logger.info("Method Execution Completed IN createProduct |SaveData={}", productSave);
                return createErrorProductResponse("Failed to create product: " + productDto.getProductName(),
                        HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Error Create Product: {}", e.getMessage(), e);
                return createErrorProductResponse("Failed to create product: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        logger.info("Method Execution Completed IN createProduct |Response={}", productDto.getProductName());
        return createErrorProductResponse("Already Product In System",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public BaseProductResponse createAccessory(ProductAccessory productDto) {
        logger.info("Method Execution Started IN createAccessory |AccessoryDto={} |AccessoryName={}", productDto, productDto.getProductName());

        try {
            ProductModel productModel = generateProductModelWithAccessory(productDto);
            AccessoryModel accessoryModel = generateAccessoryModel(productDto.getAccessoryDto());

            accessoryModel.setProduct(productModel);
            productModel.setAccessoryId(accessoryModel);

            ProductModel productSave = productRepo.save(productModel);
            if (productSave != null) {
                logger.info("Method Execution Completed IN createAccessory |SaveData={}", productSave);
                return createSuccessProductResponse("Save Product Successfully", HttpStatus.CREATED, productSave);
            }
            logger.info("Method Execution Completed IN createAccessory |SaveData={}", productSave);
            return createErrorProductResponse("Failed to create product: " + productDto.getProductName(),
                    HttpStatus.BAD_REQUEST);
        } catch (
                Exception e) {
            logger.error("Error Create Product: {}", e.getMessage(), e);
            return createErrorProductResponse("Failed to create product: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public BaseAllProductResponse viewAllProduct() {
        logger.info("Method Execution Started IN viewAllProduct");
        BaseAllProductResponse baseAllProductResponse = new BaseAllProductResponse();
        try {
            List<ProductModel> allProduct = productRepo.findAll();
            if (allProduct.isEmpty()) {
                logger.info("Method Execution Completed IN viewAllProduct |Data={}", allProduct.size());
                baseAllProductResponse.setStatusCode("200");
                baseAllProductResponse.setMsg("Empty product data In System");
                baseAllProductResponse.setData(allProduct);
            }
            logger.info("Method Execution Completed IN viewAllProduct ?Response={}", allProduct);
            baseAllProductResponse.setStatusCode("200");
            baseAllProductResponse.setMsg("Successfully fetch all product data");
            baseAllProductResponse.setData(allProduct);
            return baseAllProductResponse;
        } catch (Exception e) {
            logger.error("Error View All product: {}", e.getMessage(), e);
            return createErrorProductAllResponse("Failed to view product: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BaseProductResponse viewSingleProduct(int productId) {
        logger.info("Method Execution Started IN viewSingleProduct |ProductId={}", productId);
        Optional<ProductModel> dbResponse = productRepo.findById((long) productId);
        if (dbResponse.isPresent()) {
            logger.info("Method Execution Completed IN viewSingleProduct |ProductId={}", productId);
            return createSuccessProductResponse("Product Data Fetch Successfully", HttpStatus.OK, dbResponse.get());
        }
        logger.info("Method Execution Completed IN viewSingleProduct |ProductId={} |Response={}", productId, "Please Check Product");
        return createErrorProductResponse("Please Check Product", HttpStatus.BAD_REQUEST);
    }
//
//    @Override
//    public BaseProductResponse deleteSingleProduct(int productId) {
//        logger.info("Method Execution Started IN deleteSingleProduct |ProductId={}", productId);
//        try {
//            List<SaleItemModel> existingProduct = saleItemRepo.allSaleItemByProductId((long) productId);
//            if(existingProduct.isEmpty()){
//                phoneRepo.deleteById((long) productId);
//                logger.info("Method Execution Completed IN deleteSingleProduct |ProductId={}", productId);
//                return createSuccessProductResponse("Product Delete Successfully", HttpStatus.OK,null);
//            }
//            saleItemRepo.deleteAllByProductId((long)productId);
//            phoneRepo.deleteById((long) productId);
//            logger.info("Method Execution Completed IN deleteSingleProduct |ProductId={}", productId);
//            return createSuccessProductResponse("Product Delete Successfully", HttpStatus.OK,null);
//        } catch (Exception e) {
//            logger.error("Error deleteSingleProduct: {}", e.getMessage(), e);
//            return createSuccessProductResponse("Failed to view product category: " + e.getMessage(),
//                    HttpStatus.INTERNAL_SERVER_ERROR,null);
//        }
//    }
//
    @Override
    public BaseAllProductResponse viewAllProductByCategory(String type) {
        logger.info("Method Execution Started IN viewAllProductByCategory");
        BaseAllProductResponse baseAllProductResponse = new BaseAllProductResponse();
        try {
            List<ProductModel> allTypeOfProducts = productRepo.allProductByType(type);
            if (allTypeOfProducts.isEmpty()) {
                logger.info("Method Execution Completed IN viewAllProductByCategory |Data={}", allTypeOfProducts.size());
                baseAllProductResponse.setStatusCode("200");
                baseAllProductResponse.setMsg("Empty product data In System");
                baseAllProductResponse.setData(allTypeOfProducts);
            }
            logger.info("Method Execution Completed IN viewAllProductByCategory ?Response={}", allTypeOfProducts);
            baseAllProductResponse.setStatusCode("200");
            baseAllProductResponse.setMsg("Successfully fetch all product data");
            baseAllProductResponse.setData(allTypeOfProducts);
            return baseAllProductResponse;
        } catch (Exception e) {
            logger.error("Error View All product category: {}", e.getMessage(), e);
            return createErrorProductAllResponse("Failed to view product category: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @Override
//    public BaseProductResponse updateSingleProduct(UpdateProductDto productDto) {
//        logger.info("Method Execution Started IN updateSingleProduct |ProductDto={} |ProductName={}", productDto, productDto.getName());
//
//        if (phoneRepo.existsByProduct(productDto.getImeiNumber()) != null) {
//            try {
//                PhoneModel phoneModel = generateUpdateProductModel(productDto);
//                PhoneModel saveData = phoneRepo.save(phoneModel);
//                if (saveData != null) {
//                    logger.info("Method Execution Completed IN updateSingleProduct |SaveData={}", saveData);
//                    return createSuccessProductResponse("Upgrade Product Successfully", HttpStatus.CREATED, saveData);
//                }
//                logger.info("Method Execution Completed IN updateSingleProduct |SaveData={}", saveData);
//                return createErrorProductResponse("Failed to create product: " + productDto.getName(),
//                        HttpStatus.BAD_REQUEST);
//            } catch (Exception e) {
//                logger.error("Error Create Product: {}", e.getMessage(), e);
//                return createErrorProductResponse("Failed to update product: " + e.getMessage(),
//                        HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//        logger.info("Method Execution Completed IN updateSingleProduct |Response={}", productDto.getName());
//        return createErrorProductResponse("Empty Product In System " + productDto.getName(),
//                HttpStatus.BAD_REQUEST);
//    }

    private PhoneModel generatePhoneModel(PhoneDto phoneDto) {
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setModel(phoneDto.getModel());
        CategoryModel category = categoryRepo.findById(phoneDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        phoneModel.setCategoryId(category);
        phoneModel.setUpdatedBy(phoneDto.getCreateBy());
        phoneModel.setBrand(phoneDto.getBrand());
        phoneModel.setCondition(PhoneCondition.valueOf(phoneDto.getCondition().toUpperCase()));
        phoneModel.setImeiNumber(phoneDto.getImeiNumber());
        phoneModel.setColor(phoneDto.getColor());
        phoneModel.setStorageCapacity(phoneDto.getStorageCapacity());
        return phoneModel;
    }

    private AccessoryModel generateAccessoryModel(AccessoryDto accessoryDto){
        AccessoryModel accessoryModel = new AccessoryModel();
        CategoryModel category = categoryRepo.findById(accessoryDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        accessoryModel.setRackId(accessoryDto.getRackId());
        accessoryModel.setBrand(accessoryDto.getBrand());
        accessoryModel.setType(accessoryDto.getType());
        accessoryModel.setCategoryId(category);
        accessoryDto.setCompatibleWith(accessoryDto.getCompatibleWith());
        return accessoryModel;
    }

    private ProductModel generateProductModel(ProductDto productDto) {
        ProductModel productModel = new ProductModel();
        productModel.setProductName(productDto.getProductName());
        productModel.setDiscountPercentage(productDto.getDiscountPercentage());
        productModel.setSellingPrice(productDto.getSellingPrice());
        productModel.setPurchasePrice(productDto.getPurchasePrice());
        productModel.setStatus(ProductStatus.valueOf(productDto.getStatus()));
        productModel.setStock(productDto.getStock());
        productModel.setType(productDto.getType());
        return productModel;
    }
    private  ProductModel generateProductModelWithAccessory(ProductAccessory productAccessory){
        ProductModel productModel = new ProductModel();
        productModel.setProductName(productAccessory.getProductName());
        productModel.setDiscountPercentage(productAccessory.getDiscountPercentage());
        productModel.setSellingPrice(productAccessory.getSellingPrice());
        productModel.setPurchasePrice(productAccessory.getPurchasePrice());
        productModel.setStatus(ProductStatus.valueOf(productAccessory.getStatus()));
        productModel.setStock(productAccessory.getStock());
        productModel.setType(productAccessory.getType());
        return productModel;
    }

    private PhoneModel generateUpdateProductModel(UpdateProductDto productDto) {
        PhoneModel phoneModel = new PhoneModel();
//        phoneModel.setProductId((long)productDto.getProductId());
//        phoneModel.setProductName(productDto.getName());
//        CategoryModel category = categoryRepo.findById(productDto.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        phoneModel.setCategoryId(category);
//        phoneModel.setDiscountPercentage(productDto.getDiscount());
//        phoneModel.setPurchasePrice(productDto.getPurchasePrice());
//        phoneModel.setUpdatedBy(productDto.getCreateBy());
//        phoneModel.setSellingPrice(productDto.getSellingPrice());
//        phoneModel.setModel(productDto.getModel());
//        phoneModel.setBrand(productDto.getBrand());
//        phoneModel.setCondition(productDto.getCondition());
//        phoneModel.setQuantityInStock(productDto.getQuantityInStock());
//        phoneModel.setImeiNumber(productDto.getImeiNumber());
//        phoneModel.setColor(productDto.getColor());
        phoneModel.setStorageCapacity(productDto.getStorageCapacity());

        return phoneModel;
    }

    private BaseProductResponse createSuccessProductResponse(String message, HttpStatus status, ProductModel productModel) {
        BaseProductResponse response = new BaseProductResponse();
        response.setStatusCode(String.valueOf(status.value()));
        response.setMsg(message);
        response.setData(productModel);
        return response;
    }

    private BaseProductResponse createErrorProductResponse(String message, HttpStatus status) {
        BaseProductResponse response = new BaseProductResponse();
        response.setStatusCode(String.valueOf(status.value()));
        response.setMsg(message);
        response.setData(null);
        return response;
    }

    private BaseAllProductResponse createErrorProductAllResponse(String message, HttpStatus status) {
        BaseAllProductResponse response = new BaseAllProductResponse();
        response.setStatusCode(String.valueOf(status.value()));
        response.setMsg(message);
        response.setData(null);
        return response;
    }
}
