package com.diyadahara.orders_manage.controller;

import com.diyadahara.orders_manage.dto.report.TodaySales;
import com.diyadahara.orders_manage.model.*;
import com.diyadahara.orders_manage.repo.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final ReloadRepo reloadRepo;
    private final ProductRepo productRepo;
    private final ExpensesRepo expensesRepo;
    private final SpringTemplateEngine templateEngine;

    public ReportController(SaleRepo saleRepo, SaleItemRepo saleItemRepo, ReloadRepo reloadRepo, ProductRepo productRepo, ExpensesRepo expensesRepo, SpringTemplateEngine templateEngine) {
        this.saleRepo = saleRepo;
        this.saleItemRepo = saleItemRepo;
        this.reloadRepo = reloadRepo;
        this.productRepo = productRepo;
        this.expensesRepo = expensesRepo;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/pdf")
    public void generatePdfReport(HttpServletResponse response) throws Exception {
        double todaySaleTotal = 0;
        List<TodaySales> todaySales = new ArrayList<>();

        List<SaleModel> allSaleResponse = saleRepo.findAll();
        for (SaleModel details: allSaleResponse){
            List<SaleItemModel> saleItemList = saleItemRepo.allSaleItemBySaleId(details.getSaleId());
            todaySaleTotal+= details.getTotalAmount();
            for(SaleItemModel saleItems : saleItemList){
                TodaySales sale = new TodaySales();
                sale.setCusName(details.getCustomer().getCustomerName());
                sale.setProductId(saleItems.getProductId());
                Optional<ProductModel> byId = productRepo.findById(Long.valueOf(saleItems.getProductId()));
                sale.setProductName(byId.get().getProductName());
                sale.setPrice(String.valueOf(saleItems.getUnitPrice()));
                sale.setQty(String.valueOf(saleItems.getQuantity()));
                todaySales.add(sale);
            }
        }
        List<ExpensesModel> todayExpensive = expensesRepo.findAll();
        List<ReloadModel> todayAllReload = reloadRepo.findAll();


        Context context = new Context();
        context.setVariable("orders", todaySales);
        context.setVariable("expensive", todayExpensive);
        context.setVariable("totalSales", todaySaleTotal);
//        context.setVariable("totalExpenses", "5000");

        String htmlContent = templateEngine.process("report", context);

        // 🔹 File path (change as needed)
        String filePath = "C:\\Users\\User\\Desktop\\Maheesha Mobile POS\\orders_report.pdf";

        OutputStream fileOutputStream = new FileOutputStream(filePath);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(fileOutputStream);

        fileOutputStream.close();

        System.out.println("PDF saved successfully at: " + filePath);
    }

}

