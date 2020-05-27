Feature: Search by category

  Scenario Outline: User wants to search products by category
    Given <user> is in the home page
    When <user> clicks the search button
    Then he sees the search results page
    And results are for <category>

    Examples:
      | user      | category     |
      | Francisco | BOOKS        |

  Scenario Outline: Logged user wants to search products category
    Given at least one <category> product exists
    And <user> is in the home page
    When <user> clicks the search button
    Then he sees the search results page
    And results are for <category>
    And number of results is more than zero

    Examples:
      | user      | category     |
      | Francisco | BOOKS        |

  Scenario Outline: User wants to search products by different categories
    Given <user> is in the home page
    When <user> clicks the search for <category> button
    Then he sees the search results page
    And results are for <category>

    Examples:
      | user      | category     |
      | Francisco | TOYS         |
      | Francisco | MISC         |