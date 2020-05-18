Feature: Login user

    Registered users want to log in to the platform

    Scenario Outline: Francisco is on the signup page
        Given that I'm a logged out user
        And I'm on the Signup page
        When I fill in the 'name', 'email' and 'password', 'Municipality' and 'District'
        And check the checkbox of the Terms and Conditions
        Then the system signs up