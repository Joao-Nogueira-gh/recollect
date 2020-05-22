Feature: Add Product

    Scenario Outline: User tries to submit a product to the platform
        Given <user> is a logged in user
        And <user> is on the Announce page
        When <user> fills in <qt> the fields with <user>s product information
        And clicks the Submit Item button
        Then The product <action> go on sale on the platform
        And <action> be visible to other users
        And Error messages <action> pop up

        Examples:
            | user      | qt      | action     |
            | Alexandra | all     | should     |
            | Roberto   | some of | should not |