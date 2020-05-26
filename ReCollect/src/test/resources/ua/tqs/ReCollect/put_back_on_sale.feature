Feature: Put item back on sale

  Scenario Outline: User wants to indicate that one of his products has been put back on sale
    Given <user> is a logged in user___
    And <user> is on profile page__
    And has at least one item in sold items list
    And clicks the put back on sale button
    Then The product is removed from the sold items list
    And the product is added to the on sale items list

    Examples:
      | user      |
      | Alexandra |
