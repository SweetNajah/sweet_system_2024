Feature: Monitoring and Reporting
  Scenario: Monitor Profits and Generate Financial Reports for Each Store
    Given I am an admin
    When I choose to generate a financial report for the stores
    Then the system should calculate and display the total profits for each store
    And the report should be available separately for the two stores in Nablus and the two stores in Jenin
    And the report should be downloadable in PDF format
  Scenario: Identify Best-Selling Products by Store and City
    Given I am an admin
    When I request a report on best-selling products
    Then the system should display a list of best-selling products for each store
    And the report should include a comparison of best-selling products between the stores in Nablus and the stores in Jenin
    And the report should include total units sold and revenue generated for each product in each store
  Scenario: Display Statistics on Registered Users by City (Nablus and Jenin)
    Given I am an admin
    When I request user statistics by city
    Then the system should display the number of registered users for each city
    And the report should show a breakdown of users registered with the stores in Nablus and the stores in Jenin
    And the report should include a total count of users for each city