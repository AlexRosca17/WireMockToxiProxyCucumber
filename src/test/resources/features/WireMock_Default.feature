Feature: Default ToxiProxy proprieties

  Scenario: Simulating network latency
    When I send a GET request to "/api/test"
    Then The response should be 200 with message "Success"