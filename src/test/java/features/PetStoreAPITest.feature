Feature: Functional Tests for PetStore API
#Note the below first 3 scenarios can be created as 1 scenario with scenario outline and by passing examples with different status
  Background:
   # Given The endpoint is up

@SmokeTest
  Scenario Outline: To verify the count of Pets with different status
    When I perform get request to endpoint "/v2/pet/findByStatus" with status <status>
    Then I should see a response of <responseStatus>
    And I should see a count with <expectedCount> for pet <petName>

  Examples:
  |status|responseStatus|petName|expectedCount|
  |"available"|200|"doggie"|604               |
  |"pending"|200|"doggie"|500                 |
  |"sold"|200|"doggie"|2                      |

@SmokeTest
  Scenario: To verify the count of Pets with 'Available' status
    When I perform get request to endpoint "/v2/pet/findByStatus" with status "available"
    Then I should see a response of 200
    And I should see a count with 604 for pet "doggie"

 @SmokeTest
  Scenario: To verify the count of Pets with 'Pending' status
    When I perform get request to endpoint "/v2/pet/findByStatus" with status "pending"
    Then I should see a response of 200
    And I should see a count with 10 for pet "doggie"

  Scenario: To verify the count of Pets with 'Sold' status
    When I perform get request to endpoint "/v2/pet/findByStatus" with status "sold"
    Then I should see a response of 200
    And I should see a count with 10 for pet "doggie"

  Scenario: To validate the response when Application is down
    Given The swagger service is down for endpoint "/v2/pet/findByStatus"
    When I read the Json response file
    Then I should verify the count for pet "doggie" to be 170


  Scenario: To validate the petStore count when Application is down
    Given The swagger service is down for endpoint "/v2/pet/findByStatus"
    When I read the Json response file
    Then I should see a valid response with count 170;