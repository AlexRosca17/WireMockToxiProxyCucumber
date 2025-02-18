Feature: Default ToxiProxy proprieties

  Scenario: Simulating network latency
    When I send a GET request to "/api/test"
    Then the response should be "Success"