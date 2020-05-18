Feature: Register user

   Scenario Outline: Registering
    Given <user> is a logged out user
    And <user> is on the login page
    When The email <email> is submitted
    Then <user> should be let known his register <code>

    Examples:
    | email                      | user      | code      |
    | email_em_uso@hotmail.com   | Francisco | failed    |
    | email_livre@hotmail.com    | Alice     | Succeeded |
