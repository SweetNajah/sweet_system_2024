Feature: Order Management
  As a store owner
  I want to manage orders
  So that I can track and update their status

  Background:
    Given an order is created with a selected product, store owner name, product name, and quantity

  Scenario: Create an Order with Valid Details
    Given a new order with product "Laptop", store owner "Alice", product name "Dell XPS", and quantity 2
    When the order is created
    Then the order status should be "Pending"
    And the order ID should be unique
    And the request date should be today's date
    And the order should not be installed

  Scenario: Update Order Status
    Given an existing order with status "Pending"
    When the store owner updates the order status to "Processed"
    Then the order status should be updated to "Processed"
    And a log entry should indicate the status update

  Scenario: Cancel an Order
    Given an existing order with status "Pending"
    When the store owner cancels the order
    Then the order status should be updated to "Cancelled"
    And a log entry should indicate the order has been cancelled

  Scenario: Calculate Total Price for Multiple Products
    Given an order with multiple products
    When the total price is calculated
    Then the total price should be the sum of the individual product prices

  Scenario: Validate Order Before Processing
    Given an order with missing product details
    When the order is validated
    Then the order should be deemed invalid
    And a warning log entry should indicate the reason for invalidity

  Scenario: Process an Order
    Given a valid order with status "Pending"
    When the order is processed
    Then the order status should be updated to "Processed"
    And a log entry should confirm successful processing

  Scenario: Generate Detailed Receipt for an Order
    Given an order with one or more products
    When a receipt is generated
    Then the receipt should contain the order ID, store owner name, request date, status, product details, total price, and installation status

  Scenario: Filter Orders by Status
    Given multiple orders with various statuses
    When the orders are filtered by "Processed" status
    Then only the orders with "Processed" status should be returned
