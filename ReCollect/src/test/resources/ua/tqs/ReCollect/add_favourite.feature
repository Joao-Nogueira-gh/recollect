Feature: Add Item to favourites

  Scenario: A product is not in the favorite list
    Given that I’m a logged-in user
    And I’m on an on-sale product page
    When I click in add to favorites button
    Then the button to remove from favourites appears
    And I can see it on my favorites list

