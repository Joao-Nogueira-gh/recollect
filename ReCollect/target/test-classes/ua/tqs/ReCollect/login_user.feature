Feature: Login User

    Scenario Outline: Logging in
        Given <user> is a logged out user
        And <user> is on the login page
        When <user> fills in the email field with <email> and the password field with <password>
        And clicks the login button
        Then the system <outcome>

        Examples:
            | user  | email           | password  | outcome                          |
            | Alice | alice@email.com | pwd       | signs Alice in                   |
            | Bob   | bob@email.com   | bobIsCool | informs about unregistered email |
            | Craig | alice@email.com | gexor929  | informs about wrong credentials  |

# Feature: Login User
#     Scenario: Provided credentials are valid
#         Given that I am a logged out user
#             And I am on the login page
#         When I fill in the email and password fields with my credentials
#             And I click the login button
#         Then the system signs me in successfully
    
#     Scenario: Provided credentials are invalid
#         Given that I am a logged out user
#             And I am on the login page
#         When I fill in the email and password fields with invalid credentials
#             And I click the login button
#         Then the system displays an error message
