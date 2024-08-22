package sweet_2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Report {
    private static final Logger LOGGER = Logger.getLogger(Report.class.getName());
    private String chosenReportType;

    public String generateFinancialReport() {
        StringBuilder report = new StringBuilder();
        report.append("Generating financial report...\n");

        Map<String, Double> revenues = new HashMap<>();
        revenues.put("January", 15000.0);
        revenues.put("February", 12000.0);
        revenues.put("March", 18000.0);

        Map<String, Double> expenses = new HashMap<>();
        expenses.put("January", 7000.0);
        expenses.put("February", 5000.0);
        expenses.put("March", 6000.0);

        report.append("Financial Report:\n");
        report.append(String.format("%-10s %-10s %-10s %-10s%n", "Month", "Revenue", "Expenses", "Profit"));
        report.append("----------------------------------------\n");

        for (String month : revenues.keySet()) {
            double revenue = revenues.get(month);
            double expense = expenses.get(month);
            double profit = revenue - expense;

            report.append(String.format("%-10s %-10.2f %-10.2f %-10.2f%n", month, revenue, expense, profit));
        }

        report.append("----------------------------------------\n");
        report.append("Financial report complete.\n");
        return report.toString();
    }

    public Map<String, Double> getStoreProfits() {
        Map<String, Double> storeProfits = new HashMap<>();
        storeProfits.put("NubluStors1", 1500.0);
        storeProfits.put("NubluStors2", 2500.5);
        storeProfits.put("JeninStors1", 3200.75);
        storeProfits.put("JeninStors2", 2200.50);
        return storeProfits;
    }

    public boolean downloadFinancialReportAsPDF() {
        LOGGER.info("Downloading financial report as PDF...");

        String pdfFilePath = "financial_report.pdf";
        try (FileWriter writer = new FileWriter(pdfFilePath)) {
            writer.write("Financial Report\n");
            writer.write("=================\n");
            writer.write("Month: January | Revenue: $15000.00 | Expenses: $7000.00 | Profit: $8000.00\n");
            writer.write("Month: February | Revenue: $12000.00 | Expenses: $5000.00 | Profit: $7000.00\n");
            writer.write("Month: March | Revenue: $18000.00 | Expenses: $6000.00 | Profit: $12000.00\n");

            LOGGER.info("Financial report downloaded successfully as PDF.");
            return true;
        } catch (IOException e) {
            LOGGER.severe("An error occurred while downloading the report: " + e.getMessage());
            return false;
        }
    }

    public String generateBestSellingProductsReport() {
        StringBuilder report = new StringBuilder();
        report.append("Generating report on best-selling products...\n");

        String[] bestSellingProducts = {"Product A", "Product B", "Product C"};
        int[] salesCounts = {120, 90, 75};

        report.append(String.format("%-15s %-10s%n", "Product", "Sales Count"));
        report.append("---------------------------------\n");

        for (int i = 0; i < bestSellingProducts.length; i++) {
            report.append(String.format("%-15s %-10d%n", bestSellingProducts[i], salesCounts[i]));
        }

        report.append("Best-selling products report generated successfully.\n");
        return report.toString();
    }


    public Map<String, List<String>> getBestSellingProducts() {
        Map<String, List<String>> bestSellingProducts = new HashMap<>();
        List<String> bestForCategoryA = new ArrayList<>();
        bestForCategoryA.add("product 1");
        bestForCategoryA.add("product 2");
        bestSellingProducts.put("Category A", bestForCategoryA);
        List<String> bestForCategoryB = new ArrayList<>();
        bestForCategoryB.add("Product 3");
        bestForCategoryB.add("Product 4");
        bestSellingProducts.put("Category B", bestForCategoryB);
        return bestSellingProducts;
    }

    public Map<String, Map<String, Double>> getProductSales() {
        Map<String, Map<String, Double>> productSales = new HashMap<>();
        Map<String, Double> salesForProduct1 = new HashMap<>();
        salesForProduct1.put("January", 500.0);
        salesForProduct1.put("February", 300.0);
        productSales.put("Product 1", salesForProduct1);

        Map<String, Double> salesForProduct2 = new HashMap<>();
        salesForProduct2.put("January", 700.0);
        salesForProduct2.put("February", 250.0);
        productSales.put("Product 2", salesForProduct2);

        return productSales;
    }

    public String generateUserStatisticsByCity() {
        StringBuilder report = new StringBuilder();
        report.append("Generating user statistics by city...\n");

        Map<String, Integer> userStatistics = new HashMap<>();
        userStatistics.put("Jenin", 120);
        userStatistics.put("Nablus", 85);
        userStatistics.put("Ramallah", 95);
        userStatistics.put("Hebron", 75);
        userStatistics.put("Gaza", 100);

        report.append(String.format("%-15s %-10s%n", "City", "Number of Users"));
        report.append("---------------------------------\n");

        for (Map.Entry<String, Integer> entry : userStatistics.entrySet()) {
            report.append(String.format("%-15s %-10d%n", entry.getKey(), entry.getValue()));
        }

        report.append("User statistics by city generated successfully.\n");
        return report.toString();
    }


    public Map<String, Integer> getUserStatisticsBy() {
        Map<String, Integer> userStatistics = new HashMap<>();
        userStatistics.put("Nablus", 120);
        userStatistics.put("Jenin", 95);

        return userStatistics;
    }

    public Map<String, Map<String, Integer>> getUserBreakdownByCityAndStore() {
        Map<String, Map<String, Integer>> userBreakdown = new HashMap<>();
        Map<String, Integer> nablusStores = new HashMap<>();
        nablusStores.put("Store A", 50);
        nablusStores.put("Store B", 70);
        userBreakdown.put("Nablus", nablusStores);

        Map<String, Integer> jeninStores = new HashMap<>();
        jeninStores.put("Store A", 30);
        jeninStores.put("Store C", 65);
        userBreakdown.put("Jenin", jeninStores);

        return userBreakdown;
    }

    public void generateInventoryReport() {
        LOGGER.info("Generating inventory report...");
        Map<String, Integer> inventoryDetails = new HashMap<>();
        inventoryDetails.put("Product X", 150);
        inventoryDetails.put("Product Y", 100);
        inventoryDetails.put("Product Z", 200);

        LOGGER.info("Inventory Report Details:");
        LOGGER.info("---------------------------------");

        for (Map.Entry<String, Integer> entry : inventoryDetails.entrySet()) {
            LOGGER.info(String.format("%-15s %-10d units in stock", entry.getKey(), entry.getValue()));
        }

        LOGGER.info("---------------------------------");
        LOGGER.info("Inventory report generated successfully.");
    }


    public void generateReport(String reportType, String filename) throws IOException {
        String reportContent = "";

        switch (reportType) {
            case "Financial":
                generateFinancialReport();
                break;
            case "Sales":
                generateBestSellingProductsReport();
                break;
            case "Inventory":
                 generateInventoryReport();
                break;
            default:
                reportContent = "No such report type available";
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(reportContent);
            LOGGER.info("Report generated and saved successfully: " + filename);
        } catch (IOException e) {
            LOGGER.severe("Failed to write the report to file: " + e.getMessage());
            throw e;
        }
    }

    private String compareBestSellingProducts() {
        Map<String, Map<String, Integer>> salesData = new HashMap<>();
        salesData.put("Nablus", Map.of("Product A", 150, "Product B", 120));
        salesData.put("Jenin", Map.of("Product A", 140, "Product B", 130));

        StringBuilder report = new StringBuilder("Comparison of Best-Selling Products:\n");
        report.append(String.format("%-15s %-15s %-15s\n", "Product", "Nablus", "Jenin"));
        report.append("--------------------------------------------\n");

        Set<String> products = new HashSet<>(salesData.get("Nablus").keySet());
        products.addAll(salesData.get("Jenin").keySet());

        for (String product : products) {
            int nablusSales = salesData.get("Nablus").getOrDefault(product, 0);
            int jeninSales = salesData.get("Jenin").getOrDefault(product, 0);
            report.append(String.format("%-15s %-15d %-15d\n", product, nablusSales, jeninSales));
        }

        return report.toString();
    }

    private String totalRegisteredUsers() {
        Map<String, Integer> userStatistics = new HashMap<>();
        userStatistics.put("Nablus", 120);
        userStatistics.put("Jenin", 95);
        userStatistics.put("Ramallah", 110);

        int totalUsers = userStatistics.values().stream().mapToInt(Integer::intValue).sum();

        return "Total Registered Users: " + totalUsers;
    }



    public String generateReport(String type) {
        switch (type) {
            case "Financial Report for Nablus Stores":
                return generateFinancialReport();
            case "Financial Report for Jenin Stores":
                return generateFinancialReport();
            case "Best-Selling Products in Nablus":
                return generateBestSellingProductsReport();
            case "Best-Selling Products in Jenin":
                return generateBestSellingProductsReport();
            case "Comparison of Best-Selling Products":
                return compareBestSellingProducts();
            case "Registered Users in Nablus":
                return generateUserStatisticsByCity();
            case "Registered Users in Jenin":
                return generateUserStatisticsByCity();
            case "Total Registered Users":
                return totalRegisteredUsers();
            default:
                return "Unknown report type";
        }
    }

    public void setReportType(String chosenReportType) {
        this.chosenReportType = chosenReportType;
    }

    public String getReportType() {
        return this.chosenReportType;
    }


}
