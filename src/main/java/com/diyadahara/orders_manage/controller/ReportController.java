package com.diyadahara.orders_manage.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final SpringTemplateEngine templateEngine;

    public ReportController(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/pdf")
    public void generatePdfReport(HttpServletResponse response) throws Exception {

        // 1️⃣ Sample Data
        List<Order> orders = Arrays.asList(
                new Order(1, "John", 100, "SOLD"),
                new Order(2, "Alice", 200, "PENDING")
        );

        // 2️⃣ Thymeleaf Context
        Context context = new Context();
        context.setVariable("orders", orders);

        // 3️⃣ Render HTML from template
        String htmlContent = templateEngine.process("report", context);

        // 4️⃣ Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders_report.pdf");

        // 5️⃣ Convert HTML to PDF
        OutputStream os = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(os);
        os.close();
    }

    // Sample Order class
    public static class Order {
        private int id;
        private String customerName;
        private double total;
        private String status;

        public Order(int id, String customerName, double total, String status) {
            this.id = id;
            this.customerName = customerName;
            this.total = total;
            this.status = status;
        }

        // Getters
        public int getId() { return id; }
        public String getCustomerName() { return customerName; }
        public double getTotal() { return total; }
        public String getStatus() { return status; }
    }
}

