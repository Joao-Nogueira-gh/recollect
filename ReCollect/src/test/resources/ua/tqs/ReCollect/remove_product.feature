Feature: Remove Product

    Scenario Outline: User wants to remove a product previously added to the platform
        Given <user> is a logged in user_
        And <user> has at least a product on <list>
        And <user> is on profile page
        When clicks the delete Item button on <list>
        Then disappears from <list> list

        Examples:
            | user      | list         | 
            | Alexandra | sales        | 
            | Francisco | sold items   |