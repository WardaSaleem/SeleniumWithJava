Feature: Validate Data Entry and Calculation for Trade System
@test
  Scenario Outline: Validate data entry and calculations for a branch and product
    Given the user navigates to the "<Branch>" page
    When the user inputs "<Data>" into the system
    Then the system calculates "<ExpectedOutput>"
    And the calculated data is sent to the downstream system successfully

    Examples:
      | Branch | Data| ExpectedOutput |
      | 1      | 2   | $920           |
      | 2      | 4   | $1840          |
      | 3      | 6    | $2760         |
      | 4      | 8    | $3680         |
      | 5      | 10   | $4600         |
      | 6      | 12   | $5520         |
      | 7      | 14   | $6440         |
      | 8      | 16   | $7360         |
      | 9      | 18   | $8280         |
      | 10     | 20   | $9200         |