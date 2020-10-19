Feature: RabbitMQ Publish Subscribe Mechanism
  Scenario: Successful Message Publish and Receive
    Given I send the message "Write Message Here"
    When  I consume the Test_One queue queue
    When  I consume the Test_Two queue queue
    When  I consume the Test_Three queue queue
    Then  I expect the sent and received message are same




