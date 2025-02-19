Feature: WireMock ToxiProxy Bad Request

  Scenario: WireMock ToxyProxy Bad Request
    Given I setup proxy with latency of 12000 ms
    When I send a GET request to "/BAD"
    Then The response should be 404 with message "Bad Request"