Feature: GateMate Home Page
  Scenario: User without account sees all flights and goes to the details of the first one
    Given the user is on the homepage
    When the user clicks on the all flights button
    Then the user is redirected to the all flights page 
    Given the first flight
    When the user clicks on the details of the first flight
    Then the user is redirected to the details page of the flight


  Scenario: User without account sees all flights and filters from 'LIS' and goes to the details of the first one
    Given the user is on the homepage
    When the user clicks on the all flights button
    Then the user is redirected to the all flights page 
    Given the user selects "LIS" as the from location
    And the first flight
    When the user clicks on the details of the first flight
    Then the user is redirected to the details page of the flight


  Scenario: User purchase the first flight and Checks in that flight
    Given the user is on the homepage
    When the user clicks on the all flights button
    Then the user is redirected to the all flights page 
    Given the first flight
    When the user clicks on the details of the first flight
    Then the user is redirected to the details page of the flight
    Given the user wants to purchase the flight
    When the user clicks in the purchase button
    Then the user is redirected to the login page
    Given the user enters "ab@gmail.com" as email
    And the user enters "123" as password
    When the user clicks to login
    Then the user loggedin and is redirected to the homepage
    Given the user is on the homepage
    When the user clicks on the all flights button
    Then the user is redirected to the all flights page 
    Given the first flight
    When the user clicks on the details of the first flight
    Then the user is redirected to the details page of the flight
    Given the user wants to purchase the flight
    When the user clicks in the purchase button
    Then the user is redirected to the ticket purchase page
    Given the user enters "Dale" as name on the purchase form
    And the user enters "ab@gmail.com" as email on the purchase form
    And the user enters "123123123" as cc Number on the purchase form
    And the user selects "Visa" in the card type on the purchase form
    And the user enters "123456789" as credit card Number on the purchase form
    And the user enters "2" as month on the purchase form
    And the user enters "2028" as year on the purchase form
    And the user enters "ora_vai_dale" as name of card on the purchase form
    When the user clicks in the final purchase button
    Then the user is redirected to his tickets page
    Given the user in his tickers page
    When the user clicks in the check in button
    Then the flight is checked in


  Scenario: Admin Checks in a ticket
    Given the user is on the homepage
    When the user clicks on the login button
    Then the user is redirected to the login page
    Given the user enters "bc@gmail.com" as email
    And the user enters "123" as password
    When the user clicks to login
    Then the user loggedin and is redirected to the admin homepage
    Given the admin is in the admin page
    When the admin clicks in the Admin Page button
    Then the admin is redirected to the admin page
    Given the admin wants to check in a ticket
    When the admin clicks in the checkin button
    Then the admin has access to the forms
    Given the admin enters "5" as ticket id on the checkin form
    And the admin enters "dale" as name on the checkin form
    And the admin enters "TK8104" as flight iata on the checkin form
    And the admin enters "1" as number of bags on the checkin form
    And the admin enters "20" as weight of the bag on the checkin form
    When the admin clicks in the submit button
    Then the checkin is completed successfully

