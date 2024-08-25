Feature: Content Management

  Scenario: Manage Shared Content including Recipes and Posts

    When I access the content management section
    And I create a new recipe titled "Chocolate Cake"
    And I add the recipe details and upload an image
    Then the recipe should be successfully published
    And I should see a confirmation message "Recipe published successfully!"

  Scenario: Manage User Feedback
    When I view the user feedback section
    And I select feedback with ID "feedback123"
    Then I should see the feedback details
    When I mark the feedback as "Resolved"
    Then the feedback status should be updated to "Resolved"
    And I should see a confirmation message "Recipe published successfully!"

