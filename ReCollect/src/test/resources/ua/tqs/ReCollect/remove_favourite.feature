Feature: Remove item from favourites

  Scenario: The user is in the favorite list
    Given that I’m a logged-in user_
    And I’m on my profile page
    When I see a product in my favorite list
    And I click the delete button
    Then my product is removed from the list
    And disappears from my favorite list

  Scenario: The user is in an on-sale product page
    Given that I’m a logged-in user_
    And I’m on an on-sale product page_
    When I click the remove from favourites button
    Then the button to mark as favourite appears
    And the product disappears from my favorite list

