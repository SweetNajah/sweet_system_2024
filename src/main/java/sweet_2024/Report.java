package sweet_2024;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {


    public void generateFinancialReport() {
        System.out.println("Generating financial report...");

        Map<String, Double> revenues = new HashMap<>();
        revenues.put("January", 15000.0);
        revenues.put("February", 12000.0);
        revenues.put("March", 18000.0);

        Map<String, Double> expenses = new HashMap<>();
        expenses.put("January", 7000.0);
        expenses.put("February", 5000.0);
        expenses.put("March", 6000.0);

        System.out.println("Financial Report:\n");
        System.out.printf("%-10s %-10s %-10s %-10s%n", "Month", "Revenue", "Expenses", "Profit");
        System.out.println("----------------------------------------");

        for (String month : revenues.keySet()) {
            double revenue = revenues.get(month);
            double expense = expenses.get(month);
            double profit = revenue - expense;

            System.out.printf("%-10s %-10.2f %-10.2f %-10.2f%n", month, revenue, expense, profit);
        }

        System.out.println("----------------------------------------");
        System.out.println("Financial report complete.");
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
        System.out.println("Downloading financial report as PDF...");

        String pdfFilePath = "financial_report.pdf";

        try (FileWriter writer = new FileWriter(pdfFilePath)) {
            writer.write("Financial Report\n");
            writer.write("=================\n");
            writer.write("Month: January | Revenue: $15000.00 | Expenses: $7000.00 | Profit: $8000.00\n");
            writer.write("Month: February | Revenue: $12000.00 | Expenses: $5000.00 | Profit: $7000.00\n");
            writer.write("Month: March | Revenue: $18000.00 | Expenses: $6000.00 | Profit: $12000.00\n");

            System.out.println("Financial report downloaded successfully as PDF.");
            return true; // Return true if downloaded successfully
        } catch (IOException e) {
            System.out.println("An error occurred while downloading the report: " + e.getMessage());
            return false; // Return false if there was an error
        }
    }

    public void generateBestSellingProductsReport() {
        System.out.println("Generating report on best-selling products...");

        // Sample best-selling products data
        String[] bestSellingProducts = {"Product A", "Product B", "Product C"};
        int[] salesCounts = {120, 90, 75};

        System.out.printf("%-15s %-10s%n", "Product", "Sales Count");
        System.out.println("---------------------------------");

        for (int i = 0; i < bestSellingProducts.length; i++) {
            System.out.printf("%-15s %-10d%n", bestSellingProducts[i], salesCounts[i]);
        }

        System.out.println("Best-selling products report generated successfully.");
    }

    public Map<String, List<String>> getBestSellingProducts() {
        Map<String, List<String>> bestSellingProducts = new HashMap<>();

        // Sample data
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

        // Dummy data
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
    public void generateUserStatisticsByCity() {
        System.out.println("Generating user statistics by city...");

        // Dummy data for statistics by city
        Map<String, Integer> userStatistics = new HashMap<>();
        userStatistics.put("Jenin", 120);
        userStatistics.put("Nablus", 85);
        userStatistics.put("Ramallah", 95);
        userStatistics.put("Hebron", 75);
        userStatistics.put("Gaza", 100);

        // Print the statistics
        System.out.printf("%-15s %-10s%n", "City", "Number of Users");
        System.out.println("---------------------------------");

        for (Map.Entry<String, Integer> entry : userStatistics.entrySet()) {
            System.out.printf("%-15s %-10d%n", entry.getKey(), entry.getValue());
        }

        System.out.println("User statistics by city generated successfully.");
    }


    // Method to get user statistics by city
    public Map<String, Integer> getUserStatisticsByCity() {
        Map<String, Integer> userStatistics = new HashMap<>();

        // Dummy data
        userStatistics.put("Nablus", 120);
        userStatistics.put("Jenin", 95);

        return userStatistics;
    }

    // Method to get user breakdown by city and store
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
}









