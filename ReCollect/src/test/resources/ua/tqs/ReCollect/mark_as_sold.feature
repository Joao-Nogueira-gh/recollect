Feature: Mark Product as Sold

    Scenario Outline: User wants to indicate that one of the user's product's has been sold
        Given <user> is a logged in user
        And <user> is on his/her profile page
        When <user> selects one of the products on my sales list
        And clicks the mark as sold button
        Then The product is removed from the sales list
        And the product is added to the sold items list
        And it is no longer visible to other users

        Examples:
            | user      |
            | Alexandra |
            | Roberto   |