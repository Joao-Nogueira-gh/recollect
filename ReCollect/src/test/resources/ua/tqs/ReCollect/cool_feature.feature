Feature: Cool user

   Scenario: Cool credentials
    When A user submits credentials that don't exist in the DB
    Then A new user should be created with the inserted credentials