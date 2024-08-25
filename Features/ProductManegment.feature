Feature: Product Management
  As a store owner in the Sweet Management System
  I want to manage my products
  So that I can keep my product offerings up-to-date

  Background:


  Scenario: Store owner adds a new product
    Given the store owner is logged in
    And fills in the product details
    Then the new product should be added to the store's product list

  Scenario: Store owner updates a product
    Given the store owner is logged in
    And makes changes to the product details

  Scenario: Store owner removes a product
    Given the store owner is logged in
    When the store owner selects a product to remove

  Scenario: Monitor sales and profits
    Given the store owner is logged in
    When I navigate to the sales dashboard
    And I should see total profits calculated

  Scenario: Identify best-selling products
    Given the store owner is logged in
          When I navigate to the product analytics page
    And the best-selling products should be highlighted


  Scenario: Implement dynamic discount features
    Given the store owner is logged in
    And I select a product to apply a discount
    And the discount details should be visible to customers


  Scenario: Verifying data integrity after order placement
    Given I am logged in as a customer
    And I place an order for a product
    When the order is processed
    Then the product inventory should reflect the updated quantity
    And the order details should be accurate in the order history

  Scenario: Consistent reporting after multiple updates
    Given I am logged in as a store owner
    When I update the price of several products
    And I generate a sales report
    Then the report should accurately reflect the updated product prices