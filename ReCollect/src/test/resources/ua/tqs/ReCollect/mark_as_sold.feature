Feature: Mark Product as Sold

    Scenario Outline: User wants to indicate that one of the user's products has been sold
        Given <user> is a logged in user__
        And <user> is on profile page_
        And has at least one item in sales list
        And clicks the mark as sold button
        Then The product is removed from the sales list
        And the product is added to the sold items list

        Examples:
            | user      |
            | Alexandra |
