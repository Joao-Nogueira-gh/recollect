Feature: Add Comment

    Scenario Outline: User tries to submit a comment on a product
        Given <user> is logged <status>
        And <user> is on an on-sale product page
        When <user> selects the reviews section
        And <user> writes a comment in the text area
        And <user> clicks the submit button
        Then the system displays a <msg>

        Examples:
            | user      | status      | msg     |
            | Alexandra | out     | error message, asking for login     |
            | Francisco   | in | success message, adding the comment |