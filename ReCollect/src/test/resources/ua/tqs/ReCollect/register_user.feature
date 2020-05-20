Feature: Register user

   Scenario: New credentials
    When A user submits credentials that don't exist in the DB
    Then A new user should be created with the inserted credentials
   Scenario Outline: Registering
    Given <user> is a logged out user
    And <user> is on the login page
    When The email <email> is submitted
    Then <user> should be let known his register <code>

    Examples:
    | email                      | user      | code      |
    | email_em_uso@hotmail.com   | Francisco | failed    |
    | email_livre@hotmail.com    | Alice     | Succeeded |
