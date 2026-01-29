package com.diyadahara.orders_manage.service.impl;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SinhalaPdfReportService {
    private static final Logger logger = LoggerFactory.getLogger(SinhalaPdfReportService.class);
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${report.output.dir:./reports}")
    private String outputDir;

    // Font registration for Sinhala
    static {
        try {
            // Register Sinhala fonts
            String fontPath = "src/main/resources/fonts/";

            // Method 1: Using ITextRenderer font mapper
            ITextRenderer renderer = new ITextRenderer();

            // Add Sinhala fonts
            renderer.getFontResolver().addFont(
                    fontPath + "AbhayaLibre-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            renderer.getFontResolver().addFont(
                    fontPath + "AbhayaLibre-Bold.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            logger.info("Sinhala fonts registered successfully");

        } catch (Exception e) {
            logger.error("Failed to register Sinhala fonts: {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 22 * * ?") // Run at 10 PM daily
    public void generateDailySinhalaReport() {
        LocalDate reportDate = LocalDate.now().minusDays(1); // Yesterday's report
        logger.info("Generating Sinhala daily report for: {}", reportDate);

        try {
            // 1. Prepare data for report
            Map<String, Object> reportData = prepareReportData(reportDate);

            // 2. Generate HTML with Thymeleaf
            String htmlContent = generateHtmlContent(reportData);

            // 3. Convert HTML to PDF with Sinhala support
            byte[] pdfBytes = convertHtmlToPdf(htmlContent);

            // 4. Save PDF file
            savePdfToFile(pdfBytes, reportDate);

            logger.info("Sinhala PDF report generated successfully");

        } catch (Exception e) {
            logger.error("Error generating Sinhala PDF report: {}", e.getMessage(), e);
        }
    }

    private Map<String, Object> prepareReportData(LocalDate date) {
        Map<String, Object> data = new HashMap<>();

        // Report metadata
        data.put("reportTitle", "දෛනික විකුණුම් වාර්තාව");
        data.put("reportDate", date);
        data.put("generatedDate", LocalDateTime.now());
        data.put("shopName", "දුරකථන ගබඩාව");
        data.put("shopAddress", "කොළඹ 05, ශ්‍රී ලංකාව");

        // Summary data (in Sinhala)
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalSales", "රු. 125,450.00");
        summary.put("totalTransactions", "45");
        summary.put("cashSales", "රු. 89,250.00");
        summary.put("cardSales", "රු. 36,200.00");
        summary.put("averageSale", "රු. 2,787.78");
        data.put("summary", summary);

        // Top selling products
        List<Map<String, Object>> topProducts = new ArrayList<>();

        Map<String, Object> product1 = new HashMap<>();
        product1.put("name", "සැම්සන්ග් ගැලැක්සි S23");
        product1.put("quantity", "5");
        product1.put("amount", "රු. 375,000");
        topProducts.add(product1);

        Map<String, Object> product2 = new HashMap<>();
        product2.put("name", "ඇපල් අයිෆෝන් 14");
        product2.put("quantity", "3");
        product2.put("amount", "රු. 285,000");
        topProducts.add(product2);

        Map<String, Object> product3 = new HashMap<>();
        product3.put("name", "හෙඩ්ෆෝන් (සෝනි)");
        product3.put("quantity", "12");
        product3.put("amount", "රු. 84,000");
        topProducts.add(product3);

        data.put("topProducts", topProducts);

        // Payment methods
        Map<String, String> payments = new HashMap<>();
        payments.put("කොටස", "රු. 89,250 (71.2%)");
        payments.put("කාඩ්", "රු. 30,200 (24.1%)");
        payments.put("මුදල් මාරුව", "රු. 6,000 (4.7%)");
        data.put("payments", payments);

        // Employee performance
        List<Map<String, Object>> employees = new ArrayList<>();
        // Add employee data...

        return data;
    }

    private String generateHtmlContent(Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);

        // Set locale to Sinhala
        Locale sinhalaLocale = new Locale("si", "LK");
        context.setLocale(sinhalaLocale);

        return templateEngine.process("sinhala-daily-report", context);
    }

    private byte[] convertHtmlToPdf(String htmlContent) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();


        // Set custom font resolver for Sinhala
        FontResolver resolver = renderer.getFontResolver();
        String fontPath = "src/main/resources/fonts/";

        // Set base URL for resources (if you have CSS/images)
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream.toByteArray();
    }

    private void savePdfToFile(byte[] pdfBytes, LocalDate date) throws IOException {
        // Create directory if not exists
        Path dirPath = Paths.get(outputDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Save file with Sinhala name
        String fileName = String.format("දෛනික_වාර්තාව_%s.pdf", date);
        Path filePath = dirPath.resolve(fileName);

        Files.write(filePath, pdfBytes);
        logger.info("PDF saved to: {}", filePath.toAbsolutePath());
    }

    // Manual report generation API
    public byte[] generateCustomReport(Map<String, Object> customData)
            throws IOException, DocumentException {
        String htmlContent = generateHtmlContent(customData);
        return convertHtmlToPdf(htmlContent);
    }
}
