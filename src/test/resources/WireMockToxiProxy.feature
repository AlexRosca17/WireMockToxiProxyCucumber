Feature: Proxy Configuration Testing

  Scenario: Simulating network latency
    Given I setup proxy with latency of 6000 ms
    When I send a GET request to "/api/test"
    Then the response should be "Success"