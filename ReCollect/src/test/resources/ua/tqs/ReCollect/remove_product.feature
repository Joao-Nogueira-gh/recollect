Feature: Remove Product

    Scenario Outline: User wants to remove a product previously added to the platform
        Given <user> is a logged in user
        And <user> is on his/her profile page
        When <user> selects one of the products on my <list> list
        And clicks the delete Item button
        Then The product is removed from the platform
        And disappears from <list> list
        And it is no longer visible to other users

        Examples:
            | user      | list         | 
            | Alexandra | sales        | 
            | Roberto   | sold items   |