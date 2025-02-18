Feature: Override ToxiProxy proprieties

  Scenario: Simulating network latency
    Given I setup proxy with latency of 7000 ms
    When I send a GET request to "/api/test"
    Then the response should be "Success"