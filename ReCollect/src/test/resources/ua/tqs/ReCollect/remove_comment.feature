Feature: Remove Comment

  Scenario: Francisco deletes a comment on a product
    Given Francisco is logged in
    And Francisco is on an on-sale product page
    When Francisco selects the reviews section
    And Francisco clicks the delete button on one of his comments
    Then the comment disappears from the platform
    