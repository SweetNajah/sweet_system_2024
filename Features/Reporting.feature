Feature: Monitoring and Reporting

  @report
  Scenario: Generating a financial report for each store
    Given I am an admin(report)
    Then I am asked to choose report1 kind "Financial Report for Nablus Stores"
    And The report1 details are printed in a file "financial_report_nablus.txt"
    Then I am asked to choose report2 kind "Financial Report for Jenin Stores"
    And The report2 details are printed in a file "financial_report_jenin.txt"

  @report
  Scenario: Identifying best-selling products by store and city
    Given I am an admin(report)
    Then I am asked to choose report1 kind "Best-Selling Products in Nablus"
    And The report1 details are printed in a file "best_selling_nablus.txt"
    Then I am asked to choose report2 kind "Best-Selling Products in Jenin"
    And The report2 details are printed in a file "best_selling_jenin.txt"
    Then I am asked to choose report3 kind "Comparison of Best-Selling Products"
    And The report3 details are printed in a file "best_selling_comparison.txt"

  @report
  Scenario: Displaying statistics on registered users by city
    Given I am an admin(report)
    Then I am asked to choose report1 kind "Registered Users in Nablus"
    And The report1 details are printed in a file "registered_users_nablus.txt"
    Then I am asked to choose report2 kind "Registered Users in Jenin"
    And The report2 details are printed in a file "registered_users_jenin.txt"
    Then I am asked to choose report3 kind "Total Registered Users"
    And The report3 details are printed in a file "total_registered_users.txt"