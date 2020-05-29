Feature: User Favorite Products

    Scenario Outline: User wants to add or remove some product to/from the favorite's list
        Given <user> is a logged in user
        And <user> is browsing <page> page and selects a specific one
        When <user> clicks the <button> button
        Then The product is <action> the favorite's list
        And <visibility> be seen on my profile

        Examples:
            | user      | page                  | button                | action        | visibility
            | Alexandra | her favorite products | remove from favorites | removed from  | can no longer
            | Roberto   | some user's products  | add to favorites      | added to      | can now