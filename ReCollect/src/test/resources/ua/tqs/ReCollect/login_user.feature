Feature: Login User
    Scenario: Provided credentials are valid
        Given that I am a logged out user
            And I am on the login page
        When I fill in the email and password fields with my credentials
            And I click the login button
        Then the system signs me in successfully
    
    Scenario: Provided credentials are invalid
        Given that I am a logged out user
            And I am on the login page
        When I fill in the email and password fields with invalid credentials
            And I click the login button
        Then the system displays an error message