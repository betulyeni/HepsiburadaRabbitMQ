Feature: RabbitMQ Publish Subscribe Mechanism
  Scenario: Successful Message Publish and Receive
    Given I send the message "Write message here"
    When  I consume the queue
    Then I expect the sent and received message are same




