Feature: Manage Blog Posts
  As a user
  I want to create, update, and view blog posts
  So that I can manage content on my blog

  Background:
    Given a post is created with title "First Post", content "This is the content of the first post.", and author "Alice"

  Scenario: Create a Post with Valid Details
    Given a new post with title "First Post", content "This is the content of the first post.", and author "Alice"
    When the post is created
    Then the post title should be "First Post"
    And the post content should be "This is the content of the first post."
    And the post author should be "Alice"

  Scenario: Update Post Title
    Given an existing post with title "First Post"
    When the user updates the post title to "Updated Post Title"
    Then the post title should be updated to "Updated Post Title"

  Scenario: Update Post Content
    Given an existing post with content "This is the content of the first post."
    When the user updates the post content to "This is the updated content of the post."
    Then the post content should be updated to "This is the updated content of the post."

  Scenario: Validate Post Information
    Given a post with title "Sample Post", content "This is sample content.", and author "John Doe"
    When the post information is validated
    Then the post should have a title
    And the post should have content
    And the post should have an author
