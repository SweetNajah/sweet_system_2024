Feature:Users Management
  Scenario: Add an exist User
    Given I am an admin
    When i choose to add new user but the user is already exist
    Then user added failed

  Scenario: Add a New User
    Given I am an admin
    When i choose to add new user with with valid formatting
    Then user successfully added

  Scenario: Edit an Existing User
    Given I am an admin
    When i choose the user and setting the new value with valid formatting
    Then user successfully updating

  Scenario: Delete a User
    Given I am an admin
    When i choose the user i want to delete
    Then user successfully deleting


  Scenario: Access restrictions for non-admin users
#    Given I am logged in as a regular user
    When I try to access the admin panel
    Then I should see an error message "Access denied."

  Scenario: Attempting sensitive actions without confirmation
  #  Given I am logged in as an admin
    When I attempt to delete a user account
    And I do not confirm the deletion
    Then the user account should not be deleted
    And I should see a message "Action canceled."

  Scenario: Logging out after making changes to inventory
    Given I am logged in as a store owner
    When I add a new product to the inventory
    And I log out
    Then the new product should still be visible in the inventory after logging back in

  Scenario: Simultaneous updates to an order
#    Given User A and User B are logged in
    And both have access to the same order
    When User A updates the order status to "Processed"
    And User B tries to cancel the same order
    Then User B should see an error message "Order already processed and cannot be canceled."
  Scenario: Attempting to navigate to a non-existent menu
    Given I am on the main menu
    When I attempt to navigate to a menu that does not exist
    Then I should see an error message "null"

  Scenario: Submitting feedback with missing required fields
    Given I am logged in as a user
    When I submit feedback without providing a message or rating
    Then I should see an error message "Feedback message and rating are required."

  Scenario: Attempting to add a product with missing fields
    Given I am logged in as a store owner
    When I try to add a product without a name or price
    Then I should see an error message "Product name and price are required."