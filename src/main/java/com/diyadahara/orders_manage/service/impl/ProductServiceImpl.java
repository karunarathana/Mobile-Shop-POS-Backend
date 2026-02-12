package com.diyadahara.orders_manage.service.impl;

import com.diyadahara.orders_manage.config.PhoneCondition;
import com.diyadahara.orders_manage.config.ProductStatus;
import com.diyadahara.orders_manage.dto.*;
import com.diyadahara.orders_manage.model.*;
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

    @Override
    public BaseAllProductResponse viewAllProductByTypeNotAPhone() {
        logger.info("Method Execution Started IN viewAllProductByTypeNotAPhone");
        BaseAllProductResponse baseAllProductResponse = new BaseAllProductResponse();
        List<ProductModel> otherAccessory = productRepo.allProductByTypeNot("Mobile Phone");
        if (!otherAccessory.isEmpty()) {
            logger.info("Method Execution Completed IN viewAllProductByTypeNotAPhone");
            baseAllProductResponse.setStatusCode("200");
            baseAllProductResponse.setMsg("Fetch all accessory model");
            baseAllProductResponse.setData(otherAccessory);
            return baseAllProductResponse;
        }
        logger.info("Method Execution Completed IN viewAllProductByTypeNotAPhone", otherAccessory, "Please Check Product");
        return baseAllProductResponse;
    }

    @Override
    public BaseProductResponse deleteSingleProduct(int productId) {
        logger.info("Method Execution Started IN deleteSingleProduct |ProductId={}", productId);
        try {
            List<SaleItemModel> existingProduct = saleItemRepo.allSaleItemByProductId((long) productId);
            if (existingProduct.isEmpty()) {
                productRepo.deleteById((long) productId);
                phoneRepo.deleteById((long) productId);
                logger.info("Method Execution Completed IN deleteSingleProduct |ProductId={}", productId);
                return createSuccessProductResponse("Product Delete Successfully", HttpStatus.OK, null);
            }
            saleItemRepo.deleteAllByProductId((long) productId);
            phoneRepo.deleteById((long) productId);
            logger.info("Method Execution Completed IN deleteSingleProduct |ProductId={}", productId);
            return createSuccessProductResponse("Product Delete Successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            logger.error("Error deleteSingleProduct: {}", e.getMessage(), e);
            return createSuccessProductResponse("Failed to view product category: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

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

    @Override
    public BaseProductResponse updateSingleProduct(UpdateAccessoryProductDto productDto) {
        return null;
    }

    @Override
    public BaseProductResponse updateSinglePhoneProduct(UpdatePhoneProductDto phoneProductDto) {
        logger.info("Method Execution Started IN updateSinglePhoneProduct |ProductDto={} |ProductName={}", phoneProductDto, phoneProductDto.getProductName());
        BaseProductResponse baseProductResponse = new BaseProductResponse();
        Optional<ProductModel> productId = productRepo.findById((long) phoneProductDto.getProductId());
        try {
            if(productId.isPresent()){
                ProductModel productModel = generateUpdatePhoneModel(phoneProductDto);
                baseProductResponse.setStatusCode("200");
                baseProductResponse.setMsg("Phone Updated successfully");
                baseProductResponse.setData(productModel);
                logger.info("Method Execution Started IN updateSinglePhoneProduct |Response={}",baseProductResponse);
                return baseProductResponse;
            }
            baseProductResponse.setStatusCode("400");
            baseProductResponse.setMsg("Product not found");
            baseProductResponse.setData(null);
            return baseProductResponse;
        } catch (Exception e) {
            logger.error("Error updateSinglePhoneProduct: {}", e.getMessage(), e);
            baseProductResponse.setMsg(e.getMessage());
            return baseProductResponse;
        }
    }


    @Override
    public BaseProductResponse updateSingleAccessoryProduct(UpdateAccessoryProductDto productDto) {
        logger.info("Method Execution Started IN updateSingleAccessoryProduct |ProductDto={} |ProductName={}", productDto, productDto.getProductName());
        BaseProductResponse baseProductResponse = new BaseProductResponse();
        Optional<ProductModel> productId = productRepo.findById((long) productDto.getProductId());
        try {
            if(productId.isPresent()){
                ProductModel productModel = generateUpdateAccessoryModel(productDto);
                baseProductResponse.setStatusCode("200");
                baseProductResponse.setMsg("Accessory Updated successfully");
                baseProductResponse.setData(productModel);
                logger.info("Method Execution Completed IN updateSingleAccessoryProduct |Response={}",baseProductResponse);
                return baseProductResponse;
            }
            baseProductResponse.setStatusCode("400");
            baseProductResponse.setMsg("Product not found");
            baseProductResponse.setData(null);
            return baseProductResponse;
        } catch (Exception e) {
            logger.error("Error updateSingleAccessoryProduct: {}", e.getMessage(), e);
            baseProductResponse.setMsg(e.getMessage());
            return baseProductResponse;
        }
    }

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

    private AccessoryModel generateAccessoryModel(AccessoryDto accessoryDto) {
        AccessoryModel accessoryModel = new AccessoryModel();
        CategoryModel category = categoryRepo.findById(accessoryDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        accessoryModel.setRackId(accessoryDto.getRackId());
        accessoryModel.setBrand(accessoryDto.getBrand());
        accessoryModel.setColor(accessoryDto.getColor());
        accessoryModel.setCategoryId(category);
        accessoryModel.setCompatibleWith(accessoryDto.getCompatibleWith());
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

    private ProductModel generateProductModelWithAccessory(ProductAccessory productAccessory) {
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

    private ProductModel generateUpdateAccessoryModel(UpdateAccessoryProductDto productDto) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId((long) productDto.getProductId());
        productModel.setProductName(productDto.getProductName());
        productModel.setDiscountPercentage(productDto.getDiscountPercentage());
        productModel.setSellingPrice(productDto.getSellingPrice());
        productModel.setPurchasePrice(productDto.getPurchasePrice());
        productModel.setStatus(ProductStatus.valueOf(productDto.getStatus()));
        productModel.setStock(productDto.getStock());
        productModel.setType(productDto.getType());

        AccessoryModel accessoryModel = new AccessoryModel();
        CategoryModel category = categoryRepo.findById(productDto.getAccessoryDto().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        accessoryModel.setAccessoryId((long)productDto.getAccessoryId());
        accessoryModel.setRackId(productDto.getAccessoryDto().getRackId());
        accessoryModel.setBrand(productDto.getAccessoryDto().getBrand());
        accessoryModel.setColor(productDto.getAccessoryDto().getColor());
        accessoryModel.setCategoryId(category);
        accessoryModel.setCompatibleWith(productDto.getAccessoryDto().getCompatibleWith());

        accessoryModel.setProduct(productModel);
        productModel.setAccessoryId(accessoryModel);
        return productRepo.save(productModel);
    }

    private ProductModel generateUpdatePhoneModel(UpdatePhoneProductDto productDto) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId((long) productDto.getProductId());
        productModel.setProductName(productDto.getProductName());
        productModel.setDiscountPercentage(productDto.getDiscountPercentage());
        productModel.setSellingPrice(productDto.getSellingPrice());
        productModel.setPurchasePrice(productDto.getPurchasePrice());
        productModel.setStatus(ProductStatus.valueOf(productDto.getStatus()));
        productModel.setStock(productDto.getStock());
        productModel.setType(productDto.getType());

        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setPhoneId((long)productDto.getPhoneId());
        phoneModel.setModel(productDto.getPhoneDto().getModel());
        CategoryModel category = categoryRepo.findById(productDto.getPhoneDto().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        phoneModel.setCategoryId(category);
        phoneModel.setUpdatedBy(productDto.getPhoneDto().getCreateBy());
        phoneModel.setBrand(productDto.getPhoneDto().getBrand());
        phoneModel.setCondition(PhoneCondition.valueOf(productDto.getPhoneDto().getCondition().toUpperCase()));
        phoneModel.setImeiNumber(productDto.getPhoneDto().getImeiNumber());
        phoneModel.setColor(productDto.getPhoneDto().getColor());
        phoneModel.setStorageCapacity(productDto.getPhoneDto().getStorageCapacity());

        phoneModel.setProduct(productModel);
        productModel.setPhone(phoneModel);
        return productRepo.save(productModel);
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
