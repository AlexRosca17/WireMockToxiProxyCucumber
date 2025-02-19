Feature: Override ToxiProxy proprieties

  Scenario: Simulating network latency
    Given I setup proxy with latency of 9000 ms
    When I send a GET request to "/api/test"
    Then The response should be 200 with message "Success"