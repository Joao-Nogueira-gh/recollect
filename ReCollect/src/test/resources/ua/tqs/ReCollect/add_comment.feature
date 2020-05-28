Feature: Add Comment

    Scenario: User submits a comment on a product
        Given Alexandra is logged in
        And Alexandra is on an on-sale product page
        When Alexandra selects the reviews section
        And Alexandra writes a comment in the text area
        And Alexandra clicks the submit button
        Then the comment is displayed in the platform


    Scenario: User tries to submit an empty comment on a product
        Given Alexandra is logged in
        And Alexandra is on an on-sale product page
        When Alexandra selects the reviews section
        And Alexandra clicks the submit button
        Then the system shows an error message
