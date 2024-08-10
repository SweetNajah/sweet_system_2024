

Feature: Communication and Feedback
  As a beneficiary user
  I want to communicate with store owners and suppliers
  So that I can get assistance and provide feedback

  Scenario: Send an inquiry
    Given I am logged in as a beneficiary user
    When I navigate to the messaging system
    And I compose an inquiry
    Then the inquiry should be sent

  Scenario: Provide feedback on purchased products
    Given I am logged in as a beneficiary user
    When I navigate to the feedback system
    And I select a purchased product
    And I provide my feedback
    Then my feedback should be submitted

