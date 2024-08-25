Feature: Exploration and Purchase
  As a beneficiary user
  I want to browse and purchase desserts
  So that I can enjoy a variety of sweets

  Scenario: Browse dessert recipes
    Given I am logged in as a beneficiary user
    When I navigate to the recipes menu
    Then I should see a list of dessert recipes

  Scenario: Filter recipes based on dietary needs
    Given I am logged in as a beneficiary user
    When I navigate to the recipes menu
    And I apply dietary filters
    Then I should see a list of filtered dessert recipes

  Scenario: Purchase desserts
    Given I am logged in as a beneficiary user
    When I navigate to the store menu
    And I select a dessert
    And I chose the purchase option.
    Then I should be able to complete the purchase

  Scenario: High load on feedback submission system
    Given there are multiple users logged in
    When each user submits feedback simultaneously
    Then the system should handle all submissions without crashing
    And each feedback should be correctly saved

  Scenario: Load testing product ordering under high traffic
    Given multiple users are accessing the store
    When each user places an order at the same time
    Then the system should process each order without any delays or errors